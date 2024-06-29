package ru.net.serbis.gtamapitems.data;

import android.content.*;
import ru.net.serbis.gtamapitems.*;

public class CheckState
{
    private boolean checking;
    private boolean erasing;
    private int type;
    private int size;

    public void setChecking(boolean checking)
    {
        this.checking = checking;
    }

    public boolean isChecking()
    {
        return checking;
    }

    public void setErasing(boolean erasing)
    {
        this.erasing = erasing;
    }

    public boolean isErasing()
    {
        return erasing;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public int getType()
    {
        return type;
    }

    public void init(Context context)
    {
        size = (int) context.getResources().getDimension(R.dimen.check_size);
    }

    public int getSize()
    {
        return size;
    }

    public boolean isActive()
    {
        return checking || erasing;
    }
}
