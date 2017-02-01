package bean;

/**
 * Created by Administrator on 2017/1/7.
 */

/**
 *
 */
public class User {
    private String username;
    private String  password;
    private String tel;

    public User() {
    }


    public User(String usermname, String password) {
        this.username = usermname;
        this.password = password;
    }

    public User(String usermname, String password, String tel) {
        this.username = usermname;
        this.password = password;
        this.tel = tel;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String usermname) {
        this.username = usermname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }
}
