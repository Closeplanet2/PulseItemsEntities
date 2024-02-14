package com.pandapulsestudios.pulseitemsentities.API;

import com.pandapulsestudios.pulseitemsentities.Enums.EntityType;
import com.pandapulsestudios.pulseitemsentities.PulseItemsEntities;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class EntityAPI {
    public static Entity SpawnEntity(String entityName, EntityType entityType, Location location){
        if(entityType == EntityType.Citizens){
            var x = PulseItemsEntities.PulseCitizensEntities.getOrDefault(entityName, null);
            if(x != null) return x.SpawnEntity(location);
        }else if(entityType == EntityType.LivingEntity){
            var x = PulseItemsEntities.PulseLivingEntities.getOrDefault(entityName, null);
            if(x != null) return x.SpawnEntity(location);
        }else if(entityType == EntityType.Entity){
            var x = PulseItemsEntities.PulseEntities.getOrDefault(entityName, null);
            if(x != null) return x.SpawnEntity(location);
        }
        return SpawnEntity(entityName, location);
    }

    public static Entity SpawnEntity(String entityName, Location location){
        var x = PulseItemsEntities.PulseCitizensEntities.getOrDefault(entityName, null);
        if(x != null)  return x.SpawnEntity(location);
        var y = PulseItemsEntities.PulseLivingEntities.getOrDefault(entityName, null);
        if(y != null)  return y.SpawnEntity(location);
        var z = PulseItemsEntities.PulseEntities.getOrDefault(entityName, null);
        if(z != null)  return z.SpawnEntity(location);
        return null;
    }
}
