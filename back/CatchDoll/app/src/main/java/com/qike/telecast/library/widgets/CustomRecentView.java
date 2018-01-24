package com.qike.telecast.library.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qike.telecast.R;

import java.util.List;

/**
 * Created by wst on 2017/12/2.
 */
public class CustomRecentView extends LinearLayout{
    private Context mContext;
    private LayoutInflater mInflater;
    public CustomRecentView(Context context) {
        super(context);
        init(context);
    }

    public CustomRecentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomRecentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        setOrientation(VERTICAL);
        setBackgroundResource(R.drawable.shape_round_fff_30_bg);
    }

    public void initRecentCatchHistory(List<Object> dolls) {
        if (dolls == null || dolls.size() == 0) {
            setVisibility(GONE);
            return;
        }
        initHeaderView(mContext.getResources().getString(R.string.string_recent_title_str));
        initRecentItemView(dolls);
    }

    private void initRecentItemView(List<Object> dolls) {
        if (dolls != null){
            for (int i=0 ;i<dolls.size();i++){
                Object o = dolls.get(i);
                addRecentView(o,i==(dolls.size()-1));
            }
        }
    }

    private void addRecentView(Object o, boolean b) {
        View itemView = mInflater.inflate(R.layout.view_recent_doll_item,null);
        View line = itemView.findViewById(R.id.recent_doll_line);
        line.setVisibility(b?GONE:VISIBLE);
        addView(itemView,LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
    }

    private void initHeaderView(String title) {
        View headerView = mInflater.inflate(R.layout.view_recent_title,null);
        TextView mTitleTv = (TextView) headerView.findViewById(R.id.recent_title_tv);
        mTitleTv.setText(title);
        addView(headerView,LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
    }


    public void initDollShow(List<Object> imgs){
        if (imgs == null || imgs.size() == 0) {
            setVisibility(GONE);
            return;
        }
        initHeaderView(mContext.getResources().getString(R.string.string_doll_shwo_title_str));
        initDollShowView(imgs);
    }

    private void initDollShowView(List<Object> imgs) {
        if(imgs != null){
            for (int i=0 ;i<imgs.size();i++){
                initDollShowItemView(imgs.get(i),i==(imgs.size()-1));
            }
        }
    }

    private void initDollShowItemView(Object o, boolean b) {
        View mDollShowItem = mInflater.inflate(R.layout.view_doll_show_item, null);

        addView(mDollShowItem,LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
    }


}
