package application;

import android.app.Application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.TestBean;

/**
 * Created by Administrator on 2017/2/19.
 */

public class MyApplication extends Application {
    public Map<String,List<TestBean>> datas=new HashMap<String,List<TestBean>>();
    //传递用户选择试题信息

}
