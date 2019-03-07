package com.easy.moduler.lib;

public class Constants {
    //==================URL========================//
    //组件的包名前缀
    public static final String MODULE_PACKAGE_PRE = "com.easy.moduler.module_";
    //服务器的包名
    public static final String SERVICE_PACKAGE_NAME = "com.easy.moduler.module_service";

    //==================MessagerConstants============//
    public static final String TAG = "Message";

    public static final String MODULE_NAME = "module";
    public static final String MESSAGE_DATA = "message_data";
    public static final String REGISTER_ID = "registerId";
    public static final String REGISTER_RES = "registerRes";//注册结果  0 失败 1 成功
    public static final String NOTICE_MSG = "notice_message";

    public static final int REGISTER_SEC = 1;
    public static final int REGISTER_FAIL = 0;


    //==========模块以及模块下的事件============//
    /**
     * 模块定义说明：
     * <p>
     * 模块暂定为0x1  ->0xf   例如：0xa
     * <p>
     * 模块下的事件  暂定为：模块名＋事件id   例如：0xa001
     */
    public static final int ROUTER_OPEN_URL = 0x0000;//打开制定url

    public static final int MODULE_S = 0x0;//服务器标识

    public static final int MODULE_PRINT_LOG = 0x0001;//log打印事件

    // module_a
    public static final int MODULE_A = 0x0a;
    public static final int MODULE_A_EVENT001 = 0xa001;

    // module_b
    public static final int MODULE_B = 0x0b;
    public static final int MODULE_B_EVENT001 = 0xb001;

    // module_recyclerview
    public static final int MODULE_RECYCLERVIEW = 0x0c;
    public static final int MODULE_RECYCLERVIEW_EVENT001 = 0xc001;

    // module_animation
    public static final int MODULE_ANIMATION = 0x0d;

    // module_widget
    public static final int MODULE_WIDGET = 0x0e;

    // module_view
    public static final int MODULE_VIEW = 0x0f;

    // module_uiautomator
    public static final int MODULE_UIAUTOMATOR = 0x10;

    // module_image
    public static final int MODULE_IMAGE = 0x11;

    //==================模块间的服务定义============//
    /**
     * 服务定义规则：
     * 1、服务的请求ID必须是负值(正值表示事件)
     * 2、服务的请求ID必须是奇数，偶数表示该服务的返回事件，
     * 即：   requestID－1 ＝ returnID
     * 例如  -0xa001表示服务请求  -0xa002表示-0xa001的服务返回
     */
    public static final int SERVICE_A_UID = -0x0a001;
    public static final int SERVICE_RECYCLERVIEW_UID = -0x0c001;
    public static final int SERVICE_ANIMATION_UID = -0x0d001;
    public static final int SERVICE_WIDGET_UID = -0x0e001;
    public static final int SERVICE_VIEW_UID = -0x0f001;
    public static final int SERVICE_UIAUTOMATOR_UID = -0x1a001;
    public static final int SERVICE_IMAGE_UID = -0x1b001;

}
