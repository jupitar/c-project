package activity;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import bean.MovieInfor;
import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import utils.CustomMediaController;

import static utils.URLUtils.Movie_URL;

/*视频播放界面
        * Vitamio视频播放框架Demo
        */
public class PlayActivity extends AppCompatActivity implements MediaPlayer.OnInfoListener, MediaPlayer.OnBufferingUpdateListener,MediaPlayer.OnCompletionListener {
    //视频地址
   //String url = "http://192.168.1.105:8080/project/1/movie/坦克大战.avi";
    //public static  String Movie_URL="http://192.168.1.107:8080/schoolproject1/1/movie/";
    String url="";
    String url1 = "http://baobab.wdjcdn.com/145076769089714.mp4";
    private String path = "http://baobab.wdjcdn.com/145076769089714.mp4";
    private Uri uri;
    private ProgressBar pb;
    private TextView downloadRateView, loadRateView;
    private TextView fileName;
    private MediaController mMediaController;
    private CustomMediaController mCustomMediaController;
    private VideoView mVideoView;
    private MovieInfor movieInfor=null;
    int flag=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //定义全屏参数
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //获得当前窗体对象
        Window window = PlayActivity.this.getWindow();
        //设置当前窗体为全屏显示
       window.setFlags(flag, flag);
        //必须写这个，初始化加载库文件
        Vitamio.isInitialized(this);
        if (!LibsChecker.checkVitamioLibs(this))
            return;
        setContentView(R.layout.av_play);
        initView();
        initData();
    }

    //初始化控件
    private void initView() {
        mVideoView = (VideoView) findViewById(R.id.buffer);
        fileName=(TextView)findViewById(R.id.mediacontroller_filename1);
        mMediaController = new MediaController(this);
       // mCustomMediaController = new CustomMediaController(this, mVideoView, this);
        movieInfor=(MovieInfor)getIntent().getSerializableExtra("movieInfor");
        if(movieInfor!=null){
            fileName.setText(movieInfor.getMovie_name());
           // mCustomMediaController.setVideoName(movieInfor.getMovie_name());
            url=Movie_URL+movieInfor.getPage()+"/"+movieInfor.getUrl();
        }
        pb = (ProgressBar) findViewById(R.id.probar);
        downloadRateView = (TextView) findViewById(R.id.download_rate);
        loadRateView = (TextView) findViewById(R.id.load_rate);
    }

    //初始化数据
    private void initData() {
        uri = Uri.parse(url);
        mVideoView.setVideoURI(uri);//设置视频播放地址
        mVideoView.setMediaController(mMediaController);
        //VIDEOQUALITY_HIGH 高画质
        mVideoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);//中等画质
        mMediaController.show(5000);
        mVideoView.requestFocus();
        mVideoView.setOnInfoListener(this);
        mVideoView.setOnBufferingUpdateListener(this);
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {

                mediaPlayer.setPlaybackSpeed(1.0f);
            }
        });
        mVideoView.setOnCompletionListener(this);
        mVideoView.seekTo(70000);

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
        /*屏幕切换时，设置全屏
        if (mVideoView != null) {
            mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
        }
        super.onConfigurationChanged(newConfig);*/
    }


    @Override
    public void onCompletion(MediaPlayer mp) {
        Toast.makeText(this,"视频播放完成!",Toast.LENGTH_LONG).show();
        if(mVideoView != null) {


           // mVideoView.start();
            //playOrPause()

           // mCustomMediaController.playOrPause();
        }
    }
}
