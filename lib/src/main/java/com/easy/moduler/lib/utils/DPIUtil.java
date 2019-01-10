package com.easy.moduler.lib.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * 屏幕属性，各个密度单位转化
 */
public class DPIUtil {

    private static float mDensity = DisplayMetrics.DENSITY_DEFAULT;
    private static Display defaultDisplay;

    /**
     * 设置全局的density
     *
     * @param density getDisplayMetrics().density的值，在application中设置
     */
    public static void setDensity(float density) {
        mDensity = density;
    }

    /**
     * 取出density
     *
     * @return getDisplayMetrics().density的值
     */
    public static float getDensity() {
        return mDensity;
    }

    /**
     * 取得getDefaultDisplay的值
     */
    public static Display getDefaultDisplay(Context context) {
        if (null == defaultDisplay) {
            WindowManager systemService = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            defaultDisplay = systemService.getDefaultDisplay();
        }
        return defaultDisplay;
    }

    /**
     * 返回制定比例的屏幕宽度
     *
     * @param percent 百分比，比如0.3
     * @return 屏幕宽度×percent值
     */
    public static int percentWidth(Context context, float percent) {
        return (int) (getWidth(context) * percent);
    }

    /**
     * 返回制定比例的屏幕高度
     *
     * @param percent 百分比，比如0.3
     * @return 屏幕高度×percent值
     */
    public static int percentHeight(Context context, float percent) {
        return (int) (getHeight(context) * percent);
    }

    /**
     * dip转像素
     *
     * @param dipValue 指定dip值
     * @return 转换后的像素
     */
    public static int dip2px(float dipValue) {
        return (int) (dipValue * mDensity + 0.5f);
    }

    /**
     * 像素转dip
     *
     * @param pxValue 指定像素值
     * @return 转换后的dip
     */
    public static int px2dip(Context context, float pxValue) {
        return (int) (pxValue / mDensity + 0.5f);
    }

    /**
     * 获取屏幕宽度(in px)
     *
     * @return 屏幕宽度
     */
    public static int getWidth(Context context) {
        return getDefaultDisplay(context).getWidth();
    }

    /**
     * 获取屏幕高度(in px)
     *
     * @return 屏幕高度
     */
    public static int getHeight(Context context) {
        return getDefaultDisplay(context).getHeight();
    }

    /**
     * 像素转sp
     *
     * @param pxValue 指定像素值
     * @return 转换后的sp
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * sp转像素
     *
     * @param spValue 指定sp值
     * @return 转换后的像素
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) ((spValue - 0.5f) * fontScale);
    }

    /**
     * 获取屏幕高度的nDesignScreenWidth分之nDesignValue
     *
     * @param nDesignValue       分子
     * @param nDesignScreenWidth 分母
     * @return 屏幕高度×（nDesignValue/nDesignScreenWidth）四舍五入
     */
    public static int getWidthByDesignValue(Context context, int nDesignValue, int nDesignScreenWidth) {
        return (int) (getWidth(context) * nDesignValue / (float) nDesignScreenWidth + 0.5f);
    }

    /**
     * 获取屏幕高度的720分之nDesignValue
     *
     * @param nDesignValue 分子
     * @return 屏幕高度×（nDesignValue/720）
     */
    public static int getWidthByDesignValue720(Context context, int nDesignValue) {
        return getWidthByDesignValue(context, nDesignValue, 720);
    }

    public static int getThemeColor(Context c, @AttrRes int themeColor) {
        TypedArray array = c.getTheme().obtainStyledAttributes(new int[]{themeColor});
        int mAccentColor = array.getColor(0, 0);
        array.recycle();
        return mAccentColor;
    }

    public static Drawable getThemeDrawable(Context c, @AttrRes int themeDrawable) {
        TypedArray array = c.getTheme().obtainStyledAttributes(new int[]{themeDrawable});
        Drawable d = array.getDrawable(0);
        array.recycle();
        return d;
    }

    public static int getThemeDimension(Context c, @AttrRes int themeDimen) {
        TypedArray array = c.getTheme().obtainStyledAttributes(new int[]{themeDimen});
        int size = array.getDimensionPixelOffset(0, 0);
        array.recycle();
        return size;
    }
}
