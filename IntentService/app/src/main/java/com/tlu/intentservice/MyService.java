package com.tlu.intentservice;



import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class MyService extends Service {

    MediaPlayer mymedia;



    //Gọi hàm OnCreate để tạo đối tượng mà Service quản lý
    @Override
    public void onCreate() {
        super.onCreate();
        mymedia =
                MediaPlayer.create(MyService.this, R.raw.demomusic
                );
        mymedia.setLooping(true);   //Cho phép lặp lại liên
    }

    //Gọi Hàm onStartCommand để khởi chạy đối tượng mà
    @Override
    public int onStartCommand(Intent intent, int flags, int
            startId) {
        if (mymedia.isPlaying())
            mymedia.pause();
        else
            mymedia.start();
        return super.onStartCommand(intent, flags, startId);
    }

    //Gọi Hàm onDestroy để dừng đối tượng mà Service quản lý
    @Override
    public void onDestroy() {
        super.onDestroy();
        mymedia.stop();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}