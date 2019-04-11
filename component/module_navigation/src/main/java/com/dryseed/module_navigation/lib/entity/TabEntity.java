package com.dryseed.module_navigation.lib.entity;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author caiminming
 */
public class TabEntity implements Serializable {
    /**
     * Tab Id
     */
    private String id;
    /**
     * Tab 标题
     */
    private String title;
    /**
     * Tab 展示样式
     */
    private TabStyleEntity tabStyleEntity;

    private HashMap<String, Object> data;

    public TabEntity() {
        tabStyleEntity = new TabStyleEntity();
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TabStyleEntity getTabStyleEntity() {
        return tabStyleEntity;
    }

    public void setTabStyleEntity(TabStyleEntity tabStyleEntity) {
        this.tabStyleEntity = tabStyleEntity;
    }

    public Object getData(String key) {
        return data != null ? data.get(key) : null;
    }

    public Object putData(String key, Object object) {
        if (data == null) {
            data = new HashMap<>();
        }
        return data.put(key, object);
    }

    public String getStringData(String key) {
        Object data = getData(key);
        return data == null ? null : String.valueOf(data);
    }

    public String putStringData(String key, String value) {
        return (String) putData(key, value);
    }
}
