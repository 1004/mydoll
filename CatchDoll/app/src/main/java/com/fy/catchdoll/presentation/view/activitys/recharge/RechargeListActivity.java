package com.fy.catchdoll.presentation.view.activitys.recharge;

import android.text.Html;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fy.catchdoll.R;
import com.fy.catchdoll.library.widgets.NetStateView;
import com.fy.catchdoll.library.widgets.ResultsListView;
import com.fy.catchdoll.library.widgets.dialog.DialogManager;
import com.fy.catchdoll.library.widgets.dialog.DialogStyle;
import com.fy.catchdoll.module.network.Page;
import com.fy.catchdoll.module.support.recharge.RechargeManager;
import com.fy.catchdoll.module.support.recharge.RechargeNotifyManager;
import com.fy.catchdoll.module.support.recharge.common.dto.BasePayEntry;
import com.fy.catchdoll.module.support.recharge.common.dto.WxPayEntry;
import com.fy.catchdoll.presentation.model.dto.account.User;
import com.fy.catchdoll.presentation.model.dto.base.BaseItemDto;
import com.fy.catchdoll.presentation.model.dto.box.AddressInfo;
import com.fy.catchdoll.presentation.model.dto.box.BoxDoll;
import com.fy.catchdoll.presentation.model.dto.box.BoxInfoDto;
import com.fy.catchdoll.presentation.model.dto.box.BoxOrder;
import com.fy.catchdoll.presentation.model.dto.doll.banner.BannerInfo;
import com.fy.catchdoll.presentation.model.dto.recharge.OrderConfirmDto;
import com.fy.catchdoll.presentation.model.dto.recharge.RechargeDto;
import com.fy.catchdoll.presentation.model.dto.recharge.RechargeItem;
import com.fy.catchdoll.presentation.presenter.ErrorCodeOperate;
import com.fy.catchdoll.presentation.presenter.IBasePresenterLinstener;
import com.fy.catchdoll.presentation.presenter.account.AccountManager;
import com.fy.catchdoll.presentation.presenter.box.BoxInfoPresenter;
import com.fy.catchdoll.presentation.presenter.recharge.CheckOrderPresenter;
import com.fy.catchdoll.presentation.presenter.recharge.RechargeListPresenter;
import com.fy.catchdoll.presentation.presenter.recharge.RechargeOrderPresenter;
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
public class RechargeListActivity extends AppCompatBaseActivity  implements OnWrapItemClickListener, NetStateView.IRefreshListener, IBasePresenterLinstener, RechargeNotifyManager.OnPayStateListener {
    private ListView topicLv;
    private NetStateView netstate;
    private CommonAdapterType mAdapter;
    private RechargeListPresenter mInfoPresenter;
    private RechargeOrderPresenter mOrderPresenter;
    private CheckOrderPresenter mCheckPresenter;
    private DialogManager mDialogManager;
    private final String KEY = "RechargeListActivity";
    private BasePayEntry mCurrentEntry;

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
        mOrderPresenter = new RechargeOrderPresenter();
        mCheckPresenter = new CheckOrderPresenter();
        mDialogManager = new DialogManager(this);
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
        mOrderPresenter.registPresenterCallBack(mOrderCallBack);
        mCheckPresenter.registPresenterCallBack(mCheckOrderCallBack);
        RechargeNotifyManager.getInstance().registPayStateListener(KEY, this);
    }


    @Override
    public void loadData() {
        netstate.show(NetStateView.NetState.LOADING);
        mInfoPresenter.firstTask();
    }

    @Override
    public void dataResult(Object obj, Page page, int status) {
        if (obj != null){
            RechargeDto mDto = (RechargeDto) obj;

            List<BaseItemDto> datas = parseData(mDto);
            mAdapter.addList(datas);
            mAdapter.notifyDataSetChanged();
            netstate.show(NetStateView.NetState.CONTENT);
        }
    }

    private List<BaseItemDto> parseData( RechargeDto mDto) {
        List<BaseItemDto> temp = new ArrayList<>();
        if (mDto != null){
            List<BannerInfo> banner = mDto.getBanner();
            if (banner != null && banner.size()>0){
                BannerInfo info = banner.get(0);
                info.setItemType(WrapConstants.WRAP_RECHARGE_IMG);
                temp.add(info);
            }

            List<RechargeItem> package_list = mDto.getPackage_list();

            for (int i=0;package_list != null && i<package_list.size();i++){
                RechargeItem rechargeItem = package_list.get(i);
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
        switch (v.getId()){
            case R.id.recharge_container:
                operateOrder(obj);
                break;
        }
    }

    private void operateOrder(Object[] obj) {
        if (obj == null){
            return;
        }
        RechargeItem item = (RechargeItem) obj[0];
        mDialogManager.showDialog(DialogStyle.PAY_STATE,null,getMString(R.string.string_pay_state_loading));
        mOrderPresenter.firstTask(item.getId());
    }

    private IBasePresenterLinstener mOrderCallBack = new IBasePresenterLinstener() {
        @Override
        public void dataResult(Object obj, Page page, int status) {
            if (obj != null){
                mCurrentEntry = (BasePayEntry) obj;
                mDialogManager.dismissDialog();
                RechargeManager.getInstance().Pay(RechargeListActivity.this,mCurrentEntry);
            }
        }

        @Override
        public void errerResult(int code, String msg) {
            mDialogManager.dismissDialog();
            ErrorCodeOperate.executeError("", getContext(), code, msg, true);
        }
    };

    private IBasePresenterLinstener mCheckOrderCallBack = new IBasePresenterLinstener() {
        @Override
        public void dataResult(Object obj, Page page, int status) {
            mDialogManager.dismissDialog();
            if (obj != null){
                OrderConfirmDto dto = (OrderConfirmDto) obj;
                User user = AccountManager.getInstance().getUser();
                if (user != null){
                    user.setGold(dto.getGold_number());
                }
                Toast.makeText(RechargeListActivity.this,getMString(R.string.string_pay_state_success),Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        @Override
        public void errerResult(int code, String msg) {
            mDialogManager.dismissDialog();
            ErrorCodeOperate.executeError("", getContext(), code, msg, true);
        }
    };

    @Override
    public void onRefrsh(View view) {
        loadData();
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
            mDialogManager.showDialog(DialogStyle.PAY_STATE,null,getMString(R.string.string_pay_state_check));
            mCheckPresenter.firstTask(mCurrentEntry.getOrder_no());
        }
    }
}
