package com.thermsx.localbuoys.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Item {
    public static final int TYPE_BROWSE = 0;
    public static final int TYPE_ITEM = 2;

    @SerializedName("ItemType")
    private int mType;
    @SerializedName("Name")
    private String mName;
    @SerializedName("Items")
    private List<Item> mItems;

    public int getType() {
        return mType;
    }

    public String getName() {
        return mName;
    }

    public List<Item> getItems() {
        return mItems;
    }
}
