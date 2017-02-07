package bean;

/**
 * Created by Administrator on 2017/1/22.
 */

/**
 * 视频评价数据
 */
public class CommentsBean {
    private String user_id;
    private int page;
    private String detail_id;
    private String comments;
    private String date;//评论时间


    public CommentsBean() {
    }

    public CommentsBean(String user_id, int page, String detail_id, String comments, String date ) {
        this.user_id = user_id;
        this.page = page;
        this.detail_id = detail_id;
        this.comments = comments;
        this.date = date;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
