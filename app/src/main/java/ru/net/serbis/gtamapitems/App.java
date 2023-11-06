package ru.net.serbis.gtamapitems;

import android.app.*;
import ru.net.serbis.gtamapitems.handler.*;

public class App extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getApplicationContext()));
    }
}
