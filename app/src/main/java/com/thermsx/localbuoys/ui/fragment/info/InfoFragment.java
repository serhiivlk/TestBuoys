package com.thermsx.localbuoys.ui.fragment.info;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public abstract class InfoFragment extends Fragment {
    public static final String EXTRA_INFO_ITEM_ID =
            "com.thermsx.localbuoys.INFO_ITEM_ID";

    public static <T extends InfoFragment> InfoFragment newInstance(long itemId, Class<T> tClass)
            throws IllegalAccessException, java.lang.InstantiationException {
        Bundle args = new Bundle();
        args.putLong(EXTRA_INFO_ITEM_ID, itemId);
        InfoFragment fragment = tClass.newInstance();
        fragment.setArguments(args);
        return fragment;
    }

}

