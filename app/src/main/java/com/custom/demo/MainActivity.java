package com.custom.demo;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.custom.demo.fragment.CommonFragment;
import com.custom.demo.fragment.FirewormsViewFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager viewPager= (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), Arrays.asList(items)));
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    private Item[] items={
            new Item(R.layout.fragment_common, CommonFragment.class,
                    "环形倒计时进度条",
                    "https://github.com/SuperKotlin/CountDownView"),
            new Item(R.layout.fragment_fireworms_view,FirewormsViewFragment.class,
                    "萤火飞舞 粒子运动",
                    "https://github.com/JadynAi/Particle")
    };
}
