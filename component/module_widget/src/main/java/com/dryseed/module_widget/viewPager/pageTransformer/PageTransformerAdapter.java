package com.dryseed.module_widget.viewPager.pageTransformer;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.dryseed.module_widget.R;

/**
 * @author caiminming
 */
public class PageTransformerAdapter extends PagerAdapter {

    private int[] mImageRes = {R.drawable.t0, R.drawable.t1, R.drawable.t2, R.drawable.t3};

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(container.getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setImageResource(mImageRes[position]);
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
        return mImageRes.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
