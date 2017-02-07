package utils;

/**
 * Created by Administrator on 2017/1/5.
 */

public class URLUtils {
    public static String LOCAL_URL="http://10.0.2.2:8080/";
    //视频URL
    public static  String Movie_URL="http://192.168.1.101:8080/project/movie/";
    //网络url
    public static  String BASIC_URL="http://192.168.1.101:8080/";
    //登录servlet
    public static String LOGIN_SERVLET=BASIC_URL+"schoolproject/servlet/LoginServlet";
    //注册servlet
    public static String REGISTER_SERVLET=BASIC_URL+"schoolproject/servlet/RegisterServlet";
   //重置密码servlet
    public static String RESET_SERVLET=BASIC_URL+"schoolproject/servlet/ResetServlet";
   //获取视频信息servlet
    public static String MOVIE_SERVLET=BASIC_URL+"schoolproject/servlet/MoviesServlet";
    //获取上次观看视频信息servlet
    public static String LAST_INFOR_SERVLET=BASIC_URL+"schoolproject/servlet/LastMovieServlet";
}
