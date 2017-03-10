package afasfafsafsdfad;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import fragment.PlayerFragment;
import fragment.SettingFragment;
import fragment.TestFragment;

import static utils.URLUtils.UBASIC_EXAMSERVLET;


public class MainActivity extends AppCompatActivity {
    String userName,psw;
    PlayerFragment playerFragment;
    TestFragment testFragment;

    SettingFragment settingFragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    ImageView play_img, test_img,settings_img;
    TextView play_text, test_text,settings_text;
    TextView r_text;
    ImageView r_back,top_refresh;
    View v;
    private String data = "12";
    private int count = 0;
    String url1 = "http://baobab.wdjcdn.com/145076769089714.mp4";
    String url = "http://112.253.22.157/17/z/z/y/u/zzyuasjwufnqerzvyxgkuigrkcatxr/hc.yinyuetai.com/D046015255134077DDB3ACA0D\n" +
            "7E68D45.flv";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init() {
        allFind();
        Intent intent=getIntent();
        userName= intent.getStringExtra("username");
        psw=intent.getStringExtra("password");
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        playerFragment = new PlayerFragment();
        fragmentTransaction.replace(R.id.mainContent, playerFragment);
        play_img.setImageResource(R.mipmap.blue_play);
        play_text.setTextColor(Color.BLUE);
        fragmentTransaction.commit();
    }

    public void allFind(){
        play_img = (ImageView) findViewById(R.id.play_img);
        play_text = (TextView) findViewById(R.id.play_text);
        test_img = (ImageView) findViewById(R.id.test_img);
        test_text = (TextView) findViewById(R.id.test_text);

        settings_img = (ImageView) findViewById(R.id.settings_img);
        settings_text = (TextView) findViewById(R.id.settings_text);
        v=findViewById(R.id.topBar);
        r_text= (TextView) v.findViewById(R.id.mian_context);
        top_refresh=(ImageView) v.findViewById(R.id.test_refresh);

        top_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testFragment.postConnection(UBASIC_EXAMSERVLET, userName);
            }
        });
        r_text.setText("主界面");
        r_back=(ImageView) v.findViewById(R.id.login_img);
        r_back.setVisibility(View.INVISIBLE);
    }

    public String getName(){
        return userName;
    }
    public String getPsw(){
        return psw;
    }

    public void mulclick(View v) {
        switch (v.getId()) {
            case R.id.ll_play:
                r_text.setText("视频界面");
                top_refresh.setVisibility(View.INVISIBLE);
                fragmentTransaction = fragmentManager.beginTransaction();
                if (playerFragment == null) {
                    playerFragment = new PlayerFragment();
                }
                fragmentTransaction.replace(R.id.mainContent, playerFragment);
                fragmentTransaction.commit();
                multisetting(v);
                clearOther(0);
                break;
            case R.id.ll_test:
                r_text.setText("测试界面");
                top_refresh.setVisibility(View.VISIBLE);
                fragmentTransaction = fragmentManager.beginTransaction();
                if (testFragment == null) {
                    testFragment = new TestFragment();
                }
                fragmentTransaction.replace(R.id.mainContent, testFragment);
                fragmentTransaction.commit();
                multisetting(v);
                clearOther(1);
                break;

            case R.id.ll_settings:
                r_text.setText("设置界面");
                top_refresh.setVisibility(View.INVISIBLE);
                fragmentTransaction = fragmentManager.beginTransaction();
                if (settingFragment == null) {
                    settingFragment = new SettingFragment();
                }

                fragmentTransaction.replace(R.id.mainContent, settingFragment);
                fragmentTransaction.commit();
                multisetting(v);
                clearOther(3);
                break;
        }
    }

    //将未选中的还原为未选中状态
    private void clearOther(int number) {
        switch (number) {
            case 0:
                test_img.setImageResource(R.mipmap.gray_test);
                test_text.setTextColor(Color.GRAY);

                settings_img.setImageResource(R.mipmap.gray_setting);
                settings_text.setTextColor(Color.GRAY);
                break;
            case 1:
                play_img.setImageResource(R.mipmap.gray_play);
                play_text.setTextColor(Color.GRAY);

                settings_img.setImageResource(R.mipmap.gray_setting);
                settings_text.setTextColor(Color.GRAY);
                break;
            case 2:
                play_img.setImageResource(R.mipmap.gray_play);
                play_text.setTextColor(Color.GRAY);
                test_img.setImageResource(R.mipmap.gray_test);
                test_text.setTextColor(Color.GRAY);
                settings_img.setImageResource(R.mipmap.gray_setting);
                settings_text.setTextColor(Color.GRAY);
                break;
            case 3:
                play_img.setImageResource(R.mipmap.gray_play);
                play_text.setTextColor(Color.GRAY);
                test_img.setImageResource(R.mipmap.gray_test);
                test_text.setTextColor(Color.GRAY);
                break;
        }


    }

    public void multisetting(View v) {

        switch (v.getId()) {
            case R.id.ll_play:

                play_img.setImageResource(R.mipmap.blue_play);

                play_text.setTextColor(Color.BLUE);
                break;
            case R.id.ll_test:

                test_img.setImageResource(R.mipmap.blue_test);

                test_text.setTextColor(Color.BLUE);
                break;
           
            case R.id.ll_settings:

                settings_img.setImageResource(R.mipmap.blue_setting);

                settings_text.setTextColor(Color.BLUE);
                break;

        }


    }
    //重写返回键，关闭当前Activity
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK){
           new AlertDialog.Builder(MainActivity.this).setTitle("系统提示").setMessage("您确定退出当前应用").setPositiveButton("确定", new DialogInterface.OnClickListener() {
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




