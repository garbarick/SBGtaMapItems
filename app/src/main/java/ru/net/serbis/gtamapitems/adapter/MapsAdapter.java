package ru.net.serbis.gtamapitems.adapter;

import android.content.*;
import android.widget.*;
import ru.net.serbis.gtamapitems.data.*;
import ru.net.serbis.gtamapitems.util.*;

public class MapsAdapter extends ArrayAdapter<Resource>
{
    public MapsAdapter(Context context)
    {
        super(
            context,
            android.R.layout.simple_spinner_item,
            new Maps().getMaps(context));
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }
}
