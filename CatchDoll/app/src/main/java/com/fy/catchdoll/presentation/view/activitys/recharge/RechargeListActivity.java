package com.fy.catchdoll.presentation.view.activitys.recharge;

import android.text.Html;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.fy.catchdoll.R;
import com.fy.catchdoll.library.widgets.NetStateView;
import com.fy.catchdoll.library.widgets.ResultsListView;
import com.fy.catchdoll.module.network.Page;
import com.fy.catchdoll.presentation.model.dto.base.BaseItemDto;
import com.fy.catchdoll.presentation.model.dto.box.AddressInfo;
import com.fy.catchdoll.presentation.model.dto.box.BoxDoll;
import com.fy.catchdoll.presentation.model.dto.box.BoxInfoDto;
import com.fy.catchdoll.presentation.model.dto.box.BoxOrder;
import com.fy.catchdoll.presentation.model.dto.recharge.RechargeItem;
import com.fy.catchdoll.presentation.presenter.ErrorCodeOperate;
import com.fy.catchdoll.presentation.presenter.IBasePresenterLinstener;
import com.fy.catchdoll.presentation.presenter.box.BoxInfoPresenter;
import com.fy.catchdoll.presentation.presenter.recharge.RechargeListPresenter;
import com.fy.catchdoll.presentation.view.activitys.base.AppCompatBaseActivity;
import com.fy.catchdoll.presentation.view.adapters.wrap.WrapConstants;
import com.fy.catchdoll.presentation.view.adapters.wrap.base.CommonAdapterType;
import com.fy.catchdoll.presentation.view.adapters.wrap.base.OnWrapItemClickListener;
import com.fy.catchdoll.presentation.view.adapters.wrap.box.BoxAddressWrap;
import com.fy.catchdoll.presentation.view.adapters.wrap.box.BoxDollWrap;
import com.fy.catchdoll.presentation.view.adapters.wrap.recharge.RechargeImgWrap;
import com.fy.catchdoll.presentation.view.adapters.wrap.recharge.RechargeItemWrap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wst on 2017/12/2.
 */
public class RechargeListActivity extends AppCompatBaseActivity  implements OnWrapItemClickListener, NetStateView.IRefreshListener, IBasePresenterLinstener {
    private ListView topicLv;
    private NetStateView netstate;
    private CommonAdapterType mAdapter;
    private RechargeListPresenter mInfoPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_recharge_list;
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
        setCommonTitle(getMString(R.string.string_recharge_title));
        topicLv.setAdapter(getAdapter());
        mInfoPresenter = new RechargeListPresenter();
    }

    private CommonAdapterType getAdapter() {
        mAdapter = new CommonAdapterType<BaseItemDto>(this);

        RechargeImgWrap mImgWrap = new RechargeImgWrap();
        mImgWrap.setOnWrapItemClickListener(this);
        mAdapter.addViewObtains(WrapConstants.WRAP_RECHARGE_IMG, mImgWrap);

        RechargeItemWrap mItemWrap = new RechargeItemWrap();
        mItemWrap.setOnWrapItemClickListener(this);
        mAdapter.addViewObtains(WrapConstants.WRAP_RECHARGE_ITEM, mItemWrap);
        return mAdapter;
    }

    @Override
    public void setListener() {
        netstate.setOnRefreshListener(this);
        mInfoPresenter.registPresenterCallBack(this);
    }

    @Override
    public void loadData() {
        netstate.show(NetStateView.NetState.LOADING);
        mInfoPresenter.firstTask();
    }

    @Override
    public void dataResult(Object obj, Page page, int status) {
        if (obj != null){
            List<RechargeItem> mDto = (List<RechargeItem>) obj;

            List<BaseItemDto> datas = parseData(mDto);
            mAdapter.addList(datas);
            mAdapter.notifyDataSetChanged();
            netstate.show(NetStateView.NetState.CONTENT);
        }
    }

    private List<BaseItemDto> parseData( List<RechargeItem> mDto) {
        List<BaseItemDto> temp = new ArrayList<>();
        if (mDto != null){
            for (int i=0;i<mDto.size();i++){
                RechargeItem rechargeItem = mDto.get(i);
                rechargeItem.setItemType(WrapConstants.WRAP_RECHARGE_ITEM);
                temp.add(rechargeItem);
            }
        }
        return temp;
    }


    @Override
    public void errerResult(int code, String msg) {
        ErrorCodeOperate.executeError("", getContext(), code, msg, true);
    }

    @Override
    public void onItemClick(View v, Object... obj) {

    }

    @Override
    public void onRefrsh(View view) {
        loadData();
    }
}
