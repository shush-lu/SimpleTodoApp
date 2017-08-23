package com.shusheng.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * @author Shusheng Lu
 */

public class SplashActivity extends AppCompatActivity {

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Thread newThread = new Thread() {
      @Override
      public void run() {
        try {
          sleep(2000);
          Intent intent = new Intent(SplashActivity.this, MainActivity.class);
          startActivity(intent);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    };
    newThread.start();
  }
}
