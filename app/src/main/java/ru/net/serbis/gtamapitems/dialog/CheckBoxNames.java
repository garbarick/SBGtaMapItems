package ru.net.serbis.gtamapitems.dialog;

import android.app.*;
import java.util.*;
import ru.net.serbis.gtamapitems.*;
import ru.net.serbis.gtamapitems.data.*;
import ru.net.serbis.gtamapitems.param.*;
import ru.net.serbis.gtamapitems.util.*;
import ru.net.serbis.utils.adapter.*;
import ru.net.serbis.utils.dialog.*;
import ru.net.serbis.utils.param.*;

public class CheckBoxNames extends ParamsDialog
{
    public CheckBoxNames(Activity context)
    {
        super(context, R.string.check_names, getParams(), true, false);
    }

    private static Param[] getParams()
    {
        GameMap last = Maps.get().getLast();
        Map<Integer, String> names = last.getCheckNames();
        List<Param> params = new ArrayList<Param>();
        for (int i = 0; i < CheckBoxes.get().size(); i ++)
        {
            String name = names.containsKey(i) ? names.get(i) : "";
            params.add(new ImageNameParam(i, name));
        }
        return params.toArray(new Param[0]);
    }

    @Override
    public void ok(ParamsAdapter adapter)
    {
        GameMap last = Maps.get().getLast();
        last.saveCheckNames(adapter.getValues().get());
    }
}
