package com.pandapulsestudios.pulseitemsentities.Java;

import com.pandapulsestudios.pulseitemsentities.Entity.CustomEntity;
import com.pandapulsestudios.pulseitemsentities.Entity.PulseEntity;
import com.pandapulsestudios.pulseitemsentities.PulseItemsEntities;
import com.pandapulsestudios.pulsevariable.API.JavaAPI;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class JavaClassAPI {
    public static void RegisterRaw(JavaPlugin javaPlugin){
        try { Register(javaPlugin); }
        catch (Exception e) { throw new RuntimeException(e); }
    }

    public static void Register(JavaPlugin javaPlugin) throws Exception {
        var interfaceClasses = JavaAPI.ReturnALlClassOfTypes(javaPlugin, CustomEntity.class);
        for(var enchantmentInterface : interfaceClasses.get(CustomEntity.class)) Register((PulseEntity) enchantmentInterface.getConstructor().newInstance());
    }

    private static void Register(PulseEntity pulseEntity){
        PulseItemsEntities.registeredCitizensNPCS.put(pulseEntity.customName(), new ArrayList<>());
        pulseEntity.RegisteredEntity();
    }
}
