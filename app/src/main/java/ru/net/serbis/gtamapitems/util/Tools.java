package ru.net.serbis.gtamapitems.util;

import android.app.*;
import android.view.*;
import java.io.*;

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

    public String errorToText(Throwable error)
    {
        StringWriter writer = new StringWriter();
        error.printStackTrace(new PrintWriter(writer));
        return writer.toString();
    }
}
