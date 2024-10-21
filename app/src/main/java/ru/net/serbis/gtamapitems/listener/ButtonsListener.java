package ru.net.serbis.gtamapitems.listener;

import android.app.*;
import android.view.*;
import android.widget.*;
import ru.net.serbis.gtamapitems.*;
import ru.net.serbis.gtamapitems.dialog.*;
import ru.net.serbis.gtamapitems.popup.*;
import ru.net.serbis.gtamapitems.util.*;
import ru.net.serbis.gtamapitems.view.*;
import ru.net.serbis.utils.*;

import ru.net.serbis.gtamapitems.R;

public class ButtonsListener implements View.OnClickListener, CheckBoxesPopup.OnChangeCheckTypeListener
{
    private Activity context;
    private ImageMap imageMap;
    private CheckBoxesPopup checkBoxPopup;
    private MapsPopup mapsPopup;

    public ButtonsListener(Activity context)
    {
        this.context = context;
        imageMap = UITool.get().findView(context, R.id.map);
        LinearLayout main = UITool.get().findView(context, R.id.main);
        UITool.get().initAllButtons(main, this);
        initPopup();
    }

    private void initPopup()
    {
        checkBoxPopup = new CheckBoxesPopup(context);
        checkBoxPopup.setOnChangeCheckTypeListener(this);

        Button maps = UITool.get().findView(context, R.id.maps);
        mapsPopup = new MapsPopup(context, maps);
        mapsPopup.setOnMenuItemClickListener(new MapSelectListener(context, mapsPopup));
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
            case R.id.maps:
                mapsPopup.show();
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
            checkBoxPopup.updateCounts(imageMap.getChecks());
            checkBoxPopup.show(button);
        }
    }

    @Override
    public void onChangeCheckType(int type)
    {
        ImageButton button = UITool.get().findView(context, R.id.check);
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
        ImageButton button = UITool.get().findView(context, id);
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
