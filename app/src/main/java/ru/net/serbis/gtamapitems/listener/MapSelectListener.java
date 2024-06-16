package ru.net.serbis.gtamapitems.listener;

import android.app.*;
import android.content.*;
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

    public MapSelectListener(Activity context, MapsAdapter adapter)
    {
        this.context = context;
        this.adapter = adapter;
        imageMap = Tools.get().findView(context, R.id.map);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
    {
        Map map = adapter.getItem(pos);
        imageMap.setImageResource(map.getDrawableId());
        imageMap.setChecks(map.getChecks());
        imageMap.reset();

        SharedPreferences.Editor editor = Tools.get().getPreferencesEditor(context);
        editor.putString(Constants.LAST_MAP, map.getKey());
        editor.commit();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {
    }
}
