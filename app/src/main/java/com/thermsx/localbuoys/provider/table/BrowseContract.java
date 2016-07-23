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

import java.util.ArrayList;
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
        List<Item> items = getFromHierarchy(rootItem);
        List<ContentValues> values = new ArrayList<>(items.size());
        for (Item item : items) {
            values.add(toContentValues(item));
        }
        ContentValues[] valuesArray = values.toArray(new ContentValues[values.size()]);
        context.getContentResolver()
                .bulkInsert(CONTENT_URI, valuesArray);
    }

    private static List<Item> getFromHierarchy(Item rootItem) {
        List<Item> items = new ArrayList<>();
        if (rootItem.getItems() != null) {
            items.addAll(rootItem.getItems());
            for (Item item : rootItem.getItems()) {
                items.addAll(getFromHierarchy(item));
            }
        }
        return items;
    }

    public static boolean isRoot(Item item) {
        return item.getLocationId() == ROOT_ID;
    }

    public static Item fromCursor(Cursor cursor) {
        int locationIdIndex = cursor.getColumnIndex(Column.LOCATION_ID);
        int parentIdIndex = cursor.getColumnIndex(Column.PARENT_ID);
        int nameIndex = cursor.getColumnIndex(Column.NAME);
        int typeIndex = cursor.getColumnIndex(Column.ITEM_TYPE);
        int visibleBuoysIndex = cursor.getColumnIndex(Column.VISIBLE_ON_BUOYS);
        int visibleWeatherForecastIndex =
                cursor.getColumnIndex(Column.VISIBLE_ON_WEATHER_FORECAST);
        int visibleMarineForecastIndex =
                cursor.getColumnIndex(Column.VISIBLE_ON_MARINE_FORECAST);
        int visibleTidesIndex = cursor.getColumnIndex(Column.VISIBLE_ON_TIDES);
        int visibleMoonPhasesIndex = cursor.getColumnIndex(Column.VISIBLE_ON_MOON_PHASES);
        int visibleRadarIndex = cursor.getColumnIndex(Column.VISIBLE_ON_RADAR);
        int visibleWavewatchIndex = cursor.getColumnIndex(Column.VISIBLE_ON_WAVEWATCH);
        int visibleSurfaceTempIndex = cursor.getColumnIndex(Column.VISIBLE_ON_SEA_SURFACE_TEMP);
        Item item = new Item();
        item.setLocationId(cursor.getLong(locationIdIndex));
        item.setParentId(cursor.getLong(parentIdIndex));
        item.setType(cursor.getInt(typeIndex));
        item.setName(cursor.getString(nameIndex));
        item.setVisibleOnBuoys(cursor.getInt(visibleBuoysIndex) == 1);
        item.setVisibleOnWeatherForecast(cursor.getInt(visibleWeatherForecastIndex) == 1);
        item.setVisibleOnMarineForecast(cursor.getInt(visibleMarineForecastIndex) == 1);
        item.setVisibleOnTides(cursor.getInt(visibleTidesIndex) == 1);
        item.setVisibleOnMoonPhases(cursor.getInt(visibleMoonPhasesIndex) == 1);
        item.setVisibleOnRadar(cursor.getInt(visibleRadarIndex) == 1);
        item.setVisibleOnWavewatch(cursor.getInt(visibleWavewatchIndex) == 1);
        item.setVisibleOnSeaSurfaceTemp(cursor.getInt(visibleSurfaceTempIndex) == 1);
        return item;
    }

    public static ContentValues toContentValues(Item item) {
        ContentValues values = new ContentValues();
        values.put(Column.LOCATION_ID, item.getLocationId());
        values.put(Column.PARENT_ID, item.getParentId());
        values.put(Column.ITEM_TYPE, item.getType());
        values.put(Column.NAME, item.getName());
        values.put(Column.VISIBLE_ON_BUOYS, item.isVisibleOnBuoys() ? 1 : 0);
        values.put(Column.VISIBLE_ON_WEATHER_FORECAST, item.isVisibleOnWeatherForecast() ? 1 : 0);
        values.put(Column.VISIBLE_ON_MARINE_FORECAST, item.isVisibleOnMarineForecast() ? 1 : 0);
        values.put(Column.VISIBLE_ON_TIDES, item.isVisibleOnTides() ? 1 : 0);
        values.put(Column.VISIBLE_ON_MOON_PHASES, item.isVisibleOnMoonPhases() ? 1 : 0);
        values.put(Column.VISIBLE_ON_RADAR, item.isVisibleOnRadar() ? 1 : 0);
        values.put(Column.VISIBLE_ON_WAVEWATCH, item.isVisibleOnWavewatch() ? 1 : 0);
        values.put(Column.VISIBLE_ON_SEA_SURFACE_TEMP, item.isVisibleOnSeaSurfaceTemp() ? 1 : 0);
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
        String VISIBLE_ON_BUOYS = "visible_on_buoys";
        String VISIBLE_ON_WEATHER_FORECAST = "visible_on_weather_forecast";
        String VISIBLE_ON_MARINE_FORECAST = "visible_on_marine_forecast";
        String VISIBLE_ON_TIDES = "visible_on_tides";
        String VISIBLE_ON_MOON_PHASES = "visible_on_moon_phases";
        String VISIBLE_ON_RADAR = "visible_on_radar";
        String VISIBLE_ON_WAVEWATCH = "visible_on_wavewatch";
        String VISIBLE_ON_SEA_SURFACE_TEMP = "visible_on_sea_surface_temp";
    }

    public interface Request {
        String TABLE_NAME = "browse";

        String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
                Column.LOCATION_ID + " INTEGER PRIMARY KEY, " +
                Column.PARENT_ID + " INTEGER, " +
                Column.ITEM_TYPE + " INTEGER, " +
                Column.NAME + " TEXT," +
                Column.VISIBLE_ON_BUOYS + " INTEGER, " +
                Column.VISIBLE_ON_WEATHER_FORECAST + " INTEGER, " +
                Column.VISIBLE_ON_MARINE_FORECAST + " INTEGER, " +
                Column.VISIBLE_ON_TIDES + " INTEGER, " +
                Column.VISIBLE_ON_MOON_PHASES + " INTEGER, " +
                Column.VISIBLE_ON_RADAR + " INTEGER, " +
                Column.VISIBLE_ON_WAVEWATCH + " INTEGER, " +
                Column.VISIBLE_ON_SEA_SURFACE_TEMP + " INTEGER)";

        String TABLE_DROP = "DROP TABLE IF EXIST " + TABLE_NAME;
    }
}
