package com.qike.telecast.presentation.presenter;


import com.qike.telecast.module.network.Page;

public interface IBasePresenterLinstener {
	public void dataResult(Object obj, Page page, int status);
	public void errerResult(int code, String msg);
}
