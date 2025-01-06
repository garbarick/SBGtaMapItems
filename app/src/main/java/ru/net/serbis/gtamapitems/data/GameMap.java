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

    public List<Check> getChecks()
    {
        String checks = Preferences.get().getString(key, "[]");
        return JsonTools.get().parseChecks(checks);
    }

    public int getCount()
    {
        return getChecks().size();
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

    public void saveChecks(List<Check> checks)
    {
        Preferences.get().setString(key, JsonTools.get().toJson(checks, false).toString());
    }

    public float[] getValues()
    {
        Log.info(this, getKeyValues());
        String values = Preferences.get().getString(getKeyValues(), "[]");
        return JsonTools.get().parseValues(values);
    }

    private String getKeyValues()
    {
        return key + "#values";
    }

    public void saveValues(float[] values)
    {
        Preferences.get().setString(getKeyValues(), JsonTools.get().toJson(values).toString());
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

    private String getKeyCheckNames()
    {
        return key + "#checkNames";
    }

    public Map<Integer, String> getCheckNames()
    {
        return JsonTools.get().parseChecNames(Preferences.get().getString(getKeyCheckNames(), "{}"));
    }

    public void saveCheckNames(Map<Integer, String> data)
    {
        Preferences.get().setString(getKeyCheckNames(), JsonTools.get().toJson(data).toString());
    }
}
