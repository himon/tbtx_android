package com.buddysoft.tbtx_android.ui.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.buddysoft.tbtx_android.R;
import com.buddysoft.tbtx_android.data.entity.AlbumDetailEntity;
import com.buddysoft.tbtx_android.ui.activity.album.AlbumDetailActivity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/18.
 */
public class AlbumDetailAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<AlbumDetailEntity.ItemsEntity> mDataList;
    private final LayoutInflater mInflater;

    public AlbumDetailAdapter(Context context, ArrayList<AlbumDetailEntity.ItemsEntity> dataList) {
        this.mContext = context;
        this.mDataList = dataList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.adapter_album_detail_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        AlbumDetailEntity.ItemsEntity itemsEntity = mDataList.get(position);
        Glide.with(mContext)
                .load(itemsEntity.getOriginal())
                .centerCrop()
                .placeholder(R.color.colorPrimary)
                .crossFade()
                .into(holder.imageView);

        return convertView;
    }

    static class ViewHolder {

        ImageView imageView;

        public ViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.iv_item);
        }
    }
}
