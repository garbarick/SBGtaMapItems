package ru.net.serbis.gtamapitems.util;

import java.util.*;
import java.util.regex.*;
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
            String checks = Preferences.get().getString(map.getName(), "[]");
            map.setChecks(new JsonTools().parseChecks(checks));

            String values = Preferences.get().getString(map.getKeyValues(), "[]");
            map.setValues(new JsonTools().parseValues(values));

            result.put(map.getName(), map);
        }
        return result;
    }

    private void collectItems()
    {
        if (!items.isEmpty())
        {
            return;
        }
        Map<String, Integer> pictures = Reflection.get().getValues(R.drawable.class, int.class);
        Map<String, Integer> games = getGames(pictures);
        Map<String, Integer> strings = Reflection.get().getValues(R.string.class, int.class);
        for (Map.Entry<String, Integer> entry : pictures.entrySet())
        {
            String name = entry.getKey();
            if (name.startsWith("map_") &&
                strings.containsKey(name))
            {
                boolean withLayer = false;
                for (Map.Entry<String, Integer> entryGame : games.entrySet())
                {
                    String game = entryGame.getKey();
                    if (name.startsWith("map_" + game + "_"))
                    {
                        items.add(new GameMap(name, strings.get(name), entryGame.getValue(), entry.getValue()));
                        withLayer = true;
                        break;
                    }
                }
                if (withLayer)
                {
                    continue;
                }
                items.add(new GameMap(name, strings.get(name), entry.getValue()));
            }
        }
        Collections.sort(items);
    }

    private Map<String, Integer> getGames(Map<String, Integer> pictures)
    {
        Pattern main = Pattern.compile("map_(.*?)_main");
        Map<String, Integer> result = new HashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : pictures.entrySet())
        {
            String name = entry.getKey();
            Matcher matcher = main.matcher(name);
            if (matcher.matches())
            {
                String game = matcher.group(1);
                result.put(game, entry.getValue());
            }
        }
        return result;
    }
}
