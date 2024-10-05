package ru.net.serbis.gtamapitems.data;

import java.util.*;
import ru.net.serbis.gtamapitems.util.*;
import ru.net.serbis.utils.*;

public class GameMap implements Comparable<GameMap>
{
    protected String name;
    protected int id;
    protected int pictureId;
    protected int layerId;
    private List<Check> checks = new ArrayList<Check>();
    private float[] values;

    public GameMap(String name, int id, int pictureId, int layerId)
    {
        this.name = name;
        this.id = id;
        this.pictureId = pictureId;
        this.layerId = layerId;
    }

    public GameMap(String name, int id, int pictureId)
    {
        this(name, id, pictureId, 0);
    }

    public String getName()
    {
        return name;
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
        Preferences.get().setString(name, new JsonTools().toJson(checks).toString());
    }

    public float[] getValues()
    {
        return values;
    }

    public String getKeyValues()
    {
        return name + "#values";
    }

    public void saveValues()
    {
        Preferences.get().setString(getKeyValues(), new JsonTools().toJson(getValues()).toString());
    }

    @Override
    public int compareTo(GameMap that)
    {
        return name.compareTo(that.name);
    }
}
