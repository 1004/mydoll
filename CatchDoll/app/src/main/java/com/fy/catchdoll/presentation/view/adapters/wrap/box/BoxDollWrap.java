package com.fy.catchdoll.presentation.view.adapters.wrap.box;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fy.catchdoll.R;
import com.fy.catchdoll.library.utils.ImageLoaderUtils;
import com.fy.catchdoll.presentation.model.dto.base.BaseItemDto;
import com.fy.catchdoll.presentation.model.dto.box.BoxDoll;
import com.fy.catchdoll.presentation.model.dto.doll.Doll;
import com.fy.catchdoll.presentation.view.adapters.wrap.base.BaseViewObtion;

/**
 * Created by xky on 2017/11/28 0028.
 */
public class BoxDollWrap extends BaseViewObtion<BaseItemDto>{
    @Override
    public View createView(BaseItemDto baseItemDto, int position, View convertView, ViewGroup parent) {
        convertView = getView(R.layout.wrap_box_item);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.dollIcon = (ImageView) convertView.findViewById(R.id.box_doll_icon);
        viewHolder.dollTitle = (TextView) convertView.findViewById(R.id.box_doll_title);
        viewHolder.dollTime = (TextView) convertView.findViewById(R.id.box_doll_time);

        convertView.setTag(viewHolder);
        return convertView;
    }

    @Override
    public void updateView(BaseItemDto baseItemDto, int position, View convertView) {
        ViewHolder holder = (ViewHolder) convertView.getTag();
        if (!(baseItemDto instanceof BoxDoll)){
            return;
        }
        BoxDoll dollBox = (BoxDoll) baseItemDto;
        if (dollBox!= null && dollBox.getDoll_info() != null){
            Doll doll_info = dollBox.getDoll_info();
            ImageLoaderUtils.displayImage(holder.dollIcon,R.drawable.drawable_default_color,doll_info.getImage());
            holder.dollTitle.setText(doll_info.getTitle());
            holder.dollTime.setText(doll_info.getCreated_at());
        }

    }

    class ViewHolder{
        public ImageView dollIcon;
        public TextView dollTitle;
        public TextView dollTime;
    }
}
