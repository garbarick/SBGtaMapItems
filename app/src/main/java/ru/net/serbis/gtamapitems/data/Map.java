package ru.net.serbis.gtamapitems.data;

import android.content.*;
import java.util.*;

public class Map extends Resource
{
    private List<Check> checks;

    public Map(String name, int nameId, int drawableId)
    {
        super(name, nameId, drawableId);
    }

    public void setChecks(List<Check> checks)
    {
        this.checks = checks;
    }

    public List<Check> getChecks()
    {
        return checks;
    }

    public int getCount()
    {
        return checks.size();
    }
    
    public String getName(Context context)
    {
        String result = context.getResources().getText(nameId).toString();
        int count = getCount();
        if (count > 0)
        {
            return result + " (" + count + ")";
        }
        return result;
    }
}
