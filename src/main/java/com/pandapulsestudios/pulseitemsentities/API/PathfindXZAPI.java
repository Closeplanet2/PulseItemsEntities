package com.pandapulsestudios.pulseitemsentities.API;

import com.pandapulsestudios.pulsecore.Data.API.UUIDDataAPI;
import com.pandapulsestudios.pulseitemsentities.Abstract.PulseCitizensEntity;
import com.pandapulsestudios.pulseitemsentities.Citizens.NavigatorCallback.WalkingNPC;
import com.pandapulsestudios.pulseitemsentities.Configs.GridXZ;
import com.pandapulsestudios.pulseitemsentities.CustomVariable.GridSquareXZ;
import com.pandapulsestudios.pulseitemsentities.CustomVariable.LocXZ;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.ai.event.CancelReason;
import net.citizensnpcs.api.ai.event.NavigatorCallback;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class PathfindXZAPI {
    public static boolean Pathfind(Entity entity, Location end, GridXZ gridXZ, PulseCitizensEntity pulseCitizensEntity, boolean diagonal){
        var walkingPath = gridXZ == null ? List.of(end) : AStarPathfind(entity.getLocation(), end, gridXZ, diagonal);
        if(!pulseCitizensEntity.IsEntity(entity) || walkingPath.isEmpty()) return false;

        var npc = CitizensAPI.getNPCRegistry().getNPC(entity);
        npc.getNavigator().getDefaultParameters().addSingleUseCallback(new WalkingNPC(npc, walkingPath));
        npc.getNavigator().setTarget(walkingPath.get(0));

        return true;
    }

    private static List<Location> AStarPathfind(Location start, Location end, GridXZ gridXZ, boolean diagonal){
        //return the start and end location on the grid -> start and end location doesn't need to be grid points
        var closestStartLocXZ = gridXZ.ReturnClosestLocXZ(start);
        var closestEndLocXZ = gridXZ.ReturnClosestLocXZ(end);

        //create a list of all the grid squares with the data for a* pathfind and a list of all neighbours
        var gridSquares = new HashMap<LocXZ, GridSquareXZ>();
        for(var locXZ : gridXZ.gridLocations) gridSquares.put(locXZ, new GridSquareXZ(gridXZ.gridLocations, locXZ, diagonal));

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
                path.add(end);
                path.add(lowestLocXZ.ConvertLocation(end));

                var temp = gridSquares.get(lowestLocXZ);
                while (temp != null){
                    if(temp.previous == null) break;
                    path.add(temp.previous.ConvertLocation(end));
                    temp = temp.previous == null ? null : gridSquares.get(temp.previous);
                }

                path.add(closestStartLocXZ.ConvertLocation(end));
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
                gridSquares.get(neighbourLocXZ).h = neighbourLocXZ.distance(closestEndLocXZ.ConvertLocation(end));
                //calculate the overall score
                gridSquares.get(neighbourLocXZ).f = gridSquares.get(neighbourLocXZ).h + gridSquares.get(neighbourLocXZ).g;
                //store where we came from as the lowest xz
                gridSquares.get(neighbourLocXZ).previous = lowestLocXZ;
            }
        }

        return null;
    }
}
