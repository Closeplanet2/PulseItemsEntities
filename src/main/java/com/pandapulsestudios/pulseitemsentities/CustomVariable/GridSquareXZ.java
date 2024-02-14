package com.pandapulsestudios.pulseitemsentities.CustomVariable;

import java.util.ArrayList;
import java.util.List;

public class GridSquareXZ {
    public static final int DISTANCE_BETWEEN_SQUARES = 1;

    public LocXZ currentLocxz;
    public List<LocXZ> neighbours = new ArrayList<>();

    public double f;
    public double g;
    public double h;
    public LocXZ previous;

    public GridSquareXZ(List<LocXZ> locXZS, LocXZ currentLocxz, boolean allowDiag){
        this.currentLocxz = currentLocxz;
        for(var x = currentLocxz.x - DISTANCE_BETWEEN_SQUARES; x <= currentLocxz.x + DISTANCE_BETWEEN_SQUARES; x++){
            for(var z = currentLocxz.z - DISTANCE_BETWEEN_SQUARES; z <= currentLocxz.z + DISTANCE_BETWEEN_SQUARES; z++){
                if(currentLocxz.IsSame(x, z)) continue;
                if(x != 0 && z != 0 && !allowDiag) continue;
                for(var loc : locXZS){
                    if(loc.IsSame(x, z)) neighbours.add(loc);
                }
            }
        }
    }
}
