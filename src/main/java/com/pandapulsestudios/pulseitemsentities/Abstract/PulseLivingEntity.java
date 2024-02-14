package com.pandapulsestudios.pulseitemsentities.Abstract;

import com.pandapulsestudios.pulseitemsentities.PulseItemsEntities;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public abstract class PulseLivingEntity extends PulseEntity {

    private boolean ai = true;
    private int arrowCooldown = 0;
    private int arrowsInBody = 0;
    private boolean canPickupItems = true;
    private boolean collidable = true;
    private boolean gliding = false;
    private boolean invisible = false;
    private Entity leashHolder = null;
    private boolean swimming = false;
    private double health = 20;
    private double absorptionAmount = 0;

    public PulseLivingEntity(String customName, EntityType entityType) {
        super(customName, entityType);
    }

    public void setAi(boolean ai){ this.ai = ai; }
    public void setArrowCooldown(int arrowCooldown){ this.arrowCooldown = arrowCooldown; }
    public void setArrowsInBody(int arrowsInBody){ this.arrowsInBody = arrowsInBody; }
    public void setCanPickupItems(boolean canPickupItems){ this.canPickupItems = canPickupItems; }
    public void setCollidable(boolean collidable){ this.collidable = collidable; }
    public void setGliding(boolean gliding){ this.gliding = gliding; }
    public void setInvisible(boolean invisible){ this.invisible = invisible; }
    public void setLeashHolder(Entity leashHolder){ this.leashHolder = leashHolder; }
    public void setSwimming(boolean swimming){ this.swimming = swimming; }
    public void setHealth(double health){ this.health = health; }
    public void setAbsorptionAmount(double absorptionAmount){ this.absorptionAmount = absorptionAmount; }

    public boolean ai(){return ai;}
    public int arrowCooldown(){return arrowCooldown;}
    public int arrowsInBody(){return arrowsInBody;}
    public boolean canPickupItems(){return canPickupItems;}
    public boolean collidable(){return collidable;}
    public boolean gliding(){return gliding;}
    public boolean invisible(){return invisible;}
    public Entity leashHolder(){return leashHolder;}
    public boolean swimming(){return swimming;}
    public double health(){return health;}
    public double absorptionAmount(){return absorptionAmount;}

    @Override
    public Entity SpawnEntity(Location location) {
        var entity = super.SpawnEntity(location);
        if(entity instanceof LivingEntity livingEntity) AssignLivingEntitySettings(livingEntity);
        return entity;
    }

    public void AssignLivingEntitySettings(LivingEntity livingEntity){
        livingEntity.setAI(ai());
        livingEntity.setArrowCooldown(arrowCooldown());
        livingEntity.setArrowsInBody(arrowsInBody());
        livingEntity.setCanPickupItems(canPickupItems());
        livingEntity.setCollidable(collidable());
        livingEntity.setGliding(gliding());
        livingEntity.setInvisible(invisible());
        if(leashHolder() != null) livingEntity.setLeashHolder(leashHolder());
        livingEntity.setSwimming(swimming());
        livingEntity.setHealth(health());
        livingEntity.setAbsorptionAmount(absorptionAmount());
    }
}
