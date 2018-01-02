package com.fy.catchdoll.presentation.view.activitys.orderhistory;

import android.view.View;

import com.fy.catchdoll.R;
import com.fy.catchdoll.library.widgets.NetStateView;
import com.fy.catchdoll.library.widgets.ResultsListView;
import com.fy.catchdoll.module.network.Page;
import com.fy.catchdoll.presentation.model.dto.base.BaseItemDto;
import com.fy.catchdoll.presentation.model.dto.box.AddressInfo;
import com.fy.catchdoll.presentation.model.dto.box.BoxDoll;
import com.fy.catchdoll.presentation.model.dto.box.OrderHistoryDto;
import com.fy.catchdoll.presentation.model.dto.box.SendState;
import com.fy.catchdoll.presentation.model.dto.my.MySpendDto;
import com.fy.catchdoll.presentation.presenter.ErrorCodeOperate;
import com.fy.catchdoll.presentation.presenter.IBasePresenterLinstener;
import com.fy.catchdoll.presentation.presenter.my.MySpendPresenter;
import com.fy.catchdoll.presentation.presenter.orderhistory.OrderHistoryPresenter;
import com.fy.catchdoll.presentation.presenter.page.PagePresenter;
import com.fy.catchdoll.presentation.view.activitys.base.AppCompatBaseActivity;
import com.fy.catchdoll.presentation.view.adapters.wrap.WrapConstants;
import com.fy.catchdoll.presentation.view.adapters.wrap.base.CommonAdapterType;
import com.fy.catchdoll.presentation.view.adapters.wrap.base.OnWrapItemClickListener;
import com.fy.catchdoll.presentation.view.adapters.wrap.my.MySpendWrap;
import com.fy.catchdoll.presentation.view.adapters.wrap.orderhistory.OrderHostoryAddressWrap;
import com.fy.catchdoll.presentation.view.adapters.wrap.orderhistory.OrderHostoryItemWrap;
import com.fy.catchdoll.presentation.view.adapters.wrap.orderhistory.SentStateWrap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xky on 2017/12/14 0014.
 */
public class OrderHistoryActivity extends AppCompatBaseActivity implements OnWrapItemClickListener, NetStateView.IRefreshListener, ResultsListView.OnRefreshListener, IBasePresenterLinstener {
    private static final String TAG = "MySpendActivity";
    private ResultsListView topicLv;
    private NetStateView netstate;
    private boolean isRefrsh = false;
    private PagePresenter mPagePresenter;
    private OrderHistoryPresenter mPresenter;
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
        setCommonTitle(getMString(R.string.string_orderhistory_title));
        topicLv.setAdapter(getAdapter(), this);
        topicLv.setFooterView(ResultsListView.FOOTER_NOT_DATA);
        mPresenter = new OrderHistoryPresenter();
        mPagePresenter = new PagePresenter();
    }

    private CommonAdapterType getAdapter() {
        mAdapter = new CommonAdapterType<BaseItemDto>(this);

        SentStateWrap mStateWrap = new SentStateWrap();
        mStateWrap.setOnWrapItemClickListener(this);
        mAdapter.addViewObtains(WrapConstants.WRAP_SEND_STATE, mStateWrap);

        OrderHostoryItemWrap mItemWrap = new OrderHostoryItemWrap();
        mItemWrap.setOnWrapItemClickListener(this);
        mAdapter.addViewObtains(WrapConstants.WRAP_SEND_ITEM, mItemWrap);

        OrderHostoryAddressWrap mAddressWrap = new OrderHostoryAddressWrap();
        mAddressWrap.setOnWrapItemClickListener(this);
        mAdapter.addViewObtains(WrapConstants.WRAP_MY_SPEND, mAddressWrap);

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
            List<OrderHistoryDto> mDto = (List<OrderHistoryDto>) obj;
            mPagePresenter.setPage(page);
            if (isRefrsh) {
                // 刷新
                mAdapter.clear();
                topicLv.onRefreshComplete();
                isRefrsh = false;
            }

            List<BaseItemDto> datas = parseData(mDto);
            if (page.getPagenum() == 1 && datas.size() == 0){
                netstate.show(NetStateView.NetState.EMPTY);
            }else {
                mAdapter.addList(datas);
                mAdapter.notifyDataSetChanged();
                netstate.show(NetStateView.NetState.CONTENT);
                if (!mPagePresenter.checkHasMore()) {
                    topicLv.setFooterView(ResultsListView.FOOTER_NOT_SHOW);
                }
            }
        }
    }

    private List<BaseItemDto> parseData( List<OrderHistoryDto>  mDto) {
        List<BaseItemDto> temp = new ArrayList<>();
        if (mDto != null){
            for (int i=0 ;i<mDto.size() ;i++){
                OrderHistoryDto mySpendDto = mDto.get(i);

                SendState order = mySpendDto.getOrder();
                List<BoxDoll> doll_list = mySpendDto.getDoll_list();
                AddressInfo address = mySpendDto.getAddress();

                order.setItemType(WrapConstants.WRAP_SEND_STATE);
                temp.add(order);


                for (int j=0 ;doll_list != null && j<doll_list.size();j++){
                    BoxDoll doll = doll_list.get(j);
                    doll.setItemType(WrapConstants.WRAP_SEND_ITEM);
                    temp.add(doll);
                }

                address.setItemType(WrapConstants.WRAP_SEND_ADDRESS);
                temp.add(address);
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
