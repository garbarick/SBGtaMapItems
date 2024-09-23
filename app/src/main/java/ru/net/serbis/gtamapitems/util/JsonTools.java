package ru.net.serbis.gtamapitems.util;

import java.text.*;
import java.util.*;
import org.json.*;
import ru.net.serbis.gtamapitems.*;
import ru.net.serbis.gtamapitems.data.*;
import ru.net.serbis.utils.*;

public class JsonTools
{
    public JSONArray toJson(List<Check> checks)
    {
        JSONArray result = new JSONArray();
        try
        {
            for (Check check : checks)
            {
                JSONObject item = new JSONObject();
                item.put("x", check.x);
                item.put("y", check.y);
                item.put("t", check.type);
                result.put(item);
            }
        }
        catch (Exception e)
        {
            Log.error(this, e);
        }
        return result;
    }

    public List<Check> parseChecks(String json)
    {
        List<Check> result = new ArrayList<Check>();
        try
        {
            JSONArray items = new JSONArray(json);
            result.addAll(parseChecks(items));
        }
        catch (Exception e)
        {
            Log.error(this, e);
        }
        return result;
    }

    public List<Check> parseChecks(JSONArray items)
    {
        List<Check> result = new ArrayList<Check>();
        try
        {
            for (int i = 0; i < items.length(); i++)
            {
                JSONObject item = items.getJSONObject(i);
                Check check = new Check(getInt(item, "x"), getInt(item, "y"), getInt(item, "t"));
                result.add(check);
            }
        }
        catch (Exception e)
        {
            Log.error(this, e);
        }
        return result;
    }

    private int getInt(JSONObject item, String key) throws Exception
    {
        return item.has(key) ? item.getInt(key) : 0;
    }

    public JSONArray toJson(float[] data)
    {
        JSONArray result = new JSONArray();
        try
        {
            for (float i : data)
            {
                result.put(i);
            }
        }
        catch (JSONException e)
        {
            Log.error(this, e);
        }
        return result;
    }

    public float[] parseValues(String data)
    {
        float[] result = null;
        try
        {
            JSONArray items = new JSONArray(data);
            if (items.length() > 0)
            {
                result = new float[items.length()];
                for (int i = 0; i < items.length(); i++)
                {
                    Double value = items.getDouble(i);
                    result[i] = value.floatValue();
                }
            }
        }
        catch (Exception e)
        {
            Log.error(this, e);
        }
        return result;
    }

    public JSONObject toJson(Collection<GameMap> maps)
    {
        JSONObject result = new JSONObject();
        try
        {
            for (GameMap map : maps)
            {
                List<Check> checks = map.getChecks();
                if (checks.isEmpty())
                {
                    continue;
                }
                result.put(map.getKey(), toJson(checks));
            }
        }
        catch (Exception e)
        {
            Log.error(this, e);
        }
        return result;
    }

    public boolean parseMapChecks(String data)
    {
        try
        {
            Map<String, GameMap> maps = Maps.get().getItems();
            JSONObject object = new JSONObject(data);
            Iterator<String> iter = object.keys();
            while (iter.hasNext())
            {
                String key = iter.next();
                if (maps.containsKey(key))
                {
                    JSONArray array = object.getJSONArray(key);
                    List<Check> checks = parseChecks(array);
                    GameMap map = maps.get(key);
                    map.setChecks(checks);
                    map.saveChecks();
                }
            }
            return true;
        }
        catch (Exception e)
        {
            Log.error(this, e);
            UITool.get().toast(e.getMessage());
            return false;
        }
    }

    public String getJsonName()
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        return Constants.APP + "-" + format.format(new Date()) + Constants.JSON_EXT;
    }
}
