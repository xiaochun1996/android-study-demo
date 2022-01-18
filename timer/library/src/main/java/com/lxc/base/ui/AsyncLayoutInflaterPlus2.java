package com.lxc.base.ui;
/**
 * Created by lxc on 1/10/22 2:53 PM.
 * Desc:
 */
/*
 * Copyright 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.core.util.Pools;
import androidx.core.view.LayoutInflaterCompat;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * <p>Helper class for inflating layouts asynchronously. To use, construct
 * an instance of {@link AsyncLayoutInflaterPlus2} on the UI thread and call
 * {@link #inflate(int, ViewGroup, AsyncLayoutInflaterPlus2.OnInflateFinishedListener)}. The
 * {@link AsyncLayoutInflaterPlus2.OnInflateFinishedListener} will be invoked on the UI thread
 * when the inflate request has completed.
 *
 * <p>This is intended for parts of the UI that are created lazily or in
 * response to user interactions. This allows the UI thread to continue
 * to be responsive & animate while the relatively heavy inflate
 * is being performed.
 *
 * <p>For a layout to be inflated asynchronously it needs to have a parent
 * whose {@link ViewGroup#generateLayoutParams(AttributeSet)} is thread-safe
 * and all the Views being constructed as part of inflation must not create
 * any {@link Handler}s or otherwise call {@link Looper#myLooper()}. If the
 * layout that is trying to be inflated cannot be constructed
 * asynchronously for whatever reason, {@link AsyncLayoutInflaterPlus2} will
 * automatically fall back to inflating on the UI thread.
 *
 * <p>NOTE that the inflated View hierarchy is NOT added to the parent. It is
 * equivalent to calling {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
 * with attachToRoot set to false. Callers will likely want to call
 * {@link ViewGroup#addView(View)} in the {@link AsyncLayoutInflaterPlus2.OnInflateFinishedListener}
 * callback at a minimum.
 *
 * <p>This inflater does not support setting a {@link LayoutInflater.Factory}
 * nor {@link LayoutInflater.Factory2}. Similarly it does not support inflating
 * layouts that contain fragments.
 */
public final class AsyncLayoutInflaterPlus2 {
    private static final String TAG = "AsyncLayoutInflaterPlus2";

    LayoutInflater mInflater;
    Handler mHandler;
    AsyncLayoutInflaterPlus2.InflateThread mInflateThread;

    public AsyncLayoutInflaterPlus2(@NonNull Context context, LayoutInflater.Factory2 factory2) {
        mInflater = new AsyncLayoutInflaterPlus2.BasicInflater(context);
        LayoutInflaterCompat.setFactory2(mInflater, factory2);
        mHandler = new Handler(mHandlerCallback);
        mInflateThread = AsyncLayoutInflaterPlus2.InflateThread.getInstance();
    }

    @UiThread
    public void inflate(@LayoutRes int resid, @Nullable ViewGroup parent,
                        @NonNull AsyncLayoutInflaterPlus2.OnInflateFinishedListener callback) {
        if (callback == null) {
            throw new NullPointerException("callback argument may not be null!");
        }
        AsyncLayoutInflaterPlus2.InflateRequest request = mInflateThread.obtainRequest();
        request.inflater = this;
        request.resid = resid;
        request.parent = parent;
        request.callback = callback;
        mInflateThread.enqueue(request);
    }

