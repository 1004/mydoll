package com.fy.catchdoll.presentation.model.business;

/**
 * 
 *<p>账号数据层的回掉</p><br/>
 *<p>TODO (类的详细的功能描述)</p>
 * @since 1.0.0
 * @author xky
 */
public interface IAccountBizCallBack extends BaseBizListener{
	public void callBackStats(int status);
}
