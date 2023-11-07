package ru.net.serbis.gtamapitems.activity;

import android.app.*;
import android.content.*;
import android.content.res.*;
import android.os.*;
import android.widget.*;
import ru.net.serbis.gtamapitems.*;
import ru.net.serbis.gtamapitems.adapter.*;
import ru.net.serbis.gtamapitems.data.*;
import ru.net.serbis.gtamapitems.listener.*;
import ru.net.serbis.gtamapitems.util.*;
import ru.net.serbis.gtamapitems.view.*;

public class Main extends Activity implements OnChangeCheckingListener
{
    private Spinner maps;
    private MapsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        
        initImg();
        initMaps();
        new ButtonsListener(this);
    }

    private int getLayout()
    {
        if (Configuration.ORIENTATION_LANDSCAPE == getResources().getConfiguration().orientation)
        {
            return R.layout.main_landscape;
        }
        return R.layout.main_portrait;
    }

    private void initImg()
    {
        ImageViewExt img = Tools.get().findView(this, R.id.img);
        img.setOnChangeCheckingListener(this);
    }

    private void initMaps()
    {
        maps = Tools.get().findView(this, R.id.maps);
        adapter = new MapsAdapter(this);
        maps.setAdapter(adapter);
        maps.setOnItemSelectedListener(new MapSelectListener(this, adapter));

        String mapId = Tools.get().getPreferences(this).getString(Constants.LAST_MAP, "");
        initCurrent(mapId);
    }

    private void initCurrent(String mapId)
    {
        for (int i = 0; i < adapter.getCount(); i++)
        {
            Resource resource = adapter.getItem(i);
            if (mapId.equals(resource.getNameId()))
            {
                maps.setSelection(i);
                break;
            }
        }
    }

    @Override
    public void onChange(ImageViewExt view)
    {
        Resource resource = adapter.getItem(maps.getSelectedItemPosition());
        String mapId = resource.getNameId();
        String value = new JsonTools().toJson(view.getPoints());
        SharedPreferences.Editor editor = Tools.get().getPreferencesEditor(this);
        editor.putString(mapId, value);
        editor.commit();
    }
}
