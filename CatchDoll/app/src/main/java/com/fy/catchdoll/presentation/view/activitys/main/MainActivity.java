package com.fy.catchdoll.presentation.view.activitys.main;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.fy.catchdoll.R;
import com.fy.catchdoll.library.utils.ActivityUtils;
import com.fy.catchdoll.library.utils.ImageLoaderUtils;
import com.fy.catchdoll.library.widgets.NetStateView;
import com.fy.catchdoll.library.widgets.ResultsListView;
import com.fy.catchdoll.module.network.Page;
import com.fy.catchdoll.module.service.NetService;
import com.fy.catchdoll.presentation.model.dto.account.User;
import com.fy.catchdoll.presentation.model.dto.base.BaseItemDto;
import com.fy.catchdoll.presentation.model.dto.doll.DollMachine;
import com.fy.catchdoll.presentation.model.dto.doll.IndexDataDto;
import com.fy.catchdoll.presentation.model.dto.doll.WrapDollMachineInfo;
import com.fy.catchdoll.presentation.model.dto.doll.banner.BannerInfo;
import com.fy.catchdoll.presentation.model.dto.doll.banner.WrapBannerInfo;
import com.fy.catchdoll.presentation.presenter.ErrorCodeOperate;
import com.fy.catchdoll.presentation.presenter.IBasePresenterLinstener;
import com.fy.catchdoll.presentation.presenter.account.AccountManager;
import com.fy.catchdoll.presentation.presenter.index.IndexPresenter;
import com.fy.catchdoll.presentation.presenter.page.PagePresenter;
import com.fy.catchdoll.presentation.view.activitys.base.AppCompatBaseActivity;
import com.fy.catchdoll.presentation.view.adapters.wrap.WrapConstants;
import com.fy.catchdoll.presentation.view.adapters.wrap.base.CommonAdapterType;
import com.fy.catchdoll.presentation.view.adapters.wrap.base.OnWrapItemClickListener;
import com.fy.catchdoll.presentation.view.adapters.wrap.index.BannerWrap;
import com.fy.catchdoll.presentation.view.adapters.wrap.index.DollRoomWrap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xky on 2017/11/10 0010.
 */
public class MainActivity extends AppCompatBaseActivity implements OnWrapItemClickListener, NetStateView.IRefreshListener, ResultsListView.OnRefreshListener, IBasePresenterLinstener {
    private static final String TAG = "MainActivity";
    private ResultsListView topicLv;
    private NetStateView netstate;
    private boolean isRefrsh = false;
    private PagePresenter mPagePresenter;
    private IndexPresenter mPresenter;
    private CommonAdapterType mAdapter;
    private ImageView mIcon;
    private View myCenter;


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        topicLv = (ResultsListView) findViewById(R.id.lv);
        netstate = (NetStateView) findViewById(R.id.netstate);
        myCenter = findViewById(R.id.main_my_center);
        mIcon = (ImageView) findViewById(R.id.main_my_center);
        findViewById(R.id.main_my_box).setOnClickListener(this);

        netstate.setContentView(topicLv);
        netstate.show(NetStateView.NetState.LOADING);
    }

    @Override
    public void initData() {
        topicLv.setAdapter(getAdapter(), this);
        topicLv.setFooterView(ResultsListView.FOOTER_NOT_DATA);
        mPresenter = new IndexPresenter();
        mPagePresenter = new PagePresenter();

        setUserData();
        try{
            startService(new Intent(this, NetService.class));
        }catch (Throwable e){

        }
    }

    private void setUserData(){
        User user = AccountManager.getInstance().getUser();
        if (user != null){
            ImageLoaderUtils.displayImage(mIcon,R.drawable.drawable_default_color,user.getHeadimgurl());
        }
    }

    private CommonAdapterType getAdapter() {
        mAdapter = new CommonAdapterType<BaseItemDto>(this);

        BannerWrap mBannerWrap = new BannerWrap();
        mBannerWrap.setOnWrapItemClickListener(this);
        mAdapter.addViewObtains(WrapConstants.WRAP_INDEX_BANNER, mBannerWrap);

        DollRoomWrap mRoomWrap = new DollRoomWrap();
        mRoomWrap.setOnWrapItemClickListener(this);
        mAdapter.addViewObtains(WrapConstants.WRAP_INDEX_LIST,mRoomWrap);

        return mAdapter;
    }

    @Override
    public void setListener() {
        netstate.setOnRefreshListener(this);
        topicLv.setonRefreshListener(this);
        mPresenter.registPresenterCallBack(this);
        myCenter.setOnClickListener(this);
    }

    @Override
    public void onRefrsh(View view) {
        //点击刷新
        isRefrsh = true;
        loadData();
    }

    @Override
    protected int getBarColor() {
        return R.color.color_A2AFC9;
    }

    @Override
    protected boolean isDarkFont() {
        return false;
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
        switch (v.getId()) {
            case R.id.main_my_center:
                ActivityUtils.startMyActivity(this);
                break;
            case R.id.main_my_box:
                ActivityUtils.startBoxInfoActivity(this);
                break;
        }
    }

    @Override
    public void onItemClick(View v, Object... obj) {
        ActivityUtils.startDollRoomActivity(this,"");
    }

    @Override
    public void dataResult(Object obj, Page page, int status) {
        if (obj != null && obj instanceof IndexDataDto){
            IndexDataDto mDto = (IndexDataDto) obj;
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

    private List<BaseItemDto> parseData(IndexDataDto mDto) {
        List<BaseItemDto> temp = new ArrayList<>();
        List<BannerInfo> banner = mDto.getBanner();
        if (banner != null && banner.size()>0){
            WrapBannerInfo bannerInfo = new WrapBannerInfo();
            bannerInfo.setBanner(banner);
            bannerInfo.setItemType(WrapConstants.WRAP_INDEX_BANNER);
            temp.add(bannerInfo);
        }
        List<DollMachine> machine_list = mDto.getMachine_list();
        if (machine_list != null && machine_list.size()>0){
            for (int i= 0;i<machine_list.size() ;i++){
                if (i%2 == 0){
                    WrapDollMachineInfo info = new WrapDollMachineInfo();
                    List<DollMachine> data = new ArrayList<>();
                    info.setData(data);
                    info.setItemType(WrapConstants.WRAP_INDEX_LIST);
                    temp.add(info);
                }
                BaseItemDto baseItemDto = temp.get(temp.size() - 1);
                if (baseItemDto != null && baseItemDto instanceof WrapDollMachineInfo){
                    ((WrapDollMachineInfo) baseItemDto).getData().add(machine_list.get(i));
                }
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
