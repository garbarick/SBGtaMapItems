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
    private ImageViewExt img;

    public MapSelectListener(Activity context, MapsAdapter adapter)
    {
        this.context = context;
        this.adapter = adapter;
        img = Tools.get().findView(context, R.id.img);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
    {
        Resource resource = adapter.getItem(pos);
        img.setImageResource(resource.getId());
        String mapId = resource.getNameId();
        String value = Tools.get().getPreferences(context).getString(mapId, "[]");
        img.setPoints(new JsonTools().parsePoints(value));

        SharedPreferences.Editor editor = Tools.get().getPreferencesEditor(context);
        editor.putString(Constants.LAST_MAP, mapId);
        editor.commit();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {
    }
}
