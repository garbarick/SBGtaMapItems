package ru.net.serbis.gtamapitems.util;

public class Strings extends Util
{
    private static final Strings instance = new Strings();

    public static Strings get()
    {
        return instance;
    }

    public String get(int id, Object ... args)
    {
        String str = context.getResources().getString(id);
        return String.format(str, args);
    }
}
