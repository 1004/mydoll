package com.qike.telecast.module.network;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qike.telecast.library.utils.CommonConstant;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.CacheLoad;
import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.impl.AbstractGetDao;
import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.impl.ParamsContants;
import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.core.cache.CacheEntry;
import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.core.cache.CacheLoader;
import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.core.config.Configuration;
import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.core.thread.Task;
import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.core.thread.impl.HttpActionProxy;

/**
 * <p>dao封装类</p><br/>
 * <p>用于处理市场客户端特有数据格式</p>
 *
 * @param <T>
 * @author suenxianhao
 * @since 5.0.0
 */
public class BazaarGetDao<T> extends AbstractGetDao {

    private Class<T> clazz = null;
    private String mStrResult;
    private Map<String, Object> mParams = new HashMap<String, Object>();
    private CommonalityParams mCommonality = new CommonalityParams();
    private String mUrl;
    private ResultData<T> mData;

    private int mDataType;
    public static final int ARRAY_DATA = 0;
    public static final int ARRAY_DATA_CHUNK = 1;
    public static final int ARRAY_DATA_LOOP = 2;
    public static final int ARRAY_DATA_STATUS = 3;
    public static final int YESCODE = 1;
    private boolean isNext = false;
    private boolean isPre = true;
    private T mLoopData;
    private boolean isRefresh = false;
    private int mPagenumCount;



    private Gson gson = new Gson();

    public BazaarGetDao(String url, Class<T> clazz, int type) {
        super(url);
        mDataType = type;
        mData = new ResultData<T>();
        mUrl = url;
        this.clazz = clazz;
        //		mContext = context;
        if (mCacheLoader == null) {
            mCacheLoader = buildCacheLoader();
        }

    }

    /**
     * 这个构造函数只返回String
     *
     * @param url
     */
    public BazaarGetDao(String url) {
        super(url);
        mUrl = url;

    }

    @Override
    protected void _onTaskCancelled() {
        super._onTaskCancelled();
    }

