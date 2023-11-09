package ru.net.serbis.gtamapitems.util;

import android.widget.*;
import java.lang.reflect.*;

public class Reflection
{
    public void setIconPopup(PopupMenu menu)
    {
        try
        {
            Field fMenuHelper = PopupMenu.class.getDeclaredField("mPopup");
            fMenuHelper.setAccessible(true);
            Object menuHelper = fMenuHelper.get(menu);
            Class[] argTypes = new Class[]{boolean.class};
            menuHelper.getClass().getDeclaredMethod("setForceShowIcon", argTypes).invoke(menuHelper, true);
        }
        catch (Exception e)
        {
            Log.error(this, e);
        }
    }
}
