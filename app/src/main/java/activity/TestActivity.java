package activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import application.MyApplication;
import bean.TestBean;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import utils.BaseView;

import static android.view.View.inflate;
import static utils.URLUtils.GET_EXAMSERVLET;
import static utils.URLUtils.INSERT_EXAMSERVLET;


/**
 * 模块测试界面
 */
public class TestActivity extends AppCompatActivity {
    private MyApplication app;
    ViewPager viewPager;
    Button lastPage, nextPage,submit;
    ImageView back_img;
    TextView content;
    MyPagerAdapter myAdapter;
    List<TestBean> testBeans = new ArrayList<TestBean>();
    Handler handler;
    String userName;
    FrameLayout fm;

    View submit_v,bottom_button;
    boolean isLast=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        init();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                testBeans = (List<TestBean>) (msg.obj);
                myAdapter.setDatas(testBeans);

            }
        };


    }

    private void init() {
        //获取用户用户名
        userName= getIntent().getStringExtra("userName");
        //获得我们的应用程序MyApplication
        app= (MyApplication) getApplication();

        fm= (FrameLayout) findViewById(R.id.fm);

        submit_v= inflate(this,R.layout.submit,null);
        bottom_button=View.inflate(this,R.layout.bottom_button,null);
        lastPage = (Button) bottom_button.findViewById(R.id.lastPage);
        nextPage = (Button) bottom_button.findViewById(R.id.nextPage);
        fm.addView(bottom_button);
        submit= (Button) submit_v.findViewById(R.id.test_submit);
        viewPager = (ViewPager) findViewById(R.id.vPager);
      back_img=(ImageView) findViewById(R.id.login_img);
        content= (TextView) findViewById(R.id.mian_context);
        content.setText("试题测试界面");
        myAdapter = new MyPagerAdapter(this);
        viewPager.setAdapter(myAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {


              if(position==testBeans.size()-1){
                    //将底部布局换成提交按钮
                    fm.removeAllViews();
                    fm.addView(submit_v);
                    isLast=true;

                }

                if(position<testBeans.size() - 1&&isLast){
                    isLast=false;
                    //v
                    fm.removeAllViews();
                    fm.addView(bottom_button);
                }


                viewPager.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
       //顶部返回按钮点击事件

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();


            }
        });
        setClick();
        //获取数据
        getData();
    }

    public void getData() {
        postConnection(GET_EXAMSERVLET,0,null);
    }

    private void setClick() {

        lastPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = viewPager.getCurrentItem();
                if (i > 0) {
                    i--;
                    viewPager.setCurrentItem(i);

                } else {
                    Toast.makeText(TestActivity.this, "当前已经是第一题了!", Toast.LENGTH_SHORT).show();
                }


            }
        });
        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = viewPager.getCurrentItem();
                if (i < testBeans.size() - 1) {
                    i++;
                    viewPager.setCurrentItem(i);

                } else {
                    Toast.makeText(TestActivity.this, "当前已经是最后一题了!", Toast.LENGTH_SHORT).show();
                }


            }
        });
        //提交试题
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //判断用户试题是否做完
               boolean  flag=isFinished();
                if(!flag){
                    new AlertDialog.Builder(TestActivity.this).setTitle("系统提示").setMessage("您还有试题未完成，你确定提交吗？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                      //将用户数据爆粗到数据库


                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return ;

                        }
                    }).show();

                }
                Gson gson=new Gson();
                String json=gson.toJson(testBeans);
                postConnection(INSERT_EXAMSERVLET,1,json);

                Intent intent=new Intent(TestActivity.this,SubmitActivity.class);
                app.datas.put("applicationBean",testBeans);
                startActivity(intent);
                finish();


            }
        });


    }

    private boolean isFinished() {
        boolean flag=false;
        for(TestBean testBean:testBeans) {
            if (testBean.getU_choice() == null){

                return flag;
            }

        }
        flag=true;
        return flag;


    }

    //弹出对话框
    public void openDialog(){
        new AlertDialog.Builder(TestActivity.this).setTitle("系统提示").setMessage("您确定退出当前测试吗？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();

            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();

    }

    public void postConnection(String url,final int flag,final String json) {//post提交数据请求,获取批量视频
        //userName = "wp2";
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody requestBodyPost=null;
        if(flag==1)
            requestBodyPost = new FormBody.Builder().add("userName",userName).add("json", json).build();
        else{
            requestBodyPost = new FormBody.Builder().add("userName",userName).build();
        }
        Request requestPost = new Request.Builder().url(url).post(requestBodyPost).build();
        mOkHttpClient.newCall(requestPost).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (json==null) {
                    String result = response.body().string();
                    Gson gson = new Gson();

                    testBeans = gson.fromJson(result, new TypeToken<List<TestBean>>() {
                    }.getType());
                    Message msg = new Message();
                    msg.obj = testBeans;
                    handler.sendMessage(msg);
                }

                if(flag==1){
                    String result = response.body().string();

                    Looper.prepare();
                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                    Looper.loop();


                }



            }
        });


    }





    class MyPagerAdapter extends PagerAdapter {
        Context context;
        List<BaseView> baseViews = new ArrayList<BaseView>();

        public MyPagerAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return baseViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //


            container.addView(baseViews.get(position).getView());

            return baseViews.get(position).getView();


        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(baseViews.get(position).getView());
        }

        public void setDatas(List<TestBean> testBeens) {
            BaseView baseView;

            for (int i = 0; i < testBeens.size(); i++) {
                baseView = new BaseView(context, testBeens.get(i), false);
                baseViews.add(baseView);
            }
            myAdapter.notifyDataSetChanged();

        }


    }

    //重写返回键，关闭当前Activity
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            openDialog();

        }

        return true;
    }


}
