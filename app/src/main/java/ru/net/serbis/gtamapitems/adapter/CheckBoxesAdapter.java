package ru.net.serbis.gtamapitems.adapter;

import android.content.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import ru.net.serbis.gtamapitems.*;
import ru.net.serbis.gtamapitems.data.*;
import ru.net.serbis.gtamapitems.util.*;

public class CheckBoxesAdapter extends ArrayAdapter<Holder<Integer>>
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
            add(new Holder<Integer>(0));
        }
    }

    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        if (view == null)
        {
            view = LayoutInflater.from(getContext()).inflate(R.layout.image_item, parent, false);
        }
        ImageView img = Tools.get().findView(view, R.id.image);
        img.setForeground(CheckBoxes.get().getDrawable(position, getContext()));
        TextView count = Tools.get().findView(view, R.id.count);
        count.setText(getItem(position).getValue().toString());
        return view;
    }

    public void updateCounts(List<Check> checks)
    {
        for (int i = 0; i < getCount(); i++)
        {
            getItem(i).setValue(0);
        }
        for (Check check : checks)
        {
            Holder<Integer> holder = getItem(check.type);
            holder.setValue(holder.getValue() + 1);
        }
    }
}
