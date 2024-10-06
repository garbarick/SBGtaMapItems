package ru.net.serbis.gtamapitems.data;

public class GameFolder
{
    private String name;
    private int count;

    public GameFolder(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public String getFullName()
    {
        if (count > 0)
        {
            return name + " (" + count + ")";
        }
        return name;
    }

    public void add()
    {
        count ++;
    }
}
