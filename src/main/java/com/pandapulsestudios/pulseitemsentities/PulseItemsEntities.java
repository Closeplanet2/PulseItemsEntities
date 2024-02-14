package com.pandapulsestudios.pulseitemsentities;

import com.pandapulsestudios.pulsecommands.PulseCommands;
import com.pandapulsestudios.pulsecore.Chat.ChatAPI;
import com.pandapulsestudios.pulsecore.Chat.MessageType;
import com.pandapulsestudios.pulsecore.Java.ClassAPI;
import com.pandapulsestudios.pulsecore.Java.JavaAPI;
import com.pandapulsestudios.pulseitemsentities.API.GridXZAPI;
import com.pandapulsestudios.pulseitemsentities.Abstract.PulseCitizensEntity;
import com.pandapulsestudios.pulseitemsentities.Abstract.PulseEntity;
import com.pandapulsestudios.pulseitemsentities.Abstract.PulseLivingEntity;
import com.pandapulsestudios.pulseitemsentities.Configs.GridXZ;
import com.pandapulsestudios.pulseitemsentities.Interface.CustomEntity;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class PulseItemsEntities extends JavaPlugin {

    public static PulseItemsEntities Instance;
    public static HashMap<String, GridXZ> GridXZHashMap = new HashMap<>();

    public static HashMap<String, PulseCitizensEntity> PulseCitizensEntities = new HashMap<>();
    public static HashMap<String, PulseLivingEntity> PulseLivingEntities = new HashMap<>();
    public static HashMap<String, PulseEntity> PulseEntities = new HashMap<>();

    @Override
    public void onEnable() {
        Instance = this;
        GridXZAPI.ReloadGrids();
        RegisterRaw(this);
        PulseCommands.RegisterRaw(this);
        ClassAPI.RegisterClasses(this);
    }

    @Override
    public void onDisable() {
        for(var x : GridXZHashMap.keySet()) GridXZHashMap.get(x).Disable();
    }

    public static void RegisterRaw(JavaPlugin javaPlugin){
        try {
            Register(javaPlugin);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void Register(JavaPlugin javaPlugin) throws Exception {
        var interfaceClasses = JavaAPI.ReturnALlClassOfTypes(javaPlugin, CustomEntity.class);
        for(var customClass : interfaceClasses.get(CustomEntity.class)){
            if(PulseCitizensEntity.class.isAssignableFrom(customClass)){
                var x = ((Class<? extends PulseCitizensEntity>) customClass).getConstructor().newInstance();
                PulseCitizensEntities.put(x.customName(), x);
                ChatAPI.SendChat(MessageLibrary.RegisteredEntity.REGISTER(x.customName()), MessageType.ConsoleMessageNormal, false, null);
            } else if (PulseLivingEntity.class.isAssignableFrom(customClass)) {
                var x = ((Class<? extends PulseLivingEntity>) customClass).getConstructor().newInstance();
                PulseLivingEntities.put(x.customName(), x);
                ChatAPI.SendChat(MessageLibrary.RegisteredEntity.REGISTER(x.customName()), MessageType.ConsoleMessageNormal, false, null);
            }else if (PulseEntity.class.isAssignableFrom(customClass)) {
                var x = ((Class<? extends PulseEntity>) customClass).getConstructor().newInstance();
                PulseEntities.put(x.customName(), x);
                ChatAPI.SendChat(MessageLibrary.RegisteredEntity.REGISTER(x.customName()), MessageType.ConsoleMessageNormal, false, null);
            }
        }

    }



}
