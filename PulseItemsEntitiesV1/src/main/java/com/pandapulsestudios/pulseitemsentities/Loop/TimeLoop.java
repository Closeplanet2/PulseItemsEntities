package com.pandapulsestudios.pulseitemsentities.Loop;

import com.pandapulsestudios.pulsecore.Loops.CustomLoop;
import com.pandapulsestudios.pulsecore.Loops.PulseLoop;
import com.pandapulsestudios.pulseitemsentities.PulseItemsEntities;
import org.bukkit.entity.*;

@CustomLoop
public class TimeLoop implements PulseLoop {
    @Override
    public Long StartDelay() {
        return 0L;
    }

    @Override
    public Long LoopInterval() {
        return 1L;
    }

    @Override
    public void LoopFunction() {
        for(var entityName : PulseItemsEntities.registeredPulseEntity.keySet()){
            var pulseEntityWrapper  = PulseItemsEntities.registeredPulseEntity.get(entityName);
            pulseEntityWrapper.LoopTime();
        }
    }
}
