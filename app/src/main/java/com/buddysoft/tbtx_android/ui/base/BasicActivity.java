package com.buddysoft.tbtx_android.ui.base;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.buddysoft.tbtx_android.R;
import com.buddysoft.tbtx_android.ui.activity.live.BaseOperation;
import com.buddysoft.tbtx_android.util.AppManager;
import com.buddysoft.tbtx_android.util.ImageUtils;
import com.buddysoft.tbtx_android.widgets.CustomerProgress;
import com.buddysoft.tbtx_android.widgets.RefreshLayout;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import de.greenrobot.event.EventBus;

public abstract class BasicActivity extends AppCompatActivity implements OnClickListener {


    private Activity mActivity;
    protected Builder mBialog;
    protected TextView mTvTitle;
    protected TextView mTvRDesc;
    protected ImageView mImgRight1;
    protected ImageButton mImgBack;


    private CustomerProgress mCustomerProgress;

    public RefreshLayout getRefreshLayout() {
        return mRefreshLayout;
    }

    public void setRefreshLayout(RefreshLayout refreshLayout) {
        this.mRefreshLayout = refreshLayout;
    }

    protected RefreshLayout mRefreshLayout;

    protected void initBaseView() {
        mImgBack = (ImageButton) findViewById(R.id.img_back);
        mImgBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTvTitle = (TextView) findViewById(R.id.tv_title);
    }

    protected String toDouble(Double money) {
        DecimalFormat df = new DecimalFormat("######0.00");
        return df.format(money);
    }

    protected String toDouble(String money) {
        if (TextUtils.isEmpty(money)) {
            return "";
        }
        double money1 = Double.parseDouble(money);
        DecimalFormat df = new DecimalFormat("######0.00");
        return df.format(money1);
    }

    public void waittingDialog() {
        setTheme(android.R.style.Theme);
        mCustomerProgress = new CustomerProgress(this, "进行中,请稍后");
        mCustomerProgress.show();
    }

    public void stopCusDialog() {
        if (mCustomerProgress != null) {
            mCustomerProgress.dismiss();
        }
    }

    /**
     * 对context进行初始化，并将当前的Activity加入到堆栈中，便于管理
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 添加Activity到堆栈
        AppManager.getAppManager().addActivity(this);
        mActivity = this;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 结束Activity&从堆栈中移除
        EventBus.getDefault().unregister(this);
        AppManager.getAppManager().finishActivity(this);
    }


    protected void hideKey() {
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(this.getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    protected void settingEtClickStatus(EditText et) {
        et.setOnClickListener(this);
        et.setFocusable(false);
        et.setFocusableInTouchMode(false);
    }

    protected void showShortToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void showShortToast(int msg) {
        Toast.makeText(this, getResources().getString(msg), Toast.LENGTH_SHORT)
                .show();
    }

    protected void showCenterToast(String msg) {
        Toast toast = Toast.makeText(getApplicationContext(), msg,
                Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    public abstract void didSucceed(BaseOperation operation);


    public void didNoData(BaseOperation operation) {
        stopCusDialog();
        if (getRefreshLayout() != null) {
            getRefreshLayout().stopRefresh();
        }
        Toast.makeText(this, "暂无数据", Toast.LENGTH_LONG).show();
    }

    public void didFail() {
        try {
            stopCusDialog();
            if (getRefreshLayout() != null) {
                getRefreshLayout().stopRefresh();
            }
            Toast.makeText(this, "网络异常,请稍后再试", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
        }
    }

    public void didFail(String msg) {
        try {
            stopCusDialog();
            if (getRefreshLayout() != null) {
                getRefreshLayout().stopRefresh();
            }
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
        }
    }

    public static void forbidPopup(EditText editText, MotionEvent event) {
        int inType = editText.getInputType(); // backup the input type
        editText.setInputType(InputType.TYPE_NULL); // disable soft input
        editText.onTouchEvent(event); // call native handler
        editText.setInputType(inType); // restore input type
        editText.setSelection(editText.getText().length());
    }

    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 1000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    protected void setBuilder(String confirmBtn, String cancelBtn,
                              String title, String msg, BaseActivity baseActivity,
                              boolean isCancelable) {
        mBialog = new Builder(baseActivity);
        mBialog.setTitle(title);
        mBialog.setMessage(msg);
        mBialog.setCancelable(isCancelable);
        mBialog.setPositiveButton(confirmBtn,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onBuilderExcute();
                    }
                });
        if (cancelBtn != null) {
            mBialog.setNegativeButton(cancelBtn,
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            onBuilderCancel();
                            dialog.dismiss();

                        }
                    });
        }
        mBialog.show();

    }

    protected void setBuilder(String title, String msg, final Bundle bundle,
                              BaseActivity baseActivity) {
        setTheme(android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        mBialog = new Builder(baseActivity);
        mBialog.setTitle(title);
        mBialog.setMessage(msg);
        mBialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onBuilderExcute(bundle);
            }
        });
        mBialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        });
        mBialog.show();

    }

    protected void onBuilderExcute() {

    }

    protected void onBuilderExcute(Bundle bundle) {

    }

    protected void onBuilderCancel() {

    }

    protected void setImage(String imgUrl, ImageView img) {
        ImageUtils.imgLoader(this)
                .displayImage(imgUrl, img, ImageUtils.options);
    }

    public void openActivity(Class activity) {
        Intent intent = new Intent(mActivity, activity);
        startActivity(intent);
    }

    /**
     * 所有省
     */
    protected String[] mProvinceDatas;
    /**
     * key - 省 value - 市
     */
    protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    /**
     * key - 市 values - 区
     */
    protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();

