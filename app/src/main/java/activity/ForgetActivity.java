package activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;


/**
 * 忘记密码界面，输入注册手机信息确认手机信息正确，进入到修改密码界面
 */
public class ForgetActivity extends AppCompatActivity {
    TextView r_text;
    ImageView r_back;
    View v;
    EditText f_tel, f_code;
    Button f_getCode, f_submit;
    String tel = "";
    int TIMECHANGE = 1, TIMECHANGE2 = 2, j = 60;
    boolean isRunning = true;//到计时线程是否在运行
    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // super.handleMessage(msg);
            //倒计时
            if (msg.what == 1) {
                f_getCode.setText("重新发送还有" + j--);
            } else if (msg.what == 2) {
                f_getCode.setText("重新发送");
                j = 60;
                f_getCode.setEnabled(true);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        init();
    }

    private void init() {
        //手机号
        f_tel = (EditText) findViewById(R.id.f_phone);
        //验证码
        f_code = (EditText) findViewById(R.id.inputCode);
        //获取验证码按钮
        f_getCode = (Button) findViewById(R.id.getBtn);
        //提交按钮
        f_submit = (Button) findViewById(R.id.submit);
        v=findViewById(R.id.forget_include);
        r_text= (TextView) v.findViewById(R.id.mian_context);
        r_text.setText("忘记密码界面");
        r_back=(ImageView) v.findViewById(R.id.login_img);
        r_back.setVisibility(View.INVISIBLE);
    }

    //获取验证码按钮监听
    public void f_getcode(View v) {
        if (TextUtils.isEmpty(f_tel.getText())) {
            Toast.makeText(this, "手机号码不能为空!", Toast.LENGTH_SHORT).show();
            return;
        } else {
            tel = f_tel.getText().toString();


            //发送短信验证码到手机
            initSSDK();
            new AlertDialog.Builder(ForgetActivity.this).setTitle("发送短信").setMessage("我们将发送短信到如下号码:\n" + "86:" + tel).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //请求获取验证码
                    SMSSDK.getVerificationCode("86", tel);
                    //按钮不可点击
                    f_getCode.setEnabled(false);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Message msg = new Message();
                            while (isRunning && j > 0) {

                                msg.what = 1;
                                handler.sendEmptyMessage(TIMECHANGE);
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }


                            }
                            if (j <= 0) {
                                msg.what = 2;

                                handler.sendEmptyMessage(TIMECHANGE2);

                            }


                        }
                    }).start();


                }
            }).create().show();


        }
    }

    //初始化发送短信
    private void initSSDK() {
        SMSSDK.initSDK(this, "1a70eb3b5a182", "76ecf98e06491014cf7d9fa55d2ad2db");

        EventHandler eh = new EventHandler() {

            @Override
            public void afterEvent(int event, int result, Object data) {

                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        isRunning = false;//不需要再重新绘制按钮了
                        //提交验证码成功，则跳转到重置密码界面
                        Intent intent = new Intent(ForgetActivity.this, SetPswActivity.class);
                        intent.putExtra("tel", tel);
                        finish();
                        startActivity(intent);

                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                    }
                } else {
                    try {

                        Throwable throwable = (Throwable) data;
                        throwable.printStackTrace();
                        JSONObject object = new JSONObject(throwable.getMessage());
                        String des = object.optString("detail");//错误描述
                        int status = object.optInt("status");//错误代码
                        if (status > 0 && !TextUtils.isEmpty(des)) {
                            Toast.makeText(getApplicationContext(), des, Toast.LENGTH_SHORT).show();
                            return;

                        }
                    } catch (Exception e) {

                    }
                }
            }
        };
        SMSSDK.registerEventHandler(eh); //注册短信回调
    }

    //提交数据
    public void f_submit(View v) {
        if (TextUtils.isEmpty(f_code.getText())) {
            Toast.makeText(this, "验证码不能为空!", Toast.LENGTH_SHORT).show();
            return;
        }
        //提交上验证码
        SMSSDK.submitVerificationCode("86", tel, f_code.getText().toString());//触发回调接口

    }
    //


}
