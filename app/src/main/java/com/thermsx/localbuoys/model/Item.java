package com.thermsx.localbuoys.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Item {
    public static final int TYPE_BROWSE = 0;
    public static final int TYPE_ITEM = 2;

    @SerializedName("LocationId")
    private long mLocationId;
    @SerializedName("ParentId")
    private long mParentId;
    @SerializedName("ItemType")
    private int mType;
    @SerializedName("Name")
    private String mName;
    @SerializedName("Items")
    private List<Item> mItems;

    public long getLocationId() {
        return mLocationId;
    }

    public void setLocationId(long locationId) {
        mLocationId = locationId;
    }

    public long getParentId() {
        return mParentId;
    }

    public void setParentId(long parentId) {
        mParentId = parentId;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public List<Item> getItems() {
        return mItems;
    }

    public void setItems(List<Item> items) {
        mItems = items;
    }
}
