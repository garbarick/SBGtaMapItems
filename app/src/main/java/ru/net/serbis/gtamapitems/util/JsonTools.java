package ru.net.serbis.gtamapitems.util;

import java.util.*;
import org.json.*;
import ru.net.serbis.gtamapitems.data.*;

public class JsonTools
{
    public String toJson(List<Check> checks)
    {
        JSONArray items = new JSONArray();
        try
        {
            for (Check check : checks)
            {
                JSONObject item = new JSONObject();
                item.put("x", check.x);
                item.put("y", check.y);
                item.put("t", check.type);
                items.put(item);
            }
        }
        catch (Exception e)
        {
            Log.error(this, e);
        }
        return items.toString();
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
}