    private Handler.Callback mHandlerCallback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            AsyncLayoutInflaterPlus2.InflateRequest request = (AsyncLayoutInflaterPlus2.InflateRequest) msg.obj;
            if (request.view == null) {
                request.view = mInflater.inflate(
                        request.resid, request.parent, false);
            }
            request.callback.onInflateFinished(
                    request.view, request.resid, request.parent);
            mInflateThread.releaseRequest(request);
            return true;
        }
    };

    public interface OnInflateFinishedListener {
        void onInflateFinished(@NonNull View view, @LayoutRes int resid,
                               @Nullable ViewGroup parent);
    }

    private static class InflateRequest {
        AsyncLayoutInflaterPlus2 inflater;
        ViewGroup parent;
        int resid;
        View view;
        AsyncLayoutInflaterPlus2.OnInflateFinishedListener callback;

        InflateRequest() {
        }
    }

    private static class BasicInflater extends LayoutInflater {
        private static final String[] sClassPrefixList = {
                "android.widget.",
                "android.webkit.",
                "android.app."
        };

        BasicInflater(Context context) {
            super(context);
//            if (context instanceof AppCompatActivity) {
//                // 加上这些可以保证AppCompatActivity的情况下，super.onCreate之前
//                // 使用AsyncLayoutInflater加载的布局也拥有默认的效果
//                AppCompatDelegate appCompatDelegate = ((AppCompatActivity) context).getDelegate();
//                if (appCompatDelegate instanceof LayoutInflater.Factory2) {
//                    LayoutInflaterCompat.setFactory2(this, (LayoutInflater.Factory2) appCompatDelegate);
//                }
//            }
        }

        @Override
        public LayoutInflater cloneInContext(Context newContext) {
            return new AsyncLayoutInflaterPlus2.BasicInflater(newContext);
        }

        @Override
        protected View onCreateView(String name, AttributeSet attrs) throws ClassNotFoundException {
            for (String prefix : sClassPrefixList) {
                try {
                    View view = createView(name, prefix, attrs);
                    if (view != null) {
                        return view;
                    }
                } catch (ClassNotFoundException e) {
                    // In this case we want to let the base class take a crack
                    // at it.
                }
            }

            return super.onCreateView(name, attrs);
        }
    }

    private static class InflateThread extends Thread {
        private static final AsyncLayoutInflaterPlus2.InflateThread sInstance;
        static {
            sInstance = new AsyncLayoutInflaterPlus2.InflateThread();
            sInstance.start();
        }

        public static AsyncLayoutInflaterPlus2.InflateThread getInstance() {
            return sInstance;
        }

        private ArrayBlockingQueue<AsyncLayoutInflaterPlus2.InflateRequest> mQueue = new ArrayBlockingQueue<>(10);
        private Pools.SynchronizedPool<InflateRequest> mRequestPool = new Pools.SynchronizedPool<>(10);

        // Extracted to its own method to ensure locals have a constrained liveness
        // scope by the GC. This is needed to avoid keeping previous request references
        // alive for an indeterminate amount of time, see b/33158143 for details
        @SuppressLint("LongLogTag")
        public void runInner() {
            AsyncLayoutInflaterPlus2.InflateRequest request;
            try {
                request = mQueue.take();
            } catch (InterruptedException ex) {
                // Odd, just continue
                Log.w(TAG, ex);
                return;
            }

            try {
                request.view = request.inflater.mInflater.inflate(
                        request.resid, request.parent, false);
            } catch (RuntimeException ex) {
                // Probably a Looper failure, retry on the UI thread
                Log.w(TAG, "Failed to inflate resource in the background! Retrying on the UI"
                        + " thread", ex);
            }
            Message.obtain(request.inflater.mHandler, 0, request)
                    .sendToTarget();
        }

        @Override
        public void run() {
            while (true) {
                runInner();
            }
        }

        public AsyncLayoutInflaterPlus2.InflateRequest obtainRequest() {
            AsyncLayoutInflaterPlus2.InflateRequest obj = mRequestPool.acquire();
            if (obj == null) {
                obj = new AsyncLayoutInflaterPlus2.InflateRequest();
            }
            return obj;
        }

        public void releaseRequest(AsyncLayoutInflaterPlus2.InflateRequest obj) {
            obj.callback = null;
            obj.inflater = null;
            obj.parent = null;
            obj.resid = 0;
            obj.view = null;
            mRequestPool.release(obj);
        }

        public void enqueue(AsyncLayoutInflaterPlus2.InflateRequest request) {
            try {
                mQueue.put(request);
            } catch (InterruptedException e) {
                throw new RuntimeException(
                        "Failed to enqueue async inflate request", e);
            }
        }
    }
}

