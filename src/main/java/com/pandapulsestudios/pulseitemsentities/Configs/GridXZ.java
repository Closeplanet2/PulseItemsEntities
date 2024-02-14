package com.pandapulsestudios.pulseitemsentities.Configs;

import com.pandapulsestudios.pulseconfig.APIS.ConfigAPI;
import com.pandapulsestudios.pulseconfig.Interfaces.IgnoreSave;
import com.pandapulsestudios.pulseconfig.Interfaces.PulseConfig;
import com.pandapulsestudios.pulsecore.Chat.ChatAPI;
import com.pandapulsestudios.pulsecore.Chat.MessageType;
import com.pandapulsestudios.pulsecore.FileSystem.DirAPI;
import com.pandapulsestudios.pulseitemsentities.CustomVariable.LocXZ;
import com.pandapulsestudios.pulseitemsentities.PulseItemsEntities;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class GridXZ implements PulseConfig {

    @IgnoreSave
    private static final Material EDIT_MATERIAL = Material.GLOWSTONE;

    public static HashMap<String, GridXZ> LoadAll(){
        var data = new HashMap<String, GridXZ>();
        var configPath = new File(ConfigAPI.ReturnConfigPath(PulseItemsEntities.class, GridXZ.class));
        for(var documentID : DirAPI.ReturnAllDirectoryPaths(configPath, false)){
            var gridXZ = new GridXZ(documentID.getName().replace(".yml", ""));
            gridXZ.Load(PulseItemsEntities.Instance, PulseItemsEntities.class, false, false);
            data.put(gridXZ.gridName, gridXZ);
            ChatAPI.SendChat(String.format("Loaded Grid: %s", gridXZ.gridName), MessageType.ConsoleMessageNormal, false, null);
        }
        return data;
    }

    @Override
    public String documentID() { return gridName; }

    @IgnoreSave
    public String gridName;
    public List<LocXZ> gridLocations = new ArrayList<>();
    @IgnoreSave
    public UUID lockedForEdit;
    @IgnoreSave
    public HashMap<Location, Material> storedEditBlocks = new HashMap<>();

    public GridXZ(String gridName){
        this.gridName = gridName;
    }

    public boolean StartEdit(Player player){
        if(lockedForEdit != null) return false;
        lockedForEdit = player.getUniqueId();
        storedEditBlocks.clear();

        var playerLocation =  player.getLocation();
        for(var locXZ : gridLocations){
            var location = new Location(playerLocation.getWorld(), locXZ.x, playerLocation.getY() - 1, locXZ.z);
            BlockBreakEvent(location.getBlock());
        }
        return true;
    }

    public boolean StopEdit(Player player){
        if(lockedForEdit == null) return false;
        if(lockedForEdit != player.getUniqueId()) return false;
        lockedForEdit = null;
        Disable();
        return true;
    }

    public boolean ClearGrid(){
        Disable();
        gridLocations.clear();
        Save(PulseItemsEntities.class, false, false, false);
        return true;
    }

    public void Disable(){
        for(var location : storedEditBlocks.keySet()) location.getBlock().setType(storedEditBlocks.get(location));
        storedEditBlocks.clear();
    }

    public boolean BlockBreakEvent(Block block){
        var locXZ = LocXZ.CreateLoc(block.getLocation());
        if(block.getType() == EDIT_MATERIAL){
            gridLocations.remove(IndexOf(locXZ));
            block.setType(storedEditBlocks.get(block.getLocation()));
            Save(PulseItemsEntities.class, false, false, false);
            return false;
        }else{
            if(IndexOf(locXZ) < 0) gridLocations.add(locXZ);
            storedEditBlocks.put(block.getLocation(), block.getType());
            block.setType(EDIT_MATERIAL);
            Save(PulseItemsEntities.class, false, false, false);
            return true;
        }
    }

    private int IndexOf(LocXZ locXZ){
        for(var stored : gridLocations){
            if(stored.IsSame(locXZ)) return gridLocations.indexOf(stored);
        }
        return -1;
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
}
