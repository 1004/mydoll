package com.fy.catchdoll.presentation.model.business;


import com.fy.catchdoll.module.network.Page;

public interface BaseBizListener {
	public void dataResult(Object obj, Page page, int status);
	public void errerResult(int code, String msg);

}
