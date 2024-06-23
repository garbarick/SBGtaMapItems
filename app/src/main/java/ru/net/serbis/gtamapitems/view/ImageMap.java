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

public class ImageMap extends ImageView implements View.OnTouchListener
{
    public interface OnChangeListener
    {
        void onChangeChecking(ImageMap view)
        void onChangeMatrixValues(ImageMap view);
    }

    private List<Check> checks = new ArrayList<Check>();
    private boolean checking;
    private boolean erasing;
    private GestureDetector detector;
    private List<OnChangeListener> listeners = new ArrayList<OnChangeListener>();
    private int type;
    private int checkSize;
    private MatrixState state;

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

    public ImageMap(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        detector = new GestureDetector(context, new GestureListener());
        setOnTouchListener(this);
        setScaleType(ScaleType.MATRIX);
        state = new MatrixState(this);
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

    private void drawCheck(Canvas canvas, Check check)
    {
        float scale = state.getScale();
        PointF pos = state.getPosition();

        int h = (int) (checkSize * scale);
        int x = (int) (check.x * scale - h / 2 + pos.x);
        int y = (int) (check.y * scale - h / 2 + pos.y);
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
        if (checking || erasing)
        {
            return detector.onTouchEvent(event);
        }
        switch (event.getAction() & MotionEvent.ACTION_MASK)
        {
            case MotionEvent.ACTION_DOWN:
                state.startMove(event);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                state.startZoom(event);
                break;
            case MotionEvent.ACTION_UP:
                state.cancel();
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                state.endMove(event);
                state.endZoom(event);
                break;
        }
        state.apply();
        changeMatrixValues();
        return true;
    }

    private void changeChecking(MotionEvent event)
    {
        float scale = state.getScale();
        PointF pos = state.getPosition();

        int x = (int) ((event.getX() - pos.x) / scale);
        int y = (int) ((event.getY() - pos.y) / scale);

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

    public void setOnChangeCheckingListener(OnChangeListener listener)
    {
        listeners.add(listener);
    }

    private void changeChecking()
    {
        for (OnChangeListener listener : listeners)
        {
            listener.onChangeChecking(this);
        }
    }

    public void clear()
    {
        checks.clear();
        invalidate();
        changeChecking();
    }

    public ViewGroup parent()
    {
        return (ViewGroup) getParent();
    }

    public void reset(boolean save)
    {
        state.reset();
        if (save)
        {
            changeMatrixValues();
        }
    }

    public void fitWidth()
    {
        state.fitWidth();
        PointF pos = state.getPosition();
        state.translate(- pos.x, - pos.y);
        state.apply();
        changeMatrixValues();
    }

    public void zoomIn()
    {
        state.setScale(1.1f, 1.1f, 0, 0);
        state.apply();
        changeMatrixValues();
    }

    public void zoomOut()
    {
        state.setScale(0.9f, 0.9f, 0, 0);
        state.apply();
        changeMatrixValues();
    }

    public float[] getMatrixValues()
    {
        return state.getValues();
    }

    public void setMatrixValues(float[] values)
    {
        if (values == null)
        {
            return;
        }
        state.setValues(values);
        state.apply();
    }

    private void changeMatrixValues()
    {
        for (OnChangeListener listener : listeners)
        {
            listener.onChangeMatrixValues(this);
        }
    }
}
