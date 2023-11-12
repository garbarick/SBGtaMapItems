package ru.net.serbis.gtamapitems.popup;

import android.content.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import ru.net.serbis.gtamapitems.*;
import ru.net.serbis.gtamapitems.adapter.*;
import ru.net.serbis.gtamapitems.data.*;
import ru.net.serbis.gtamapitems.util.*;

public class CheckBoxesPopup extends PopupWindow implements AdapterView.OnItemClickListener
{
    public interface OnChangeCheckTypeListener
    {
        void onChangeCheckType(int type)
    }

    protected Context context;
    protected List<OnChangeCheckTypeListener> listeners = new ArrayList<OnChangeCheckTypeListener>();
    protected CheckBoxesAdapter adapter;

    public CheckBoxesPopup(Context context)
    {
        super(
            LayoutInflater.from(context).inflate(R.layout.list, null),
            (int) context.getResources().getDimension(R.dimen.check_boxes_popup_width),
            ViewGroup.LayoutParams.WRAP_CONTENT);

        this.context = context;
        setOutsideTouchable(true);

        View view = getContentView();

        ListView list = Tools.get().findView(view, R.id.list);
        adapter = new CheckBoxesAdapter(context);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);
    }

    public void show(View anchor)
    {
        showAsDropDown(anchor);
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
