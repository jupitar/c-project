package activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 添加评论ctivity
 */
public class GettingAdviceActivity extends AppCompatActivity {
    ImageView img;
    TextView content;
    RadioGroup radioGroup;
    Button submit;
    EditText advice,leave_mesg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_advice);
        initVew();
        allSets();


    }
    private void initVew() {
        img= (ImageView) findViewById(R.id.login_img);
        content= (TextView) findViewById(R.id.mian_context);
        radioGroup= (RadioGroup) findViewById(R.id.radioGroup);
        submit= (Button) findViewById(R.id.submit);
        advice= (EditText) findViewById(R.id.reback_advice);
        leave_mesg= (EditText) findViewById(R.id.leave_mes);

    }

    private void allSets() {
        content.setText("意见反馈");
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(advice.getText())){
                    Toast.makeText(GettingAdviceActivity.this,"建议不能为空!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(leave_mesg.getText())){
                    Toast.makeText(GettingAdviceActivity.this,"联系方式不能为空!",Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    new AlertDialog.Builder(GettingAdviceActivity.this).setTitle("系统提示").setMessage("提交反馈成功，感谢您的反馈！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                        }
                    }).show();
                }

            }
        });

    }


    //重写返回键


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK){
            finish();



        }
        return true;

    }
}
