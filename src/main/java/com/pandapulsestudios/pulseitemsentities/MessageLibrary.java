package com.pandapulsestudios.pulseitemsentities;

import com.pandapulsestudios.pulsecore.Chat.ChatAPI;
import com.pandapulsestudios.pulsecore.Chat.MessageType;

public class MessageLibrary {
    private static String error = "#FF5B5B";
    private static String success = "#8FFF32";
    private static String name = "#229DDF";

    public static class GridXZCommand{
        public static class CreateGrid{
            public static String FAILED(String gridName){ return String.format("%sYou cannot create a grid with the given name: %s%s", error, name, gridName); }
            public static String SUCCESS(String gridName){ return String.format("%sYou created a grid with the given name: %s%s", success, name, gridName); }
        }

        public static class DeleteGrid{
            public static String FAILED(String gridName){ return String.format("%sYou cannot delete a grid with the given name: %s%s", error, name, gridName); }
            public static String SUCCESS(String gridName){ return String.format("%sYou deleted a grid with the given name: %s%s", success, name, gridName); }
        }

        public static class StartEdit{
            public static String FAILED(String gridName){ return String.format("%sYou cannot edit a grid with the given name: %s%s", error, name, gridName); }
            public static String SUCCESS(String gridName){ return String.format("%sYou are now editing a grid with the given name: %s%s", success, name, gridName); }
        }

        public static class StopEdit{
            public static String FAILED(String gridName){ return String.format("%sYou are not editing a grid with the given name: %s%s", error, name, gridName); }
            public static String SUCCESS(String gridName){ return String.format("%sYou are no longer editing a grid with the given name: %s%s", success, name, gridName); }
        }

        public static class ClearGrid{
            public static String FAILED(String gridName){ return String.format("%sYou cannot clear a grid with the given name: %s%s", error, name, gridName); }
            public static String SUCCESS(String gridName){ return String.format("%sYou cleared all points from a grid with the given name: %s%s", success, name, gridName); }
        }

        public static class ReloadGrids{
            public static String SUCCESS(){ return String.format("%sYou reloaded all grids (%d)!", success, PulseItemsEntities.GridXZHashMap.size()); }
        }
    }

    public static class BlockBreak{
        public static String FAILED(String gridName){ return String.format("%sBlock removed from grid with the given name: %s%s", error, name, gridName); }
        public static String SUCCESS(String gridName){ return String.format("%sBlock added to grid grid with the given name: %s%s", success, name, gridName); }
    }

    public static class RegisteredEntity{
       public static String REGISTER(String customName){ return String.format("&3Registered Custom Entity: %s", customName);}
    }
}
