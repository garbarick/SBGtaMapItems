package ru.net.serbis.gtamapitems.listener;

import android.app.*;
import android.view.*;
import android.widget.*;
import ru.net.serbis.gtamapitems.*;
import ru.net.serbis.gtamapitems.dialog.*;
import ru.net.serbis.gtamapitems.popup.*;
import ru.net.serbis.gtamapitems.util.*;
import ru.net.serbis.gtamapitems.view.*;

public class ButtonsListener implements View.OnClickListener, CheckBoxesPopup.OnChangeCheckTypeListener
{
    private Activity context;
    private ImageMap imageMap;
    private CheckBoxesPopup popup;

    public ButtonsListener(Activity context)
    {
        this.context = context;
        imageMap = Tools.get().findView(context, R.id.map);
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
        initButton(R.id.clean_up);
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
            case R.id.clean_up:
                cleanUp();
                break;
        }
    }

    private void check(ImageButton button)
    {
        if (button.isSelected())
        {
            button.setSelected(false);
            imageMap.setChecking(false);
        }
        else
        {
            popup.updateCounts(imageMap.getChecks());
            popup.show(button);
        }
    }

    @Override
    public void onChangeCheckType(int type)
    {
        ImageButton button = Tools.get().findView(context, R.id.check);
        button.setSelected(true);
        selectButton(R.id.erase, false);
        imageMap.setChecking(true);
        imageMap.setErasing(false);
        imageMap.setType(type);
    }

    private void erase(ImageButton button)
    {
        button.setSelected(!button.isSelected());
        selectButton(R.id.check, false);
        imageMap.setErasing(button.isSelected());
        imageMap.setChecking(false);
    }

    private void original()
    {
        imageMap.reset(true);
    }

    private void zoom()
    {
        imageMap.fitWidth();
    }

    private void zoomIn()
    {
        imageMap.zoomIn();
    }

    private void zoomOut()
    {
        imageMap.zoomOut();
    }

    private void selectButton(int id, boolean selected)
    {
        ImageButton button = Tools.get().findView(context, id);
        button.setSelected(selected);
    }

    private void cleanUp()
    {
        new Confirm(context, R.string.clean_up_all_items)
        {
            @Override
            protected void onOk()
            {
                imageMap.clear();
            }
        };
    }
}
