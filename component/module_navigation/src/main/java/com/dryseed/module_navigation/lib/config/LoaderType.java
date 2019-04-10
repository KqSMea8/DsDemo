package com.dryseed.module_navigation.lib.config;

/**
 * @author caiminming
 */
public enum LoaderType {
    /**
     * Url
     */
    LOADER_TYPE_URL(0, "Url"),
    /**
     * Json字符串
     */
    LOADER_TYPE_JSON(1, "Json"),
    /**
     * List<{@link com.dryseed.module_navigation.lib.entity.TopMenuTabEntity}>
     */
    LOADER_TYPE_LIST(2, "List"),
    /**
     * 本地文件
     */
    LOADER_TYPE_LOCAL_FILE(3, "LocalFile");

    private int type;
    private String name;

    LoaderType(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name == null ? "" : name;
    }
}
