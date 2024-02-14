package com.pandapulsestudios.pulseitemsentities.Loops;

import com.pandapulsestudios.pulsecore.Loops.CustomLoop;
import com.pandapulsestudios.pulsecore.Loops.PulseLoop;
import com.pandapulsestudios.pulseitemsentities.PulseItemsEntities;

@CustomLoop
public class TimeJobLoop implements PulseLoop {
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
        for(var citizensName : PulseItemsEntities.PulseCitizensEntities.keySet()){
            PulseItemsEntities.PulseCitizensEntities.get(citizensName).DoTimeJob();
        }
    }
}
