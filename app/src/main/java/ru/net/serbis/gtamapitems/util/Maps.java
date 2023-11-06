package ru.net.serbis.gtamapitems.util;

import android.content.*;
import java.lang.reflect.*;
import java.util.*;
import ru.net.serbis.gtamapitems.*;
import ru.net.serbis.gtamapitems.data.*;

public class Maps
{
    public List<Resource> getMaps(Context context)
    {
        List<Resource> result = new ArrayList<Resource>();
        for(Field field : R.drawable.class.getFields())
        {
            if (field.getName().startsWith("map_"))
            {
                Resource resource = createResource(context, field);
                if (resource != null)
                {
                    result.add(resource);
                }
            }
        }
        return result;
    }

    private Resource createResource(Context context, Field field)
    {
        try
        {
            String resName = field.getName();
            int nameId = getStringId(resName);
            String name = context.getString(nameId);
            int id = field.get(null);
            return new Resource(name, resName, id);
        }
        catch (Exception e)
        {
            Log.error(this, e);
            return null;
        }
    }

    private int getStringId(String name) throws Exception
    {
        return R.string.class.getField(name).get(null);
    }
}
