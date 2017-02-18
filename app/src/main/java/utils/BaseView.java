package utils;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import activity.R;
import bean.TestBean;

/**
 * Created by Administrator on 2017/2/13.
 */

public class BaseView {
    private LayoutInflater mInflater;

    private View view;
    private TestBean testBean;
    Context context ;
    TextView title,commentContents;
    RadioGroup radioGroup;
    RelativeLayout comment_layout;
    RadioButton radioButton;
    public BaseView(){

    }





    public BaseView(Context context,TestBean testBean,boolean isCommentVisible){
        this.testBean=testBean;
        this.context= context;
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        initView(isCommentVisible);
        initData(isCommentVisible);

    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    private void initView(boolean isCommentVisible) {
        view=mInflater.inflate(R.layout.test_item,null);
        title= (TextView) view.findViewById(R.id.title);

        radioGroup= (RadioGroup) view.findViewById(R.id.choicegroup);
        comment_layout=(RelativeLayout)view.findViewById(R.id.comment_layout);
        commentContents=(TextView)view.findViewById(R.id.analize);
        if(isCommentVisible){
            comment_layout.setVisibility(View.VISIBLE);
            disableRadioGroup(radioGroup);

        }
        else{
            enableRadioGroup(radioGroup);

        }
    }


    public void initData(boolean isCommentVisible){
        title.setText(testBean.getTitle());
        if(isCommentVisible){
            commentContents.setText(testBean.getComments());
        }
        Map<String,Integer> map=new HashMap<String,Integer>();
        map.put("A",0);
        map.put("B",1);
        map.put("C",2);
        map.put("D",3);


        RadioButton radioButton= (RadioButton) radioGroup.getChildAt(0);
        radioButton.setText(testBean.getChoiceA());
        radioButton= (RadioButton) radioGroup.getChildAt(1);
        radioButton.setText(testBean.getChoiceB());
        radioButton= (RadioButton) radioGroup.getChildAt(2);
        radioButton.setText(testBean.getChoiceC());
        radioButton= (RadioButton) radioGroup.getChildAt(3);
        radioButton.setText(testBean.getChoiceD());
        if(testBean.getIsTrue()==0){
            int j=map.get(testBean.getU_choice());
            radioButton= (RadioButton) radioGroup.getChildAt(j);
            radioButton.setBackgroundColor(Color.RED);
            int k=map.get(testBean.getC_right());
            radioButton= (RadioButton) radioGroup.getChildAt(k);
            radioButton.setBackgroundColor(Color.GREEN);
        }else{
           int  j=map.get(testBean.getC_right());
            radioButton= (RadioButton) radioGroup.getChildAt(j);
            radioButton.setBackgroundColor(Color.GREEN);

        }



    }

    //设置按钮不可点击
    public void disableRadioGroup(RadioGroup testRadioGroup) {
        for (int i = 0; i < testRadioGroup.getChildCount(); i++) {
            testRadioGroup.getChildAt(i).setEnabled(false);
        }
    }
    //设置按钮可以点击

    public void enableRadioGroup(RadioGroup testRadioGroup) {
        for (int i = 0; i < testRadioGroup.getChildCount(); i++) {
            testRadioGroup.getChildAt(i).setEnabled(true);
        }
    }
    /*private void selectRadioBtn(View v) {
        radioButton = (RadioButton) v.findViewById(radioGroup.getCheckedRadioButtonId());

        String selectText = radioButton.getText().toString();


        Toast.makeText(context, selectText.substring(0,1), Toast.LENGTH_SHORT).show();

    }*/

}
