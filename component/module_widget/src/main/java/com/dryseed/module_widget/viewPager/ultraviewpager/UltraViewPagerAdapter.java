/*
 *
 *  MIT License
 *
 *  Copyright (c) 2017 Alibaba Group
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 *
 */

package com.dryseed.module_widget.viewPager.ultraviewpager;

import android.database.DataSetObserver;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by mikeafc on 15/11/25.
 */
class UltraViewPagerAdapter extends PagerAdapter {
    interface IUltraViewPagerCenterListener {
        void center();

        void resetPosition();
    }

    private static final int INFINITE_RATIO = 400;

    private PagerAdapter adapter;
    private boolean enableLoop;

    /**
     * Ensure that the first item is in the middle when enabling loop-mode
     */
    private boolean hasCentered;
    private int infiniteRatio;
    private int screenWidth;
    private IUltraViewPagerCenterListener centerListener;

    private SparseArray<View> viewArray = new SparseArray<>();

    private UltraViewPager mViewPager;

    private Runnable applyTransformerRunnable = new Runnable() {
        @Override
        public void run() {
            if (mViewPager != null) {
                mViewPager.updateTransforming();
            }
        }
    };

    public void setViewPager(UltraViewPager viewPager) {
        mViewPager = viewPager;
    }


    public UltraViewPagerAdapter(PagerAdapter adapter) {
        this.adapter = adapter;
        infiniteRatio = INFINITE_RATIO;
    }

    @Override
    public int getCount() {
        int count;
        if (enableLoop) {
            if (adapter.getCount() == 0) {
                count = 0;
            } else {
                count = adapter.getCount() * infiniteRatio;
            }
        } else {
            count = adapter.getCount();
        }
        return count;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int realPosition = getRealPosition(position);
        if (screenWidth == 0) {
            screenWidth = container.getResources().getDisplayMetrics().widthPixels;
        }
        Object item = adapter.instantiateItem(container, realPosition);
        viewArray.put(realPosition, (View) item);

        return item;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        int realPosition = getRealPosition(position);

        adapter.destroyItem(container, realPosition, object);

        viewArray.remove(realPosition);
    }

    int getRealPosition(int position) {
        int realPosition = position;
        if (enableLoop && adapter.getCount() != 0) {
            realPosition = position % adapter.getCount();
        }
        return realPosition;
    }

    View getViewAtPosition(int position) {
        return viewArray.get(position);
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        // only need to set the center position  when the loop is enabled
        if (!hasCentered) {
            if (adapter.getCount() > 0 && getCount() > adapter.getCount()) {
                centerListener.center();
            }
        }
        hasCentered = true;
        adapter.finishUpdate(container);

        if (mViewPager != null) {
            mViewPager.post(applyTransformerRunnable);
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return adapter.isViewFromObject(view, object);
    }

    @Override
    public void restoreState(Parcelable bundle, ClassLoader classLoader) {
        adapter.restoreState(bundle, classLoader);
    }

    @Override
    public Parcelable saveState() {
        return adapter.saveState();
    }

    @Override
    public void startUpdate(ViewGroup container) {
        adapter.startUpdate(container);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        int virtualPosition = position % adapter.getCount();
        return adapter.getPageTitle(virtualPosition);
    }

    @Override
    public float getPageWidth(int position) {
        return adapter.getPageWidth(position);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        adapter.setPrimaryItem(container, position, object);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        adapter.unregisterDataSetObserver(observer);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        adapter.registerDataSetObserver(observer);
    }

    @Override
    public void notifyDataSetChanged() {
        if (mViewPager != null) {
            // 确保数据更新时，停止当前的fakeDrag
            mViewPager.stopCurrentScrollAnimation();
        }

        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return adapter.getItemPosition(object);
    }

    public PagerAdapter getAdapter() {
        return adapter;
    }

    public int getRealCount() {
        return adapter.getCount();
    }

    void setEnableLoop(boolean status) {
        if (enableLoop == status) {
            return;
        }

        this.enableLoop = status;
        notifyDataSetChanged();
        if (!status) {
            centerListener.resetPosition();
        }
    }

    boolean isEnableLoop() {
        return enableLoop;
    }

    void setCenterListener(IUltraViewPagerCenterListener listener) {
        centerListener = listener;
    }

    void setInfiniteRatio(int infiniteRatio) {
        this.infiniteRatio = infiniteRatio;
    }
}
