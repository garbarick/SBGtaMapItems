package ru.net.serbis.gtamapitems.util;

import java.util.*;
import ru.net.serbis.gtamapitems.*;
import ru.net.serbis.gtamapitems.data.*;
import ru.net.serbis.utils.*;

import ru.net.serbis.gtamapitems.R;

public class Maps
{
    private static final Maps instance = new Maps();

    private List<GameMap> items = new ArrayList<GameMap>();

    private Maps()
    {
    }

    public static Maps get()
    {
        return instance;
    }

    public Map<String, GameMap> getItems()
    {
        collectItems();
        Map<String, GameMap> result = new LinkedHashMap<String, GameMap>();
        for (GameMap map : items)
        {
            String checks = Preferences.get().getString(map.getKey(), "[]");
            map.setChecks(new JsonTools().parseChecks(checks));

            String values = Preferences.get().getString(map.getKeyValues(), "[]");
            map.setValues(new JsonTools().parseValues(values));

            result.put(map.getKey(), map);
        }
        return result;
    }

    private void collectItems()
    {
        if (!items.isEmpty())
        {
            return;
        }
        Map<String, Integer> drawables = Reflection.get().getValues(R.drawable.class, int.class);
        Map<String, Integer> strings = Reflection.get().getValues(R.string.class, int.class);
        for (Map.Entry<String, Integer> entry : drawables.entrySet())
        {
            String name = entry.getKey();
            if (name.startsWith("map_") && strings.containsKey(name))
            {
                items.add(new GameMap(name, strings.get(name), entry.getValue()));
            }
        }
        Collections.sort(items);
    }
}
