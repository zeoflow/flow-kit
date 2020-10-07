// Copyright 2018 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.zeoflow.flow.kit.initializer;

import android.app.Application;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;

import com.zeoflow.flow.kit.annotation.NonNull;
import com.zeoflow.flow.kit.annotation.Nullable;
import com.zeoflow.flow.kit.annotation.VisibleForTesting;
import com.zeoflow.flow.kit.core.utils.Preconditions;
import com.zeoflow.flow.kit.crash.reporter.CrashReporter;

public class ZeoFlowInitProvider extends ContentProvider
{

    /**
     * Should match the {@link ZeoFlowInitProvider} authority if $androidId is empty.
     */
    @VisibleForTesting
    static final String EMPTY_APPLICATION_ID_PROVIDER_AUTHORITY =
        "com.zeoflow.flow.kit.initializer";
    private static final String TAG = "ZeoFlowInitProvider";

    /**
     * Check that the content provider's authority does not use firebase-common's package name. If it
     * does, crash in order to alert the developer of the problem before they distribute the app.
     */
    private static void checkContentProviderAuthority(@NonNull ProviderInfo info)
    {
        Preconditions.checkNotNull(info, "ZeoFlowInitProvider ProviderInfo cannot be null.");
        if (EMPTY_APPLICATION_ID_PROVIDER_AUTHORITY.equals(info.authority))
        {
            throw new IllegalStateException(
                "Incorrect provider authority in manifest. Most likely due to a missing "
                    + "applicationId variable in application's build.gradle.");
        }
    }

    @Override
    public void attachInfo(@NonNull Context context, @NonNull ProviderInfo info)
    {
        // super.attachInfo calls onCreate. Fail as early as possible.
        checkContentProviderAuthority(info);
        super.attachInfo(context, info);
    }

    /**
     * Called before {@link Application#onCreate()}.
     */
    @Override
    public boolean onCreate()
    {
        assert getContext() != null;
        if (ZeoFlowApp.initializeApp(getContext()) == null)
        {
            throw new IllegalStateException("ZeoFlowApp initialization unsuccessful");
        } else
        {
            CrashReporter.initialize();
        }
        return false;
    }

    @Nullable
    @Override
    public Cursor query(
        @NonNull Uri uri,
        @Nullable String[] projection,
        @Nullable String selection,
        @Nullable String[] selectionArgs,
        @Nullable String sortOrder)
    {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri)
    {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values)
    {
        return null;
    }

    @Override
    public int delete(
        @NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs)
    {
        return 0;
    }

    @Override
    public int update(
        @NonNull Uri uri,
        @Nullable ContentValues values,
        @Nullable String selection,
        @Nullable String[] selectionArgs)
    {
        return 0;
    }
}
