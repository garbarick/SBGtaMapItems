package ru.net.serbis.gtamapitems;

import android.app.*;
import android.content.*;
import ru.net.serbis.gtamapitems.handler.*;

public class App extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        Context context = getApplicationContext();
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(context));
    }
}
