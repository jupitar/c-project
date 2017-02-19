package activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 试题信息提交成功ctivity
 */
public class SubmitActivity extends AppCompatActivity {
    ImageView img;
    TextView content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);
        initVew();
        allSets();


    }

    private void allSets() {
        content.setText("关于软件");
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

    }

    private void initVew() {
        img= (ImageView) findViewById(R.id.login_img);
        content= (TextView) findViewById(R.id.mian_context);
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
