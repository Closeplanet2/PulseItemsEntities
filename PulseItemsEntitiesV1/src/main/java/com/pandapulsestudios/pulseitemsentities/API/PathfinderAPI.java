package com.pandapulsestudios.pulseitemsentities.API;

import com.pandapulsestudios.pulsecore.Data.API.UUIDDataAPI;
import com.pandapulsestudios.pulseitemsentities.Configs.GridXZ;
import com.pandapulsestudios.pulseitemsentities.Interface.CustomEntity;
import com.pandapulsestudios.pulseitemsentities.Interface.PulseEntity;
import com.pandapulsestudios.pulseitemsentities.Interface.WalkingEntityCallback;
import com.pandapulsestudios.pulseitemsentities.Objects.Grid.LocXZ;
import com.pandapulsestudios.pulseitemsentities.Objects.Grid.GridSquareXZ;
import com.pandapulsestudios.pulseitemsentities.Objects.Wrapper.PulseEntityWrapper;
import com.pandapulsestudios.pulseitemsentities.PulseItemsEntities;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.ai.event.CancelReason;
import net.citizensnpcs.api.ai.event.NavigatorCallback;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.*;

public class PathfinderAPI {
    public static boolean Pathfind(Entity entity, Location endLocation, GridXZ gridXZ, PulseEntityWrapper pulseEntityWrapper, boolean allowDiag){
        if(!pulseEntityWrapper.customEntity().useCitizens()) return false;
        var walkingPath =  gridXZ == null ? List.of(endLocation) : AStarPathfind(entity.getLocation(), endLocation, gridXZ, allowDiag);
        if(walkingPath == null || walkingPath.size() <= 1) return false;

        var entityNPC = CitizensAPI.getNPCRegistry().getNPC(entity);
        entityNPC.getNavigator().getDefaultParameters().addSingleUseCallback(
                new NavigatorCallback() {
                    @Override
                    public void onCompletion(CancelReason cancelReason) {
                        var nextTarget = (int) UUIDDataAPI.GET(entityNPC.getUniqueId(), "PathIndex", 0) + 1;
                        if(nextTarget < walkingPath.size()){
                            entityNPC.getNavigator().setTarget(walkingPath.get(nextTarget));
                            UUIDDataAPI.STORE(entityNPC.getUniqueId(), "PathIndex", nextTarget);
                        }
                    }
                }
        );

        entityNPC.getNavigator().setTarget(walkingPath.get(0));
        return true;
    }

    private static List<Location> AStarPathfind(Location startLocation, Location endLocation, GridXZ gridXZ, boolean allowDiag){
        //return the start and end location on the grid -> start and end location doesn't need to be grid points
        var closestStartLocXZ = gridXZ.ReturnClosestLocXZ(startLocation);
        var closestEndLocXZ = gridXZ.ReturnClosestLocXZ(endLocation);

        //create a list of all the grid squares with the data for a* pathfind and a list of all neighbours
        var gridSquares = new HashMap<LocXZ, GridSquareXZ>();
        for(var locXZ : gridXZ.gridLocations) gridSquares.put(locXZ, new GridSquareXZ(gridXZ.gridLocations, locXZ, allowDiag));

        //create the start data for a star pathfind -> add the start tile on the grid
        var openSet = new ArrayList<LocXZ>();
        var closedSet = new ArrayList<LocXZ>();
        openSet.add(closestStartLocXZ);

        while (!openSet.isEmpty()){//Assume the first value in open set is the winner
            var lowestLocXZ = openSet.get(0);
            //Loop through all values in the open set to check if the assumed winner is correct based on the stored f value
            for(var locXZ : openSet){
                if(gridSquares.get(locXZ).f < gridSquares.get(lowestLocXZ).f) lowestLocXZ = locXZ;
            }

            //check if the lowestf is the final location
            if(lowestLocXZ.IsSame(closestEndLocXZ)){
                //Find the path of the shortest path
                var path = new ArrayList<Location>();
                path.add(endLocation);
                path.add(lowestLocXZ.ConvertLocation(endLocation));

                var temp = gridSquares.get(lowestLocXZ);
                while (temp != null){
                    if(temp.previous == null) break;
                    path.add(temp.previous.ConvertLocation(endLocation));
                    temp = temp.previous == null ? null : gridSquares.get(temp.previous);
                }

                path.add(closestStartLocXZ.ConvertLocation(endLocation));
                Collections.reverse(path);
                return path;
            }

            //remove from open set and add to closed set to mark that we have checked / checking that square
            //Also stops squares checking through neighbour
            closedSet.add(lowestLocXZ);
            openSet.remove(lowestLocXZ);

            //Loop through all the current neighbours
            for(var neighbourLocXZ : gridSquares.get(lowestLocXZ).neighbours){
                if(closedSet.contains(neighbourLocXZ)) continue;

                var temp_g = gridSquares.get(lowestLocXZ).g + GridSquareXZ.DISTANCE_BETWEEN_SQUARES;
                if(openSet.contains(neighbourLocXZ)){
                    //If the neigbour is in the open set check if we have the shortest value
                    if(temp_g < gridSquares.get(neighbourLocXZ).g) gridSquares.get(neighbourLocXZ).g = temp_g;
                }else{
                    //If not we have the shortest value
                    gridSquares.get(neighbourLocXZ).g = temp_g;
                    openSet.add(neighbourLocXZ);
                }

                //calculate the raw distance between the neigbour and the closest end location in the grid
                gridSquares.get(neighbourLocXZ).h = neighbourLocXZ.distance(closestEndLocXZ.ConvertLocation(endLocation));
                //calculate the overall score
                gridSquares.get(neighbourLocXZ).f = gridSquares.get(neighbourLocXZ).h + gridSquares.get(neighbourLocXZ).g;
                //store where we came from as the lowest xz
                gridSquares.get(neighbourLocXZ).previous = lowestLocXZ;
            }
        }

        return null;
    }
}
