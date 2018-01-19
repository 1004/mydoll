package com.fy.catchdoll.presentation.view.adapters.wrap.index;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fy.catchdoll.R;
import com.fy.catchdoll.library.utils.CommonUtil;
import com.fy.catchdoll.library.utils.ImageLoaderUtils;
import com.fy.catchdoll.presentation.model.dto.base.BaseItemDto;
import com.fy.catchdoll.presentation.model.dto.doll.DollMachine;
import com.fy.catchdoll.presentation.model.dto.doll.WrapDollMachineInfo;
import com.fy.catchdoll.presentation.view.adapters.wrap.base.BaseViewObtion;

import java.util.List;

/**
 * Created by xky on 2017/11/27 0027.
 */
public class DollRoomWrap extends BaseViewObtion<BaseItemDto>{
    @Override
    public View createView(BaseItemDto baseItemDto, int position, View convertView, ViewGroup parent) {
        convertView = getView(R.layout.wrap_room_item);
        DolllViewHolder viewHolder = new DolllViewHolder();
        viewHolder.mLeftContainer = convertView.findViewById(R.id.left_room_container);
        viewHolder.mLeftCover = (ImageView) convertView.findViewById(R.id.left_room_pic);
        viewHolder.mLeftTitle = (TextView) convertView.findViewById(R.id.left_room_title);
        viewHolder.mleftMoney = (TextView) convertView.findViewById(R.id.left_room_money);
        viewHolder.mLeftBIcon = (ImageView) convertView.findViewById(R.id.left_room_b_icon);
        viewHolder.mleftBContent = (TextView) convertView.findViewById(R.id.left_room_b_con);

        viewHolder.mRightContainer = convertView.findViewById(R.id.right_room_container);
        viewHolder.mRightCover = (ImageView) convertView.findViewById(R.id.right_room_pic);
        viewHolder.mRightTitle = (TextView) convertView.findViewById(R.id.right_room_title);
        viewHolder.mRightMoney = (TextView) convertView.findViewById(R.id.right_room_money);
        viewHolder.mRightBIcon = (ImageView) convertView.findViewById(R.id.right_room_b_icon);
        viewHolder.mRightBContent = (TextView) convertView.findViewById(R.id.right_room_b_con);


        convertView.setTag(viewHolder);
        return convertView;
    }

    @Override
    public void updateView(BaseItemDto baseItemDto, int position, View convertView) {
        DolllViewHolder holder = (DolllViewHolder) convertView.getTag();
        if (!(baseItemDto instanceof WrapDollMachineInfo)){
            return;
        }

        WrapDollMachineInfo machineInfos = (WrapDollMachineInfo) baseItemDto;
        List<DollMachine> data = machineInfos.getData();
        int leftPosition = 0;
        int rightPosition = data.size()>=2 ? 1 : 0;
        if (leftPosition == rightPosition){
            holder.mRightContainer.setVisibility(View.INVISIBLE);
        }else {
            holder.mRightContainer.setVisibility(View.VISIBLE);
        }
        DollMachine leftDollRoom = data.get(leftPosition);
        initItem(holder.mLeftContainer,holder.mLeftCover, holder.mLeftTitle, holder.mleftMoney, holder.mleftBContent,holder.mLeftBIcon,leftDollRoom);

        if (leftPosition != rightPosition){
            DollMachine rightDollRoom = data.get(rightPosition);
            initItem(holder.mRightContainer,holder.mRightCover, holder.mRightTitle, holder.mRightMoney, holder.mRightBContent,holder.mRightBIcon,rightDollRoom);
        }

    }

    private void  initItem(View container,ImageView cover,TextView title,TextView money,TextView bcon,  ImageView bImg, final DollMachine dollRoom){
//        ImageLoaderUtils.setRoundedImage(dollRoom.getDoll_image(),cover);
        ImageLoaderUtils.displayImage(cover, R.drawable.drawable_write_color, dollRoom.getDoll_image());
        title.setText(dollRoom.getDoll_title());
        money.setText(dollRoom.getGold()+" "+ CommonUtil.getMoneyUnit());
        if (dollRoom.getGame_state() == DollMachine.BUSY){
            //游戏中
            bcon.setText(mActivity.getResources().getString(R.string.string_room_busy));
            bImg.setImageResource(R.mipmap.busy);
        }else {
            //空闲
            bcon.setText(mActivity.getResources().getString(R.string.string_room_free));
            bImg.setImageResource(R.mipmap.free);
        }
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemElementClick(v,dollRoom);
            }
        });
    }

    class DolllViewHolder{
        public View mLeftContainer;
        public ImageView mLeftCover;
        public TextView mLeftTitle;
        public TextView mleftMoney;
        public ImageView mLeftBIcon;
        public TextView mleftBContent;

        public View mRightContainer;
        public ImageView mRightCover;
        public TextView mRightTitle;
        public TextView mRightMoney;
        public ImageView mRightBIcon;
        public TextView mRightBContent;
    }
}
