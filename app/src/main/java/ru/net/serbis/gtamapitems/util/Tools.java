package ru.net.serbis.gtamapitems.util;

import android.app.*;
import android.content.*;
import android.view.*;
import java.io.*;
import ru.net.serbis.gtamapitems.*;

public class Tools
{
    private static final Tools instance = new Tools();

    private Tools()
    {
    }

    public static Tools get()
    {
        return instance;
    }

    public <T> T findView(View view, int id)
    {
        return (T) view.findViewById(id);
    }

    public <T> T findView(Activity view, int id)
    {
        return (T) view.findViewById(id);
    }

    public int dpToPx(int dp, Context context)
    {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    public String errorToText(Throwable error)
    {
        StringWriter writer = new StringWriter();
        error.printStackTrace(new PrintWriter(writer));
        return writer.toString();
    }

    public SharedPreferences getPreferences(Context context)
    {
        return context.getSharedPreferences(Constants.APP, Context.MODE_PRIVATE);
    }

    public SharedPreferences.Editor getPreferencesEditor(Context context)
    {
        return getPreferences(context).edit();
    }
}
