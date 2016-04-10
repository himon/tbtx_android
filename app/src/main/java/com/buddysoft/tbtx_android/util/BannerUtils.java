package com.buddysoft.tbtx_android.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.ToxicBakery.viewpager.transforms.ABaseTransformer;
import com.ToxicBakery.viewpager.transforms.AccordionTransformer;
import com.ToxicBakery.viewpager.transforms.BackgroundToForegroundTransformer;
import com.ToxicBakery.viewpager.transforms.CubeInTransformer;
import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import com.ToxicBakery.viewpager.transforms.DefaultTransformer;
import com.ToxicBakery.viewpager.transforms.DepthPageTransformer;
import com.ToxicBakery.viewpager.transforms.FlipHorizontalTransformer;
import com.ToxicBakery.viewpager.transforms.FlipVerticalTransformer;
import com.ToxicBakery.viewpager.transforms.ForegroundToBackgroundTransformer;
import com.ToxicBakery.viewpager.transforms.RotateDownTransformer;
import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;
import com.ToxicBakery.viewpager.transforms.StackTransformer;
import com.ToxicBakery.viewpager.transforms.ZoomInTransformer;
import com.ToxicBakery.viewpager.transforms.ZoomOutTranformer;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.buddysoft.tbtx_android.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.List;

/**
 * 本库来自:https://github.com/saiwu-bigkoo/Android-ConvenientBanner
 * 使用本库前需要添加如下配置
 * compile 'com.bigkoo:convenientbanner:2.0.5'(本库项目地址)
 * compile 'com.ToxicBakery.viewpager.transforms:view-pager-transforms:1.2.32@aar' (动画效果项目地址)
 * 使用本库的activity中如果需要自动翻页，只需在activity添加如下代码
 * // 开始自动翻页
 *
 * @Override protected void onResume() {
 * super.onResume();
 * //开始自动翻页
 * convenientBanner.startTurning(2000);
 * }
 * <p/>
 * // 停止自动翻页
 * @Override protected void onPause() {
 * super.onPause();
 * //停止翻页
 * convenientBanner.stopTurning();
 * }
 * Created by hyy on 2016/3/23.
 */
public class BannerUtils {

    private ConvenientBanner mConvenientBanner;
    private List<Integer> mIntImg;
    private List<String> mStrImg;
    private Activity mActivity;
    private String[] transformerList = {DefaultTransformer.class.getSimpleName(),
            AccordionTransformer.class.getSimpleName(),
            BackgroundToForegroundTransformer.class.getSimpleName(),
            CubeInTransformer.class.getSimpleName(),
            CubeOutTransformer.class.getSimpleName(),
            DepthPageTransformer.class.getSimpleName(),
            FlipHorizontalTransformer.class.getSimpleName(),
            FlipVerticalTransformer.class.getSimpleName(),
            ForegroundToBackgroundTransformer.class.getSimpleName(),
            RotateDownTransformer.class.getSimpleName(),
            RotateUpTransformer.class.getSimpleName(),
            StackTransformer.class.getSimpleName(),
            ZoomInTransformer.class.getSimpleName(),
            ZoomOutTranformer.class.getSimpleName()
    };
    private int mAnimIndex = 0;


    private OnItemClickListener mOnItemClickListener;

    /**
     * @param activity
     * @param convenientBanner
     * @param intImg              本地图片路径
     * @param strImg              网络图片路径(本地路径和网络路径填写一个即可)
     * @param onItemClickListener 图片点击事件，需要activity  implements OnItemClickListener
     * @param animIndex           动画效果，取值范围0-13
     */
    public BannerUtils(Activity activity, ConvenientBanner convenientBanner, List<Integer> intImg, List<String> strImg, OnItemClickListener onItemClickListener, int animIndex) {
        mConvenientBanner = convenientBanner;
        mIntImg = intImg;
        mStrImg = strImg;
        mActivity = activity;
        mOnItemClickListener = onItemClickListener;
        mAnimIndex = animIndex;
    }

    public void init() {
        initImageLoader();
        String animString = "";
        if (mAnimIndex < transformerList.length && mAnimIndex >= 0) {
            animString = transformerList[mAnimIndex];
        } else {
            animString = transformerList[0];
        }

        ABaseTransformer transforemer = null;
        try {
            Class cls = Class.forName("com.ToxicBakery.viewpager.transforms." + animString);
            transforemer = (ABaseTransformer) cls.newInstance();
            //部分3D特效需要调整滑动速度
            if (animString.equals("StackTransformer")) {
                mConvenientBanner.setScrollDuration(1200);
            }
        } catch (Exception e) {
        }

        if (mIntImg != null) {
            mConvenientBanner.setPages(
                    new CBViewHolderCreator<LocalImageHolderView>() {
                        @Override
                        public LocalImageHolderView createHolder() {
                            return new LocalImageHolderView();
                        }
                    }, mIntImg);

        } else {
            if (mStrImg != null) {
                mConvenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
                    @Override
                    public NetworkImageHolderView createHolder() {
                        return new NetworkImageHolderView();
                    }
                }, mStrImg);
            }
        }
        mConvenientBanner
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                        //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
//                .setOnPageChangeListener(this)//监听翻页事件
                .setPageTransformer(transforemer)
                .setOnItemClickListener(mOnItemClickListener);

    }

    //初始化网络图片缓存库
    private void initImageLoader() {
        //网络图片例子,结合常用的图片缓存库UIL,你可以根据自己需求自己换其他网络图片库
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().
                showImageForEmptyUri(R.drawable.ic_default_adimage)
                .cacheInMemory(true).cacheOnDisk(true).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                mActivity).defaultDisplayImageOptions(defaultOptions)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }

    public class LocalImageHolderView implements Holder<Integer> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, Integer data) {
            imageView.setImageResource(data);
        }
    }

    public class NetworkImageHolderView implements Holder<String> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            imageView.setImageResource(R.drawable.ic_default_adimage);
            ImageLoader.getInstance().displayImage(data, imageView);
        }
    }
}
