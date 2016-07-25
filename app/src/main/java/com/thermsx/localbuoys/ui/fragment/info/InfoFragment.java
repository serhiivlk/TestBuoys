package com.thermsx.localbuoys.ui.fragment.info;

import android.app.Fragment;
import android.os.Bundle;

public abstract class InfoFragment extends Fragment {
    public static final String EXTRA_INFO_ITEM_ID =
            "com.thermsx.localbuoys.INFO_ITEM_ID";

    private long mLocationId;

    public static <T extends InfoFragment> InfoFragment newInstance(long itemId, Class<T> tClass)
            throws IllegalAccessException, java.lang.InstantiationException {
        Bundle args = new Bundle();
        args.putLong(EXTRA_INFO_ITEM_ID, itemId);
        InfoFragment fragment = tClass.newInstance();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(EXTRA_INFO_ITEM_ID)) {
            mLocationId = getArguments().getLong(EXTRA_INFO_ITEM_ID);
        }
    }

    protected long getLocationId() {
        return mLocationId;
    }
}

