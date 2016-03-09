package com.example.yangjw.fragmenttabdemo;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private RadioGroup mRadioGroup;
    private List<Fragment> fragmentList = new ArrayList<>();
    private FragmentManager mSupportFragmentManager;

    private int lastIndex;
    private Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initFragment();

    }

    private void initFragment() {
        mSupportFragmentManager = getSupportFragmentManager();
        fragmentList.add(HomeFragment.newInstance(null, null));
        fragmentList.add(DiscorverFragment.newInstance(null, null));
        fragmentList.add(CategoryFragment.newInstance(null, null));
        setupFragment(0, false);
    }

    private void setupFragment(int index, boolean isHide) {
        FragmentTransaction fragmentTransaction = mSupportFragmentManager.beginTransaction();
        Fragment fragment = fragmentList.get(index);
        if (isHide) {
            Fragment lastFragment = fragmentList.get(lastIndex);
            fragmentTransaction.hide(lastFragment);
        }


        if (fragment.isAdded()) {
            fragmentTransaction.show(fragment);
        } else {
            fragmentTransaction.add(R.id.home_tab_content, fragment);
        }


        fragmentTransaction.commit();
        lastIndex = index;
    }

    private void initView() {
        mToolBar = (Toolbar) findViewById(R.id.home_tool_bar);
        setSupportActionBar(mToolBar);
        mRadioGroup = (RadioGroup) findViewById(R.id.home_tab_radiogroup);
        ((RadioButton) mRadioGroup.getChildAt(0)).setChecked(true);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.home_tab_home:
                        setupFragment(0, true);
                        break;
                    case R.id.home_tab_discover:
                        setupFragment(1, true);
                        break;
                    case R.id.home_tab_category:
                        setupFragment(2, true);
                        break;
                }

            }
        });

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
