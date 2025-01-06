package ru.net.serbis.gtamapitems.util;

import android.util.*;
import java.text.*;
import java.util.*;
import org.json.*;
import ru.net.serbis.gtamapitems.*;
import ru.net.serbis.gtamapitems.data.*;
import ru.net.serbis.utils.*;

import ru.net.serbis.utils.Log;

public class JsonTools extends Util
{
    private static final JsonTools instance = new JsonTools();

    public static JsonTools get()
    {
        return instance;
    }

    private JsonTools()
    {
    }

    public JSONArray toJson(List<Check> checks, boolean convert)
    {
        JSONArray result = new JSONArray();
        try
        {
            for (Check check : checks)
            {
                JSONObject item = new JSONObject();
                int x = check.x;
                int y = check.y;
                if (convert)
                {
                    x = pixelsToDp(x);
                    y = pixelsToDp(y);
                }
                item.put("x", x);
                item.put("y", y);
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
            result.addAll(parseChecks(items, false));
        }
        catch (Exception e)
        {
            Log.error(this, e);
        }
        return result;
    }

    private List<Check> parseChecks(JSONArray items, boolean convert)
    {
        List<Check> result = new ArrayList<Check>();
        try
        {
            for (int i = 0; i < items.length(); i++)
            {
                JSONObject item = items.getJSONObject(i);
                int x = getInt(item, "x");
                int y = getInt(item, "y");
                if (convert)
                {
                    x = dpToPixel(x);
                    y = dpToPixel(y);
                }
                Check check = new Check(x, y, getInt(item, "t"));
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
                result.put(map.getKey(), toJson(checks, true));
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
                    List<Check> checks = parseChecks(array, true);
                    GameMap map = maps.get(key);
                    map.saveChecks(checks);
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

    private int dpToPixel(float dp)
    {
        return new Double(dp * context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT).intValue();
    }

    private int pixelsToDp(int px)
    {
        return new Double(px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT)).intValue();
    }

    public JSONObject toJson(Map<Integer, String> data)
    {
        JSONObject result = new JSONObject();
        try
        {
            for (Map.Entry<Integer, String> entry : data.entrySet())
            {
                result.put(entry.getKey().toString(), entry.getValue());
            }
        }
        catch (Exception e)
        {
            Log.error(this, e);
        }
        return result;
    }

    public Map<Integer, String> parseChecNames(String data)
    {
        Map<Integer, String> result = new HashMap<Integer, String>();
        try
        {
            JSONObject object = new JSONObject(data);
            for (int i = 0; i < CheckBoxes.get().size(); i++)
            {
                String key = Integer.valueOf(i).toString();
                if (object.has(key))
                {
                    result.put(i, object.getString(key));
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
