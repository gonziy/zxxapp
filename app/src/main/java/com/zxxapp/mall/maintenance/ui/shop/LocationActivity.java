package com.zxxapp.mall.maintenance.ui.shop;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.databinding.ActivityShopLocationBinding;
import com.zxxapp.mall.maintenance.utils.StringFormatUtil;
import com.zxxapp.mall.maintenance.utils.ToastUtil;

import java.text.MessageFormat;

//https://blog.csdn.net/jumpingerror/article/details/70172085
public class LocationActivity extends AppCompatActivity {

    private ActivityShopLocationBinding binding;
    private boolean isFirstLocate=true;
    private LatLng shopPosition = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shop_location);
        binding.bmapView.getMap().setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                shopPosition = latLng;

                //清空已有的Marker
                binding.bmapView.getMap().clear();

                BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.ic_shop_marker);
                //构建MarkerOption，用于在地图上添加Marker
                OverlayOptions option = new MarkerOptions().position(latLng).icon(bitmap);
                //在地图上添加Marker，并显示
                binding.bmapView.getMap().addOverlay(option);
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(shopPosition==null){
                    ToastUtil.showToast("请在地图上标识出商铺的坐标。");
                    return ;
                }

                Intent intent = new Intent();
                intent.putExtra("position", MessageFormat.format("{0},{1}", shopPosition.latitude, shopPosition.longitude));
                LocationActivity.this.setResult(RESULT_OK, intent);
                LocationActivity.this.finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        binding.bmapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        binding.bmapView.onResume();

        // 开启定位图层
        binding.bmapView.getMap().setMyLocationEnabled(true);
        // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
        binding.bmapView.getMap().setMyLocationConfiguration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, null));

        //开始定位
        startLocate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        binding.bmapView.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();

        // 当不需要定位图层时关闭定位图层
        binding.bmapView.getMap().setMyLocationEnabled(false);
    }

    private void startLocate(){
        LocationClient locationClient = new LocationClient(getApplicationContext());

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        //option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        //int span = 2000;
        //option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        //option.setOpenGps(true);//可选，默认false,设置是否使用gps
        //option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        //option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        //option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        //option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        //option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        //option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        locationClient.setLocOption(option);

        locationClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                //if(isFirstLocate){
                    LatLng latLng = new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude());
                    MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(latLng);
                    binding.bmapView.getMap().animateMapStatus(update);
//                    update=MapStatusUpdateFactory.zoomTo(16f);
//                    binding.bmapView.getMap().animateMapStatus(update);
                    isFirstLocate = false;
               // }

//                // 构造定位数据
//                MyLocationData locData = new MyLocationData.Builder()
//                        .accuracy(bdLocation.getRadius())
//                        // 此处设置开发者获取到的方向信息，顺时针0-360
//                        .latitude(bdLocation.getLatitude())
//                        .longitude(bdLocation.getLongitude()).build();
//                // 设置定位数据
//                binding.bmapView.getMap().setMyLocationData(locData);
            }
        });

        //开启定位
        locationClient.start();
    }
}
