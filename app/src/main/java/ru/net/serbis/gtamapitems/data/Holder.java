package ru.net.serbis.gtamapitems.data;

public class Holder<T>
{
    protected T value;

    public Holder(T value)
    {
        this.value = value;
    }

    public void setValue(T value)
    {
        this.value = value;
    }

    public T getValue()
    {
        return value;
    }
}
