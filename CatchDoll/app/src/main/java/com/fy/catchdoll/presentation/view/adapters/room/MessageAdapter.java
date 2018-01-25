package com.fy.catchdoll.presentation.view.adapters.room;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.fy.catchdoll.R;
import com.fy.catchdoll.library.utils.ImageLoaderUtils;
import com.fy.catchdoll.presentation.model.dto.msg.MessDto;

import java.util.ArrayList;
import java.util.List;

/**
 * 这里是聊天列表的数据适配器，比如大家使用的是环信或者第三方直播的聊天室功能，都会用的listview，
 * 对于聊天列表里面的交互以及显示方式，大家都可以在这里修改，以及布局文件
 *
 * Success is the sum of small efforts, repeated day in and day out.
 * 成功就是日复一日那一点点小小努力的积累。
 * AndroidGroup：158423375
 * Author：Johnny
 * AuthorQQ：956595454
 * AuthorWX：Qiang_it
 * AuthorPhone：nothing
 * Created by 2016/9/22.
 */
public class MessageAdapter extends BaseAdapter {

    private Context mContext;
    private List<MessDto> data;
    private ListView mListView;

    public MessageAdapter(Context context, ListView listView) {
        this.mContext = context;
        this.mListView = listView;
        this.data = new ArrayList<>();
    }


    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addData(MessDto msg){
        if (msg != null){
            if (data.size()>100){
                data.remove(0);
            }

            this.data.add(msg);
            notifyDataSetChanged();
            mListView.setSelection(data.size());
        }
    }

    public void NotifyAdapter(List<MessDto> msgs) {
        if (msgs == null){
            return;
        }
        data.addAll(msgs);
        notifyDataSetChanged();
        mListView.setSelection(this.data.size());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_messageadapter, null);
            holder.tvcontent = (TextView) convertView.findViewById(R.id.msg_content);
            holder.icon = (ImageView) convertView.findViewById(R.id.msg_icon);
            convertView.setTag(holder);
        } else{
            holder = (ViewHolder) convertView.getTag();
        }
        MessDto dto = data.get(position);
        ImageLoaderUtils.displayImage(holder.icon,R.drawable.drawable_default_color,dto.getUser_avatar());
        holder.tvcontent.setText(dto.getUser_nick()+":"+dto.getContent());
        return convertView;
    }

    private final class ViewHolder {
        ImageView icon;
        TextView tvcontent;
    }
}