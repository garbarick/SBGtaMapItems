package ru.net.serbis.gtamapitems.activity;

import android.app.*;
import android.content.*;
import android.content.res.*;
import android.os.*;
import android.view.*;
import ru.net.serbis.gtamapitems.*;
import ru.net.serbis.gtamapitems.listener.*;

public class Main extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        new ButtonsListener(this);
    }

    private int getLayout()
    {
        if (Configuration.ORIENTATION_LANDSCAPE == getResources().getConfiguration().orientation)
        {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            return R.layout.main_landscape;
        }
        return R.layout.main_portrait;
    }

    public void notifyDataSetChanged()
    {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}