    @Override
    protected void _onTaskFailed(Task.TaskFailed failed) {
        super._onTaskFailed(failed);
        nextPageError();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onDoInBackgroundProcess(HttpActionProxy action, HashMap<String, String> headers) {

        if (isServerDataError(headers)) {
            return;
        }
        CacheEntry entry = action.getCacheEntry();

        try {

            content = decodeResponse(entry.data);
            //			content = decodeResponse(action.getBytes());
        } catch (Throwable e) {
            e.printStackTrace();
            nextPageError();
            mResult.setCode(ParamsContants.ERROR_DECODE);
        }

        if (!TextUtils.isEmpty(content)) {
            JSONObject object = JSONObject.parseObject(content);
            mData = JSONObject.parseObject(content, ResultData.class);
            if (checkCorrentCode()) {

                try {

                    if (clazz != null && clazz.equals(String.class)) {
                        mStrResult = (String) content;
                        return;
                    }

                    isNext = true;

                    switch (mDataType) {
                        case ARRAY_DATA:
                            if (object.get("page") != null) {
                                mData.setPage(JSONObject.parseObject(object.get("page").toString(), Page.class));
                            }
                            if (object.get("data") != null && object.get("data") instanceof JSONArray) {
                                mData.setDataList(JSONArray.parseArray(object.get("data").toString(), clazz));
                            }
                            break;

                        case ARRAY_DATA_CHUNK:
                            if (object.get("data") != null && object.get("data") instanceof JSONObject) {
                                mData.setData(JSONObject.parseObject(object.get("data").toString(), clazz));
                            }
                            break;
                        case ARRAY_DATA_STATUS:
                            break;
                        case ARRAY_DATA_LOOP:
                            Gson gson = new Gson();
                            mLoopData = gson.fromJson(content, clazz);
                            break;
                        default:
                            break;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    nextPageError();
                    mResult.setCode(ParamsContants.ERROR_PARSE);
                }
            } else {
                nextPageError();
                if(mData != null){
                    if (mResult != null) {
                        mResult.setCode(ParamsContants.ERROR_SERVER);
                        mResult.setErrmsg(mData.getStatus().getMessage());
                    }
                }
            }
        } else {
            nextPageError();
            mResult.setCode(ParamsContants.ERROR_PARSE);
        }

    }

    private void nextPageError() {
        if (mParams.get(CommonConstant.PAGETYPE) != null) {
            if (CommonConstant.DOWN.equals(mParams.get(CommonConstant.PAGETYPE))) {
                if (mParams.get("down") != null) {
                    mParams.put("down", ((Integer) mParams.get("page")) - 1);
                    isPre = true;
                }
            } else {
                if (mParams.get("page") != null) {
                    if (isRefresh) {
                        mParams.put("page", mPagenumCount);
                    } else {
                        mParams.put("page", ((Integer) mParams.get("page")) - 1);

                    }
                    isNext = true;
                }
            }
        }

    }

    private void nextPageYes(){
        if (mParams.get(CommonConstant.PAGETYPE) != null) {
            if (CommonConstant.DOWN.equals(mParams.get(CommonConstant.PAGETYPE))) {
                isPre = true;
            } else {
                isNext = true;
            }
        }
    }

    /**
     * <p>刷新及第一次请求列表</p><br/>
     *
     * @author suenxianhao
     * @since 5.0.0
     */
    public void startTask() {
        mParams.put("page", 1);
        isRefresh = true;
        asyncDoAPI();
    }


    /**
     * 上啦加载首页
     */
    public void startUpTask(){
        mParams.put("page", 1);
        mParams.put(CommonConstant.PAGETYPE,CommonConstant.UP);
        isRefresh = true;
        asyncDoAPI();
    }

    /**
     * 上啦加载下页
     */
    public void nextUpTask(){
        if (isNext) {
            isRefresh = false;
            mPagenumCount = (Integer) mParams.get("page");
            mParams.put(CommonConstant.PAGETYPE,CommonConstant.UP);
            mParams.put("page", mPagenumCount + 1);
            asyncDoAPI();
            isNext = false;
        }
    }

    /**
     * 下拉加载分页
     */
    public void nextDownTask(){
        if (isPre) {
            if (!mParams.containsKey("down")){
                mParams.put("down", 1);
            }else {
                int downV = (Integer) mParams.get("down");
                mParams.put("down", downV + 1);
            }
            mParams.put(CommonConstant.PAGETYPE,CommonConstant.DOWN);
            asyncDoAPI();
            isPre = false;
        }
    }



    public boolean checkCorrentCode() {
        return mData.getStatus().getCode() == YESCODE;
    }

    /**
     * 请求单一数据
     */
    @Override
    public void asyncDoAPI() {
        mParams = mCommonality.initGeneralParams(mUrl, mParams);
        putAllParams(mParams);
        super.asyncDoAPI();
    }

    /**
     * <p>请求下一页列表数据</p><br/>
     *
     * @author suenxianhao
     * @since 5.0.0
     */
    public void nextTask() {
        if (isNext) {
            isRefresh = false;
            mPagenumCount = (Integer) mParams.get("page");
            mParams.put("page", mPagenumCount + 1);
            asyncDoAPI();
            isNext = false;
        }
    }

    public void putParams(String key, Object value) {
        mParams.put(key, value);
    }

    @Override
    public void putParams(String key, String value) {
        // TODO Auto-generated method stub
        mParams.put(key, value);
        super.putParams(key, value);
    }

    @Override
    public void putAllParams(Map paramsMap) {
        mParams.putAll(paramsMap);
        super.putAllParams(mParams);
    }

    public List<T> getList() {

        return mData.getDataList();

    }

    public T getLoopData() {
        return mLoopData;
    }

    public String getStringResult() {
        return mStrResult;
    }


    public T getData() {
        return mData.getData();
    }

    public ResultData<T> getmData() {
        return mData;
    }

    public int getCode() {
        return mData.getStatus().getCode();
    }


    public Page getPage() {
        return mData.getPage();
    }

    @Override
    protected CacheLoad buildCacheLoader() {
        Configuration config = Configuration.getConfiguration();
        CacheLoad cacheLoad = new CacheLoader(config.getDiskCache());
        return cacheLoad;
    }

}
