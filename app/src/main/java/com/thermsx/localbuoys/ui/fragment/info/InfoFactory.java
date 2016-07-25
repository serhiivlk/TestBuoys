package com.thermsx.localbuoys.ui.fragment.info;

import android.support.v4.app.Fragment;

import com.socks.library.KLog;
import com.thermsx.localbuoys.model.Item;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class InfoFactory {
    private static String getPackageName() {

        Class<? extends InfoFactory> aClass = InfoFactory.class;
        return aClass.getPackage().getName();
    }

    public static List<Fragment> create(Item item) {
        List<Fragment> fragmentList = new ArrayList<>();
        Class<? extends Item> itemClass = item.getClass();
        KLog.d("fields");
        for (Field field : itemClass.getFields()) {
            KLog.d(field);
        }
        KLog.d("declaredFields");
        for (Field field : itemClass.getDeclaredFields()) {
            KLog.d(field);
        }
        return fragmentList;
    }
}
