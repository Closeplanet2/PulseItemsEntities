package com.pandapulsestudios.pulseitemsentities;

import com.pandapulsestudios.pulsecore.Chat.ChatAPI;
import com.pandapulsestudios.pulsecore.Chat.Enums.MessageType;
import com.pandapulsestudios.pulsecore.Events.CustomCoreEvents;
import com.pandapulsestudios.pulsecore.Events.PulseCoreEvents;
import com.pandapulsestudios.pulseitemsentities.Entity.CustomEntity;
import com.pandapulsestudios.pulseitemsentities.Entity.PulseEntity;
import net.citizensnpcs.api.event.NPCSpawnEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.NPC;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

@CustomEntity
@CustomCoreEvents
public class TestEntity implements PulseEntity, PulseCoreEvents {
    @Override
    public EntityType entityType() { return EntityType.VILLAGER; }

    @Override
    public boolean isLivingEntity() { return true; }

    @Override
    public boolean useCitizens() { return false; }
}
