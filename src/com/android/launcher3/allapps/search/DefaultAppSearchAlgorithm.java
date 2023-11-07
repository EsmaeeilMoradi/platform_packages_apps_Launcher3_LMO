/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.launcher3.allapps.search;

import static android.content.Context.BIND_AUTO_CREATE;
import static com.android.launcher3.allapps.BaseAllAppsAdapter.VIEW_TYPE_EMPTY_SEARCH;
import static com.android.launcher3.util.Executors.MAIN_EXECUTOR;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.AnyThread;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.launcher3.LauncherAppState;
import com.android.launcher3.R;
import com.android.launcher3.allapps.BaseAllAppsAdapter.AdapterItem;
import com.android.launcher3.allapps.search.init.AppSearchSettings;
import com.android.launcher3.allapps.search.init.Contact;
import com.android.launcher3.allapps.search.init.ContactsAdapter;
import com.android.launcher3.model.AllAppsList;
import com.android.launcher3.model.BaseModelUpdateTask;
import com.android.launcher3.model.BgDataModel;
import com.android.launcher3.model.data.AppInfo;
import com.android.launcher3.search.SearchAlgorithm;
import com.android.launcher3.search.SearchCallback;
import com.android.launcher3.search.StringMatcherUtility;
import com.android.settings.intelligence.search.ISearchService;
import com.android.settings.intelligence.search.ISearchServiceCallback;
import com.android.settings.intelligence.search.SearchServiceResult;

import java.util.ArrayList;
import java.util.List;

/**
 * The default search implementation.
 */
public class DefaultAppSearchAlgorithm implements SearchAlgorithm<AdapterItem> {

    private static final int MAX_RESULTS_COUNT = 5;

    private final LauncherAppState mAppState;
    private final Handler mResultHandler;
    private final boolean mAddNoResultsMessage;


    ISearchService searchService;
    Context context;
    private final ArrayList<AppSearchSettings> elementSearchList = new ArrayList<>();

    public DefaultAppSearchAlgorithm(Context context) {
        this(context, false);
    }

    public DefaultAppSearchAlgorithm(Context context, boolean addNoResultsMessage) {
        mAppState = LauncherAppState.getInstance(context);
        mResultHandler = new Handler(MAIN_EXECUTOR.getLooper());
        mAddNoResultsMessage = addNoResultsMessage;
        this.context = context;

    }

    @Override
    public void cancel(boolean interruptActiveRequests) {
        if (interruptActiveRequests) {
            mResultHandler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public void doSearch(String query, SearchCallback<AdapterItem> callback) {

        Intent intent = new Intent("SearchService");
        intent.setPackage("com.android.settings.intelligence");
        intent.setComponent(new ComponentName("com.android.settings.intelligence", "com.android.settings.intelligence.search.SearchService"));
        context.bindService(intent, connectToService(query), BIND_AUTO_CREATE);

        mAppState.getModel().enqueueModelUpdateTask(new BaseModelUpdateTask() {
            @Override
            public void execute(@NonNull final LauncherAppState app,
                                @NonNull final BgDataModel dataModel, @NonNull final AllAppsList apps) {
                ArrayList<AdapterItem> result = getTitleMatchResult(apps.data, query);
                if (mAddNoResultsMessage && result.isEmpty()) {
                    result.add(getEmptyMessageAdapterItem(query));
                }
                mResultHandler.post(() -> callback.onSearchResult(query, result));
            }
        });
    }

    private static AdapterItem getEmptyMessageAdapterItem(String query) {
        AdapterItem item = new AdapterItem(VIEW_TYPE_EMPTY_SEARCH);
        // Add a place holder info to propagate the query
        AppInfo placeHolder = new AppInfo();
        placeHolder.title = query;
        item.itemInfo = placeHolder;
        return item;
    }

    /**
     * Filters {@link AppInfo}s matching specified query
     */
    @AnyThread
    public static ArrayList<AdapterItem> getTitleMatchResult(List<AppInfo> apps, String query) {
        // Do an intersection of the words in the query and each title, and filter out all the
        // apps that don't match all of the words in the query.
        final String queryTextLower = query.toLowerCase();
        final ArrayList<AdapterItem> result = new ArrayList<>();
        StringMatcherUtility.StringMatcher matcher =
                StringMatcherUtility.StringMatcher.getInstance();

        int resultCount = 0;
        int total = apps.size();
        for (int i = 0; i < total && resultCount < MAX_RESULTS_COUNT; i++) {
            AppInfo info = apps.get(i);
            if (StringMatcherUtility.matches(queryTextLower, info.title.toString(), matcher)) {
                result.add(AdapterItem.asApp(info));
                resultCount++;
            }
        }
        return result;
    }


    private ServiceConnection connectToService(String query) {
        ServiceConnection serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Log.e("ServiceInt", "connected");
                searchService = ISearchService.Stub.asInterface(iBinder);
                try {
//                actionSwitch.setEnabled(searchService.isIndexingComplete());
                    searchService.startIndexing(new ISearchServiceCallback.Stub() {
                        @Override
                        public void onIndexingFinished() throws RemoteException {
                            Log.e("ServiceInt", "finished");
                            searchService.querySearch(this, query);
                        }

                        @Override
                        public void onSearchResult(String query, List<SearchServiceResult> result) throws RemoteException {
                            Log.e("ServiceInt", "finished resss");

                            for (SearchServiceResult r : result) {
                                boolean showSettingItem;
                                if (result.indexOf(r) == 0) {
                                    showSettingItem = true;
                                } else {
                                    showSettingItem = false;

                                }


                                elementSearchList.add(new AppSearchSettings(r.title, r.summary, r.intent, r.breadcrumbs, showSettingItem));
                                Log.e("ServiceInt", r.title.toString());
                                Log.e("ServiceInt", r.summary.toString() + "   $ ");
                                Log.e("ServiceInt", r.intent.getData() + "   $intent ");
                                Log.e("ServiceInt", r.breadcrumbs.size() + "   breadcrumbs ");

                            }
                        }
                    });

                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }

            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
            }
        };

        return serviceConnection;
    }
}
