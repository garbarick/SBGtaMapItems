package ru.net.serbis.gtamapitems.util;

import java.util.*;
import ru.net.serbis.gtamapitems.*;
import ru.net.serbis.gtamapitems.data.*;

public class Maps
{
    private static final Maps instance = new Maps();

    private List<GameMap> items = Arrays.asList(
        new GameMap[]{
            new GameMap("map_gta_3_jumps", R.string.map_gta_3_jumps, R.drawable.map_gta_3_jumps),
            new GameMap("map_gta_3_packages", R.string.map_gta_3_packages, R.drawable.map_gta_3_packages),
            new GameMap("map_gta_3_rampages", R.string.map_gta_3_rampages, R.drawable.map_gta_3_rampages),

            new GameMap("map_gta_lcs_portland_others", R.string.map_gta_lcs_portland_others, R.drawable.map_gta_lcs_portland_others),
            new GameMap("map_gta_lcs_portland_packages", R.string.map_gta_lcs_portland_packages, R.drawable.map_gta_lcs_portland_packages),
            new GameMap("map_gta_lcs_shoreside_others", R.string.map_gta_lcs_shoreside_others, R.drawable.map_gta_lcs_shoreside_others),
            new GameMap("map_gta_lcs_shoreside_packages", R.string.map_gta_lcs_shoreside_packages, R.drawable.map_gta_lcs_shoreside_packages),
            new GameMap("map_gta_lcs_staunton_others", R.string.map_gta_lcs_staunton_others, R.drawable.map_gta_lcs_staunton_others),
            new GameMap("map_gta_lcs_staunton_packages", R.string.map_gta_lcs_staunton_packages, R.drawable.map_gta_lcs_staunton_packages),

            new GameMap("map_gta_vc_jumps", R.string.map_gta_vc_jumps, R.drawable.map_gta_vc_jumps),
            new GameMap("map_gta_vc_packages", R.string.map_gta_vc_packages, R.drawable.map_gta_vc_packages),
            new GameMap("map_gta_vc_rampages", R.string.map_gta_vc_rampages, R.drawable.map_gta_vc_rampages),

            new GameMap("map_gta_vcs_balloons", R.string.map_gta_vcs_balloons, R.drawable.map_gta_vcs_balloons),
            new GameMap("map_gta_vcs_jumps", R.string.map_gta_vcs_jumps, R.drawable.map_gta_vcs_jumps),
            new GameMap("map_gta_vcs_rampages", R.string.map_gta_vcs_rampages, R.drawable.map_gta_vcs_rampages),
            
            new GameMap("map_gta_ctw", R.string.map_gta_ctw, R.drawable.map_gta_ctw),

            new GameMap("map_gta_sindacco_chronicles_jumps", R.string.map_gta_sindacco_chronicles_jumps, R.drawable.map_gta_sindacco_chronicles_jumps),
            new GameMap("map_gta_sindacco_chronicles_missions", R.string.map_gta_sindacco_chronicles_missions, R.drawable.map_gta_sindacco_chronicles_missions),
            new GameMap("map_gta_sindacco_chronicles_packages", R.string.map_gta_sindacco_chronicles_packages, R.drawable.map_gta_sindacco_chronicles_packages),
            new GameMap("map_gta_sindacco_chronicles_properties", R.string.map_gta_sindacco_chronicles_properties, R.drawable.map_gta_sindacco_chronicles_properties),
            new GameMap("map_gta_sindacco_chronicles_rampages", R.string.map_gta_sindacco_chronicles_rampages, R.drawable.map_gta_sindacco_chronicles_rampages),
            new GameMap("map_gta_sindacco_chronicles_snapshots", R.string.map_gta_sindacco_chronicles_snapshots, R.drawable.map_gta_sindacco_chronicles_snapshots),

            new GameMap("map_gta_forelli_redemption_jumps", R.string.map_gta_forelli_redemption_jumps, R.drawable.map_gta_forelli_redemption_jumps),
            new GameMap("map_gta_forelli_redemption_missions", R.string.map_gta_forelli_redemption_missions, R.drawable.map_gta_forelli_redemption_missions),
            new GameMap("map_gta_forelli_redemption_packages", R.string.map_gta_forelli_redemption_packages, R.drawable.map_gta_forelli_redemption_packages),
            new GameMap("map_gta_forelli_redemption_rampages", R.string.map_gta_forelli_redemption_rampages, R.drawable.map_gta_forelli_redemption_rampages),

            new GameMap("map_gta_sa_spray_tags", R.string.map_gta_sa_spray_tags, R.drawable.map_gta_sa_spray_tags),
            new GameMap("map_gta_sa_snapshots", R.string.map_gta_sa_snapshots, R.drawable.map_gta_sa_snapshots),
            new GameMap("map_gta_sa_horseshoes", R.string.map_gta_sa_horseshoes, R.drawable.map_gta_sa_horseshoes),
            new GameMap("map_gta_sa_oysters", R.string.map_gta_sa_oysters, R.drawable.map_gta_sa_oysters),
            new GameMap("map_gta_sa_import_export", R.string.map_gta_sa_import_export, R.drawable.map_gta_sa_import_export)
        }
    );

    private Maps()
    {
    }

    public static Maps get()
    {
        return instance;
    }

    public Map<String, GameMap> getItems()
    {
        Map<String, GameMap> result = new LinkedHashMap<String, GameMap>();
        for (GameMap map : items)
        {
            String checks = Preferences.get().getString(map.getKey(), "[]");
            map.setChecks(new JsonTools().parseChecks(checks));

            String values = Preferences.get().getString(map.getKeyValues(), "[]");
            map.setValues(new JsonTools().parseValues(values));

            result.put(map.getKey(), map);
        }
        return result;
    }
}
