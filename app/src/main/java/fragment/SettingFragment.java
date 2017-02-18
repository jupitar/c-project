package fragment;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import activity.AboutActivity;
import activity.ChangePswActivity;
import activity.GettingAdviceActivity;
import activity.R;

/**
 * Created by Administrator on 2017/1/8.
 */
//设置fragment

public class SettingFragment extends Fragment {
    View set_fragment;
    ListView set_list;
    BasicAdapter baseAdapter;
    List<String> datas=new ArrayList<String>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        set_fragment = inflater.inflate(R.layout.settings_fragment, container, false);
        init(set_fragment);
        return set_fragment;
    }

    public void init(View v) {
        set_list = (ListView) v.findViewById(R.id.set_list);
        datas.add("修改密码");
        datas.add("意见反馈");
        datas.add("版本更新");
        datas.add("关于软件");
        baseAdapter=new BasicAdapter(datas);
        set_list.setAdapter(baseAdapter);
        set_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){


                    case 0://修改密码
                        Intent  intent=new Intent(getActivity(), ChangePswActivity.class);
                        startActivity(intent);
                        break;
                    case 1://意见反馈
                          intent=new Intent(getActivity(), GettingAdviceActivity.class);
                        startActivity(intent);

                        break;
                    case 2://版本更新
                        new AlertDialog.Builder(getActivity()).setTitle("系统提示").setMessage("当前软件已经是最新版！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                            }
                        }).show();



                        break;
                    case 3://关于软件
                        intent=new Intent(getActivity(), AboutActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });

    }

    class BasicAdapter extends BaseAdapter{
        List<String> lists=new ArrayList<String>();

        public BasicAdapter(List<String> lists){
            this.lists=lists;


        }
        @Override
        public int getCount() {
           return  lists.size()   ;
        }

        @Override
        public Object getItem(int position) {
            return lists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if(convertView==null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.set_item
                        , null, false);
                viewHolder=new ViewHolder();
                viewHolder.textview= (TextView) convertView.findViewById(R.id.set_text);

                convertView.setTag(viewHolder);
            }else{
                viewHolder= (ViewHolder) convertView.getTag();

            }
            viewHolder.textview.setText(lists.get(position));
            return convertView;
        }
        class ViewHolder{
            TextView textview;
        }

    }
}

