package activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
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

import static utils.URLUtils.LOGIN_SERVLET;

/**
 * 登录Activity
 */
public class LoginActivity extends AppCompatActivity {
    EditText userName, password;
    TextView show,textDesc;
    ImageView img_back;
    View top_back;
    String name = null, psw = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    //初始化控件
    private void init() {
        userName = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        top_back=findViewById(R.id.ll_include);
        textDesc= (TextView) top_back.findViewById(R.id.mian_context);
        textDesc.setText("登录界面");
        img_back= (ImageView) top_back.findViewById(R.id.login_img);
        img_back.setVisibility(View.INVISIBLE);
    }

    //登录
    public void on_login(View v) {
        //判断用户名密码是否为空
        if (TextUtils.isEmpty(userName.getText()) || TextUtils.isEmpty(password.getText())) {
            Toast.makeText(this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
        } else {
            getString();
            postConnection();
        }

    }

    //注册
    public void on_register(View v) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);

        startActivity(intent);
    }

    //忘记密码
    public void on_forget(View v) {

        Intent intent = new Intent(LoginActivity.this, ForgetActivity.class);
        startActivity(intent);

    }

    //获取用户名和密码
    private void getString() {
        name = userName.getText().toString();
        psw = password.getText().toString();
    }

    public  void postConnection() {//post提交数据请求
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody requestBodyPost = new FormBody.Builder().add("username", name).add("password", psw).build();
        Request requestPost = new Request.Builder().url(LOGIN_SERVLET).post(requestBodyPost).build();
        mOkHttpClient.newCall(requestPost).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                if (result.equals("successful")) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("username",name);
                    intent.putExtra("password",psw);
                    finish();
                    startActivity(intent);
                } else if (result.equals("用户密码错误")) {
                    Looper.prepare();
                    Toast.makeText(getApplicationContext(), "用户密码错误！", Toast.LENGTH_SHORT).show();
                    Looper.loop();


                } else if (result.equals("用户名或者密码错误")) {
                    Looper.prepare();
                    Toast.makeText(getApplicationContext(), "用户名或者密码错误", Toast.LENGTH_SHORT).show();
                    Looper.loop();

                }
            }
        });


    }

    //重写返回键，关闭当前Activity
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK){
            new AlertDialog.Builder(LoginActivity.this).setTitle("系统提示").setMessage("您确定退出当前应用").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();

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



