package com.android.settings.intelligence.search;

import android.graphics.Bitmap;

parcelable SearchServiceResult {
    Bitmap icon;
    String dataKey;
    CharSequence title;
    CharSequence summary;
    Intent intent;
    List<String> breadcrumbs;

}
