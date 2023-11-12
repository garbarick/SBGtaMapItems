package ru.net.serbis.gtamapitems.listener;

import android.app.*;
import android.view.*;
import android.widget.*;
import ru.net.serbis.gtamapitems.*;
import ru.net.serbis.gtamapitems.popup.*;
import ru.net.serbis.gtamapitems.util.*;
import ru.net.serbis.gtamapitems.view.*;

public class ButtonsListener implements View.OnClickListener, CheckBoxesPopup.OnChangeCheckTypeListener
{
    private Activity context;
    private ImageViewExt map;
    private CheckBoxesPopup popup;

    public ButtonsListener(Activity context)
    {
        this.context = context;
        map = Tools.get().findView(context, R.id.map);
        initButtons();
        initPopup();
    }

    private void initButtons()
    {
        initButton(R.id.check);
        initButton(R.id.erase);
        initButton(R.id.original);
        initButton(R.id.zoom);
        initButton(R.id.zoom_in);
        initButton(R.id.zoom_out);
    }

    private void initButton(int id)
    {
        ImageButton button = Tools.get().findView(context, id);
        button.setOnClickListener(this);
    }

    private void initPopup()
    {
        popup = new CheckBoxesPopup(context);
        popup.setOnChangeCheckTypeListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.check:
                check((ImageButton) view);
                break;
            case R.id.erase:
                erase((ImageButton) view);
                break;
            case R.id.original:
                original();
                break;
            case R.id.zoom:
                zoom();
                break;
            case R.id.zoom_in:
                zoomIn();
                break;
            case R.id.zoom_out:
                zoomOut();
                break;
        }
    }

    private void check(ImageButton button)
    {
        if (button.isSelected())
        {
            button.setSelected(false);
            map.setChecking(false);
        }
        else
        {
            popup.updateCounts(map.getChecks());
            popup.show(button);
        }
    }

    @Override
    public void onChangeCheckType(int type)
    {
        ImageButton button = Tools.get().findView(context, R.id.check);
        button.setSelected(true);
        selectButton(R.id.erase, false);
        map.setChecking(true);
        map.setErasing(false);
        map.setType(type);
    }

    private void erase(ImageButton button)
    {
        button.setSelected(!button.isSelected());
        selectButton(R.id.check, false);
        map.setErasing(button.isSelected());
        map.setChecking(false);
    }

    private void original()
    {
        map.getLayoutParams().height = RelativeLayout.LayoutParams.WRAP_CONTENT;
        map.requestLayout();
    }

    private void zoom()
    {
        map.getLayoutParams().height = getParentHeight();
        map.requestLayout();
    }

    private void zoomIn()
    {
        map.getLayoutParams().height = map.getLayoutHeight() + Tools.get().dpToPx(64, context);
        map.requestLayout();
    }

    private void zoomOut()
    {
        map.getLayoutParams().height = Math.max(
            map.getLayoutHeight() - Tools.get().dpToPx(64, context),
            getParentHeight());
        map.requestLayout();
    }

    private int getParentHeight()
    {
        ScrollView parent = (ScrollView) map.getParent().getParent();
        return parent.getHeight();
    }
    
    private void selectButton(int id, boolean selected)
    {
        ImageButton button = Tools.get().findView(context, id);
        button.setSelected(selected);
    }
}
