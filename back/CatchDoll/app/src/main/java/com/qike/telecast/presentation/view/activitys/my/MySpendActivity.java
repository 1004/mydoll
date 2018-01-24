package com.qike.telecast.presentation.view.activitys.my;

import android.view.View;

import com.qike.telecast.R;
import com.qike.telecast.library.widgets.NetStateView;
import com.qike.telecast.library.widgets.ResultsListView;
import com.qike.telecast.module.network.Page;
import com.qike.telecast.presentation.model.dto.base.BaseItemDto;
import com.qike.telecast.presentation.model.dto.my.MySpendDto;
import com.qike.telecast.presentation.presenter.ErrorCodeOperate;
import com.qike.telecast.presentation.presenter.IBasePresenterLinstener;
import com.qike.telecast.presentation.presenter.my.MySpendPresenter;
import com.qike.telecast.presentation.presenter.page.PagePresenter;
import com.qike.telecast.presentation.view.activitys.base.AppCompatBaseActivity;
import com.qike.telecast.presentation.view.adapters.wrap.WrapConstants;
import com.qike.telecast.presentation.view.adapters.wrap.base.CommonAdapterType;
import com.qike.telecast.presentation.view.adapters.wrap.base.OnWrapItemClickListener;
import com.qike.telecast.presentation.view.adapters.wrap.my.MySpendWrap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xky on 2017/11/30 0030.
 * 我的消费记录
 */
public class MySpendActivity extends AppCompatBaseActivity implements OnWrapItemClickListener, NetStateView.IRefreshListener, ResultsListView.OnRefreshListener, IBasePresenterLinstener {
    private static final String TAG = "MySpendActivity";
    private ResultsListView topicLv;
    private NetStateView netstate;
    private boolean isRefrsh = false;
    private PagePresenter mPagePresenter;
    private MySpendPresenter mPresenter;
    private CommonAdapterType mAdapter;


    @Override
    public int getLayoutId() {
        return R.layout.activity_my_spend;
    }

    @Override
    public void initView() {
        topicLv = (ResultsListView) findViewById(R.id.lv);
        netstate = (NetStateView) findViewById(R.id.netstate);

        netstate.setContentView(topicLv);
        netstate.show(NetStateView.NetState.LOADING);
    }

    @Override
    public void initData() {
        setCommonTitle(getMString(R.string.string_spend_title));
        topicLv.setAdapter(getAdapter(), this);
        topicLv.setFooterView(ResultsListView.FOOTER_NOT_DATA);
        mPresenter = new MySpendPresenter();
        mPagePresenter = new PagePresenter();
    }

    private CommonAdapterType getAdapter() {
        mAdapter = new CommonAdapterType<BaseItemDto>(this);

        MySpendWrap mSpendWrap = new MySpendWrap();
        mSpendWrap.setOnWrapItemClickListener(this);
        mAdapter.addViewObtains(WrapConstants.WRAP_MY_SPEND, mSpendWrap);

        return mAdapter;
    }

    @Override
    public void setListener() {
        netstate.setOnRefreshListener(this);
        topicLv.setonRefreshListener(this);
        mPresenter.registPresenterCallBack(this);
    }

    @Override
    public void onRefrsh(View view) {
        //点击刷新
        isRefrsh = true;
        loadData();
    }


    @Override
    public void onRefresh() {
        //下拉刷新
        isRefrsh = true;
        mPresenter.firstTask();
    }

    @Override
    public void onUpload() {
        //上拉加载
        if (mPagePresenter != null && mPagePresenter.checkHasMore()) {
            topicLv.setFooterView(ResultsListView.FOOTER_SHOW);
            mPresenter.nextTask();
        } else {
            topicLv.setFooterView(ResultsListView.FOOTER_NOT_SHOW);
        }
    }



    @Override
    public void loadData() {
        isRefrsh = true;
        netstate.show(NetStateView.NetState.LOADING);
        mPresenter.firstTask();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    public void onItemClick(View v, Object... obj) {

    }

    @Override
    public void dataResult(Object obj, Page page, int status) {
        if (obj != null){
            List<MySpendDto> mDto = (List<MySpendDto>) obj;
            mPagePresenter.setPage(page);
            if (isRefrsh) {
                // 刷新
                mAdapter.clear();
                topicLv.onRefreshComplete();
                isRefrsh = false;
            }

            List<BaseItemDto> datas = parseData(mDto);
            mAdapter.addList(datas);
            mAdapter.notifyDataSetChanged();
            netstate.show(NetStateView.NetState.CONTENT);
            if (!mPagePresenter.checkHasMore()) {
                topicLv.setFooterView(ResultsListView.FOOTER_NOT_SHOW);
            }
        }
    }

    private List<BaseItemDto> parseData( List<MySpendDto>  mDto) {
        List<BaseItemDto> temp = new ArrayList<>();
        if (mDto != null){
            for (int i=0 ;i<mDto.size() ;i++){
                MySpendDto mySpendDto = mDto.get(i);
                mySpendDto.setItemType(WrapConstants.WRAP_MY_SPEND);
                temp.add(mySpendDto);
            }
        }
        return temp;
    }

    @Override
    public void errerResult(int code, String msg) {
        if (isRefrsh) {
            topicLv.onRefreshComplete();
            isRefrsh = false;
        }
        if (mAdapter.getCount() == 0) {
            netstate.show(NetStateView.NetState.NETERROR);
        } else if (!isRefrsh) {
        }
        ErrorCodeOperate.executeError(TAG, getContext(), code, msg, true);
    }
}
