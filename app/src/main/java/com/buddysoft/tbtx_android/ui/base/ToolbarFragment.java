package com.buddysoft.tbtx_android.ui.base;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.buddysoft.tbtx_android.R;

/**
 * Created by lc on 16/4/10.
 */
public abstract class ToolbarFragment extends BaseFragment implements Toolbar.OnMenuItemClickListener {

    protected Toolbar toolbar;
    protected TextView toolbar_title;

    protected void setUpToolbar(int titleResId, int menuId) {
        toolbar = (Toolbar) mRootView.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar_title = (TextView) mRootView.findViewById(R.id.toolbar_title);
        setUpTitle(titleResId);
        setUpMenu(menuId);
    }

    protected void setUpMenu(int menuId) {
        if (toolbar != null) {
            toolbar.getMenu().clear();
            if (menuId > 0) {
                toolbar.inflateMenu(menuId);
                toolbar.setOnMenuItemClickListener(this);
            }
        }
    }

    protected void setUpTitle(int titleResId) {
        if (titleResId > 0 && toolbar_title != null) {
            toolbar_title.setText(titleResId);
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}
