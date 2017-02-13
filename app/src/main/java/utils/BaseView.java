package utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
    TextView title;
    RadioGroup radioGroup;
    RadioButton radioButton;


    public BaseView(Context context,TestBean testBean){
        this.testBean=testBean;
        this.context= context;
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        initView();
        initData();

    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    private void initView() {

        view=mInflater.inflate(R.layout.test_item,null);
        title= (TextView) view.findViewById(R.id.title);
        radioGroup= (RadioGroup) view.findViewById(R.id.choicegroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectRadioBtn(view);

            }
        });



    }


    public void initData(){
        title.setText(testBean.getTitle());

            RadioButton radioButton= (RadioButton) radioGroup.getChildAt(0);
            radioButton.setText(testBean.getChoiceA());
        radioButton= (RadioButton) radioGroup.getChildAt(1);
        radioButton.setText(testBean.getChoiceB());
        radioButton= (RadioButton) radioGroup.getChildAt(2);

        radioButton= (RadioButton) radioGroup.getChildAt(3);
        radioButton.setText(testBean.getChoiceD());



    }
    private void selectRadioBtn(View v) {
        radioButton = (RadioButton) v.findViewById(radioGroup.getCheckedRadioButtonId());

        String selectText = radioButton.getText().toString();
        testBean.setC_choice(selectText.substring(0,1));

        Toast.makeText(context, selectText.substring(0,1), Toast.LENGTH_SHORT).show();

    }

}
