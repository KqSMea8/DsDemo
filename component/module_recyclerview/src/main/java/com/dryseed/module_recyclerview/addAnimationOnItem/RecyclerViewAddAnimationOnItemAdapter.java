package com.dryseed.module_recyclerview.addAnimationOnItem;

import android.animation.Animator;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.airbnb.lottie.LottieAnimationView;
import com.dryseed.module_recyclerview.R;
import com.easy.moduler.lib.utils.LogUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author caiminming
 */
public class RecyclerViewAddAnimationOnItemAdapter extends RecyclerView.Adapter<RecyclerViewAddAnimationOnItemAdapter.SimpleViewHolder> {

    List<String> mData;

    IItemClickListener mItemClickListener;

    int[] flags;

    Map<Integer, LottieAnimationView> mLottieAnimationViewHashMap = new HashMap<>();
    Map<Integer, CountDownTimer> mCountDownTimerHashMap = new HashMap<>();

    public RecyclerViewAddAnimationOnItemAdapter() {
    }

    public RecyclerViewAddAnimationOnItemAdapter(List<String> data) {
        this.mData = data;
        flags = new int[data.size()];
    }

    public void setData(List<String> data) {
        this.mData = data;
        flags = new int[data.size()];
        notifyDataSetChanged();
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_animation_on_item_layout, parent, false);
        SimpleViewHolder holder = new SimpleViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {
        if (mData == null || mData.size() <= 0) {
            return;
        }

        if (flags[position] == 1) {
            viewHolder.lottieImageView.setVisibility(View.VISIBLE);
        } else if (flags[position] == 2) {
            viewHolder.lottieImageView.setVisibility(View.VISIBLE);
        } else {
            viewHolder.lottieImageView.setVisibility(View.GONE);
        }

        viewHolder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CountDownTimer countDownTimer = new CountDownTimer(3500, 3500) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        //开启关闭动画
                        LogUtils.d("countDownTimer onFinish");
                        flags[position] = 2;
                        playHideAnimation(viewHolder, position);
                    }
                };
                countDownTimer.start();

                Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        //开启展示动画
                        LogUtils.d("category_del_show onAnimationStart");
                        flags[position] = 1;
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        LogUtils.d("category_del_show onAnimationEnd");
                        viewHolder.lottieImageView.removeAnimatorListener(this);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        LogUtils.d("category_del_show onAnimationCancel");
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                };

                viewHolder.lottieImageView.addAnimatorListener(animatorListener);
                viewHolder.lottieImageView.setVisibility(View.VISIBLE);
                viewHolder.lottieImageView.setAnimation("category_del_show.json");
                viewHolder.lottieImageView.loop(false);
                viewHolder.lottieImageView.playAnimation();
                mLottieAnimationViewHashMap.put(position, viewHolder.lottieImageView);
                mCountDownTimerHashMap.put(position, countDownTimer);
            }
        });
    }

    private void playHideAnimation(SimpleViewHolder viewHolder, final int position) {
        viewHolder.lottieImageView.setAnimation("category_del_hide.json");
        viewHolder.lottieImageView.loop(false);
        viewHolder.lottieImageView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                LogUtils.d("category_del_hide onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                LogUtils.d("category_del_hide onAnimationEnd");
                viewHolder.lottieImageView.setVisibility(View.GONE);
                viewHolder.btn.setVisibility(View.VISIBLE);
                viewHolder.lottieImageView.removeAnimatorListener(this);
                flags[position] = 0;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                LogUtils.d("category_del_hide onAnimationCancel");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        viewHolder.lottieImageView.playAnimation();
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void stopAnimation() {
        Iterator<Map.Entry<Integer, LottieAnimationView>> it = mLottieAnimationViewHashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, LottieAnimationView> entry = it.next();
            LottieAnimationView lottieAnimationView = entry.getValue();
            if (flags[entry.getKey()] == 1) {
                //开启展示动画
                CountDownTimer countDownTimer = mCountDownTimerHashMap.get(entry.getKey());
                countDownTimer.onFinish();
                countDownTimer.cancel();
            }
            if (flags[entry.getKey()] == 2) {
                //开启关闭动画
            }
        }

        mLottieAnimationViewHashMap.clear();
        mCountDownTimerHashMap.clear();
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        View lottieLayout;
        ImageView btn;
        LottieAnimationView lottieImageView;

        SimpleViewHolder(View itemView) {
            super(itemView);
            lottieLayout = itemView.findViewById(R.id.category_lottie_layout);
            lottieImageView = itemView.findViewById(R.id.category_lottie_view);
            btn = itemView.findViewById(R.id.category_operate_btn);
        }
    }

    public void setItemClickListener(IItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public interface IItemClickListener {
        void onItemClickListener(View view, int position);
    }
}
