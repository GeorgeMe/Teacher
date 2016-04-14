package com.dmd.tutor.lbs;

import android.content.Context;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.dmd.tutor.R;
import com.dmd.tutor.utils.XmlDB;

public  class LocationManager implements BDLocationListener
{
    public LocationClient mLocationClient = null;
    private static LocationManager Instance;
    private static double          latitude = 0; //经度
    private static double          longitude = 0; //纬度

    public static Context context;


    public LocationManager(Context context)
    {
        LocationManager.context = context;
        latitude = XmlDB.getInstance(context).getKeyFloatValue("latitude", 0);
        longitude = XmlDB.getInstance(context).getKeyFloatValue("longitude", 0);

        Instance = this;
        mLocationClient = new LocationClient(context);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置定位模式
        option.setCoorType("bd09ll");//返回的定位结果是百度经纬度，默认值gcj02
        int span=1000;
        option.setScanSpan(span*60);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//返回的定位结果包含地址信息
        option.setNeedDeviceDirect(false);//返回的定位结果包含手机机头的方向
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要

        mLocationClient.setLocOption(option);
        mLocationClient.registerLocationListener(this);
        mLocationClient.start();

    }

    public static LocationManager getInstance()
    {
        return Instance;
    }

    public void refreshLocation()
    {
       // mLocationClient.requestOfflineLocation();离线定位
        mLocationClient.requestLocation();
    }

    public BDLocation getLocation()
    {
        return mLocationClient.getLastKnownLocation();
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation)
    {
        if (bdLocation == null)
            return ;
        StringBuffer sb = new StringBuffer(256);
        sb.append("time : ");
        sb.append(bdLocation.getTime());
        sb.append("\nerror code : ");
        sb.append(bdLocation.getLocType());
        sb.append("\nlatitude : ");
        sb.append(bdLocation.getLatitude());
        sb.append("\nlontitude : ");
        sb.append(bdLocation.getLongitude());
        sb.append("\nradius : ");
        sb.append(bdLocation.getRadius());
        if (bdLocation.getLocType() == BDLocation.TypeGpsLocation){
            sb.append("\ngetCityCode : ");
            sb.append(bdLocation.getCityCode());
            sb.append("\nspeed : ");
            sb.append(bdLocation.getSpeed());
            sb.append("\nsatellite : ");
            sb.append(bdLocation.getSatelliteNumber());
            String loc=(bdLocation.getCity()==null? "" : bdLocation.getCity() )+(bdLocation.getDistrict()==null? "" : bdLocation.getDistrict());
            XmlDB.getInstance(context).saveKey("BDLocation",loc.trim().length()==0? "定位失败":loc);
            XmlDB.getInstance(context).saveKey("addr",bdLocation.getAddrStr());
        } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation){
            String loc=(bdLocation.getCity()==null? "" : bdLocation.getCity() )+(bdLocation.getDistrict()==null? "" : bdLocation.getDistrict());
            XmlDB.getInstance(context).saveKey("BDLocation",loc.trim().length()==0? "定位失败":loc);
            sb.append("\ngetCityCode : ");
            sb.append(bdLocation.getCityCode());
            sb.append("\ngetCity : ");
            sb.append(bdLocation.getCity());
            sb.append("\ngetDistrict : ");
            sb.append(bdLocation.getDistrict());
            sb.append("\naddr : ");
            sb.append(bdLocation.getAddrStr());
            XmlDB.getInstance(context).saveKey("addr",bdLocation.getAddrStr());

        }

        latitude = bdLocation.getLatitude();
        longitude = bdLocation.getLongitude();
        
        if (latitude > 1 && longitude > 1)
        {
            XmlDB.getInstance(context).saveKey("latitude", (float) latitude);
            XmlDB.getInstance(context).saveKey("longitude", (float) longitude);
        }
        else
        {
            if (mLocationClient.isStarted())
            {

                int result =mLocationClient.requestOfflineLocation();
                Log.d("LocSDK3", "result:" + result);

            }
            else
            {
               Log.d("LocSDK3", "locClient is null or not started");
            }

        }
        Log.e("location", sb.toString());
    }

    
    public static String getLocation(double lat, double lon) {
        String loc = null;
        double s = gps2m(getLatitude(), getLongitude(), lat, lon);
        if(s > 1000) {
            s = s / 1000.0;
            s = Math.ceil(s * 100+.5)/100;
            loc = s + context.getString(R.string.kilometre);
        } else {
            s = Math.ceil(s * 100+.5)/100;
            loc = s + context.getString(R.string.meter);
        }
        return loc;
    }
    
    public static final double EARTH_RADIUS = 6378137.0;
    public static double gps2m(double lat_a, double lng_a, double lat_b, double lng_b) {
        double radLat1 = (lat_a * Math.PI / 180.0);
        double radLat2 = (lat_b * Math.PI / 180.0);
        double a = radLat1 - radLat2;
        double b = (lng_a - lng_b) * Math.PI / 180.0;
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

    public void destory()
    {
        mLocationClient.stop();
    }
    
    public static double getLatitude() {
    latitude = XmlDB.getInstance(context).getKeyFloatValue("latitude", 0);
    return latitude;
    } 
    public static double getLongitude() {
    longitude =XmlDB.getInstance(context).getKeyFloatValue("longitude", 0);
    return longitude;
    } 
    
}
