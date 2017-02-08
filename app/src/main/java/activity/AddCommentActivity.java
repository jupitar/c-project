package activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * 添加评论ctivity
 */
public class AddCommentActivity extends AppCompatActivity {
    String username="wp1";
    View back_view;
    ImageView back_img;
    EditText comment_content;
    Button comment_btn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_comment);
        LayoutInflater inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        back_view=inflater.inflate(R.layout.top_back1, null);
        back_img=(ImageView)back_view.findViewById(R.id.t_back);
        comment_btn= (Button) findViewById(R.id.comment_btn);


        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });
        comment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


}
