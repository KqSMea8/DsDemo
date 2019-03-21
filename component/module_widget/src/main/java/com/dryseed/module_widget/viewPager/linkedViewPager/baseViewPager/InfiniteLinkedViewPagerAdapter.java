package com.dryseed.module_widget.viewPager.linkedViewPager.baseViewPager;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.dryseed.module_widget.R;
import com.easy.moduler.lib.utils.LogUtils;

/**
 * @author caiminming
 */
public class InfiniteLinkedViewPagerAdapter extends PagerAdapter {

    private int[] mImageRes = {R.drawable.t0, R.drawable.t1, R.drawable.t2, R.drawable.t3};

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LogUtils.d("instantiateItem [position:%d]", position);
        ImageView imageView = new ImageView(container.getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setImageResource(mImageRes[position % mImageRes.length]);
        imageView.setBackgroundColor(0xffff0000);
        imageView.setTag(position);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return 1000;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}