package ru.net.serbis.gtamapitems.popup;

import android.content.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import ru.net.serbis.gtamapitems.*;
import ru.net.serbis.gtamapitems.adapter.*;
import ru.net.serbis.gtamapitems.data.*;

public class CheckBoxesPopup extends ListViewPopup<CheckBoxesAdapter>
{
    public interface OnChangeCheckTypeListener
    {
        void onChangeCheckType(int type)
    }

    protected List<OnChangeCheckTypeListener> listeners = new ArrayList<OnChangeCheckTypeListener>();

    public CheckBoxesPopup(Context context)
    {
        super(context, R.layout.list, R.dimen.check_boxes_popup_width);
    }

    @Override
    protected CheckBoxesAdapter createAdapter()
    {
        return new CheckBoxesAdapter(context);
    }

    public void setOnChangeCheckTypeListener(OnChangeCheckTypeListener listener)
    {
        listeners.add(listener);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int pos, long id)
    {
        dismiss();
        for (OnChangeCheckTypeListener listener : listeners)
        {
            listener.onChangeCheckType(pos);
        }
    }

    public void updateCounts(List<Check> checks)
    {
        adapter.updateCounts(checks);
    }
}
