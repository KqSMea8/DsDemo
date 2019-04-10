package com.dryseed.module_navigation.lib.entity;

/**
 * @author caiminming
 */
public class TopMenuTabEntity {
    private String id;
    private String title;

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
}
