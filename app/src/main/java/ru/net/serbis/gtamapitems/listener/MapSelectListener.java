package ru.net.serbis.gtamapitems.listener;

import android.app.*;
import android.content.*;
import android.view.*;
import android.widget.*;
import ru.net.serbis.gtamapitems.*;
import ru.net.serbis.gtamapitems.data.*;
import ru.net.serbis.gtamapitems.popup.*;
import ru.net.serbis.gtamapitems.util.*;
import ru.net.serbis.gtamapitems.view.*;
import ru.net.serbis.utils.*;

import ru.net.serbis.gtamapitems.R;

public class MapSelectListener implements PopupMenu.OnMenuItemClickListener, ImageMap.OnChangeListener
{
    private Activity context;
    private ImageMap imageMap;
    private GameMap current;
    private Button maps;
    private MapsPopup popup;

    public MapSelectListener(Activity context, MapsPopup popup)
    {
        this.context = context;
        this.popup = popup;
        imageMap = UITool.get().findView(context, R.id.map);
        imageMap.setOnChangeCheckingListener(this);
        maps = UITool.get().findView(context, R.id.maps);

        current = Maps.get().getLast();
        apply();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem)
    {
        Intent intent = menuItem.getIntent();
        if (intent == null)
        {
            return false;
        }
        if (current != null)
        {
            current.saveValues(imageMap.getMatrixValues());
        }
        final String action = intent.getAction();
        if (Constants.PARENT.equals(action))
        {
            maps.post(
                new Runnable()
                {
                    public void run()
                    {
                        popup.initMenuFolders();
                        popup.show();
                    }
                }
            );
        }
        else if (Maps.get().isFolder(action))
        {
            maps.post(
                new Runnable()
                {
                    public void run()
                    {
                        popup.initMenuGames(action);
                        popup.show();
                    }
                }
            );
        }
        else if (Maps.get().isGame(action))
        {
            current = Maps.get().get(action);
            apply();
            Maps.get().setLast(current);
        }
        return false;
    }

    private void apply()
    {
        imageMap.setImageResource(current.getPictureId());
        imageMap.setLayer(current.getLayerId());
        imageMap.setChecks(current.getChecks());
        imageMap.reset(false);
        imageMap.setMatrixValues(current.getValues());
        maps.setText(current.getFullName());
    }

    @Override
    public void onChangeChecking(ImageMap view)
    {
        current.saveChecks(view.getChecks());
        maps.setText(current.getFullName());
        current.setItemTitle();
    }

    @Override
    public void onChangeMatrixValues(ImageMap view)
    {
        current.saveValues(view.getMatrixValues());
    }
}
