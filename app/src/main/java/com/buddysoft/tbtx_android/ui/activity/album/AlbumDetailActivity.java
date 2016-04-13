package com.buddysoft.tbtx_android.ui.activity.album;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.buddysoft.tbtx_android.util.loader.GlideImageLoader;
import com.buddysoft.tbtx_android.util.loader.GlidePauseOnScrollListener;
import com.buddysoft.tbtx_android.widgets.popup.AlbumDetailWindows;
import com.buddysoft.tbtx_android.widgets.popup.SearchAlbumWindows;
import com.buddysoft.tbtx_android.widgets.pull.BaseViewHolder;
import com.buddysoft.tbtx_android.widgets.pull.layoutmanager.ILayoutManager;
import com.buddysoft.tbtx_android.widgets.pull.layoutmanager.MyGridLayoutManager;
import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.upyun.library.common.Params;
import com.upyun.library.common.UploadManager;
import com.upyun.library.listener.SignatureListener;
import com.upyun.library.listener.UpCompleteListener;
import com.upyun.library.listener.UpProgressListener;
import com.upyun.library.utils.UpYunUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.PauseOnScrollListener;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * 相册详情
 */
public class AlbumDetailActivity extends BaseListActivity<AlbumDetailEntity.ItemsEntity> implements IAlbumDetailView {

    private static final String TAG = "AlbumDetailActivity";
    private final int REQUEST_CODE_CAMERA = 1000;
    private final int REQUEST_CODE_GALLERY = 1001;

    private String mAlbumId;
    private String mAlbumName;
    private AlbumDetailWindows mWindows;
    private List<PhotoInfo> mPhotoList;
    private List<String> mUrl = new ArrayList<>();

    @Inject
    AlbumDetailActivityPresenter mPresenter;
    private FunctionConfig mFunctionConfig;

    @Override
    protected void setUpContentView() {
        super.setUpContentView();
        Intent intent = getIntent();
        if (intent != null) {
            mAlbumId = intent.getStringExtra(C.IntentKey.MESSAGE_EXTRA_KEY);
            mAlbumName = intent.getStringExtra(C.IntentKey.MESSAGE_EXTRA_KEY2);
            toolbar_title.setText(mAlbumName);
        }
        mPhotoList = new ArrayList<>();
        galleryInit();
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
        if (albumDetailEntity.getItems() != null) {
            mDataList.addAll(albumDetailEntity.getItems());
            adapter.notifyDataSetChanged();
        }
        recycler.onRefreshCompleted();
    }

    @Override
    public void setDel(BaseEntity entity) {
        finish();
    }

    @Override
    public void setUploadSuccess(BaseEntity baseEntity) {
        recycler.onRefresh();
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
        switch (item.getItemId()) {
            case R.id.menu_operate:
                popup();
                break;
        }
        return true;
    }

    private void popup() {
        mWindows = new AlbumDetailWindows(this, recycler);
        mWindows.setOperationInterface(new AlbumDetailWindows.OperationInterface() {

            @Override
            public void openAlbum() {
                mUrl.clear();
                mPhotoList.clear();
                GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, mFunctionConfig, mOnHanlderResultCallback);
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
                mUrl.clear();
                mPhotoList.clear();
                GalleryFinal.openCamera(REQUEST_CODE_CAMERA, mFunctionConfig, mOnHanlderResultCallback);
            }
        });
    }

    private void galleryInit() {
        //公共配置都可以在application中配置，这里只是为了代码演示而写在此处
        ThemeConfig themeConfig = ThemeConfig.DEFAULT;
        FunctionConfig.Builder functionConfigBuilder = new FunctionConfig.Builder();
        //设置图片加载方式
        ImageLoader imageLoader = new GlideImageLoader();
        PauseOnScrollListener pauseOnScrollListener = new GlidePauseOnScrollListener(false, true);
        //单选或多选
        int maxSize = 8;
        functionConfigBuilder.setMutiSelectMaxSize(maxSize);
        //裁剪宽高
        //functionConfigBuilder.setCropWidth(width);
        //functionConfigBuilder.setCropHeight(height);
        functionConfigBuilder.setEnableCrop(true);
        functionConfigBuilder.setEnableEdit(true);
        functionConfigBuilder.setEnablePreview(true);
        functionConfigBuilder.setEnableCamera(true);
        functionConfigBuilder.setEnableRotate(true);
        //强制裁剪
        //functionConfigBuilder.setForceCrop(true);
        //强制裁剪后可编辑
        //functionConfigBuilder.setForceCropEdit(true);
        functionConfigBuilder.setSelected(mPhotoList);//添加过滤集合
        mFunctionConfig = functionConfigBuilder.build();
        CoreConfig coreConfig = new CoreConfig.Builder(this, imageLoader, themeConfig)
                .setFunctionConfig(mFunctionConfig)
                .setPauseOnScrollListener(pauseOnScrollListener)
                .setNoAnimcation(false)
                .build();
        GalleryFinal.init(coreConfig);
    }

    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
                mPhotoList.addAll(resultList);
                upload();
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {

        }
    };

    public static String KEY = "eUydyi06Ka/BTZLo25VkLQWLBZU=";
    public static String SPACE = "wonderstar-img";
    String savePath = "/{year}/{mon}/album/" + UUID.randomUUID() + ".jpeg";

    public void upload() {
        final Map<String, Object> paramsMap = new HashMap<>();
        //上传空间
        paramsMap.put(Params.BUCKET, SPACE);
        //保存路径，任选其中一个
        paramsMap.put(Params.SAVE_KEY, savePath);
        //paramsMap.put(Params.PATH, savePath);
        //可选参数（详情见api文档介绍）
        paramsMap.put(Params.RETURN_URL, "httpbin.org/post");
        //进度回调，可为空
        UpProgressListener progressListener = new UpProgressListener() {
            @Override
            public void onRequestProgress(final long bytesWrite, final long contentLength) {
                Log.e(TAG, (100 * bytesWrite) / contentLength + "%");
            }
        };

        //结束回调，不可为空
        UpCompleteListener completeListener = new UpCompleteListener() {
            @Override
            public void onComplete(boolean isSuccess, String result) {
                Log.e(TAG, isSuccess + ":" + result);
                try {
                    JSONObject json = new JSONObject(result);
                    String url = json.getString("url");
                    mUrl.add("http://wonderstar-img.b0.upaiyun.com" + url);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(mUrl.size() > 0){
                    mPresenter.uploadPhoto(mAlbumId, mUrl);
                }
            }
        };

        SignatureListener signatureListener = new SignatureListener() {
            @Override
            public String getSignature(String raw) {
                return UpYunUtils.md5(raw + KEY);
            }
        };

        for (PhotoInfo item : mPhotoList) {
            File file = new File(item.getPhotoPath());
            UploadManager.getInstance().formUpload(file, paramsMap, signatureListener, completeListener, progressListener);
        }
    }
}
