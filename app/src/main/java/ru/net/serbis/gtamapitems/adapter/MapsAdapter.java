package ru.net.serbis.gtamapitems.adapter;

import android.*;
import android.content.*;
import android.view.*;
import android.widget.*;
import ru.net.serbis.gtamapitems.data.*;
import ru.net.serbis.gtamapitems.util.*;

public class MapsAdapter extends ArrayAdapter<Resource>
{
    public MapsAdapter(Context context)
    {
        super(
            context,
            R.layout.simple_spinner_item,
            Maps.get().getItems());
    }

    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        if (view == null)
        {
            view = LayoutInflater.from(getContext()).inflate(R.layout.simple_spinner_item, parent, false);
        }
        Resource resource = getItem(position);
        TextView text = Tools.get().findView(view, R.id.text1);
        text.setText(resource.getNameId());
        return view;
    }

    @Override
    public View getDropDownView(int position, View view, ViewGroup parent)
    {
        return getView(position, view, parent);
    }
}
