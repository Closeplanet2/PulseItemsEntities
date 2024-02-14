package com.pandapulsestudios.pulseitemsentities.Interface;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.UUID;

public interface WalkingEntityCallback {
    void StartedWalking(Entity entity, Location startLocation);
    void IsWalking(Entity entity, double distanceToNextPoint);
    void ReachedTarget(Entity entity, Location targetLocation);
    void FinishedWalking(Entity entity, Location endLocation);
}
