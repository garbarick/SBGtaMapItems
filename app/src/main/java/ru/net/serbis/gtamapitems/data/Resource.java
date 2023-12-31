package ru.net.serbis.gtamapitems.data;

public class Resource
{
    protected String key;
    protected int nameId;
    protected int drawableId;

    public Resource(String key, int nameId, int drawableId)
    {
        this.key = key;
        this.nameId = nameId;
        this.drawableId = drawableId;
    }

    public String getKey()
    {
        return key;
    }

    public int getNameId()
    {
        return nameId;
    }

    public int getDrawableId()
    {
        return drawableId;
    }
}
