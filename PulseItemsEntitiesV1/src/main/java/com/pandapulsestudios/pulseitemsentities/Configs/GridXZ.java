package com.pandapulsestudios.pulseitemsentities.Configs;

import com.pandapulsestudios.pulseconfig.APIS.ConfigAPI;
import com.pandapulsestudios.pulseconfig.Interfaces.IgnoreSave;
import com.pandapulsestudios.pulseconfig.Interfaces.PulseConfig;
import com.pandapulsestudios.pulsecore.FileSystem.DirAPI;
import com.pandapulsestudios.pulseitemsentities.Objects.Grid.LocXZ;
import com.pandapulsestudios.pulseitemsentities.PulseItemsEntities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GridXZ implements PulseConfig {

    @Override
    public String documentID() { return documentID; }

    @IgnoreSave
    public String documentID;
    public List<LocXZ> gridLocations = new ArrayList<>();
    @IgnoreSave
    public HashMap<Location, Material> tempBLockData = new HashMap<>();

    public boolean DoesContainLocation(Location location){
        return tempBLockData.containsKey(location);
    }

    public boolean AreWeOnOneLevel(Location location){
        if(tempBLockData.isEmpty()) return true;
        var stored = tempBLockData.keySet().stream().toList().get(0);
        return location.getY() == stored.getY();
    }

    public void AddLocation(Block block, Material tempMaterial){
        gridLocations.add(LocXZ.CreateLoc(block.getLocation()));
        tempBLockData.put(block.getLocation(), block.getType());
        block.setType(tempMaterial);
        Save(PulseItemsEntities.class, false, false, false);
    }

    public void EndEdit(){
        for(var location : tempBLockData.keySet()) location.getBlock().setType(tempBLockData.get(location));
        tempBLockData.clear();
    }

    public LocXZ ReturnClosestLocXZ(Location location){
        var shortestDistance = Double.POSITIVE_INFINITY;
        LocXZ locXZ = null;
        for(var loc : gridLocations){
            var distance = loc.distance(location);
            if(distance < shortestDistance){
                shortestDistance = distance;
                locXZ = loc;
            }
        }
        return locXZ;
    }

    public static HashMap<String, GridXZ> LoadAllGridXZ(Class<?> mainClass){
        var data = new HashMap<String, GridXZ>();
        var configPath = new File(ConfigAPI.ReturnConfigPath(mainClass, GridXZ.class));
        var directoryPaths = DirAPI.ReturnAllDirectoryPaths(configPath, false);
        for(var documentID : directoryPaths){
            var gridXZ = new GridXZ();
            gridXZ.documentID = documentID.getName().replace(".yml", "");
            gridXZ.Load(PulseItemsEntities.Instance, mainClass, false, true);
            data.put(gridXZ.documentID, gridXZ);
        }
        return data;
    }

    public static GridXZ CreateGridXZ(String gridName, boolean overrideExisting){
        var storedGridXZ = PulseItemsEntities.gridXZHashMap.getOrDefault(gridName, null);
        if(storedGridXZ != null && !overrideExisting) return storedGridXZ;
        var gridXZ = new GridXZ();
        gridXZ.documentID = gridName;
        gridXZ.Save(PulseItemsEntities.class, false, false, false);
        PulseItemsEntities.gridXZHashMap.put(gridXZ.documentID, gridXZ);
        return gridXZ;
    }
}
