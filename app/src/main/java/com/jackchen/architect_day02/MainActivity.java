package com.jackchen.architect_day02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    private String name ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @CheckNet
    public void click(View view){
        Intent intent = new Intent(this , MainActivity.class) ;
        startActivity(intent);
    }
}
