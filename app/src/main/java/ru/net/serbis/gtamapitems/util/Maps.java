package ru.net.serbis.gtamapitems.util;

import android.content.*;
import java.util.*;
import ru.net.serbis.gtamapitems.*;
import ru.net.serbis.gtamapitems.data.*;

import ru.net.serbis.gtamapitems.data.Map;

public class Maps
{
    private static final Maps instance = new Maps();

    private List<Map> items = Arrays.asList(
        new Map[]{
            new Map("map_gta_3_jumps", R.string.map_gta_3_jumps, R.drawable.map_gta_3_jumps),
            new Map("map_gta_3_packages", R.string.map_gta_3_packages, R.drawable.map_gta_3_packages),
            new Map("map_gta_3_rampages", R.string.map_gta_3_rampages, R.drawable.map_gta_3_rampages),

            new Map("map_gta_lcs_portland_others", R.string.map_gta_lcs_portland_others, R.drawable.map_gta_lcs_portland_others),
            new Map("map_gta_lcs_portland_packages", R.string.map_gta_lcs_portland_packages, R.drawable.map_gta_lcs_portland_packages),
            new Map("map_gta_lcs_shoreside_others", R.string.map_gta_lcs_shoreside_others, R.drawable.map_gta_lcs_shoreside_others),
            new Map("map_gta_lcs_shoreside_packages", R.string.map_gta_lcs_shoreside_packages, R.drawable.map_gta_lcs_shoreside_packages),
            new Map("map_gta_lcs_staunton_others", R.string.map_gta_lcs_staunton_others, R.drawable.map_gta_lcs_staunton_others),
            new Map("map_gta_lcs_staunton_packages", R.string.map_gta_lcs_staunton_packages, R.drawable.map_gta_lcs_staunton_packages),

            new Map("map_gta_vc_jumps", R.string.map_gta_vc_jumps, R.drawable.map_gta_vc_jumps),
            new Map("map_gta_vc_packages", R.string.map_gta_vc_packages, R.drawable.map_gta_vc_packages),
            new Map("map_gta_vc_rampages", R.string.map_gta_vc_rampages, R.drawable.map_gta_vc_rampages),

            new Map("map_gta_vcs_balloons", R.string.map_gta_vcs_balloons, R.drawable.map_gta_vcs_balloons),
            new Map("map_gta_vcs_jumps", R.string.map_gta_vcs_jumps, R.drawable.map_gta_vcs_jumps),
            new Map("map_gta_vcs_rampages", R.string.map_gta_vcs_rampages, R.drawable.map_gta_vcs_rampages),
            
            new Map("map_gta_ctw", R.string.map_gta_ctw, R.drawable.map_gta_ctw),

            new Map("map_gta_sindacco_chronicles_jumps", R.string.map_gta_sindacco_chronicles_jumps, R.drawable.map_gta_sindacco_chronicles_jumps),
            new Map("map_gta_sindacco_chronicles_missions", R.string.map_gta_sindacco_chronicles_missions, R.drawable.map_gta_sindacco_chronicles_missions),
            new Map("map_gta_sindacco_chronicles_packages", R.string.map_gta_sindacco_chronicles_packages, R.drawable.map_gta_sindacco_chronicles_packages),
            new Map("map_gta_sindacco_chronicles_properties", R.string.map_gta_sindacco_chronicles_properties, R.drawable.map_gta_sindacco_chronicles_properties),
            new Map("map_gta_sindacco_chronicles_rampages", R.string.map_gta_sindacco_chronicles_rampages, R.drawable.map_gta_sindacco_chronicles_rampages),
            new Map("map_gta_sindacco_chronicles_snapshots", R.string.map_gta_sindacco_chronicles_snapshots, R.drawable.map_gta_sindacco_chronicles_snapshots),

            new Map("map_gta_forelli_redemption_jumps", R.string.map_gta_forelli_redemption_jumps, R.drawable.map_gta_forelli_redemption_jumps),
            new Map("map_gta_forelli_redemption_missions", R.string.map_gta_forelli_redemption_missions, R.drawable.map_gta_forelli_redemption_missions),
            new Map("map_gta_forelli_redemption_packages", R.string.map_gta_forelli_redemption_packages, R.drawable.map_gta_forelli_redemption_packages),
            new Map("map_gta_forelli_redemption_rampages", R.string.map_gta_forelli_redemption_rampages, R.drawable.map_gta_forelli_redemption_rampages),

            new Map("map_gta_sa_spray_tags", R.string.map_gta_sa_spray_tags, R.drawable.map_gta_sa_spray_tags),
            new Map("map_gta_sa_snapshots", R.string.map_gta_sa_snapshots, R.drawable.map_gta_sa_snapshots),
            new Map("map_gta_sa_horseshoes", R.string.map_gta_sa_horseshoes, R.drawable.map_gta_sa_horseshoes),
            new Map("map_gta_sa_oysters", R.string.map_gta_sa_oysters, R.drawable.map_gta_sa_oysters),
            new Map("map_gta_sa_import_export", R.string.map_gta_sa_import_export, R.drawable.map_gta_sa_import_export)
        }
    );

    private Maps()
    {
    }

    public static Maps get()
    {
        return instance;
    }

    public List<Map> getItems(Context context)
    {
        for (Map map : items)
        {
            String value = Tools.get().getPreferences(context).getString(map.getKey(), "[]");
            map.setChecks(new JsonTools().parseChecks(value));
        }
        return items;
    }
}
