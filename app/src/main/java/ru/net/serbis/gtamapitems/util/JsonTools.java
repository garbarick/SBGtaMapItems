package ru.net.serbis.gtamapitems.util;

import java.util.*;
import org.json.*;
import ru.net.serbis.gtamapitems.data.*;

public class JsonTools
{
    public String toJson(List<Check> checks)
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
        return result.toString();
    }

    public List<Check> parseChecks(String json)
    {
        List<Check> result = new ArrayList<Check>();
        try
        {
            JSONArray items = new JSONArray(json);
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

    public String toJson(float[] data)
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
        return result.toString();
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
}
