package afasfafsafsdfad;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import bean.CommentsBean;
import bean.MovieInfor;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import utils.DateFormatUtil;

import static utils.URLUtils.COMMENT_SERVLET;

/**
 * 添加评论ctivity
 */
public class AddCommentActivity extends AppCompatActivity {

    View add_include;
    ImageView img_back;
    TextView add_text;

    EditText comment_content;
    Button comment_btn;

    String username="";//用户名
    int page;
    String detail_id="";

    private MovieInfor movieInfor;
    long currentTime;

    public View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_comment);
        initView();
        initData();
        setOnclick();
    }



    private void initView() {
        add_include=findViewById(R.id.add_include);
        img_back= (ImageView) add_include.findViewById(R.id.login_img);
        add_text=(TextView) add_include.findViewById(R.id.mian_context);
        add_text.setText("添加评论界面");
        comment_content= (EditText)findViewById(R.id.add_comment);
        comment_btn= (Button) findViewById(R.id.comment_btn);

    }
    private void initData() {
        //获取用户名，视频章节
        movieInfor = (MovieInfor)getIntent().getSerializableExtra("movieInfor");
        username=movieInfor.getUser_id();
        currentTime=getIntent().getLongExtra("currentTime",0);
        page=movieInfor.getPage();
        detail_id=movieInfor.getDetail_id();
    }

    private void setOnclick() {
        img_back.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Intent intent=new Intent();
                setResult(0,intent);
                finish();
            }
        });
        //评论后将数据保存到远程服务器，同时将结果返回给上一个Activity,更新到ListView评论列表
        comment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取评论内容
                if(TextUtils.isEmpty(comment_content.getText())){
                    Toast.makeText(getApplicationContext(),"评论内容不能为空！",Toast.LENGTH_SHORT).show();

                }else{
                    String content=comment_content.getText().toString();
                    //获取评论对象
                    CommentsBean commentsBean=new CommentsBean(username,  page,  detail_id,content , DateFormatUtil.getCurrentTime()  );
                    //将数据发送到服务器
                    Gson gson = new Gson();
                    String commentsBeanString=gson.toJson(commentsBean);
                    postConnection(commentsBeanString,content);
                   // Intent intent=new Intent(BROCAST_ACTION);
                   // intent.setAction(BROCAST_ACTION);
                    Intent intent=new Intent();
                    intent.putExtra("commentsBean",commentsBean);
                    intent.putExtra("currentTime",currentTime);
                    setResult(1,intent);
                    finish();

                }



            }
        });
    }
    //影藏软键盘
    public void hideSoftware(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        // LayoutInflater inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //view=inflater.inflate(R.layout.add_comment, null);
        //imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

    }

    public  void postConnection(String detail_id,String content) {//post提交数据请求
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody requestBodyPost = new FormBody.Builder().add("flag", 2+"").add("commentbean", detail_id).add("content", content).build();//comment_time  content
        Request requestPost = new Request.Builder().url(COMMENT_SERVLET).post(requestBodyPost).build();
        mOkHttpClient.newCall(requestPost).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(getApplicationContext(),"请检查你的网络连接!",Toast.LENGTH_SHORT).show();
                Looper.loop();

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                if (result.equals("successful")) {
                    Looper.prepare();
                    Toast.makeText(getApplicationContext(),"评论成功!",Toast.LENGTH_SHORT).show();
                    Looper.loop();
                } else  {
                    Looper.prepare();
                    Toast.makeText(getApplicationContext(),"评论失败!",Toast.LENGTH_SHORT).show();
                    Looper.loop();

                }
            }
        });


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //如果输入法在窗口上已经显示，则隐藏，反之则显示)
        //imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        //hideSoftware();
        Intent intent=new Intent();
        setResult(0,intent);
        finish();
        return true;
    }
}
