package com.pandapulsestudios.pulseitemsentities;

import com.pandapulsestudios.pulseitemsentities.Entity.PulseEntity;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class PulseItemsEntities extends JavaPlugin {

    public static HashMap<String, List<PulseEntity>> registeredCitizensNPCS = new HashMap<>();

    @Override
    public void onEnable() {
        com.pandapulsestudios.pulsecore.Java.JavaClassAPI.RegisterRaw(this);
        com.pandapulsestudios.pulseitemsentities.Java.JavaClassAPI.RegisterRaw(this);
        for(var player : Bukkit.getOnlinePlayers()) new TestEntity().SpawnEntityVanilla(player.getLocation());
    }
}
