package com.zeoflow.sample;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.zeoflow.app.Activity;

public class MainActivity extends Activity
{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logger("hello world 1");
        logger("hello world 2");
        logger("hello world 3");
        logger("hello world 4");
        logger("hello world 5");
    }

}
