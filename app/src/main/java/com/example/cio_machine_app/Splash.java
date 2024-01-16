package com.example.cio_machine_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends AppCompatActivity {
    websocket_client w_c =new websocket_client();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(Splash.this,MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        },3000);

        Thread thread = new Thread(){
            public  void run(){
                try{
//                    sleep(4000);
                    w_c.connect(Splash.this);
                }catch (Exception e) {
                    e.printStackTrace();
                }finally {

                }
            }
        };
        thread.start();
    }

    public void main_activity(){
        Intent intent = new Intent(Splash.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void error_activity(){
        Static_class.error_no= R.string.ini_connx_error;
        Intent i = new Intent(this,Error_Activity.class);
        startActivity(i);
        finish();

    }


}