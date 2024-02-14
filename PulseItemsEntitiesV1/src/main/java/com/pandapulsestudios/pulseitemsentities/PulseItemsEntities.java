package com.pandapulsestudios.pulseitemsentities;

import com.pandapulsestudios.pulsecommands.PulseCommands;
import com.pandapulsestudios.pulsecore.Java.ClassAPI;
import com.pandapulsestudios.pulsecore.Java.JavaAPI;
import com.pandapulsestudios.pulseitemsentities.API.EntityAPI;
import com.pandapulsestudios.pulseitemsentities.Interface.CustomEntity;
import com.pandapulsestudios.pulseitemsentities.Interface.PulseEntity;
import com.pandapulsestudios.pulseitemsentities.Configs.GridXZ;
import com.pandapulsestudios.pulseitemsentities.Objects.Wrapper.PulseEntityWrapper;
import it.unimi.dsi.fastutil.Hash;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class PulseItemsEntities extends JavaPlugin {
    public static PulseItemsEntities Instance;
    public static HashMap<String, PulseEntityWrapper> registeredPulseEntity = new HashMap<>();
    public static HashMap<String, GridXZ> gridXZHashMap = new HashMap<>();
    public static HashMap<UUID, String> lockedGridXZForEditing = new HashMap<>();

    @Override
    public void onEnable() {
        Instance = this;
        ClassAPI.RegisterClasses(this);
        RegisterRaw(this);
        PulseCommands.RegisterRaw(this);

        gridXZHashMap = GridXZ.LoadAllGridXZ(PulseItemsEntities.class);

        for(var world : Bukkit.getWorlds()){
            for(var entity : world.getEntities()){
                var pulseEntityWrapper = PulseItemsEntities.registeredPulseEntity.getOrDefault(entity.getName(), null);
                if(pulseEntityWrapper == null) continue;

                if(pulseEntityWrapper.AddActiveEntity(entity)){
                    EntityAPI.AssignVanillaEntitySettings(pulseEntityWrapper, entity);
                    EntityAPI.AssignModelEngineSettings(pulseEntityWrapper, entity);
                }
            }
        }
    }

    public static void RegisterRaw(JavaPlugin javaPlugin){
        try { Register(javaPlugin); }
        catch (Exception e) { throw new RuntimeException(e); }
    }

    public static void Register(JavaPlugin javaPlugin) throws Exception {
        var interfaceClasses = JavaAPI.ReturnALlClassOfTypes(javaPlugin, CustomEntity.class);
        for(var customClass : interfaceClasses.get(CustomEntity.class)) Register(customClass);
    }

    private static void Register(Class<?> tClass){
        if(!tClass.isAnnotationPresent(CustomEntity.class)) return;
        var pulseEntityWrapper = new PulseEntityWrapper(tClass);
        registeredPulseEntity.put(pulseEntityWrapper.pulseEntity().customName(), pulseEntityWrapper);
        pulseEntityWrapper.pulseEntity().RegisteredEntity();
    }
}
