package activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

/*视频播放界面
        * Vitamio视频播放框架Demo
        */
public class PlayActivity extends AppCompatActivity implements MediaPlayer.OnInfoListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener {
    //视频地址
    //String url = "http://192.168.1.105:8080/project/1/movie/坦克大战.avi";
    //public static  String Movie_URL="http://192.168.1.107:8080/schoolproject1/1/movie/";
    String url = "";
    String url1 = "http://baobab.wdjcdn.com/145076769089714.mp4";
    private String path = "http://baobab.wdjcdn.com/145076769089714.mp4";
    private Uri uri;
    private ProgressBar pb;
    private TextView downloadRateView, loadRateView, current_time, total_time;
    ImageView back_img,pause_img,scren_img,add_comment_img,reset_img;
    private MediaController mMediaController;
    private CustomMediaController mCustomMediaController;
    private VideoView mVideoView;
    private MovieInfor movieInfor = null;
    int flag = 0;
    SeekBar seekabar1, seekbar2;//视频进度
    public int UPDATE_UI = 1,HIDENING=2,k=5;
    private int screen_width,screen_height;
    private View  videoview_layout,control_layout,control_layout1,temp_video,temp_finish;
    private ListView comment_list;
    private AudioManager audioManager;
    FrameLayout frameLayout;
    boolean isVisible=true;
   List<String> str=new ArrayList<String>();
    List<CommentsBean> commentsBeanList=new ArrayList<CommentsBean>();
    MyAdapter myAdapter=null;
    private Handler handler = new Handler() {
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
                    Toast.makeText(getApplication(),"hahha!",Toast.LENGTH_LONG).show();
                    break;
                case 2:
                    isVisible=false;
                    control_layout.setVisibility(View.GONE);
                    break;
                case 3:
                    commentsBeanList=(List<CommentsBean> )msg.obj;
                    myAdapter.notifyData();


            }


        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //定义全屏参数
        //int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //获得当前窗体对象
        //Window window = PlayActivity.this.getWindow();
        //设置当前窗体为全屏显示
        //window.setFlags(flag, flag);
        //必须写这个，初始化加载库文件
        Vitamio.isInitialized(this);
        if (!LibsChecker.checkVitamioLibs(this))
            return;
        setContentView(R.layout.av_play);
        //获取屏幕的宽和高
        screen_width=getResources().getDisplayMetrics().widthPixels;
        screen_height=getResources().getDisplayMetrics().heightPixels;
        audioManager= (AudioManager) getSystemService(AUDIO_SERVICE);
        initView();
        initData();
        handler.sendEmptyMessage(UPDATE_UI);
        handler.sendEmptyMessageDelayed(HIDENING,2000);

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

    //初始化控件
    private void initView() {

        comment_list= (ListView) findViewById(R.id.comment_list);
       // for(int i=0;i<5;i++){
           // CommentsBean commentsBean=new CommentsBean("wp"+i,i,i+"","comment:"+i,,i+"");
           // commentsBeanList.add(commentsBean);
      //
        myAdapter=new MyAdapter(this,commentsBeanList);
        comment_list.setAdapter(myAdapter);



       // ArrayAdapter<String> adapter = new ArrayAdapter<String>(
              //  PlayActivity.this, R.layout.comment_iten, str);
       // comment_list.setAdapter(adapter);
        frameLayout=(FrameLayout) findViewById(R.id.view_replace);
        LayoutInflater inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      temp_video=inflater.inflate(R.layout.temp_video, null);
        mVideoView = (VideoView) temp_video.findViewById(R.id.buffer);
        temp_finish=inflater.inflate(R.layout.temp_finish, null);
        reset_img=(ImageView)temp_finish.findViewById(R.id.reset_img);
        frameLayout.addView(temp_video);

        //temp_video=


        back_img = (ImageView) findViewById(R.id.back_img);
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
        //mCustomMediaController = new CustomMediaController(this, mVideoView, this);
        movieInfor = (MovieInfor) getIntent().getSerializableExtra("movieInfor");
        if (movieInfor != null) {
            url = Movie_URL + movieInfor.getPage() + "/" + movieInfor.getUrl();
            getComment_list(movieInfor.getDetail_id());
        }
        pb = (ProgressBar) temp_video.findViewById(R.id.probar);
        downloadRateView = (TextView) temp_video.findViewById(R.id.download_rate);
        loadRateView = (TextView) temp_video.findViewById(R.id.load_rate);


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
                String result = response.body().string();
                if (result.equals("null")) {
                    Looper.prepare();
                    Toast.makeText(getApplicationContext(),"当前还没有评论!",Toast.LENGTH_LONG).show();
                    Looper.loop();
                } else  {
                    Gson gson = new Gson();
                    commentsBeanList=gson.fromJson(result, new TypeToken<List<CommentsBean>>(){}.getType());
                    Message msg=new Message();
                    msg.what=3;
                    msg.obj=commentsBeanList;
                    handler.sendMessage(msg);




                }
            }
        });


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
        //mVideoView.seekTo(10000);
        mVideoView.start();
        mVideoView.setOnInfoListener(this);
        mVideoView.setOnBufferingUpdateListener(this);
        //
        setOnclick();

    }
    //弹出添加评论对话框
    private void showInputDialog() {
    /*@setView 装入一个EditView
     */
        final EditText editText = new EditText(PlayActivity.this);
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(PlayActivity.this);
        inputDialog.setTitle("我是一个输入Dialog").setView(editText);
        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(PlayActivity.this,
                                editText.getText().toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                }).show();
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
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(getApplicationContext(), "12345", Toast.LENGTH_LONG).show();
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
                        //temp_finish temp_video
                        frameLayout.removeView(temp_finish);
                        frameLayout.addView(temp_video);
                        mVideoView.start();
                        //handler.sendEmptyMessage(UPDATE_UI);
                       // handler.sendEmptyMessageDelayed(HIDENING,2000);

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
                startActivity(intent);
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
        //Toast.makeText(this, "视频播放完成!", Toast.LENGTH_LONG).show();
        //videoview_layout.setVisibility(View.INVISIBLE);
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
    private void updateUI(TextView textView, long time) {
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

        textView.setText(str);

    }

//重写返回键，关闭当前Activity
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK){
            this.finish();

        }

        return true;
    }

    @Override
    protected void onPause() {

        handler.removeMessages(UPDATE_UI);
        handler.removeMessages(HIDENING);
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        handler.removeMessages(UPDATE_UI);
        handler.removeMessages(HIDENING);
        super.onDestroy();


    }
}
