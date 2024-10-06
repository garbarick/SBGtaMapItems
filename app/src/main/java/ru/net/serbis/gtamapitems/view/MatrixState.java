package ru.net.serbis.gtamapitems.view;

import android.graphics.*;
import android.view.*;
import android.graphics.drawable.*;

public class MatrixState
{
    private Matrix matrix = new Matrix();
    private Matrix stored = new Matrix();
    private PointF start = new PointF();
    private float space;
    private MatrixMode mode;
    private ImageMap imageMap;

    public MatrixState(ImageMap imageMap)
    {
        this.imageMap = imageMap;
    }

    public Matrix getMatrix()
    {
        return matrix;
    }

    public boolean isNone()
    {
        return mode == MatrixMode.NONE;
    }

    public void cancel()
    {
        mode = MatrixMode.NONE;
    }

    public void startMove(MotionEvent event)
    {
        stored.set(matrix);
        start.set(event.getX(), event.getY());
        mode = MatrixMode.MOVE;
    }

    public void endMove(MotionEvent event)
    {
        if (mode != MatrixMode.MOVE)
        {
            return;
        }
        matrix.set(stored);
        translate(event.getX() - start.x, event.getY() - start.y);
    }

    public void apply()
    {
        imageMap.setImageMatrix(matrix);
    }

    public void translate(float x, float y)
    {
        matrix.postTranslate(x, y);
    }

    public void reset()
    {
        cancel();
        space = 0;
        start.set(new PointF());
        matrix.reset();
        apply();
    }

    private float getSpace(MotionEvent event)
    {
        if (event.getPointerCount() < 2)
        {
            return 0;
        }
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    private boolean enoughSpace(float space)
    {
        return space > 10;
    }

    public void startZoom(MotionEvent event)
    {
        space = getSpace(event);
        if (enoughSpace(space))
        {
            stored.set(matrix);
            averagePoint(event);
            mode = MatrixMode.ZOOM;
        }
    }

    private void averagePoint(MotionEvent event)
    {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        start.set(x / 2, y / 2);
    }

    public void endZoom(MotionEvent event)
    {
        if (mode != MatrixMode.ZOOM)
        {
            return;
        }
        float space = getSpace(event);
        if (enoughSpace(space))
        {
            matrix.set(stored);
            float scale = space / this.space;
            setScale(scale, scale, start.x, start.y);
        }
    }

    public float[] getValues()
    {
        float[] values = new float[9];
        matrix.getValues(values);
        return values;
    }

    public void setValues(float[] values)
    {
        matrix.setValues(values);
    }

    public PointF getPosition()
    {
        float[] points = new float[2];
        matrix.mapPoints(points);
        return new PointF(points[0], points[1]);
    }

    public float getScale()
    {
        return getScale(getValues());
    }

    private float getScale(float[] values)
    {
        return values[Matrix.MSCALE_X];
    }

    public void setScale(float sx, float sy, float px, float py)
    {
        matrix.postScale(sx, sy, px, py);
    }

    public void fitWidth()
    {
        Drawable image = imageMap.getDrawable();
        if (image == null)
        {
            return;
        }
        Rect rect = image.getBounds();
        float imageWidth = rect.width();
        float scale = imageMap.parent().getWidth() / imageWidth;
        scale /= getScale();
        setScale(scale, scale, 0, 0);
    }
}
