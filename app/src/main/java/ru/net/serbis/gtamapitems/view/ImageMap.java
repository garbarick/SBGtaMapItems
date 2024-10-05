package ru.net.serbis.gtamapitems.view;

import android.content.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import ru.net.serbis.gtamapitems.data.*;
import ru.net.serbis.gtamapitems.listener.*;
import ru.net.serbis.gtamapitems.util.*;

public class ImageMap extends ImageView implements View.OnTouchListener
{
    public interface OnChangeListener
    {
        void onChangeChecking(ImageMap view)
        void onChangeMatrixValues(ImageMap view);
    }

    private List<Check> checks = new ArrayList<Check>();
    private GestureDetector detector;
    private List<OnChangeListener> listeners = new ArrayList<OnChangeListener>();
    private MatrixState state;
    private CheckState checkState = new CheckState();
    private int layerId;

    public ImageMap(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        detector = new GestureDetector(context, new GestureListener(this));
        setOnTouchListener(this);
        setScaleType(ScaleType.MATRIX);
        state = new MatrixState(this);
        checkState.init(context);
        setOnGenericMotionListener(new MotionListener(this));
    }

    public void setLayer(int layerId)
    {
        this.layerId = layerId;
    }

    public void setCheckState(boolean checking, boolean erasing, int type)
    {
        checkState.setChecking(checking);
        checkState.setErasing(erasing);
        checkState.setType(type);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.drawColor(Color.TRANSPARENT);
        drawLayer(canvas);
        for (Check check : checks)
        {
            drawCheck(canvas, check);
        }
    }

    private void drawLayer(Canvas canvas)
    {
        if (layerId == 0)
        {
            return;
        }
        float scale = state.getScale();
        PointF pos = state.getPosition();

        Drawable layer = getContext().getDrawable(layerId);
        layer.setBounds(0, 0, layer.getIntrinsicWidth(), layer.getIntrinsicHeight());

        canvas.save();
        canvas.translate(pos.x, pos.y);
        canvas.scale(scale, scale);
        layer.draw(canvas);
        canvas.restore();
    }

    private void drawCheck(Canvas canvas, Check check)
    {
        float scale = state.getScale();
        PointF pos = state.getPosition();

        Drawable item = CheckBoxes.get().getDrawable(check.type, getContext());
        item.setBounds(0, 0, checkState.getSize(), checkState.getSize());
 
        int h = (int) (checkState.getSize() * scale);
        int x = (int) (check.x * scale - h / 2 + pos.x);
        int y = (int) (check.y * scale - h / 2 + pos.y);

        canvas.save();
        canvas.translate(x, y);
        canvas.scale(scale, scale);
        item.draw(canvas);
        canvas.restore();
    }

    @Override
    public boolean onTouch(View view, MotionEvent event)
    {
        if (checkState.isActive())
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

    public void changeChecking(MotionEvent event)
    {
        float scale = state.getScale();
        PointF pos = state.getPosition();

        int x = (int) ((event.getX() - pos.x) / scale);
        int y = (int) ((event.getY() - pos.y) / scale);

        if (checkState.isChecking())
        {
            checks.add(new Check(x, y, checkState.getType()));
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
        state.setScale(1.05f, 1.05f, 0, 0);
        state.apply();
        changeMatrixValues();
    }

    public void zoomOut()
    {
        state.setScale(0.95f, 0.95f, 0, 0);
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
