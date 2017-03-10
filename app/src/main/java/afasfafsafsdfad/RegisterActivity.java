package afasfafsafsdfad;

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

import static utils.URLUtils.REGISTER_SERVLET;

/**
 * 注册Activity
 */
public class RegisterActivity extends AppCompatActivity {
    EditText name, password, tel;
    TextView r_text;
    ImageView r_back;
    View v;
    String username, psw, _tel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();

    }

    private void init() {
        name = (EditText) findViewById(R.id._username);
        password = (EditText) findViewById(R.id._password);
        tel = (EditText) findViewById(R.id.tel);
        v=findViewById(R.id.register_include);
        r_text= (TextView) v.findViewById(R.id.mian_context);
        r_text.setText("注册界面");
        r_back=(ImageView) v.findViewById(R.id.login_img);
        r_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void on_register(View v) {
        //判断用户名密码是否为空
        if (TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(password.getText()) || TextUtils.isEmpty(tel.getText())) {
            Toast.makeText(this, "用户名,密码或手机号码不能为空", Toast.LENGTH_SHORT).show();


        }



         else {
            if((tel.getText().toString()).length()!=11){
                Toast.makeText(this, "请输入11位格式的手机号!", Toast.LENGTH_SHORT).show();
                return ;
            }
            getString();
            postConnection();
        }

    }



    //获取用户名和密码
    private void getString() {
        username = name.getText().toString();
        psw = password.getText().toString();
        _tel = tel.getText().toString();


    }
    //post请求数据

    public void postConnection() {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody requestBodyPost = new FormBody.Builder().add("username", username).add("password", psw).add("tel", _tel).build();
        Request requestPost = new Request.Builder().url(REGISTER_SERVLET).post(requestBodyPost).build();
        mOkHttpClient.newCall(requestPost).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(getApplicationContext(), "连接服务器失败!", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                if (result.equals("插入数据成功")) {

                    Looper.prepare();
                    Toast.makeText(getApplicationContext(), "恭喜你注册成功!", Toast.LENGTH_SHORT).show();
                    Looper.loop();


                } else if (result.equals("该手机号已经注册!")) {
                    Looper.prepare();
                    Toast.makeText(getApplicationContext(), "该手机号已经注册!", Toast.LENGTH_SHORT).show();
                    Looper.loop();


                } else if (result.equals("用户名已注册")) {
                    Looper.prepare();
                    Toast.makeText(getApplicationContext(), "用户名已注册", Toast.LENGTH_SHORT).show();
                    Looper.loop();

                }
            }
        });

    }
}
