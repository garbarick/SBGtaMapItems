package ru.net.serbis.gtamapitems.util;

import android.graphics.*;
import java.util.*;
import org.json.*;

public class JsonTools
{
    public String toJson(List<Point> points)
    {
        JSONArray items = new JSONArray();
        try
        {
            for (Point point : points)
            {
                JSONObject item = new JSONObject();
                item.put("x", point.x);
                item.put("y", point.y);
                items.put(item);
            }
        }
        catch (Exception e)
        {
            Log.error(this, e);
        }
        return items.toString();
    }

    public List<Point> parsePoints(String json)
    {
        List<Point> points = new ArrayList<Point>();
        try
        {
            JSONArray items = new JSONArray(json);
            for (int i = 0; i < items.length(); i++)
            {
                JSONObject item = items.getJSONObject(i);
                Point point = new Point(item.getInt("x"), item.getInt("y"));
                points.add(point);
            }
        }
        catch (Exception e)
        {
            Log.error(this, e);
        }
        return points;
    }
}
