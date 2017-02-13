package activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jupitarwp.MyAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bean.CommentsBean;
import bean.MovieInfor;
import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import utils.CustomMediaController;
import utils.DensityUtil;

import static utils.URLUtils.COMMENT_SERVLET;
import static utils.URLUtils.Movie_URL;
import static utils.URLUtils.UPDATE_MOVIE_SERVLET;

/*视频播放界面
        * Vitamio视频播放框架Demo
        */
public class PlayActivity extends AppCompatActivity implements MediaPlayer.OnInfoListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener {
    //视频地址
    //String url = "http://192.168.1.105:8080/project/1/movie/坦克大战.avi";

    String url = "";
    String url1 = "http://baobab.wdjcdn.com/145076769089714.mp4";
    private String path = "http://baobab.wdjcdn.com/145076769089714.mp4";
    private Uri uri;
    private ProgressBar pb;
    private TextView downloadRateView, loadRateView, current_time, total_time,comment_none;
    ImageView pause_img,scren_img,add_comment_img,reset_img;
    private MediaController mMediaController;
    private CustomMediaController mCustomMediaController;
    private  VideoView mVideoView;
    private MovieInfor movieInfor = null;
    int flag = 0;
    static SeekBar seekabar1, seekbar2;//视频进度
    public static int UPDATE_UI = 1,HIDENING=2,k=5;
    private int screen_width,screen_height;
    private View  videoview_layout,control_layout1,temp_video,temp_finish;
    private  View control_layout;
    View v,v1;
    View topView;

    ImageView img_back;
    TextView play_textView;

    private ListView comment_list;
    FrameLayout list_frameLayout;
    FrameLayout frameLayout;
     boolean isVisible=true;
   List<String> str=new ArrayList<String>();
     List<CommentsBean> commentsBeanList=new ArrayList<CommentsBean>();
   MyAdapter myAdapter=null;
    private String userName;
    private int RequestCode=0;


