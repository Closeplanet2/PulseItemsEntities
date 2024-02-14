package com.pandapulsestudios.pulseitemsentities.Citizens.Traits;

import net.citizensnpcs.api.npc.NPC;

public record ArmorStandTrait(NPC setAsHelperEntity, NPC setAsHelperEntityWithName, boolean setGravity,
                             boolean setHasArms, boolean setHasBaseplate, boolean setMarker, boolean setSmall,
                             boolean setVisible) {
    public void Set(NPC npc){
        var npcTrait = npc.getOrAddTrait(net.citizensnpcs.trait.ArmorStandTrait.class);
        npcTrait.setAsHelperEntity(setAsHelperEntity);
        npcTrait.setAsHelperEntityWithName(setAsHelperEntityWithName);
        npcTrait.setGravity(setGravity);
        npcTrait.setHasArms(setHasArms);
        npcTrait.setHasBaseplate(setHasBaseplate);
        npcTrait.setMarker(setMarker);
        npcTrait.setSmall(setSmall);
        npcTrait.setVisible(setVisible);
    }

    public static ArmorStandBuilder ArmorStandBuilder(){ return new ArmorStandBuilder(); }
    public static class ArmorStandBuilder{
        private NPC setAsHelperEntity = null;
        private NPC setAsHelperEntityWithName = null;
        private boolean setGravity = true;
        private boolean setHasArms = false;
        private boolean setHasBaseplate = true;
        private boolean setMarker = false;
        private boolean setSmall = false;
        private boolean setVisible = true;

        public ArmorStandBuilder setAsHelperEntity(NPC setAsHelperEntity){
            this.setAsHelperEntity = setAsHelperEntity;
            return this;
        }

        public ArmorStandBuilder setAsHelperEntityWithName(NPC setAsHelperEntityWithName){
            this.setAsHelperEntityWithName = setAsHelperEntityWithName;
            return this;
        }

        public ArmorStandBuilder setGravity(boolean setGravity){
            this.setGravity = setGravity;
            return this;
        }
        public ArmorStandBuilder setHasArms(boolean setHasArms){
            this.setHasArms = setHasArms;
            return this;
        }

        public ArmorStandBuilder setHasBaseplate(boolean setHasBaseplate){
            this.setHasBaseplate = setHasBaseplate;
            return this;
        }

        public ArmorStandBuilder setMarker(boolean setMarker){
            this.setMarker = setMarker;
            return this;
        }

        public ArmorStandBuilder setSmall(boolean setSmall){
            this.setSmall = setSmall;
            return this;
        }

        public ArmorStandBuilder setVisible(boolean setVisible){
            this.setVisible = setVisible;
            return this;
        }

        public ArmorStandTrait Build(){
            return new ArmorStandTrait(setAsHelperEntity, setAsHelperEntityWithName, setGravity, setHasArms,
                    setHasBaseplate, setMarker, setSmall, setVisible);
        }
    }
}