    /**
     * key - 区 values - 邮编
     */
    protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();

    /**
     * 当前省的名称
     */
    protected String mCurrentProviceName;
    /**
     * 当前市的名称
     */
    protected String mCurrentCityName;
    /**
     * 当前区的名称
     */
    protected String mCurrentDistrictName = "";

    /**
     * 当前区的邮政编码
     */
    protected String mCurrentZipCode = "";

    /**
     * 解析省市区的XML数据
     */

    protected void initProvinceDatas() {
        List<ProvinceModel> provinceList = null;
        AssetManager asset = getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            // 创建一个解析xml的工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // 解析xml
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            // 获取解析出来的数据
            provinceList = handler.getDataList();
            //*/ 初始化默认选中的省、市、区
            if (provinceList != null && !provinceList.isEmpty()) {
                mCurrentProviceName = provinceList.get(0).getName();
                List<CityModel> cityList = provinceList.get(0).getCityList();
                if (cityList != null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getName();
                    List<DistrictModel> districtList = cityList.get(0).getDistrictList();
                    mCurrentDistrictName = districtList.get(0).getName();
                    mCurrentZipCode = districtList.get(0).getZipcode();
                }
            }
            //*/
            mProvinceDatas = new String[provinceList.size()];
            for (int i = 0; i < provinceList.size(); i++) {
                // 遍历所有省的数据
                mProvinceDatas[i] = provinceList.get(i).getName();
                List<CityModel> cityList = provinceList.get(i).getCityList();
                String[] cityNames = new String[cityList.size()];
                for (int j = 0; j < cityList.size(); j++) {
                    // 遍历省下面的所有市的数据
                    cityNames[j] = cityList.get(j).getName();
                    List<DistrictModel> districtList = cityList.get(j).getDistrictList();
                    String[] distrinctNameArray = new String[districtList.size()];
                    DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
                    for (int k = 0; k < districtList.size(); k++) {
                        // 遍历市下面所有区/县的数据
                        DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        // 区/县对于的邮编，保存到mZipcodeDatasMap
                        mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        distrinctArray[k] = districtModel;
                        distrinctNameArray[k] = districtModel.getName();
                    }
                    // 市-区/县的数据，保存到mDistrictDatasMap
                    mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
                }
                // 省-市的数据，保存到mCitisDatasMap
                mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }
    }
}
