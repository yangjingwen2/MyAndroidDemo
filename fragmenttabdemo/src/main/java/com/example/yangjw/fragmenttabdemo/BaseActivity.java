package com.example.yangjw.fragmenttabdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by yangjw on 2016/3/6.
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseFragment.OnFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
