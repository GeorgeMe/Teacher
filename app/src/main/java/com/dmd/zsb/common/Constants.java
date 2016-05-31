package com.dmd.zsb.common;

import android.os.Environment;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 */
public class Constants {

    public static String FILEPATH = Environment.getExternalStorageDirectory() + "/zsb/t/";
    //择师宝基础常量
    public static final String ZSBAPPKEY = "o6wmjqsrpmqakbqa0jvtxcmuidqidq";
    public static final String ZSBVERSION= "v1.0";
    public static final String USER_ROLE="teacher";
    public static final String PLATFORM="android";

    //MOB 短信验证key
    public static final String SMSAPPKEY = "f3fc6baa9ac4";

    public static final String SMSAPPSECRET = "7f3dedcb36d92deebcb373af921d635a";

    public static final int EVENT_BEGIN = 0X100;
    public static final int EVENT_REFRESH_DATA = EVENT_BEGIN + 10;
    public static final int EVENT_LOAD_MORE_DATA = EVENT_BEGIN + 20;
    public static final int EVENT_START_PLAY_MUSIC = EVENT_BEGIN + 30;
    public static final int EVENT_STOP_PLAY_MUSIC = EVENT_BEGIN + 40;

    public static final int EVENT_RECOMMEND_COURSES_HOME = 100;
    public static final int EVENT_RECOMMEND_COURSES_SEEK = 101;
    public static final int EVENT_RECOMMEND_COURSES_SIGNIN = 102;

    public static String imageName() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Date date = new Date();
        String time = format.format(date);
        String imageName = "IMG_" + time + ".jpg";
        return imageName;
    }

    public static String videoName() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Date date = new Date();
        String time = format.format(date);
        String imageName = "Video_" + time + ".mp4";
        return imageName;
    }



}
