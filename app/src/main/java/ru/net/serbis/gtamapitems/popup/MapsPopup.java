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
            for (GameMap map : Maps.get().getItems(folder))
            {
                MenuItem item = subMenu.add(map.getName());
                if (first == null)
                {
                    first = map;
                }
                item.setIntent(new Intent(map.getKey()));
            }
        }
        if (Maps.get().getLast() == null)
        {
            Maps.get().setLast(first);
        }
    }
}
