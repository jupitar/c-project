package com.jupitarwp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import activity.R;
import bean.CommentsBean;

/**
 * Created by Administrator on 2017/2/6.
 */

/**
 * 评论适配器
 */
public class MyAdapter extends BaseAdapter{

    private LayoutInflater mInflater;
    private List<CommentsBean> commentsBeens=new ArrayList<CommentsBean>();
    public int i=0,size;
    public MyAdapter(Context context) {


        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        size=commentsBeens.size();

        return size;
    }

    @Override
    public Object getItem(int position) {
        return commentsBeens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // LayoutInflater inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       // View view=inflater.inflate(R.layout.view_dialog, null);
          ViewHolder holder;
        if(convertView==null){
            holder =new ViewHolder();
            convertView=mInflater.inflate(R.layout.comment_iten,null);
            holder.user_name=(TextView)convertView.findViewById(R.id.user_name);
            holder.user_comment=(TextView)convertView.findViewById(R.id.user_comment);
            holder.comment_time=(TextView)convertView.findViewById(R.id.comment_time);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        size=commentsBeens.size();
        holder.user_name.setText(commentsBeens.get(size-1-position).getUser_id());
        holder.user_comment.setText(commentsBeens.get(size-1-position).getComments());
       // holder.comment_time.setText(commentsBeens.get(size-1-position).getDate().toString());
        return convertView;
    }
    public void addDatas(CommentsBean commentsBean){
        commentsBeens.add(commentsBean);
        size++;
        notifyDataSetChanged();
    }

    public void setDatas(List<CommentsBean> commentsBeens){
        this.commentsBeens=commentsBeens;
        notifyDataSetChanged();

    }





    class ViewHolder{
        ImageView user_img;
        TextView user_name;
        TextView user_comment;
        TextView comment_time;

    }
}
