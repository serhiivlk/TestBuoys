package com.thermsx.localbuoys.provider.table;

import android.provider.BaseColumns;

public class BrowseTable {

    public interface Column {
        String ID = BaseColumns._ID;
        String NAME = "name";
    }

    public interface Request {
        String TABLE_NAME = "browse";

        String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
                Column.ID + " INTEGER PRIMARY KEY, " +
                Column.NAME + " TEXT)";

        String TABLE_DROP = "DROP TABLE IF EXIST " + TABLE_NAME;
    }
}
