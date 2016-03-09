package com.example.yangjw.planedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PlaneView planeView = new PlaneView(this);
        setContentView(planeView);
//        setContentView(R.layout.activity_main);
    }
}
