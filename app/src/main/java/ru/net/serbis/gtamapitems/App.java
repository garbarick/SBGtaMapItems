package ru.net.serbis.gtamapitems;

import android.app.*;
import android.content.*;
import ru.net.serbis.gtamapitems.activity.*;
import ru.net.serbis.utils.*;
import ru.net.serbis.gtamapitems.util.*;

public class App extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        Context context = getApplicationContext();

        Preferences.get().set(context);
        Preferences.get().setApp(Constants.APP);
        Strings.get().set(context);
        UITool.get().set(context);
        Maps.get().set(context);
        ExceptionHandler.get().set(context);
        ExceptionHandler.get().setErrorActivity(ExceptionReport.class);
    }
}
