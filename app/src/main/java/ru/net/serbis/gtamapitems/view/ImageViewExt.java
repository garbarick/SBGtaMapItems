package ru.net.serbis.gtamapitems.view;

import android.content.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import ru.net.serbis.gtamapitems.*;
import ru.net.serbis.gtamapitems.data.*;
import ru.net.serbis.gtamapitems.util.*;

public class ImageViewExt extends ImageView implements View.OnTouchListener
{
    public interface OnChangeCheckingListener
    {
        void onChangeChecking(ImageViewExt view)
    }

    private List<Check> checks = new ArrayList<Check>();
    private boolean checking;
    private boolean erasing;
    private GestureDetector detector;
    private List<OnChangeCheckingListener> listeners = new ArrayList<OnChangeCheckingListener>();
    private int type;
    private int checkSize;

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
        detector = new GestureDetector(context, new GestureListener());
        setOnTouchListener(this);
        checkSize = (int) context.getResources().getDimension(R.dimen.check_size);
    }

    public void setChecking(boolean checking)
    {
        this.checking = checking;
    }

    public void setErasing(boolean erasing)
    {
        this.erasing = erasing;
    }
    
    public void setType(int type)
    {
        this.type = type;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.drawColor(Color.TRANSPARENT);
        for (Check check : checks)
        {
            drawCheck(canvas, check);
        }
    }

    public int getLayoutHeight()
    {
        if (getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT)
        {
            return getDrawable().getIntrinsicHeight();
        }
        return getLayoutParams().height;
    }

    private float getScale()
    {
        return getLayoutHeight() * 1f / getDrawable().getIntrinsicHeight();
    }

    private void drawCheck(Canvas canvas, Check check)
    {
        float scale = getScale();
        int h = (int) (checkSize * scale);
        int x = (int) (check.x * scale - h/2) ;
        int y = (int) (check.y * scale - h/2);
        Drawable item = CheckBoxes.get().getDrawable(check.type, getContext());
        item.setBounds(0, 0, checkSize, checkSize);
        canvas.save();
        canvas.translate(x, y);
        canvas.scale(scale, scale);
        item.draw(canvas);
        canvas.restore();
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
            checks.add(new Check(x, y, type));
        }
        else
        {
            Check check = findCheck(x, y);
            if (check == null)
            {
                return;
            }
            checks.remove(check);
        }
        invalidate();
        changeChecking();
    }

    private Check findCheck(int x, int y)
    {
        for (Check check : checks)
        {
            if (Math.max(
                Math.abs(check.x - x),
                Math.abs(check.y - y)) < 50)
            {
                return check;
            }
        }
        return null;
    }

    public void setChecks(List<Check> checks)
    {
        this.checks.clear();
        this.checks.addAll(checks);
    }

    public List<Check> getChecks()
    {
        return checks;
    }

    public void setOnChangeCheckingListener(OnChangeCheckingListener listener)
    {
        listeners.add(listener);
    }

    private void changeChecking()
    {
        for (OnChangeCheckingListener listener : listeners)
        {
            listener.onChangeChecking(this);
        }
    }
}
