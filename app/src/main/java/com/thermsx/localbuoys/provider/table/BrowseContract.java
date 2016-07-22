package com.thermsx.localbuoys.provider.table;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;

import com.thermsx.localbuoys.model.Item;
import com.thermsx.localbuoys.provider.LocalBuoysProvider;

import java.util.List;


public class BrowseContract {
    public static final long ROOT_ID = -1;

    public static final String PATH = "browse";
    public static final String PATH_BY_PARENT = "browse_by_parent";

    public static final Uri CONTENT_URI =
            LocalBuoysProvider.BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();
    public static final Uri CONTENT_URI_BY_PARENT_ID =
            LocalBuoysProvider.BASE_CONTENT_URI.buildUpon().appendPath(PATH_BY_PARENT).build();

    public static Uri insert(Context context, Item item) {
        return context.getContentResolver().insert(CONTENT_URI, toContentValues(item));
    }

    public static void saveHierarchy(Context context, Item rootItem) {
        if (!isRoot(rootItem)) {
            insert(context, rootItem);
        }
        if (rootItem.getItems() != null) {
            for (Item item : rootItem.getItems()) {
                saveHierarchy(context, item);
            }
        }
    }

    public static boolean isRoot(Item item) {
        return item.getLocationId() == ROOT_ID;
    }

    public static int clean(Context context) {
        return context.getContentResolver().delete(CONTENT_URI, null, null);
    }

    public static Item fromCursor(Cursor cursor) {
        int locationIdIndex = cursor.getColumnIndex(Column.LOCATION_ID);
        int parentIdIndex = cursor.getColumnIndex(Column.PARENT_ID);
        int nameIndex = cursor.getColumnIndex(Column.NAME);
        int typeIndex = cursor.getColumnIndex(Column.ITEM_TYPE);
        Item item = new Item();
        item.setLocationId(cursor.getLong(locationIdIndex));
        item.setParentId(cursor.getLong(parentIdIndex));
        item.setType(cursor.getInt(typeIndex));
        item.setName(cursor.getString(nameIndex));
        return item;
    }

    public static ContentValues toContentValues(Item item) {
        ContentValues values = new ContentValues();
        values.put(Column.LOCATION_ID, item.getLocationId());
        values.put(Column.PARENT_ID, item.getParentId());
        values.put(Column.ITEM_TYPE, item.getType());
        values.put(Column.NAME, item.getName());
        return values;
    }

    public static Uri buildUriWithParentId(long itemId) {
        return ContentUris.withAppendedId(CONTENT_URI_BY_PARENT_ID, itemId);
    }

    public static List<String> getParentId(@NonNull Uri uri) {
        return uri.getPathSegments();
    }

    public interface Column {
        String LOCATION_ID = BaseColumns._ID;
        String PARENT_ID = "parent_id";
        String NAME = "name";
        String ITEM_TYPE = "item_type";
    }

    public interface Request {
        String TABLE_NAME = "browse";

        String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
                Column.LOCATION_ID + " INTEGER PRIMARY KEY, " +
                Column.PARENT_ID + " INTEGER, " +
                Column.ITEM_TYPE + " INTEGER, " +
                Column.NAME + " TEXT)";

        String TABLE_DROP = "DROP TABLE IF EXIST " + TABLE_NAME;
    }
}
