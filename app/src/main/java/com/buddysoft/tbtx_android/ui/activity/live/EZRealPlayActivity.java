/* 
 * @ProjectName VideoGo
 * @Copyright HangZhou Hikvision System Technology Co.,Ltd. All Right Reserved
 * 
 * @FileName RealPlayActivity.java
 * @Description 这里对文件进行描述
 * 
 * @author chenxingyf1
 * @data 2014-6-11
 * 
 * @note 这里写本文件的详细功能描述和注释
 * @note 历史记录
 * 
 * @warning 这里写本文件的相关警告
 */
package com.buddysoft.tbtx_android.ui.activity.live;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.buddysoft.tbtx_android.R;
import com.buddysoft.tbtx_android.app.C;
import com.buddysoft.tbtx_android.app.TbtxApplication;
import com.buddysoft.tbtx_android.data.entity.CameraEntity;
import com.buddysoft.tbtx_android.data.entity.UserEntity;
import com.buddysoft.tbtx_android.ui.base.ToolbarActivity;
import com.buddysoft.tbtx_android.ui.module.EZRealPlayActivityModule;
import com.buddysoft.tbtx_android.ui.presenter.EZRealPlayActivityPresenter;
import com.buddysoft.tbtx_android.ui.view.IEZRealPlayView;
import com.buddysoft.tbtx_android.widgets.popup.VedioWindows;
import com.buddysoft.tbtx_android.videogo.AudioPlayUtil;
import com.buddysoft.tbtx_android.videogo.EZUtils;
import com.buddysoft.tbtx_android.videogo.LoadingTextView;
import com.buddysoft.tbtx_android.videogo.OpenYSService;
import com.buddysoft.tbtx_android.videogo.RealPlaySquareInfo;
import com.buddysoft.tbtx_android.videogo.SecureValidate;
import com.buddysoft.tbtx_android.videogo.WaitDialog;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.hik.streamconvert.StreamConvertCB;
import com.videogo.constant.Config;
import com.videogo.constant.Constant;
import com.videogo.constant.IntentConsts;
import com.videogo.exception.BaseException;
import com.videogo.exception.CASClientSDKException;
import com.videogo.exception.ErrorCode;
import com.videogo.exception.HCNetSDKException;
import com.videogo.exception.InnerException;
import com.videogo.exception.RtspClientException;
import com.videogo.exception.TTSClientSDKException;
import com.videogo.openapi.EZConstants;
import com.videogo.openapi.EZConstants.EZPTZAction;
import com.videogo.openapi.EZConstants.EZPTZCommand;
import com.videogo.openapi.EZConstants.EZRealPlayConstants;
import com.videogo.openapi.EZConstants.EZVideoLevel;
import com.videogo.openapi.EZOpenSDK;
import com.videogo.openapi.EZPlayer;
import com.videogo.openapi.bean.EZCameraInfo;
import com.videogo.openapi.bean.EZDeviceInfo;
import com.videogo.realplay.RealPlayStatus;
import com.videogo.util.ConnectionDetector;
import com.videogo.util.LocalInfo;
import com.videogo.util.LogUtil;
import com.videogo.util.MediaScanner;
import com.videogo.util.RotateViewUtil;
import com.videogo.util.SDCardUtil;
import com.videogo.util.Utils;
import com.videogo.widget.CheckTextButton;
import com.videogo.widget.CustomRect;
import com.videogo.widget.CustomTouchListener;
import com.videogo.widget.RingView;
import com.videogo.widget.TitleBar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

/**
 * 实时预览2.7
 *
 * @author xiaxingsuo
 * @data 2015-11-11
 */
