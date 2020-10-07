package com.zeoflow.compat;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import com.zeoflow.flow.kit.R;
import com.zeoflow.annotation.ColorInt;
import com.zeoflow.annotation.IntRange;
import com.zeoflow.annotation.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class StatusBarUtil
{

    public static final int DEFAULT_STATUS_BAR_ALPHA = 112;
    private static final int FAKE_STATUS_BAR_VIEW_ID = R.id.statusbarutil_fake_status_bar_view;
    private static final int FAKE_TRANSLUCENT_VIEW_ID = R.id.statusbarutil_translucent_view;
    private static final int TAG_KEY_HAVE_SET_OFFSET = -123;


    public static void setColor(Activity activity, @ColorInt int color)
    {
        setColor(activity, color, DEFAULT_STATUS_BAR_ALPHA);
    }


    public static void setColor(Activity activity, @ColorInt int color, @IntRange(from = 0, to = 255) int statusBarAlpha)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().setStatusBarColor(calculateStatusColor(color, statusBarAlpha));
        } else
        {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            View fakeStatusBarView = decorView.findViewById(FAKE_STATUS_BAR_VIEW_ID);
            if (fakeStatusBarView != null)
            {
                if (fakeStatusBarView.getVisibility() == View.GONE)
                {
                    fakeStatusBarView.setVisibility(View.VISIBLE);
                }
                fakeStatusBarView.setBackgroundColor(calculateStatusColor(color, statusBarAlpha));
            } else
            {
                decorView.addView(createStatusBarView(activity, color, statusBarAlpha));
            }
            setRootView(activity);
        }
    }


    public static void setColorForSwipeBack(Activity activity, int color)
    {
        setColorForSwipeBack(activity, color, DEFAULT_STATUS_BAR_ALPHA);
    }


    public static void setColorForSwipeBack(Activity activity, @ColorInt int color,
                                            @IntRange(from = 0, to = 255) int statusBarAlpha)
    {

        ViewGroup contentView = ((ViewGroup) activity.findViewById(android.R.id.content));
        View rootView = contentView.getChildAt(0);
        int statusBarHeight = getStatusBarHeight(activity);
        if (rootView instanceof CoordinatorLayout)
        {
            final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) rootView;
            coordinatorLayout.setStatusBarBackgroundColor(calculateStatusColor(color, statusBarAlpha));
        } else
        {
            contentView.setPadding(0, statusBarHeight, 0, 0);
            contentView.setBackgroundColor(calculateStatusColor(color, statusBarAlpha));
        }
        setTransparentForWindow(activity);
    }


    public static void setColorNoTranslucent(Activity activity, @ColorInt int color)
    {
        setColor(activity, color, 0);
    }


    @Deprecated
    public static void setColorDiff(Activity activity, @ColorInt int color)
    {
        transparentStatusBar(activity);
        ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);

        View fakeStatusBarView = contentView.findViewById(FAKE_STATUS_BAR_VIEW_ID);
        if (fakeStatusBarView != null)
        {
            if (fakeStatusBarView.getVisibility() == View.GONE)
            {
                fakeStatusBarView.setVisibility(View.VISIBLE);
            }
            fakeStatusBarView.setBackgroundColor(color);
        } else
        {
            contentView.addView(createStatusBarView(activity, color));
        }
        setRootView(activity);
    }


    public static void setTranslucent(Activity activity)
    {
        setTranslucent(activity, DEFAULT_STATUS_BAR_ALPHA);
    }

    public static void setTranslucent(Activity activity, @IntRange(from = 0, to = 255) int statusBarAlpha)
    {
        setTransparent(activity);
        addTranslucentView(activity, statusBarAlpha);
    }

    public static void setTranslucentForCoordinatorLayout(Activity activity, @IntRange(from = 0, to = 255) int statusBarAlpha)
    {
        transparentStatusBar(activity);
        addTranslucentView(activity, statusBarAlpha);
    }

    public static void setTransparent(Activity activity)
    {
        transparentStatusBar(activity);
        setRootView(activity);
    }

    @Deprecated
    public static void setTranslucentDiff(Activity activity)
    {

        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setRootView(activity);
    }

    public static void setColorForDrawerLayout(Activity activity, DrawerLayout drawerLayout, @ColorInt int color)
    {
        setColorForDrawerLayout(activity, drawerLayout, color, DEFAULT_STATUS_BAR_ALPHA);
    }

    public static void setColorNoTranslucentForDrawerLayout(Activity activity, DrawerLayout drawerLayout, @ColorInt int color)
    {
        setColorForDrawerLayout(activity, drawerLayout, color, 0);
    }

    public static void setColorForDrawerLayout(Activity activity, DrawerLayout drawerLayout, @ColorInt int color,
                                               @IntRange(from = 0, to = 255) int statusBarAlpha)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else
        {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }


        ViewGroup contentLayout = (ViewGroup) drawerLayout.getChildAt(0);
        View fakeStatusBarView = contentLayout.findViewById(FAKE_STATUS_BAR_VIEW_ID);
        if (fakeStatusBarView != null)
        {
            if (fakeStatusBarView.getVisibility() == View.GONE)
            {
                fakeStatusBarView.setVisibility(View.VISIBLE);
            }
            fakeStatusBarView.setBackgroundColor(color);
        } else
        {
            contentLayout.addView(createStatusBarView(activity, color), 0);
        }

        if (!(contentLayout instanceof LinearLayout) && contentLayout.getChildAt(1) != null)
        {
            contentLayout.getChildAt(1)
                .setPadding(contentLayout.getPaddingLeft(), getStatusBarHeight(activity) + contentLayout.getPaddingTop(),
                    contentLayout.getPaddingRight(), contentLayout.getPaddingBottom());
        }

        setDrawerLayoutProperty(drawerLayout, contentLayout);
        addTranslucentView(activity, statusBarAlpha);
    }


    private static void setDrawerLayoutProperty(DrawerLayout drawerLayout, ViewGroup drawerLayoutContentLayout)
    {
        ViewGroup drawer = (ViewGroup) drawerLayout.getChildAt(1);
        drawerLayout.setFitsSystemWindows(false);
        drawerLayoutContentLayout.setFitsSystemWindows(false);
        drawerLayoutContentLayout.setClipToPadding(true);
        drawer.setFitsSystemWindows(false);
    }


    @Deprecated
    public static void setColorForDrawerLayoutDiff(Activity activity, DrawerLayout drawerLayout, @ColorInt int color)
    {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        ViewGroup contentLayout = (ViewGroup) drawerLayout.getChildAt(0);
        View fakeStatusBarView = contentLayout.findViewById(FAKE_STATUS_BAR_VIEW_ID);
        if (fakeStatusBarView != null)
        {
            if (fakeStatusBarView.getVisibility() == View.GONE)
            {
                fakeStatusBarView.setVisibility(View.VISIBLE);
            }
            fakeStatusBarView.setBackgroundColor(calculateStatusColor(color, DEFAULT_STATUS_BAR_ALPHA));
        } else
        {

            contentLayout.addView(createStatusBarView(activity, color), 0);
        }

        if (!(contentLayout instanceof LinearLayout) && contentLayout.getChildAt(1) != null)
        {
            contentLayout.getChildAt(1).setPadding(0, getStatusBarHeight(activity), 0, 0);
        }

        setDrawerLayoutProperty(drawerLayout, contentLayout);
    }


    public static void setTranslucentForDrawerLayout(Activity activity, DrawerLayout drawerLayout)
    {
        setTranslucentForDrawerLayout(activity, drawerLayout, DEFAULT_STATUS_BAR_ALPHA);
    }


    public static void setTranslucentForDrawerLayout(Activity activity, DrawerLayout drawerLayout,
                                                     @IntRange(from = 0, to = 255) int statusBarAlpha)
    {
        setTransparentForDrawerLayout(activity, drawerLayout);
        addTranslucentView(activity, statusBarAlpha);
    }


    public static void setTransparentForDrawerLayout(Activity activity, DrawerLayout drawerLayout)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else
        {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        ViewGroup contentLayout = (ViewGroup) drawerLayout.getChildAt(0);

        if (!(contentLayout instanceof LinearLayout) && contentLayout.getChildAt(1) != null)
        {
            contentLayout.getChildAt(1).setPadding(0, getStatusBarHeight(activity), 0, 0);
        }


        setDrawerLayoutProperty(drawerLayout, contentLayout);
    }


    @Deprecated
    public static void setTranslucentForDrawerLayoutDiff(Activity activity, DrawerLayout drawerLayout)
    {

        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        ViewGroup contentLayout = (ViewGroup) drawerLayout.getChildAt(0);
        contentLayout.setFitsSystemWindows(true);
        contentLayout.setClipToPadding(true);

        ViewGroup vg = (ViewGroup) drawerLayout.getChildAt(1);
        vg.setFitsSystemWindows(false);

        drawerLayout.setFitsSystemWindows(false);
    }


    public static void setTransparentForImageView(Activity activity, View needOffsetView)
    {
        setTranslucentForImageView(activity, 0, needOffsetView);
    }


    public static void setTranslucentForImageView(Activity activity, View needOffsetView)
    {
        setTranslucentForImageView(activity, DEFAULT_STATUS_BAR_ALPHA, needOffsetView);
    }


    public static void setTranslucentForImageView(Activity activity, @IntRange(from = 0, to = 255) int statusBarAlpha,
                                                  View needOffsetView)
    {
        setTransparentForWindow(activity);
        addTranslucentView(activity, statusBarAlpha);
        if (needOffsetView != null)
        {
            Object haveSetOffset = needOffsetView.getTag(TAG_KEY_HAVE_SET_OFFSET);
            if (haveSetOffset != null && (Boolean) haveSetOffset)
            {
                return;
            }
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) needOffsetView.getLayoutParams();
            layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin + getStatusBarHeight(activity),
                layoutParams.rightMargin, layoutParams.bottomMargin);
            needOffsetView.setTag(TAG_KEY_HAVE_SET_OFFSET, true);
        }
    }


    public static void setTranslucentForImageViewInFragment(Activity activity, View needOffsetView)
    {
        setTranslucentForImageViewInFragment(activity, DEFAULT_STATUS_BAR_ALPHA, needOffsetView);
    }


    public static void setTransparentForImageViewInFragment(Activity activity, View needOffsetView)
    {
        setTranslucentForImageViewInFragment(activity, 0, needOffsetView);
    }


    public static void setTranslucentForImageViewInFragment(Activity activity, @IntRange(from = 0, to = 255) int statusBarAlpha,
                                                            View needOffsetView)
    {
        setTranslucentForImageView(activity, statusBarAlpha, needOffsetView);
    }


    public static void hideFakeStatusBarView(Activity activity)
    {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        View fakeStatusBarView = decorView.findViewById(FAKE_STATUS_BAR_VIEW_ID);
        if (fakeStatusBarView != null)
        {
            fakeStatusBarView.setVisibility(View.GONE);
        }
        View fakeTranslucentView = decorView.findViewById(FAKE_TRANSLUCENT_VIEW_ID);
        if (fakeTranslucentView != null)
        {
            fakeTranslucentView.setVisibility(View.GONE);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static void setLightMode(Activity activity)
    {
        setMIUIStatusBarDarkIcon(activity, true);
        setMeizuStatusBarDarkIcon(activity, true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static void setDarkMode(Activity activity)
    {
        setMIUIStatusBarDarkIcon(activity, false);
        setMeizuStatusBarDarkIcon(activity, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }


    private static void setMIUIStatusBarDarkIcon(@NonNull Activity activity, boolean darkIcon)
    {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try
        {
            @SuppressLint("PrivateApi") Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            int darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkIcon ? darkModeFlag : 0, darkModeFlag);
        } catch (Exception ignored)
        {

        }
    }


    private static void setMeizuStatusBarDarkIcon(@NonNull Activity activity, boolean darkIcon)
    {
        try
        {
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            if (darkIcon)
            {
                value |= bit;
            } else
            {
                value &= ~bit;
            }
            meizuFlags.setInt(lp, value);
            activity.getWindow().setAttributes(lp);
        } catch (Exception e)
        {

        }
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static void clearPreviousSetting(Activity activity)
    {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        View fakeStatusBarView = decorView.findViewById(FAKE_STATUS_BAR_VIEW_ID);
        if (fakeStatusBarView != null)
        {
            decorView.removeView(fakeStatusBarView);
            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
            rootView.setPadding(0, 0, 0, 0);
        }
    }


    private static void addTranslucentView(Activity activity, @IntRange(from = 0, to = 255) int statusBarAlpha)
    {
        ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
        View fakeTranslucentView = contentView.findViewById(FAKE_TRANSLUCENT_VIEW_ID);
        if (fakeTranslucentView != null)
        {
            if (fakeTranslucentView.getVisibility() == View.GONE)
            {
                fakeTranslucentView.setVisibility(View.VISIBLE);
            }
            fakeTranslucentView.setBackgroundColor(Color.argb(statusBarAlpha, 0, 0, 0));
        } else
        {
            contentView.addView(createTranslucentStatusBarView(activity, statusBarAlpha));
        }
    }


    private static View createStatusBarView(Activity activity, @ColorInt int color)
    {
        return createStatusBarView(activity, color, 0);
    }


    private static View createStatusBarView(Activity activity, @ColorInt int color, int alpha)
    {

        View statusBarView = new View(activity);
        LinearLayout.LayoutParams params =
            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(calculateStatusColor(color, alpha));
        statusBarView.setId(FAKE_STATUS_BAR_VIEW_ID);
        return statusBarView;
    }


    private static void setRootView(Activity activity)
    {
        ViewGroup parent = (ViewGroup) activity.findViewById(android.R.id.content);
        for (int i = 0, count = parent.getChildCount(); i < count; i++)
        {
            View childView = parent.getChildAt(i);
            if (childView instanceof ViewGroup)
            {
                childView.setFitsSystemWindows(true);
                ((ViewGroup) childView).setClipToPadding(true);
            }
        }
    }


    private static void setTransparentForWindow(Activity activity)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            activity.getWindow()
                .getDecorView()
                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else
        {
            activity.getWindow()
                .setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static void transparentStatusBar(Activity activity)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else
        {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


    private static View createTranslucentStatusBarView(Activity activity, int alpha)
    {

        View statusBarView = new View(activity);
        LinearLayout.LayoutParams params =
            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(Color.argb(alpha, 0, 0, 0));
        statusBarView.setId(FAKE_TRANSLUCENT_VIEW_ID);
        return statusBarView;
    }


    private static int getStatusBarHeight(Context context)
    {

        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }


    private static int calculateStatusColor(@ColorInt int color, int alpha)
    {
        if (alpha == 0)
        {
            return color;
        }
        float a = 1 - alpha / 255f;
        int red = color >> 16 & 0xff;
        int green = color >> 8 & 0xff;
        int blue = color & 0xff;
        red = (int) (red * a + 0.5);
        green = (int) (green * a + 0.5);
        blue = (int) (blue * a + 0.5);
        return 0xff << 24 | red << 16 | green << 8 | blue;
    }
}