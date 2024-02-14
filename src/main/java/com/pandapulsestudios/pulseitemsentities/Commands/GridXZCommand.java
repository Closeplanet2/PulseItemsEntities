package com.pandapulsestudios.pulseitemsentities.Commands;

import com.pandapulsestudios.pulsecommands.Enums.TabType;
import com.pandapulsestudios.pulsecommands.Interface.*;
import com.pandapulsestudios.pulsecommands.PlayerCommand;
import com.pandapulsestudios.pulsecore.Chat.ChatAPI;
import com.pandapulsestudios.pulsecore.Chat.MessageType;
import com.pandapulsestudios.pulseitemsentities.API.GridXZAPI;
import com.pandapulsestudios.pulseitemsentities.MessageLibrary;
import com.pandapulsestudios.pulseitemsentities.PulseItemsEntities;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@PCCommand
public class GridXZCommand extends PlayerCommand {
    public GridXZCommand() { super("gridxz", false); }

    @PCMethod
    @PCSignature("CreateGrid")
    @PCPerm({"PulseItemsEntities.GridXZCommand.CreateGrid"})
    @PCTab(pos = 0, type = TabType.Pure_Data, data = "{Grid_Name}")
    public void CreateGrid(UUID playerUUID, String[] gridNames){
        var player = Bukkit.getPlayer(playerUUID);
        if(player == null) return;
        var gridName = ReturnGridName(gridNames, "_");
        var message = GridXZAPI.CreateGrid(gridName) ?
                MessageLibrary.GridXZCommand.CreateGrid.SUCCESS(gridName) :
                MessageLibrary.GridXZCommand.CreateGrid.FAILED(gridName);
        ChatAPI.SendChat(message, MessageType.PlayerMessageFromPlugin, false, null, player);
    }

    @PCMethod
    @PCSignature("DeleteGrid")
    @PCPerm({"PulseItemsEntities.GridXZCommand.DeleteGrid"})
    @PCFunctionHideTab("GridExists")
    @PCTab(pos = 0, type = TabType.Information_From_Function, data = "ReturnALlStoredGridNames")
    public void DeleteGrid(UUID playerUUID, String gridName){
        var player = Bukkit.getPlayer(playerUUID);
        if(player == null) return;
        var message = GridXZAPI.DeleteGrid(gridName) ?
                MessageLibrary.GridXZCommand.DeleteGrid.SUCCESS(gridName) :
                MessageLibrary.GridXZCommand.DeleteGrid.FAILED(gridName);
        ChatAPI.SendChat(message, MessageType.PlayerMessageFromPlugin, false, null, player);
    }

    @PCMethod
    @PCSignature("StartEdit")
    @PCPerm({"PulseItemsEntities.GridXZCommand.StartEdit"})
    @PCFunctionHideTab("GridExists")
    @PCTab(pos = 0, type = TabType.Information_From_Function, data = "ReturnALlStoredGridNames")
    public void StartEdit(UUID playerUUID, String gridName){
        var player = Bukkit.getPlayer(playerUUID);
        if(player == null) return;
        var message = GridXZAPI.StartEdit(gridName, player) ?
                MessageLibrary.GridXZCommand.StartEdit.SUCCESS(gridName) :
                MessageLibrary.GridXZCommand.StartEdit.FAILED(gridName);
        ChatAPI.SendChat(message, MessageType.PlayerMessageFromPlugin, false, null, player);
    }

    @PCMethod
    @PCSignature("StopEdit")
    @PCPerm({"PulseItemsEntities.GridXZCommand.StopEdit"})
    @PCFunctionHideTab("GridExists")
    @PCTab(pos = 0, type = TabType.Information_From_Function, data = "ReturnALlStoredGridNames")
    public void StopEdit(UUID playerUUID, String gridName){
        var player = Bukkit.getPlayer(playerUUID);
        if(player == null) return;
        var message = GridXZAPI.StopEdit(gridName, player) ?
                MessageLibrary.GridXZCommand.StopEdit.SUCCESS(gridName) :
                MessageLibrary.GridXZCommand.StopEdit.FAILED(gridName);
        ChatAPI.SendChat(message, MessageType.PlayerMessageFromPlugin, false, null, player);
    }

    @PCMethod
    @PCSignature("ClearGrid")
    @PCPerm({"PulseItemsEntities.GridXZCommand.ClearGrid"})
    @PCFunctionHideTab("GridExists")
    @PCTab(pos = 0, type = TabType.Information_From_Function, data = "ReturnALlStoredGridNames")
    public void ClearGrid(UUID playerUUID, String gridName){
        var player = Bukkit.getPlayer(playerUUID);
        if(player == null) return;
        var message = GridXZAPI.ClearGrid(gridName) ?
                MessageLibrary.GridXZCommand.ClearGrid.SUCCESS(gridName) :
                MessageLibrary.GridXZCommand.ClearGrid.FAILED(gridName);
        ChatAPI.SendChat(message, MessageType.PlayerMessageFromPlugin, false, null, player);
    }

    @PCMethod
    @PCSignature("ReloadGrids")
    @PCPerm({"PulseItemsEntities.GridXZCommand.ReloadGrids"})
    public void ReloadGrids(UUID playerUUID){
        var player = Bukkit.getPlayer(playerUUID);
        if(player == null) return;
        GridXZAPI.ReloadGrids();
        ChatAPI.SendChat(MessageLibrary.GridXZCommand.ReloadGrids.SUCCESS(), MessageType.PlayerMessageFromPlugin, false, null, player);
    }

    @PCMethodData
    public boolean GridExists(UUID playerUUID){
        return !PulseItemsEntities.GridXZHashMap.isEmpty();
    }

    @PCMethodData
    public List<String> ReturnALlStoredGridNames(String currentInput){
        var data = new ArrayList<String>();
        for(var key : PulseItemsEntities.GridXZHashMap.keySet()){
            if(key.contains(currentInput)) data.add(key);
        }
        if(data.isEmpty()) data.add("{" + currentInput + "}");
        return data;
    }

    private String ReturnGridName(String[] gridNames, String bridge){
        var stringBuilder = new StringBuilder();
        for(var x : gridNames) stringBuilder.append(x + bridge);
        var data =  stringBuilder.toString();
        return data.substring(0, data.length() - 1);
    }
}
