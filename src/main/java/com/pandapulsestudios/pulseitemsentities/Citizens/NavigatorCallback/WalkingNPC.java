package com.pandapulsestudios.pulseitemsentities.Citizens.NavigatorCallback;

import com.pandapulsestudios.pulsecore.Data.API.UUIDDataAPI;
import com.pandapulsestudios.pulseitemsentities.PulseItemsEntities;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.ai.event.CancelReason;
import net.citizensnpcs.api.ai.event.NavigatorCallback;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.List;

public class WalkingNPC implements NavigatorCallback {

    private final NPC npc;
    private final List<Location> walkingPath;
    private int walkingIndex = 0;

    public WalkingNPC(NPC npc, List<Location> walkingPath){
        this.npc = npc;
        this.walkingPath = walkingPath;
    }

    @Override
    public void onCompletion(CancelReason cancelReason) {
        var nextTarget = walkingIndex + 1;
        if(nextTarget < walkingPath.size()){
            walkingIndex = nextTarget;
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(PulseItemsEntities.Instance, new Runnable(){
                @Override
                public void run(){
                    npc.getNavigator().setTarget(walkingPath.get(nextTarget));
                }
            }, 1L);
        }
    }
}
