package com.thermsx.localbuoys.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.database.sqlite.SQLiteStatement;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.socks.library.KLog;
import com.thermsx.localbuoys.provider.table.BrowseContract;

public class LocalBuoysProvider extends ContentProvider {

    public static final String CONTENT_AUTHORITY = "com.thermsx.localbuoys.LocalBuoysProvider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final int BROWSE = 368;
    private static final int BROWSE_ID = 661;
    private static final int BROWSE_BY_PARENT = 365;

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(CONTENT_AUTHORITY, BrowseContract.PATH, BROWSE);
        URI_MATCHER.addURI(CONTENT_AUTHORITY, BrowseContract.PATH + "/#", BROWSE_ID);
        URI_MATCHER.addURI(CONTENT_AUTHORITY, BrowseContract.PATH_BY_PARENT + "/*", BROWSE_BY_PARENT);
    }

    private DatabaseHelper mDatabaseHelper;

    @Override
    public boolean onCreate() {
        mDatabaseHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        KLog.d(uri);
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        switch (URI_MATCHER.match(uri)) {
            case BROWSE:
                KLog.d("URI browse match");
                builder.setTables(BrowseContract.Request.TABLE_NAME);
                break;
            case BROWSE_ID: {
                long id = ContentUris.parseId(uri);
                KLog.d("URI browse id " + id + " match");
                builder.setTables(BrowseContract.Request.TABLE_NAME);
                builder.appendWhere(BrowseContract.Column.LOCATION_ID + " = " + id);
                break;
            }
            case BROWSE_BY_PARENT:
                String id = BrowseContract.getParentId(uri).get(1);
                KLog.d("URI browse by parent id " + id);
                builder.setTables(BrowseContract.Request.TABLE_NAME);
                builder.appendWhere(BrowseContract.Column.PARENT_ID + " = " + id);
                break;
            default:
                throw new IllegalArgumentException("Unknown query URI " + uri);
        }
        Cursor cursor = builder.query(mDatabaseHelper.getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
//        KLog.d(uri);
        switch (URI_MATCHER.match(uri)) {
            case BROWSE:
//                KLog.d("URI browse match");
                long rowId = mDatabaseHelper.getWritableDatabase().insert(BrowseContract.Request.TABLE_NAME, null, contentValues);
                if (rowId > 0) {
                    Uri resultUri = ContentUris.withAppendedId(uri, rowId);
                    getContext().getContentResolver().notifyChange(uri, null);
                    getContext().getContentResolver().notifyChange(BrowseContract.CONTENT_URI_BY_PARENT_ID, null);
                    return resultUri;
                } else {
                    throw new SQLException("Failed to add a record into " + uri);
                }
            default:
                throw new IllegalArgumentException("Unknown query URI " + uri);
        }
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        switch (URI_MATCHER.match(uri)) {
            case BROWSE:
                int count = 0;
                String query = "INSERT INTO " +
                        BrowseContract.Request.TABLE_NAME + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
                SQLiteDatabase database = mDatabaseHelper.getWritableDatabase();
                database.beginTransaction();
                database.delete(BrowseContract.Request.TABLE_NAME, null, null);
                SQLiteStatement statement = database.compileStatement(query);
                for (ContentValues value : values) {
                    count++;
                    statement.clearBindings();
                    statement.bindLong(1, value.getAsLong(BrowseContract.Column.LOCATION_ID));
                    statement.bindLong(2, value.getAsLong(BrowseContract.Column.PARENT_ID));
                    statement.bindLong(3, value.getAsInteger(BrowseContract.Column.ITEM_TYPE));
                    statement.bindString(4, value.getAsString(BrowseContract.Column.NAME));
                    statement.bindLong(5, value.getAsInteger(BrowseContract.Column.VISIBLE_ON_BUOYS));
                    statement.bindLong(6, value.getAsInteger(BrowseContract.Column.VISIBLE_ON_WEATHER_FORECAST));
                    statement.bindLong(7, value.getAsInteger(BrowseContract.Column.VISIBLE_ON_MARINE_FORECAST));
                    statement.bindLong(8, value.getAsInteger(BrowseContract.Column.VISIBLE_ON_TIDES));
                    statement.bindLong(9, value.getAsInteger(BrowseContract.Column.VISIBLE_ON_MOON_PHASES));
                    statement.bindLong(10, value.getAsInteger(BrowseContract.Column.VISIBLE_ON_RADAR));
                    statement.bindLong(11, value.getAsInteger(BrowseContract.Column.VISIBLE_ON_WAVEWATCH));
                    statement.bindLong(12, value.getAsInteger(BrowseContract.Column.VISIBLE_ON_SEA_SURFACE_TEMP));
                    statement.execute();
                }
                database.setTransactionSuccessful();
                database.endTransaction();

                getContext().getContentResolver().notifyChange(uri, null);
                getContext().getContentResolver().notifyChange(BrowseContract.CONTENT_URI_BY_PARENT_ID, null);
                return count;
            default:
                return super.bulkInsert(uri, values);
        }
    }


    @Override
    public int delete(@NonNull Uri uri, String whereClause, String[] whereArgs) {
        switch (URI_MATCHER.match(uri)) {
            case BROWSE:
                int count = mDatabaseHelper.getWritableDatabase().delete(BrowseContract.Request.TABLE_NAME, whereClause, whereArgs);
                getContext().getContentResolver().notifyChange(uri, null);
                return count;
            default:
                throw new IllegalArgumentException("Unknown query URI " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
