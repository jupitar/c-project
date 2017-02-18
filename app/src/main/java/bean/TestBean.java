package bean;

/**
 * Created by Administrator on 2017/2/13.
 */

/**
 * 试题实体类
 */

public class TestBean {

    private int id;
    private String user_id;
    private String title;
    private String choiceA;
    private String choiceB;
    private String choiceC;
    private String choiceD;
    private String u_choice;
    private String c_right;
    private String comments;
    private int isTrue;
    private String total_time;

    public TestBean() {


    }



    public TestBean(int id , String title, String choiceA, String choiceB, String choiceC, String choiceD,  String c_right, String comments){
        this.id = id;

        this.choiceB = choiceB;
        this.choiceA = choiceA;
        this.choiceC = choiceC;
        this.choiceD = choiceD;

        this.c_right = c_right;


    }
    //插入数据构造函数

    public TestBean(int id , String user_id,String title, String u_choice, String c_right,int isTrue,String total_timeString ){
        this.id = id;

        this.user_id= user_id;
        this.title = title;

        this.u_choice=u_choice;
        this.c_right = c_right;
        this.isTrue=isTrue;
        this.total_time=total_time;


    }

    public TestBean(int id, String user_id,String title, String choiceB, String choiceA, String choiceC, String choiceD,String u_choice, String c_right,String comments,int isTrue,String total_time) {
        this.id = id;
        this.user_id= user_id;
        this.title = title;
        this.choiceB = choiceB;
        this.choiceA = choiceA;
        this.choiceC = choiceC;
        this.choiceD = choiceD;
        this.u_choice=u_choice;
        this.c_right = c_right;
        this.isTrue=isTrue;
        this.comments=comments;
        this.total_time=total_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChoiceA() {
        return choiceA;
    }

    public void setChoiceA(String choiceA) {
        this.choiceA = choiceA;
    }

    public String getChoiceB() {
        return choiceB;
    }

    public void setChoiceB(String choiceB) {
        this.choiceB = choiceB;
    }

    public String getChoiceC() {
        return choiceC;
    }

    public void setChoiceC(String choiceC) {
        this.choiceC = choiceC;
    }

    public String getChoiceD() {
        return choiceD;
    }

    public void setChoiceD(String choiceD) {
        this.choiceD = choiceD;
    }
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }


    public String getC_right() {
        return c_right;
    }

    public void setC_right(String c_right) {
        this.c_right = c_right;
    }


    public String getU_choice() {
        return u_choice;
    }

    public void setU_choice(String u_choice) {
        this.u_choice = u_choice;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getIsTrue() {
        return isTrue;
    }

    public void setIsTrue(int isTrue) {
        this.isTrue = isTrue;
    }

    public String getTotal_time() {
        return total_time;
    }

    public void setTotal_time(String total_time) {
        this.total_time = total_time;
    }

    @Override
    public String toString() {
        return "TestBean{" +
                "id=" + id +
                ", user_id='" + user_id + '\'' +
                ", title='" + title + '\'' +
                ", choiceA='" + choiceA + '\'' +
                ", choiceB='" + choiceB + '\'' +
                ", choiceC='" + choiceC + '\'' +
                ", choiceD='" + choiceD + '\'' +
                ", u_choice='" + u_choice + '\'' +
                ", c_right='" + c_right + '\'' +
                ", comments='" + comments + '\'' +
                ", isTrue=" + isTrue +
                ", total_time='" + total_time + '\'' +
                '}';
    }


}
