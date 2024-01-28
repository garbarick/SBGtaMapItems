package ru.net.serbis.gtamapitems.dialog;

import android.app.*;
import android.content.*;
import ru.net.serbis.gtamapitems.*;

public class Confirm extends AlertDialog.Builder implements DialogInterface.OnClickListener
{
    public Confirm(Context context, int messageId)
    {
        super(context);
        setIcon(android.R.drawable.ic_dialog_alert);
        setTitle(R.string.confirmation);
        setMessage(messageId);
        setNegativeButton(android.R.string.cancel, this);
        setPositiveButton(android.R.string.ok, this);
        show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which)
    {
        switch (which)
        {
            case Dialog.BUTTON_POSITIVE:
                onOk();
                break;
            case Dialog.BUTTON_NEGATIVE:
                onCancel();
                break;
        }
    }

    protected void onOk()
    {
    }

    protected void onCancel()
    {
    }
}
