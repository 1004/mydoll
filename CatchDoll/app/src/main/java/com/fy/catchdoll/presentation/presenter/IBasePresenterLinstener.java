package com.fy.catchdoll.presentation.presenter;


import com.fy.catchdoll.module.network.Page;

public interface IBasePresenterLinstener {
	public void dataResult(Object obj, Page page, int status);
	public void errerResult(int code, String msg);
}
