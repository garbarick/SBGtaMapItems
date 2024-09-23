package ru.net.serbis.gtamapitems.data;

import java.util.*;
import ru.net.serbis.gtamapitems.util.*;
import ru.net.serbis.utils.*;

public class GameMap extends Resource
{
    private List<Check> checks = new ArrayList<Check>();
    private float[] values;

    public GameMap(String name, int id, int drawableId)
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

    @Override
    public String toString()
    {
        String result = Strings.get().get(id);
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

    public void saveChecks()
    {
        Preferences.get().setString(getKey(), new JsonTools().toJson(checks).toString());
    }

    public float[] getValues()
    {
        return values;
    }

    public String getKeyValues()
    {
        return key + "#values";
    }

    public void saveValues()
    {
        Preferences.get().setString(getKeyValues(), new JsonTools().toJson(getValues()).toString());
    }
}
