package ru.net.serbis.gtamapitems.data;

public class Resource
{
    protected String key;
    protected int id;
    protected int drawableId;

    public Resource(String key, int id, int drawableId)
    {
        this.key = key;
        this.id = id;
        this.drawableId = drawableId;
    }

    public String getKey()
    {
        return key;
    }

    public int getId()
    {
        return id;
    }

    public int getDrawableId()
    {
        return drawableId;
    }
}
