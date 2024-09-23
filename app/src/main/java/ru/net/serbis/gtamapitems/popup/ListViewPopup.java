package ru.net.serbis.gtamapitems.popup;

import android.content.*;
import android.view.*;
import android.widget.*;
import ru.net.serbis.gtamapitems.*;
import ru.net.serbis.utils.*;

import ru.net.serbis.gtamapitems.R;

public abstract class ListViewPopup<T extends ArrayAdapter> extends PopupWindow implements AdapterView.OnItemClickListener
{
    protected Context context;
    protected View view;
    protected ListView list;
    protected T adapter;

    public ListViewPopup(Context context, int layoutId, int dimensionId)
    {
        super(
            LayoutInflater.from(context).inflate(layoutId, null),
            (int) context.getResources().getDimension(dimensionId),
            ViewGroup.LayoutParams.WRAP_CONTENT);

        this.context = context;
        setOutsideTouchable(true);
        view = getContentView();
        list = UITool.get().findView(view, R.id.list);
        adapter = createAdapter();
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);
    }

    protected abstract T createAdapter()

    public void show(View anchor)
    {
        showAsDropDown(anchor);
    }
}
