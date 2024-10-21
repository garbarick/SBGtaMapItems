package ru.net.serbis.gtamapitems.popup;

import android.app.*;
import android.content.*;
import android.view.*;
import android.widget.*;
import ru.net.serbis.gtamapitems.*;
import ru.net.serbis.gtamapitems.data.*;
import ru.net.serbis.gtamapitems.util.*;

public class MapsPopup extends PopupMenu
{
    public MapsPopup(Activity context, View anchor)
    {
        super(context, anchor);
        init(context);
    }

    private void init(Activity context)
    {
        initMenuFolders();
        if (Maps.get().getLast() == null)
        {
            GameMap first = Maps.get().getFolders().iterator().next().getGames().get(0);
            Maps.get().setLast(first);
        }
    }

    public void initMenuFolders()
    {
        Menu menu = getMenu();
        menu.clear();
        for (GameFolder folder : Maps.get().getFolders())
        {
            SubMenu sub = menu.addSubMenu(folder.getFullName());
            setAction(sub.getItem(), folder.getName());
        }
    }

    public void initMenuGames(String folder)
    {
        Menu menu = getMenu();
        menu.clear();
        add(Constants.PARENT, Constants.PARENT);
        for (GameMap map : Maps.get().getFolder(folder).getGames())
        {
            MenuItem item = add(map.getName(), map.getKey());
            map.setItem(item);
        }
    }

    private MenuItem add(String name, String action)
    {
        Menu menu = getMenu();
        return setAction(menu.add(name), action);
    }
    
    private MenuItem setAction(MenuItem item, String action)
    {
        item.setIntent(new Intent(action));
        return item;
    }
}
