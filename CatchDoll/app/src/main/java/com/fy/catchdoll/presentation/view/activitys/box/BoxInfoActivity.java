package com.fy.catchdoll.presentation.view.activitys.box;

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
import com.fy.catchdoll.presentation.presenter.ErrorCodeOperate;
import com.fy.catchdoll.presentation.presenter.IBasePresenterLinstener;
import com.fy.catchdoll.presentation.presenter.box.BoxInfoPresenter;
import com.fy.catchdoll.presentation.view.activitys.base.AppCompatBaseActivity;
import com.fy.catchdoll.presentation.view.adapters.wrap.WrapConstants;
import com.fy.catchdoll.presentation.view.adapters.wrap.base.CommonAdapterType;
import com.fy.catchdoll.presentation.view.adapters.wrap.base.OnWrapItemClickListener;
import com.fy.catchdoll.presentation.view.adapters.wrap.box.BoxAddressWrap;
import com.fy.catchdoll.presentation.view.adapters.wrap.box.BoxDollWrap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xky on 2017/11/28 0028.
 */
public class BoxInfoActivity extends AppCompatBaseActivity implements OnWrapItemClickListener, NetStateView.IRefreshListener, IBasePresenterLinstener {
    private ListView topicLv;
    private NetStateView netstate;
    private CommonAdapterType mAdapter;
    private View mCommitContainer;
    private BoxInfoPresenter mInfoPresenter;
    private TextView mOrderMoneyInfo;
    private TextView mCommitOrder;

    @Override
    public int getLayoutId() {
        return R.layout.activity_box_info;
    }

    @Override
    public void initView() {
        topicLv = (ResultsListView) findViewById(R.id.lv);
        netstate = (NetStateView) findViewById(R.id.netstate);
        mCommitContainer = findViewById(R.id.box_commit_container);
        mOrderMoneyInfo = (TextView) findViewById(R.id.order_commit_tv);
        mCommitOrder = (TextView) findViewById(R.id.order_money_info);

        netstate.setContentView(topicLv,mCommitContainer);
        netstate.show(NetStateView.NetState.LOADING);
    }

    @Override
    public void initData() {
        setCommonTitle(getMString(R.string.string_box_title));
        topicLv.setAdapter(getAdapter());
        mInfoPresenter = new BoxInfoPresenter();
    }

    private CommonAdapterType getAdapter() {
        mAdapter = new CommonAdapterType<BaseItemDto>(this);
        BoxDollWrap mDollWrap = new BoxDollWrap();
        mDollWrap.setOnWrapItemClickListener(this);
        mAdapter.addViewObtains(WrapConstants.WRAP_BOX_DOLL, mDollWrap);

        BoxAddressWrap mAddressWrap = new BoxAddressWrap();
        mAddressWrap.setOnWrapItemClickListener(this);
        mAdapter.addViewObtains(WrapConstants.WRAP_ADDRESS, mAddressWrap);
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
        if (obj != null && obj instanceof BoxInfoDto){
            BoxInfoDto mDto = (BoxInfoDto) obj;

            List<BaseItemDto> datas = parseData(mDto);
            mAdapter.addList(datas);
            mAdapter.notifyDataSetChanged();
            netstate.show(NetStateView.NetState.CONTENT);
        }
    }

    private List<BaseItemDto> parseData(BoxInfoDto mDto) {
        List<BaseItemDto> temp = new ArrayList<>();
        AddressInfo address = mDto.getAddress();
        List<BoxDoll> backpack_doll_list = mDto.getBackpack_doll_list();
        BoxOrder express = mDto.getExpress();
        initOrderInfo(express);
        if (backpack_doll_list != null){
            for (int i=0 ;i<backpack_doll_list.size() ;i++){
                BoxDoll doll = backpack_doll_list.get(i);
                doll.setItemType(WrapConstants.WRAP_BOX_DOLL);
                temp.add(doll);
            }
        }

        if (address != null){
            address.setItemType(WrapConstants.WRAP_ADDRESS);
            temp.add(address);
        }
        return temp;
    }

    private void initOrderInfo(BoxOrder order){
        if (order != null){
            String s = "<font  color='#FFFFFF'>还需要支付邮费</font><font color='#FF0000'><big>" + order.getPrice()  + "</big></font><font  color='#FFFFFF'>元</font>";
            mCommitOrder.setText(Html.fromHtml(s));
        }
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
