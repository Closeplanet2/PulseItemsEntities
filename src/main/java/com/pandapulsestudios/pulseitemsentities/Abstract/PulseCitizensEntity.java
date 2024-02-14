package com.pandapulsestudios.pulseitemsentities.Abstract;


import com.pandapulsestudios.pulseitemsentities.Citizens.Traits.AgeTrait;
import com.pandapulsestudios.pulseitemsentities.Citizens.Traits.ArmorStandTrait;
import com.pandapulsestudios.pulseitemsentities.Citizens.Traits.LookCloseTrait;
import com.pandapulsestudios.pulseitemsentities.Citizens.Traits.SkinTrait;
import com.pandapulsestudios.pulseitemsentities.Interface.PulseCitizensTimeJob;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;

import java.util.HashMap;
import java.util.UUID;

public abstract class PulseCitizensEntity extends PulseLivingEntity {

    private HashMap<String, PulseCitizensTimeJob> pulseCitizensTimeJobs = new HashMap<>();
    private HashMap<UUID, Boolean> canDoTimeJobs = new HashMap<>();
    private HashMap<UUID, PulseCitizensTimeJob> lastWorkingJob = new HashMap<>();
    private AgeTrait ageTrait = null;
    private LookCloseTrait lookCloseTrait = null;
    private SkinTrait skinTrait = null;
    private ArmorStandTrait armorStandTrait = null;

    public PulseCitizensEntity(String customName, EntityType entityType) {
        super(customName, entityType);
    }

    @Override
    public Entity SpawnEntity(Location location) {
        var npc = CitizensAPI.getNPCRegistry().createNPC(entityType(), customName());
        npc.spawn(location);
        return null;
    }

    public void NPCSpawnEvent(NPC npc){
        if(ageTrait != null)  ageTrait.Set(npc);
        if(lookCloseTrait != null) lookCloseTrait.Set(npc);
        if(skinTrait != null) skinTrait.Set(npc);
        if(armorStandTrait != null) armorStandTrait.Set(npc);
        AddEntity(npc.getEntity());
        AssignEntitySettings(npc.getEntity());
        if(npc.getEntity() instanceof LivingEntity livingEntity) AssignLivingEntitySettings(livingEntity);
    }

    public void AddTimeJob(PulseCitizensTimeJob timeJob){
        pulseCitizensTimeJobs.put(timeJob.jobID(), timeJob);
    }

    public void DoTimeJob(){
        for(var currentEntity : RegisteredEntities()){
            var currentTime = currentEntity.getWorld().getTime();

            if(!canDoTimeJobs.getOrDefault(currentEntity.getUniqueId(), true)){
                var lastJob = lastWorkingJob.getOrDefault(currentEntity.getUniqueId(), null);
                if(lastJob != null) lastJob.PauseWork(this, currentEntity, lastJob.jobID(), currentTime);
                IsWorking(false, currentEntity);
                continue;
            }

            for(var jobID : pulseCitizensTimeJobs.keySet()){
                var timeJob = pulseCitizensTimeJobs.get(jobID);
                if(timeJob.startTime() == currentTime){
                    timeJob.StartWork(this, currentEntity, jobID, currentTime);
                    IsWorking(true, currentEntity);
                    lastWorkingJob.put(currentEntity.getUniqueId(), timeJob);
                    return;
                }else if(timeJob.endTime() == currentTime){
                    timeJob.StopWork(this, currentEntity, jobID, currentTime);
                    IsWorking(true, currentEntity);
                    lastWorkingJob.put(currentEntity.getUniqueId(), timeJob);
                    return;
                }else if(currentTime > timeJob.startTime() && currentTime < timeJob.endTime()){
                    timeJob.Working(this, currentEntity, jobID, currentTime);
                    IsWorking(true, currentEntity);
                    lastWorkingJob.put(currentEntity.getUniqueId(), timeJob);
                    return;
                }
            }

            lastWorkingJob.put(currentEntity.getUniqueId(), null);
            IsWorking(false, currentEntity);
        }
    }

    public void canDoTimeJobs(Entity entity, boolean canDoTimeJobs){
        this.canDoTimeJobs.put(entity.getUniqueId(), canDoTimeJobs);
        if(canDoTimeJobs){
            var lastJob = lastWorkingJob.getOrDefault(entity.getUniqueId(), null);
            if(lastJob != null) lastJob.PlayWork(this, entity, lastJob.jobID(), entity.getWorld().getTime());
        }
    }
    public void AgeTrait(AgeTrait ageTrait){ this.ageTrait = ageTrait; }
    public void LookCloseTrait(LookCloseTrait lookCloseTrait){ this.lookCloseTrait = lookCloseTrait;}
    public void SkinTrait(SkinTrait skinTrait){ this.skinTrait = skinTrait; }
    public void ArmorStandTrait(ArmorStandTrait armorStandTrait){this.armorStandTrait = armorStandTrait;}

    protected abstract void IsWorking(boolean isWorking, Entity entity);
}