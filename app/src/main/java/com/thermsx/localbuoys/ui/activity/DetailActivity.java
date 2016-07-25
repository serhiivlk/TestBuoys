package com.thermsx.localbuoys.ui.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.socks.library.KLog;
import com.thermsx.localbuoys.R;
import com.thermsx.localbuoys.model.LocationNode;
import com.thermsx.localbuoys.provider.table.BrowseContract;
import com.thermsx.localbuoys.ui.fragment.info.InfoFragmentFactory;

import java.util.List;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String EXTRA_ITEM_ID =
            "com.thermsx.localbuoys.ITEM_ID";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        if (savedInstanceState == null) {
            if (getIntent() != null) {
                Bundle extras = getIntent().getExtras();
                if (extras != null && extras.containsKey(EXTRA_ITEM_ID)) {
                    getLoaderManager().initLoader(R.id.browse_id_loader, extras, this);
                }
            }
        }
    }

    private void updateTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        KLog.d();
        long itemId = bundle.getLong(EXTRA_ITEM_ID);
        return new CursorLoader(
                this,
                ContentUris.withAppendedId(BrowseContract.CONTENT_URI, itemId),
                null, null, null, null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        KLog.d();
        if (cursor != null && cursor.moveToFirst()) {
            final LocationNode node = BrowseContract.fromCursor(cursor);
            KLog.d("id " + node.getLocationId());
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    try {
                        List<Fragment> fragmentList = InfoFragmentFactory.create(node);
                        if (fragmentList.isEmpty()) {
                            return;
                        }
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        for (Fragment fragment : fragmentList) {
                            transaction.add(R.id.info_container, fragment);
                        }
                        transaction.commit();
                    } catch (Exception e) {
                        KLog.e(e);
                    }
                }
            });
            updateTitle(node.getName());

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item != null && item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
