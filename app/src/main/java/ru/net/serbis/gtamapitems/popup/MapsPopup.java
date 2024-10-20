package ru.net.serbis.gtamapitems.popup;

import android.app.*;
import android.content.*;
import android.view.*;
import android.widget.*;
import ru.net.serbis.gtamapitems.*;
import ru.net.serbis.gtamapitems.data.*;
import ru.net.serbis.gtamapitems.util.*;
import ru.net.serbis.utils.*;

public class MapsPopup extends PopupMenu
{
    public MapsPopup(Activity context, View anchor)
    {
        super(context, anchor);
        init(context);
    }

    private void init(Activity context)
    {
        GameMap first = null;
        Menu menu = getMenu();
        for (GameFolder folder : Maps.get().getFolders())
        {
            SubMenu subMenu = menu.addSubMenu(folder.getFullName());
            subMenu.setHeaderTitle(folder.getName());
            for (GameMap map : folder.getGames())
            {
                add(subMenu, map);
                if (first == null)
                {
                    first = map;
                }
            }
        }
        if (Maps.get().getLast() == null)
        {
            Maps.get().setLast(first);
        }
    }

    private void add(SubMenu subMenu, GameMap map)
    {
        MenuItem item = add(subMenu, map.getName(), map.getKey());
        map.setItem(item);
    }

    private MenuItem add(SubMenu subMenu, String name, String action)
    {
        MenuItem item = subMenu.add(name);
        item.setIntent(new Intent(action));
        return item;
    }

    @Override
    public void show()
    {
        super.show();
    }
}
