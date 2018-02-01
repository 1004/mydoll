package com.fy.catchdoll.presentation.view.activitys.question;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fy.catchdoll.R;
import com.fy.catchdoll.library.utils.ImageLoaderUtils;
import com.fy.catchdoll.module.network.Page;
import com.fy.catchdoll.presentation.model.dto.doll.CatchHistoryDto;
import com.fy.catchdoll.presentation.presenter.ErrorCodeOperate;
import com.fy.catchdoll.presentation.presenter.IBasePresenterLinstener;
import com.fy.catchdoll.presentation.presenter.box.QuestionPresenter;
import com.fy.catchdoll.presentation.view.activitys.base.AppCompatBaseActivity;

import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.impl.ImageLoader;

/**
 * Created by xky on 2018/2/1 0001.
 */
public class QuestionActivity extends AppCompatBaseActivity implements IBasePresenterLinstener {
    public static final String QUESTION_HISTORY_KEY = "question_history_key";
    private static final String TAG = "QuestionActivity";
    private CatchHistoryDto mHistoryDto;
    private QuestionPresenter mPresenter;
    private ImageView mDollIcon;
    private TextView mDollTitle;
    private TextView mCatchResult;
    private TextView mGameNum;
    private View mVideoContainer;
    private View mQuestBtn;

    @Override
    public int getLayoutId() {
        return R.layout.activity_question;
    }

    @Override
    public void initView() {
        mDollIcon = (ImageView) findViewById(R.id.box_doll_icon);
        mDollTitle = (TextView) findViewById(R.id.box_doll_title);
        mCatchResult = (TextView) findViewById(R.id.catch_state);
        mGameNum = (TextView) findViewById(R.id.box_num_tv);
        mVideoContainer = findViewById(R.id.doll_video_container);
        mQuestBtn = findViewById(R.id.recharge_save_tv);
    }

    @Override
    public void initData() {
        setCommonTitle(getMString(R.string.string_help_title));
        mPresenter = new QuestionPresenter();
        Intent intent = getIntent();
        if (intent != null){
            mHistoryDto = (CatchHistoryDto) intent.getSerializableExtra(QUESTION_HISTORY_KEY);
        }
        ImageLoaderUtils.displayImage(mDollIcon, R.drawable.drawable_default_color, mHistoryDto.getDoll_info().getImage());
        mDollTitle.setText(mHistoryDto.getDoll_info().getTitle());
        mCatchResult.setText(getResources().getString(mHistoryDto.getIs_grab() == 1 ? R.string.string_history_catch_success : R.string.string_history_catch_failed));
        mGameNum.setText(mHistoryDto.getId());
    }

    @Override
    public void setListener() {
        mQuestBtn.setOnClickListener(this);
        mVideoContainer.setOnClickListener(this);
        mPresenter.registPresenterCallBack(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.recharge_save_tv:
                mPresenter.firstTask(mHistoryDto.getId());
                break;
        }
    }

    @Override
    public void loadData() {

    }

    @Override
    public void dataResult(Object obj, Page page, int status) {
        Toast.makeText(this,getMString(R.string.string_help_result),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void errerResult(int code, String msg) {
        ErrorCodeOperate.executeError(TAG, getContext(), code, msg, true);
    }
}
