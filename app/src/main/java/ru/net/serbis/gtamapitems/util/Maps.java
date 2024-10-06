package ru.net.serbis.gtamapitems.util;

import java.util.*;
import java.util.regex.*;
import ru.net.serbis.gtamapitems.*;
import ru.net.serbis.gtamapitems.data.*;
import ru.net.serbis.utils.*;

import ru.net.serbis.gtamapitems.R;
import android.content.*;
import android.graphics.drawable.*;

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
                    if (name.startsWith("map_" + game + "_") &&
                        isSameSize(entryGame.getValue(), entry.getValue()))
                    {
                        addItem(new GameMap(name, strings.get(name), entryGame.getValue(), entry.getValue()));
                        withLayer = true;
                        break;
                    }
                }
                if (withLayer)
                {
                    continue;
                }
                addItem(new GameMap(name, strings.get(name), entry.getValue()));
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
        item.setChecks(new JsonTools().parseChecks(checks));

        String values = Preferences.get().getString(item.getKeyValues(), "[]");
        item.setValues(new JsonTools().parseValues(values));

        items.put(item.getKey(), item);

        String parent = item.getParent();
        if (!folders.containsKey(parent))
        {
            folders.put(parent, new GameFolder(parent));
        }
        folders.get(parent).add();
    }

    public Collection<GameFolder> getFolders()
    {
        return folders.values();
    }

    public Collection<GameMap> getItems(GameFolder folder)
    {
        List<GameMap> result = new ArrayList<GameMap>();
        for (GameMap item : items.values())
        {
            if (folder.getName().equals(item.getParent()))
            {
                result.add(item);
            }
        }
        return result;
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

    private boolean isSameSize(int pictureId, int layerId)
    {
        Drawable picture = context.getDrawable(pictureId);
        Drawable layer = context.getDrawable(layerId);
        return picture.getIntrinsicHeight() == layer.getIntrinsicHeight() &&
            picture.getIntrinsicWidth() == layer.getIntrinsicWidth();
    }
}
