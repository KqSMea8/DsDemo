package com.dryseed.module_navigation.lib.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.RadioGroup;
import com.dryseed.module_navigation.lib.adapter.IPagerAdapter;
import com.dryseed.module_navigation.lib.config.TabStyleConstants;
import com.dryseed.module_navigation.lib.entity.TabEntity;
import com.easy.moduler.lib.utils.DPIUtil;

/**
 * @author caiminming
 */
public class TopMenuPagerSlidingTabStrip extends TopMenuBasePagerSlidingTabStrip {

    private float density;

    public TopMenuPagerSlidingTabStrip(Context context) {
        super(context);
    }

    public TopMenuPagerSlidingTabStrip(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TopMenuPagerSlidingTabStrip(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    void addCustomView(PagerAdapter adapter, int position) {
        TabEntity tabEntity = ((IPagerAdapter) adapter).getData().get(position);
        int showStyle = tabEntity.getTabStyleEntity().getShowStyle();
        String title = tabEntity.getTitle();
        String icon = tabEntity.getTabStyleEntity().getIcon();

        switch (showStyle) {
            case TabStyleConstants.TAB_STYLE_TEXT:
                addConfigColorTextTab(position, title);
                break;
            case TabStyleConstants.TAB_STYLE_LEFT_IMAGE_RIGHT_TEXT:
                addIconTextTab(position, icon, title, Gravity.LEFT);
                break;
            case TabStyleConstants.TAB_STYLE_IMAGE_BG_TEXT:
                addBackgroundImageTextTab(position, icon, title);
                break;
            case TabStyleConstants.TAB_STYLE_IMAGE_BG_NO_TEXT:
                addBackgroundImageTab(position, icon);
                break;
            case TabStyleConstants.TAB_STYLE_RIGHT_IMAGE_LEFT_TEXT:
                addIconTextTab(position, icon, title, Gravity.RIGHT);
                break;
            default:
                addCommonTextTab(position, title);
                break;
        }
    }

    /**
     * 文本颜色可配
     *
     * @param position 位置
     * @param title    标题
     */
    private void addConfigColorTextTab(int position, String title) {
        DraweeRadioButton tab = new DraweeRadioButton(getContext());
        setConfigColorTextTab(tab, true, position, title);
    }

    private void setConfigColorTextTab(DraweeRadioButton tab, boolean add, int position, String title) {
        tab.setButtonDrawable(new ColorDrawable());
        tab.setText(title);
        tab.setGravity(Gravity.CENTER);
        tab.setLines(1);
        tab.setIncludeFontPadding(false);
        tab.setBackgroundColor(Color.TRANSPARENT);
        if (add) {
            addTab(position, tab);
        }
    }

    /**
     * 显示图标和文本
     *
     * @param position 位置
     * @param url      地址
     * @param title    标题
     */
    private void addIconTextTab(int position, String url, final String title, int gravity) {
        final DraweeRadioButton tab = new DraweeRadioButton(getContext());
        setIconTextTab(tab, true, position, url, title, gravity);
    }

    private void setIconTextTab(final DraweeRadioButton tab, boolean add,
                                int position, String url, final String title, int gravity) {
        tab.setButtonDrawable(new ColorDrawable());
        tab.setText(title);
        tab.setGravity(Gravity.CENTER);
        tab.setLines(1);
        tab.setIncludeFontPadding(false);
        tab.setBackgroundColor(Color.TRANSPARENT);
        tab.setCompoundDrawablePadding(DPIUtil.dip2px(3));
        if (add) {
            addTab(position, tab);
        }

        if (!TextUtils.isEmpty(url)) {
            tab.loadCompoundDrawable(url, gravity, new DraweeRadioButton.ImageSizeListener() {
                @Override
                public Rect onImageSizeReady(String url, int gravity, int width, int height) {
                    int stripHeight = DPIUtil.dip2px(24);
                    int scaledWidth = stripHeight * width / height;

                    return new Rect(0, 0, scaledWidth, stripHeight);
                }
            });
        }
    }

    /**
     * 显示背景图片和文本
     *
     * @param position 位置
     * @param url      图片url
     * @param title    标题
     */
    private void addBackgroundImageTextTab(int position, String url, String title) {
        final DraweeRadioButton tab = new DraweeRadioButton(getContext());
        setBackgroundImageTextTab(tab, true, position, url, title);
    }


    private void setBackgroundImageTextTab(final DraweeRadioButton tab, boolean add, int position, String url, String title) {
        tab.setButtonDrawable(new ColorDrawable());
        tab.setText(title);
        tab.setGravity(Gravity.CENTER);
        tab.setLines(1);
        tab.setIncludeFontPadding(false);
        if (add) {
            addTab(position, tab);
        }

        if (!TextUtils.isEmpty(url)) {
            tab.loadBackgroundImage(url, new DraweeRadioButton.ImageSizeListener() {
                @Override
                public Rect onImageSizeReady(String url, int gravity, int width, int height) {
                    int stripHeight = (int) (getHeight() - mIndicatorHeight * density);
                    int scaledW = width * stripHeight / height;
                    return new Rect(0, 0, scaledW, stripHeight);
                }
            });
        }
    }

    /**
     * 只设置背景图
     *
     * @param position 位置
     * @param url      背景图url
     */
    private void addBackgroundImageTab(int position, String url) {
        final DraweeRadioButton tab = new DraweeRadioButton(getContext());
        setBackgroundImageTab(tab, true, position, url);
    }

    private void setBackgroundImageTab(final DraweeRadioButton tab, boolean add, int position, String url) {
        tab.setButtonDrawable(new ColorDrawable());
        tab.setGravity(Gravity.CENTER);
        tab.setLines(1);
        tab.setIncludeFontPadding(false);
        tab.setBackgroundColor(Color.TRANSPARENT);
        if (add) {
            addTab(position, tab);
        }

        if (!TextUtils.isEmpty(url)) {
            tab.loadBackgroundImage(url, new DraweeRadioButton.ImageSizeListener() {
                @Override
                public Rect onImageSizeReady(String url, int gravity, int width, int height) {
                    int stripHeight = (int) (getHeight() - mIndicatorHeight * density);
                    int scaledW = width * stripHeight / height;
                    RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(scaledW, stripHeight);
                    tab.setLayoutParams(params);
                    return new Rect(0, 0, scaledW, stripHeight);
                }
            });
        }
    }

    /**
     * 普通文本,使用默认颜色
     *
     * @param position 位置
     * @param title    标题
     */
    private void addCommonTextTab(int position, String title) {
        DraweeRadioButton tab = new DraweeRadioButton(getContext());
        setCommonTextTab(tab, true, position, title);
    }


    private void setCommonTextTab(DraweeRadioButton tab, boolean add, int position, String title) {
        tab.setButtonDrawable(new ColorDrawable());
        tab.setText(title);
        tab.setGravity(Gravity.CENTER);
        tab.setLines(1);
        tab.setIncludeFontPadding(false);
        tab.setBackgroundColor(Color.TRANSPARENT);
        if (add) {
            addTab(position, tab);
        }

        density = this.getResources().getDisplayMetrics().density;
    }
}
