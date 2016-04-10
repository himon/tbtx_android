package com.buddysoft.tbtx_android.ui.presenter;


import com.buddysoft.tbtx_android.data.RepositoriesManager;
import com.buddysoft.tbtx_android.ui.view.IMainView;

/**
 * Created by Administrator on 2016/4/8.
 */
public class MainActivityPresenter {

    private IMainView mIMainView;
    private RepositoriesManager mRepositoriesManager;

    public MainActivityPresenter(IMainView iMainView, RepositoriesManager repositoriesManager) {
        this.mIMainView = iMainView;
        this.mRepositoriesManager = repositoriesManager;
    }
}
