package com.jy.medical.util;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

public class GetLocation {

	private static String address="";
	private static String city="";
	private static String province="";
	public static LocationClient mLocationClient = null;
	public  interface LocationCallBack{
		void getLocationSuccess(BDLocation bdLocation,String address, String province, String city);
		void getLocationFailed();
	}
	public static void getLoc(Context context,final LocationCallBack locationCallBack) {

			mLocationClient = new LocationClient(context); // 声明LocationClient类
			mLocationClient.registerLocationListener(new BDLocationListener() {
				@Override
				public void onReceiveLocation(BDLocation bdLocation) {
					if(bdLocation.getLocType() == BDLocation.TypeGpsLocation||bdLocation.getLocType() == BDLocation.TypeNetWorkLocation||bdLocation.getLocType() == BDLocation.TypeOffLineLocation)
					{
						address= bdLocation.getAddrStr();
						province=bdLocation.getProvince();
						city=bdLocation.getCity();
						locationCallBack.getLocationSuccess(bdLocation,address,province,city);
//						mLocationClient.stop();
					}else{
						locationCallBack.getLocationFailed();
//						mLocationClient.stop();
					}

				}
			}); // 注册监听函数
			initLocation();
			mLocationClient.start();

	}
	// 初始化定位数据
	private static void initLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
		int span = 0;
		option.setLocationMode(LocationMode.Hight_Accuracy);
		option.setAddrType("all");
		option.setScanSpan(span);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(true);// 可选，默认false,设置是否使用gps
		option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
		option.setIsNeedLocationDescribe(true);// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
		option.setIsNeedLocationPoiList(false);// 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
		option.setIgnoreKillProcess(false);// 可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
		option.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
		option.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
		mLocationClient.setLocOption(option);
	}

}
