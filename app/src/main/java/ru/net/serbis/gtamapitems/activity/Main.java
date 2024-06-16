package ru.net.serbis.gtamapitems.activity;

import android.app.*;
import android.content.*;
import android.content.res.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import ru.net.serbis.gtamapitems.*;
import ru.net.serbis.gtamapitems.adapter.*;
import ru.net.serbis.gtamapitems.data.*;
import ru.net.serbis.gtamapitems.listener.*;
import ru.net.serbis.gtamapitems.util.*;
import ru.net.serbis.gtamapitems.view.*;

public class Main extends Activity implements ImageMap.OnChangeCheckingListener
{
    private Spinner maps;
    private MapsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        
        initMap();
        initMaps();
        new ButtonsListener(this);
    }

    private int getLayout()
    {
        if (Configuration.ORIENTATION_LANDSCAPE == getResources().getConfiguration().orientation)
        {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            return R.layout.main_landscape;
        }
        return R.layout.main_portrait;
    }

    private void initMap()
    {
        ImageMap map = Tools.get().findView(this, R.id.map);
        map.setOnChangeCheckingListener(this);
    }

    private void initMaps()
    {
        maps = Tools.get().findView(this, R.id.maps);
        adapter = new MapsAdapter(this);
        maps.setAdapter(adapter);
        maps.setOnItemSelectedListener(new MapSelectListener(this, adapter));

        String mapKey = Tools.get().getPreferences(this).getString(Constants.LAST_MAP, "");
        initCurrent(mapKey);
    }

    private void initCurrent(String mapKey)
    {
        for (int i = 0; i < adapter.getCount(); i++)
        {
            Map map = adapter.getItem(i);
            if (mapKey.equals(map.getKey()))
            {
                maps.setSelection(i);
                break;
            }
        }
    }

    @Override
    public void onChangeChecking(ImageMap view)
    {
        Map map = adapter.getItem(maps.getSelectedItemPosition());
        map.setChecks(view.getChecks());
        String value = new JsonTools().toJson(view.getChecks());
        SharedPreferences.Editor editor = Tools.get().getPreferencesEditor(this);
        editor.putString(map.getKey(), value);
        editor.commit();
        adapter.notifyDataSetChanged();
    }
}
