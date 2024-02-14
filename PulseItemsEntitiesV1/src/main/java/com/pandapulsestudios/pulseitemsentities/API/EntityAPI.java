package com.pandapulsestudios.pulseitemsentities.API;

import com.pandapulsestudios.pulseitemsentities.Interface.PulseEntity;
import com.pandapulsestudios.pulseitemsentities.Objects.Wrapper.PulseEntityWrapper;
import com.pandapulsestudios.pulseitemsentities.PulseItemsEntities;
import com.ticxo.modelengine.api.ModelEngineAPI;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Equipment;
import net.citizensnpcs.api.trait.trait.Inventory;
import net.citizensnpcs.api.trait.trait.Owner;
import net.citizensnpcs.api.trait.trait.PlayerFilter;
import net.citizensnpcs.trait.LookClose;
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

public class EntityAPI {

    public static boolean SpawnEntity(String pulseEntityName, Location spawnLocation){
        var pulseEntityWrapper = PulseItemsEntities.registeredPulseEntity.getOrDefault(pulseEntityName, null);
        if(pulseEntityWrapper == null || spawnLocation.getWorld() == null) return false;
        var world = spawnLocation.getWorld();

        if(pulseEntityWrapper.customEntity().useVanilla()){
            var entity = world.spawnEntity(spawnLocation, pulseEntityWrapper.customEntity().entityType());
            AssignVanillaEntitySettings(pulseEntityWrapper, entity);
            AssignModelEngineSettings(pulseEntityWrapper, entity);
            return true;
        }else if(pulseEntityWrapper.customEntity().useCitizens()){
            var pulseEntity = pulseEntityWrapper.pulseEntity();
            var npc = CitizensAPI.getNPCRegistry().createNPC(pulseEntityWrapper.customEntity().entityType(),pulseEntity.customName());
            npc.spawn(spawnLocation);
            return true;
        }

        return false;
    }

    public static void AssignVanillaEntitySettings(PulseEntityWrapper pulseEntityWrapper, Entity entity){
        if(! pulseEntityWrapper.customEntity().useVanilla()) return;

        entity.setCustomName(pulseEntityWrapper.pulseEntity().customName());
        entity.setCustomNameVisible(pulseEntityWrapper.pulseEntity().customNameVisible());
        entity.setFallDistance(pulseEntityWrapper.pulseEntity().fallDistance());
        entity.setFireTicks(pulseEntityWrapper.pulseEntity().fireTicks());
        entity.setFreezeTicks(pulseEntityWrapper.pulseEntity().freezeTicks());
        entity.setGlowing(pulseEntityWrapper.pulseEntity().glowing());
        entity.setGravity(pulseEntityWrapper.pulseEntity().gravity());
        entity.setInvulnerable(pulseEntityWrapper.pulseEntity().invulnerable());
        entity.setPersistent(pulseEntityWrapper.pulseEntity().persistent());
        entity.setPortalCooldown(pulseEntityWrapper.pulseEntity().portalCooldown());
        if(pulseEntityWrapper.customEntity().entityType() != EntityType.PLAYER) entity.setRotation(pulseEntityWrapper.pulseEntity().rotation().x, pulseEntityWrapper.pulseEntity().rotation().y);
        entity.setSilent(pulseEntityWrapper.pulseEntity().silent());
        entity.setVisualFire(pulseEntityWrapper.pulseEntity().visualFire());

        if(pulseEntityWrapper.customEntity().isLivingEntity()){
            var livingEntity = (LivingEntity) entity;
            livingEntity.setAI(pulseEntityWrapper.pulseEntity().ai());
            livingEntity.setArrowCooldown(pulseEntityWrapper.pulseEntity().arrowCooldown());
            livingEntity.setArrowsInBody(pulseEntityWrapper.pulseEntity().arrowsInBody());
            livingEntity.setCanPickupItems(pulseEntityWrapper.pulseEntity().canPickupItems());
            livingEntity.setCollidable(pulseEntityWrapper.pulseEntity().collidable());
            livingEntity.setGliding(pulseEntityWrapper.pulseEntity().gliding());
            livingEntity.setInvisible(pulseEntityWrapper.pulseEntity().invisible());
            if(pulseEntityWrapper.pulseEntity().leashHolder() != null) livingEntity.setLeashHolder(pulseEntityWrapper.pulseEntity().leashHolder());
            livingEntity.setSwimming(pulseEntityWrapper.pulseEntity().swimming());
            livingEntity.setHealth(pulseEntityWrapper.pulseEntity().health());
            livingEntity.setAbsorptionAmount(pulseEntityWrapper.pulseEntity().absorptionAmount());
        }
    }

