package com.android.settings.intelligence.search;

import com.android.settings.intelligence.search.ISearchServiceCallback;

interface ISearchService {
    void startIndexing(ISearchServiceCallback callback);
    boolean isIndexingComplete();
    boolean querySearch(ISearchServiceCallback callback, String query);
}
