package com.buddysoft.tbtx_android.ui.activity.album;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.MenuItem;

import com.buddysoft.tbtx_android.R;
import com.buddysoft.tbtx_android.app.C;
import com.buddysoft.tbtx_android.app.TbtxApplication;
import com.buddysoft.tbtx_android.data.entity.AlbumDetailEntity;
import com.buddysoft.tbtx_android.data.entity.BaseEntity;
import com.buddysoft.tbtx_android.data.event.AlbumPhotoEvent;
import com.buddysoft.tbtx_android.ui.base.ToolbarActivity;
import com.buddysoft.tbtx_android.ui.fragment.PhotoShowFragment;
import com.buddysoft.tbtx_android.ui.module.PhotoShowActivityModule;
import com.buddysoft.tbtx_android.ui.presenter.PhotoShowActivityPresenter;
import com.buddysoft.tbtx_android.ui.view.IPhotoShowView;
import com.buddysoft.tbtx_android.widgets.ViewPagerFixed;
import com.buddysoft.tbtx_android.widgets.popup.AlbumPhotoShowWindows;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public class PhotoShowActivity extends ToolbarActivity implements IPhotoShowView {

    @Bind(R.id.viewpager)
    ViewPagerFixed mViewPager;
    private ArrayList<AlbumDetailEntity.ItemsEntity> mList;
    private List<Fragment> mFragments;
    private FragmentPagerAdapter mAdapter;
    private int mIndex;
    private AlbumPhotoShowWindows mWindows;

    @Inject
    PhotoShowActivityPresenter mPresenter;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_photo_show, R.string.title_empty, R.menu.menu_photo_show, MODE_BACK);
        ButterKnife.bind(this);
    }

    @Override
    protected void setUpView() {
        mFragments = new ArrayList<>();

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        };

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                toolbar_title.setText((position + 1) + "/" + mList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void setUpData() {
        Intent intent = getIntent();
        if (intent != null) {
            mList = intent.getParcelableArrayListExtra(C.IntentKey.MESSAGE_EXTRA_KEY);
            mIndex = intent.getIntExtra(C.IntentKey.MESSAGE_EXTRA_KEY2, 0);
            String albumName = intent.getStringExtra(C.IntentKey.MESSAGE_EXTRA_KEY3);
            toolbar.setTitle(albumName);
        }
        initFragment();
    }

    @Override
    protected void setupActivityComponent() {
        TbtxApplication.get(this).getUserComponent().plus(new PhotoShowActivityModule(this)).inject(this);
    }

    private void initFragment() {
        for (int i = 0; i < mList.size(); i++) {
            AlbumDetailEntity.ItemsEntity itemsEntity = mList.get(i);
            PhotoShowFragment fragment = new PhotoShowFragment();
            Bundle data = new Bundle();
            data.putParcelable(C.IntentKey.MESSAGE_EXTRA_KEY, itemsEntity);
            fragment.setArguments(data);
            mFragments.add(fragment);
        }
        mViewPager.setAdapter(mAdapter);
        // 设置预加载数
        //mViewPager.setOffscreenPageLimit(mFragments.size());
        mViewPager.setCurrentItem(mIndex);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_operate:
                popup();
                break;
        }
        return true;
    }

    private void popup() {
        mWindows = new AlbumPhotoShowWindows(this, toolbar, 1);
        mWindows.setOperationInterface(new AlbumPhotoShowWindows.OperationInterface() {


            @Override
            public void shareWechat() {

            }

            @Override
            public void shareWechatCircle() {

            }

            @Override
            public void save() {

            }

            @Override
            public void delete() {
                AlbumDetailEntity.ItemsEntity entity = mList.get(mIndex);
                mPresenter.delete(entity.getId());
            }
        });
    }

    @Override
    public void setDelSuccess(BaseEntity entity) {
        EventBus.getDefault().post(new AlbumPhotoEvent("refresh"));
        finish();
    }
}
