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
import com.thermsx.localbuoys.provider.table.BrowseTable;

public class LocalBuoysProvider extends ContentProvider {

    public static final String CONTENT_AUTHORITY = "com.thermsx.localbuoys.LocalBuoysProvider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final int BROWSE = 368;
    private static final int BROWSE_ID = 661;

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(CONTENT_AUTHORITY, BrowseTable.PATH, BROWSE);
        URI_MATCHER.addURI(CONTENT_AUTHORITY, BrowseTable.PATH + "/#", BROWSE_ID);
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
                builder.setTables(BrowseTable.Request.TABLE_NAME);
                Cursor cursor = builder.query(mDatabaseHelper.getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            default:
                throw new IllegalArgumentException("Unknown query URI " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        KLog.d(uri);
        switch (URI_MATCHER.match(uri)) {
            case BROWSE:
                KLog.d("URI browse match");
                long rowId = mDatabaseHelper.getWritableDatabase().insert(BrowseTable.Request.TABLE_NAME, null, contentValues);
                if (rowId > 0) {
                    Uri resultUri = ContentUris.withAppendedId(uri, rowId);
                    getContext().getContentResolver().notifyChange(resultUri, null);
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
                String sql = "INSERT INTO " + BrowseTable.Request.TABLE_NAME + " VALUES (?,?,?);";
                SQLiteDatabase database = mDatabaseHelper.getWritableDatabase();
                SQLiteStatement statement = database.compileStatement(sql);
                database.beginTransaction();
                for (int i = 0; i < values.length; i++) {
                    statement.clearBindings();
                    statement.bindLong(1, i);
                    statement.bindLong(2, i);
                    statement.bindLong(3, i * i);
                    statement.execute();
                }
                database.setTransactionSuccessful();
                database.endTransaction();
        }
        return super.bulkInsert(uri, values);
    }


    @Override
    public int delete(@NonNull Uri uri, String whereClause, String[] whereArgs) {
        switch (URI_MATCHER.match(uri)) {
            case BROWSE:
                int count = mDatabaseHelper.getWritableDatabase().delete(BrowseTable.Request.TABLE_NAME, whereClause, whereArgs);
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
