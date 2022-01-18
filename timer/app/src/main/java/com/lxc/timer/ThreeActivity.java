package com.lxc.timer;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lxc.base.thread.LifeStamp;
import com.lxc.base.thread.Performance;
import com.zhangyue.we.x2c.ano.Xml;

/**
 *
 * @author lxc
 * @date 1/10/22 1:50 PM
 * Desc:
 */
@Xml(layouts = "activity_main")
public class ThreeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Performance.INSTANCE.startLifecycle(new LifeStamp("setContentViewTag",System.currentTimeMillis(),null));
        setContentView(R.layout.activity_main);
        Performance.INSTANCE.stopLifecycle(new LifeStamp("setContentViewTag",System.currentTimeMillis(),null),10);
    }
}
