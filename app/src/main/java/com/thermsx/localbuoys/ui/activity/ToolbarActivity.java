package com.thermsx.localbuoys.ui.activity;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.thermsx.localbuoys.R;

public abstract class ToolbarActivity extends AppCompatActivity {
    private final FragmentManager.OnBackStackChangedListener mBackStackChangedListener = new FragmentManager.OnBackStackChangedListener() {
        @Override
        public void onBackStackChanged() {
            updateToolbar();
        }
    };
    private Toolbar mToolbar;

    @Override
    protected void onResume() {
        super.onResume();

        getFragmentManager().addOnBackStackChangedListener(mBackStackChangedListener);
    }

    @Override
    protected void onPause() {
        super.onPause();

        getFragmentManager().removeOnBackStackChangedListener(mBackStackChangedListener);
    }

    protected void updateToolbar() {
        boolean isRoot = getFragmentManager().getBackStackEntryCount() == 0;
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(!isRoot);
            getSupportActionBar().setDisplayHomeAsUpEnabled(!isRoot);
            getSupportActionBar().setHomeButtonEnabled(!isRoot);
        }
    }

    protected void initializeToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar == null) {
            throw new IllegalStateException("Layout is required to include a Toolbar with id " +
                    "'toolbar'");
        }

        setSupportActionBar(mToolbar);
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        mToolbar.setTitle(title);
    }

    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
        mToolbar.setTitle(titleId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item != null && item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
