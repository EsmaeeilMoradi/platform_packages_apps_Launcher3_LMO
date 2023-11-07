package com.android.settings.intelligence.search;

import com.android.settings.intelligence.search.SearchServiceResult;

interface ISearchServiceCallback {
    void onIndexingFinished();
    void onSearchResult(String query, in List<SearchServiceResult> result);
}
