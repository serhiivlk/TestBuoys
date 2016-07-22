package com.thermsx.localbuoys.provider.table;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import com.thermsx.localbuoys.model.Item;
import com.thermsx.localbuoys.provider.LocalBuoysProvider;


public class BrowseTable {

    public static final String PATH = "browse";
    public static final String PATH_BY_PARENT = "browse_by_parent_id";
    public static final Uri CONTENT_URI =
            LocalBuoysProvider.BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();
    public static final Uri CONTENT_URI_BY_PARENT_ID =
            LocalBuoysProvider.BASE_CONTENT_URI.buildUpon().appendPath(PATH_BY_PARENT).build();

    public static Uri insert(Context context, Item item) {
        return context.getContentResolver().insert(CONTENT_URI, toContentValues(item));
    }

    public static int clean(Context context) {
        return context.getContentResolver().delete(CONTENT_URI, null, null);
    }

    public static Item fromCursor(Cursor cursor) {
        int locationIdIndex = cursor.getColumnIndex(Column.LOCATION_ID);
        int parentIdIndex = cursor.getColumnIndex(Column.PARENT_ID);
        int nameIndex = cursor.getColumnIndex(Column.NAME);
        Item item = new Item();
        item.setLocationId(cursor.getLong(locationIdIndex));
        item.setParentId(cursor.getLong(parentIdIndex));
        item.setName(cursor.getString(nameIndex));
        return item;
    }

    public static ContentValues toContentValues(Item item) {
        ContentValues values = new ContentValues();
        values.put(Column.LOCATION_ID, item.getLocationId());
        values.put(Column.PARENT_ID, item.getParentId());
        values.put(Column.NAME, item.getName());
        return values;
    }

    public interface Column {
        String LOCATION_ID = BaseColumns._ID;
        String PARENT_ID = "parent_id";
        String NAME = "name";
    }

    public interface Request {
        String TABLE_NAME = "browse";

        String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
                Column.LOCATION_ID + " INTEGER PRIMARY KEY, " +
                Column.PARENT_ID + " INTEGER, " +
                Column.NAME + " TEXT)";

        String TABLE_DROP = "DROP TABLE IF EXIST " + TABLE_NAME;
    }
}
