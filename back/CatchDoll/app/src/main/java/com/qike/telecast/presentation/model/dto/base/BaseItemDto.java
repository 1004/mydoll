package com.qike.telecast.presentation.model.dto.base;

/**
 * <p>最基本的item</p><br/>
 * <p>含有类型</p>
 *
 * @author xky
 * @since 1.0.0
 */
public class BaseItemDto extends BaseDto {

    /**
     * TODO(用一句话描述这个变量表示什么)
     */
    private static final long serialVersionUID = -7353798426390112621L;
    //item类型
    private int itemType;
    //头部标题
    private String headerTitle;


    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }


}
