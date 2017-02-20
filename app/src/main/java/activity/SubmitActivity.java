package activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import application.MyApplication;
import bean.TestBean;
import utils.BaseView;

/**
 * 试题信息提交成功ctivity,在此页面可以看到自己的错题
 */
public class SubmitActivity extends AppCompatActivity {
    private MyApplication app;
    ImageView img;
    TextView content;
    ViewPager viewPager;
    FrameLayout fm;
    View bottom_button;
    Button lastPage, nextPage;
    MyPagerAdapter myAdapter;

    List<TestBean> testBeans = new ArrayList<TestBean>();

    Handler handler;
    String userName;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        initVew();

    }

    private void initVew() {
        fm= (FrameLayout) findViewById(R.id.fm);
        linearLayout= (LinearLayout) findViewById(R.id.test_activity);
        linearLayout.setVisibility(View.VISIBLE);
        bottom_button=View.inflate(this, R.layout.bottom_button,null);
        lastPage = (Button) bottom_button.findViewById(R.id.lastPage);
        nextPage = (Button) bottom_button.findViewById(R.id.nextPage);
        fm.addView(bottom_button);
        app= (MyApplication) getApplication();
        viewPager = (ViewPager) findViewById(R.id.vPager);
        testBeans=(List<TestBean>) app.datas.get("applicationBean");
        img= (ImageView) findViewById(R.id.login_img);
        content= (TextView) findViewById(R.id.mian_context);
        content.setText("试题信息显示");
        myAdapter = new MyPagerAdapter(this);
        myAdapter.setDatas(testBeans);
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


        setClick();
    }

    private void setClick() {


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();



            }
        });
        lastPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = viewPager.getCurrentItem();
                if (i > 0) {
                    i--;
                    viewPager.setCurrentItem(i);

                } else {
                    Toast.makeText(SubmitActivity.this, "当前已经是第一题了!", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(SubmitActivity.this, "当前已经是最后一题了!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    //弹出对话框

    private void openDialog() {
        new AlertDialog.Builder(SubmitActivity.this).setTitle("系统提示").setMessage("您确定退出当前错题查看吗？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
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
                baseView = new BaseView(context, testBeens.get(i), true);
                baseViews.add(baseView);
            }
            myAdapter.notifyDataSetChanged();

        }


    }


    //重写返回键


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK){
            openDialog();
        }
        return true;

    }
}
