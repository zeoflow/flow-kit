// Copyright 2021 ZeoFlow SRL
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

package com.zeoflow.utils;

import android.graphics.Bitmap;
import android.os.Build;

import androidx.annotation.NonNull;

/**
 * Helper for accessing features in {@link Bitmap}.
 */
public final class BitmapCompat {
    public static boolean hasMipMap(@NonNull Bitmap bitmap) {
        return bitmap.hasMipMap();
    }

    public static void setHasMipMap(@NonNull Bitmap bitmap, boolean hasMipMap) {
        bitmap.setHasMipMap(hasMipMap);
    }

    /**
     * Returns the size of the allocated memory used to store this bitmap's pixels in a backwards
     * compatible way.
     *
     * @param bitmap the bitmap in which to return its allocation size
     * @return the allocation size in bytes
     */
    public static int getAllocationByteCount(@NonNull Bitmap bitmap) {
        return bitmap.getAllocationByteCount();
    }

    private BitmapCompat() {}
}
