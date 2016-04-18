package com.buddysoft.tbtx_android.ui.activity.album;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.buddysoft.tbtx_android.R;
import com.buddysoft.tbtx_android.app.C;
import com.buddysoft.tbtx_android.app.TbtxApplication;
import com.buddysoft.tbtx_android.data.entity.AlbumDetailEntity;
import com.buddysoft.tbtx_android.data.entity.AnnouncementEntity;
import com.buddysoft.tbtx_android.data.entity.BaseEntity;
import com.buddysoft.tbtx_android.data.entity.PhotoDetailCommentEntity;
import com.buddysoft.tbtx_android.data.entity.PhotoIsPraiseEntity;
import com.buddysoft.tbtx_android.data.entity.UserEntity;
import com.buddysoft.tbtx_android.ui.adapter.CommonAdapter;
import com.buddysoft.tbtx_android.ui.adapter.ViewHolder;
import com.buddysoft.tbtx_android.ui.base.ToolbarActivity;
import com.buddysoft.tbtx_android.ui.module.AlbumPhotoDetailActivityModule;
import com.buddysoft.tbtx_android.ui.presenter.AlbumPhotoDetailActivityPresenter;
import com.buddysoft.tbtx_android.ui.view.IAlbumPhotoDetailView;
import com.buddysoft.tbtx_android.util.ListViewHeight;
import com.buddysoft.tbtx_android.widgets.popup.AlbumPhotoDetailWindows;
import com.buddysoft.tbtx_android.widgets.popup.AlbumPhotoShowWindows;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AlbumPhotoDetailActivity extends ToolbarActivity implements IAlbumPhotoDetailView, View.OnClickListener {

    @Bind(R.id.iv_photo)
    ImageView mIvPhoto;
    @Bind(R.id.iv_praise)
    ImageView mIvPraise;
    @Bind(R.id.et_commit)
    EditText mEtCommit;
    @Bind(R.id.listview)
    ListView mListView;

    private AlbumDetailEntity.ItemsEntity mPhotoDetail;
    private BaseAdapter mBaseAdapter;
    private List<PhotoDetailCommentEntity.ItemsEntity> mList;
    private AlbumPhotoShowWindows mWindows;
    private AlbumPhotoDetailWindows mCommentWindows;

    @Inject
    AlbumPhotoDetailActivityPresenter mPresenter;

    private boolean isPraise = false;
    private UserEntity.ObjectEntity mUser;
    private String commentId = "";
    private PhotoDetailCommentEntity.ItemsEntity mCurrentDelitem;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_album_photo_detail, R.string.title_photo_detail, R.menu.menu_album_photo_detail, MODE_BACK);
        ButterKnife.bind(this);
    }

    @Override
    protected void setUpView() {
        mList = new ArrayList<>();
        mBaseAdapter = new CommonAdapter<PhotoDetailCommentEntity.ItemsEntity>(this, mList, R.layout.adapter_photo_comment_item) {
            @Override
            public void convert(ViewHolder helper, PhotoDetailCommentEntity.ItemsEntity item) {
                helper.setText(R.id.tv_name, item.getPublisher().getName());
                helper.setText(R.id.tv_comment, item.getContent());
                ImageView icon = helper.getView(R.id.iv_head_icon);
                Glide.with(icon.getContext())
                        .load(item.getPublisher().getAvatar())
                        .centerCrop()
                        .placeholder(R.color.colorPrimary)
                        .crossFade()
                        .into(icon);
            }
        };
        mListView.setAdapter(mBaseAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCurrentDelitem = (PhotoDetailCommentEntity.ItemsEntity) parent.getAdapter().getItem(position);
                if(mCurrentDelitem.getOperatorId().equals(mUser.getId())){
                    commentDelPopup(mCurrentDelitem.getId());
                }else{
                    mEtCommit.setHint("回复" + mCurrentDelitem.getPublisher().getName() + ":");
                    commentId = mCurrentDelitem.getOperatorId();
                }
            }
        });
        initEvent();
    }

    private void commentDelPopup(String commentId) {
        mCommentWindows = new AlbumPhotoDetailWindows(this, toolbar);
        mCommentWindows.setOperationInterface(new AlbumPhotoDetailWindows.OperationInterface() {
            @Override
            public void delete() {
                mPresenter.delComment(commentId);
            }
        });
    }

    private void initEvent() {
        mIvPraise.setOnClickListener(this);

        mEtCommit.setOnKeyListener(new View.OnKeyListener() {//输入完后按键盘上的搜索键【回车键改为了搜索键】

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    //修改回车键功能
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    //发表评论
                    commitReply(commentId);
                    commentId = "";
                }
                return false;
            }
        });
    }

    private void commitReply(String commentId) {
        mPresenter.commit(mPhotoDetail.getId(), mEtCommit.getText().toString(), commentId);
    }

    @Override
    protected void setUpData() {
        Intent intent = getIntent();
        if (intent != null) {
            mPhotoDetail = intent.getParcelableExtra(C.IntentKey.MESSAGE_EXTRA_KEY);
        }

        Glide.with(mIvPhoto.getContext())
                .load(mPhotoDetail.getOriginal())
                .centerCrop()
                .placeholder(R.color.colorPrimary)
                .crossFade()
                .into(mIvPhoto);
        mPresenter.isPraise(mPhotoDetail.getId());
        mPresenter.getCommentList(mPhotoDetail.getId());

        mUser = mPresenter.getRepositoriesManager().getUser().getObject();
    }

    @Override
    protected void setupActivityComponent() {
        TbtxApplication.get(this).getUserComponent().plus(new AlbumPhotoDetailActivityModule(this)).inject(this);
    }

    @Override
    public void setIsPraise(PhotoIsPraiseEntity photoIsPraiseEntity) {
        String praise = photoIsPraiseEntity.getObject().getIsPraise();
        if ("yes".equals(praise)) {
            isPraise = true;
        } else {
            isPraise = false;
        }
    }

    @Override
    public void setCanclePraise(BaseEntity entity) {
        isPraise = false;
    }

    @Override
    public void setPraise(BaseEntity entity) {
        isPraise = true;
    }

    @Override
    public void setCommentList(List<PhotoDetailCommentEntity.ItemsEntity> items) {
        mList.clear();
        if (items != null) {
            mList.addAll(items);
        }
        ListViewHeight.setListViewHeightBasedOnChildren(mListView);
        mBaseAdapter.notifyDataSetChanged();
    }

    @Override
    public void setDelCommentSuccess(BaseEntity entity) {
        mList.remove(mCurrentDelitem);
        ListViewHeight.setListViewHeightBasedOnChildren(mListView);
        mBaseAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_praise:
                if (isPraise) {
                    mPresenter.canclePraise(mPhotoDetail.getId());
                } else {
                    mPresenter.praise(mPhotoDetail.getId());
                }
                break;
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
        mWindows = new AlbumPhotoShowWindows(this, toolbar, 0);
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

            }
        });
    }
}
