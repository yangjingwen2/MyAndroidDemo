package com.example.yangjw.baidumapdemo;

import com.baidu.mapapi.map.BaiduMap;

/**
 * Created by yangjw on 2016/3/1.
 */
public class MyPoiOverlay extends PoiOverlay {


    /**
     * 构造函数
     *
     * @param baiduMap 该 PoiOverlay 引用的 BaiduMap 对象
     */
    public MyPoiOverlay(BaiduMap baiduMap) {
        super(baiduMap);
    }

    @Override
    public boolean onPoiClick(int i) {
        return super.onPoiClick(i);
    }
}
