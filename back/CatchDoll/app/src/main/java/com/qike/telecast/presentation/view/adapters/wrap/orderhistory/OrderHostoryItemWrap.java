package com.qike.telecast.presentation.view.adapters.wrap.orderhistory;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qike.telecast.R;
import com.qike.telecast.library.utils.ImageLoaderUtils;
import com.qike.telecast.presentation.model.dto.base.BaseItemDto;
import com.qike.telecast.presentation.model.dto.box.BoxDoll;
import com.qike.telecast.presentation.model.dto.doll.Doll;
import com.qike.telecast.presentation.view.adapters.wrap.base.BaseViewObtion;

/**
 * Created by xky on 2017/11/28 0028.
 */
public class OrderHostoryItemWrap extends BaseViewObtion<BaseItemDto> {
    @Override
    public View createView(BaseItemDto baseItemDto, int position, View convertView, ViewGroup parent) {
        convertView = getView(R.layout.wrap_history_item);
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
        if (dollBox!= null && dollBox.getDoll() != null){
            Doll doll_info = dollBox.getDoll();
            ImageLoaderUtils.displayImage(holder.dollIcon, R.drawable.drawable_default_color, doll_info.getImage());
            holder.dollTitle.setText(doll_info.getTitle());
            holder.dollTime.setText(dollBox.getCreated_at());
        }

    }

    class ViewHolder{
        public ImageView dollIcon;
        public TextView dollTitle;
        public TextView dollTime;
    }
}
