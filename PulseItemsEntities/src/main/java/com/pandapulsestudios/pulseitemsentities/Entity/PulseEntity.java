package com.pandapulsestudios.pulseitemsentities.Entity;

import com.pandapulsestudios.pulsecore.Chat.ChatAPI;
import com.pandapulsestudios.pulsecore.Chat.Enums.MessageType;
import com.pandapulsestudios.pulsecore.Java.JavaClassAPI;
import com.pandapulsestudios.pulseitemsentities.Java.JavaPlugins;
import com.pandapulsestudios.pulseitemsentities.PulseItemsEntities;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.NPCSpawnEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Equipment;
import net.citizensnpcs.api.trait.trait.Inventory;
import net.citizensnpcs.api.trait.trait.Owner;
import net.citizensnpcs.api.trait.trait.PlayerFilter;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public interface PulseEntity {

    //default values
    EntityType entityType();
    boolean isLivingEntity();
    boolean useCitizens();



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

    default List<String> hideFromPermGroup(){return new ArrayList<>();}
    default List<UUID> hideFromPlayers(){return new ArrayList<>();}
    default PlayerFilter.Mode returnFilterMode(){ return PlayerFilter.Mode.DENYLIST; }


    default void RegisteredEntity(){ ChatAPI.SendChat(String.format("&3Registered Custom Entity: %s", customName()), MessageType.ConsoleMessageNormal, true, null);}
    default boolean NPCSpawnEvent(NPCSpawnEvent npcSpawnEvent){ return false; }





    default Entity SpawnEntityVanilla(Location spawnLocation){
        var world = spawnLocation.getWorld();
        if(world == null || useCitizens()) return null;

        var entity = world.spawnEntity(spawnLocation, entityType());
        entity.setCustomName(customName());
        entity.setCustomNameVisible(customNameVisible());
        entity.setFallDistance(fallDistance());
        entity.setFireTicks(fireTicks());
        entity.setFreezeTicks(freezeTicks());
        entity.setGlowing(glowing());
        entity.setGravity(gravity());
        entity.setInvulnerable(invulnerable());
        entity.setPersistent(persistent());
        entity.setPortalCooldown(portalCooldown());
        entity.setRotation(rotation().x, rotation().y);
        entity.setSilent(silent());
        entity.setVisualFire(visualFire());

        if(isLivingEntity()){
            var livingEntity = (LivingEntity) entity;
            livingEntity.setAI(ai());
            livingEntity.setArrowCooldown(arrowCooldown());
            livingEntity.setArrowsInBody(arrowsInBody());
            livingEntity.setCanPickupItems(canPickupItems());
            livingEntity.setCollidable(collidable());
            livingEntity.setGliding(gliding());
            livingEntity.setInvisible(invisible());
            if(leashHolder() != null) livingEntity.setLeashHolder(leashHolder());
            livingEntity.setSwimming(swimming());
            livingEntity.setHealth(health());
            livingEntity.setAbsorptionAmount(absorptionAmount());
        }

        return entity;
    }

    default NPC SpawnEntityCitizens(Location spawnLocation){
        var world = spawnLocation.getWorld();
        if(world == null || !useCitizens() || !JavaClassAPI.IsPluginInstalled(null, JavaPlugins.Citizens)) return null;

        var npc = CitizensAPI.getNPCRegistry().createNPC(entityType(), customName());
        for(var slot : setItemsBySlot().keySet()) npc.getOrAddTrait(Equipment.class).set(slot, setItemsBySlot().get(slot));
        for(var equipmentSlot : setItemsByEquipmentSlot().keySet()) npc.getOrAddTrait(Equipment.class).set(equipmentSlot, setItemsByEquipmentSlot().get(equipmentSlot));
        for(var slot : setItemsInventoryBySlot().keySet()) npc.getOrAddTrait(Inventory.class).setItem(slot, setItemsBySlot().get(slot));
        npc.getOrAddTrait(Inventory.class).setContents(setItemsInInventory());

        if(setOwner() != null) npc.getOrAddTrait(Owner.class).setOwner(setOwner());

        for(var permGroup : hideFromPermGroup()) npc.getOrAddTrait(PlayerFilter.class).addGroup(permGroup);
        for(var uuidKey : hideFromPlayers()) npc.getOrAddTrait(PlayerFilter.class).addPlayer(uuidKey);
        if(returnFilterMode() == PlayerFilter.Mode.ALLOWLIST) npc.getOrAddTrait(PlayerFilter.class).isAllowlist();
        else npc.getOrAddTrait(PlayerFilter.class).isDenylist();

        PulseItemsEntities.registeredCitizensNPCS.get(customName()).add(this);
        npc.spawn(spawnLocation);
        return npc;
    }
}
