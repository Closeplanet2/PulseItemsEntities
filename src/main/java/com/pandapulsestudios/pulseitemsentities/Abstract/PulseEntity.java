package com.pandapulsestudios.pulseitemsentities.Abstract;

import com.pandapulsestudios.pulseitemsentities.PulseItemsEntities;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class PulseEntity {

    private List<Entity> registeredEntities = new ArrayList<>();

    private String customName;
    private EntityType entityType;
    private boolean customNameVisible = true;
    private float fallDistance = 0.0f;
    private int fireTicks = 0;
    private int freezeTicks = 0;
    private boolean glowing = false;
    private boolean gravity = true;
    private boolean invulnerable = false;
    private boolean persistent = true;
    private int portalCooldown = 0;
    private Vector2f rotation = new Vector2f(0, 0);
    private boolean silent = false;
    private boolean visualFire = false;

    public PulseEntity(String customName, EntityType entityType){
        this.customName = customName;
        this.entityType = entityType;
    }
    public void setCustomNameVisible(boolean customNameVisible){ this.customNameVisible = customNameVisible; }
    public void setFallDistance(float fallDistance){ this.fallDistance = fallDistance; }
    public void setFireTicks(int fireTicks){ this.fireTicks = fireTicks; }
    public void setFreezeTicks(int freezeTicks){ this.freezeTicks = freezeTicks; }
    public void setGlowing(boolean glowing){ this.glowing = glowing; }
    public void setGravity(boolean gravity){ this.gravity = gravity; }
    public void setInvulnerable(boolean invulnerable){ this.invulnerable = invulnerable; }
    public void setPersistent(boolean persistent){ this.persistent = persistent; }
    public void setPortalCooldown(int portalCooldown){ this.portalCooldown = portalCooldown; }
    public void setRotation(Vector2f rotation){ this.rotation = rotation; }
    public void setSilent(boolean silent){ this.silent = silent; }
    public void setVisualFire(boolean visualFire){ this.visualFire = visualFire; }

    public String customName(){return customName;}
    public EntityType entityType(){return entityType;}
    public boolean customNameVisible(){return customNameVisible;}
    public float fallDistance(){return fallDistance;}
    public int fireTicks(){return fireTicks;}
    public int freezeTicks(){return freezeTicks;}
    public boolean glowing(){return glowing;}
    public boolean gravity(){return gravity;}
    public boolean invulnerable(){return invulnerable;}
    public boolean persistent(){return persistent;}
    public int portalCooldown(){return portalCooldown;}
    public Vector2f rotation(){return rotation;}
    public boolean silent(){return silent;}
    public boolean visualFire(){return visualFire;}

    public Entity SpawnEntity(Location location){
        var entity = location.getWorld().spawnEntity(location, entityType());
        AddEntity(entity);
        AssignEntitySettings(entity);
        return entity;
    }

    public boolean IsEntity(Entity entity){
        var checkA = entity.getType() == entityType();
        var checkB = entity.getName().equals(customName());
        return checkA && checkB;
    }

    public List<Entity> RegisteredEntities(){
        return registeredEntities;
    }

    public void AddEntity(Entity entity){
        if(IsEntity(entity)) registeredEntities.add(entity);
    }

    public void AssignEntitySettings(Entity entity){
        entity.setCustomName(customName());
        entity.setCustomNameVisible(customNameVisible());
        entity.setFallDistance(fallDistance());
        entity.setFireTicks(fireTicks());
        entity.setFreezeTicks(freezeTicks());
        entity.setGlowing(glowing());
        entity.setGravity(gravity());
        entity.setInvulnerable(invulnerable());
        entity.setPersistent(persistent());
        entity.setPortalCooldown(portalCooldown());
        if(entityType() != EntityType.PLAYER) entity.setRotation(rotation().x, rotation().y);
        entity.setSilent(silent());
        entity.setVisualFire(visualFire());
    }
}
