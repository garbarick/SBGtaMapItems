package ru.net.serbis.gtamapitems.listener;

import android.view.*;
import ru.net.serbis.gtamapitems.view.*;

public class GestureListener extends GestureDetector.SimpleOnGestureListener
{
    private ImageMap imageMap;

    public GestureListener(ImageMap imageMap)
    {
        this.imageMap = imageMap;
    }

    @Override
    public boolean onDown(MotionEvent event)
    {
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent event)
    {
        imageMap.changeChecking(event);
        return false;
    }
}

