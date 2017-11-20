package com.fy.catchdoll.presentation.view.activitys.main;

import android.view.View;

import com.fy.catchdoll.R;
import com.fy.catchdoll.library.utils.ActivityUtils;
import com.fy.catchdoll.presentation.view.activitys.base.AppCompatBaseActivity;

/**
 * Created by xky on 2017/11/10 0010.
 */
public class MainActivity extends AppCompatBaseActivity{
    private View mEnterRoomView;
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        mEnterRoomView = findViewById(R.id.main_enter_room);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {
        mEnterRoomView.setOnClickListener(this);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.main_enter_room:
                ActivityUtils.startRoomActivity(this,"");
                break;
        }

    }
}
