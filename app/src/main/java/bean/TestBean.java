package bean;

/**
 * Created by Administrator on 2017/2/13.
 */

/**
 * 试题实体类
 */

public class TestBean {

    private int id;
    private String title;
    private String choiceA;
    private String choiceB;
    private String choiceC;
    private String choiceD;
    private String c_choice;
    private String c_right;

    public TestBean() {
    }

    public TestBean(int id, String title, String choiceB, String choiceA, String choiceC, String choiceD, String c_right) {
        this.id = id;
        this.title = title;
        this.choiceB = choiceB;
        this.choiceA = choiceA;
        this.choiceC = choiceC;
        this.choiceD = choiceD;
        this.c_right = c_right;
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

    public String getC_choice() {
        return c_choice;
    }

    public void setC_choice(String c_choice) {
        this.c_choice = c_choice;
    }

    public String getC_right() {
        return c_right;
    }

    public void setC_right(String c_right) {
        this.c_right = c_right;
    }
}
