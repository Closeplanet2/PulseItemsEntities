package com.pandapulsestudios.pulseitemsentities.Event;

import com.pandapulsestudios.pulsecore.Events.CustomCoreEvents;
import com.pandapulsestudios.pulsecore.Events.PulseCoreEvents;
import com.pandapulsestudios.pulseitemsentities.API.GridAPI;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@CustomCoreEvents(op = false, perms = {}, worlds = {}, regions = {})
public class CoreEventListner implements PulseCoreEvents {
    @Override
    public void PlayerQuitEvent(PlayerQuitEvent event) {
        GridAPI.EndGridXZEdit(event.getPlayer());
    }

    @Override
    public boolean BlockBreakEvent(BlockBreakEvent event) {
        var gridXZEdit = GridAPI.ReturnGridXZEdit(event.getPlayer());
        return gridXZEdit != null && BlockBreak.WeBrokeGridXZBlock(gridXZEdit, event.getBlock());
    }
}
