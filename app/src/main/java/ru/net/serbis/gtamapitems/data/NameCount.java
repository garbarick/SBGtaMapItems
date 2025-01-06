package ru.net.serbis.gtamapitems.data;

import android.text.*;

public class NameCount
{
    private String name;
    private int count;

    public NameCount(String name, int count)
    {
        this.name = name;
        this.count = count;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    public int getCount()
    {
        return count;
    }

    @Override
    public String toString()
    {
        return count + (TextUtils.isEmpty(name) ? "" : " " + name);
    }
}
