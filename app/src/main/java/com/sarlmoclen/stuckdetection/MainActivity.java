package com.sarlmoclen.stuckdetection;

import android.os.Bundle;
import android.util.Log;
import android.util.Printer;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Printer mLogging;
    private long currentTime = 0;
    private long differenceTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.sleep).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //主线程睡眠3秒，模拟卡顿
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        mLogging = new Printer() {
            @Override
            public void println(String x) {
                //检测打印里面字符，判断">>>>> Dispatching to"和"<<<<< Finished to"执行时间差，下面2秒为我们设置的报警阈值
                if(x.contains(">>>>> Dispatching to")){
                    currentTime = System.currentTimeMillis();
                }
                if(x.contains("<<<<< Finished to")){
                    differenceTime = System.currentTimeMillis() - currentTime;
                }
                Log.i("sarlmoclen", "differenceTime:" + differenceTime);
                if(differenceTime > 2000){
                    Log.e("sarlmoclen", "differenceTime:" + differenceTime + "-stuck detection");
                }
            }
        };
        //给主线程Looper设置自己的mLogging
        getMainLooper().setMessageLogging(mLogging);
    }

}
