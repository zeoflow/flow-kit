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

package com.zeoflow.initializer;

import android.content.Context;
import android.text.TextUtils;

import com.zeoflow.annotation.KeepForApi;
import com.zeoflow.annotation.NonNull;
import com.zeoflow.annotation.Nullable;
import com.zeoflow.core.manage.StringResourceValueReader;
import com.zeoflow.core.utils.Objects;

import static com.zeoflow.core.manage.StringsVerify.isEmptyOrWhitespace;
import static com.zeoflow.core.utils.Preconditions.checkNotEmpty;
import static com.zeoflow.core.utils.Preconditions.checkState;

/**
 * Configurable ZeoFlow options.
 */
public final class ZeoFlowOptions
{

    private static final String API_KEY_RESOURCE_NAME = "google_api_key";
    private static final String APP_ID_RESOURCE_NAME = "google_app_id";
    private static final String DATABASE_URL_RESOURCE_NAME = "zeoflow_database_url";
    private static final String GA_TRACKING_ID_RESOURCE_NAME = "ga_trackingId";
    private static final String GCM_SENDER_ID_RESOURCE_NAME = "gcm_defaultSenderId";
    private static final String STORAGE_BUCKET_RESOURCE_NAME = "google_storage_bucket";
    private static final String PROJECT_ID_RESOURCE_NAME = "project_id";

    private final String apiKey;
    private final String applicationId;
    private final String databaseUrl;
    private final String gaTrackingId;
    private final String gcmSenderId;
    private final String storageBucket;
    private final String projectId;

    @KeepForApi
    private ZeoFlowOptions(
        @NonNull String applicationId,
        @NonNull String apiKey,
        @Nullable String databaseUrl,
        @Nullable String gaTrackingId,
        @Nullable String gcmSenderId,
        @Nullable String storageBucket,
        @Nullable String projectId)
    {
        checkState(!isEmptyOrWhitespace(applicationId), "ApplicationId must be set.");
        this.applicationId = applicationId;
        this.apiKey = apiKey;
        this.databaseUrl = databaseUrl;
        this.gaTrackingId = gaTrackingId;
        this.gcmSenderId = gcmSenderId;
        this.storageBucket = storageBucket;
        this.projectId = projectId;
    }

    @Nullable
    @KeepForApi
    public static ZeoFlowOptions fromResource(@NonNull Context context)
    {
        StringResourceValueReader reader = new StringResourceValueReader(context);
        String applicationId = reader.getString(APP_ID_RESOURCE_NAME);
        if (TextUtils.isEmpty(applicationId))
        {
            return null;
        }
        return new ZeoFlowOptions(
            applicationId,
            reader.getString(API_KEY_RESOURCE_NAME),
            reader.getString(DATABASE_URL_RESOURCE_NAME),
            reader.getString(GA_TRACKING_ID_RESOURCE_NAME),
            reader.getString(GCM_SENDER_ID_RESOURCE_NAME),
            reader.getString(STORAGE_BUCKET_RESOURCE_NAME),
            reader.getString(PROJECT_ID_RESOURCE_NAME));
    }

    @NonNull
    public String getApiKey()
    {
        return apiKey;
    }

    @NonNull
    public String getApplicationId()
    {
        return applicationId;
    }

    @Nullable
    public String getDatabaseUrl()
    {
        return databaseUrl;
    }

    @Nullable
    @KeepForApi
    public String getGaTrackingId()
    {
        return gaTrackingId;
    }

    @Nullable
    public String getGcmSenderId()
    {
        return gcmSenderId;
    }

    @Nullable
    public String getStorageBucket()
    {
        return storageBucket;
    }

    @Nullable
    public String getProjectId()
    {
        return projectId;
    }

    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof ZeoFlowOptions))
        {
            return false;
        } else
        {
            ZeoFlowOptions other = (ZeoFlowOptions) o;
            return Objects.equal(this.applicationId, other.applicationId) && Objects.equal(this.apiKey, other.apiKey) && Objects.equal(this.databaseUrl, other.databaseUrl) && Objects.equal(this.gaTrackingId, other.gaTrackingId) && Objects.equal(this.gcmSenderId, other.gcmSenderId) && Objects.equal(this.storageBucket, other.storageBucket) && Objects.equal(this.projectId, other.projectId);
        }
    }

    /**
     * Builder for constructing ZeoFlowOptions.
     */
    public static final class Builder
    {
        private String apiKey;
        private String applicationId;
        private String databaseUrl;
        private String gaTrackingId;
        private String gcmSenderId;
        private String storageBucket;
        private String projectId;

        /**
         * Constructs an empty builder.
         */
        public Builder()
        {
        }

        /**
         * Initializes the builder's values from the options object.
         *
         * <p>The new builder is not backed by this objects values, that is changes made to the new
         * builder don't change the values of the origin object.
         */
        public Builder(@NonNull ZeoFlowOptions options)
        {
            applicationId = options.applicationId;
            apiKey = options.apiKey;
            databaseUrl = options.databaseUrl;
            gaTrackingId = options.gaTrackingId;
            gcmSenderId = options.gcmSenderId;
            storageBucket = options.storageBucket;
            projectId = options.projectId;
        }

        @NonNull
        public Builder setApiKey(@NonNull String apiKey)
        {
            this.apiKey = checkNotEmpty(apiKey, "ApiKey must be set.");
            return this;
        }

        @NonNull
        public Builder setApplicationId(@NonNull String applicationId)
        {
            this.applicationId = checkNotEmpty(applicationId, "ApplicationId must be set.");
            return this;
        }

        @NonNull
        public Builder setDatabaseUrl(@Nullable String databaseUrl)
        {
            this.databaseUrl = databaseUrl;
            return this;
        }

        /**
         * @hide
         */
        // TODO: unhide once an API (AppInvite) starts reading it.
        @NonNull
        @KeepForApi
        public Builder setGaTrackingId(@Nullable String gaTrackingId)
        {
            this.gaTrackingId = gaTrackingId;
            return this;
        }

        @NonNull
        public Builder setGcmSenderId(@Nullable String gcmSenderId)
        {
            this.gcmSenderId = gcmSenderId;
            return this;
        }

        @NonNull
        public Builder setStorageBucket(@Nullable String storageBucket)
        {
            this.storageBucket = storageBucket;
            return this;
        }

        @NonNull
        public Builder setProjectId(@Nullable String projectId)
        {
            this.projectId = projectId;
            return this;
        }

        @NonNull
        public ZeoFlowOptions build()
        {
            return new ZeoFlowOptions(
                applicationId, apiKey, databaseUrl, gaTrackingId, gcmSenderId, storageBucket, projectId);
        }
    }

}
