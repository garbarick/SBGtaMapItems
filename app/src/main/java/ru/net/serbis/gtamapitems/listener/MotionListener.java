package ru.net.serbis.gtamapitems.listener;

import android.view.*;
import ru.net.serbis.gtamapitems.view.*;

public class MotionListener implements View.OnGenericMotionListener
{
    private ImageMap imageMap;

    public MotionListener(ImageMap imageMap)
    {
        this.imageMap = imageMap;
    }

    @Override
    public boolean onGenericMotion(View view, MotionEvent event)
    {
        if ((event.getSource() & InputDevice.SOURCE_CLASS_POINTER) == 0)
        {
            return false;
        }
        if (event.getAction() != MotionEvent.ACTION_SCROLL)
        {
            return false;
        }
        if (event.getAxisValue(MotionEvent.AXIS_VSCROLL) < 0.0f)
        {
            imageMap.zoomOut();
        }
        else
        {
            imageMap.zoomIn();
        }
        return true;
    }
}

