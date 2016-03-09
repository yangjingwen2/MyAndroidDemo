package com.example.yangjw.eventdispatchdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Fragment> fragments = new ArrayList<>();
    private MyListView listView;
    private List<String> items = new ArrayList<String>();
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        viewPager = (ViewPager) findViewById(R.id.viewpager);

//        fragments.add(Blank1Fragment.newInstance());
//        fragments.add(Blank2Fragment.newInstance());
//
//        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        setItems();
        listView = (MyListView) findViewById(R.id.list);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items));
    }

    private void setItems() {
        items.add("11111111111");
        items.add("11111111111");
        items.add("bbbbbbbbbbb");
        items.add("11111111111");
        items.add("11111111111");
        items.add("aaaaaaaaaaa");
        items.add("11111111111");
        items.add("11111111111");
        items.add("11111111111");
        items.add("11111111111");
        items.add("22222222222");
        items.add("11111111111");
        items.add("11111111111");
        items.add("11111111111");
        items.add("11111111111");
        items.add("44444444444");
        items.add("11111111111");
        items.add("11111111111");
        items.add("11111111111");
        items.add("11111111111");
        items.add("11111111111");
        items.add("11111111111");
        items.add("11111111111");
    }

    /**
     * 事件消费
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("Activity", ">>>>onTouchEvent=" + +event.getAction());
        return true;
    }

    /**
     * 事件分发
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d("Activity", ">>>>dispatchTouchEvent---" + ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    class MyViewPagerAdapter extends FragmentPagerAdapter {

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }


}
