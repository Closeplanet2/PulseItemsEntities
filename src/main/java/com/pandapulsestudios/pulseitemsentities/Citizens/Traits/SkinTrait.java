package com.pandapulsestudios.pulseitemsentities.Citizens.Traits;

import net.citizensnpcs.api.npc.NPC;

public record SkinTrait(boolean setFetchDefaultSkin,
                        boolean setShouldUpdateSkins,
                        String setSkinName,
                        String[] setSkinPersistent,
                        String[] setTexture) {

    public void Set(NPC npc){
        var npcTrait = npc.getOrAddTrait(net.citizensnpcs.trait.SkinTrait.class);
        npcTrait.setFetchDefaultSkin(setFetchDefaultSkin());
        npcTrait.setShouldUpdateSkins(setShouldUpdateSkins());
        if(!setSkinName().isEmpty()) npcTrait.setSkinName(setSkinName());
        if(setSkinPersistent().length == 3) npcTrait.setSkinPersistent(setSkinPersistent()[0], setSkinPersistent()[1], setSkinPersistent()[2]);
        if(setTexture().length == 2) npcTrait.setTexture(setTexture()[0], setTexture()[1]);
    }

    public static SkinBuilder SkinBuilder(){ return new SkinBuilder(); }
    public static class SkinBuilder {
        boolean setFetchDefaultSkin = true;
        boolean setShouldUpdateSkins = false;
        String setSkinName = "";
        String[] setSkinPersistent = {};
        String[] setTexture = {};

        public SkinBuilder setFetchDefaultSkin(boolean setFetchDefaultSkin){
            this.setFetchDefaultSkin = setFetchDefaultSkin;
            return this;
        }

        public SkinBuilder setShouldUpdateSkins(boolean setShouldUpdateSkins){
            this.setShouldUpdateSkins = setShouldUpdateSkins;
            return this;
        }

        public SkinBuilder setSkinName(String setSkinName){
            this.setSkinName = setSkinName;
            return this;
        }

        public SkinBuilder setSkinPersistent(String[] setSkinPersistent){
            this.setSkinPersistent = setSkinPersistent;
            return this;
        }

        public SkinBuilder setTexture(String[] setTexture){
            this.setTexture = setTexture;
            return this;
        }

        public SkinTrait Build(){
            return new SkinTrait(setFetchDefaultSkin, setShouldUpdateSkins, setSkinName, setSkinPersistent, setTexture);
        }
    }
}
