package ru.net.serbis.gtamapitems.param;

import android.view.*;
import android.widget.*;
import ru.net.serbis.gtamapitems.*;
import ru.net.serbis.gtamapitems.util.*;
import ru.net.serbis.utils.*;
import ru.net.serbis.utils.param.*;

import ru.net.serbis.gtamapitems.R;

public class ImageNameParam extends EditTextParam
{
    public ImageNameParam(int id, String value)
    {
        super(id, value);
        stored = false;
    }

    @Override
    public int getLayoutId()
    {
        return R.layout.image_edit;
    }

    @Override
    protected void initNameView(View parent)
    {
        ImageView view = UITool.get().findView(parent, R.id.image);
        view.setImageResource(CheckBoxes.get().getDrawableId(nameId));
    }
}
