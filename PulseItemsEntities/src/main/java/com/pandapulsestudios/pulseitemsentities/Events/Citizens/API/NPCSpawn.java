package com.pandapulsestudios.pulseitemsentities.Events.Citizens.API;

import com.pandapulsestudios.pulsecore.Events.CustomEvent;
import com.pandapulsestudios.pulseitemsentities.PulseItemsEntities;
import net.citizensnpcs.api.event.NPCSpawnEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@CustomEvent
public class NPCSpawn implements Listener {
    @EventHandler
    public void onNPCSpawn(NPCSpawnEvent event){
        var npc = event.getNPC();
        for(var pulseEntity : PulseItemsEntities.registeredCitizensNPCS.get(npc.getName())){
            if(!event.isCancelled() && pulseEntity.NPCSpawnEvent(event)) event.setCancelled(true);
        }
    }
}
