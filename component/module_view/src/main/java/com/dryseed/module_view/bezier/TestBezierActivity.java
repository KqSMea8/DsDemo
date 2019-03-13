package com.dryseed.module_view.bezier;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.dryseed.module_view.R;
import com.easy.moduler.lib.BaseActivity;
import com.easy.moduler.lib.utils.DPIUtil;
import com.easy.moduler.lib.utils.LogUtils;

/**
 * @author caiminming
 */
public class TestBezierActivity extends BaseActivity {

    @BindView(R.id.view_layout)
    FrameLayout mViewLayout;

    @BindView(R.id.button1)
    Button mButton1;

    View mRootView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootView = LayoutInflater.from(this).inflate(R.layout.activity_bezier_layout, null);
        setContentView(mRootView);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button1)
    public void onBezierQuadViewClick(View view) {
        mViewLayout.removeAllViews();
        BezierQuadView bezierQuadView = new BezierQuadView(this);
        mViewLayout.addView(bezierQuadView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @OnClick(R.id.button2)
    public void onBezierCubicViewClick(View view) {
        mViewLayout.removeAllViews();
        BezierCubicView bezierCubicView = new BezierCubicView(this);
        mViewLayout.addView(bezierCubicView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @OnClick(R.id.button3)
    public void onBezierShoppingCartViewClick(View view) {
        mViewLayout.removeAllViews();
        final View shoppingCartLayout = LayoutInflater.from(this).inflate(R.layout.activity_bezier_shopping_cart_layout, mViewLayout, true);
        final ImageView addBtn = shoppingCartLayout.findViewById(R.id.iv_shop_add);
        final ImageView shoppingCartBtn = shoppingCartLayout.findViewById(R.id.iv_shop_cart);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取商品坐标
                int[] goodsPointOnScreen = new int[2];
                addBtn.getLocationOnScreen(goodsPointOnScreen);

                //获取购物车坐标
                int[] shoppingCartPointOnScreen = new int[2];
                shoppingCartBtn.getLocationOnScreen(shoppingCartPointOnScreen);

                int[] addBtnPointOnScreen = new int[2];
                int[] addBtnPointInWindow = new int[2];
                int[] contentViewPointOnScreen = new int[2];
                addBtn.getLocationOnScreen(addBtnPointOnScreen);
                addBtn.getLocationInWindow(addBtnPointInWindow);
                mRootView.getLocationOnScreen(contentViewPointOnScreen);
                /*
                    getLocationOnScreen/getLocationInWindow包含了StatusBar的高度，getY不包含StatusBar的高度
                    getLocationOnScreen [x:10][y:54][getY:10.00]
                    getLocationInWindow [x:10][y:54][getY:10.00]
                 */
                LogUtils.d("addBtn getLocationOnScreen [x:%d][y:%d][getY:%.2f]", addBtnPointOnScreen[0], addBtnPointOnScreen[1], addBtn.getY());
                LogUtils.d("addBtn getLocationInWindow [x:%d][y:%d][getY:%.2f]", addBtnPointInWindow[0], addBtnPointInWindow[1], addBtn.getY());
                LogUtils.d("contentView getLocationOnScreen [x:%d][y:%d][getY:%.2f]", contentViewPointOnScreen[0], contentViewPointOnScreen[1], mRootView.getY());

                //生成商品View
                BezierShoppingCartView goodsView = new BezierShoppingCartView(TestBezierActivity.this);
                goodsView.setCircleStartPoint(goodsPointOnScreen[0], addBtnPointOnScreen[1] - contentViewPointOnScreen[1]);
                goodsView.setCircleEndPoint(
                        shoppingCartPointOnScreen[0] + DPIUtil.dip2px(40) / 2,
                        shoppingCartPointOnScreen[1] - (int) shoppingCartLayout.getY() - contentViewPointOnScreen[1]);

                //添加View并执行动画
                mViewLayout.addView(goodsView);
                goodsView.startAnimation();
            }
        });
    }
}
