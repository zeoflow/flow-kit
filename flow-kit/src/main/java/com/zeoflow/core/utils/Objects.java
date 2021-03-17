/*
 * Copyright (c) 2009, 2020, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package com.zeoflow.core.utils;

import android.os.Build;

import com.zeoflow.annotation.ForceInline;
import com.zeoflow.annotation.KeepForApi;
import com.zeoflow.annotation.Nullable;
import com.zeoflow.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings({"EqualsReplaceableByObjectsCall", "unused"})
public final class Objects
{

    @KeepForApi
    public static boolean equal(@Nullable Object var0, @Nullable Object var1)
    {
        return java.util.Objects.equals(var0, var1);
    }

    @KeepForApi
    public static int hashCode(Object... var0)
    {
        return Arrays.hashCode(var0);
    }

    @KeepForApi
    public static final class ToStringHelper
    {
        private final ArrayList zzer;
        private final Object zzes;

        private ToStringHelper(Object var1)
        {
            this.zzes = Preconditions.checkNotNull(var1);
            this.zzer = new ArrayList();
        }

        @KeepForApi
        public final ToStringHelper add(String var1, @Nullable Object var2)
        {
            String var3 = Preconditions.checkNotNull(var1);
            String var4 = String.valueOf(var2);
            ((List) this.zzer).add(var3 + "=" + var4);
            return this;
        }

        @KeepForApi
        public final String toString()
        {
            StringBuilder var1 = (new StringBuilder(100)).append(this.zzes.getClass().getSimpleName()).append('{');
            int var2 = this.zzer.size();

            for (int var3 = 0; var3 < var2; ++var3)
            {
                var1.append((String) this.zzer.get(var3));
                if (var3 < var2 - 1)
                {
                    var1.append(", ");
                }
            }

            return var1.append('}').toString();
        }
    }

}
