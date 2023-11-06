package ru.net.serbis.gtamapitems.data;

public class Resource
{
    private String name;
    private String nameId;
    private int id;

    public Resource(String name, String nameId, int id)
    {
        this.name = name;
        this.nameId = nameId;
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public String getNameId()
    {
        return nameId;
    }

    public int getId()
    {
        return id;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
