package com.qike.telecast.presentation.model.business;


import com.qike.telecast.module.network.Page;

public interface BaseBizListener {
	public void dataResult(Object obj, Page page, int status);
	public void errerResult(int code, String msg);

}
