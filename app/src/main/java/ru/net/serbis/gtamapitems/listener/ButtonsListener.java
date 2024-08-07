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
        initButton(R.id.original_size);
        initButton(R.id.fit);
        initButton(R.id.zoom_in);
        initButton(R.id.zoom_out);
        initButton(R.id.clean_up);
        initButton(R.id.export_checks);
        initButton(R.id.import_checks);
        initButton(R.id.info);
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
            case R.id.original_size:
                original();
                break;
            case R.id.fit:
                fit();
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
            case R.id.export_checks:
                new ExportImport(context).exportChecks();
                break;
            case R.id.import_checks:
                new ExportImport(context).importChecks();
                break;
            case R.id.info:
                new InfoDialog(context);
                break;
        }
    }

    private void check(ImageButton button)
    {
        if (button.isSelected())
        {
            button.setSelected(false);
            imageMap.setCheckState(false, false, 0);
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
        imageMap.setCheckState(true, false, type);
    }

    private void erase(ImageButton button)
    {
        button.setSelected(!button.isSelected());
        selectButton(R.id.check, false);
        imageMap.setCheckState(false, button.isSelected(), 0);
    }

    private void original()
    {
        imageMap.reset(true);
    }

    private void fit()
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
