package com.thermsx.localbuoys.provider.table;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;

import com.thermsx.localbuoys.model.LocationNode;
import com.thermsx.localbuoys.provider.LocalBuoysProvider;

import java.util.ArrayList;
import java.util.List;


public abstract class BrowseContract {
    public static final long ROOT_ID = -1;

    public static final String PATH = "browse";
    public static final String PATH_BY_PARENT = "browse_by_parent";

    public static final Uri CONTENT_URI =
            LocalBuoysProvider.BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();
    public static final Uri CONTENT_URI_BY_PARENT_ID =
            LocalBuoysProvider.BASE_CONTENT_URI.buildUpon().appendPath(PATH_BY_PARENT).build();

    public static Uri insert(Context context, LocationNode node) {
        return context.getContentResolver().insert(CONTENT_URI, toContentValues(node));
    }

    public static void saveHierarchy(Context context, LocationNode rootNode) {
        List<LocationNode> locationNodes = getFromHierarchy(rootNode);
        List<ContentValues> values = new ArrayList<>(locationNodes.size());
        for (LocationNode locationNode : locationNodes) {
            values.add(toContentValues(locationNode));
        }
        ContentValues[] valuesArray = values.toArray(new ContentValues[values.size()]);
        context.getContentResolver()
                .bulkInsert(CONTENT_URI, valuesArray);
    }

    private static List<LocationNode> getFromHierarchy(LocationNode rootNode) {
        List<LocationNode> locationNodes = new ArrayList<>();
        if (rootNode.getLocationNodes() != null) {
            locationNodes.addAll(rootNode.getLocationNodes());
            for (LocationNode locationNode : rootNode.getLocationNodes()) {
                locationNodes.addAll(getFromHierarchy(locationNode));
            }
        }
        return locationNodes;
    }

    public static boolean isRoot(LocationNode locationNode) {
        return locationNode.getLocationId() == ROOT_ID;
    }

    public static LocationNode fromCursor(Cursor cursor) {
        int locationIdIndex = cursor.getColumnIndex(Column.LOCATION_ID);
        int parentIdIndex = cursor.getColumnIndex(Column.PARENT_ID);
        int nameIndex = cursor.getColumnIndex(Column.NAME);
        int typeIndex = cursor.getColumnIndex(Column.ITEM_TYPE);
        int buoysIndex = cursor.getColumnIndex(Column.VISIBLE_ON_BUOYS);
        int weatherForecastIndex = cursor.getColumnIndex(Column.VISIBLE_ON_WEATHER_FORECAST);
        int marineForecastIndex = cursor.getColumnIndex(Column.VISIBLE_ON_MARINE_FORECAST);
        int tidesIndex = cursor.getColumnIndex(Column.VISIBLE_ON_TIDES);
        int moonPhasesIndex = cursor.getColumnIndex(Column.VISIBLE_ON_MOON_PHASES);
        int radarIndex = cursor.getColumnIndex(Column.VISIBLE_ON_RADAR);
        int wavewatchIndex = cursor.getColumnIndex(Column.VISIBLE_ON_WAVEWATCH);
        int seaSurfaceTempIndex = cursor.getColumnIndex(Column.VISIBLE_ON_SEA_SURFACE_TEMP);
        LocationNode node = new LocationNode();
        node.setLocationId(cursor.getLong(locationIdIndex));
        node.setParentId(cursor.getLong(parentIdIndex));
        node.setType(cursor.getInt(typeIndex));
        node.setName(cursor.getString(nameIndex));
        node.setVisibleOnBuoys(cursor.getInt(buoysIndex) == 1);
        node.setVisibleOnWeatherForecast(cursor.getInt(weatherForecastIndex) == 1);
        node.setVisibleOnMarineForecast(cursor.getInt(marineForecastIndex) == 1);
        node.setVisibleOnTides(cursor.getInt(tidesIndex) == 1);
        node.setVisibleOnMoonPhases(cursor.getInt(moonPhasesIndex) == 1);
        node.setVisibleOnRadar(cursor.getInt(radarIndex) == 1);
        node.setVisibleOnWavewatch(cursor.getInt(wavewatchIndex) == 1);
        node.setVisibleOnSeaSurfaceTemp(cursor.getInt(seaSurfaceTempIndex) == 1);

        return node;
    }

    public static ContentValues toContentValues(LocationNode node) {
        ContentValues values = new ContentValues();
        values.put(Column.LOCATION_ID, node.getLocationId());
        values.put(Column.PARENT_ID, node.getParentId());
        values.put(Column.ITEM_TYPE, node.getType());
        values.put(Column.NAME, node.getName());
        values.put(Column.VISIBLE_ON_BUOYS, node.isVisibleOnBuoys() ? 1 : 0);
        values.put(Column.VISIBLE_ON_WEATHER_FORECAST, node.isVisibleOnWeatherForecast() ? 1 : 0);
        values.put(Column.VISIBLE_ON_MARINE_FORECAST, node.isVisibleOnMarineForecast() ? 1 : 0);
        values.put(Column.VISIBLE_ON_TIDES, node.isVisibleOnTides() ? 1 : 0);
        values.put(Column.VISIBLE_ON_MOON_PHASES, node.isVisibleOnMoonPhases() ? 1 : 0);
        values.put(Column.VISIBLE_ON_RADAR, node.isVisibleOnRadar() ? 1 : 0);
        values.put(Column.VISIBLE_ON_WAVEWATCH, node.isVisibleOnWavewatch() ? 1 : 0);
        values.put(Column.VISIBLE_ON_SEA_SURFACE_TEMP, node.isVisibleOnSeaSurfaceTemp() ? 1 : 0);
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
                Column.NAME + " TEXT, " +
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
