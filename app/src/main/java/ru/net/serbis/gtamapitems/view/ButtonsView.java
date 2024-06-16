package ru.net.serbis.gtamapitems.view;

import android.content.*;
import android.content.res.*;
import android.util.*;
import android.widget.*;
import ru.net.serbis.gtamapitems.*;
import ru.net.serbis.gtamapitems.util.*;

public class ButtonsView extends GridLayout
{
    public ButtonsView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        TypedArray values = context.obtainStyledAttributes(attrs, new int[] {
            android.R.attr.layout,
            android.R.attr.columnCount
        });
        int layout = values.getResourceId(0, 0);
        int columnCount = values.getInt(1, 0);
        inflate(context, layout, this);
        GridLayout buttons = Tools.get().findView(this, R.id.buttons);
        buttons.setColumnCount(columnCount);
    }
}
