package com.buddysoft.tbtx_android.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.buddysoft.tbtx_android.R;
import com.buddysoft.tbtx_android.app.C;
import com.buddysoft.tbtx_android.data.entity.AdvertisementEntity;
import com.buddysoft.tbtx_android.data.entity.AnnouncementEntity;
import com.buddysoft.tbtx_android.ui.activity.MainActivity;
import com.buddysoft.tbtx_android.ui.activity.album.AlbumListActivity;
import com.buddysoft.tbtx_android.ui.activity.live.EZRealPlayActivity;
import com.buddysoft.tbtx_android.ui.activity.webview.WebViewActivity;
import com.buddysoft.tbtx_android.ui.adapter.CommonAdapter;
import com.buddysoft.tbtx_android.ui.adapter.ViewHolder;
import com.buddysoft.tbtx_android.ui.base.ToolbarFragment;
import com.buddysoft.tbtx_android.ui.module.HomeFragmentModule;
import com.buddysoft.tbtx_android.ui.presenter.HomeFragmentPresenter;
import com.buddysoft.tbtx_android.ui.view.IHomeView;
import com.buddysoft.tbtx_android.util.BannerUtils;
import com.buddysoft.tbtx_android.util.ListViewHeight;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends ToolbarFragment implements IHomeView, OnItemClickListener, View.OnClickListener {


    @Bind(R.id.convenientBanner)
    ConvenientBanner convenientBanner;
    @Bind(R.id.lv_list)
    ListView mListView;
    @Bind(R.id.btn_album_list)
    ImageButton mIbAlbum;
    @Bind(R.id.ib_video)
    ImageButton mIbVideo;

    @Inject
    HomeFragmentPresenter mPresenter;

    private List<String> mImgList;
    private List<AnnouncementEntity.ItemsEntity> mAnnoucementList;
    private BaseAdapter mBaseAdapter;
    private List<AdvertisementEntity.ItemsEntity> mAdvertiseList;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    protected void setUpContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_home, container,
                    false);
            ButterKnife.bind(this, mRootView);
        }

        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        setUpToolbar(R.string.title_home, R.menu.menu_home);
    }

    @Override
    protected void setUpView() {
        mAnnoucementList = new ArrayList<>();
        mImgList = new ArrayList<>();
        BannerUtils bannerUtils = new BannerUtils(getActivity(), convenientBanner, null, mImgList, this, 1);
        bannerUtils.init();
        convenientBanner.startTurning(2000);
        mBaseAdapter = new CommonAdapter<AnnouncementEntity.ItemsEntity>(getActivity(), mAnnoucementList, R.layout.layout_announcement_item) {
            @Override
            public void convert(ViewHolder helper, AnnouncementEntity.ItemsEntity item) {
                helper.setText(R.id.tv_title, item.getTitle());
                helper.setText(R.id.tv_time, item.getCreatedAt());
            }
        };
        mListView.setAdapter(mBaseAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AnnouncementEntity.ItemsEntity item = (AnnouncementEntity.ItemsEntity) parent.getAdapter().getItem(position);
                toShow(item.getId());
            }
        });

        convenientBanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                AdvertisementEntity.ItemsEntity itemsEntity = mAdvertiseList.get(position);
                toShowAdvert(itemsEntity.getId());
            }
        });

        initEvent();
    }

    private void toShowAdvert(String id) {
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        intent.putExtra(C.IntentKey.MESSAGE_EXTRA_KEY, "http://wsd-fe.bl99w.com/advertisement/app-view&id=" + id);
        startActivity(intent);
    }

    private void toShow(String id) {

        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        intent.putExtra(C.IntentKey.MESSAGE_EXTRA_KEY, "http://wsd-fe.bl99w.com/announcement/app-view&id=" + id);
        startActivity(intent);
    }

    private void initEvent() {
        mIbAlbum.setOnClickListener(this);
        mIbVideo.setOnClickListener(this);
    }

    @Override
    protected void setUpData() {
        mPresenter.getAdList();
        mPresenter.getAnnouncementList();
    }

    @Override
    protected void setupFragmentComponent() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).getMainActivityComponent().plus(new HomeFragmentModule(this)).inject(this);
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_map:
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra(C.IntentKey.MESSAGE_EXTRA_KEY, "http://wsd-be.bl99w.com/index.php?r=kindergarten/app-index");
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void setAdvertisement(List<AdvertisementEntity.ItemsEntity> items) {
        mImgList.clear();
        if (items != null) {
            mAdvertiseList = items;
            for (AdvertisementEntity.ItemsEntity item : items) {
                mImgList.add(item.getCover());
            }
        }
        convenientBanner.notifyDataSetChanged();
    }

    @Override
    public void setAnnouncement(List<AnnouncementEntity.ItemsEntity> items) {
        mAnnoucementList.clear();
        if (items != null) {
            mAnnoucementList.addAll(items);
        }
        ListViewHeight.setListViewHeightBasedOnChildren(mListView);
        mBaseAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_album_list:
                toAlbum();
                break;
            case R.id.ib_video:
                toVideo();
                break;
        }
    }

    private void toVideo() {
        Intent intent = new Intent(getActivity(), EZRealPlayActivity.class);
        startActivity(intent);
    }

    private void toAlbum() {
        Intent intent = new Intent(getActivity(), AlbumListActivity.class);
        startActivity(intent);
    }
}
