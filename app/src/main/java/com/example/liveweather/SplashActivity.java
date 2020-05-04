//package com.example.liveweather;
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import androidx.core.app.ActivityCompat;
//
//public class SplashActivity extends Activity implements Runnable {
//
//    Thread mThread;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_splash2);
//        mThread = new Thread(this);
//
//        mThread.start();
//    }
//
//    @Override
//    public void run(){
//        try {
//            Thread.sleep(8000);
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//        finally {
//            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//
//            finish();
//        }
//    }
//}
//
