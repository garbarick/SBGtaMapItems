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
        Pattern layer = Pattern.compile("(map_.*?)_layer_(.*)");
        Map<String, Integer> pictures = Reflection.get().getValues(R.drawable.class, int.class);
        Map<String, Integer> strings = Reflection.get().getValues(R.string.class, int.class);
        for (Map.Entry<String, Integer> entry : pictures.entrySet())
        {
            String main = null;
            String name = entry.getKey();
            int pictureId = entry.getValue();
            boolean withLayer = false;

            Matcher matcher = layer.matcher(name);
            if (matcher.matches())
            {
                name = matcher.group(1);
                main = matcher.group(2);
                withLayer = true;
            }
            if (name.startsWith("map_") &&
                strings.containsKey(name))
            {
                if (withLayer)
                {
                    int mainId = pictures.get(main);
                    addItem(new GameMap(name, strings.get(name), mainId, pictureId));
                }
                else
                {
                    addItem(new GameMap(name, strings.get(name), pictureId));
                }
            }
        }
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

    public boolean isGame(String key)
    {
        return items.containsKey(key);
    }

    public boolean isFolder(String key)
    {
        return folders.containsKey(key);
    }

    public GameFolder getFolder(String key)
    {
        return folders.get(key);
    }
}
