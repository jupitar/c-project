package afasfafsafsdfad;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static utils.URLUtils.UPDATE_PSWERVLET;

/**
 * 添加评论ctivity
 */
public class ChangePswActivity extends AppCompatActivity {
    View add_include;
    ImageView img_back;
    TextView add_text;
    EditText setting_orig_psw,setting_psw;
    Button setting_submit;
    String orig_psw,psw,userName,passw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepsw_activity);
        init();
        setOnclick();

    }

    private void init() {
        add_include=findViewById(R.id.add_include);
        img_back= (ImageView) add_include.findViewById(R.id.login_img);

        add_text=(TextView) add_include.findViewById(R.id.mian_context);
        add_text.setText("修改密码界面");
        setting_orig_psw= (EditText) findViewById(R.id.setting_orig_psw);
        setting_psw= (EditText) findViewById(R.id.setting_psw);
        setting_submit= (Button) findViewById(R.id.setting_submit);
        userName=getIntent().getStringExtra("userName");
        passw=getIntent().getStringExtra("psw");
    }

    private void setOnclick() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setting_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断用户名密码是否为空
                if (TextUtils.isEmpty(setting_orig_psw.getText()) || TextUtils.isEmpty(setting_psw.getText())) {
                    Toast.makeText(ChangePswActivity.this, "原始密码或新密码不能为空", Toast.LENGTH_SHORT).show();
                } else {

                    getString();

                    if(passw.equals(orig_psw)){
                        postConnection();
                    }else{
                        Toast.makeText(ChangePswActivity.this, "原始密码错误!", Toast.LENGTH_SHORT).show();

                    }

                }

            }
        });
    }
    //提交数据

    private void postConnection() {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody requestBodyPost = new FormBody.Builder().add("username", userName).add("password", psw).build();
        Request requestPost = new Request.Builder().url(UPDATE_PSWERVLET).post(requestBodyPost).build();
        mOkHttpClient.newCall(requestPost).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
               // String result = response.body().string();
                Looper.prepare();
                Toast.makeText(ChangePswActivity.this, "修改密码成功！", Toast.LENGTH_SHORT).show();
                Looper.loop();

            }
        });

    }

    //获取用户名和密码
    private void getString() {
        orig_psw = setting_orig_psw.getText().toString();//原始密码
        psw = setting_psw.getText().toString();
    }
    //重写返回键，关闭当前Activity
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK){
            new AlertDialog.Builder(ChangePswActivity.this).setTitle("系统提示").setMessage("您确定退出当前应用").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    System.exit(0);
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).show();

        }

        return true;
    }




}
