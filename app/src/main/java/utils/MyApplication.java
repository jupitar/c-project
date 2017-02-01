package utils;

import android.app.Application;

/**
 * Created by Administrator on 2017/1/8.
 */

public class MyApplication extends Application {

    boolean isChoosed=false;
    public static int My_count=0;
    @Override
    public void onCreate() {
        super.onCreate();
        isChoosed=true;
        My_count=2+My_count;


    }
}
