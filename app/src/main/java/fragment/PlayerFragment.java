package fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import activity.MainActivity;
import activity.PlayActivity;
import activity.R;
import bean.MovieInfor;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import utils.MyItemClickListener;

import static utils.URLUtils.LAST_INFOR_SERVLET;
import static utils.URLUtils.MOVIE_SERVLET;

/**
 * Created by Administrator on 2017/1/8.
 */
//在线视频播放fragment

public class PlayerFragment extends Fragment implements MyItemClickListener {
    private MainActivity mainActivity;

    List<MovieInfor> mDatas;
    Spinner spinner;
    TextView textView, movie_infor, cancle_movie;
    RecyclerView mRecyclerView;
    ImageView img;
    View ll_show;
    View fragment_v;
    int i = 0;
    int flag = 0;
    GridLayoutManager gm;
    HomeAdapter adapter;
    int page = 0;//选中视频章节
    int count = 0;//跳过的视频小节
    int k=0;
    String userName="";
    String json = "";
    boolean  isFirst;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
           switch(msg.what) {
               case 1:
                   k = page;
                   Toast.makeText(getActivity(), "亲，本章视频只有这么多", Toast.LENGTH_SHORT).show();
                   break;
               case 2:
                   adapter.setmDatas((List<MovieInfor>) msg.obj);
                   adapter.notifyDataSetChanged();
                   break;

           }
        }
    };

        public PlayerFragment() {

        }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragment_v = inflater.inflate(R.layout.play_fragment, container, false);
        init(fragment_v);
        set();
        return fragment_v;
    }

    private void init(View v) {//初始化相关数据
         mainActivity= (MainActivity)getActivity();
        userName= mainActivity.getName();



        mDatas = new ArrayList<MovieInfor>();
        ll_show = v.findViewById(R.id.ll_show);
        textView = (TextView) v.findViewById(R.id.changeOther);
        movie_infor = (TextView) v.findViewById(R.id.movie_infor);
        cancle_movie = (TextView) v.findViewById(R.id.cancel);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.list);
        spinner = (Spinner) v.findViewById(R.id.character);
        spinner.setGravity(Gravity.RIGHT);
        spinner.setSelection(0);
        gm = new GridLayoutManager(getActivity(), 2);

    }


    private void getLastInfor() {//获取上次观影记录
        //将用户名发送给服务器
        postConnection();

    }

    public void postConnection() {//post提交数据请求
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody requestBodyPost = new FormBody.Builder().add("user_id", userName).build();
        Request requestPost = new Request.Builder().url(LAST_INFOR_SERVLET).post(requestBodyPost).build();
        mOkHttpClient.newCall(requestPost).enqueue(new Callback() {
                                                       @Override
                                                       public void onFailure(Call call, IOException e) {

                                                       }

                                                       @Override
                                                       public void onResponse(Call call, Response response) throws IOException {
                                                           String result = response.body().string();
                                                           if (result != null) {
                                                               Gson gson = new Gson();
                                                               final MovieInfor movieInfor = gson.fromJson(result, MovieInfor.class);

                                                                       Message msg=new Message();
                                                                       msg.what=3;
                                                                       msg.obj="上次观看到" + movieInfor.getHasFinished();
                                                                       handler.sendMessage(msg);

                                                                       //cancle_movie.setVisibility(View.VISIBLE);



                                                           }

                                                       }
                                                   }

        );


    }

    //根据用户名查询用户观看信息

    private void getMovieInfor(String[] users) {
    }

    private void set() {//各种信息设置
        movie_infor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "继续播放!", Toast.LENGTH_LONG).show();


            }
        });
        cancle_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "取消播放!", Toast.LENGTH_LONG).show();


            }
        });
        //换一批的点击事件
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count += 6;
                postConnection1(page, count);



            }
        });

        //获取下拉列表选中的值
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //获取默认选中视频信息
                page = position+1;
                //每次默认加载该章节最开始的视频
                count = 0;
                postConnection1(page, count);




            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mRecyclerView.setLayoutManager(gm);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

            adapter=new HomeAdapter();


        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);


    }

    public void postConnection1(int page, int count) {//post提交数据，请求视频列表数据
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody requestBodyPost = new FormBody.Builder().add("page", page + "").add("count", count + "").build();
        Request requestPost = new Request.Builder().url(MOVIE_SERVLET).post(requestBodyPost).build();
        mOkHttpClient.newCall(requestPost).enqueue(new Callback() {
                                                       @Override
                                                       public void onFailure(Call call, IOException e) {


                                                       }

                                                       @Override
                                                       public void onResponse(Call call, Response response) throws IOException {


                                                           String result = response.body().string();
                                                           Message msg=new Message();
                                                           if (result.equals("亲,本章视频只有这么多")) {
                                                               msg.what=1;
                                                               handler.sendMessage(msg);

                                                           } else {
                                                               Gson gson = new Gson();
                                                               mDatas.clear();

                                                               mDatas =  gson.fromJson(result, new TypeToken<List<MovieInfor>>(){}.getType());
                                                               msg.what=2;
                                                               msg.obj=mDatas;
                                                               handler.sendMessage(msg);
                                                           }
                                                       }
                                                   }

        );


    }







    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
        List<MovieInfor> mDatas = new ArrayList<>();

        private MyItemClickListener mListener;

        public void setmDatas(List<MovieInfor> mDatas) {
            this.mDatas = mDatas;
        }

        public HomeAdapter() {


        }

        public void setOnItemClickListener(MyItemClickListener listener) {
            this.mListener = listener;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(
                    getActivity()).inflate(R.layout.fplay_item, parent,
                    false));
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tv.setText(mDatas.get(position).getMovie_name());
            holder.img.setBackgroundResource(R.drawable.c_1);
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView tv;
            ImageView img;

            public MyViewHolder(View view) {
                super(view);
                tv = (TextView) view.findViewById(R.id.fplay_text);
                img = (ImageView) view.findViewById(R.id.fplay_img);
                view.setOnClickListener(this);

            }

            /**
             * 点击监听
             */
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(v, getPosition());
                }
            }
        }
    }

    @Override
    public void onItemClick(View view, int postion) {

        MovieInfor movieInfor = mDatas.get(postion);
        if (movieInfor != null) {
            Intent intent = new Intent(getActivity(), PlayActivity.class);

            intent.putExtra("movieInfor", movieInfor);
            intent.putExtra("userName",userName);

           //Toast.makeText(getActivity(),movieInfor.getUrl(),Toast.LENGTH_LONG).show();
            startActivity(intent);

        }
    }

}
/**
 * 对话框
 * new AlertDialog.Builder(getActivity()).setTitle("系统提示").setMessage("你当前选中的是" +
 * position).setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
 *
 * @Override public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
 * <p>
 * }
 * }).show();
 */
