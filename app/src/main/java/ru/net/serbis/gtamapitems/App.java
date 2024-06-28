package ru.net.serbis.gtamapitems;

import android.app.*;
import android.content.*;
import ru.net.serbis.gtamapitems.handler.*;
import ru.net.serbis.gtamapitems.util.*;

public class App extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        Context context = getApplicationContext();
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(context));
        Preferences.get().set(context);
        Strings.get().set(context);
        Toasts.get().set(context);
    }
}
