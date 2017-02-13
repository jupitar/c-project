package activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import bean.TestBean;
import utils.BaseView;


/**
 * 模块测试界面
 */
public class TestActivity extends AppCompatActivity {
    ViewPager viewPager;
    Button lastPage,nextPage;
    MyPagerAdapter myAdapter;
    List<TestBean> testBeans=new ArrayList<TestBean>();
    List<BaseView> baseViews=new ArrayList<BaseView>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        init();

    }

    private void init() {
        viewPager= (ViewPager) findViewById(R.id.vPager);
        lastPage=(Button)findViewById(R.id.lastPage);
        nextPage=(Button)findViewById(R.id.nextPage);
        setClick();
    }

    private void setClick() {
        lastPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    class MyPagerAdapter extends PagerAdapter{
        @Override
        public int getCount() {
            return testBeans.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //
            BaseView baseView=new BaseView(TestActivity.this,testBeans.get(position));

            container.addView(baseView.getView());
            baseViews.add(baseView);
            return baseViews.get(position).getView();


        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(baseViews.get(position).getView());
        }



    }


}
