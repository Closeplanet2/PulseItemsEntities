package com.pandapulsestudios.pulseitemsentities.Citizens.Traits;

import net.citizensnpcs.api.npc.NPC;

public record LookCloseTrait(boolean setDisableWhileNavigating, boolean setHeadOnly, boolean lookClose,
                             boolean setLinkedBody, boolean setPerPlayer, boolean setRandomLook,
                             int setRandomLookDelay, float[] setRandomLookPitchRange, float[] setRandomLookYawRange,
                             boolean setRandomlySwitchTargets, double setRange, boolean setRealisticLooking) {

    public void Set(NPC npc){
        var npcTrait = npc.getOrAddTrait(net.citizensnpcs.trait.LookClose.class);
        npcTrait.setDisableWhileNavigating(setDisableWhileNavigating);
        npcTrait.setHeadOnly(setHeadOnly);
        npcTrait.lookClose(lookClose);
        npcTrait.setLinkedBody(setLinkedBody);
        npcTrait.setPerPlayer(setPerPlayer);
        npcTrait.setRandomLook(setRandomLook);
        npcTrait.setRandomLookDelay(setRandomLookDelay);
        if(setRandomLookPitchRange.length == 2) npcTrait.setRandomLookPitchRange(setRandomLookPitchRange[0], setRandomLookPitchRange[1]);
        if(setRandomLookYawRange.length == 2) npcTrait.setRandomLookYawRange(setRandomLookYawRange[0], setRandomLookYawRange[1]);
        npcTrait.setRandomlySwitchTargets(setRandomlySwitchTargets);
        npcTrait.setRange(setRange);
        npcTrait.setRealisticLooking(setRealisticLooking);
    }

    public static LookCloseBuilder LookCloseBuilder(){ return new LookCloseBuilder(); }
    public static class LookCloseBuilder{
        private boolean setDisableWhileNavigating = true;
        private boolean lookClose = true;
        private boolean setRandomLook = false;
        private boolean setRealisticLooking = false;
        private boolean setHeadOnly = false;
        private boolean setLinkedBody = false;
        private boolean setPerPlayer = false;
        private boolean setRandomlySwitchTargets = false;
        private int setRandomLookDelay = 3;
        private float[] setRandomLookPitchRange= new float[]{0.0F, 0.0F};
        private float[] setRandomLookYawRange = new float[]{0.0F, 360.0F};
        private double setRange = 10;

        public LookCloseBuilder setDisableWhileNavigating(boolean setDisableWhileNavigating){
            this.setDisableWhileNavigating = setDisableWhileNavigating;
            return this;
        }

        public LookCloseBuilder lookClose(boolean lookClose){
            this.lookClose = lookClose;
            return this;
        }

        public LookCloseBuilder setRandomLook(boolean setRandomLook){
            this.setRandomLook = setRandomLook;
            return this;
        }

        public LookCloseBuilder setHeadOnly(boolean setHeadOnly){
            this.setHeadOnly = setHeadOnly;
            return this;
        }

        public LookCloseBuilder setRealisticLooking(boolean setRealisticLooking){
            this.setRealisticLooking = setRealisticLooking;
            return this;
        }

        public LookCloseBuilder setLinkedBody(boolean setLinkedBody){
            this.setLinkedBody = setLinkedBody;
            return this;
        }

        public LookCloseBuilder setPerPlayer(boolean setPerPlayer){
            this.setPerPlayer = setPerPlayer;
            return this;
        }

        public LookCloseBuilder setRandomlySwitchTargets(boolean setRandomlySwitchTargets){
            this.setRandomlySwitchTargets = setRandomlySwitchTargets;
            return this;
        }

        public LookCloseBuilder setRandomLookDelay(int setRandomLookDelay){
            this.setRandomLookDelay = setRandomLookDelay;
            return this;
        }

        public LookCloseBuilder setRandomLookPitchRange(float[] setRandomLookPitchRange){
            this.setRandomLookPitchRange = setRandomLookPitchRange;
            return this;
        }

        public LookCloseBuilder setRandomLookYawRange(float[] setRandomLookYawRange){
            this.setRandomLookYawRange = setRandomLookYawRange;
            return this;
        }

        public LookCloseBuilder setRange(double setRange){
            this.setRange = setRange;
            return this;
        }

        public LookCloseTrait Build(){
            return new LookCloseTrait(setDisableWhileNavigating, setHeadOnly, lookClose, setLinkedBody,
                    setPerPlayer, setRandomLook, setRandomLookDelay, setRandomLookPitchRange,
                    setRandomLookYawRange, setRandomlySwitchTargets, setRange, setRealisticLooking);
        }
    }
}
