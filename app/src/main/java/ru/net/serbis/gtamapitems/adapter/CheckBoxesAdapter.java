package ru.net.serbis.gtamapitems.adapter;

import android.content.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import ru.net.serbis.gtamapitems.*;
import ru.net.serbis.gtamapitems.data.*;
import ru.net.serbis.gtamapitems.popup.*;
import ru.net.serbis.gtamapitems.util.*;
import ru.net.serbis.utils.*;

import ru.net.serbis.gtamapitems.R;

public class CheckBoxesAdapter extends ArrayAdapter<Holder<NameCount>>
{
    public CheckBoxesAdapter(Context context)
    {
        super(context, R.layout.image_item);
        init();
    }

    protected void init()
    {
        int count = CheckBoxes.get().size();
        for (int i = 0; i < count; i++)
        {
            add(new Holder<NameCount>(new NameCount(null, 0)));
        }
    }

    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        if (view == null)
        {
            view = LayoutInflater.from(getContext()).inflate(R.layout.image_item, parent, false);
        }
        ImageView img = UITool.get().findView(view, R.id.image);
        img.setImageResource(CheckBoxes.get().getDrawableId(position));
        TextView count = UITool.get().findView(view, R.id.count);
        count.setText(getItem(position).getValue().toString());
        return view;
    }

    public void updateCounts(CheckBoxesPopup popup, List<Check> checks)
    {
        boolean hasNames = false;
        GameMap last = Maps.get().getLast();
        Map<Integer, String> names = last.getCheckNames();
        for (int i = 0; i < getCount(); i++)
        {
            NameCount value = getItem(i).getValue();
            value.setName(names.get(i));
            value.setCount(0);
            if (names.containsKey(i))
            {
                hasNames = true;
            }
        }
        for (Check check : checks)
        {
            NameCount value = getItem(check.type).getValue();
            value.setCount(value.getCount() + 1);
        }
        popup.setWidth(hasNames);
    }
}
