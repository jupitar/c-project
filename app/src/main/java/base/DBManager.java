package base;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import bean.TestBean;

/**
 * Created by Administrator on 2017/2/18.
 */

public class DBManager {
    private DBHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context) {
        helper = new DBHelper(context);
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = helper.getWritableDatabase();
    }

    /**
     * add persons
     * @param testBeans
     */
    public void add(List<TestBean> testBeans) {
        db.beginTransaction();  //开始事务
        try {
            for (TestBean testBean : testBeans) {
                db.execSQL("INSERT INTO testInfor(id,title,choiceA,choiceB,choiceC,choiceD,c_right,comments) VALUES(?,?,?,?,?,?,?,?)", new Object[]{testBean.getId(),testBean.getTitle(),testBean.getChoiceA(),testBean.getChoiceB(),testBean.getChoiceC(),testBean.getChoiceD(),testBean.getC_right(),testBean.getComments()});
            }
            db.setTransactionSuccessful();  //设置事务成功完成
        } finally {
            db.endTransaction();    //结束事务
        }
    }

    /**
     * update person's age
     * @param person
     */
    /*public void updateAge(Person person) {
        ContentValues cv = new ContentValues();
        cv.put("age", person.age);
        db.update("person", cv, "name = ?", new String[]{person.name});
    }*/

    /**
     * delete old person
     * @param person
     */
    /*public void deleteOldPerson(Person person) {
        db.delete("person", "age >= ?", new String[]{String.valueOf(person.age)});
    }*/

    /**
     * query all persons, return list
     * @return List<Person>
     */
    public List<TestBean> query(int limit,int offset) {
        ArrayList<TestBean> testBeans = new ArrayList<TestBean>();
        Cursor c = queryTheCursor(limit,offset);
        while (c.moveToNext()) {
         TestBean testBean=new TestBean();
            testBean.setId( c.getInt(c.getColumnIndex("id")));
            byte []b=new byte[1024*1024*6];

            b= c.getBlob(c.getColumnIndex("title"));
            int size=b.length;

            //String ss= new String( c.getBlob(c.getColumnIndex("title")).toString());
            //String ss=c.getString(c.getColumnIndex("title"));
            //testBean.setTitle( ss);
            testBean.setChoiceA(c.getString(c.getColumnIndex("choiceA")));
            testBean.setChoiceB(c.getString(c.getColumnIndex("choiceB")));
            testBean.setChoiceC(c.getString(c.getColumnIndex("choiceC")));
            testBean.setChoiceD(c.getString(c.getColumnIndex("choiceD")));
            testBean.setC_right(c.getString(c.getColumnIndex("c_right")));
            testBean.setComments(c.getString(c.getColumnIndex("comments")));

            testBeans.add(testBean);
        }
        c.close();
        return testBeans;
    }

    /**
     * query all persons, return cursor
     * @return  Cursor
     */
    public Cursor queryTheCursor(int limit,int count) {
        Cursor c=null;
        c = db.rawQuery("SELECT * FROM testInfor limit ? offset ? ", new String []{limit+"",count+""});
        return c;
    }

    /**
     * close database
     */
    public void closeDB() {
        db.close();
    }
    //获取数据总记录数
    public int getCount(){
        Cursor c=null;
        c = db.rawQuery("SELECT * FROM testInfor ",null);
        return c.getCount();

    }

    //分页查询

    public int getOffsetCount(int limit,int offset){
        Cursor c= queryTheCursor(limit,offset);
        return c.getCount();

    }


}
