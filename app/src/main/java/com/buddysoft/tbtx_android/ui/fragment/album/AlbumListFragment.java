package com.buddysoft.tbtx_android.ui.fragment.album;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.buddysoft.tbtx_android.R;
import com.buddysoft.tbtx_android.app.C;
import com.buddysoft.tbtx_android.data.entity.AlbumEntity;
import com.buddysoft.tbtx_android.ui.activity.album.AlbumDetailActivity;
import com.buddysoft.tbtx_android.ui.adapter.CommonAdapter;
import com.buddysoft.tbtx_android.ui.adapter.ViewHolder;
import com.buddysoft.tbtx_android.ui.base.BaseFragment;
import com.buddysoft.tbtx_android.ui.base.BaseListFragment;
import com.buddysoft.tbtx_android.widgets.pull.BaseViewHolder;
import com.buddysoft.tbtx_android.widgets.pull.layoutmanager.ILayoutManager;
import com.buddysoft.tbtx_android.widgets.pull.layoutmanager.MyGridLayoutManager;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumListFragment extends BaseFragment {

    @Bind(R.id.gv_common)
    GridView mGvCommont;

    private List<AlbumEntity.ItemsEntity> mAlbumList;
    private BaseAdapter mBaseAdapter;
    private String mType;

    @Override
    protected void setUpContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_album_list, container, false);
            ButterKnife.bind(this, mRootView);
            mAlbumList = new ArrayList<>();
        }

        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
    }

    @Override
    protected void setUpView() {
        mBaseAdapter = new CommonAdapter<AlbumEntity.ItemsEntity>(getActivity(), mAlbumList, R.layout.layout_album_list_item) {
            @Override
            public void convert(ViewHolder helper, AlbumEntity.ItemsEntity item) {
                helper.setText(R.id.tv_album_name, item.getName());
                helper.setText(R.id.tv_album_num, item.getPhotoCount());
                helper.setText(R.id.tv_time, item.getCreatedAt());
                helper.setImageByUrl(R.id.img_bg, item.getCover());
            }
        };
        mGvCommont.setAdapter(mBaseAdapter);

        initEvent();
    }

    private void initEvent() {
        mGvCommont.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlbumEntity.ItemsEntity entity = (AlbumEntity.ItemsEntity) parent.getAdapter().getItem(position);
                toAlbumDetail(entity.getId(), entity.getName());
            }
        });
    }

    private void toAlbumDetail(String id, String name) {
        Intent intent = new Intent(getActivity(), AlbumDetailActivity.class);
        intent.putExtra(C.IntentKey.MESSAGE_EXTRA_KEY, id);
        intent.putExtra(C.IntentKey.MESSAGE_EXTRA_KEY2, name);
        startActivity(intent);
    }

    @Override
    protected void setUpData() {

    }

    @Override
    protected void setupFragmentComponent() {

    }

    public void refresh(ArrayList<AlbumEntity.ItemsEntity> items, String type) {
        mType = type;
        setData(items);
    }

    private void setData(List<AlbumEntity.ItemsEntity> albums) {
        mAlbumList.clear();
        if (albums != null) {
            if (mType.equals(C.AlbumType.KINDERGARTEN)) {
                for (AlbumEntity.ItemsEntity album : albums) {
                    if (album.getClassId() == null || album.getClassId().equals("0")) {
                        mAlbumList.add(album);
                    }
                }
            } else {
                for (AlbumEntity.ItemsEntity album : albums) {
                    if (album.getClassId() != null && !album.getClassId().equals("0")) {
                        mAlbumList.add(album);
                    }
                }
            }
        }
        if (mBaseAdapter != null) {
            mBaseAdapter.notifyDataSetChanged();
        }
    }


}
