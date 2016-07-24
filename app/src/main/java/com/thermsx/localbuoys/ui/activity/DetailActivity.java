package com.thermsx.localbuoys.ui.activity;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.socks.library.KLog;
import com.thermsx.localbuoys.R;
import com.thermsx.localbuoys.model.Item;
import com.thermsx.localbuoys.provider.table.BrowseContract;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String EXTRA_ITEM_ID =
            "com.thermsx.localbuoys.ITEM_ID";

    private long mItemId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        Bundle extras = getIntent().getExtras();
        if (getIntent() != null && extras.containsKey(EXTRA_ITEM_ID)) {
            getLoaderManager().initLoader(R.id.item_loader, extras, this);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        long id = bundle.getLong(EXTRA_ITEM_ID);
        KLog.d("id: " + id);
        return new CursorLoader(
                this,
                ContentUris.withAppendedId(BrowseContract.CONTENT_URI, id),
                null, null, null, null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        KLog.d();
        if (cursor.moveToFirst()) {
            KLog.d("moveToFirst");
            final Item item = BrowseContract.fromCursor(cursor);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(item.getName());
            }
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    getFragmentManager().beginTransaction()
                            .add(R.id.info_container, PlaceholderFragment.newInstance(item.getName()))
                            .add(R.id.info_container, PlaceholderFragment
                                    .newInstance(item.getName() + " " + item.getType()))
                            .commit();
                }
            });
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        KLog.d();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item != null && item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public static class PlaceholderFragment extends Fragment {
        public static final String EXTRA_NAME = "name";

        public static PlaceholderFragment newInstance(String name) {

            Bundle args = new Bundle();
            args.putString(EXTRA_NAME, name);
            PlaceholderFragment fragment = new PlaceholderFragment();
            fragment.setArguments(args);
            return fragment;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            TextView textView = new TextView(getActivity());
            String name = getArguments().getString(EXTRA_NAME);
            textView.setText(name);
            return textView;
        }
    }
}
