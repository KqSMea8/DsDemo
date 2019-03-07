
package com.dryseed.module_image.myImageLoader;

import android.os.Bundle;
import android.widget.ImageView;
import com.dryseed.module_image.R;
import com.dryseed.module_image.myImageLoader.lib.cache.DoubleCache;
import com.dryseed.module_image.myImageLoader.lib.config.ImageLoaderConfig;
import com.dryseed.module_image.myImageLoader.lib.core.SimpleImageLoader;
import com.dryseed.module_image.myImageLoader.lib.policy.ReversePolicy;
import com.easy.moduler.lib.BaseActivity;

/**
 * http://blog.csdn.net/bboyfeiyu/article/details/43195413
 */
public class MyImageLoaderActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageView imageView = new ImageView(this);
        setContentView(imageView);

        initImageLoader();
        SimpleImageLoader.getInstance().displayImage(imageView, "http://i.imgur.com/DvpvklR.png");
    }

    private void initImageLoader() {
        ImageLoaderConfig config = new ImageLoaderConfig()
                .setLoadingPlaceholder(R.drawable.ic_launcher_background)
                .setNotFoundPlaceholder(R.drawable.ic_launcher)
                .setCache(new DoubleCache(this))
                .setThreadCount(4)
                .setLoadPolicy(new ReversePolicy());
        SimpleImageLoader.getInstance().init(config);
    }

    @Override
    protected void onDestroy() {
        SimpleImageLoader.getInstance().stop();
        super.onDestroy();
    }

}
