package com.pandapulsestudios.pulseitemsentities.Objects.Grid;

import com.pandapulsestudios.pulsecore.Data.Interface.CustomVariable;
import org.bukkit.Location;

public class LocXZ implements CustomVariable {

    public double x;
    public double z;

    @Override
    public Class<?> ClassType() { return LocXZ.class;}

    @Override
    public String SerializeData() {
        return String.format("%f:%f", x, z);
    }

    @Override
    public Object DeSerializeData(String s) {
        var data = s.split(":");
        return LocXZ.CreateLoc(Double.parseDouble(data[0]), Double.parseDouble(data[1]));
    }

    public boolean IsSame(LocXZ locXZ){ return this.x == locXZ.x && this.z == locXZ.z; }
    public boolean IsSame(double x, double z){
        return this.x == x && this.z == z;
    }

    public double distance(Location location){
        var storedLocation = ConvertLocation(location);
        return storedLocation.distance(location);
    }

    public Location ConvertLocation(Location location){
        return new Location(location.getWorld(), x, location.getY(), z, location.getYaw(), location.getPitch());
    }

    @Override
    public Object DefaultValue() {
        return LocXZ.CreateLoc(0, 0);
    }

    public static LocXZ CreateLoc(Location location){
        var loc = new LocXZ();
        loc.x = location.getX();
        loc.z = location.getZ();
        return loc;
    }

    public static LocXZ CreateLoc(double x, double z){
        var loc = new LocXZ();
        loc.x = x;
        loc.z = z;
        return loc;
    }
}

