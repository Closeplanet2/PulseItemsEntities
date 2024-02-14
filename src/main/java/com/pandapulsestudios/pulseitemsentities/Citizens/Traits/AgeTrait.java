package com.pandapulsestudios.pulseitemsentities.Citizens.Traits;

import net.citizensnpcs.api.npc.NPC;

public record AgeTrait(int age, boolean locked) {

    public void Set(NPC npc){
        var npcTrait = npc.getOrAddTrait(net.citizensnpcs.trait.Age.class);
        npcTrait.setAge(age);
        npcTrait.setLocked(locked);
    }

    public static AgeBuilder AgeBuilder(){ return new AgeBuilder(); }
    public static class AgeBuilder{
        private int age = 0;
        private boolean locked = true;

        private AgeBuilder age(int age){
            this.age = age;
            return this;
        }

        private AgeBuilder locked(boolean locked){
            this.locked = locked;
            return this;
        }

        private AgeTrait Build(){
            return new AgeTrait(age, locked);
        }
    }
}
