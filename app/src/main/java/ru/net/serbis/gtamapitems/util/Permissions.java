package ru.net.serbis.gtamapitems.util;

import android.app.*;
import android.content.*;
import android.content.pm.*;

public class Permissions
{
    public void initPermissions(Activity context) 
    {
        try
        {
            String packageName = context.getPackageName();
            PackageManager packageManager = context.getPackageManager();
            for (String permission : packageManager.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS).requestedPermissions)
            {
                if (isGrantedPermission(context, permission))
                {
                    continue;
                }
                context.requestPermissions(new String[]{permission}, 200);
            }
        }
        catch (Exception e)
        {
            Log.error(this, e);
        }
    }

    public boolean isGrantedPermission(Context context, String permission)
    {
        return context.checkCallingOrSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }
}
