package com.zxxapp.mall.maintenance.ui.shopping;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.databinding.ActivityLocationBinding;
import com.zxxapp.mall.maintenance.databinding.ActivityLoginBinding;
import com.zxxapp.mall.maintenance.ui.mine.child.LoginActivity;
import com.zxxapp.mall.maintenance.utils.ToastUtil;
import com.zxxapp.mall.maintenance.view.paymentradio.PayRadioButton;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;


public class LocationActivity extends AppCompatActivity  implements SensorEventListener {
    private ActivityLocationBinding binding;

    // 定位相关
    private static final double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
    LocationClient mLocClient;
    private LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker;
    private static final int accuracyCircleFillColor = 0xAAFFFF88;
    private static final int accuracyCircleStrokeColor = 0xAA00FF00;
    private SensorManager mSensorManager;
    private Double lastX = 0.0;
    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;

    private String mCurrentAddress = "";
    private float mCurrentAccracy;

    MapView mMapView;
    BaiduMap mBaiduMap;

    // UI相关
    boolean isFirstLoc = true; // 是否首次定位
    private MyLocationData locData;
    private float direction;


    private static final int BAIDU_READ_PHONE_STATE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_location);
        mBaiduMap = binding.baiduMap.getMap();
        initLocation();
        List<String> permissionList = new ArrayList<>();
        if(ContextCompat.checkSelfPermission(LocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_PHONE_STATE)!=PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if(!permissionList.isEmpty()){
            String[] permissions= permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(LocationActivity.this,permissions,1);
        }else{
            requestLocation();
        }
        binding.btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                Bundle data = new Bundle();
                data.putDouble("lat",mCurrentLat);
                data.putDouble("lng",mCurrentLon);
                data.putString("addr",mCurrentAddress);
                intent.putExtras(data);
                // 设置该SelectCityActivity结果码，并设置结束之后退回的Activity
                LocationActivity.this.setResult(0, intent);
                // 结束SelectCityActivity
                LocationActivity.this.finish();
            }
        });
        binding.btnReLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLocClient.registerLocationListener(new BDLocationListener() {
                    @Override
                    public void onReceiveLocation(BDLocation bdLocation) {
                        StringBuilder currentPosition = new StringBuilder();
                        currentPosition.append("维度：").append(bdLocation.getLatitude()).append("\n");
                        currentPosition.append("经度：").append(bdLocation.getLongitude()).append("\n");
                        currentPosition.append("定位方式：");
                        Log.e("tag", "当前的定位方式=" + bdLocation.getLocType());

                        if (bdLocation.getLocType() == BDLocation.TypeGpsLocation) {
                            currentPosition.append("GPS");
                        } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
                            currentPosition.append("网络");
                        }
                        Log.e("bdmap",currentPosition.toString());
                        mCurrentLat = bdLocation.getLatitude()+ 0.006;
                        mCurrentLon = bdLocation.getLongitude() + 0.0065;

                        mCurrentAddress = bdLocation.getAddrStr();
                        LatLng point = new LatLng(mCurrentLat, mCurrentLon);
                        BitmapDescriptor bitmap = BitmapDescriptorFactory
                                .fromResource(R.mipmap.icon_gcoding);
                        OverlayOptions option = new MarkerOptions()
                                .position(point)
                                .icon(bitmap);
                        mBaiduMap.addOverlay(option);
                        LatLng cenpt = new LatLng(mCurrentLat,mCurrentLon);
                        MapStatus mMapStatus = new MapStatus.Builder()
                                .target(cenpt)
                                .zoom(15)
                                .build();

                        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                        //改变地图状态
                        mBaiduMap.setMapStatus(mMapStatusUpdate);
                    }
                });
                mLocClient.restart();
            }
        });
    }

    private void initLocation() {

        mLocClient = new LocationClient(getApplicationContext());
        LocationClientOption option = new LocationClientOption();

        option.setIsNeedAddress(true);

        mLocClient.setLocOption(option);
        mLocClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                StringBuilder currentPosition = new StringBuilder();
                currentPosition.append("维度：").append(bdLocation.getLatitude()).append("\n");
                currentPosition.append("经度：").append(bdLocation.getLongitude()).append("\n");
                currentPosition.append("定位方式：");
                Log.e("tag", "当前的定位方式=" + bdLocation.getLocType());

                if (bdLocation.getLocType() == BDLocation.TypeGpsLocation) {
                    currentPosition.append("GPS");
                } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
                    currentPosition.append("网络");
                }
                Log.e("bdmap",currentPosition.toString());
                mCurrentLat = bdLocation.getLatitude()+ 0.006;
                mCurrentLon = bdLocation.getLongitude() + 0.0065;

                mCurrentAddress = bdLocation.getAddrStr();
                LatLng point = new LatLng(mCurrentLat, mCurrentLon);
                BitmapDescriptor bitmap = BitmapDescriptorFactory
                        .fromResource(R.mipmap.icon_gcoding);
                OverlayOptions option = new MarkerOptions()
                        .position(point)
                        .icon(bitmap);
                mBaiduMap.addOverlay(option);
                LatLng cenpt = new LatLng(mCurrentLat,mCurrentLon);
                MapStatus mMapStatus = new MapStatus.Builder()
                        .target(cenpt)
                        .zoom(15)
                        .build();

                MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                //改变地图状态
                mBaiduMap.setMapStatus(mMapStatusUpdate);
            }
        });
        mLocClient.start();
    }

    private void requestLocation() {
        mLocClient.restart();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0){
                    for (int result: grantResults
                            ) {
                        if(result !=PackageManager.PERMISSION_GRANTED){
                            ToastUtil.showToast("必须同意所有的权限才能使用本程序");
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                }else{
                    ToastUtil.showToast("发生了错误");
                }
                break;
        }
    }


    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, LocationActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode==KeyEvent.KEYCODE_BACK){
            ToastUtil.showToast("您必须选择定位地址");
            return true;//不执行父类点击事件
        }
        return super.onKeyDown(keyCode, event);//继续执行父类其他点击事件

    }
}
