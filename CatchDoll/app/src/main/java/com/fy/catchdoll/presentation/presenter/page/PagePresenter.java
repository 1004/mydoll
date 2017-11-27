package com.fy.catchdoll.presentation.presenter.page;

import com.fy.catchdoll.module.network.Page;

import java.util.List;

/**
 * Created by xky on 2017/11/27 0027.
 */
public class PagePresenter {
    private  int LIMIT = 10;
    private boolean isHasMore = false;
    private Page mPage;

    @Deprecated
    public void setPagesize(int pagesize) {
        this.LIMIT = pagesize;
    }

    @Deprecated
    public void initPageData(List pageData){
        if (pageData == null || pageData.size()<LIMIT){
            isHasMore = false;
        }else {
            isHasMore = true;
        }
    }

    public void setPage(Page page){
        mPage = page;
        if (mPage != null){
            isHasMore = mPage.getPagenum() < mPage.getPagecount();
        }else {
            isHasMore = false;
        }
    }

    public boolean checkHasMore(){
        return isHasMore;
    }
}
