package afasfafsafsdfad;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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

import static utils.URLUtils.RESET_SERVLET;

public class SetPswActivity extends AppCompatActivity {
    EditText pswText,psw1Text;
    String tel,password;
    TextView r_text;
    ImageView r_back;
    View v;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_activity);
        init();
    }

    private void init() {
        pswText=(EditText)findViewById(R.id.set_psw);
        psw1Text=(EditText)findViewById(R.id.set_psw1);
        v=findViewById(R.id.setpsw_include);
        r_text= (TextView) v.findViewById(R.id.mian_context);
        r_text.setText("重置密码界面");
        r_back=(ImageView) v.findViewById(R.id.login_img);
        r_back.setVisibility(View.INVISIBLE);
    }

    //提交密码按钮点击事件处理
    public void s_onclick(View v) {
        if (TextUtils.isEmpty(pswText.getText()) || TextUtils.isEmpty(psw1Text.getText())) {
            Toast.makeText(this, "密码或确认密码不能为空!", Toast.LENGTH_SHORT).show();
            return;
        } else {
            password = pswText.getText().toString();
            String password1 = psw1Text.getText().toString();
            if (!password.equals(password1)) {
                Toast.makeText(this, "两次输入密码不同!", Toast.LENGTH_SHORT).show();
                return;
            } else {
                //获取需要重置密码的手机号
                Intent intent = getIntent();
                tel = intent.getStringExtra("tel");
                postConnection();

            }
        }
    }
        //返回主界面按钮的监听事件
        public void back_onclick(View v){
            finish();


    }


    public void postConnection( ) {//post提交数据请求
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody requestBodyPost = new FormBody.Builder().add("tel", tel).add("password", password).build();
        Request requestPost = new Request.Builder().url(RESET_SERVLET).post(requestBodyPost).build();
        mOkHttpClient.newCall(requestPost).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                if (result.equals("successful")) {
                  //  Intent intent = new Intent(SetPswActivity.this, MainActivity.class);
                    Looper.prepare();
                    Toast.makeText(getApplicationContext(),"重置密码成功!",Toast.LENGTH_SHORT).show();
                    //finish();
                    Looper.loop();
                    //startActivity(intent);
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
}
