package com.jock.thread;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlinx.coroutines.DelayKt;

/**
 * Description: 协程原理探究
 * Author: lxc
 * Date: 2022/1/20 08:32
 */
class CoroutinesDemo2 {

    public static final Object request1(Continuation preCallback) {

        ContinuationImpl request1Callback;
        if (!(preCallback instanceof ContinuationImpl) || (((ContinuationImpl) preCallback).label & Integer.MIN_VALUE) == 0) {
            request1Callback = new ContinuationImpl(preCallback) {

                @Override
                Object invokeSuspend(@NonNull Object resumeResult) {
                    this.result = resumeResult;
                    // 代表已经进行过包装了
                    this.label |= Integer.MIN_VALUE;
                    System.out.println("request1 has resumed");
                    return request1(this);
                }
            };
        } else {
            request1Callback = (ContinuationImpl) preCallback;
        }
        switch (request1Callback.label){
            case  0:{
                Object request2 = request2(request1Callback);
                // 如果返回是挂起，则返回挂起状态
                if(request2 == IntrinsicsKt.getCOROUTINE_SUSPENDED()){
                    System.out.println("request1 has suspended");
                    return IntrinsicsKt.getCOROUTINE_SUSPENDED();
                }
            }
        }
        System.out.println("request1 completed");
        return "result from request1"+request1Callback.result;
    }

    public static final Object request2(Continuation preCallback) {

        ContinuationImpl request2Callback;
        if (!(preCallback instanceof ContinuationImpl) || (((ContinuationImpl) preCallback).label & Integer.MIN_VALUE) == 0) {
            request2Callback = new ContinuationImpl(preCallback) {

                @Override
                Object invokeSuspend(@NonNull Object resumeResult) {
                    this.result = resumeResult;
                    // 代表已经进行过包装了.下次恢复的时候不需要进行包装了
                    this.label |= Integer.MIN_VALUE;
                    System.out.println("request2 has resumed");
                    return request2(this);
                }
            };
        } else {
            request2Callback = (ContinuationImpl) preCallback;
        }
        switch (request2Callback.label){
            case  0:{
                Object delay = DelayKt.delay(2000, request2Callback);
                // 如果返回是挂起，则返回挂起状态
                if(delay == IntrinsicsKt.getCOROUTINE_SUSPENDED()){
                    System.out.println("request2 has suspended");
                    return IntrinsicsKt.getCOROUTINE_SUSPENDED();
                }
            }
        }
        System.out.println("request2 completed");
        return "result from request2";
    }


    static abstract class ContinuationImpl<T> implements Continuation<T> {

        private Continuation preCallback;
        Object result;
        int label;

        public ContinuationImpl(Continuation preCallback) {
            this.preCallback = preCallback;
        }

        @NonNull
        @Override
        public CoroutineContext getContext() {
            return preCallback.getContext();
        }

        @Override
        public void resumeWith(@NonNull Object resumeResult) {
            // 判断恢复后是否需要再次挂起，判断是否调用完成
            Object suspend = invokeSuspend(resumeResult);
            if(suspend == IntrinsicsKt.getCOROUTINE_SUSPENDED()){
                return;
            }
            // 本次执行完全，则回调上一层
            preCallback.resumeWith(suspend);
        }

        abstract Object invokeSuspend(@NotNull Object resumeResult);
    }
}