    public static void AssignModelEngineSettings(PulseEntityWrapper pulseEntityWrapper, Entity entity){
        if(!pulseEntityWrapper.customEntity().useModelEngine()) return;
        var modelEntity = ModelEngineAPI.createModeledEntity(entity);
        modelEntity.setBaseEntityVisible(pulseEntityWrapper.pulseEntity().isBaseEntityVisible());
        for(var modelName : pulseEntityWrapper.pulseEntity().activeModelData().keySet()){
            var activeModel = ModelEngineAPI.createActiveModel(modelName);
            if(activeModel == null) continue;
            modelEntity.addModel(activeModel, pulseEntityWrapper.pulseEntity().activeModelData().get(modelName));
        }
    }

    public static void AssignCitizensSettings(PulseEntityWrapper pulseEntityWrapper, NPC npc){
        for(var slot : pulseEntityWrapper.pulseEntity().setItemsBySlot().keySet()){
            npc.getOrAddTrait(Equipment.class).set(slot, pulseEntityWrapper.pulseEntity().setItemsBySlot().get(slot));
        }

        for(var equipmentSlot : pulseEntityWrapper.pulseEntity().setItemsByEquipmentSlot().keySet()){
            npc.getOrAddTrait(Equipment.class).set(equipmentSlot, pulseEntityWrapper.pulseEntity().setItemsByEquipmentSlot().get(equipmentSlot));
        }

        for(var slot : pulseEntityWrapper.pulseEntity().setItemsInventoryBySlot().keySet()){
            npc.getOrAddTrait(Inventory.class).setItem(slot, pulseEntityWrapper.pulseEntity().setItemsBySlot().get(slot));
        }

        npc.getOrAddTrait(Inventory.class).setContents(pulseEntityWrapper.pulseEntity().setItemsInInventory());

        var lookCloseParams = pulseEntityWrapper.pulseEntity().lookCloseParams();
        if(lookCloseParams != null){
            var lookCloseTrait = npc.getOrAddTrait(LookClose.class);
            lookCloseTrait.setDisableWhileNavigating(lookCloseParams.setDisableWhileNavigating());
            lookCloseTrait.setHeadOnly(lookCloseParams.setHeadOnly());
            lookCloseTrait.lookClose(lookCloseParams.lookClose());
            lookCloseTrait.setLinkedBody(lookCloseParams.setLinkedBody());
            lookCloseTrait.setPerPlayer(lookCloseParams.setPerPlayer());
            lookCloseTrait.setRandomLook(lookCloseParams.setRandomLook());
            lookCloseTrait.setRandomLookDelay(lookCloseParams.setRandomLookDelay());
            if(lookCloseParams.setRandomLookPitchRange().length == 2) lookCloseTrait.setRandomLookPitchRange(lookCloseParams.setRandomLookPitchRange()[0], lookCloseParams.setRandomLookPitchRange()[1]);
            if(lookCloseParams.setRandomLookYawRange().length == 2) lookCloseTrait.setRandomLookYawRange(lookCloseParams.setRandomLookYawRange()[0], lookCloseParams.setRandomLookPitchRange()[1]);
            lookCloseTrait.setRandomlySwitchTargets(lookCloseParams.setRandomlySwitchTargets());
            lookCloseTrait.setRange(lookCloseParams.setRange());
            lookCloseTrait.setRealisticLooking(lookCloseParams.setRealisticLooking());
        }

        var skinParams = pulseEntityWrapper.pulseEntity().skinParams();
        if(skinParams != null){
            var skinTrait = npc.getOrAddTrait(SkinTrait.class);
            skinTrait.setFetchDefaultSkin(skinParams.setFetchDefaultSkin());
            skinTrait.setShouldUpdateSkins(skinParams.setShouldUpdateSkins());
            if(!skinParams.setSkinName().isEmpty()) skinTrait.setSkinName(skinParams.setSkinName());
            if(skinParams.setSkinPersistent().length == 3) skinTrait.setSkinPersistent(skinParams.setSkinPersistent()[0], skinParams.setSkinPersistent()[1], skinParams.setSkinPersistent()[2]);
            if(skinParams.setSkinPersistent().length == 2) skinTrait.setTexture(skinParams.setTexture()[0], skinParams.setTexture()[1]);
        }

        if(pulseEntityWrapper.pulseEntity().setOwner() != null) npc.getOrAddTrait(Owner.class).setOwner(pulseEntityWrapper.pulseEntity().setOwner());

        for(var permGroup : pulseEntityWrapper.pulseEntity().hideFromPermGroup()) npc.getOrAddTrait(PlayerFilter.class).addGroup(permGroup);
        for(var uuidKey : pulseEntityWrapper.pulseEntity().hideFromPlayers()) npc.getOrAddTrait(PlayerFilter.class).addPlayer(uuidKey);
        if(pulseEntityWrapper.pulseEntity().returnFilterMode() == PlayerFilter.Mode.ALLOWLIST) npc.getOrAddTrait(PlayerFilter.class).isAllowlist();
        else npc.getOrAddTrait(PlayerFilter.class).isDenylist();
    }
}
