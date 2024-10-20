package ru.net.serbis.gtamapitems.data;

import android.view.*;
import java.util.*;
import ru.net.serbis.gtamapitems.util.*;
import ru.net.serbis.utils.*;

public class GameMap
{
    protected String key;
    protected int id;
    protected int pictureId;
    protected int layerId;
    private List<Check> checks = new ArrayList<Check>();
    private float[] values;
    private MenuItem item;

    public GameMap(String key, int id, int pictureId, int layerId)
    {
        this.key = key;
        this.id = id;
        this.pictureId = pictureId;
        this.layerId = layerId;
    }

    public GameMap(String key, int id, int pictureId)
    {
        this(key, id, pictureId, 0);
    }

    public String getKey()
    {
        return key;
    }

    public int getPictureId()
    {
        return pictureId;
    }

    public int getLayerId()
    {
        return layerId;
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

    public String getFullName()
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
        Preferences.get().setString(key, JsonTools.get().toJson(checks, false).toString());
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
        Preferences.get().setString(getKeyValues(), JsonTools.get().toJson(getValues()).toString());
    }

    public String getName()
    {
        return getFullName().split("/")[1];
    }

    public String getParent()
    {
        return getFullName().split("/")[0];
    }

    public void setItem(MenuItem item)
    {
        this.item = item;
    }

    public void setItemTitle()
    {
        if (item == null)
        {
            return;
        }
        item.setTitle(getName());
    }
}
