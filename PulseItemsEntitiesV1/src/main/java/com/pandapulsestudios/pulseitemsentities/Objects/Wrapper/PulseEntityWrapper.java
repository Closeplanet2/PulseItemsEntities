package com.pandapulsestudios.pulseitemsentities.Objects.Wrapper;

import com.pandapulsestudios.pulseitemsentities.Interface.*;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.HashMap;

public class PulseEntityWrapper {

    private PulseEntity pulseEntity;
    private CustomEntity customEntity;
    private WalkingEntityCallback walkingEntityCallback;
    private HashMap<Entity, EntityWrapper> registeredEntities = new HashMap<>();


    public PulseEntityWrapper(Class<?> pulseEntityClass) {
        try { this.pulseEntity = (PulseEntity) pulseEntityClass.getConstructor().newInstance();}
        catch (Exception e) { this.pulseEntity = null; }

        try { this.walkingEntityCallback = (WalkingEntityCallback) pulseEntityClass.getConstructor().newInstance();}
        catch (Exception e) { this.walkingEntityCallback = null; }

        this.customEntity = pulseEntityClass.isAnnotationPresent(CustomEntity.class) ? pulseEntityClass.getAnnotation(CustomEntity.class) : null;
    }

    public PulseEntity pulseEntity(){return pulseEntity;}
    public CustomEntity customEntity(){return customEntity;}

    public boolean IsEntityPulseEntity(Entity entity){
        if(customEntity.entityType() != entity.getType()) return false;
        return pulseEntity.customName().equals(entity.getName());
    }

    public boolean AddActiveEntity(Entity entity){
        if(!IsEntityPulseEntity(entity)) return false;
        registeredEntities.put(entity, new EntityWrapper(this, entity));
        return true;
    }

    public boolean IsActiveEntity(Entity entity){
        return registeredEntities.containsKey(entity);
    }

    public void RemoveActiveEntity(Entity entity){
        registeredEntities.remove(entity);
    }

    public EntityWrapper ReturnEntityWrapper(Entity entity){
        return registeredEntities.getOrDefault(entity, null);
    }

    public void LoopTime(){
        for(var entity : registeredEntities.keySet()) {
            registeredEntities.get(entity).LoopCanWork();
            registeredEntities.get(entity).LoopJobTime(this);
        }
    }

    public void StartedWalking(Entity entity, Location startLocation){
        registeredEntities.get(entity).SetIsWalking(true);
        walkingEntityCallback.StartedWalking(entity, startLocation);
    }

    public void IsWalking(Entity entity, double distanceToNextPoint){
        registeredEntities.get(entity).SetIsWalking(true);
        walkingEntityCallback.IsWalking(entity, distanceToNextPoint);
    }

    public void ReachedTarget(Entity entity, Location targetLocation){
        walkingEntityCallback.ReachedTarget(entity, targetLocation);
    }

    public void FinishedWalking(Entity entity, Location endLocation){
        registeredEntities.get(entity).SetIsWalking(false);
        walkingEntityCallback.StartedWalking(entity, endLocation);
    }

}
