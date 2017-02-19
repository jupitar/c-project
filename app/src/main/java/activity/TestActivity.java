package activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bean.TestBean;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import utils.BaseView;

import static utils.URLUtils.ERROR_EXAMSERVLET;


/**
 * 模块测试界面
 */
public class TestActivity extends AppCompatActivity {
    ViewPager viewPager;
    Button lastPage, nextPage,submit;
    ImageView back_img;
    TextView content;
    MyPagerAdapter myAdapter;
    List<TestBean> testBeans = new ArrayList<TestBean>();
    Handler handler;
    String userName;
    FrameLayout fm;
    RelativeLayout relativeLayout;
    View v;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        init();
        //userName= getIntent().getStringExtra("userName");
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                testBeans = (List<TestBean>) (msg.obj);
                myAdapter.setDatas(testBeans);

            }
        };


    }

    private void init() {
        fm= (FrameLayout) findViewById(R.id.fm);
        relativeLayout= (RelativeLayout) findViewById(R.id.change_page);
        v=View.inflate(this,R.layout.submit,null);
        submit= (Button) viewPager.findViewById(R.id.submit);
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
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        lastPage = (Button) findViewById(R.id.lastPage);
        nextPage = (Button) findViewById(R.id.nextPage);//顶部返回按钮点击事件

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
        postConnection();
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
                if(i==testBeans.size()-1){
                    //将底部布局换成提交按钮
                    Button button=new Button(TestActivity.this);
                    button.setText("提交");

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
                            finish();
                            Intent intent=new Intent(TestActivity.this,SubmitActivity.class);
                           //intent.putExtra("testBean",testBeans);
                            startActivity(intent);

                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();

                }


            }
        });


    }

    private boolean isFinished() {
        boolean flag=false;
        for(TestBean testBean:testBeans) {
            if (testBean.getU_choice() == null)
                return flag;
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

    public void postConnection() {//post提交数据请求
        userName = "wp2";
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody requestBodyPost = new FormBody.Builder().add("userName", userName).build();
        Request requestPost = new Request.Builder().url(ERROR_EXAMSERVLET).post(requestBodyPost).build();
        mOkHttpClient.newCall(requestPost).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Gson gson = new Gson();

                testBeans = gson.fromJson(result, new TypeToken<List<TestBean>>() {
                }.getType());
                Message msg = new Message();
                msg.obj = testBeans;
                handler.sendMessage(msg);


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
