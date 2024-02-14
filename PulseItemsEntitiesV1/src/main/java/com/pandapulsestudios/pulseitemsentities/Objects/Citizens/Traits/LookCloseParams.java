package com.pandapulsestudios.pulseitemsentities.Objects.Citizens.Traits;

public record LookCloseParams(boolean setDisableWhileNavigating, boolean setHeadOnly, boolean lookClose,
                              boolean setLinkedBody, boolean setPerPlayer, boolean setRandomLook,
                              int setRandomLookDelay, float[] setRandomLookPitchRange, float[] setRandomLookYawRange,
                              boolean setRandomlySwitchTargets, double setRange, boolean setRealisticLooking) {
    public static Builder Create(){return new Builder();}
    public static class Builder{
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

        public Builder setDisableWhileNavigating(boolean setDisableWhileNavigating){
            this.setDisableWhileNavigating = setDisableWhileNavigating;
            return this;
        }

        public Builder lookClose(boolean lookClose){
            this.lookClose = lookClose;
            return this;
        }

        public Builder setRandomLook(boolean setRandomLook){
            this.setRandomLook = setRandomLook;
            return this;
        }

        public Builder setHeadOnly(boolean setHeadOnly){
            this.setHeadOnly = setHeadOnly;
            return this;
        }

        public Builder setRealisticLooking(boolean setRealisticLooking){
            this.setRealisticLooking = setRealisticLooking;
            return this;
        }

        public Builder setLinkedBody(boolean setLinkedBody){
            this.setLinkedBody = setLinkedBody;
            return this;
        }

        public Builder setPerPlayer(boolean setPerPlayer){
            this.setPerPlayer = setPerPlayer;
            return this;
        }

        public Builder setRandomlySwitchTargets(boolean setRandomlySwitchTargets){
            this.setRandomlySwitchTargets = setRandomlySwitchTargets;
            return this;
        }

        public Builder setRandomLookDelay(int setRandomLookDelay){
            this.setRandomLookDelay = setRandomLookDelay;
            return this;
        }

        public Builder setRandomLookPitchRange(float[] setRandomLookPitchRange){
            this.setRandomLookPitchRange = setRandomLookPitchRange;
            return this;
        }

        public Builder setRandomLookYawRange(float[] setRandomLookYawRange){
            this.setRandomLookYawRange = setRandomLookYawRange;
            return this;
        }

        public Builder setRange(double setRange){
            this.setRange = setRange;
            return this;
        }

        public LookCloseParams Build(){
            return new LookCloseParams(setDisableWhileNavigating, setHeadOnly, lookClose, setLinkedBody,
                    setPerPlayer, setRandomLook, setRandomLookDelay, setRandomLookPitchRange,
                    setRandomLookYawRange, setRandomlySwitchTargets, setRange, setRealisticLooking);
        }
    }
}
