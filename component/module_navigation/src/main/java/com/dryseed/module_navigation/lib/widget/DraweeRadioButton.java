package com.dryseed.module_navigation.lib.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeHolder;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.lang.reflect.Field;

/**
 * author: liuchun
 * date: 2018/8/14
 * <p>
 * 支持左侧，右侧加载gif图的RadioButton，使用Fresco实现
 * <hrefs>https://github.com/facebook/fresco/blob/master/docs/_docs/writing-custom-views.md</hrefs>
 */
public class DraweeRadioButton extends android.support.v7.widget.AppCompatRadioButton {

    private DraweeHolder<GenericDraweeHierarchy> mBackgroundDraweeHolder;
    private DraweeHolder<GenericDraweeHierarchy> mCompundDraweeHolder;

    private String mCompoundImageUrl;
    private boolean mInitialised = false;

    public DraweeRadioButton(Context context) {
        super(context);
        init(context);
    }

    public DraweeRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DraweeRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        if (mInitialised) {
            return;
        }

        mInitialised = true;
        GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(context.getResources())
                .setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .build();
        mBackgroundDraweeHolder = DraweeHolder.create(hierarchy, context);
        mCompundDraweeHolder = DraweeHolder.create(hierarchy, context);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        onAttach();
    }

    @Override
    public void onFinishTemporaryDetach() {
        super.onFinishTemporaryDetach();
        onAttach();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        onDetach();
    }

    @Override
    public void onStartTemporaryDetach() {
        super.onStartTemporaryDetach();
        onDetach();
    }

    @Override
    protected boolean verifyDrawable(@NonNull Drawable who) {
        return super.verifyDrawable(who);
    }

    private void onAttach() {
        mBackgroundDraweeHolder.onAttach();
        mCompundDraweeHolder.onAttach();
    }

    private void onDetach() {
        mBackgroundDraweeHolder.onDetach();
        mCompundDraweeHolder.onDetach();
    }

    private void setCompoundDrawable(int gravity, Drawable drawable) {
        Drawable left = null;
        Drawable top = null;
        Drawable right = null;
        Drawable bottom = null;
        switch (gravity) {
            case Gravity.LEFT:
                left = drawable;
                break;
            case Gravity.TOP:
                top = drawable;
                break;
            case Gravity.RIGHT:
                right = drawable;
                break;
            case Gravity.BOTTOM:
                bottom = drawable;
                break;
            default:
                break;
        }
        // 注意，这里必须调用super的方法
        super.setCompoundDrawables(left, top, right, bottom);
    }


    @Override
    public void setCompoundDrawables(@Nullable Drawable left, @Nullable Drawable top,
                                     @Nullable Drawable right, @Nullable Drawable bottom) {
        clearCompoundDrawableHolder();
        super.setCompoundDrawables(left, top, right, bottom);
    }

    @Override
    public void setCompoundDrawablesWithIntrinsicBounds(int left, int top, int right, int bottom) {
        clearCompoundDrawableHolder();
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }

    @Override
    public void setCompoundDrawablesWithIntrinsicBounds(
            @Nullable Drawable left, @Nullable Drawable top, @Nullable Drawable right, @Nullable Drawable bottom) {
        clearCompoundDrawableHolder();
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }

    private void clearCompoundDrawableHolder() {
        init(getContext());
        mCompundDraweeHolder.setController(null);
        mCompundDraweeHolder.getTopLevelDrawable().setCallback(null);
    }

    @Override
    public void setBackground(Drawable background) {
        clearBackgroundHolder();
        super.setBackground(background);
    }

    @Override
    public void setBackgroundColor(int color) {
        clearBackgroundHolder();
        super.setBackgroundColor(color);
    }

    @Override
    public void setBackgroundResource(int resid) {
        clearBackgroundHolder();
        super.setBackgroundResource(resid);
    }

    @Override
    public void setBackgroundDrawable(Drawable background) {
        clearBackgroundHolder();
        super.setBackgroundDrawable(background);
    }

    private void clearBackgroundHolder() {
        init(getContext());
        mBackgroundDraweeHolder.setController(null);
        mBackgroundDraweeHolder.getTopLevelDrawable().setCallback(null);
    }

    // 注意这里必须调用super的方法，因为相关方法被重写了
    private void setBackgroundInternal(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
    }

    /**
     * 加载图片到指定位置的Drawable上，其他位置传null
     *
     * @param url          图片云端地址
     * @param gravity      CompoundDrawable的位置
     * @param sizeListener 大小监听器
     */
    public void loadCompoundDrawable(final String url, final int gravity, final ImageSizeListener sizeListener) {
        mCompoundImageUrl = url;
        ImageRequest request = ImageRequestBuilder
                .newBuilderWithSource(Uri.parse(url))
                .setResizeOptions(getResizeOption())
                .setProgressiveRenderingEnabled(true)
                .setImageDecodeOptions(ImageDecodeOptions.newBuilder().setDecodePreviewFrame(true).build())
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(mCompundDraweeHolder.getController())
                .setControllerListener(new BaseControllerListener<ImageInfo>() {
                    @Override
                    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                        super.onFinalImageSet(id, imageInfo, animatable);
                        Drawable drawable = mCompundDraweeHolder.getTopLevelDrawable();
                        if (sizeListener != null) {
                            Rect bounds = sizeListener.onImageSizeReady(
                                    url, gravity, imageInfo.getWidth(), imageInfo.getHeight());
                            drawable.setBounds(bounds);
                        }
                        // 设置到TextView的CompoundDrawable上
                        setCompoundDrawable(gravity, drawable);
                    }
                })
                .build();
        mCompundDraweeHolder.setController(controller);
    }

    /**
     * 加载图片到控件的背景上
     *
     * @param url
     * @param sizeListener
     */
    public void loadBackgroundImage(final String url, final ImageSizeListener sizeListener) {
        ImageRequest request = ImageRequestBuilder
                .newBuilderWithSource(Uri.parse(url))
                .setResizeOptions(getResizeOption())
                .setProgressiveRenderingEnabled(true)
                .setImageDecodeOptions(ImageDecodeOptions.newBuilder().setDecodePreviewFrame(true).build())
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(mBackgroundDraweeHolder.getController())
                .setAutoPlayAnimations(true)
                .setControllerListener(new BaseControllerListener<ImageInfo>() {
                    @Override
                    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                        super.onFinalImageSet(id, imageInfo, animatable);
                        Drawable drawable = mBackgroundDraweeHolder.getTopLevelDrawable();
                        if (sizeListener != null) {
                            Rect bounds = sizeListener.onImageSizeReady(
                                    url, Gravity.CENTER, imageInfo.getWidth(), imageInfo.getHeight());
                            drawable.setBounds(bounds);
                        }

                        // 更新背景图
                        setBackgroundInternal(drawable);
                    }
                })
                .build();
        mBackgroundDraweeHolder.setController(controller);
    }


    @Override
    public int getMaxWidth() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            // 该方法API 16以上才可用
            return super.getMaxWidth();
        }

        return getMaxSize("mMaxWidth");
    }

    @Override
    public int getMaxHeight() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            // 该方法API 16以上才可用
            return super.getMaxHeight();
        }
        return getMaxSize("mMaximum");
    }

    private int getMaxSize(String fieldName) {

        try {
            Field field = TextView.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            int size = (Integer) field.get(this);
            if (size > 0 && size < Integer.MAX_VALUE) {
                return size;
            }
        } catch (Exception e) {
            /* ignore */
        }
        // 默认无限制
        return Integer.MAX_VALUE;
    }

    /**
     * 计算ResizeOptions参数，限制分辨率过大的图片
     * 1. 如果指定了LayoutParams为固定尺寸，则根据LayoutParams对图片进行resize
     * 2. 没有指定LayoutParams，则根据{maxWidth, maxHeight}与屏幕尺寸的关系进行裁剪
     *
     * @return resizeOption，决定了Decode Bitmap图片时的采样率
     */
    private ResizeOptions getResizeOption() {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        int width = 1;
        int height = 1;

        DisplayMetrics dm = getResources().getDisplayMetrics();
        int maxWidth = getMaxWidth();
        int maxHeight = getMaxHeight();

        if (layoutParams != null && layoutParams.width > 0) {
            width = layoutParams.width;
        } else {
            width = maxWidth > 0 ? Math.min(maxWidth, dm.widthPixels) : dm.widthPixels;
        }

        if (layoutParams != null && layoutParams.height > 0) {
            height = layoutParams.height;
        } else {
            height = maxHeight > 0 ? Math.min(maxHeight, dm.heightPixels) : dm.heightPixels / 2;
        }

        return new ResizeOptions(width, height);
    }

    public DraweeHolder<GenericDraweeHierarchy> getBackgroundDraweeHolder() {
        return mBackgroundDraweeHolder;
    }

    public DraweeHolder<GenericDraweeHierarchy> getCompundDraweeHolder() {
        return mCompundDraweeHolder;
    }

    public String getCompoundImageUrl() {
        return mCompoundImageUrl == null ? "" : mCompoundImageUrl;
    }

    public interface ImageSizeListener {
        /**
         * 加载图片，图片大小确定了，需要应用层返回Drawable的bound返回
         *
         * @param url
         * @param gravity
         * @param width   图片的宽度
         * @param height  图片的高度
         * @return Drawable的bound位置
         */
        Rect onImageSizeReady(String url, int gravity, int width, int height);
    }
}
