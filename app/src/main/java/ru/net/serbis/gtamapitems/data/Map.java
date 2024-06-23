package ru.net.serbis.gtamapitems.data;

import android.content.*;
import java.util.*;
import ru.net.serbis.gtamapitems.util.*;

public class Map extends Resource
{
    private List<Check> checks = new ArrayList<Check>();
    private float[] values;

    public Map(String name, int id, int drawableId)
    {
        super(name, id, drawableId);
    }

    public void setChecks(List<Check> checks)
    {
        this.checks.clear();
        this.checks.addAll(checks);
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
        String result = context.getResources().getText(id).toString();
        int count = getCount();
        if (count > 0)
        {
            return result + " (" + count + ")";
        }
        return result;
    }

    public void setValues(float[] values)
    {
        this.values = values;
    }

    public float[] getValues()
    {
        return values;
    }

    public void saveValues()
    {
        Preferences.get().setString(getKeyValues(), new JsonTools().toJson(getValues()));
    }
}
