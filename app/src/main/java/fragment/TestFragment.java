package fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import activity.R;

/**
 * Created by Administrator on 2017/1/8.
 */
//在线测试fragment

public class TestFragment extends Fragment {

    public TestFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.test_fragment, container, false);
    }
}
