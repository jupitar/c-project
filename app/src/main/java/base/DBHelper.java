package base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import bean.TestBean;

/**
 * Created by Administrator on 2017/2/18.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "test.db";
    private static final int DATABASE_VERSION = 1;
    List<TestBean> testBeens=new ArrayList<TestBean>();

    public DBHelper(Context context) {
        //CursorFactory设置为null,使用默认值
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //数据库第一次被创建时onCreate会被调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS testInfor" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, id int ,\n" +
                "title VARCHAR(100),\n" +
                "choiceA VARCHAR(50),\n" +
                "choiceB VARCHAR(50),\n" +
                "choiceC VARCHAR(50),\n" +
                "choiceD VARCHAR(50),\n" +
                "c_right VARCHAR(10),\n" +
                "comments Text)");
        getDatas();
        for(TestBean testBean:testBeens){
            db.execSQL("INSERT INTO testInfor(id,title,choiceA,choiceB,choiceC,choiceD,c_right,comments) VALUES(?,?,?,?,?,?,?,?)", new Object[]{testBean.getId(),testBean.getTitle(),testBean.getChoiceA(),testBean.getChoiceB(),testBean.getChoiceC(),testBean.getChoiceD(),testBean.getC_right(),testBean.getComments()});
        }



        /*db.execSQL("CREATE TABLE IF NOT EXISTS user_test" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, id int ,\n" + "user_id VARCHAR(50), id INT, u_choice VARCHAR(10), c_right VARCHAR(10), isTrue INT , total_time VARCHAR(50) ,title VARCHAR(100),  choiceA VARCHAR(50), choiceB VARCHAR(50),choiceC VARCHAR(50),choiceD VARCHAR(50),c_right VARCHAR(10),comments Text)");*/





    }
    //获取数据

    private void getDatas() {
        //int id, String user_id,String title, String choiceB, String choiceA, String choiceC, String choiceD,String u_choice, String c_right,int isTrue,String total_time

        TestBean testBean=new TestBean(1,"____是C语言合法的常量。","A. .45","B. 078","C. 25.6E34","D. XY","B","分析可知，正确答案是B");
        testBeens.add(testBean);

        testBean=new TestBean(2,"____是C语言合法的常量。","A. .45","B. 078","C. 25.6E34","D. XY","B","分析可知，正确答案是B");
        testBeens.add(testBean);
        testBean=new TestBean(3,"____是C语言合法的常量。","A. .45","B. 078","C. 25.6E34","D. XY","B","分析可知，正确答案是B");
        testBeens.add(testBean);
        testBean=new TestBean(4,"____是C语言合法的常量。","A. .45","B. 078","C. 25.6E34","D. XY","B","分析可知，正确答案是B");
        testBeens.add(testBean);
        testBean=new TestBean(5,"____是C语言合法的常量。","A. .45","B. 078","C. 25.6E34","D. XY","B","分析可知，正确答案是B");
        testBeens.add(testBean);
        testBean=new TestBean(6,"____是C语言合法的常量。","A. .45","B. 078","C. 25.6E34","D. XY","B","分析可知，正确答案是B");
        testBeens.add(testBean);
        testBean=new TestBean(7,"____是C语言合法的常量。","A. .45","B. 078","C. 25.6E34","D. XY","B","分析可知，正确答案是B");
        testBeens.add(testBean);
        testBean=new TestBean(8,"____是C语言合法的常量。","A. .45","B. 078","C. 25.6E34","D. XY","B","分析可知，正确答案是B");
        testBeens.add(testBean);
        testBean=new TestBean(9,"____是C语言合法的常量。","A. .45","B. 078","C. 25.6E34","D. XY","B","分析可知，正确答案是B");

        testBeens.add(testBean);

        testBean=new TestBean(10,"____是C语言合法的常量。","A. .45","B. 078","C. 25.6E34","D. XY","B","分析可知，正确答案是B");
        testBeens.add(testBean);
        testBean=new TestBean(11,"____是C语言合法的常量。","A. .45","B. 078","C. 25.6E34","D. XY","B","分析可知，正确答案是B");testBean=new TestBean(2,"____是C语言合法的常量。","A. .45","B. 078","C. 25.6E34","D. XY","B","分析可知，正确答案是B");testBean=new TestBean(2,"____是C语言合法的常量。","A. .45","B. 078","C. 25.6E34","D. XY","B","分析可知，正确答案是B");testBean=new TestBean(2,"____是C语言合法的常量。","A. .45","B. 078","C. 25.6E34","D. XY","B","分析可知，正确答案是B");testBean=new TestBean(2,"____是C语言合法的常量。","A. .45","B. 078","C. 25.6E34","D. XY","B","分析可知，正确答案是B");testBean=new TestBean(2,"____是C语言合法的常量。","A. .45","B. 078","C. 25.6E34","D. XY","B","分析可知，正确答案是B");
        testBeens.add(testBean);

        testBean=new TestBean(12,"____是C语言合法的常量。","A. .45","B. 078","C. 25.6E34","D. XY","B","分析可知，正确答案是B");

        testBeens.add(testBean);
        testBean=new TestBean(13,"____是C语言合法的常量。","A. .45","B. 078","C. 25.6E34","D. XY","B","分析可知，正确答案是B");
        testBeens.add(testBean);
        testBean=new TestBean(14,"____是C语言合法的常量。","A. .45","B. 078","C. 25.6E34","D. XY","B","分析可知，正确答案是B");
        testBeens.add(testBean);
        testBean=new TestBean(15,"____是C语言合法的常量。","A. .45","B. 078","C. 25.6E34","D. XY","B","分析可知，正确答案是B");

        testBeens.add(testBean);
        testBean=new TestBean(16,"____是C语言合法的常量。","A. .45","B. 078","C. 25.6E34","D. XY","B","分析可知，正确答案是B");
        testBeens.add(testBean);
        testBean=new TestBean(17,"____是C语言合法的常量。","A. .45","B. 078","C. 25.6E34","D. XY","B","分析可知，正确答案是B");
        testBeens.add(testBean);
        testBean=new TestBean(18,"____是C语言合法的常量。","A. .45","B. 078","C. 25.6E34","D. XY","B","分析可知，正确答案是B");
        testBeens.add(testBean);
        testBean=new TestBean(19,"____是C语言合法的常量。","A. .45","B. 078","C. 25.6E34","D. XY","B","分析可知，正确答案是B");testBean=new TestBean(2,"____是C语言合法的常量。","A. .45","B. 078","C. 25.6E34","D. XY","B","分析可知，正确答案是B");testBean=new TestBean(2,"____是C语言合法的常量。","A. .45","B. 078","C. 25.6E34","D. XY","B","分析可知，正确答案是B");testBean=new TestBean(2,"____是C语言合法的常量。","A. .45","B. 078","C. 25.6E34","D. XY","B","分析可知，正确答案是B");
        testBeens.add(testBean);
        testBean=new TestBean(20,"____是C语言合法的常量。","A. .45","B. 078","C. 25.6E34","D. XY","B","分析可知，正确答案是B");
        testBeens.add(testBean);
        testBean=new TestBean(21,"____是C语言合法的常量。","A. .45","B. 078","C. 25.6E34","D. XY","B","分析可知，正确答案是B");testBean=new TestBean(2,"____是C语言合法的常量。","A. .45","B. 078","C. 25.6E34","D. XY","B","分析可知，正确答案是B");testBean=new TestBean(2,"____是C语言合法的常量。","A. .45","B. 078","C. 25.6E34","D. XY","B","分析可知，正确答案是B");testBean=new TestBean(2,"____是C语言合法的常量。","A. .45","B. 078","C. 25.6E34","D. XY","B","分析可知，正确答案是B");testBean=new TestBean(2,"____是C语言合法的常量。","A. .45","B. 078","C. 25.6E34","D. XY","B","分析可知，正确答案是B");testBean=new TestBean(2,"____是C语言合法的常量。","A. .45","B. 078","C. 25.6E34","D. XY","B","分析可知，正确答案是B");
        testBeens.add(testBean);
        testBean=new TestBean(12,"____是C语言合法的常量。","A. .45","B. 078","C. 25.6E34","D. XY","B","分析可知，正确答案是B");
        testBeens.add(testBean);
        testBean=new TestBean(13,"____是C语言合法的常量。","A. .45","B. 078","C. 25.6E34","D. XY","B","分析可知，正确答案是B");
        testBeens.add(testBean);
        testBean=new TestBean(24,"____是C语言合法的常量。","A. .45","B. 078","C. 25.6E34","D. XY","B","分析可知，正确答案是B");
        testBeens.add(testBean);
        testBean=new TestBean(25,"____是C语言合法的常量。","A. .45","B. 078","C. 25.6E34","D. XY","B","分析可知，正确答案是B");
        testBeens.add(testBean);











    }

    //如果DATABASE_VERSION值被改为2,系统发现现有数据库版本不同,即会调用onUpgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       //db.execSQL("ALTER TABLE person ADD COLUMN other STRING");
    }


}
