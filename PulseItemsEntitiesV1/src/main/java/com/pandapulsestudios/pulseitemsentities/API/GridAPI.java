package com.pandapulsestudios.pulseitemsentities.API;

import com.pandapulsestudios.pulseitemsentities.Configs.GridXZ;
import com.pandapulsestudios.pulseitemsentities.PulseItemsEntities;
import org.bukkit.entity.Player;

public class GridAPI {
    public static GridXZ CreateGridXZ(String gridName, boolean overrideExisting){
        return GridXZ.CreateGridXZ(gridName, overrideExisting);
    }

    public static GridXZ ReturnGridXZ(String gridName){
        return PulseItemsEntities.gridXZHashMap.getOrDefault(gridName, null);
    }

    public static boolean StartGridXZEdit(Player player, String gridName, boolean multipleEditors){
        if(ReturnGridXZEdit(player) != null) return false;
        var doesGridXZHaveEditor = DoesGridXZHaveEditor(player, gridName, multipleEditors);
        var gridXZ = PulseItemsEntities.gridXZHashMap.getOrDefault(gridName, null);
        if(gridXZ == null || (doesGridXZHaveEditor && !multipleEditors)) return false;
        PulseItemsEntities.lockedGridXZForEditing.put(player.getUniqueId(), gridName);
        return true;
    }

    public static boolean EndGridXZEdit(Player player){
        var gridXZ = ReturnGridXZEdit(player);
        if(gridXZ == null) return false;
        gridXZ.EndEdit();
        PulseItemsEntities.lockedGridXZForEditing.remove(player.getUniqueId());
        return true;
    }

    public static GridXZ ReturnGridXZEdit(Player player){
        var gridXZName = PulseItemsEntities.lockedGridXZForEditing.getOrDefault(player.getUniqueId(), null);
        return gridXZName == null ? null : PulseItemsEntities.gridXZHashMap.getOrDefault(gridXZName, null);
    }

    private static boolean DoesGridXZHaveEditor(Player player, String gridName, boolean multipleEditors){
        for(var storedUUID : PulseItemsEntities.lockedGridXZForEditing.keySet()){
            var storedGridName = PulseItemsEntities.lockedGridXZForEditing.get(storedUUID);
            if(!storedGridName.equals(gridName)) continue;
            return true;
        }
        return false;
    }
}
