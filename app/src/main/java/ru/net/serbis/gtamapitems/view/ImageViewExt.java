package ru.net.serbis.gtamapitems.view;

import android.content.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import ru.net.serbis.gtamapitems.*;
import ru.net.serbis.gtamapitems.listener.*;

public class ImageViewExt extends ImageView implements View.OnTouchListener
{
    private Drawable check;
    private List<Point> points = new ArrayList<Point>();
    private boolean checking;
    private boolean erasing;
    private GestureDetector detector;
    private List<OnChangeCheckingListener> listeners = new ArrayList<OnChangeCheckingListener>();

    private class GestureListener extends GestureDetector.SimpleOnGestureListener
    {
        @Override
        public boolean onDown(MotionEvent event)
        {
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent event)
        {
            changeChecking(event);
            return false;
        }
    }
    
    public ImageViewExt(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        check = context.getResources().getDrawable(R.drawable.check_box);
        detector = new GestureDetector(context, new GestureListener());
        setOnTouchListener(this);
    }

    public void setChecking(boolean checking)
    {
        this.checking = checking;
    }

    public void setErasing(boolean erasing)
    {
        this.erasing = erasing;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.drawColor(Color.TRANSPARENT);
        for (Point point : points)
        {
            drawCheck(canvas, point.x, point.y);
        }
    }

    public int getLayoutHeight()
    {
        if (getLayoutParams().height == RelativeLayout.LayoutParams.WRAP_CONTENT)
        {
            return getDrawable().getIntrinsicHeight();
        }
        return getLayoutParams().height;
    }

    private float getScale()
    {
        return getLayoutHeight() * 1f / getDrawable().getIntrinsicHeight();
    }

    private void drawCheck(Canvas canvas, int x, int y)
    {
        float scale = getScale();
        int h = (int) (80 * scale);
        x = (int) (x * scale - h/2) ;
        y = (int) (y * scale - h/2);
        check.setBounds(x, y, x + h, y + h);
        check.draw(canvas);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event)
    {
        if (!checking && !erasing)
        {
            return false;
        }
        return detector.onTouchEvent(event);
    }

    private void changeChecking(MotionEvent event)
    {
        float scale = getScale();
        int x = (int) (event.getX() / scale);
        int y = (int) (event.getY() / scale);

        if (checking)
        {
            points.add(new Point(x, y));
        }
        else
        {
            Point point = findPoint(x, y);
            if (point == null)
            {
                return;
            }
            points.remove(point);
        }
        invalidate();
        changeChecking();
    }

    private Point findPoint(int x, int y)
    {
        for (Point point : points)
        {
            if (Math.max(
                Math.abs(point.x - x),
                Math.abs(point.y - y)) < 50)
            {
                return point;
            }
        }
        return null;
    }

    public void setPoints(List<Point> points)
    {
        this.points.clear();
        this.points.addAll(points);
    }

    public List<Point> getPoints()
    {
        return points;
    }

    public void setOnChangeCheckingListener(OnChangeCheckingListener listener)
    {
        listeners.add(listener);
    }

    private void changeChecking()
    {
        for (OnChangeCheckingListener listener : listeners)
        {
            listener.onChange(this);
        }
    }
}
