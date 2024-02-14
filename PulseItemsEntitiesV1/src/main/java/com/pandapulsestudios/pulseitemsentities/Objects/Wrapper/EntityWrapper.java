package com.pandapulsestudios.pulseitemsentities.Objects.Wrapper;

import com.pandapulsestudios.pulseitemsentities.Interface.CustomTimeJob;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;

public class EntityWrapper {
    private final Entity entity;
    private final PulseEntityWrapper pulseEntityWrapper;
    private boolean canWork = true;
    private boolean canWalk = true;
    private boolean isWalking = false;
    private boolean isWorking = false;

    public EntityWrapper(PulseEntityWrapper pulseEntityWrapper, Entity entity){
        this.entity = entity;
        this.pulseEntityWrapper = pulseEntityWrapper;
    }

    public Entity entity(){ return entity; }
    public boolean canWork(){ return canWork; }
    public boolean canWalk(){ return canWalk; }
    public boolean isWalking(){ return isWalking; }
    public boolean isWorking(){ return isWorking; }
    public void SetCanWork(boolean state){ canWork = state; }
    public void SetIsWalking(boolean state){ isWalking = state; }
    public void SetCanWalk(boolean state){ isWorking = state; }

    public void LoopCanWork(){
        var distanceToStopWork = pulseEntityWrapper.pulseEntity().stopWorkingRadius();
        if(distanceToStopWork < 0) return;
        for(var player : Bukkit.getOnlinePlayers()){
            var distanceToPlayer = player.getLocation().distance(entity.getLocation());
            if(distanceToPlayer <= distanceToStopWork){
                SetCanWork(false);
                return;
            }
        }
        SetCanWork(true);
    }

    public void LoopJobTime(PulseEntityWrapper pulseEntityWrapper){
        if(!canWork) return;

        var workFound = false;
        for (var jobID : pulseEntityWrapper.pulseEntity().jobTimetable().keySet()) {
            var timeJob = pulseEntityWrapper.pulseEntity().jobTimetable().get(jobID);
            if (!timeJob.getClass().isAnnotationPresent(CustomTimeJob.class)) return;
            var customTimeJob = timeJob.getClass().getAnnotation(CustomTimeJob.class);

            if (customTimeJob.startTime() == entity.getWorld().getTime()) {
                timeJob.StartWork(pulseEntityWrapper, entity, jobID, entity.getWorld().getTime());
                workFound = true;
            } else if (customTimeJob.endTime() == entity.getWorld().getTime()) {
                timeJob.StopWork(pulseEntityWrapper, entity, jobID, entity.getWorld().getTime());
                workFound = true;
            } else if (entity.getWorld().getTime() > customTimeJob.startTime() && entity.getWorld().getTime() < customTimeJob.endTime()) {
                timeJob.Working(pulseEntityWrapper, entity, jobID, entity.getWorld().getTime());
            }
        }

        if (!workFound) pulseEntityWrapper.pulseEntity().NotWorking(entity, entity.getWorld().getTime());
    }
}
