package ru.net.serbis.gtamapitems.util;

import android.content.*;
import android.graphics.drawable.*;
import java.util.*;
import ru.net.serbis.gtamapitems.*;

public class CheckBoxes
{
    private static final CheckBoxes instance = new CheckBoxes();

    private List<Integer> items = Arrays.asList(
        new Integer[]{
            R.drawable.check_box_0,
            R.drawable.check_box_1,
            R.drawable.check_box_2,
            R.drawable.check_box_3,
            R.drawable.check_box_4
        }
    );

    private CheckBoxes()
    {
    }

    public static CheckBoxes get()
    {
        return instance;
    }

    public int getDrawableId(int type)
    {
        return items.get(type);
    }

    public int size()
    {
        return items.size();
    }

    public Drawable getDrawable(int type, Context context)
    {
        return context.getResources().getDrawable(getDrawableId(type));
    }
}
