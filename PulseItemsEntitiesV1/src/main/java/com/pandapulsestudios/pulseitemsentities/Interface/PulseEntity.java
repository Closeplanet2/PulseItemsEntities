package com.pandapulsestudios.pulseitemsentities.Interface;

import com.pandapulsestudios.pulsecore.Chat.ChatAPI;
import com.pandapulsestudios.pulsecore.Chat.MessageType;
import com.pandapulsestudios.pulseitemsentities.Objects.Citizens.Traits.LookCloseParams;
import com.pandapulsestudios.pulseitemsentities.Objects.Citizens.Traits.SkinParams;
import net.citizensnpcs.api.event.NPCSpawnEvent;
import net.citizensnpcs.api.trait.trait.Equipment;
import net.citizensnpcs.api.trait.trait.PlayerFilter;
import net.citizensnpcs.trait.LookClose;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public interface PulseEntity {

    //Time jobs
    default HashMap<String, TimeJob> jobTimetable(){return new HashMap<>();};
    default void NotWorking(Entity entity, long currentTime){}
    default int stopWorkingRadius(){return -1;}

    //Entity values
    default String customName(){ return getClass().getSimpleName(); }
    default boolean customNameVisible(){ return true; }
    default float fallDistance(){return 0.0f; }
    default int fireTicks(){ return 0;}
    default int freezeTicks(){ return 0;}
    default boolean glowing(){ return false; }
    default boolean gravity(){ return true; }
    default boolean  invulnerable(){ return false; }
    default boolean persistent(){ return true; }
    default int portalCooldown(){ return 0; }
    default Vector2f rotation(){ return new Vector2f(0, 0); }
    default boolean silent(){ return false; }
    default boolean visualFire(){ return false; }


    //Living Entity Values
    default boolean ai(){ return true; }
    default int arrowCooldown(){ return 0; }
    default int arrowsInBody(){ return 0; }
    default boolean canPickupItems(){ return true; }
    default boolean collidable(){ return true; }
    default boolean gliding(){ return false; }
    default boolean invisible(){ return false; }
    default Entity leashHolder(){ return null; }
    default boolean swimming(){return false; }
    default double health(){return 20;}
    default double absorptionAmount(){ return 0; }


    //Citizens Values
    default HashMap<Integer, ItemStack> setItemsBySlot(){ return new HashMap<>(); }
    default HashMap<Equipment.EquipmentSlot, ItemStack> setItemsByEquipmentSlot(){ return new HashMap<>(); }
    default HashMap<Integer, ItemStack> setItemsInventoryBySlot(){ return new HashMap<>(); }
    default ItemStack[] setItemsInInventory(){ return new ItemStack[]{}; }
    default UUID setOwner(){ return null; }

    default LookCloseParams lookCloseParams(){ return null; }
    default SkinParams skinParams(){ return null; }

    default List<String> hideFromPermGroup(){return new ArrayList<>();}
    default List<UUID> hideFromPlayers(){return new ArrayList<>();}
    default PlayerFilter.Mode returnFilterMode(){ return PlayerFilter.Mode.DENYLIST; }

    //Model Engine values
    default HashMap<String, Boolean> activeModelData(){return new HashMap<>();}
    default boolean isBaseEntityVisible(){return false;}


    default void RegisteredEntity(){ ChatAPI.SendChat(String.format("&3Registered Custom Entity: %s", customName()), MessageType.ConsoleMessageNormal, true, null);}
    default boolean NPCSpawnEvent(NPCSpawnEvent npcSpawnEvent){ return false; }
}
