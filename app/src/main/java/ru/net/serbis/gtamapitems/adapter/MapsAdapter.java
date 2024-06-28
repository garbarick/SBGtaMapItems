package ru.net.serbis.gtamapitems.adapter;

import android.*;
import android.content.*;
import android.widget.*;
import ru.net.serbis.gtamapitems.data.*;
import ru.net.serbis.gtamapitems.util.*;

public class MapsAdapter extends ArrayAdapter<GameMap>
{
    public MapsAdapter(Context context)
    {
        super(
            context,
            R.layout.simple_spinner_dropdown_item);
        addAll(Maps.get().getItems().values());
    }
}
