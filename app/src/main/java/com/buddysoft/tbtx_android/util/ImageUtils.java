/**
 * maoyu
 * 2014.5.24
 */
package com.buddysoft.tbtx_android.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.io.File;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ImageUtils {
    /**
     * 请求相册
     */
    public static final int REQUEST_CODE_GETIMAGE_BYSDCARD = 20;
    /**
     * 请求相机
     */
    public static final int REQUEST_CODE_GETIMAGE_BYCAMERA = 21;
    public static int mImageWidthScale;// 获取截图时的图片宽的比值
    public static int mImageHeigthScale;// 获取截图时的图片高的比值
    public static long millionSeconds;// 毫秒

    // public static boolean mIsSquare;

    /**
     * 获取照相机使用的目录,并创建目录
     *
     * @return
     */
    public static String getCamerPath() {
        String path = Environment.getExternalStorageDirectory()
                + File.separator + "MallPhotos" + File.separator;

        File filePath = new File(path);
        if (!filePath.exists()) {
            filePath.mkdir();
        }

        return path;
    }

    /**
     * 使用当前时间戳拼接一个唯一的文件名
     *
     * @param
     * @return
     */
    public static String getTempFileName() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss_SS");
        String fileName = format.format(new Timestamp(System
                .currentTimeMillis()));
        return fileName;
    }

    /**
     * 缓存图片
     */
    public static DisplayImageOptions options = new DisplayImageOptions.Builder()
            .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
            .considerExifParams(true) // 是否考虑JPEG图像EXIF参数（旋转，翻转）
            .imageScaleType(ImageScaleType.IN_SAMPLE_INT) // 设置图片以如何的编码方式显示
            .bitmapConfig(Bitmap.Config.RGB_565) // 设置图片的解码类型
            .decodingOptions(new BitmapFactory.Options()) // 设置图片的解码配置
            .resetViewBeforeLoading(true) // 设置图片在下载前是否重置，复位 ;
//			.showImageForEmptyUri(R.mipmap.empty_photo)
//			.showImageOnFail(R.mipmap.empty_photo)
//			.showImageOnLoading(R.mipmap.empty_photo)
            .build();
    public static ImageLoader mImageLoader;

    public static ImageLoader imgLoader(Context context) {
        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(ImageLoaderConfiguration.createDefault(context));
        return mImageLoader;

    }

    /**
     * 将标准的时间格式转化为毫秒
     */
    @SuppressLint("SimpleDateFormat")
    public static long transformTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            millionSeconds = sdf.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }// 毫秒
        return millionSeconds;
    }

    /**
     * 图片裁剪
     *
     * @param bgimage
     * @param newWidth
     * @param newHeight
     * @return
     */
    public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
                                   double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }

    public static Bitmap small(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(0.8f, 0.8f); // 长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
        int bmpSize = resizeBmp.getRowBytes() * resizeBmp.getHeight();
        if (bmpSize / 1024 > 32) {
            return small(resizeBmp);
        }
        return resizeBmp;
    }


    /**
     * 图片加载配置
     */
    public static DisplayImageOptions getOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                // // 设置图片在下载期间显示的图片
                // .showImageOnLoading(R.drawable.small_image_holder_listpage)
                // // 设置图片Uri为空或是错误的时候显示的图片
                // .showImageForEmptyUri(R.drawable.small_image_holder_listpage)
                // // 设置图片加载/解码过程中错误时候显示的图片
                // .showImageOnFail(R.drawable.small_image_holder_listpage)
                .cacheInMemory(true)
                // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)// 设置下载的图片是否缓存在SD卡中
                .considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY)// 设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型
                // .decodingOptions(android.graphics.BitmapFactory.Options
                // decodingOptions)//设置图片的解码配置
                .considerExifParams(true)
                // 设置图片下载前的延迟
                // .delayBeforeLoading(int delayInMillis)//int
                // delayInMillis为你设置的延迟时间
                // 设置图片加入缓存前，对bitmap进行设置
                // 。preProcessor(BitmapProcessor preProcessor)
                .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
                // .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
                .displayer(new FadeInBitmapDisplayer(100))// 淡入
                .build();

        return options;
    }
}
