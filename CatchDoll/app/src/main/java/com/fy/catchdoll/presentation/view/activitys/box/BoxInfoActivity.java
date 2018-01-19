package com.fy.catchdoll.presentation.view.activitys.box;

import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fy.catchdoll.R;
import com.fy.catchdoll.library.utils.ActivityUtils;
import com.fy.catchdoll.library.widgets.NetStateView;
import com.fy.catchdoll.library.widgets.ResultsListView;
import com.fy.catchdoll.library.widgets.dialog.DialogManager;
import com.fy.catchdoll.library.widgets.dialog.DialogStyle;
import com.fy.catchdoll.module.network.Page;
import com.fy.catchdoll.module.network.Paths;
import com.fy.catchdoll.module.support.recharge.RechargeManager;
import com.fy.catchdoll.module.support.recharge.RechargeNotifyManager;
import com.fy.catchdoll.module.support.recharge.common.dto.WxPayEntry;
import com.fy.catchdoll.presentation.model.dto.account.User;
import com.fy.catchdoll.presentation.model.dto.base.BaseItemDto;
import com.fy.catchdoll.presentation.model.dto.box.AddressInfo;
import com.fy.catchdoll.presentation.model.dto.box.BoxDoll;
import com.fy.catchdoll.presentation.model.dto.box.BoxInfoDto;
import com.fy.catchdoll.presentation.model.dto.box.BoxOrder;
import com.fy.catchdoll.presentation.model.dto.recharge.BoxOrderDto;
import com.fy.catchdoll.presentation.model.dto.recharge.OrderConfirmDto;
import com.fy.catchdoll.presentation.model.dto.recharge.OrderExpress;
import com.fy.catchdoll.presentation.presenter.ErrorCodeOperate;
import com.fy.catchdoll.presentation.presenter.IBasePresenterLinstener;
import com.fy.catchdoll.presentation.presenter.account.AccountManager;
import com.fy.catchdoll.presentation.presenter.box.BoxInfoPresenter;
import com.fy.catchdoll.presentation.presenter.recharge.BoxOrderPresenter;
import com.fy.catchdoll.presentation.presenter.recharge.CheckOrderPresenter;
import com.fy.catchdoll.presentation.view.activitys.base.AppCompatBaseActivity;
import com.fy.catchdoll.presentation.view.activitys.recharge.RechargeListActivity;
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
public class BoxInfoActivity extends AppCompatBaseActivity implements OnWrapItemClickListener, NetStateView.IRefreshListener, IBasePresenterLinstener, DialogManager.OnClickListenerContent, RechargeNotifyManager.OnPayStateListener {
    private static final String KEY = "BoxInfoActivity";
    private ListView topicLv;
    private NetStateView netstate;
    private CommonAdapterType mAdapter;
    private View mCommitContainer;
    private BoxInfoPresenter mInfoPresenter;
    private TextView mOrderMoneyInfo;
    private TextView mCommitOrder;
    private BoxOrderPresenter mOrderPresenter;
    private boolean isCommit = false;
    private DialogManager dialogManager;
    private WxPayEntry mCurrentEntry;
    private CheckOrderPresenter mCheckPresenter;
    private OrderExpress mExpress;
    private View mRightView;
    private ImageView mRightIcon;

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
        mRightView = findViewById(R.id.nav_right);
        mRightIcon = (ImageView) findViewById(R.id.nav_right_icon);
        mRightView.setVisibility(View.VISIBLE);


