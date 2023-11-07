package com.android.launcher3.allapps.search.init;

import android.content.Intent;
import android.graphics.drawable.Drawable;



import java.util.List;

public class AppSearchSettings  {

    private Drawable icon;
    private String dataKey;
    private CharSequence title;
    private CharSequence summary;

    private Intent intent;

    List<String> breadcrumbs;
    boolean showSettingIcon;


    public AppSearchSettings(CharSequence title, CharSequence summary) {
        this.title = title;
        this.summary = summary;


    }

    public AppSearchSettings(CharSequence title, CharSequence summary, Intent intent, List<String> breadcrumbs, boolean showSettingIcon) {
        this.title = title;
        this.summary = summary;
        this.intent = intent;
        this.breadcrumbs = breadcrumbs;
        this.showSettingIcon =showSettingIcon;


    }

    public boolean isShowSettingIcon() {
        return showSettingIcon;
    }

    public void setShowSettingIcon(boolean showSettingIcon) {
        this.showSettingIcon = showSettingIcon;
    }

    public List<String> getBreadcrumbs() {
        return breadcrumbs;
    }

    public void setBreadcrumbs(List<String> breadcrumbs) {
        this.breadcrumbs = breadcrumbs;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

    public CharSequence getTitle() {
        return title;
    }

    public void setTitle(CharSequence title) {
        this.title = title;
    }

    public CharSequence getSummary() {
        return summary;
    }

    public void setSummary(CharSequence summary) {
        this.summary = summary;
    }


}
