package com.pandapulsestudios.pulseitemsentities.Event;

import com.pandapulsestudios.pulsecore.Events.CustomEvent;
import com.pandapulsestudios.pulseitemsentities.API.EntityAPI;
import com.pandapulsestudios.pulseitemsentities.PulseItemsEntities;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

@CustomEvent
public class EntitySpawn implements Listener {
    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event){
        var pulseEntityWrapper = PulseItemsEntities.registeredPulseEntity.getOrDefault(event.getEntity().getName(), null);
        if(pulseEntityWrapper == null) return;

        if(pulseEntityWrapper.AddActiveEntity(event.getEntity())){
            EntityAPI.AssignVanillaEntitySettings(pulseEntityWrapper, event.getEntity());
            EntityAPI.AssignModelEngineSettings(pulseEntityWrapper,event.getEntity());
        }
    }
}