        netstate.setContentView(topicLv, mCommitContainer);
        netstate.show(NetStateView.NetState.LOADING);
    }

    @Override
    public void initData() {
        setCommonTitle(getMString(R.string.string_box_title));
        topicLv.setAdapter(getAdapter());
        mInfoPresenter = new BoxInfoPresenter();
        mOrderPresenter = new BoxOrderPresenter();
        dialogManager = new DialogManager(this);
        mCheckPresenter = new CheckOrderPresenter(Paths.CHECK_ORDER_DATA_BOX);
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
        mOrderMoneyInfo.setOnClickListener(this);
        mRightView.setOnClickListener(this);
        mOrderPresenter.registPresenterCallBack(mOrderCallBack);
        mCheckPresenter.registPresenterCallBack(mCheckOrderCallBack);
        RechargeNotifyManager.getInstance().registPayStateListener(KEY, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RechargeNotifyManager.getInstance().unRegistPayStateListener(KEY);
    }

    private IBasePresenterLinstener mOrderCallBack = new IBasePresenterLinstener() {
        @Override
        public void dataResult(Object obj, Page page, int status) {
            isCommit = false;
            dialogManager.dismissDialog();
            if (obj != null){
                BoxOrderDto dto = (BoxOrderDto) obj;
                operatePay(dto);
            }
        }

        @Override
        public void errerResult(int code, String msg) {
            isCommit = false;
            dialogManager.dismissDialog();
            ErrorCodeOperate.executeError("", getContext(), code, msg, true);
        }
    };

    private IBasePresenterLinstener mCheckOrderCallBack = new IBasePresenterLinstener() {
        @Override
        public void dataResult(Object obj, Page page, int status) {
            dialogManager.dismissDialog();
            if (obj != null){
                Toast.makeText(BoxInfoActivity.this,getMString(R.string.string_pay_state_success),Toast.LENGTH_SHORT).show();
                if (mExpress != null){
                    netstate.show(NetStateView.NetState.EMPTY);
                    dialogManager.showDialog(DialogStyle.BOX_ORDER_SUCCESS, BoxInfoActivity.this, mExpress.getSuccess_msg());
                }
            }
        }

        @Override
        public void errerResult(int code, String msg) {
            dialogManager.dismissDialog();
            ErrorCodeOperate.executeError("", getContext(), code, msg, true);
        }
    };

    private void operatePay(BoxOrderDto dto) {
        mExpress = dto.getExpress();
        mCurrentEntry = dto.getWx_pay();
        if (mExpress != null && mExpress.getIs_free_shipping() == 1){
            //包邮，无需支付
            netstate.show(NetStateView.NetState.EMPTY);
            dialogManager.showDialog(DialogStyle.BOX_ORDER_SUCCESS,this,mExpress.getSuccess_msg());
            return;
        }
        if (mExpress != null && mCurrentEntry != null && mExpress.getIs_free_shipping() == 0){
            // 0:需要支付邮费
            RechargeManager.getInstance().Pay(this,mCurrentEntry);
        }
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
            if (datas.size() == 0 || datas.size() == 1){
                netstate.show(NetStateView.NetState.EMPTY);
            }else {
                mAdapter.clear();
                mAdapter.addList(datas);
                mAdapter.notifyDataSetChanged();
                netstate.show(NetStateView.NetState.CONTENT);
            }
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
            String str = TextUtils.isEmpty(order.getPacket_mail_number_text()) ? "" : order.getPacket_mail_number_text();
            String s = "<font  color='#FFFFFF'>还需要支付邮费</font><font color='#FF0000'><big>" + order.getPrice()  + "</big></font><font  color='#FFFFFF'>元 "+str+"</font>";
            mCommitOrder.setText(Html.fromHtml(s));
        }
    }

    @Override
    public void errerResult(int code, String msg) {
        ErrorCodeOperate.executeError("", getContext(), code, msg, true);
    }

    @Override
    public void onItemClick(View v, Object... obj) {
        switch (v.getId()){
            case R.id.box_address_update:
                operateUpdataInfo(obj);
                break;
        }

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.order_commit_tv:
                operateCommitOrder();
                break;
            case R.id.nav_right:
                ActivityUtils.startSendHistoryActivity(this);
                break;
        }
    }

    private void operateCommitOrder() {
        if (isCommit){
            return;
        }
        isCommit = true;
        dialogManager.showDialog(DialogStyle.PAY_STATE,null,getMString(R.string.string_box_order_commit));
        mOrderPresenter.firstTask();
    }

    private void operateUpdataInfo(Object[] obj) {
        if(obj != null){
            AddressInfo info = (AddressInfo) obj[0];
            ActivityUtils.startUpdateAddressActivity(this,info);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UpdateAddressActivity.ADDRESS_REQUESTCODE && resultCode == UpdateAddressActivity.ADDRESS_RESULTCODE){
            loadData();
        }
    }





    @Override
    public void onRefrsh(View view) {
        loadData();
    }

    @Override
    public void onClick(View view, Object... content) {
        switch (view.getId()){
            case R.id.box_order_success:
                dialogManager.dismissDialog();
                ActivityUtils.startSendHistoryActivity(this);
//                Toast.makeText(this,"跳转到背包状态列表0",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onPayState(int state) {
        switch (state){
            case RechargeNotifyManager.RECHARGE_CANCEL_STATE:
                Toast.makeText(this,getMString(R.string.string_pay_state_cancel),Toast.LENGTH_SHORT).show();
                break;
            case RechargeNotifyManager.RECHARGE_ERROR_STATE:
                Toast.makeText(this,getMString(R.string.string_pay_state_error),Toast.LENGTH_SHORT).show();
                break;
            case RechargeNotifyManager.RECHARGE_SUCCESS_STATE:
                operateConfirmOrder();
                break;
        }
    }

    /**
     * 确认订单
     */
    private void operateConfirmOrder() {
        if (mCurrentEntry != null){
            dialogManager.showDialog(DialogStyle.PAY_STATE, null, getMString(R.string.string_pay_state_check));
            mCheckPresenter.firstTask(mCurrentEntry.getOrder_no());
        }
    }
}
