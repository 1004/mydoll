package com.qike.telecast.presentation.presenter.account;

import com.qike.telecast.presentation.presenter.IBasePresenterLinstener;

/**
 * 
 *<p>账号逻辑层的回掉</p><br/>
 *<p>TODO (类的详细的功能描述)</p>
 * @since 1.0.0
 * @author xky
 */
public interface IAccountPresenterCallBack extends IBasePresenterLinstener{
	public void callBackStats(int status);
}
