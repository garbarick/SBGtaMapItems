package ru.net.serbis.gtamapitems.activity;

import android.app.*;
import android.content.res.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import ru.net.serbis.gtamapitems.*;
import ru.net.serbis.gtamapitems.adapter.*;
import ru.net.serbis.gtamapitems.data.*;
import ru.net.serbis.gtamapitems.listener.*;
import ru.net.serbis.gtamapitems.view.*;
import ru.net.serbis.utils.*;

import ru.net.serbis.gtamapitems.R;

public class Main extends Activity implements ImageMap.OnChangeListener
{
    private ImageMap map;
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
        map = UITool.get().findView(this, R.id.map);
        map.setOnChangeCheckingListener(this);
    }

    private void initMaps()
    {
        maps = UITool.get().findView(this, R.id.maps);
        adapter = new MapsAdapter(this);
        maps.setAdapter(adapter);
        MapSelectListener listener = new MapSelectListener(this, adapter);
        maps.setOnItemSelectedListener(listener);

        String mapKey = Preferences.get().getString(Constants.LAST_MAP, "");
        initCurrent(mapKey);
    }

    private void initCurrent(String mapKey)
    {
        for (int i = 0; i < adapter.getCount(); i++)
        {
            GameMap map = adapter.getItem(i);
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
        GameMap map = adapter.getItem(maps.getSelectedItemPosition());
        map.setChecks(view.getChecks());
        map.saveChecks();
        adapter.notifyDataSetChanged();
    }

    public void notifyDataSetChanged()
    {
        adapter.notifyDataSetChanged();
        int pos = maps.getSelectedItemPosition();
        map.setChecks(adapter.getItem(pos).getChecks());
        map.invalidate();
    }

    @Override
    public void onChangeMatrixValues(ImageMap view)
    {
        GameMap map = adapter.getItem(maps.getSelectedItemPosition());
        map.setValues(view.getMatrixValues());
        map.saveValues();
    }
}
