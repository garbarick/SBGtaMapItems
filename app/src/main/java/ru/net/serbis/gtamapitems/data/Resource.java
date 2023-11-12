package ru.net.serbis.gtamapitems.data;

public class Resource
{
    private String name;
    private int nameId;
    private int drawableId;

    public Resource(String name, int nameId, int drawableId)
    {
        this.name = name;
        this.nameId = nameId;
        this.drawableId = drawableId;
    }

    public String getName()
    {
        return name;
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
