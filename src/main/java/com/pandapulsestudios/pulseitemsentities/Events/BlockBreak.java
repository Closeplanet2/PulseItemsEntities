package com.pandapulsestudios.pulseitemsentities.Events;

import com.pandapulsestudios.pulsecore.Chat.ChatAPI;
import com.pandapulsestudios.pulsecore.Chat.MessageType;
import com.pandapulsestudios.pulsecore.Events.CustomEvent;
import com.pandapulsestudios.pulseitemsentities.API.GridXZAPI;
import com.pandapulsestudios.pulseitemsentities.MessageLibrary;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

@CustomEvent
public class BlockBreak implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        var player = event.getPlayer();
        var gridXZ = GridXZAPI.ReturnGridFromEditor(player);
        if(gridXZ == null) return;
        event.setCancelled(true);
        var message = gridXZ.BlockBreakEvent(event.getBlock()) ?
                MessageLibrary.BlockBreak.SUCCESS(gridXZ.gridName) :
                MessageLibrary.BlockBreak.FAILED(gridXZ.gridName);
        ChatAPI.SendChat(message, MessageType.PlayerMessageFromPlugin, false, null, player);
    }

}
