package ru.net.serbis.gtamapitems.listener;

import android.app.*;
import android.view.*;
import android.widget.*;
import ru.net.serbis.gtamapitems.*;
import ru.net.serbis.gtamapitems.util.*;
import ru.net.serbis.gtamapitems.view.*;

public class ButtonsListener implements View.OnClickListener, PopupMenu.OnMenuItemClickListener
{
    private Activity context;
    private ImageViewExt img;
    private PopupMenu menu;

    public ButtonsListener(Activity context)
    {
        this.context = context;
        img = Tools.get().findView(context, R.id.img);
        initButtons();
        initMenu();
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

    private void initMenu()
    {
        ImageButton button = Tools.get().findView(context, R.id.check);
        menu = new PopupMenu(context, button);
        menu.getMenuInflater().inflate(R.menu.check_menu, menu.getMenu());
        menu.setOnMenuItemClickListener(this);
        new Reflection().setIconPopup(menu);
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

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        int type = item.getOrder();
        check(type);
        return true;
    }

    private void check(ImageButton button)
    {
        if (button.isSelected())
        {
            button.setSelected(false);
            img.setChecking(false);
        }
        else
        {
            menu.show();
        }
    }

    private void check(int type)
    {
        ImageButton button = Tools.get().findView(context, R.id.check);
        button.setSelected(true);
        selectButton(R.id.erase, false);
        img.setChecking(true);
        img.setErasing(false);
        img.setType(type);
    }

    private void erase(ImageButton button)
    {
        button.setSelected(!button.isSelected());
        selectButton(R.id.check, false);
        img.setErasing(button.isSelected());
        img.setChecking(false);
    }

    private void original()
    {
        img.getLayoutParams().height = RelativeLayout.LayoutParams.WRAP_CONTENT;
        img.requestLayout();
    }

    private void zoom()
    {
        img.getLayoutParams().height = getParentHeight();
        img.requestLayout();
    }

    private void zoomIn()
    {
        img.getLayoutParams().height = img.getLayoutHeight() + Tools.get().dpToPx(64, context);
        img.requestLayout();
    }

    private void zoomOut()
    {
        img.getLayoutParams().height = Math.max(
            img.getLayoutHeight() - Tools.get().dpToPx(64, context),
            getParentHeight());
        img.requestLayout();
    }

    private int getParentHeight()
    {
        ScrollView parent = (ScrollView) img.getParent().getParent();
        return parent.getHeight();
    }
    
    private void selectButton(int id, boolean selected)
    {
        ImageButton button = Tools.get().findView(context, id);
        button.setSelected(selected);
    }
}
