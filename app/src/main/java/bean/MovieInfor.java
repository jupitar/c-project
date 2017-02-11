package bean;

/**
 * Created by Administrator on 2017/1/17.
 */

import java.io.Serializable;

/**
 * 视频信息bean
 */
public class MovieInfor implements Serializable{
    private String user_id;
    private int page;//视频章节
    private String detail_id;//具体小节
    private String movie_name;//视频名称
    private String url;//视频url
    String hasFinished;//视频已经观看时长
    int  isFinished;//是否观看完
    private String movie_length;//视频时长

    public MovieInfor() {
    }

    public MovieInfor(String user_id, int page, String detail_id, String length, int isFinished, String hasFinished, String url, String movie_name) {
        this.user_id = user_id;
        this.page = page;
        this.detail_id = detail_id;
        this.movie_length = length;
        this.isFinished = isFinished;
        this.hasFinished = hasFinished;
        this.url = url;
        this.movie_name = movie_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getDetail_id() {
        return detail_id;
    }

    public void setDetail_id(String detail_id) {
        this.detail_id = detail_id;
    }

    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHasFinished() {
        return hasFinished;
    }

    public void setHasFinished(String hasFinished) {
        this.hasFinished = hasFinished;
    }

    public int  getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(int  isFinished) {
        this.isFinished = isFinished;
    }

    public String getMovie_length() {
        return movie_length;
    }

    public void setMovie_length(String movie_length) {
        this.movie_length = movie_length;
    }
}
