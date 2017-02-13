package fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import activity.ErrorTestActivity;
import activity.TestActivity;
import activity.R;


/**
 * Created by Administrator on 2017/1/8.
 */
//在线测试fragment

public class TestFragment extends Fragment {
    Button modern_test,look_error;


    public TestFragment(){

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View v=  LayoutInflater.from(getActivity()).inflate(R.layout.test_fragment, null, false);
        init(v);

        return v;
    }

    private void init(View v) {

        modern_test= (Button) v.findViewById(R.id.modern_test);
        look_error= (Button) v.findViewById(R.id.look_error);
        setClick();

    }

    private void setClick() {


        //模块测试

        modern_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(), TestActivity.class);
                startActivity(intent);

            }
        });

        //查看错题
        look_error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(), ErrorTestActivity.class);
                startActivity(intent);
            }
        });


    }

}
