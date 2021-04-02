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

import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.zeoflow.initializer.ZeoFlowApp;

import java.lang.ref.WeakReference;

final public class ViewSizeUtils
{

    private static long lastTime = 0L;
    public static void setViewSize(View view, int width, int height)
    {
        WeakReference<View> viewWeakReference = new WeakReference<>(view);
        if (viewWeakReference.get() != null)
        {
            ViewGroup.LayoutParams params = view.getLayoutParams();
            if (params == null)
            {
                params = new ViewGroup.LayoutParams(width, height);
            } else
            {
                if (width != -1)
                {
                    params.width = width;
                }
                if (height != -1)
                {
                    params.height = height;
                }
            }
            viewWeakReference.get().setLayoutParams(params);
        }
    }
    public static void setViewSize(View view, int width, float widthHeightRatio)
    {
        WeakReference<View> viewWeakReference = new WeakReference<>(view);
        if (viewWeakReference.get() != null)
        {
            ViewGroup.LayoutParams params = view.getLayoutParams();
            if (params == null)
            {
                params = new ViewGroup.LayoutParams(width, (int) (width / widthHeightRatio));
            } else
            {
                if (width != -1)
                {
                    params.width = width;
                }
                if (widthHeightRatio != 0)
                {
                    params.height = (int) (width / widthHeightRatio);
                }
            }
            viewWeakReference.get().setLayoutParams(params);
        }
    }
    public static void setViewSize(View view, int width, int height, int marginLeft, int marginTop, int marginRight, int marginBottom)
    {
        WeakReference<View> viewWeakReference = new WeakReference<>(view);
        if (viewWeakReference.get() != null)
        {
            if (viewWeakReference.get().getLayoutParams() != null &&
                    (viewWeakReference.get().getLayoutParams() instanceof ViewGroup.MarginLayoutParams))
            {
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                params.width = width;
                params.height = height;
                if (marginLeft != -1)
                {
                    params.leftMargin = marginLeft;
                }
                if (marginRight != -1)
                {
                    params.rightMargin = marginRight;
                }
                if (marginTop != -1)
                {
                    params.topMargin = marginTop;
                }
                if (marginBottom != -1)
                {
                    params.bottomMargin = marginBottom;
                }
                viewWeakReference.get().setLayoutParams(params);
            }
        }
    }
    public static void setViewMargin(View view, int margin)
    {
        WeakReference<View> viewWeakReference = new WeakReference<>(view);
        if (viewWeakReference.get() != null)
        {
            if (viewWeakReference.get().getLayoutParams() != null &&
                    (viewWeakReference.get().getLayoutParams() instanceof ViewGroup.MarginLayoutParams))
            {
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                if (margin != -1)
                {
                    params.leftMargin = margin;
                    params.rightMargin = margin;
                    params.topMargin = margin;
                    params.bottomMargin = margin;
                }
                viewWeakReference.get().setLayoutParams(params);
            }
        }
    }
    public static int getViewHeight(View v)
    {
        ViewGroup.LayoutParams params = v.getLayoutParams();
        if (params != null)
        {
            return params.height;
        }
        return v.getHeight();
    }
    public static int getViewWidth(View v)
    {
        ViewGroup.LayoutParams params = v.getLayoutParams();
        if (params != null)
        {
            return params.width;
        }
        return v.getWidth();
    }
    public static void setMarginStart(View view, int marginStart)
    {
        WeakReference<View> viewWeakReference = new WeakReference<>(view);
        if (viewWeakReference.get() != null)
        {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            if (params != null)
            {
                params.leftMargin = marginStart;
                viewWeakReference.get().setLayoutParams(params);
            }
        }
    }
    public static void setMarginStartAndEnd(View view, int marginStart, int marginEnd)
    {
        WeakReference<View> viewWeakReference = new WeakReference<>(view);
        if (viewWeakReference.get() != null)
        {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            if (params != null)
            {
                params.leftMargin = marginStart;
                params.rightMargin = marginEnd;
                viewWeakReference.get().setLayoutParams(params);
            }
        }
    }
    public static void setMarginTopAndBottom(View view, int marginTop, int marginBottom)
    {
        WeakReference<View> viewWeakReference = new WeakReference<>(view);
        if (viewWeakReference.get() != null)
        {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            if (params != null)
            {
                params.topMargin = marginTop;
                params.bottomMargin = marginBottom;
                viewWeakReference.get().setLayoutParams(params);
            }
        }
    }
    public static void setMarginTop(View view, int marginTop)
    {
        WeakReference<View> viewWeakReference = new WeakReference<>(view);
        if (viewWeakReference.get() != null)
        {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            if (params != null)
            {
                params.topMargin = marginTop;
                viewWeakReference.get().setLayoutParams(params);
            }
        }
    }
    public static int getMarginTop(View view)
    {
        WeakReference<View> viewWeakReference = new WeakReference<>(view);
        if (viewWeakReference.get() != null)
        {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            if (params != null)
            {
                return params.topMargin;
            }
        }
        return 0;
    }
    public static int dp(float dp)
    {
        float density = ZeoFlowApp.getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5);
    }
    public static int sp(int spValue)
    {
        final float fontScale = ZeoFlowApp.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
    public static int getScreenWidth()
    {
        WindowManager wm = (WindowManager) ZeoFlowApp.getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        assert wm != null;
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }
    public static int getScreenHeight()
    {
        WindowManager wm = (WindowManager) ZeoFlowApp.getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        assert wm != null;
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }
    public static int blendColors(int color1, int color2, float ratio)
    {
        final float inverseRation = 1f - ratio;
        float r = (Color.red(color1) * ratio)
                + (Color.red(color2) * inverseRation);
        float g = (Color.green(color1) * ratio)
                + (Color.green(color2) * inverseRation);
        float b = (Color.blue(color1) * ratio)
                + (Color.blue(color2) * inverseRation);
        return Color.rgb((int) r, (int) g, (int) b);
    }
    public static boolean onDoubleClick()
    {
        boolean flag = false;
        long time = System.currentTimeMillis() - lastTime;

        if (time > 300)
        {
            flag = true;
        }
        lastTime = System.currentTimeMillis();
        return !flag;
    }

}
