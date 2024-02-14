package com.pandapulsestudios.pulseitemsentities.Objects.Citizens.Traits;

public record SkinParams(boolean setFetchDefaultSkin,
                         boolean setShouldUpdateSkins,
                         String setSkinName,
                         String[] setSkinPersistent,
                         String[] setTexture) {
    public static Builder Create(){return new Builder();}
    public static class Builder{
        boolean setFetchDefaultSkin = true;
        boolean setShouldUpdateSkins = false;
        String setSkinName = "";
        String[] setSkinPersistent = {};
        String[] setTexture = {};

        public Builder setFetchDefaultSkin(boolean setFetchDefaultSkin){
            this.setFetchDefaultSkin = setFetchDefaultSkin;
            return this;
        }

        public Builder setShouldUpdateSkins(boolean setShouldUpdateSkins){
            this.setShouldUpdateSkins = setShouldUpdateSkins;
            return this;
        }

        public Builder setSkinName(String setSkinName){
            this.setSkinName = setSkinName;
            return this;
        }

        public Builder setSkinPersistent(String[] setSkinPersistent){
            this.setSkinPersistent = setSkinPersistent;
            return this;
        }

        public Builder setTexture(String[] setTexture){
            this.setTexture = setTexture;
            return this;
        }

        public SkinParams Create(){
            return new SkinParams(setFetchDefaultSkin, setShouldUpdateSkins, setSkinName, setSkinPersistent, setTexture);
        }
    }

}
