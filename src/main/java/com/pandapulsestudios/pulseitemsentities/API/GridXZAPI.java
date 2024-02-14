package com.pandapulsestudios.pulseitemsentities.API;

import com.pandapulsestudios.pulseitemsentities.Configs.GridXZ;
import com.pandapulsestudios.pulseitemsentities.PulseItemsEntities;
import org.bukkit.entity.Player;

public class GridXZAPI {
    public static boolean CreateGrid(String gridName){
        var storedGrid = ReturnGrid(gridName);
        if(storedGrid != null) return false;

        var gridXZ = new GridXZ(gridName);
        gridXZ.Save(PulseItemsEntities.class, false, false, false);
        PulseItemsEntities.GridXZHashMap.put(gridName, gridXZ);
        return true;
    }

    public static boolean DeleteGrid(String gridName){
        var storedGrid = ReturnGrid(gridName);
        if(storedGrid == null) return false;

        storedGrid.DeleteConfig(PulseItemsEntities.class);
        PulseItemsEntities.GridXZHashMap.remove(gridName);
        return true;
    }

    public static boolean StartEdit(String gridName, Player player){
        var storedGrid = ReturnGrid(gridName);
        if(storedGrid == null) return false;
        return storedGrid.StartEdit(player);
    }

    public static boolean StopEdit(String gridName, Player player){
        var storedGrid = ReturnGrid(gridName);
        if(storedGrid == null) return false;
        return storedGrid.StopEdit(player);
    }

    public static boolean ClearGrid(String gridName){
        var storedGrid = ReturnGrid(gridName);
        if(storedGrid == null) return false;
        return storedGrid.ClearGrid();
    }

    public static void ReloadGrids(){
        PulseItemsEntities.GridXZHashMap = GridXZ.LoadAll();
    }

    public static GridXZ ReturnGrid(String gridName){
        return PulseItemsEntities.GridXZHashMap.getOrDefault(gridName, null);
    }

    public static GridXZ ReturnGridFromEditor(Player player){
        for(var gridName : PulseItemsEntities.GridXZHashMap.keySet()){
            var gridXZ = PulseItemsEntities.GridXZHashMap.get(gridName);
            if(gridXZ.lockedForEdit == null) continue;
            if(gridXZ.lockedForEdit == player.getUniqueId()) return gridXZ;
        }
        return null;
    }
}
