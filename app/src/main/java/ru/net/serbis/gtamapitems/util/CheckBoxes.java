package ru.net.serbis.gtamapitems.util;

import android.content.*;
import android.graphics.drawable.*;
import java.lang.reflect.*;
import java.util.*;
import ru.net.serbis.gtamapitems.*;
import ru.net.serbis.gtamapitems.data.*;

public class CheckBoxes
{
    private List<Drawable> items = new ArrayList<Drawable>();

    public void init(Context context)
    {
        for(Field field : R.drawable.class.getFields())
        {
            if (field.getName().startsWith("check_box_"))
            {
                Drawable item = getDrawable(field, context);
                if (item != null)
                {
                    items.add(item);
                }
            }
        }
    }

    protected Drawable getDrawable(Field field, Context context)
    {
        try
        {
            int id = field.get(null);
            return context.getResources().getDrawable(id);
        }
        catch (Exception e)
        {
            Log.error(this, e);
            return null;
        }
    }

    public Drawable getItem(int type)
    {
        return items.get(type);
    }
}
