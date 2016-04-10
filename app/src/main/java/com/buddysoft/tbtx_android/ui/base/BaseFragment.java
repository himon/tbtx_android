package com.buddysoft.tbtx_android.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.buddysoft.tbtx_android.app.TbtxApplication;
import com.buddysoft.tbtx_android.app.component.AppProductionComponent;

/**
 * Created by lc on 16/4/10.
 */
public abstract class BaseFragment extends Fragment {

    protected View mRootView = null;

    protected abstract void setUpContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    protected abstract void setUpView();

    protected abstract void setUpData();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setupFragmentComponent();
        setUpContentView(inflater, container, savedInstanceState);
        setUpView();
        setUpData();
        return mRootView;
    }

    protected abstract void setupFragmentComponent();

    public AppProductionComponent getAppProductionComponent() {
        return TbtxApplication.get(getContext()).getAppProductionComponent();
    }


}
