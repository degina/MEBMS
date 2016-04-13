package com.example.mebms.mebms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img=(ImageView)findViewById(R.id.loadScreen);
        Thread screen=new Thread(){
            public void run(){
                try{
                    sleep(3000);
                    Intent i = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(i);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
                finally{
                    finish();
                }
            }
        };
        screen.start();
    }
}
