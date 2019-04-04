package com.dryseed.module_widget.attr;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.blankj.utilcode.util.ToastUtils;
import com.dryseed.module_widget.R;
import com.easy.moduler.lib.BaseActivity;

/**
 * @author caiminming
 */
public class GetAttrFromXmlActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_attr_from_xml_layout);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button1)
    public void onClickBtn1(View view) {
        //Get Dimen
        //48dp，获取到的val值为144
        float val = getResources().getDimension(R.dimen.common_button_height);
        ToastUtils.showShort("val : " + val);
    }
}