public class EZRealPlayActivity extends ToolbarActivity implements IEZRealPlayView, OnClickListener, SurfaceHolder.Callback,
        Handler.Callback, OnTouchListener, SecureValidate.SecureValidateListener, OpenYSService.OpenYSServiceListener {
    private static final String TAG = "RealPlayerActivity";

    @Inject
    EZRealPlayActivityPresenter mPresenter;

    /**
     * 动画时间
     */
    private static final int ANIMATION_DURING_TIME = 500;

    public static final float BIG_SCREEN_RATIO = 1.60f;

    // UI消息
    public static final int MSG_PLAY_UI_UPDATE = 200;

    public static final int MSG_AUTO_START_PLAY = 202;

    public static final int MSG_CLOSE_PTZ_PROMPT = 203;

    public static final int MSG_HIDE_PTZ_DIRECTION = 204;

    public static final int MSG_HIDE_PAGE_ANIM = 205;

    public static final int MSG_PLAY_UI_REFRESH = 206;

    public static final int MSG_PREVIEW_START_PLAY = 207;

    public static final int MSG_SET_VEDIOMODE_SUCCESS = 105;

    /**
     * 设置视频质量成功
     */
    public static final int MSG_SET_VEDIOMODE_FAIL = 106;

    // 视频广场URL
    private String mRtspUrl = null;
    // 视频广场播放信息
    private RealPlaySquareInfo mRealPlaySquareInfo = null;
    private EZCameraInfo mCameraInfo = null;

    private AudioPlayUtil mAudioPlayUtil = null;
    private LocalInfo mLocalInfo = null;
    private Handler mHandler = null;

    private float mRealRatio = Constant.LIVE_VIEW_RATIO;
    /**
     * 标识是否正在播放
     */
    private int mStatus = RealPlayStatus.STATUS_INIT;
    private boolean mIsOnStop = false;
    /**
     * 屏幕当前方向
     */
    private int mOrientation = Configuration.ORIENTATION_PORTRAIT;
    private int mForceOrientation = 0;
    private Rect mRealPlayRect = null;

    private LinearLayout mRealPlayPageLy = null;
    private TitleBar mLandscapeTitleBar = null;
    private Button mTiletRightBtn = null;
    private RelativeLayout mRealPlayPlayRl = null;

    private SurfaceView mRealPlaySv = null;
    private SurfaceHolder mRealPlaySh = null;
    private CustomTouchListener mRealPlayTouchListener = null;

    // 提示布局界面
    private RelativeLayout mRealPlayPromptRl = null;
    //loading控件
    private RelativeLayout mRealPlayLoadingRl;
    private TextView mRealPlayTipTv;
    private ImageView mRealPlayPlayIv;
    private LoadingTextView mRealPlayPlayLoading;
    private LinearLayout mRealPlayPlayPrivacyLy;
    //隐私保护动画
    private ImageView mPageAnimIv = null;
    private AnimationDrawable mPageAnimDrawable = null;

    private LinearLayout mRealPlayControlRl = null;
    private ImageButton mRealPlayBtn = null;
    private ImageButton mRealPlaySoundBtn = null;
    private TextView mRealPlayFlowTv = null;
    private int mControlDisplaySec = 0;

    // 播放比例
    private float mPlayScale = 1;

    private RelativeLayout mRealPlayCaptureRl = null;
    private LayoutParams mRealPlayCaptureRlLp = null;
    private ImageView mRealPlayCaptureIv = null;
    private ImageView mRealPlayCaptureWatermarkIv = null;
    private int mCaptureDisplaySec = 0;
    private LinearLayout mRealPlayRecordLy = null;
    private ImageView mRealPlayRecordIv = null;
    private TextView mRealPlayRecordTv = null;

    /**
     * 录像文件路径
     */
    private String mRecordFilePath = null;
    private boolean mIsRecording = false;
    private String mRecordTime = null;
    /**
     * 录像时间
     */
    private int mRecordSecond = 0;

    private HorizontalScrollView mRealPlayOperateBar = null;

    //    private LinearLayout mRealPlayPrivacyBtnLy = null;
    private LinearLayout mRealPlayCaptureBtnLy = null;

    private ImageButton mRealPlayCaptureBtn = null;

    private RotateViewUtil mRecordRotateViewUtil = null;

    private Button mRealPlayQualityBtn = null;
    private TextView mRealPlayRatioTv = null;

    // 横屏云台
    private ImageView mRealPlayFullPtzPromptIv = null;
    private boolean mIsOnPtz = false;
    private ImageView mRealPlayPtzDirectionIv = null;
    private ImageButton mRealPlayFullAnimBtn = null;
    private int[] mStartXy = new int[2];
    private int[] mEndXy = new int[2];

    private PopupWindow mQualityPopupWindow = null;
    private PopupWindow mPtzPopupWindow = null;
    private LinearLayout mPtzControlLy = null;
    private PopupWindow mTalkPopupWindow = null;
    private RingView mTalkRingView = null;
    private Button mTalkBackControlBtn = null;

    private WaitDialog mWaitDialog = null;

    /**
     * 监听锁屏解锁的事件
     */
    private RealPlayBroadcastReceiver mBroadcastReceiver = null;
    /**
     * 定时器
     */
    private Timer mUpdateTimer = null;
    /**
     * 定时器执行的任务
     */
    private TimerTask mUpdateTimerTask = null;

    // 弱提示预览信息
    private long mStartTime = 0;
    private long mStopTime = 0;

    // 对讲模式
    private boolean mIsOnTalk = false;

    // 直播预告
    private TextView mRealPlayPreviewTv = null;

    /**
     * 演示点预览控制对象
     */
    private EZPlayer mEZPlayer = null;
    private EZOpenSDK mEZOpenSDK = EZOpenSDK.getInstance();
    //    private StubPlayer mStub = new StubPlayer();
    private CheckTextButton mFullScreenTitleBarBackBtn;
    private EZVideoLevel mCurrentQulityMode = EZVideoLevel.VIDEO_LEVEL_HD;
    private EZDeviceInfo mDeviceInfo = null;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private List<CameraEntity.ItemsBean> mCameraList;
    private UserEntity.ObjectEntity mUser;

    @Override
    protected void setUpContentView() {
        initData();
        initView();
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        waittingDialog();
        getCameraList();
    }

    @Override
    protected void setUpView() {

    }

    @Override
    protected void setUpData() {
        mUser = mPresenter.getRepositoriesManager().getUser().getObject();
    }

    @Override
    protected void setupActivityComponent() {
        TbtxApplication.get(this).getUserComponent().plus(new EZRealPlayActivityModule(this)).inject(this);
    }

    private void getCameraList() {
        mPresenter.getCameraList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            return;
        }
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if (mRealPlaySv != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mRealPlaySv.getWindowToken(), 0);
                }
            }
        }, 200);

        initUI();
        mRealPlaySv.setVisibility(View.VISIBLE);

        LogUtil.infoLog(TAG, "onResume real play status:" + mStatus);
        startPlay();
    }

    private void startPlay() {
        if (mCameraInfo == null) {
            stopRealPlay();
            setRealPlayStopUI();
        } else if (mCameraInfo != null && mCameraInfo.getOnlineStatus() != 1) {
            if (mStatus != RealPlayStatus.STATUS_STOP) {
                stopRealPlay();
                setRealPlayStopUI();
            }
            setRealPlayFailUI(getString(R.string.realplay_fail_device_not_exist));
        } else {
            if (mStatus == RealPlayStatus.STATUS_INIT || mStatus == RealPlayStatus.STATUS_PAUSE
                    || mStatus == RealPlayStatus.STATUS_DECRYPT) {
                // 开始播放
                startRealPlay();
            }
        }
        mIsOnStop = false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            client.connect();
            Action viewAction = Action.newAction(
                    Action.TYPE_VIEW, // TODO: choose an action type.
                    "EZRealPlay Page", // TODO: Define a title for the content shown.
                    // TODO: If you have web page content that matches this app activity's content,
                    // make sure this auto-generated web page URL is correct.
                    // Otherwise, set the URL to null.
                    Uri.parse("http://host/path"),
                    // TODO: Make sure this auto-generated app deep link URI is correct.
                    Uri.parse("android-app://com.buddysoft.tbtx_android/http/host/path")
            );
            AppIndex.AppIndexApi.start(client, viewAction);
        } catch (Exception e) {
            Log.e("message", e.getMessage());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "EZRealPlay Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.buddysoft.tbtx_android/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);

        mHandler.removeMessages(MSG_AUTO_START_PLAY);
        hidePageAnim();

        if (mCameraInfo == null && mRtspUrl == null) {
            return;
        }

        closePtzPopupWindow();
        closeTalkPopupWindow(true, false);
        if (mStatus != RealPlayStatus.STATUS_STOP) {
            mIsOnStop = true;
            stopRealPlay();
            mStatus = RealPlayStatus.STATUS_PAUSE;
            setRealPlayStopUI();
        } else {
            setStopLoading();
        }
        mRealPlaySv.setVisibility(View.INVISIBLE);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.disconnect();
    }

    /*
     * (non-Javadoc)
     * @see android.app.Activity#onDestroy()
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeMessages(MSG_AUTO_START_PLAY);
        mHandler.removeMessages(MSG_HIDE_PTZ_DIRECTION);
        mHandler.removeMessages(MSG_CLOSE_PTZ_PROMPT);
        mHandler.removeMessages(MSG_HIDE_PAGE_ANIM);
        mHandler = null;

        if (mBroadcastReceiver != null) {
            // 取消锁屏广播的注册
            unregisterReceiver(mBroadcastReceiver);
        }

    }

    // 初始化数据对象
    private void initData() {
        // 获取本地信息
        Application application = (Application) getApplication();
        mAudioPlayUtil = AudioPlayUtil.getInstance(application);
        // 获取配置信息操作对象
        mLocalInfo = LocalInfo.getInstance();
        // 获取屏幕参数
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        mLocalInfo.setScreenWidthHeight(metric.widthPixels, metric.heightPixels);
        mLocalInfo.setNavigationBarHeight((int) Math.ceil(25 * getResources().getDisplayMetrics().density));

        mHandler = new Handler(this);
        mRecordRotateViewUtil = new RotateViewUtil();

        mBroadcastReceiver = new RealPlayBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_USER_PRESENT);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mBroadcastReceiver, filter);

        mRealPlaySquareInfo = new RealPlaySquareInfo();
        Intent intent = getIntent();

        mRtspUrl = intent.getStringExtra(IntentConsts.EXTRA_RTSP_URL);
//        mCurrentQulityMode = (mCameraInfo.getVideoLevel() == 0 ? EZVideoLevel.VIDEO_LEVEL_FLUNET :
//                (mCameraInfo.getVideoLevel() == 1) ? EZVideoLevel.VIDEO_LEVEL_BALANCED :
//                        EZVideoLevel.VIDEO_LEVEL_HD);
        mCurrentQulityMode = EZVideoLevel.VIDEO_LEVEL_BALANCED;
        LogUtil.debugLog(TAG, "rtspUrl:" + mRtspUrl);
        getRealPlaySquareInfo();
    }

    private void getRealPlaySquareInfo() {
        if (TextUtils.isEmpty(mRtspUrl)) {
            return;
        }

        Uri uri = Uri.parse(mRtspUrl.replaceFirst("&", "?"));
        try {
            mRealPlaySquareInfo.mSquareId = Integer.parseInt(uri.getQueryParameter("squareid"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        try {
            mRealPlaySquareInfo.mChannelNo = Integer.parseInt(Utils.getUrlValue(mRtspUrl, "channelno=", "&"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        mRealPlaySquareInfo.mCameraName = uri.getQueryParameter("cameraname");
        try {
            mRealPlaySquareInfo.mSoundType = Integer.parseInt(uri.getQueryParameter("soundtype"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        mRealPlaySquareInfo.mCoverUrl = uri.getQueryParameter("md5Serial");
        if (!TextUtils.isEmpty(mRealPlaySquareInfo.mCoverUrl)) {
            mRealPlaySquareInfo.mCoverUrl = mLocalInfo.getServAddr() + mRealPlaySquareInfo.mCoverUrl + "_mobile.jpeg";
        }
    }

    /**
     * screen状态广播接收者
     */
    private class RealPlayBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {
                closePtzPopupWindow();
                closeTalkPopupWindow(true, false);
                if (mStatus != RealPlayStatus.STATUS_STOP) {
                    stopRealPlay();
                    mStatus = RealPlayStatus.STATUS_PAUSE;
                    setRealPlayStopUI();
                }
            }
        }
    }

    private void initTitleBar() {
        mLandscapeTitleBar = (TitleBar) findViewById(R.id.title_bar_landscape);
        mLandscapeTitleBar.setStyle(Color.rgb(0xff, 0xff, 0xff), getResources().getDrawable(R.color.dark_bg_70p),
                getResources().getDrawable(R.drawable.message_back_selector));
        mLandscapeTitleBar.setOnTouchListener(this);
        mFullScreenTitleBarBackBtn = new CheckTextButton(this);
        mFullScreenTitleBarBackBtn.setBackground(getResources().getDrawable(R.drawable.common_title_back_selector));
        mLandscapeTitleBar.addLeftView(mFullScreenTitleBarBackBtn);
    }

    private void initRealPlayPageLy() {
        mRealPlayPageLy = (LinearLayout) findViewById(R.id.realplay_page_ly);
        /** 测量状态栏高度 **/
        ViewTreeObserver viewTreeObserver = mRealPlayPageLy.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (mRealPlayRect == null) {
                    // 获取状况栏高度
                    mRealPlayRect = new Rect();
                    getWindow().getDecorView().getWindowVisibleDisplayFrame(mRealPlayRect);
                }
            }
        });
    }

    // 初始化界面
    private void initView() {
        setContentView(R.layout.ez_realplay_page, R.string.title_video, MODE_BACK);
        //super.initBaseView();
        // 保持屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        initTitleBar();
        initRealPlayPageLy();
        initLoadingUI();

        mRealPlayPlayRl = (RelativeLayout) findViewById(R.id.realplay_play_rl);
        mRealPlaySv = (SurfaceView) findViewById(R.id.realplay_sv);
        mRealPlaySv.getHolder().addCallback(this);
        mRealPlayTouchListener = new CustomTouchListener() {

            @Override
            public boolean canZoom(float scale) {
                if (mStatus == RealPlayStatus.STATUS_PLAY) {
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public boolean canDrag(int direction) {
                if (mStatus != RealPlayStatus.STATUS_PLAY) {
                    return false;
                }
                if (mEZPlayer != null) {
                    // 出界判断
                    if (DRAG_LEFT == direction || DRAG_RIGHT == direction) {
                        // 左移/右移出界判断
                        if (mDeviceInfo.isSupportPTZ()) {
                            return true;
                        }
                    } else if (DRAG_UP == direction || DRAG_DOWN == direction) {
                        // 上移/下移出界判断
                        if (mDeviceInfo.isSupportPTZ()) {
                            return true;
                        }
                    }
                }
                return false;
            }

            @Override
            public void onSingleClick() {
                onRealPlaySvClick();
            }

            @Override
            public void onDoubleClick(MotionEvent e) {
            }

            @Override
            public void onZoom(float scale) {
                LogUtil.debugLog(TAG, "onZoom:" + scale);
                if (mEZPlayer != null && mDeviceInfo.isSupportZoom()) {
                    startZoom(scale);
                }
            }

            @Override
            public void onDrag(int direction, float distance, float rate) {
                LogUtil.debugLog(TAG, "onDrag:" + direction);
                if (mEZPlayer != null) {
                    //Utils.showLog(RealPlayActivity.this, "onDrag rate:" + rate);
                }
            }

            @Override
            public void onEnd(int mode) {
                LogUtil.debugLog(TAG, "onEnd:" + mode);
                if (mEZPlayer != null && mDeviceInfo.isSupportZoom()) {
                    stopZoom();
                }
            }

            @Override
            public void onZoomChange(float scale, CustomRect oRect, CustomRect curRect) {
                LogUtil.debugLog(TAG, "onZoomChange:" + scale);
                if (mEZPlayer != null && mDeviceInfo.isSupportZoom()) {
                    //采用云台调焦
                    return;
                }
                if (mStatus == RealPlayStatus.STATUS_PLAY) {
                    if (scale > 1.0f && scale < 1.1f) {
                        scale = 1.1f;
                    }
                    setPlayScaleUI(scale, oRect, curRect);
                }
            }
        };
        mRealPlaySv.setOnTouchListener(mRealPlayTouchListener);

        mRealPlayPtzDirectionIv = (ImageView) findViewById(R.id.realplay_ptz_direction_iv);

        mRealPlayPromptRl = (RelativeLayout) findViewById(R.id.realplay_prompt_rl);
        mRealPlayControlRl = (LinearLayout) findViewById(R.id.realplay_control_rl);
        mRealPlayBtn = (ImageButton) findViewById(R.id.realplay_play_btn);
        mRealPlaySoundBtn = (ImageButton) findViewById(R.id.realplay_sound_btn);
        //        mRealPlayFlowTv = (TextView) findViewById(R.id.realplay_flow_tv);
        //        mRealPlayFlowTv.setText("0k/s 0MB");

        mRealPlayCaptureRl = (RelativeLayout) findViewById(R.id.realplay_capture_rl);
        mRealPlayCaptureRlLp = (LayoutParams) mRealPlayCaptureRl.getLayoutParams();
        mRealPlayCaptureIv = (ImageView) findViewById(R.id.realplay_capture_iv);
        mRealPlayCaptureWatermarkIv = (ImageView) findViewById(R.id.realplay_capture_watermark_iv);
        mRealPlayRecordLy = (LinearLayout) findViewById(R.id.realplay_record_ly);
        mRealPlayRecordIv = (ImageView) findViewById(R.id.realplay_record_iv);
        mRealPlayRecordTv = (TextView) findViewById(R.id.realplay_record_tv);

        mRealPlayQualityBtn = (Button) findViewById(R.id.realplay_quality_btn);

        mRealPlayRatioTv = (TextView) findViewById(R.id.realplay_ratio_tv);

        if (mRtspUrl == null) {
            initOperateBarUI(false);

            initFullOperateBarUI();
            mRealPlayOperateBar.setVisibility(View.VISIBLE);
        } else {
            LinearLayout.LayoutParams realPlayPlayRlLp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT);
            realPlayPlayRlLp.gravity = Gravity.CENTER;
            //mj 2015/11/01 realPlayPlayRlLp.weight = 1;
            mRealPlayPlayRl.setLayoutParams(realPlayPlayRlLp);
            mRealPlayPlayRl.setBackgroundColor(getResources().getColor(R.color.common_bg));
        }

        setRealPlaySvLayout();
        initCaptureUI();

        mWaitDialog = new WaitDialog(this, android.R.style.Theme_Translucent_NoTitleBar);
        mWaitDialog.setCancelable(false);

        //super.mTvTitle.setText("请选择");
        Drawable drawable = getResources().getDrawable(R.drawable.arrows_down);
        // 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        toolbar_title.setCompoundDrawables(null, null, drawable, null);
        toolbar_title.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCameraList != null && mCameraList.size() > 0) {
//                    stopRealPlay();
//                    setRealPlayStopUI();
                    VedioWindows vedioWindows = new VedioWindows(EZRealPlayActivity.this, mRealPlayPageLy, mCameraList);
                    vedioWindows.setOperationInterface(new VedioWindows.OperationInterface() {
                        @Override
                        public void playVideo(CameraEntity.ItemsBean camera, int position) {
                            for (CameraEntity.ItemsBean cam : mCameraList) {
                                cam.setCheck(false);
                            }
                            camera.setCheck(true);
                            mCameraList.set(position, camera);
                            mEZOpenSDK.setAccessToken(camera.getAccessToken());
                            mCameraInfo = new EZCameraInfo();
                            mCameraInfo.setCameraId(camera.getCameraId());
                            mCameraInfo.setDeviceSerial(camera.getSerial());
                            mCameraInfo.setOnlineStatus(1);
                            mCameraInfo.setVideoLevel(1);
                            mStatus = RealPlayStatus.STATUS_INIT;
                            toolbar_title.setText(camera.getName());
                            startPlay();
                        }
                    });
                }

            }
        });
    }


    private void startZoom(float scale) {
        if (mEZPlayer == null) {
            return;
        }

        hideControlRlAndFullOperateBar(false);
        boolean zoomIn = scale > 1.01 ? true : false;
    }

    private void stopZoom() {
        if (mEZPlayer == null) {
            return;
        }
    }

    private void setPtzDirectionIv(int command) {
        setPtzDirectionIv(command, 0);
    }

    private void setPtzDirectionIv(int command, int errorCode) {
        if (command != -1 && errorCode == 0) {
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
            switch (command) {
                case RealPlayStatus.PTZ_LEFT:
                    mRealPlayPtzDirectionIv.setBackgroundResource(R.drawable.left_twinkle);
                    params.addRule(RelativeLayout.CENTER_VERTICAL);
                    params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    mRealPlayPtzDirectionIv.setLayoutParams(params);
                    break;
                case RealPlayStatus.PTZ_RIGHT:
                    mRealPlayPtzDirectionIv.setBackgroundResource(R.drawable.right_twinkle);
                    params.addRule(RelativeLayout.CENTER_VERTICAL);
                    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    mRealPlayPtzDirectionIv.setLayoutParams(params);
                    break;
                case RealPlayStatus.PTZ_UP:
                    mRealPlayPtzDirectionIv.setBackgroundResource(R.drawable.up_twinkle);
                    params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                    mRealPlayPtzDirectionIv.setLayoutParams(params);
                    break;
                case RealPlayStatus.PTZ_DOWN:
                    mRealPlayPtzDirectionIv.setBackgroundResource(R.drawable.down_twinkle);
                    params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    params.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.realplay_sv);
                    mRealPlayPtzDirectionIv.setLayoutParams(params);
                    break;
                default:
                    break;
            }
            mRealPlayPtzDirectionIv.setVisibility(View.VISIBLE);
            mHandler.removeMessages(MSG_HIDE_PTZ_DIRECTION);
            Message msg = new Message();
            msg.what = MSG_HIDE_PTZ_DIRECTION;
            msg.arg1 = 1;
            mHandler.sendMessageDelayed(msg, 500);
        } else if (errorCode != 0) {
            LayoutParams svParams = (LayoutParams) mRealPlaySv.getLayoutParams();
            LayoutParams params = null;
            switch (errorCode) {
                case ErrorCode.ERROR_CAS_PTZ_ROTATION_LEFT_LIMIT_FAILED:
                    params = new LayoutParams(LayoutParams.WRAP_CONTENT, svParams.height);
                    mRealPlayPtzDirectionIv.setBackgroundResource(R.drawable.ptz_left_limit);
                    params.addRule(RelativeLayout.CENTER_VERTICAL);
                    params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    mRealPlayPtzDirectionIv.setLayoutParams(params);
                    break;
                case ErrorCode.ERROR_CAS_PTZ_ROTATION_RIGHT_LIMIT_FAILED:
                    params = new LayoutParams(LayoutParams.WRAP_CONTENT, svParams.height);
                    mRealPlayPtzDirectionIv.setBackgroundResource(R.drawable.ptz_right_limit);
                    params.addRule(RelativeLayout.CENTER_VERTICAL);
                    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    mRealPlayPtzDirectionIv.setLayoutParams(params);
                    break;
                case ErrorCode.ERROR_CAS_PTZ_ROTATION_UP_LIMIT_FAILED:
                    params = new LayoutParams(svParams.width, LayoutParams.WRAP_CONTENT);
                    mRealPlayPtzDirectionIv.setBackgroundResource(R.drawable.ptz_top_limit);
                    params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                    mRealPlayPtzDirectionIv.setLayoutParams(params);
                    break;
                case ErrorCode.ERROR_CAS_PTZ_ROTATION_DOWN_LIMIT_FAILED:
                    params = new LayoutParams(svParams.width, LayoutParams.WRAP_CONTENT);
                    mRealPlayPtzDirectionIv.setBackgroundResource(R.drawable.ptz_bottom_limit);
                    params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    params.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.realplay_sv);
                    mRealPlayPtzDirectionIv.setLayoutParams(params);
                    break;
                default:
                    break;
            }
            mRealPlayPtzDirectionIv.setVisibility(View.VISIBLE);
            mHandler.removeMessages(MSG_HIDE_PTZ_DIRECTION);
            Message msg = new Message();
            msg.what = MSG_HIDE_PTZ_DIRECTION;
            msg.arg1 = 1;
            mHandler.sendMessageDelayed(msg, 500);
        } else {
            mRealPlayPtzDirectionIv.setVisibility(View.GONE);
            mHandler.removeMessages(MSG_HIDE_PTZ_DIRECTION);
        }
    }

    // 初始化UI
    @SuppressWarnings("deprecation")
    private void initUI() {
        mPageAnimDrawable = null;
//        mRealPlaySoundBtn.setVisibility(View.VISIBLE);

        if (mCameraInfo != null) {
            mLandscapeTitleBar.setTitle(mCameraInfo.getCameraName());

            setCameraInfoTiletRightBtn();

            if (mLocalInfo.isSoundOpen()) {
                mRealPlaySoundBtn.setBackgroundResource(R.drawable.ezopen_vertical_preview_sound_selector);
            } else {
                mRealPlaySoundBtn.setBackgroundResource(R.drawable.ezopen_vertical_preview_sound_off_selector);
            }

            mRealPlayCaptureBtnLy.setVisibility(View.VISIBLE);
//            mRealPlayRecordContainerLy.setVisibility(View.VISIBLE);
            mRealPlayQualityBtn.setVisibility(View.VISIBLE);
            mRealPlayFullPtzPromptIv.setVisibility(View.GONE);

            updateUI();
        } else if (mRtspUrl != null) {
            if (!TextUtils.isEmpty(mRealPlaySquareInfo.mCameraName)) {
                mLandscapeTitleBar.setTitle(mRealPlaySquareInfo.mCameraName);
            }
            mRealPlaySoundBtn.setVisibility(View.GONE);
            mRealPlayQualityBtn.setVisibility(View.GONE);
        }

        if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            updateOperatorUI();
        }
    }

    private void setCameraInfoTiletRightBtn() {
        if (mTiletRightBtn != null) {
            if (mCameraInfo.getOnlineStatus() == 1) {
                mTiletRightBtn.setVisibility(View.VISIBLE);
            } else {
                mTiletRightBtn.setVisibility(View.GONE);
            }
        }
    }

    private void initOperateBarUI(boolean bigScreen) {
        bigScreen = false;
        if (mRealPlayOperateBar != null) {
            mRealPlayOperateBar.setVisibility(View.GONE);
            mRealPlayOperateBar = null;
        }
        if (bigScreen) {
            mRealPlayOperateBar = (HorizontalScrollView) findViewById(R.id.ezopen_realplay_operate_bar2);
            findViewById(R.id.ezopen_realplay_operate_bar).setVisibility(View.GONE);
            mRealPlayCaptureBtnLy = (LinearLayout) findViewById(R.id.realplay_previously_btn_ly2);
            mRealPlayCaptureBtn = (ImageButton) findViewById(R.id.realplay_previously_btn2);
        } else {
            mRealPlayOperateBar = (HorizontalScrollView) findViewById(R.id.ezopen_realplay_operate_bar);
            findViewById(R.id.ezopen_realplay_operate_bar2).setVisibility(View.GONE);
            mRealPlayCaptureBtnLy = (LinearLayout) findViewById(R.id.realplay_previously_btn_ly);
            mRealPlayCaptureBtn = (ImageButton) findViewById(R.id.realplay_previously_btn);
        }
        mRealPlayOperateBar.setVisibility(View.VISIBLE);
    }


    private void initFullOperateBarUI() {
        mRealPlayFullPtzPromptIv = (ImageView) findViewById(R.id.realplay_full_ptz_prompt_iv);
        mRealPlayFullAnimBtn = (ImageButton) findViewById(R.id.realplay_full_anim_btn);
    }

    private void startFullBtnAnim(final View animView, final int[] startXy, final int[] endXy,
                                  final AnimationListener animationListener) {
        animView.setVisibility(View.VISIBLE);
        TranslateAnimation anim = new TranslateAnimation(startXy[0], endXy[0], startXy[1], endXy[1]);
        anim.setAnimationListener(animationListener);
        anim.setDuration(ANIMATION_DURING_TIME);
        animView.startAnimation(anim);
    }

    private void setVideoLevel() {
        if (mCameraInfo == null || mEZPlayer == null) {
            return;
        }

        if (mCameraInfo.getOnlineStatus() == 1) {
            mRealPlayQualityBtn.setEnabled(true);
        } else {
            mRealPlayQualityBtn.setEnabled(false);
        }
        // 视频质量，2-高清，1-标清，0-流畅
        if (mCurrentQulityMode.getVideoLevel() == EZVideoLevel.VIDEO_LEVEL_FLUNET.getVideoLevel()) {
            mRealPlayQualityBtn.setText(R.string.quality_flunet);
        } else if (mCurrentQulityMode.getVideoLevel() == EZVideoLevel.VIDEO_LEVEL_BALANCED.getVideoLevel()) {
            mRealPlayQualityBtn.setText(R.string.quality_balanced);
        } else if (mCurrentQulityMode.getVideoLevel() == EZVideoLevel.VIDEO_LEVEL_HD.getVideoLevel()) {
            mRealPlayQualityBtn.setText(R.string.quality_hd);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        mOrientation = newConfig.orientation;

        onOrientationChanged();
        super.onConfigurationChanged(newConfig);
    }

    private void updateOrientation() {
        if (mIsOnTalk) {
            if (mEZPlayer != null && mDeviceInfo.isSupportTalk() /*mStub.getSupportTalk() == EZRealPlayConstants.TALK_FULL_DUPLEX*/) {
            } else {
                setForceOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        }
    }

    private void updateOperatorUI() {
        if (mOrientation == Configuration.ORIENTATION_PORTRAIT) {
            // 显示状态栏
            fullScreen(false);
            updateOrientation();
            mLandscapeTitleBar.setVisibility(View.GONE);
            mRealPlayControlRl.setVisibility(View.VISIBLE);
            if (mRtspUrl == null) {
                mRealPlayPageLy.setBackgroundColor(getResources().getColor(R.color.common_bg));
                mRealPlayOperateBar.setVisibility(View.VISIBLE);
            }
        } else {
            // 隐藏状态栏
            fullScreen(true);
            // hide the
            mRealPlayControlRl.setVisibility(View.GONE);
            if (!mIsOnTalk && !mIsOnPtz) {
                mLandscapeTitleBar.setVisibility(View.VISIBLE);
            }
            if (mRtspUrl == null) {
                mRealPlayOperateBar.setVisibility(View.GONE);
                mRealPlayPageLy.setBackgroundColor(getResources().getColor(R.color.black1));
            }
        }

        //        mRealPlayControlRl.setVisibility(View.GONE);
        closeQualityPopupWindow();
        if (mStatus == RealPlayStatus.STATUS_START) {
            showControlRlAndFullOperateBar();
        }
    }

    private void updatePtzUI() {
        if (!mIsOnPtz) {
            return;
        }
        if (mOrientation == Configuration.ORIENTATION_PORTRAIT) {
            setFullPtzStopUI(false);
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    openPtzPopupWindow(mRealPlayPageLy);
                }
            });
        } else {
            closePtzPopupWindow();
            setFullPtzStartUI(false);
        }
    }

    private void updateTalkUI() {
        if (!mIsOnTalk) {
            return;
        }
        if (mOrientation == Configuration.ORIENTATION_PORTRAIT) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    openTalkPopupWindow(mRealPlayPageLy, false);
                }
            });
        } else {
            closeTalkPopupWindow(false, false);
        }
    }

    private void fullScreen(boolean enable) {
        if (enable) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(lp);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            WindowManager.LayoutParams attr = getWindow().getAttributes();
            attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attr);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    private void onOrientationChanged() {
        mRealPlaySv.setVisibility(View.INVISIBLE);
        setRealPlaySvLayout();
        mRealPlaySv.setVisibility(View.VISIBLE);

        updateOperatorUI();
        updateCaptureUI();
        updateTalkUI();
        updatePtzUI();
    }

    /*
     * (non-Javadoc)
     * @see android.view.SurfaceHolder.Callback#surfaceChanged(android.view.SurfaceHolder , int,
     * int, int)
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    /*
     * (non-Javadoc)
     * @see android.view.SurfaceHolder.Callback#surfaceCreated(android.view.SurfaceHolder )
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (mEZPlayer != null) {
            mEZPlayer.setSurfaceHold(holder);
        }
        mRealPlaySh = holder;
    }

    /*
     * (non-Javadoc)
     * @see android.view.SurfaceHolder.Callback#surfaceDestroyed(android.view. SurfaceHolder)
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mEZPlayer != null) {
            mEZPlayer.setSurfaceHold(null);
        }
        mRealPlaySh = null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            closePtzPopupWindow();
            closeTalkPopupWindow(true, false);
            if (mStatus != RealPlayStatus.STATUS_STOP) {
                stopRealPlay();
                setRealPlayStopUI();
            }
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    /*
     * (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.realplay_play_btn:
            case R.id.realplay_play_iv:
                if (mStatus != RealPlayStatus.STATUS_STOP) {
                    stopRealPlay();
                    setRealPlayStopUI();
                } else {
                    startRealPlay();
                }
                break;
            case R.id.realplay_previously_btn:
            case R.id.realplay_previously_btn2:
                onCapturePicBtnClick();
                break;
            case R.id.realplay_quality_btn:
                openQualityPopupWindow(mRealPlayQualityBtn);
                break;
            case R.id.realplay_sound_btn:
                onSoundBtnClick();
                break;
            default:
                break;
        }
    }


    public void setCameraSuccess(CameraEntity cameraEntity) {
        stopCusDialog();

        List<CameraEntity.ItemsBean> items = cameraEntity.getItems();
        if (items != null) {
            CameraEntity.ItemsBean camera = null;
            mEZOpenSDK = EZOpenSDK.getInstance();
            mCameraList = new ArrayList<>();
            mCameraList.addAll(items);

            if (6 == C.Role.RoleKindergartenLeader) {
                camera = mCameraList.get(0);
            } else if (2 == C.Role.RoleFirstTeacher ||
                    mUser.getMobileRole() == C.Role.RoleOtherParents ||
                    mUser.getMobileRole() == C.Role.RoleFirstParents ||
                    mUser.getMobileRole() == C.Role.RoleSecondTeacher) {
                for (CameraEntity.ItemsBean c : mCameraList) {
                    if (c.getClassroomId() != 0 && c.getClassroomId() > 0) {
                        camera = c;
                        return;
                    }
                }
            }
            camera.setCheck(true);
            toolbar_title.setText(camera.getName());
            mEZOpenSDK.setAccessToken(camera.getAccessToken());
            mCameraInfo = new EZCameraInfo();
            mCameraInfo.setCameraId(camera.getCameraId());
            mCameraInfo.setDeviceSerial(camera.getSerial());
            mCameraInfo.setOnlineStatus(1);
            mCameraInfo.setVideoLevel(1);
            mStatus = RealPlayStatus.STATUS_INIT;
            startPlay();

        }
    }

    private void setFullPtzStartUI(boolean startAnim) {
        mIsOnPtz = true;
        setPlayScaleUI(1, null, null);
        if (mLocalInfo.getPtzPromptCount() < 3) {
            mRealPlayFullPtzPromptIv.setBackgroundResource(R.drawable.ptz_prompt);
            mRealPlayFullPtzPromptIv.setVisibility(View.VISIBLE);
            mLocalInfo.setPtzPromptCount(mLocalInfo.getPtzPromptCount() + 1);
            mHandler.removeMessages(MSG_CLOSE_PTZ_PROMPT);
            mHandler.sendEmptyMessageDelayed(MSG_CLOSE_PTZ_PROMPT, 2000);
        }
        if (startAnim) {
            mRealPlayFullAnimBtn.setBackgroundResource(R.drawable.yuntai_pressed);
            mEndXy[0] = Utils.dip2px(this, 20);
            mEndXy[1] = mStartXy[1];
            startFullBtnAnim(mRealPlayFullAnimBtn, mStartXy, mEndXy, new AnimationListener() {

                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mRealPlayFullAnimBtn.setVisibility(View.GONE);
                    onRealPlaySvClick();
                    //                    mFullscreenFullButton.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    private void setFullPtzStopUI(boolean startAnim) {
        mIsOnPtz = false;
        if (startAnim) {
            mRealPlayFullAnimBtn.setBackgroundResource(R.drawable.yuntai_pressed);
            startFullBtnAnim(mRealPlayFullAnimBtn, mEndXy, mStartXy, new AnimationListener() {

                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mRealPlayFullAnimBtn.setVisibility(View.GONE);
                    onRealPlaySvClick();
                }
            });
        }
        mRealPlayFullPtzPromptIv.setVisibility(View.GONE);
        mHandler.removeMessages(MSG_CLOSE_PTZ_PROMPT);
    }

    private void onSoundBtnClick() {
        if (mLocalInfo.isSoundOpen()) {
            mLocalInfo.setSoundOpen(false);
            mRealPlaySoundBtn.setBackgroundResource(R.drawable.ezopen_vertical_preview_sound_off_selector);

        } else {
            mLocalInfo.setSoundOpen(true);
            mRealPlaySoundBtn.setBackgroundResource(R.drawable.ezopen_vertical_preview_sound_selector);
        }
        setRealPlaySound();
    }

    private void setRealPlaySound() {
        if (mEZPlayer != null) {
            if (mRtspUrl == null) {
                if (mLocalInfo.isSoundOpen()) {
                    mEZPlayer.openSound();
                } else {
                    mEZPlayer.closeSound();
                }
            } else {
                if (mRealPlaySquareInfo.mSoundType == 0) {
                    mEZPlayer.closeSound();
                } else {
                    mEZPlayer.openSound();
                }
            }
        }
    }

    private OnClickListener mOnPopWndClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.quality_hd_btn:
                    setQualityMode(EZVideoLevel.VIDEO_LEVEL_HD);
                    break;
                case R.id.quality_balanced_btn:
                    setQualityMode(EZVideoLevel.VIDEO_LEVEL_BALANCED);
                    break;
                case R.id.quality_flunet_btn:
                    setQualityMode(EZVideoLevel.VIDEO_LEVEL_FLUNET);
                    break;
                case R.id.ptz_close_btn:
                    closePtzPopupWindow();
                    break;
                case R.id.ptz_flip_btn:
                    //                    setPtzFlip();
                    break;
                case R.id.talkback_close_btn:
                    closeTalkPopupWindow(true, false);
                    break;
                default:
                    break;
            }
        }
    };

    private OnTouchListener mOnTouchListener = new OnTouchListener() {

        @Override
        public boolean onTouch(View view, MotionEvent motionevent) {
            int ptz_result = -1;
            int action = motionevent.getAction();
            final int speed = EZConstants.PTZ_SPEED_DEFAULT;
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    switch (view.getId()) {
                        case R.id.talkback_control_btn:
                            mTalkRingView.setVisibility(View.VISIBLE);
                            mEZPlayer.setVoiceTalkStatus(true);
                            break;
                        case R.id.ptz_top_btn:
                            mPtzControlLy.setBackgroundResource(R.drawable.ptz_up_sel);
                            setPtzDirectionIv(RealPlayStatus.PTZ_UP);
                            ptz_result = mEZOpenSDK.controlPTZ(mCameraInfo.getCameraId(), EZPTZCommand.EZPTZCommandUp,
                                    EZPTZAction.EZPTZActionSTART, speed);
                            //                            mEZOpenSDK.controlDisplay(mCameraInfo.getCameraId(), EZPTZDisplayCommand.EZPTZDisplayCommandFlip);
                            LogUtil.i(TAG, "controlPTZ ptzCtrl result: " + ptz_result);
                            break;
                        case R.id.ptz_bottom_btn:
                            mPtzControlLy.setBackgroundResource(R.drawable.ptz_bottom_sel);
                            setPtzDirectionIv(RealPlayStatus.PTZ_DOWN);
                            ptz_result = mEZOpenSDK.controlPTZ(mCameraInfo.getCameraId(), EZPTZCommand.EZPTZCommandDown,
                                    EZPTZAction.EZPTZActionSTART, speed);
                            LogUtil.i(TAG, "controlPTZ ptzCtrl result: " + ptz_result);
                            break;
                        case R.id.ptz_left_btn:
                            mPtzControlLy.setBackgroundResource(R.drawable.ptz_left_sel);
                            setPtzDirectionIv(RealPlayStatus.PTZ_LEFT);
                            ptz_result = mEZOpenSDK.controlPTZ(mCameraInfo.getCameraId(), EZPTZCommand.EZPTZCommandLeft,
                                    EZPTZAction.EZPTZActionSTART, speed);
                            LogUtil.i(TAG, "controlPTZ ptzCtrl result: " + ptz_result);
                            break;
                        case R.id.ptz_right_btn:
                            mPtzControlLy.setBackgroundResource(R.drawable.ptz_right_sel);
                            setPtzDirectionIv(RealPlayStatus.PTZ_RIGHT);
                            ptz_result = mEZOpenSDK.controlPTZ(mCameraInfo.getCameraId(), EZPTZCommand.EZPTZCommandRight,
                                    EZPTZAction.EZPTZActionSTART, speed);
                            LogUtil.i(TAG, "controlPTZ ptzCtrl result: " + ptz_result);
                            break;
                        default:
                            break;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    switch (view.getId()) {
                        case R.id.talkback_control_btn:
                            mEZPlayer.setVoiceTalkStatus(false);
                            mTalkRingView.setVisibility(View.GONE);
                            break;
                        case R.id.ptz_top_btn:
                            mPtzControlLy.setBackgroundResource(R.drawable.ptz_bg);
                            ptz_result = mEZOpenSDK.controlPTZ(mCameraInfo.getCameraId(), EZPTZCommand.EZPTZCommandUp,
                                    EZPTZAction.EZPTZActionSTOP, speed);
                            LogUtil.i(TAG, "controlPTZ ptzCtrl result: " + ptz_result);
                            break;
                        case R.id.ptz_bottom_btn:
                            mPtzControlLy.setBackgroundResource(R.drawable.ptz_bg);
                            ptz_result = mEZOpenSDK.controlPTZ(mCameraInfo.getCameraId(), EZPTZCommand.EZPTZCommandDown,
                                    EZPTZAction.EZPTZActionSTOP, speed);
                            LogUtil.i(TAG, "controlPTZ ptzCtrl result: " + ptz_result);
                            break;
                        case R.id.ptz_left_btn:
                            mPtzControlLy.setBackgroundResource(R.drawable.ptz_bg);
                            ptz_result = mEZOpenSDK.controlPTZ(mCameraInfo.getCameraId(), EZPTZCommand.EZPTZCommandLeft,
                                    EZPTZAction.EZPTZActionSTOP, speed);
                            LogUtil.i(TAG, "controlPTZ ptzCtrl result: " + ptz_result);
                            break;
                        case R.id.ptz_right_btn:
                            mPtzControlLy.setBackgroundResource(R.drawable.ptz_bg);
                            ptz_result = mEZOpenSDK.controlPTZ(mCameraInfo.getCameraId(), EZPTZCommand.EZPTZCommandRight,
                                    EZPTZAction.EZPTZActionSTOP, speed);
                            LogUtil.i(TAG, "controlPTZ ptzCtrl result: " + ptz_result);
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
            return false;
        }
    };


    /**
     * 码流配置 清晰度 2-高清，1-标清，0-流畅
     *
     * @see
     * @since V2.0
     */
    private void setQualityMode(final EZVideoLevel mode) {
        // 检查网络是否可用
        if (!ConnectionDetector.isNetworkAvailable(EZRealPlayActivity.this)) {
            // 提示没有连接网络
            Utils.showToast(EZRealPlayActivity.this, R.string.realplay_set_fail_network);
            return;
        }

        if (mEZPlayer != null) {
            mWaitDialog.setWaitText(this.getString(R.string.setting_video_level));
            mWaitDialog.show();

            Thread thr = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        mEZPlayer.setVideoLevel(mode);
                        mCurrentQulityMode = mode;
                        Message msg = Message.obtain();
                        msg.what = MSG_SET_VEDIOMODE_SUCCESS;
                        mHandler.sendMessage(msg);
                        LogUtil.i(TAG, "setQualityMode success");
                    } catch (BaseException e) {
                        mCurrentQulityMode = EZVideoLevel.VIDEO_LEVEL_FLUNET;
                        e.printStackTrace();
                        Message msg = Message.obtain();
                        msg.what = MSG_SET_VEDIOMODE_FAIL;
                        mHandler.sendMessage(msg);
                        LogUtil.i(TAG, "setQualityMode fail");
                    }

                }
            }) {
            };
            thr.start();
        }
    }

    /**
     * 打开对讲控制窗口
     *
     * @see
     * @since V1.8.3
     */
    private void openTalkPopupWindow(View parent, boolean showAnimation) {
        if (mEZPlayer == null) {
            return;
        }
        closeTalkPopupWindow(false, false);
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup layoutView = (ViewGroup) layoutInflater.inflate(R.layout.realplay_talkback_wnd, null, true);
        layoutView.setFocusable(true);
        layoutView.setFocusableInTouchMode(true);
        layoutView.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
                if (arg1 == KeyEvent.KEYCODE_BACK) {
                    LogUtil.infoLog(TAG, "KEYCODE_BACK DOWN");
                    closeTalkPopupWindow(true, false);
                }
                return false;
            }
        });

        ImageButton talkbackCloseBtn = (ImageButton) layoutView.findViewById(R.id.talkback_close_btn);
        talkbackCloseBtn.setOnClickListener(mOnPopWndClickListener);
        mTalkRingView = (RingView) layoutView.findViewById(R.id.talkback_rv);
        mTalkBackControlBtn = (Button) layoutView.findViewById(R.id.talkback_control_btn);
        mTalkBackControlBtn.setOnTouchListener(mOnTouchListener);

        if (mDeviceInfo.isSupportTalk()/*mStub.getSupportTalk() == EZRealPlayConstants.TALK_FULL_DUPLEX*/) {
            mTalkRingView.setVisibility(View.VISIBLE);
            mTalkBackControlBtn.setEnabled(false);
            mTalkBackControlBtn.setText(R.string.talking);
        }

        int height = mLocalInfo.getScreenHeight() - mRealPlayPlayRl.getHeight()
                - (mRealPlayRect != null ? mRealPlayRect.top : mLocalInfo.getNavigationBarHeight());
        mTalkPopupWindow = new PopupWindow(layoutView, LayoutParams.MATCH_PARENT, height, true);
        // mTalkPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        if (showAnimation) {
            mTalkPopupWindow.setAnimationStyle(R.style.popwindowUpAnim);
        }
        mTalkPopupWindow.setFocusable(false);
        mTalkPopupWindow.setOutsideTouchable(false);
        mTalkPopupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        mTalkPopupWindow.update();
        mTalkRingView.post(new Runnable() {
            @Override
            public void run() {
                if (mTalkRingView != null) {
                    mTalkRingView.setMinRadiusAndDistance(mTalkBackControlBtn.getHeight() / 2f,
                            Utils.dip2px(EZRealPlayActivity.this, 22));
                }
            }
        });
    }

    private void closeTalkPopupWindow(boolean stopTalk, boolean startAnim) {
        if (mTalkPopupWindow != null) {
            LogUtil.infoLog(TAG, "closeTalkPopupWindow");
            dismissPopWindow(mTalkPopupWindow);
            mTalkPopupWindow = null;
        }
        mTalkRingView = null;
    }

    /**
     * 打开云台控制窗口
     *
     * @see
     * @since V1.8.3
     */
    private void openPtzPopupWindow(View parent) {
        closePtzPopupWindow();
        mIsOnPtz = true;
        setPlayScaleUI(1, null, null);

        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup layoutView = (ViewGroup) layoutInflater.inflate(R.layout.realplay_ptz_wnd, null, true);

        mPtzControlLy = (LinearLayout) layoutView.findViewById(R.id.ptz_control_ly);
        ImageButton ptzCloseBtn = (ImageButton) layoutView.findViewById(R.id.ptz_close_btn);
        ptzCloseBtn.setOnClickListener(mOnPopWndClickListener);
        ImageButton ptzTopBtn = (ImageButton) layoutView.findViewById(R.id.ptz_top_btn);
        ptzTopBtn.setOnTouchListener(mOnTouchListener);
        ImageButton ptzBottomBtn = (ImageButton) layoutView.findViewById(R.id.ptz_bottom_btn);
        ptzBottomBtn.setOnTouchListener(mOnTouchListener);
        ImageButton ptzLeftBtn = (ImageButton) layoutView.findViewById(R.id.ptz_left_btn);
        ptzLeftBtn.setOnTouchListener(mOnTouchListener);
        ImageButton ptzRightBtn = (ImageButton) layoutView.findViewById(R.id.ptz_right_btn);
        ptzRightBtn.setOnTouchListener(mOnTouchListener);
        ImageButton ptzFlipBtn = (ImageButton) layoutView.findViewById(R.id.ptz_flip_btn);
        ptzFlipBtn.setOnClickListener(mOnPopWndClickListener);

        int height = mLocalInfo.getScreenHeight() - mRealPlayPlayRl.getHeight()
                - (mRealPlayRect != null ? mRealPlayRect.top : mLocalInfo.getNavigationBarHeight());
        mPtzPopupWindow = new PopupWindow(layoutView, LayoutParams.MATCH_PARENT, height, true);
        mPtzPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPtzPopupWindow.setAnimationStyle(R.style.popwindowUpAnim);
        mPtzPopupWindow.setFocusable(true);
        mPtzPopupWindow.setOutsideTouchable(true);
        mPtzPopupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        mPtzPopupWindow.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                LogUtil.infoLog(TAG, "KEYCODE_BACK DOWN");
                mPtzPopupWindow = null;
                mPtzControlLy = null;
                closePtzPopupWindow();
            }
        });
        mPtzPopupWindow.update();
    }

    private void closePtzPopupWindow() {
        mIsOnPtz = false;
        if (mPtzPopupWindow != null) {
            dismissPopWindow(mPtzPopupWindow);
            mPtzPopupWindow = null;
            mPtzControlLy = null;
            setForceOrientation(0);
        }
    }

    Button qualityHdBtn, qualityBalancedBtn, qualityFlunetBtn;

    private void openQualityPopupWindow(View anchor) {
        if (mEZPlayer == null) {
            return;
        }
        closeQualityPopupWindow();
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup layoutView = (ViewGroup) layoutInflater.inflate(R.layout.realplay_quality_items, null, true);

        qualityHdBtn = (Button) layoutView.findViewById(R.id.quality_hd_btn);
        qualityHdBtn.setOnClickListener(mOnPopWndClickListener);
        qualityBalancedBtn = (Button) layoutView.findViewById(R.id.quality_balanced_btn);
        qualityBalancedBtn.setOnClickListener(mOnPopWndClickListener);
        qualityFlunetBtn = (Button) layoutView.findViewById(R.id.quality_flunet_btn);
        qualityFlunetBtn.setOnClickListener(mOnPopWndClickListener);

        // 视频质量，2-高清，1-标清，0-流畅
//        if (mCameraInfo.getVideoLevel() == EZVideoLevel.VIDEO_LEVEL_FLUNET.getVideoLevel()) {
//            qualityFlunetBtn.setEnabled(false);
//        } else if (mCameraInfo.getVideoLevel() == EZVideoLevel.VIDEO_LEVEL_BALANCED.getVideoLevel()) {
//            qualityBalancedBtn.setEnabled(false);
//        } else if (mCameraInfo.getVideoLevel() == EZVideoLevel.VIDEO_LEVEL_HD.getVideoLevel()) {
//            qualityHdBtn.setEnabled(false);
//        }

        int height = 105;

        qualityFlunetBtn.setVisibility(View.VISIBLE);
        qualityBalancedBtn.setVisibility(View.VISIBLE);
        qualityHdBtn.setVisibility(View.VISIBLE);

        height = Utils.dip2px(this, height);
        mQualityPopupWindow = new PopupWindow(layoutView, LayoutParams.WRAP_CONTENT, height, true);
        mQualityPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mQualityPopupWindow.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                LogUtil.infoLog(TAG, "KEYCODE_BACK DOWN");
                mQualityPopupWindow = null;
                closeQualityPopupWindow();
            }
        });
        try {
            mQualityPopupWindow.showAsDropDown(anchor, 0,
                    -(height + anchor.getHeight() + Utils.dip2px(this, 8)));
        } catch (Exception e) {
            e.printStackTrace();
            closeQualityPopupWindow();
        }
    }

    private void closeQualityPopupWindow() {
        if (mQualityPopupWindow != null) {
            dismissPopWindow(mQualityPopupWindow);
            mQualityPopupWindow = null;
        }
    }

    /**
     * 抓拍按钮响应函数
     *
     * @since V1.0
     */
    private void onCapturePicBtnClick() {
        mControlDisplaySec = 0;
        if (!SDCardUtil.isSDCardUseable()) {
            // 提示SD卡不可用
            Utils.showToast(EZRealPlayActivity.this, R.string.remoteplayback_SDCard_disable_use);
            return;
        }

        if (SDCardUtil.getSDCardRemainSize() < SDCardUtil.PIC_MIN_MEM_SPACE) {
            // 提示内存不足
            Utils.showToast(EZRealPlayActivity.this, R.string.remoteplayback_capture_fail_for_memory);
            return;
        }

        if (mEZPlayer != null) {
            mCaptureDisplaySec = 4;
            updateCaptureUI();

            Thread thr = new Thread() {
                @Override
                public void run() {
                    Bitmap bmp = mEZPlayer.capturePicture();
                    if (bmp != null) {
                        try {
                            mAudioPlayUtil.playAudioFile(AudioPlayUtil.CAPTURE_SOUND);

                            String path = EZUtils.generateCaptureFilePath(mLocalInfo.getFilePath(), mCameraInfo.getCameraId(), mCameraInfo.getDeviceSerial());
                            String thumnailPath = EZUtils.generateThumbnailFilePath(path);
                            if (TextUtils.isEmpty(path) || TextUtils.isEmpty(thumnailPath)) {
                                bmp.recycle();
                                bmp = null;
                                return;
                            }
                            path += ".jpg";
                            thumnailPath += ".jpg";

                            EZUtils.saveCapturePictrue(path, thumnailPath, bmp);


                            MediaScanner mMediaScanner = new MediaScanner(EZRealPlayActivity.this);
                            mMediaScanner.scanFile(path, "jpg");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(EZRealPlayActivity.this, getResources().getString(R.string.already_saved_to_volume), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (InnerException e) {
                            e.printStackTrace();
                        } finally {
                            if (bmp != null) {
                                bmp.recycle();
                                bmp = null;
                                return;
                            }
                        }
                    }
                    super.run();
                }
            };
            thr.start();
        }
    }

    private void onRealPlaySvClick() {
        if (mCameraInfo != null && mEZPlayer != null) {
            if (mCameraInfo.getOnlineStatus() != 1) {
                return;
            }
            if (mOrientation == Configuration.ORIENTATION_PORTRAIT) {
                setRealPlayControlRlVisibility();
            } else {
                setRealPlayFullOperateBarVisibility();
            }
        } else if (mRtspUrl != null) {
            setRealPlayControlRlVisibility();
        }
    }

    private void setRealPlayControlRlVisibility() {
        if (mLandscapeTitleBar.getVisibility() == View.VISIBLE || mRealPlayControlRl.getVisibility() == View.VISIBLE) {
            //            mRealPlayControlRl.setVisibility(View.GONE);
            mLandscapeTitleBar.setVisibility(View.GONE);
            closeQualityPopupWindow();
        } else {
            mRealPlayControlRl.setVisibility(View.VISIBLE);
            if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                if (!mIsOnTalk && !mIsOnPtz) {
                    mLandscapeTitleBar.setVisibility(View.VISIBLE);
                }
            } else {
                mLandscapeTitleBar.setVisibility(View.GONE);
            }
            mControlDisplaySec = 0;
        }
    }

    private void setRealPlayFullOperateBarVisibility() {
        if (mLandscapeTitleBar.getVisibility() == View.VISIBLE) {
            mLandscapeTitleBar.setVisibility(View.GONE);
        } else {
            if (!mIsOnTalk && !mIsOnPtz) {
                //mj mRealPlayFullOperateBar.setVisibility(View.VISIBLE);
                //                mFullscreenFullButton.setVisibility(View.VISIBLE);
                mLandscapeTitleBar.setVisibility(View.VISIBLE);
            }
            mControlDisplaySec = 0;
        }
    }

    /**
     * 开始播放
     *
     * @see
     * @since V2.0
     */
    private void startRealPlay() {
        // 增加手机客户端操作信息记录
        LogUtil.debugLog(TAG, "startRealPlay");

        if (mStatus == RealPlayStatus.STATUS_START || mStatus == RealPlayStatus.STATUS_PLAY) {
            return;
        }

        // 检查网络是否可用
        if (!ConnectionDetector.isNetworkAvailable(this)) {
            // 提示没有连接网络
            setRealPlayFailUI(getString(R.string.realplay_play_fail_becauseof_network));
            return;
        }

        mStatus = RealPlayStatus.STATUS_START;
        setRealPlayLoadingUI();

        if (mCameraInfo != null) {
            final String cameraId = Utils.getCameraId(mCameraInfo.getCameraId());
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    mEZPlayer = mEZOpenSDK.createPlayer(EZRealPlayActivity.this, cameraId);

                    if (mEZPlayer == null)
                        return;
                    if (mDeviceInfo == null) {
                        try {
                            mDeviceInfo = mEZOpenSDK.getDeviceInfoBySerial(mCameraInfo.getDeviceSerial());
                        } catch (BaseException e) {
                            e.printStackTrace();
                            return;
                        }
                    }
//                    if (mEZPlayer == null)
//                        return;
                    mEZPlayer.setHandler(mHandler);
                    mEZPlayer.setSurfaceHold(mRealPlaySh);

                    mEZPlayer.startRealPlay();
                }
            };
            Thread thr = new Thread(run);
            thr.start();

        } else if (mRtspUrl != null) {
            // TODO 最好也改成跟上面一样，异步播放，避免ANR
            mEZPlayer = mEZOpenSDK.createPlayerWithUrl(this, mRtspUrl);
            //mStub.setCameraId(mCameraInfo.getCameraId());////****  mj
            if (mEZPlayer == null)
                return;
            mEZPlayer.setHandler(mHandler);
            mEZPlayer.setSurfaceHold(mRealPlaySh);

            mEZPlayer.startRealPlay();
        }
        updateLoadingProgress(0);
    }

    /**
     * 停止播放
     *
     * @see
     * @since V1.0
     */
    private void stopRealPlay() {
        LogUtil.debugLog(TAG, "stopRealPlay");
        mStatus = RealPlayStatus.STATUS_STOP;

        stopUpdateTimer();
        if (mEZPlayer != null) {
            mEZPlayer.stopRealPlay();
        }

    }

    private void setRealPlayLoadingUI() {
        mStartTime = System.currentTimeMillis();
        mRealPlaySv.setVisibility(View.INVISIBLE);
        mRealPlaySv.setVisibility(View.VISIBLE);
        setStartloading();
        mRealPlayBtn.setBackgroundResource(R.drawable.play_stop_selector);

        if (mCameraInfo != null) {
            mRealPlayCaptureBtn.setEnabled(false);
            if (mCameraInfo.getOnlineStatus() == 1) {
                mRealPlayQualityBtn.setEnabled(true);
            } else {
                mRealPlayQualityBtn.setEnabled(false);
            }
        }

        showControlRlAndFullOperateBar();
    }

    private void showControlRlAndFullOperateBar() {
        if (mRtspUrl != null || mOrientation == Configuration.ORIENTATION_PORTRAIT) {
            mRealPlayControlRl.setVisibility(View.VISIBLE);
            if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                if (!mIsOnTalk && !mIsOnPtz) {
                    mLandscapeTitleBar.setVisibility(View.VISIBLE);
                }
            } else {
                mLandscapeTitleBar.setVisibility(View.GONE);
            }
            mControlDisplaySec = 0;
        } else {
            if (!mIsOnTalk && !mIsOnPtz) {
                mLandscapeTitleBar.setVisibility(View.VISIBLE);
            }
            mControlDisplaySec = 0;
        }
    }

    private void setRealPlayStopUI() {
        stopUpdateTimer();
        updateOrientation();
        setRealPlaySvLayout();

        setStopLoading();

        hideControlRlAndFullOperateBar(true);
        mRealPlayBtn.setBackgroundResource(R.drawable.play_play_selector);

        if (mCameraInfo != null) {
            closePtzPopupWindow();
            setFullPtzStopUI(false);

            mRealPlayCaptureBtn.setEnabled(false);
            if (mCameraInfo.getOnlineStatus() == 1) {
                mRealPlayQualityBtn.setEnabled(true);
            } else {
                mRealPlayQualityBtn.setEnabled(false);
            }
        }
    }

    private void setRealPlayFailUI(String errorStr) {
        mStopTime = System.currentTimeMillis();
        showType();

        stopUpdateTimer();
        updateOrientation();

        {
            setLoadingFail(errorStr);
        }
        mRealPlayBtn.setBackgroundResource(R.drawable.play_play_selector);

        hideControlRlAndFullOperateBar(true);

        if (mCameraInfo != null) {
            closePtzPopupWindow();
            setFullPtzStopUI(false);

            mRealPlayCaptureBtn.setEnabled(false);
            if (mCameraInfo.getOnlineStatus() == 1 && (mEZPlayer == null)) {
                mRealPlayQualityBtn.setEnabled(true);
            } else {
                mRealPlayQualityBtn.setEnabled(false);
            }
        }
    }

    private void setRealPlaySuccessUI() {
        mStopTime = System.currentTimeMillis();
        showType();

        updateOrientation();
        setLoadingSuccess();
        //        mRealPlayFlowTv.setVisibility(View.VISIBLE);
        mRealPlayBtn.setBackgroundResource(R.drawable.play_stop_selector);

        if (mCameraInfo != null) {
            mRealPlayCaptureBtn.setEnabled(true);
            if (mCameraInfo.getOnlineStatus() == 1) {
                mRealPlayQualityBtn.setEnabled(true);
            } else {
                mRealPlayQualityBtn.setEnabled(false);
            }
        }

        setRealPlaySound();

        startUpdateTimer();
    }


    public void setForceOrientation(int orientation) {
        if (mForceOrientation == orientation) {
            LogUtil.debugLog(TAG, "setForceOrientation no change");
            return;
        }
        mForceOrientation = orientation;
        if (mForceOrientation != 0) {
        } else {
            updateOrientation();
        }
    }

    /*
     * (non-Javadoc)
     * @see android.os.Handler.Callback#handleMessage(android.os.Message)
     */
    @SuppressLint("NewApi")
    @Override
    public boolean handleMessage(Message msg) {
        // LogUtil.infoLog(TAG, "handleMessage:" + msg.what);
        if (this.isFinishing()) {
            return false;
        }
        switch (msg.what) {
            case EZRealPlayConstants.MSG_GET_CAMERA_INFO_SUCCESS:
                updateLoadingProgress(20);
                handleGetCameraInfoSuccess();
                break;
            case EZRealPlayConstants.MSG_REALPLAY_PLAY_START:
                updateLoadingProgress(40);
                break;
            case EZRealPlayConstants.MSG_REALPLAY_CONNECTION_START:
                updateLoadingProgress(60);
                break;
            case EZRealPlayConstants.MSG_REALPLAY_CONNECTION_SUCCESS:
                updateLoadingProgress(80);
                break;
            case EZRealPlayConstants.MSG_REALPLAY_PLAY_SUCCESS:
                handlePlaySuccess(msg);
                break;
            case EZRealPlayConstants.MSG_REALPLAY_PLAY_FAIL:
                handlePlayFail(msg.arg1, msg.arg2);
                break;
            case EZRealPlayConstants.MSG_REALPLAY_PASSWORD_ERROR:
                if (mCameraInfo != null && mCameraInfo.getShareStatus() == 1) {
                    showSharePasswordError();
                } else {
                    // 处理播放密码错误
                    handlePasswordError(R.string.realplay_password_error_title,
                            R.string.realplay_password_error_message3, R.string.realplay_password_error_message1);
                }
                break;
            case EZRealPlayConstants.MSG_REALPLAY_ENCRYPT_PASSWORD_ERROR:
                if (mCameraInfo != null && mCameraInfo.getShareStatus() == 1) {
                    showSharePasswordError();
                } else {
                    // 处理播放密码错误
                    handlePasswordError(R.string.realplay_encrypt_password_error_title,
                            R.string.realplay_encrypt_password_error_message, 0);
                }
                break;
            case EZRealPlayConstants.MSG_SET_VEDIOMODE_SUCCESS:
                handleSetVedioModeSuccess();
                break;
            case EZRealPlayConstants.MSG_SET_VEDIOMODE_FAIL:
                handleSetVedioModeFail(msg.arg1);
                break;
            case EZRealPlayConstants.MSG_PTZ_SET_FAIL:
                handlePtzControlFail(msg);
                break;
            case EZRealPlayConstants.MSG_START_RECORD_SUCCESS:
                handleRecordSuccess((String) msg.obj);
                break;
            case EZRealPlayConstants.MSG_START_RECORD_FAIL:
                handleRecordFail(msg.arg1);
                break;
            case EZRealPlayConstants.MSG_REALPLAY_VOICETALK_SUCCESS:
                handleVoiceTalkSucceed();
                break;
            case EZRealPlayConstants.MSG_REALPLAY_VOICETALK_STOP:
                handleVoiceTalkStoped(false);
                break;
            case EZRealPlayConstants.MSG_REALPLAY_VOICETALK_FAIL:
                handleVoiceTalkFailed(msg.arg1, (String) msg.obj, msg.arg2);
                break;
            case MSG_PLAY_UI_UPDATE:
                updateRealPlayUI();
                break;
            case MSG_AUTO_START_PLAY:
                startRealPlay();
                break;
            case MSG_CLOSE_PTZ_PROMPT:
                mRealPlayFullPtzPromptIv.setVisibility(View.GONE);
                break;
            case MSG_HIDE_PTZ_DIRECTION:
                handleHidePtzDirection(msg);
                break;
            case MSG_HIDE_PAGE_ANIM:
                hidePageAnim();
                break;
            case MSG_PLAY_UI_REFRESH:
                initUI();
                break;
            case MSG_PREVIEW_START_PLAY:
                mPageAnimIv.setVisibility(View.GONE);
                mRealPlayPreviewTv.setVisibility(View.GONE);
                mStatus = RealPlayStatus.STATUS_INIT;
                startRealPlay();
                break;
            default:
                break;
        }
        return false;
    }

    private void handleHidePtzDirection(Message msg) {
        if (mHandler == null) {
            return;
        }
        mHandler.removeMessages(MSG_HIDE_PTZ_DIRECTION);
        if (msg.arg1 > 2) {
            mRealPlayPtzDirectionIv.setVisibility(View.GONE);
        } else {
            mRealPlayPtzDirectionIv.setVisibility(msg.arg1 == 1 ? View.GONE : View.VISIBLE);
            Message message = new Message();
            message.what = MSG_HIDE_PTZ_DIRECTION;
            message.arg1 = msg.arg1 + 1;
            mHandler.sendMessageDelayed(message, 500);
        }
    }

    private void handlePtzControlFail(Message msg) {
        LogUtil.debugLog(TAG, "handlePtzControlFail:" + msg.arg1);
        switch (msg.arg1) {
            case ErrorCode.ERROR_CAS_PTZ_CONTROL_CALLING_PRESET_FAILED:// 正在调用预置点，键控动作无效
                Utils.showToast(EZRealPlayActivity.this, R.string.camera_lens_too_busy, msg.arg1);
                break;
            case ErrorCode.ERROR_CAS_PTZ_PRESET_PRESETING_FAILE:// 当前正在调用预置点
                Utils.showToast(EZRealPlayActivity.this, R.string.ptz_is_preseting, msg.arg1);
                break;
            case ErrorCode.ERROR_CAS_PTZ_CONTROL_TIMEOUT_SOUND_LACALIZATION_FAILED:// 当前正在声源定位
                break;
            case ErrorCode.ERROR_CAS_PTZ_CONTROL_TIMEOUT_CRUISE_TRACK_FAILED:// 键控动作超时(当前正在轨迹巡航)
                Utils.showToast(EZRealPlayActivity.this, R.string.ptz_control_timeout_cruise_track_failed, msg.arg1);
                break;
            case ErrorCode.ERROR_CAS_PTZ_PRESET_INVALID_POSITION_FAILED:// 当前预置点信息无效
                Utils.showToast(EZRealPlayActivity.this, R.string.ptz_preset_invalid_position_failed, msg.arg1);
                break;
            case ErrorCode.ERROR_CAS_PTZ_PRESET_CURRENT_POSITION_FAILED:// 该预置点已是当前位置
                Utils.showToast(EZRealPlayActivity.this, R.string.ptz_preset_current_position_failed, msg.arg1);
                break;
            case ErrorCode.ERROR_CAS_PTZ_PRESET_SOUND_LOCALIZATION_FAILED:// 设备正在响应本次声源定位
                Utils.showToast(EZRealPlayActivity.this, R.string.ptz_preset_sound_localization_failed, msg.arg1);
                break;
            case ErrorCode.ERROR_CAS_PTZ_OPENING_PRIVACY_FAILED:// 当前正在开启隐私遮蔽
            case ErrorCode.ERROR_CAS_PTZ_CLOSING_PRIVACY_FAILED:// 当前正在关闭隐私遮蔽
            case ErrorCode.ERROR_CAS_PTZ_MIRRORING_FAILED:// 设备正在镜像操作（设备镜像要几秒钟，防止频繁镜像操作）
                Utils.showToast(EZRealPlayActivity.this, R.string.ptz_operation_too_frequently, msg.arg1);
                break;
            case ErrorCode.ERROR_CAS_PTZ_CONTROLING_FAILED:// 设备正在键控动作（上下左右）(一个客户端在上下左右控制，另外一个在开其它东西)
                break;
            case ErrorCode.ERROR_CAS_PTZ_FAILED:// 云台当前操作失败
                break;
            case ErrorCode.ERROR_CAS_PTZ_PRESET_EXCEED_MAXNUM_FAILED:// 当前预置点超过最大个数
                Utils.showToast(EZRealPlayActivity.this, R.string.ptz_preset_exceed_maxnum_failed, msg.arg1);
                break;
            case ErrorCode.ERROR_CAS_PTZ_PRIVACYING_FAILED:// 设备处于隐私遮蔽状态（关闭了镜头，再去操作云台相关）
                Utils.showToast(EZRealPlayActivity.this, R.string.ptz_privacying_failed, msg.arg1);
                break;
            case ErrorCode.ERROR_CAS_PTZ_TTSING_FAILED:// 设备处于语音对讲状态(区别以前的语音对讲错误码，云台单独列一个）
                Utils.showToast(EZRealPlayActivity.this, R.string.ptz_mirroring_failed, msg.arg1);
                break;
            case ErrorCode.ERROR_CAS_PTZ_ROTATION_UP_LIMIT_FAILED:// 设备云台旋转到达上限位
            case ErrorCode.ERROR_CAS_PTZ_ROTATION_DOWN_LIMIT_FAILED:// 设备云台旋转到达下限位
            case ErrorCode.ERROR_CAS_PTZ_ROTATION_LEFT_LIMIT_FAILED:// 设备云台旋转到达左限位
            case ErrorCode.ERROR_CAS_PTZ_ROTATION_RIGHT_LIMIT_FAILED:// 设备云台旋转到达右限位
                setPtzDirectionIv(-1, msg.arg1);
                break;
            default:
                Utils.showToast(EZRealPlayActivity.this, R.string.ptz_operation_failed, msg.arg1);
                break;
        }
    }

    private void hidePageAnim() {
        mHandler.removeMessages(MSG_HIDE_PAGE_ANIM);
        if (mPageAnimDrawable != null) {
            if (mPageAnimDrawable.isRunning()) {
                mPageAnimDrawable.stop();
            }
            mPageAnimDrawable = null;
            mPageAnimIv.setBackgroundDrawable(null);
            mPageAnimIv.setVisibility(View.GONE);
        }
        if (mPageAnimIv != null) {
            mPageAnimIv.setBackgroundDrawable(null);
            mPageAnimIv.setVisibility(View.GONE);
        }
    }

    private void updateUI() {
        //通过能力级设置
        setVideoLevel();
    }

    /**
     * 获取设备信息成功
     *
     * @see
     * @since V1.0
     */
    private void handleGetCameraInfoSuccess() {
        LogUtil.infoLog(TAG, "handleGetCameraInfoSuccess");

        //通过能力级设置
        updateUI();

    }

    private void handleVoiceTalkSucceed() {
        if (mOrientation == Configuration.ORIENTATION_PORTRAIT) {
            openTalkPopupWindow(mRealPlayPageLy, true);
        }
    }

    private void handleVoiceTalkFailed(int errorCode, String msg, int retryCount) {
        closeTalkPopupWindow(true, false);

        switch (errorCode) {
            case TTSClientSDKException.TTSCLIENT_MSG_DEVICE_TAKLING_NOW:
            case CASClientSDKException.CASCLIENT_CAS_TALK_CHANNEL_BUSY:
                Utils.showToast(EZRealPlayActivity.this, R.string.realplay_play_talkback_fail_ison);
                break;
            case CASClientSDKException.CASCLIENT_CAS_PU_OPEN_PRIVACY:
            case TTSClientSDKException.TTSCLIENT_MSG_DEV_PRIVACY_ON:
            case ErrorCode.ERROR_TTS_DEV_PRIVACY_ON:
                Utils.showToast(EZRealPlayActivity.this, R.string.realplay_play_talkback_fail_privacy);
                break;
            case ErrorCode.ERROR_TTS_DEV_NO_ONLINE:
                Utils.showToast(EZRealPlayActivity.this, R.string.realplay_fail_device_not_exist);
                break;
            case ErrorCode.ERROR_TTS_MSG_REQ_TIMEOUT:
            case ErrorCode.ERROR_TTS_MSG_SVR_HANDLE_TIMEOUT:
            case ErrorCode.ERROR_TTS_WAIT_TIMEOUT:
            case ErrorCode.ERROR_TTS_HNADLE_TIMEOUT:
                Utils.showToast(EZRealPlayActivity.this, R.string.realplay_play_talkback_request_timeout, errorCode);
                break;
            case ErrorCode.ERROR_CAS_AUDIO_SOCKET_ERROR:
            case ErrorCode.ERROR_CAS_AUDIO_RECV_ERROR:
            case ErrorCode.ERROR_CAS_AUDIO_SEND_ERROR:
                Utils.showToast(EZRealPlayActivity.this, R.string.realplay_play_talkback_network_exception, errorCode);
                break;
            default:
                Utils.showToast(EZRealPlayActivity.this, R.string.realplay_play_talkback_fail, errorCode);
                break;
        }
    }

    private void handleVoiceTalkStoped(boolean startAnim) {
        if (mIsOnTalk) {
            mIsOnTalk = false;
            setForceOrientation(0);
        }
        if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (startAnim) {
                mRealPlayFullAnimBtn.setBackgroundResource(R.drawable.speech_1);
                startFullBtnAnim(mRealPlayFullAnimBtn, mEndXy, mStartXy, new AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mRealPlayFullAnimBtn.setVisibility(View.GONE);
                        onRealPlaySvClick();
                    }
                });
            }
        }
        if (mStatus == RealPlayStatus.STATUS_PLAY) {
            if (mEZPlayer != null) {
                if (mLocalInfo.isSoundOpen()) {
                    mEZPlayer.openSound();
                } else {
                    mEZPlayer.closeSound();
                }
            }
        }
    }

    private void handleSetVedioModeSuccess() {
        closeQualityPopupWindow();
        setVideoLevel();
        try {
            mWaitDialog.setWaitText(null);
            mWaitDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mStatus == RealPlayStatus.STATUS_PLAY) {
            // 停止播放
            stopRealPlay();
            //下面语句防止stopRealPlay线程还没释放surface, startRealPlay线程已经开始使用surface
            //因此需要等待500ms
            SystemClock.sleep(500);
            // 开始播放
            startRealPlay();
        }
    }

    private void handleSetVedioModeFail(int errorCode) {
        closeQualityPopupWindow();
        setVideoLevel();
        try {
            mWaitDialog.setWaitText(null);
            mWaitDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Utils.showToast(EZRealPlayActivity.this, R.string.realplay_set_vediomode_fail, errorCode);
    }

    /**
     * 开始录像成功
     *
     * @param
     * @see
     * @since V2.0
     */
    private void handleRecordSuccess(String recordFilePath) {
        if (mCameraInfo == null) {
            return;
        }
        mIsRecording = true;
        // 计时按钮可见
        mRealPlayRecordLy.setVisibility(View.VISIBLE);
        mRealPlayRecordTv.setText("00:00");
        mRecordSecond = 0;
    }

    private void handleRecordFail(int errorCode) {
        Utils.showToast(EZRealPlayActivity.this, R.string.remoteplayback_record_fail, errorCode);
    }

    private void hideControlRlAndFullOperateBar(boolean excludeLandscapeTitle) {
        //        mRealPlayControlRl.setVisibility(View.GONE);
        closeQualityPopupWindow();
        if (excludeLandscapeTitle && mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (!mIsOnTalk && !mIsOnPtz) {
                mLandscapeTitleBar.setVisibility(View.VISIBLE);
            }
        } else {
            mLandscapeTitleBar.setVisibility(View.GONE);
        }
    }

    private void updateRealPlayUI() {
        if (mControlDisplaySec == 5) {
            mControlDisplaySec = 0;
            hideControlRlAndFullOperateBar(false);
        }

        updateCaptureUI();

        if (mIsRecording) {
            updateRecordTime();
        }
    }

    private void initCaptureUI() {
        mCaptureDisplaySec = 0;
        mRealPlayCaptureRl.setVisibility(View.GONE);
        mRealPlayCaptureIv.setImageURI(null);
        mRealPlayCaptureWatermarkIv.setTag(null);
        mRealPlayCaptureWatermarkIv.setVisibility(View.GONE);
    }

    // 更新抓图/录像显示UI
    private void updateCaptureUI() {
        if (mRealPlayCaptureRl.getVisibility() == View.VISIBLE) {
            if (mOrientation == Configuration.ORIENTATION_PORTRAIT) {
                if (mRealPlayControlRl.getVisibility() == View.VISIBLE) {
                    mRealPlayCaptureRlLp.setMargins(0, 0, 0, Utils.dip2px(this, 40));
                } else {
                    mRealPlayCaptureRlLp.setMargins(0, 0, 0, 0);
                }
                mRealPlayCaptureRl.setLayoutParams(mRealPlayCaptureRlLp);
            } else {
                LayoutParams realPlayCaptureRlLp = new LayoutParams(
                        Utils.dip2px(this, 65), Utils.dip2px(this, 45));
                realPlayCaptureRlLp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                realPlayCaptureRlLp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                mRealPlayCaptureRl.setLayoutParams(realPlayCaptureRlLp);
            }
            if (mRealPlayCaptureWatermarkIv.getTag() != null) {
                mRealPlayCaptureWatermarkIv.setVisibility(View.VISIBLE);
                mRealPlayCaptureWatermarkIv.setTag(null);
            }
        }
        if (mCaptureDisplaySec >= 4) {
            initCaptureUI();
        }
    }

    /**
     * 更新录像时间
     *
     * @see
     * @since V1.0
     */
    private void updateRecordTime() {
        if (mRealPlayRecordIv.getVisibility() == View.VISIBLE) {
            mRealPlayRecordIv.setVisibility(View.INVISIBLE);
        } else {
            mRealPlayRecordIv.setVisibility(View.VISIBLE);
        }
        // 计算分秒
        int leftSecond = mRecordSecond % 3600;
        int minitue = leftSecond / 60;
        int second = leftSecond % 60;

        // 显示录像时间
        String recordTime = String.format("%02d:%02d", minitue, second);
        mRealPlayRecordTv.setText(recordTime);
    }

    // 处理密码错误
    private void handlePasswordError(int title_resid, int msg1_resid, int msg2_resid) {
        stopRealPlay();
        setRealPlayStopUI();
        LogUtil.debugLog(TAG, "startRealPlay");

        if (mCameraInfo == null || mStatus == RealPlayStatus.STATUS_START || mStatus == RealPlayStatus.STATUS_PLAY) {
            return;
        }

        // 检查网络是否可用
        if (!ConnectionDetector.isNetworkAvailable(this)) {
            // 提示没有连接网络
            setRealPlayFailUI(getString(R.string.realplay_play_fail_becauseof_network));
            return;
        }

        mStatus = RealPlayStatus.STATUS_START;
        setRealPlayLoadingUI();

        updateLoadingProgress(0);
    }

    /**
     * 处理播放成功的情况
     *
     * @see
     * @since V1.0
     */
    private void handlePlaySuccess(Message msg) {
        mStatus = RealPlayStatus.STATUS_PLAY;
        mRealRatio = Constant.LIVE_VIEW_RATIO;

        boolean bSupport = true;//(float) mLocalInfo.getScreenHeight() / mLocalInfo.getScreenWidth() >= BIG_SCREEN_RATIO;
        if (bSupport) {
            initOperateBarUI(mRealRatio <= Constant.LIVE_VIEW_RATIO);
            initUI();
        }
        setRealPlaySvLayout();
        setRealPlaySuccessUI();
        updatePtzUI();
        //        startPrivacyAnim();
        updateTalkUI();
    }

    private void setRealPlaySvLayout() {
        // 设置播放窗口位置
        final int screenWidth = mLocalInfo.getScreenWidth();
        final int screenHeight = (mOrientation == Configuration.ORIENTATION_PORTRAIT) ? (mLocalInfo.getScreenHeight() - mLocalInfo
                .getNavigationBarHeight()) : mLocalInfo.getScreenHeight();
        final LayoutParams realPlaySvlp = Utils.getPlayViewLp(mRealRatio, mOrientation,
                mLocalInfo.getScreenWidth(), (int) (mLocalInfo.getScreenWidth() * Constant.LIVE_VIEW_RATIO),
                screenWidth, screenHeight);

        LayoutParams loadingR1Lp = new LayoutParams(LayoutParams.MATCH_PARENT,
                realPlaySvlp.height);
        //        loadingR1Lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        //        mRealPlayLoadingRl.setLayoutParams(loadingR1Lp);
        //        mRealPlayPromptRl.setLayoutParams(loadingR1Lp);
        LayoutParams svLp = new LayoutParams(realPlaySvlp.width, realPlaySvlp.height);
        //mj svLp.addRule(RelativeLayout.CENTER_IN_PARENT);
        mRealPlaySv.setLayoutParams(svLp);

        if (mRtspUrl == null) {
            //            LinearLayout.LayoutParams realPlayPlayRlLp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
            //                    LayoutParams.WRAP_CONTENT);
            //            realPlayPlayRlLp.gravity = Gravity.CENTER;
            //            mRealPlayPlayRl.setLayoutParams(realPlayPlayRlLp);
        } else {
            LinearLayout.LayoutParams realPlayPlayRlLp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT);
            realPlayPlayRlLp.gravity = Gravity.CENTER;
            //realPlayPlayRlLp.weight = 1;
            mRealPlayPlayRl.setLayoutParams(realPlayPlayRlLp);
        }
        mRealPlayTouchListener.setSacaleRect(Constant.MAX_SCALE, 0, 0, realPlaySvlp.width, realPlaySvlp.height);
        setPlayScaleUI(1, null, null);
    }

    /**
     * 处理播放失败的情况
     *
     * @param errorCode - 错误码
     * @see
     * @since V1.0
     */
    private void handlePlayFail(int errorCode, int retryCount) {
        LogUtil.debugLog(TAG, "handlePlayFail:" + errorCode);

        hidePageAnim();

        stopRealPlay();

        updateRealPlayFailUI(errorCode);
    }

    private void updateRealPlayFailUI(int errorCode) {
        String txt = null;
        LogUtil.i(TAG, "updateRealPlayFailUI: errorCode:" + errorCode);
        // 判断返回的错误码
        switch (errorCode) {
            case HCNetSDKException.NET_DVR_PASSWORD_ERROR:
            case HCNetSDKException.NET_DVR_NOENOUGHPRI:
                if (mCameraInfo != null && mCameraInfo.getShareStatus() == 1) {
                    showSharePasswordError();
                } else {
                    // 弹出密码输入框
                    handlePasswordError(R.string.realplay_password_error_title,
                            R.string.realplay_password_error_message3, R.string.realplay_password_error_message1);
                }
                return;
            case ErrorCode.ERROR_WEB_SESSION_ERROR:
            case ErrorCode.ERROR_WEB_SESSION_EXPIRE:
            case ErrorCode.ERROR_CAS_PLATFORM_CLIENT_NO_SIGN_RELEATED:
            case ErrorCode.ERROR_WEB_HARDWARE_SIGNATURE_ERROR:
            case ErrorCode.ERROR_CAS_VERIFY_SESSION_ERROR:
            case ErrorCode.ERROR_RTSP_SESSION_NOT_EXIST:
                mEZOpenSDK.openLoginPage();
                return;
            // case HCNetSDKException.NET_DVR_RTSP_DESCRIBESERVERERR:
            case RtspClientException.RTSPCLIENT_DEVICE_CONNECTION_LIMIT:
            case HCNetSDKException.NET_DVR_RTSP_OVER_MAX_CHAN:
            case RtspClientException.RTSPCLIENT_OVER_MAXLINK:
            case HCNetSDKException.NET_DVR_OVER_MAXLINK:
            case CASClientSDKException.CASCLIENT_MSG_PU_NO_RESOURCE:
                txt = getString(R.string.remoteplayback_over_link);
                break;
            case ErrorCode.ERROR_WEB_DIVICE_NOT_ONLINE:
            case ErrorCode.ERROR_RTSP_NOT_FOUND:
            case ErrorCode.ERROR_CAS_PLATFORM_CLIENT_REQUEST_NO_PU_FOUNDED:
            case InnerException.INNER_DEVICE_NOT_EXIST:
                if (mCameraInfo != null) {
                    mCameraInfo.setShareStatus(0);
                }
                txt = getString(R.string.realplay_fail_device_not_exist);
                break;
            case ErrorCode.ERROR_WEB_DIVICE_SO_TIMEOUT:
                txt = getString(R.string.realplay_fail_connect_device);
                break;
            case HCNetSDKException.NET_DVR_RTSP_PRIVACY_STATUS:
            case RtspClientException.RTSPCLIENT_PRIVACY_STATUS:
                txt = getString(R.string.realplay_set_fail_status);
                break;
            case ErrorCode.ERROR_RTSP_SHARE_TIME_OVER:
                txt = getString(R.string.realplay_share_time_over);
                break;
            case ErrorCode.ERROR_RTSP_TOKEN_NO_PERMISSION:
                if (mCameraInfo != null && mCameraInfo.getShareStatus() == 1) {
                    txt = getString(R.string.realplay_share_no_permission);
                } else {
                    txt = getString(R.string.realplay_no_permission);
                }
                break;
            case ErrorCode.ERROR_RTSP_NO_VIDEO_SOURCE:
                txt = getString(R.string.realplay_play_no_video_source);
                break;
            case ErrorCode.ERROR_INNER_DEVICE_ENCRYPT_PASSWORD_IS_NULL:
                txt = null;
                break;
            case ErrorCode.ERROR_WEB_CODE_ERROR:
                //VerifySmsCodeUtil.openSmsVerifyDialog(Constant.SMS_VERIFY_LOGIN, this, this);
                OpenYSService.openYSServiceDialog(this, this);
                //txt = Utils.getErrorTip(this, R.string.check_feature_code_fail, errorCode);
                break;
            case ErrorCode.ERROR_WEB_HARDWARE_SIGNATURE_OP_ERROR:
                //VerifySmsCodeUtil.openSmsVerifyDialog(Constant.SMS_VERIFY_HARDWARE, this, null);
                SecureValidate.secureValidateDialog(this, this);
                //txt = Utils.getErrorTip(this, R.string.check_feature_code_fail, errorCode);
                break;
            case ErrorCode.ERROR_INNER_DEVICE_PASSWORD_IS_NULL:
                txt = getString(R.string.device_password_is_null);
                break;
            case EZConstants.EZOpenSDKError.ERROR_WEB_NEED_DISABLE_TERMINAL_BOUND:
                txt = "请在萤石客户端关闭终端绑定";
                break;
            case ErrorCode.ERROR_EXTRA_SQUARE_NO_SHARING:
            case ErrorCode.ERROR_RTSP_BIT_STREAM_NOT_SUPPORT:
            default:
                txt = Utils.getErrorTip(this, R.string.realplay_play_fail, errorCode);
                break;
        }

        if (!TextUtils.isEmpty(txt)) {
            setRealPlayFailUI(txt);
        } else {
            setRealPlayStopUI();
        }
    }

    /**
     * 启动定时器
     *
     * @see
     * @since V1.0
     */
    private void startUpdateTimer() {
        stopUpdateTimer();
        // 开始录像计时
        mUpdateTimer = new Timer();
        mUpdateTimerTask = new TimerTask() {
            @Override
            public void run() {
                if (mLandscapeTitleBar != null && mRealPlayControlRl != null
                        && (mLandscapeTitleBar.getVisibility() == View.VISIBLE || mRealPlayControlRl.getVisibility() == View.VISIBLE)
                        && mControlDisplaySec < 5) {
                    mControlDisplaySec++;
                }
                if (mRealPlayCaptureRl != null && mRealPlayCaptureRl.getVisibility() == View.VISIBLE
                        && mCaptureDisplaySec < 4) {
                    mCaptureDisplaySec++;
                }

                // 更新录像时间
                if (mEZPlayer != null && mIsRecording) {
                    // 更新录像时间
                    Calendar OSDTime = mEZPlayer.getOSDTime();
                    if (OSDTime != null) {
                        String playtime = Utils.OSD2Time(OSDTime);
                        if (!TextUtils.equals(playtime, mRecordTime)) {
                            mRecordSecond++;
                            mRecordTime = playtime;
                        }
                    }
                }
                if (mHandler != null) {
                    mHandler.sendEmptyMessage(MSG_PLAY_UI_UPDATE);
                }
            }
        };
        // 延时1000ms后执行，1000ms执行一次
        mUpdateTimer.schedule(mUpdateTimerTask, 0, 1000);
    }

    /**
     * 停止定时器
     *
     * @see
     * @since V1.0
     */
    private void stopUpdateTimer() {
        mCaptureDisplaySec = 4;
        updateCaptureUI();
        mHandler.removeMessages(MSG_PLAY_UI_UPDATE);
        // 停止录像计时
        if (mUpdateTimer != null) {
            mUpdateTimer.cancel();
            mUpdateTimer = null;
        }

        if (mUpdateTimerTask != null) {
            mUpdateTimerTask.cancel();
            mUpdateTimerTask = null;
        }
    }

    private void dismissPopWindow(PopupWindow popupWindow) {
        if (popupWindow != null && !isFinishing()) {
            try {
                popupWindow.dismiss();
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

    private void setPlayScaleUI(float scale, CustomRect oRect, CustomRect curRect) {
        if (scale == 1) {
            if (mPlayScale == scale) {
                return;
            }
            mRealPlayRatioTv.setVisibility(View.GONE);
            try {
                if (mEZPlayer != null) {
                    mEZPlayer.setDisplayRegion(false, null, null);
                }
            } catch (BaseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            if (mPlayScale == scale) {
                try {
                    if (mEZPlayer != null) {
                        mEZPlayer.setDisplayRegion(true, oRect, curRect);
                    }
                } catch (BaseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return;
            }
            LayoutParams realPlayRatioTvLp = (LayoutParams) mRealPlayRatioTv
                    .getLayoutParams();
            if (mOrientation == Configuration.ORIENTATION_PORTRAIT) {
                realPlayRatioTvLp.setMargins(Utils.dip2px(this, 10), Utils.dip2px(this, 10), 0, 0);
            } else {
                realPlayRatioTvLp.setMargins(Utils.dip2px(this, 70), Utils.dip2px(this, 20), 0, 0);
            }
            mRealPlayRatioTv.setLayoutParams(realPlayRatioTvLp);
            String sacleStr = String.valueOf(scale);
            mRealPlayRatioTv.setText(sacleStr.subSequence(0, Math.min(3, sacleStr.length())) + "X");
            //mj mRealPlayRatioTv.setVisibility(View.VISIBLE);
            mRealPlayRatioTv.setVisibility(View.GONE);
            hideControlRlAndFullOperateBar(false);
            try {
                if (mEZPlayer != null) {
                    mEZPlayer.setDisplayRegion(true, oRect, curRect);
                }
            } catch (BaseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        mPlayScale = scale;
    }

    /*
     * (non-Javadoc)
     * @see android.view.View.OnTouchListener#onTouch(android.view.View, android.view.MotionEvent)
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    /**
     * 分享 需要密码时候的弹框
     *
     * @see
     * @since V1.8.2
     */
    private void showSharePasswordError() {
        // 处理播放密码错误
        handlePasswordError(R.string.realplay_encrypt_password_error_title, R.string.realplay_password_error_message4,
                0);
    }

    /**
     * 这里对方法做描述
     *
     * @see
     * @since V1.8
     */
    private void showType() {
        if (Config.LOGGING && mEZPlayer != null) {
            Utils.showLog(EZRealPlayActivity.this, "getType " + ",取流耗时：" + (mStopTime - mStartTime));
        }
    }

    private void initLoadingUI() {
        mRealPlayLoadingRl = (RelativeLayout) findViewById(R.id.realplay_loading_rl);
        mRealPlayTipTv = (TextView) findViewById(R.id.realplay_tip_tv);
        mRealPlayPlayIv = (ImageView) findViewById(R.id.realplay_play_iv);
        mRealPlayPlayLoading = (LoadingTextView) findViewById(R.id.realplay_loading);
        mRealPlayPlayPrivacyLy = (LinearLayout) findViewById(R.id.realplay_privacy_ly);

        // 设置点击图标的监听响应函数
        mRealPlayPlayIv.setOnClickListener(this);

        mPageAnimIv = (ImageView) findViewById(R.id.realplay_page_anim_iv);
    }

    private void updateLoadingProgress(final int progress) {
        mRealPlayPlayLoading.setTag(Integer.valueOf(progress));
        mRealPlayPlayLoading.setText(progress + "%");
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                if (mRealPlayPlayLoading != null) {
                    Integer tag = (Integer) mRealPlayPlayLoading.getTag();
                    if (tag != null && tag.intValue() == progress) {
                        Random r = new Random();
                        mRealPlayPlayLoading.setText((progress + r.nextInt(20)) + "%");
                    }
                }
            }

        }, 500);
    }

    private void setStartloading() {
        mRealPlayLoadingRl.setVisibility(View.VISIBLE);
        mRealPlayTipTv.setVisibility(View.GONE);
        mRealPlayPlayLoading.setVisibility(View.VISIBLE);
        mRealPlayPlayIv.setVisibility(View.GONE);
        mRealPlayPlayPrivacyLy.setVisibility(View.GONE);
    }

    public void setStopLoading() {
        mRealPlayLoadingRl.setVisibility(View.VISIBLE);
        mRealPlayTipTv.setVisibility(View.GONE);
        mRealPlayPlayLoading.setVisibility(View.GONE);
        mRealPlayPlayIv.setVisibility(View.VISIBLE);
        mRealPlayPlayPrivacyLy.setVisibility(View.GONE);
    }

    public void setLoadingFail(String errorStr) {
        mRealPlayLoadingRl.setVisibility(View.VISIBLE);
        mRealPlayTipTv.setVisibility(View.VISIBLE);
        mRealPlayTipTv.setText(errorStr);
        mRealPlayPlayLoading.setVisibility(View.GONE);
        mRealPlayPlayIv.setVisibility(View.GONE);
        mRealPlayPlayPrivacyLy.setVisibility(View.GONE);
    }

    private void setLoadingSuccess() {
        mRealPlayLoadingRl.setVisibility(View.INVISIBLE);
        mRealPlayTipTv.setVisibility(View.GONE);
        mRealPlayPlayLoading.setVisibility(View.GONE);
        mRealPlayPlayIv.setVisibility(View.GONE);
    }

    /* (non-Javadoc)
     * @see com.videogo.ui.util.SecureValidate.SecureValidateListener#onSecureValidate(int)
     */
    @Override
    public void onSecureValidate(int result) {
        if (result == 0) {
            // 开始播放
            startRealPlay();
        }
    }

    /* (non-Javadoc)
     * @see com.videogo.ui.util.OpenYSService.OpenYSServiceListener#onOpenYSService(int)
     */
    @Override
    public void onOpenYSService(int result) {
        if (result == 0) {
            // 开始播放
            startRealPlay();
        }
    }

    FileOutputStream mOs;

    private StreamConvertCB.OutputDataCB mLocalRecordCb = new StreamConvertCB.OutputDataCB() {
        @Override
        public void onOutputData(byte[] var1, int var2, int var3, byte[] var4) {
            if (mOs == null) {
                File f = new File("/sdcard/videogo.mp4");
                try {
                    mOs = new FileOutputStream(f);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            try {
                mOs.write(var1, 0, var2);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {

            }
        }
    };
}
