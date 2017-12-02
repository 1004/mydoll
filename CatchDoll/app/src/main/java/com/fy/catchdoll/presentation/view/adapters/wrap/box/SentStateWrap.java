package com.fy.catchdoll.presentation.view.adapters.wrap.box;

import android.view.View;
import android.view.ViewGroup;

import com.fy.catchdoll.R;
import com.fy.catchdoll.presentation.model.dto.base.BaseItemDto;
import com.fy.catchdoll.presentation.model.dto.box.BoxDoll;
import com.fy.catchdoll.presentation.view.adapters.wrap.base.BaseViewObtion;

/**
 * Created by wst on 2017/12/2.
 */
public class SentStateWrap extends BaseViewObtion<BaseItemDto>{
    @Override
    public View createView(BaseItemDto baseItemDto, int position, View convertView, ViewGroup parent) {
        convertView = getView(R.layout.wrap_sent_state);
        ViewHolder viewHolder = new ViewHolder();

        convertView.setTag(viewHolder);
        return convertView;    }

    @Override
    public void updateView(BaseItemDto baseItemDto, int position, View convertView) {
        ViewHolder holder = (ViewHolder) convertView.getTag();
        if (!(baseItemDto instanceof BoxDoll)){
            return;
        }
    }

    class ViewHolder{
    }

}
