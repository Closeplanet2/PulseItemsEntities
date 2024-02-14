package com.pandapulsestudios.pulseitemsentities.Events;

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
        var storedEntity = PulseItemsEntities.PulseCitizensEntities.getOrDefault(npc.getName(), null);
        if(storedEntity == null) return;
        if(storedEntity.IsEntity(npc.getEntity())) storedEntity.NPCSpawnEvent(npc);
    }

}
