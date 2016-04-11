package com.buddysoft.tbtx_android.ui.activity.album;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.buddysoft.tbtx_android.R;
import com.buddysoft.tbtx_android.app.C;
import com.buddysoft.tbtx_android.app.TbtxApplication;
import com.buddysoft.tbtx_android.data.entity.AlbumEntity;
import com.buddysoft.tbtx_android.data.event.AlbumSearchEvent;
import com.buddysoft.tbtx_android.ui.base.ToolbarActivity;
import com.buddysoft.tbtx_android.ui.fragment.album.AlbumListFragment;
import com.buddysoft.tbtx_android.ui.module.AlbumListActivityModule;
import com.buddysoft.tbtx_android.ui.presenter.AlbumListActivityPresenter;
import com.buddysoft.tbtx_android.ui.view.IAlbumListView;
import com.buddysoft.tbtx_android.widgets.popup.SearchAlbumWindows;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;


public class AlbumListActivity extends ToolbarActivity implements IAlbumListView, RadioGroup.OnCheckedChangeListener {

    @Bind(R.id.fl_album_main)
    FrameLayout flAlbumMain;
    @Bind(R.id.rg_album)
    RadioGroup mRgAlbum;
    @Bind(R.id.rb_class_album)
    RadioButton mRbClassAlbum;
    @Bind(R.id.rb_kindergarten_album)
    RadioButton mRbKindergartenAlbum;
    @Bind(R.id.sw_refresh)
    SwipeRefreshLayout mSwRefresh;

    @Inject
    AlbumListActivityPresenter mPresenter;

    private FragmentManager mFragmentManager;
    private AlbumListFragment mClassAlbumFragment, mKindergartenFragment;
    private SearchAlbumWindows mSearchAlbumPopup;
    private List<String> mAlbumList;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_album_list, R.string.title_album, R.menu.menu_album, MODE_BACK);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        mFragmentManager = getSupportFragmentManager();
    }

    @Override
    protected void setUpView() {
        mAlbumList = new ArrayList<>();

        mSwRefresh.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mSwRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwRefresh.setRefreshing(true);
                        mPresenter.getAlbumList("", "");
                    }
                }, 3000);
            }
        });
        mRgAlbum.setOnCheckedChangeListener(this);
        mRbClassAlbum.setChecked(true);
    }

    @Override
    protected void setUpData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void setupActivityComponent() {
        TbtxApplication.get(this).getUserComponent().plus(new AlbumListActivityModule(this)).inject(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction fragmentTran = mFragmentManager
                .beginTransaction();
        hideFragments(fragmentTran);
        switch (checkedId) {
            case R.id.rb_class_album:
                if (mClassAlbumFragment == null) {
                    mClassAlbumFragment = new AlbumListFragment();
                    fragmentTran.add(R.id.fl_album_main, mClassAlbumFragment);
                }
                fragmentTran.show(mClassAlbumFragment);
                break;
            case R.id.rb_kindergarten_album:
                if (mKindergartenFragment == null) {
                    mKindergartenFragment = new AlbumListFragment();
                    fragmentTran.add(R.id.fl_album_main, mKindergartenFragment);
                }
                fragmentTran.show(mKindergartenFragment);
                break;
        }
        fragmentTran.commitAllowingStateLoss();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (mKindergartenFragment != null) {
            transaction.hide(mKindergartenFragment);
        }
        if (mClassAlbumFragment != null) {
            transaction.hide(mClassAlbumFragment);
        }
    }

    @Override
    public void setAlbumList(ArrayList<AlbumEntity.ItemsEntity> items) {
        mSwRefresh.setRefreshing(false);
        mClassAlbumFragment.refresh(items, C.AlbumType.CLASS);
        mKindergartenFragment.refresh(items, C.AlbumType.KINDERGARTEN);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                seach();
                break;
        }
        return true;
    }

    private void seach() {
        mSearchAlbumPopup = new SearchAlbumWindows(this, mSwRefresh);
        mSearchAlbumPopup.setOperationInterface(new SearchAlbumWindows.OperationInterface() {
            @Override
            public void searchTime(String time) {
                //waittingDialog();
                time = time.substring(0, 10);
                mPresenter.getAlbumList("", time);
            }

            @Override
            public void searchName() {
                Intent intent = new Intent(AlbumListActivity.this, AlbumSearchByNameActivity.class);
                startActivity(intent);
            }


        });
    }

    public void onEvent(AlbumSearchEvent event) {
        //waittingDialog();
        mPresenter.getAlbumList(event.getMsg(), "");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }
}