package ru.net.serbis.gtamapitems.util;

import android.content.*;
import java.util.*;
import java.util.regex.*;
import ru.net.serbis.gtamapitems.*;
import ru.net.serbis.gtamapitems.data.*;
import ru.net.serbis.utils.*;

import ru.net.serbis.gtamapitems.R;

public class Maps extends Util
{
    private static final Maps instance = new Maps();

    private Map<String, GameMap> items = new TreeMap<String, GameMap>();
    private Map<String, GameFolder> folders = new TreeMap<String, GameFolder>();

    public static Maps get()
    {
        return instance;
    }

    public Map<String, GameMap> getItems()
    {
        return items;
    }

    @Override
    public void set(Context context)
    {
        super.set(context);
        collectItems();
    }

    private void collectItems()
    {
        Pattern layer = Pattern.compile("(map_.*?)_layer");
        Map<String, Integer> pictures = Reflection.get().getValues(R.drawable.class, int.class);
        Map<String, Integer> games = getGames(pictures);
        Map<String, Integer> strings = Reflection.get().getValues(R.string.class, int.class);
        for (Map.Entry<String, Integer> entry : pictures.entrySet())
        {
            String name = entry.getKey();
            int pictureId = entry.getValue();
            boolean withLayer = false;

            Matcher matcher = layer.matcher(name);
            if (matcher.matches())
            {
                name = matcher.group(1);
                withLayer = true;
            }
            if (name.startsWith("map_") &&
                strings.containsKey(name))
            {
                if (withLayer)
                {
                    for (Map.Entry<String, Integer> entryGame : games.entrySet())
                    {
                        String game = entryGame.getKey();
                        int mainId = entryGame.getValue();

                        if (name.startsWith("map_" + game + "_"))
                        {
                            addItem(new GameMap(name, strings.get(name), mainId, pictureId));
                            break;
                        }
                    }
                }
                else
                {
                    addItem(new GameMap(name, strings.get(name), pictureId));
                }
            }
        }
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

    private void addItem(GameMap item)
    {
        String checks = Preferences.get().getString(item.getKey(), "[]");
        item.setChecks(JsonTools.get().parseChecks(checks));

        String values = Preferences.get().getString(item.getKeyValues(), "[]");
        item.setValues(JsonTools.get().parseValues(values));

        items.put(item.getKey(), item);

        String parent = item.getParent();
        if (!folders.containsKey(parent))
        {
            folders.put(parent, new GameFolder(parent));
        }
        folders.get(parent).add(item);
    }

    public Collection<GameFolder> getFolders()
    {
        return folders.values();
    }

    public GameMap get(String key)
    {
        return items.get(key);
    }

    public GameMap getLast()
    {
        return get(Preferences.get().getString(Constants.LAST_MAP, ""));
    }

    public void setLast(GameMap map)
    {
        Preferences.get().setString(Constants.LAST_MAP, map.getKey());
    }
}
