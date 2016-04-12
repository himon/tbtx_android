package com.buddysoft.tbtx_android.ui.activity.album;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.buddysoft.tbtx_android.R;
import com.buddysoft.tbtx_android.app.C;
import com.buddysoft.tbtx_android.app.TbtxApplication;
import com.buddysoft.tbtx_android.data.entity.AlbumDetailEntity;
import com.buddysoft.tbtx_android.data.entity.BaseEntity;
import com.buddysoft.tbtx_android.ui.base.BaseListActivity;
import com.buddysoft.tbtx_android.ui.base.ToolbarActivity;
import com.buddysoft.tbtx_android.ui.module.AlbumDetailActivityModule;
import com.buddysoft.tbtx_android.ui.presenter.AlbumDetailActivityPresenter;
import com.buddysoft.tbtx_android.ui.view.IAlbumDetailView;
import com.buddysoft.tbtx_android.widgets.popup.AlbumDetailWindows;
import com.buddysoft.tbtx_android.widgets.popup.SearchAlbumWindows;
import com.buddysoft.tbtx_android.widgets.pull.BaseViewHolder;
import com.buddysoft.tbtx_android.widgets.pull.layoutmanager.ILayoutManager;
import com.buddysoft.tbtx_android.widgets.pull.layoutmanager.MyGridLayoutManager;
import com.bumptech.glide.Glide;

import javax.inject.Inject;

/**
 * 相册详情
 */
public class AlbumDetailActivity extends BaseListActivity<AlbumDetailEntity.ItemsEntity> implements IAlbumDetailView {

    private String mAlbumId;
    private String mAlbumName;
    private AlbumDetailWindows mWindows;

    @Inject
    AlbumDetailActivityPresenter mPresenter;

    @Override
    protected void setUpContentView() {
        super.setUpContentView();
        Intent intent = getIntent();
        if (intent != null) {
            mAlbumId = intent.getStringExtra(C.IntentKey.MESSAGE_EXTRA_KEY);
            mAlbumName = intent.getStringExtra(C.IntentKey.MESSAGE_EXTRA_KEY2);
            toolbar_title.setText(mAlbumName);
        }
    }

    @Override
    protected void setUpData() {
        super.setUpData();
        recycler.setRefreshing();
    }

    @Override
    protected void setUpMenu(int menuId) {
        super.setUpMenu(R.menu.menu_album_detail);
    }

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_album_detail_item, parent, false);
        return new SampleViewHolder(view);
    }

    @Override
    protected ILayoutManager getLayoutManager() {
        return new MyGridLayoutManager(getApplicationContext(), 3);
    }

    @Override
    protected RecyclerView.ItemDecoration getItemDecoration() {
        return null;
    }

    @Override
    protected void setupActivityComponent() {
        TbtxApplication.get(this).getUserComponent().plus(new AlbumDetailActivityModule(this)).inject(this);
    }

    @Override
    public void onRefresh(int action) {
        mPresenter.getAlbumPhoto(mAlbumId);
    }

    @Override
    public void setDetail(AlbumDetailEntity albumDetailEntity) {
        if(albumDetailEntity.getItems() != null){
            mDataList.addAll(albumDetailEntity.getItems());
            adapter.notifyDataSetChanged();
        }
        recycler.onRefreshCompleted();
    }

    @Override
    public void setDel(BaseEntity entity) {
        finish();
    }

    class SampleViewHolder extends BaseViewHolder {

        ImageView mIvItem;

        public SampleViewHolder(View itemView) {
            super(itemView);
            mIvItem = (ImageView) itemView.findViewById(R.id.iv_item);
        }

        @Override
        public void onBindViewHolder(int position) {
            AlbumDetailEntity.ItemsEntity itemsEntity = mDataList.get(position);

            Glide.with(mIvItem.getContext())
                    .load(itemsEntity.getOriginal())
                    .centerCrop()
                    .placeholder(R.color.colorPrimary)
                    .crossFade()
                    .into(mIvItem);
        }

        @Override
        public void onItemClick(View view, int position) {

        }

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_operate:
                popup();
                break;
        }
        return true;
    }

    private void popup() {
        mWindows = new AlbumDetailWindows(this, recycler);
        mWindows.setOperationInterface(new AlbumDetailWindows.OperationInterface(){

            @Override
            public void openAlbum() {

            }

            @Override
            public void delAlbum() {
                mPresenter.delAlbum(mAlbumId);
            }

            @Override
            public void editAlbum() {
                Intent intent = new Intent(AlbumDetailActivity.this, EditAlbumActivity.class);
                intent.putExtra(C.IntentKey.MESSAGE_EXTRA_KEY, mAlbumId);
                intent.putExtra(C.IntentKey.MESSAGE_EXTRA_KEY2, mAlbumName);
                startActivity(intent);
            }

            @Override
            public void photoGraph() {

            }
        });
    }
}
