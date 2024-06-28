package ru.net.serbis.gtamapitems.util;

import android.os.*;
import android.widget.*;

public class Toasts extends Util
{
    private static final Toasts instance = new Toasts();
    private Handler hadler = new Handler(Looper.getMainLooper());

    public static Toasts get()
    {
        return instance;
    }

    public void runOnUiThread(Runnable run)
    {
        hadler.post(run);
    }

    public void toast(final String text)
    {
        runOnUiThread(
            new Runnable()
            {
                public void run()
                {
                    Toast.makeText(context, text, Toast.LENGTH_LONG).show();
                }
            }
        );
    }

    public void toast(int textId)
    {
        toast(Strings.get().get(textId));
    }
}
