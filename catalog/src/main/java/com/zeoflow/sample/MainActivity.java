package com.zeoflow.sample;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.zeoflow.app.Activity;
import com.zeoflow.storage.memo.Memo;

public class MainActivity extends Activity
{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        memoExample();
    }

    private void memoExample()
    {
        timeMemoInit();
        timeMemoPut();
        timeMemoGet();
        timeMemoContains();
        timeMemoCount();
        timeMemoDelete();
        timeMemoEncrypt();
        timeMemoDecrypt();
    }

    private void timeMemoInit() {
        long startTime = System.currentTimeMillis();

        Memo.init(this).build();

        long endTime = System.currentTimeMillis();
        System.out.println("Memo.init: " + (endTime - startTime) + "ms");
    }

    private void timeMemoPut() {
        long startTime = System.currentTimeMillis();

        Memo.put("key", "value");

        long endTime = System.currentTimeMillis();
        System.out.println("Memo.put: " + (endTime - startTime) + "ms");
    }

    private void timeMemoGet() {
        long startTime = System.currentTimeMillis();

        Memo.get("key");

        long endTime = System.currentTimeMillis();
        System.out.println("Memo.get: " + (endTime - startTime) + "ms");
    }

    private void timeMemoCount() {
        long startTime = System.currentTimeMillis();

        Memo.count();

        long endTime = System.currentTimeMillis();
        System.out.println("Memo.count: " + (endTime - startTime) + "ms");
    }

    private void timeMemoContains() {
        long startTime = System.currentTimeMillis();

        Memo.contains("key");

        long endTime = System.currentTimeMillis();
        System.out.println("Memo.count: " + (endTime - startTime) + "ms");
    }

    private void timeMemoDelete() {
        long startTime = System.currentTimeMillis();

        Memo.delete("key");

        long endTime = System.currentTimeMillis();
        System.out.println("Memo.count: " + (endTime - startTime) + "ms");
    }

    private void timeMemoEncrypt() {
        long startTime = System.currentTimeMillis();

        System.out.println("e: " + Memo.encrypt(42335));

        long endTime = System.currentTimeMillis();
        System.out.println("Memo.encrypt: " + (endTime - startTime) + "ms");
    }

    private void timeMemoDecrypt() {
        long startTime = System.currentTimeMillis();

        System.out.println("v: " + Memo.decrypt(Memo.encrypt(42335)));

        long endTime = System.currentTimeMillis();
        System.out.println("Memo.decrypt: " + (endTime - startTime) + "ms");
    }

}
