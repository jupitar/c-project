package application;

import android.app.Application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.TestBean;

/**
 * Created by Administrator on 2017/2/19.
 * 使用Application，主要是重写onCreate()方法
 *
 */

public class MyApplication extends Application {

    public Map<String,List<TestBean>> datas=null;


    @Override
    public void onCreate() {
        datas=new HashMap<String,List<TestBean>>();
        super.onCreate();
    }
}
