package com.example.yangjw.a1504demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.yangjw.a1504demo.bean.ProductInfo;
import com.example.yangjw.a1504demo.http.HttpUtils;
import com.example.yangjw.a1504demo.http.RequestCallBack;
import com.example.yangjw.imageloaderlibrary.ImageLoader;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private List<ProductInfo.DataEntity.ProductsEntity> productList = new ArrayList<>();
    //    private PullToRefreshGridView mGridView;
    private GridView mGridView;
    private String url = "http://apicn.seashellmall.com/product/list/?size=20&p=1";
    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGridView = (GridView) findViewById(R.id.gridview);
//        GridView gridView = mGridView.getRefreshableView();
//        gridView.setNumColumns(2);
        productAdapter = new ProductAdapter();
        mGridView.setAdapter(productAdapter);

        //请求网络数据
        requestData();
    }

    private void requestData() {
        HttpUtils.get(url, new RequestCallBack() {
            @Override
            public void onSuccess(String result, int requestCode) {

                Log.d(MainActivity.class.toString(), "--->" + result);
                Gson gson = new Gson();
                ProductInfo productInfo = gson.fromJson(result, ProductInfo.class);

                productList.addAll(productInfo.getData().getProducts());
                productAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(String exception) {

            }

            @Override
            public void onError(IOException ex) {

            }
        }, 0);
    }

    class ProductAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return productList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            ViewHolder viewHolder = null;
            if (view == null) {
                view = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_layout, null);
//                image.setScaleType(ImageView.ScaleType.FIT_XY);
                viewHolder = new ViewHolder();
                viewHolder.imageView = (ImageView) view.findViewById(R.id.item_image);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            String url = productList.get(position).getImages().get(0).getUrl();
//            ImageUtils.loadImage(viewHolder.imageView,url);
            ImageLoader.loadImage(MainActivity.this, url, viewHolder.imageView);
            return view;
        }

        class ViewHolder {
            public ImageView imageView;
        }
    }
}
