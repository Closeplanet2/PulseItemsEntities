package com.pandapulsestudios.pulseitemsentities.Event;

import com.pandapulsestudios.pulsecore.Events.CustomEvent;
import com.pandapulsestudios.pulseitemsentities.API.EntityAPI;
import com.pandapulsestudios.pulseitemsentities.PulseItemsEntities;
import net.citizensnpcs.api.event.NPCSpawnEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@CustomEvent
public class NPCSpawn implements Listener {
    @EventHandler
    public void onNPCSpawn(NPCSpawnEvent event){
        var npc = event.getNPC();
        var pulseEntityWrapper = PulseItemsEntities.registeredPulseEntity.getOrDefault(npc.getName(), null);
        if(pulseEntityWrapper == null) return;

        if(pulseEntityWrapper.AddActiveEntity(npc.getEntity())){
            EntityAPI.AssignCitizensSettings(pulseEntityWrapper, npc);
            EntityAPI.AssignVanillaEntitySettings(pulseEntityWrapper, npc.getEntity());
            EntityAPI.AssignModelEngineSettings(pulseEntityWrapper, npc.getEntity());
            var state = pulseEntityWrapper.pulseEntity().NPCSpawnEvent(event);
            if(!event.isCancelled()) event.setCancelled(state);
        }
    }
}
