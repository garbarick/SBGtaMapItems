package ru.net.serbis.gtamapitems.util;

import android.content.*;
import java.lang.reflect.*;
import java.util.*;
import ru.net.serbis.gtamapitems.*;
import ru.net.serbis.gtamapitems.data.*;

public class Maps
{
    private static final Maps instance = new Maps();

    private List<Resource> items = Arrays.asList(
        new Resource[]{
            new Resource("map_gta_3_jumps", R.string.map_gta_3_jumps, R.drawable.map_gta_3_jumps),
            new Resource("map_gta_3_packages", R.string.map_gta_3_packages, R.drawable.map_gta_3_packages),
            new Resource("map_gta_3_rampages", R.string.map_gta_3_rampages, R.drawable.map_gta_3_rampages),

            new Resource("map_gta_lcs_portland_others", R.string.map_gta_lcs_portland_others, R.drawable.map_gta_lcs_portland_others),
            new Resource("map_gta_lcs_portland_packages", R.string.map_gta_lcs_portland_packages, R.drawable.map_gta_lcs_portland_packages),
            new Resource("map_gta_lcs_shoreside_others", R.string.map_gta_lcs_shoreside_others, R.drawable.map_gta_lcs_shoreside_others),
            new Resource("map_gta_lcs_shoreside_packages", R.string.map_gta_lcs_shoreside_packages, R.drawable.map_gta_lcs_shoreside_packages),
            new Resource("map_gta_lcs_staunton_others", R.string.map_gta_lcs_staunton_others, R.drawable.map_gta_lcs_staunton_others),
            new Resource("map_gta_lcs_staunton_packages", R.string.map_gta_lcs_staunton_packages, R.drawable.map_gta_lcs_staunton_packages),

            new Resource("map_gta_vc_jumps", R.string.map_gta_vc_jumps, R.drawable.map_gta_vc_jumps),
            new Resource("map_gta_vc_packages", R.string.map_gta_vc_packages, R.drawable.map_gta_vc_packages),
            new Resource("map_gta_vc_rampages", R.string.map_gta_vc_rampages, R.drawable.map_gta_vc_rampages),

            new Resource("map_gta_vcs_balloons", R.string.map_gta_vcs_balloons, R.drawable.map_gta_vcs_balloons),
            new Resource("map_gta_vcs_jumps", R.string.map_gta_vcs_jumps, R.drawable.map_gta_vcs_jumps),
            new Resource("map_gta_vcs_rampages", R.string.map_gta_vcs_rampages, R.drawable.map_gta_vcs_rampages),
            
            new Resource("map_gta_ctw", R.string.map_gta_ctw, R.drawable.map_gta_ctw)
        }
    );

    private Maps()
    {
    }

    public static Maps get()
    {
        return instance;
    }

    public List<Resource> getItems()
    {
        return items;
    }
}
