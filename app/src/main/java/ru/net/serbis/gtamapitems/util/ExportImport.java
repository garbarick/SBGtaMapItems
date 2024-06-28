package ru.net.serbis.gtamapitems.util;

import android.*;
import android.app.*;
import java.io.*;
import ru.net.serbis.gtamapitems.*;
import ru.net.serbis.gtamapitems.activity.*;
import ru.net.serbis.gtamapitems.dialog.*;

import ru.net.serbis.gtamapitems.R;

public class ExportImport
{
    private Activity context;

    public ExportImport(Activity context)
    {
        this.context = context;
    }

    private boolean initPermissions()
    {
        new Permissions().initPermissions(context);
        return new Permissions().isGrantedPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    public void exportChecks()
    {
        if (!initPermissions())
        {
            return;
        }
        File file = new File(IOTool.get().getExternalFile("backups"), IOTool.get().getJsonName());
        IOTool.get().copy(new JsonTools().toJson(Maps.get().getItems().values()), file);
        Toasts.get().toast(file.getAbsolutePath());
    }

    public void importChecks()
    {
        if (!initPermissions())
        {
            return;
        }
        File dir = new File(IOTool.get().getExternalFile("backups"));
        new FileChooser(context, R.string.choose_file, false, true, dir, Constants.JSON_EXT)
        {
            @Override
            public void onChoose(String path)
            {
                if (new JsonTools().parseMapChecks(IOTool.get().readFile(new File(path))))
                {
                    ((Main) context).notifyDataSetChanged();
                    Toasts.get().toast(R.string.done);
                }
            }
        };
    }
}
