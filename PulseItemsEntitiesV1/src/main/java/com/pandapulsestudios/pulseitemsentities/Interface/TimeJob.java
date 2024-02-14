package com.pandapulsestudios.pulseitemsentities.Interface;

import com.pandapulsestudios.pulseitemsentities.Objects.Wrapper.PulseEntityWrapper;
import org.bukkit.entity.Entity;

public interface TimeJob {
    void StartWork(PulseEntityWrapper pulseEntityWrapper, Entity entity, String jobID, long currentTime);
    void Working(PulseEntityWrapper pulseEntityWrapper, Entity entity, String jobID, long currentTime);
    void StopWork(PulseEntityWrapper pulseEntityWrapper, Entity entity, String jobID, long currentTime);
}
