package com.thermsx.localbuoys.ui.fragment.info;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thermsx.localbuoys.R;

public abstract class PlaceholderInfoFragment extends InfoFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_placeholder, container, false);
        String placeholderText = "placeholder " + getClass().getSimpleName();
        ((TextView) view.findViewById(R.id.placeholder_text)).setText(placeholderText);
        return view;
    }
}
