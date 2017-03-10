package utils;

import java.net.InetAddress;

/**
 * Created by Administrator on 2017/1/5.
 */

public class URLUtils {
   public static String LOCAL_URL="http://10.0.2.2:8080/";
   // public static String LOCAL_URL="http://192.168.1.103:8080/";
     public static  String Movie_URL=LOCAL_URL+"project/movie/";
    //视频URL
   // public static  String Movie_URL="http://192.168.1.103:8080/project/movie/";
    //网络url
    public static  String BASIC_URL="http://192.168.1.103:8080/";
    //登录servlet
    public static String LOGIN_SERVLET=LOCAL_URL+"schoolproject/servlet/LoginServlet";
    //注册servlet
    public static String REGISTER_SERVLET=LOCAL_URL+"schoolproject/servlet/RegisterServlet";
   //重置密码servlet
    public static String RESET_SERVLET=LOCAL_URL+"schoolproject/servlet/ResetServlet";
   //获取视频信息servlet
    public static String MOVIE_SERVLET=LOCAL_URL+"schoolproject/servlet/MoviesServlet";
    //获取上次观看视频信息servlet
    public static String LAST_INFOR_SERVLET=LOCAL_URL+"schoolproject/servlet/LastMovieServlet";
//获取视频评论列表
    public static String COMMENT_SERVLET=LOCAL_URL+"schoolproject/servlet/CommentServlet";
    //更新视频信息
    public static String UPDATE_MOVIE_SERVLET=LOCAL_URL+"schoolproject/servlet/UpdateMovieServlet";
   //获取总试题数目，已经做的试题数目，正确率
    public static String UBASIC_EXAMSERVLET=LOCAL_URL+"schoolproject/servlet/ExamServlet";
    //获取批量试题练习
    public static String GET_EXAMSERVLET=LOCAL_URL+"schoolproject/servlet/GetExamServlet";
    //错题信息查看
    public static String ERROR_EXAMSERVLET=LOCAL_URL+"schoolproject/servlet/ErrorExamServlet";
    //插入数据
    public static String INSERT_EXAMSERVLET=LOCAL_URL+"schoolproject/servlet/InsertExamServlet";
//是直接面更新密码
public static String UPDATE_PSWERVLET=LOCAL_URL+"schoolproject/servlet/UpdatePswServlet";


    //获取本机IP地址
    public static  void  getUrl(){
        InetAddress ia=null;
        String localip=null;
        try {
            ia=ia.getLocalHost();
            localip=ia.getHostAddress();
           // System.out.println("本机名称是："+ localname);
            //System.out.println("本机的ip是 ："+localip);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        finally{
            if(localip.equals("127.0.0.1")){
                BASIC_URL="http://"+"10.0.2.2:8080/";
            }else{
                BASIC_URL="http://"+localip+":8080/";

            }

        }


    }


}
