package com.example.switchingmargins;

import android.app.Activity;
import android.content.Intent;

public class Selection {

    private static int sTheme;
    public final static int THEME_BLACK = 0;
    public final static int THEME_GREEN = 1;
    public final static int THEME_BLUE = 2;

    public static void setsTheme(int sTheme) {
        Selection.sTheme = sTheme;
    }

    public static void changeToTheme(Activity activity, int theme) {
        sTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }

    public static void onActivityCreateSetTheme(Activity activity)
    {
        switch (sTheme)
        {
            case THEME_BLACK:
                activity.setTheme(R.style.ThemeBlack);
                break;
            case THEME_GREEN:
                activity.setTheme(R.style.ThemeGreen);
                break;
            case THEME_BLUE:
                activity.setTheme(R.style.ThemeBlue);
                break;
        }
    }
}