    private   Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 1 :
                    //获取当前视频的进度
                    long currentTime = mVideoView.getCurrentPosition();
                    long totalTime = mVideoView.getDuration();
                    updateUI(current_time, currentTime);
                    updateUI(total_time, totalTime);
                    handler.sendEmptyMessageDelayed(UPDATE_UI,500);
                    seekabar1.setMax((int)totalTime);
                    seekabar1.setProgress((int)currentTime);
                    break;
                case 2:
                    isVisible=false;
                    control_layout.setVisibility(View.GONE);
                    break;
                case 3:
                    commentsBeanList=(List<CommentsBean>)msg.obj;
                    list_frameLayout.removeAllViews();
                    list_frameLayout.addView(v);
                    myAdapter.setDatas(commentsBeanList);
                    break;
                case 4:
                    list_frameLayout.removeAllViews();
                    list_frameLayout.addView(v1);
                    break;

            }


        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //必须写这个，初始化加载库文件
        Vitamio.isInitialized(this);
        if (!LibsChecker.checkVitamioLibs(this))
            return;
        setContentView(R.layout.av_play);
        //获取屏幕的宽和高
        screen_width=getResources().getDisplayMetrics().widthPixels;
        screen_height=getResources().getDisplayMetrics().heightPixels;

        initView();
        initData();


        handler.sendEmptyMessage(UPDATE_UI);
        handler.sendEmptyMessageDelayed(HIDENING,2000);

    }


    //初始化控件
    private void initView() {
        topView=findViewById(R.id.play_include);
        img_back= (ImageView) topView.findViewById(R.id.login_img);
        play_textView=(TextView) topView.findViewById(R.id.mian_context);
        play_textView.setText("");
        LayoutInflater inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        list_frameLayout=(FrameLayout) findViewById(R.id.comment_frame);
        v=inflater.inflate(R.layout.comment_list, null);
        v1=inflater.inflate(R.layout.comment_none, null);
        comment_list= (ListView) v.findViewById(R.id.comment_list);
        myAdapter=new MyAdapter(getApplicationContext());
        comment_list.setAdapter(myAdapter);
        frameLayout=(FrameLayout) findViewById(R.id.view_replace);

      temp_video=inflater.inflate(R.layout.temp_video, null);
        mVideoView = (VideoView) temp_video.findViewById(R.id.buffer);
        temp_finish=inflater.inflate(R.layout.temp_finish, null);
        reset_img=(ImageView)temp_finish.findViewById(R.id.reset_img);
        frameLayout.addView(temp_video);
       // back_img = (ImageView) findViewById(back_img);
        pause_img=(ImageView) temp_video.findViewById(R.id.movie_pause);
        add_comment_img=(ImageView) findViewById(R.id.add_comment);
        scren_img=(ImageView) temp_video.findViewById(R.id.screen_control);
        seekabar1 = (SeekBar) temp_video.findViewById(R.id.movie_seekbar);
        videoview_layout=findViewById(R.id.re_video);
        control_layout=temp_video.findViewById(R.id.control_layout);
        control_layout1=temp_video.findViewById(R.id.control_layout1);
        current_time = (TextView) temp_video.findViewById(R.id.current_time);
        total_time = (TextView) temp_video.findViewById(R.id.total_time);
        mMediaController = new MediaController(this);
        movieInfor = (MovieInfor) getIntent().getSerializableExtra("movieInfor");
        userName=getIntent().getStringExtra("userName");
        movieInfor.setUser_id(userName);
        if (movieInfor != null) {
            url = Movie_URL + movieInfor.getPage() + "/" + movieInfor.getUrl();
            //获取视频评论列表
            getComment_list(movieInfor.getDetail_id());
        }
        pb = (ProgressBar) temp_video.findViewById(R.id.probar);
        downloadRateView = (TextView) temp_video.findViewById(R.id.download_rate);
        loadRateView = (TextView) temp_video.findViewById(R.id.load_rate);

    }

    //初始化数据
    private void initData() {
        uri = Uri.parse(url);
        mVideoView.setVideoURI(uri);//设置视频播放地址
        mVideoView.setMediaController(mCustomMediaController);
        //VIDEOQUALITY_HIGH 高画质
        mVideoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);//中等画质
        mMediaController.show(5000);
        mVideoView.requestFocus();
        mVideoView.start();
        mVideoView.setOnInfoListener(this);
        mVideoView.setOnBufferingUpdateListener(this);
        setOnclick();

    }

    private void setOnclick() {
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setPlaybackSpeed(1.0f);
            }
        });
        mVideoView.setOnCompletionListener(this);
        //关闭当前播放页面
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
                finish();
            }

        });
        //屏幕全屏半屏控制
        scren_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
                    //强制设置屏幕处于横屏
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    setVideoViewSale(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
                    scren_img.setImageResource(R.drawable.half_screen);
                    return ;
                }
                if(getRequestedOrientation()== ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    setVideoViewSale(ViewGroup.LayoutParams.MATCH_PARENT,DensityUtil.dip2px(getApplication(),250));
                    scren_img.setImageResource(R.drawable.full_screen);
                    return ;
                }

            }





        });
        reset_img.
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        frameLayout.removeView(temp_finish);
                        frameLayout.addView(temp_video);
                        handler.sendEmptyMessage(UPDATE_UI);
                        handler.sendEmptyMessage(HIDENING);
                        mVideoView.start();

                    }

                });
        pause_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mVideoView.isPlaying()) {
                    pause_img.setImageResource(R.drawable.ic_player_play);
                    mVideoView.pause();
                    //停止UI刷新
                    handler.removeMessages(UPDATE_UI);
                }else{
                    pause_img.setImageResource(R.drawable.ic_player_pause);
                    mVideoView.start();
                    //重新UI刷新
                    handler.sendEmptyMessage(UPDATE_UI);
                }
            }
        });
        add_comment_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PlayActivity.this,AddCommentActivity.class);
               // movieInfor.setUser_id(userName);
                intent.putExtra("movieInfor",movieInfor);
                intent.putExtra("currentTime",mVideoView.getCurrentPosition());
                //mVideoView.pause();
                //pause_img.setImageResource(R.drawable.ic_player_play);

                startActivityForResult(intent,RequestCode);
            }
        });
        videoview_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //当前控制界面可见
                if(!isVisible){
                    isVisible=true;
                    control_layout.setVisibility(View.VISIBLE);
                    //control_layout1.setBackgroundColor(Color.GRAY);
                }
                handler.sendEmptyMessageDelayed(HIDENING,2000);

            }
        });

        seekabar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /**
             * 拖动条进度改变的时候调用
             */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateUI(current_time, progress);
                //Toast.makeText(getApplicationContext(), "onProgressChanged", Toast.LENGTH_LONG).show();
            }
            /**
             * 拖动条开始拖动的时候调用
             */

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                handler.removeMessages(UPDATE_UI);
                //  Toast.makeText(getApplicationContext(), "onStartTrackingTouch", Toast.LENGTH_LONG).show();
                handler.removeMessages(HIDENING);
            }
            /**
             * 拖动条停止拖动的时候调用
             */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                handler.sendEmptyMessage(UPDATE_UI);
                // Toast.makeText(getApplicationContext(), "onStopTrackingTouch", Toast.LENGTH_LONG).show();

                mVideoView.seekTo(seekBar.getProgress());
                // mVideoView.start();
                handler.sendEmptyMessage(HIDENING);

            }
        });

    }
    //发送视频数据

    private void sendData(){
        // 将观影记录插入数据库
        if(mVideoView.getCurrentPosition()>0){
            movieInfor.setHasFinished(updateUI(null,mVideoView.getCurrentPosition()));

            if(mVideoView.getCurrentPosition()==mVideoView.getDuration()){
                movieInfor.setIsFinished(1);
            }else{
                movieInfor.setIsFinished(0);
            }
            Gson gson=new Gson();
            postConnection1(gson.toJson(movieInfor));


        }

    }




    public  void postConnection1(String movieInfor) {//post提交视频更新数据请求
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody requestBodyPost = new FormBody.Builder().add("movieInfor", movieInfor).build();
        Request requestPost = new Request.Builder().url(UPDATE_MOVIE_SERVLET).post(requestBodyPost).build();
        mOkHttpClient.newCall(requestPost).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                if (result.equals("failure")) {
                    Looper.prepare();
                    Toast.makeText(getApplicationContext(),"视频信息提交失败!",Toast.LENGTH_LONG).show();
                    Looper.loop();
                } else if (result.equals("success")) {
                    Looper.prepare();
                    Toast.makeText(getApplicationContext(),"视频信息提交成功!",Toast.LENGTH_LONG).show();
                    Looper.loop();

                }
            }
        });


    }
    //获取视频评论列表
    private void getComment_list(String detail_id){
        postConnection(detail_id);
    }
    public  void postConnection(String detail_id) {//post提交数据请求
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody requestBodyPost = new FormBody.Builder().add("flag", 1+"").add("detail_id", detail_id).build();
        Request requestPost = new Request.Builder().url(COMMENT_SERVLET).post(requestBodyPost).build();
        mOkHttpClient.newCall(requestPost).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message msg=new Message();
                String result = response.body().string();
                if (result.equals("[]")) {

                    msg.what=4;

                } else  {
                    Gson gson = new Gson();
                    commentsBeanList=gson.fromJson(result, new TypeToken<List<CommentsBean>>(){}.getType());
                    msg.what=3;
                    msg.obj=commentsBeanList;

                }
                handler.sendMessage(msg);
            }
        });


    }

    //调整videoview宽度高度，适应屏幕横竖屏方向使其处于满屏或者半屏，
    private void setVideoViewSale(int width,int height){
        ViewGroup.LayoutParams layoutParams=mVideoView.getLayoutParams();
        layoutParams.width=width;
        layoutParams.height=height;
        mVideoView.setLayoutParams(layoutParams);
        ViewGroup.LayoutParams layoutParams2= frameLayout.getLayoutParams();
        layoutParams2.width=width;
        layoutParams2.height=height;
        frameLayout.setLayoutParams(layoutParams2);
        ViewGroup.LayoutParams layoutParams1=videoview_layout.getLayoutParams();
        layoutParams1.width=width;
        layoutParams1.height=height;
        videoview_layout.setLayoutParams(layoutParams1);
    }


    //跳转到评论界面
    private void showInputDialog() {



    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        switch (what) {
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                if (mVideoView.isPlaying()) {
                    mVideoView.pause();
                    pb.setVisibility(View.VISIBLE);
                    downloadRateView.setText("");
                    loadRateView.setText("");
                    downloadRateView.setVisibility(View.VISIBLE);
                    loadRateView.setVisibility(View.VISIBLE);
                }
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:


                mVideoView.start();
                pb.setVisibility(View.GONE);
                downloadRateView.setVisibility(View.GONE);
                loadRateView.setVisibility(View.GONE);
                break;
            case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
                downloadRateView.setText("" + extra + "kb/s" + "  ");
                pb.setVisibility(View.GONE);
                break;
        }
        return true;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        loadRateView.setText(percent + "%");
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        //屏幕切换时，设置全屏
        if(getResources().getConfiguration().orientation==Configuration.ORIENTATION_LANDSCAPE)
        {
            //mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);

            setVideoViewSale(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        }else{
            setVideoViewSale(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(getApplication(),250));

        }

        super.onConfigurationChanged(newConfig);
    }


    @Override
    public void onCompletion(MediaPlayer mp) {

        frameLayout.removeView(temp_video);
        frameLayout.addView(temp_finish);
        if(getRequestedOrientation()== ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            setVideoViewSale(ViewGroup.LayoutParams.MATCH_PARENT,DensityUtil.dip2px(getApplication(),250));
            scren_img.setImageResource(R.drawable.full_screen);
            return ;
        }
        handler.removeMessages(UPDATE_UI);
        handler.removeMessages(HIDENING);

    }

    //更新时间,将时间转化成字符串
    private static String updateUI(TextView textView, long time) {
        //3700msc=1h+1msc(60)+40s
        int hour = (int) time / 1000 / 3600;
        int msc = (int) time / 1000 % 3600 / 60;
        int sec = (int) time / 1000 % 60;
        String str = "";


        if (hour != 0) {
           str= String.format("%02d:%02d:%02d",hour,msc,sec);//两位数，如果是一位，自动补零

        }else{
            str= String.format("%02d:%02d",msc,sec);
        }
        if(textView!=null){
            textView.setText(str);

        }
        return str;



    }

//重写返回键，关闭当前Activity
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK){
            sendData();
            this.finish();

        }

        return true;
    }



    @Override
    protected void onPause() {
        super.onPause();
        handler.removeMessages(UPDATE_UI);
        handler.removeMessages(HIDENING);
        Log.i("onPause"," onPause()");

    }


    @Override
    protected void onDestroy() {
        handler.removeMessages(UPDATE_UI);
        handler.removeMessages(HIDENING);

        Log.i("onPause"," onDestroy()");
        super.onDestroy();






    }
    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("pause","haha!");

        }
    }

    @Override
    protected void onStart() {
        Log.i("pause","onStart()");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.i("pause","onResume()");
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==1){
            list_frameLayout.removeAllViews();
            list_frameLayout.addView(v);

            CommentsBean commentsBean=(CommentsBean) data.getSerializableExtra("commentsBean");
            myAdapter.addDatas(commentsBean);





        }
        handler.sendEmptyMessage(UPDATE_UI);
        handler.sendEmptyMessage(HIDENING);

    }
}
