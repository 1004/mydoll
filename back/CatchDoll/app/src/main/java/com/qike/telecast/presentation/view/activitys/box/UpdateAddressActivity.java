package com.qike.telecast.presentation.view.activitys.box;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.qike.telecast.R;
import com.qike.telecast.library.utils.Device;
import com.qike.telecast.module.network.Page;
import com.qike.telecast.presentation.model.dto.box.AddressInfo;
import com.qike.telecast.presentation.presenter.ErrorCodeOperate;
import com.qike.telecast.presentation.presenter.IBasePresenterLinstener;
import com.qike.telecast.presentation.presenter.box.UpdateInfoPresenter;
import com.qike.telecast.presentation.view.activitys.base.AppCompatBaseActivity;

import java.io.Serializable;

/**
 * Created by wst on 2017/12/2.
 */
public class UpdateAddressActivity extends AppCompatBaseActivity implements IBasePresenterLinstener {
    public static final String UPDATA_INFO_ADDRESS_KEY = "updata_info_address";
    public static final int ADDRESS_REQUESTCODE = 0x011;
    public static final int ADDRESS_RESULTCODE = 0x012;
    private EditText mEtName;
    private EditText mEtPhone;
    private EditText mAddress;
    private View mSaveTv;
    private UpdateInfoPresenter mPresenter;
    private AddressInfo mInfo;



    @Override
    public int getLayoutId() {
        return R.layout.activity_update_address;
    }

    @Override
    public void initView() {
        mEtName = (EditText) findViewById(R.id.update_recevier_et);
        mEtPhone = (EditText) findViewById(R.id.update_phone_et);
        mAddress = (EditText) findViewById(R.id.update_address_et);
        mSaveTv = findViewById(R.id.recharge_save_tv);
    }

    @Override
    public void initData() {
        setCommonTitle(getMString(R.string.string_update_title));
        mPresenter = new UpdateInfoPresenter();
        Intent intent = getIntent();
        if (intent != null){
            Serializable serializableExtra = intent.getSerializableExtra(UPDATA_INFO_ADDRESS_KEY);
            if (serializableExtra != null){
                mInfo = (AddressInfo) serializableExtra;
            }
        }
        updateView();
    }

    private void updateView(){
        if (mInfo != null){
            mEtName.setText(mInfo.getName());
            mEtPhone.setText(mInfo.getPhone());
            mAddress.setText(mInfo.getAddress());
        }
    }

    @Override
    public void setListener() {
        mSaveTv.setOnClickListener(this);
        mPresenter.registPresenterCallBack(this);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.recharge_save_tv:
                operateUpdatInfo();
                break;
        }
    }

    private void operateUpdatInfo() {
        String mName = mEtName.getText().toString();
        String mPhone = mEtPhone.getText().toString();
        String mAddress = this.mAddress.getText().toString();
        if (TextUtils.isEmpty(mName) || TextUtils.isEmpty(mPhone) || TextUtils.isEmpty(mAddress)) {
            Toast.makeText(this,"基本信息不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Device.isPhone(mPhone)) {
            Toast.makeText(this,"手机号格式不正确",Toast.LENGTH_SHORT).show();
            return;
        }

        mPresenter.firstTask(mName,mPhone,mAddress);


    }

    @Override
    public void dataResult(Object obj, Page page, int status) {
        Toast.makeText(this,"保存信息成功",Toast.LENGTH_SHORT).show();
        setResult(ADDRESS_RESULTCODE);
        finish();
    }

    @Override
    public void errerResult(int code, String msg) {
        ErrorCodeOperate.executeError("", this, code, msg, true);
    }
}
