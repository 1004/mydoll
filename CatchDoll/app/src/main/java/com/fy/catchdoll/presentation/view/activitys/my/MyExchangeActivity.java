package com.fy.catchdoll.presentation.view.activitys.my;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fy.catchdoll.R;
import com.fy.catchdoll.module.network.Page;
import com.fy.catchdoll.presentation.model.dto.my.MySentCodeDto;
import com.fy.catchdoll.presentation.presenter.ErrorCodeOperate;
import com.fy.catchdoll.presentation.presenter.IBasePresenterLinstener;
import com.fy.catchdoll.presentation.presenter.my.MySentCodePresenter;
import com.fy.catchdoll.presentation.view.activitys.base.AppCompatBaseActivity;

/**
 * Created by xky on 2017/11/28 0028.
 * 我的兑换码
 */
public class MyExchangeActivity extends AppCompatBaseActivity implements IBasePresenterLinstener {
    private EditText mEt;
    private View mExchangeBtn;
    private MySentCodePresenter mPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_invite_code;
    }

    @Override
    public void initView() {
        mEt = (EditText) findViewById(R.id.invide_code_et);
        mExchangeBtn = findViewById(R.id.invide_code_exchange);
    }

    @Override
    public void initData() {
        setCommonTitle(getMString(R.string.string_invite_code_title));
        mPresenter = new MySentCodePresenter();

    }

    @Override
    public void setListener() {
        mExchangeBtn.setOnClickListener(this);
        mPresenter.registPresenterCallBack(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.invide_code_exchange:
                operateExchange();
                break;
        }
    }

    private void operateExchange() {
        String code = mEt.getText().toString();
        if (TextUtils.isEmpty(code)) {
            Toast.makeText(this,"请输入兑换码",Toast.LENGTH_SHORT).show();
            return;
        }
        mPresenter.firstTask(code);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void dataResult(Object obj, Page page, int status) {
        if (obj != null){
            MySentCodeDto dto = (MySentCodeDto) obj;
            Toast.makeText(this, "兑换成功" + dto.getGold(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void errerResult(int code, String msg) {
        ErrorCodeOperate.executeError("", this, code, msg, true);
    }
}
