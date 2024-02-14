package com.pandapulsestudios.pulseitemsentities.Events;

import com.pandapulsestudios.pulsecore.Events.CustomEvent;
import com.pandapulsestudios.pulseitemsentities.PulseItemsEntities;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

@CustomEvent
public class EntitySpawn implements Listener {

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event){
        var entity = event.getEntity();

        if(entity instanceof LivingEntity livingEntity){
            var storedEntity = PulseItemsEntities.PulseLivingEntities.getOrDefault(entity.getName(), null);
            if(storedEntity == null) return;
            if(storedEntity.IsEntity(livingEntity)) storedEntity.AssignLivingEntitySettings(livingEntity);
        }else{
            var storedEntity = PulseItemsEntities.PulseEntities.getOrDefault(entity.getName(), null);
            if(storedEntity == null) return;
            if(storedEntity.IsEntity(entity)) storedEntity.AssignEntitySettings(entity);
        }

    }

}
