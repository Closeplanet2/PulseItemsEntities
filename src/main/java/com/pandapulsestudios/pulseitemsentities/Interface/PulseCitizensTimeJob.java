package com.pandapulsestudios.pulseitemsentities.Interface;

import com.pandapulsestudios.pulseitemsentities.Abstract.PulseCitizensEntity;
import org.bukkit.entity.Entity;

public interface PulseCitizensTimeJob {
    String jobID();
    int startTime();
    int endTime();
    void StartWork(PulseCitizensEntity pulseTimetableEntity, Entity entity, String jobID, long currentTime);
    void Working(PulseCitizensEntity pulseTimetableEntity, Entity entity, String jobID, long currentTime);
    void PauseWork(PulseCitizensEntity pulseTimetableEntity, Entity entity, String jobID, long currentTime);
    void PlayWork(PulseCitizensEntity pulseTimetableEntity, Entity entity, String jobID, long currentTime);
    void StopWork(PulseCitizensEntity pulseTimetableEntity, Entity entity, String jobID, long currentTime);
}
