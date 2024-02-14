package com.pandapulsestudios.pulseitemsentities.Commands;

import com.pandapulsestudios.pulsecommands.Enums.TabType;
import com.pandapulsestudios.pulsecommands.Interface.*;
import com.pandapulsestudios.pulsecommands.PlayerCommand;
import com.pandapulsestudios.pulsecore.Chat.ChatAPI;
import com.pandapulsestudios.pulsecore.Chat.MessageType;
import com.pandapulsestudios.pulseitemsentities.API.GridAPI;
import com.pandapulsestudios.pulseitemsentities.Configs.GridXZ;
import com.pandapulsestudios.pulseitemsentities.PulseItemsEntities;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@PCCommand
public class GridXZCommand extends PlayerCommand{
    public GridXZCommand() { super("gridxz", false); }

    @PCMethod
    @PCSignature("create")
    @PCOP
    @PCTab(pos = 0, type = TabType.Pure_Data, data = "{Grid Name}")
    public void GridCreate(UUID playerUUID, String gridName){
        var player = Bukkit.getPlayer(playerUUID);
        if(player == null) return;
        var gridXZ = GridXZ.CreateGridXZ(gridName, false);
        ChatAPI.SendChat("#8FFF32You have loaded grid: #32FFF6" + gridXZ.documentID, MessageType.PlayerMessageFromPlugin, false, null, player);
    }

    @PCMethod
    @PCSignature("edit")
    @PCOP
    @PCFunctionHideTab("GridEditHide")
    @PCTab(pos = 0, type = TabType.Information_From_Function, data = "GridNames")
    public void GridEdit(UUID playerUUID, String gridName){
        var player = Bukkit.getPlayer(playerUUID);
        if(player == null) return;
        if(GridAPI.ReturnGridXZEdit(player) != null){
            var endEditState = GridAPI.EndGridXZEdit(player);
            if(endEditState){ChatAPI.SendChat("#8FFF32Ended edit for grid: #32FFF6" + gridName, MessageType.PlayerMessageFromPlugin, false, null, player);
            }else{ ChatAPI.SendChat("#FF5B5BYou are not currently editing grid: #32FFF6" + gridName, MessageType.PlayerMessageFromPlugin, false, null, player); }
        }else{
            var startEditState = GridAPI.StartGridXZEdit(player, gridName, false);
            if(startEditState){ ChatAPI.SendChat("#8FFF32You are now editing grid: #32FFF6" + gridName, MessageType.PlayerMessageFromPlugin, false, null, player);
            }else{ ChatAPI.SendChat("#FF5B5BYou cannot edit this grid: #32FFF6" + gridName, MessageType.PlayerMessageFromPlugin, false, null, player); }
        }
    }

    @PCMethodData
    public List<String> GridNames(){
        var data = new ArrayList<String>();
        for(var key : PulseItemsEntities.gridXZHashMap.keySet()) data.add(key);
        return data;
    }

    @PCMethodData
    public boolean GridEditHide(UUID playerUUID){
        return !PulseItemsEntities.gridXZHashMap.isEmpty();
    }
}
