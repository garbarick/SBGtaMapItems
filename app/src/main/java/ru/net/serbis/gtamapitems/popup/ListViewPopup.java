package ru.net.serbis.gtamapitems.popup;

import android.content.*;
import android.graphics.drawable.*;
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

    public ListViewPopup(Context context, int layoutId)
    {
        view = LayoutInflater.from(context).inflate(layoutId, null);
        setContentView(view);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);

        this.context = context;
        setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setOutsideTouchable(true);
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
