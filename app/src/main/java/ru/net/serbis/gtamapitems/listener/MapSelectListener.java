package ru.net.serbis.gtamapitems.listener;

import android.app.*;
import android.view.*;
import android.widget.*;
import ru.net.serbis.gtamapitems.*;
import ru.net.serbis.gtamapitems.adapter.*;
import ru.net.serbis.gtamapitems.data.*;
import ru.net.serbis.gtamapitems.util.*;
import ru.net.serbis.gtamapitems.view.*;

public class MapSelectListener implements AdapterView.OnItemSelectedListener
{
    private Activity context;
    private MapsAdapter adapter;
    private ImageMap imageMap;
    private GameMap current;

    public MapSelectListener(Activity context, MapsAdapter adapter)
    {
        this.context = context;
        this.adapter = adapter;
        imageMap = Tools.get().findView(context, R.id.map);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
    {
        if (current != null)
        {
            current.setValues(imageMap.getMatrixValues());
            current.saveValues();
        }

        GameMap map = adapter.getItem(pos);
        imageMap.setImageResource(map.getDrawableId());
        imageMap.setChecks(map.getChecks());
        imageMap.reset(false);
        imageMap.setMatrixValues(map.getValues());

        Preferences.get().setString(Constants.LAST_MAP, map.getKey());
        current = map;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {
    }
}
