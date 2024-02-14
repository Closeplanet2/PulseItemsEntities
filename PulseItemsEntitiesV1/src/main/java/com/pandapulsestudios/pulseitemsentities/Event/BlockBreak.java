package com.pandapulsestudios.pulseitemsentities.Event;

import com.pandapulsestudios.pulseitemsentities.Configs.GridXZ;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class BlockBreak {
    public static boolean WeBrokeGridXZBlock(GridXZ gridXZ, Block block){
        if(gridXZ.DoesContainLocation(block.getLocation())) return false;
        if(!gridXZ.AreWeOnOneLevel(block.getLocation())) return false;
        gridXZ.AddLocation(block, Material.RED_CONCRETE);
        return true;
    }
}
