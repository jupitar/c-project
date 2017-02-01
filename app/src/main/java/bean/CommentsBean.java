package bean;

/**
 * Created by Administrator on 2017/1/22.
 */

import java.util.Date;

/**
 * 视频评价数据
 */
public class CommentsBean {
    private String user_id;
    private int page;
    private String detail_id;
    private String comments;
    private Date date;//评论时间
    private String img_path;//图片路径

    public CommentsBean() {
    }

    public CommentsBean(String user_id, int page, String detail_id, String comments, Date date, String img_path) {
        this.user_id = user_id;
        this.page = page;
        this.detail_id = detail_id;
        this.comments = comments;
        this.date = date;
        this.img_path = img_path;
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }
}
