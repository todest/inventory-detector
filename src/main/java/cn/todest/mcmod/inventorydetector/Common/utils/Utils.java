package cn.todest.mcmod.inventorydetector.common.utils;


import java.util.ArrayList;
import java.util.Arrays;


public class Utils {
    public static final ArrayList<ArrayList<String>> RecordItems = new ArrayList<ArrayList<String>>() {{
        add(new ArrayList<String>(Arrays.asList("minecraft:diamond_horse_armor", "Diamante's Handle", "Handle")));
        add(new ArrayList<String>(Arrays.asList("minecraft:ender_eye", "L.A.S.R.'s Eye", "Eye")));
        add(new ArrayList<String>(Arrays.asList("minecraft:firework_charge", "Jolly Pink Rock", "Rock")));
        add(new ArrayList<String>(Arrays.asList("minecraft:lead", "Bigfoot's Lasso", "Lasso")));
        add(new ArrayList<String>(Arrays.asList("minecraft:skull", "Holy Dragon Fragment", "Holy")));
        add(new ArrayList<String>(Arrays.asList("minecraft:skull", "Young Dragon Fragment", "Young")));
        add(new ArrayList<String>(Arrays.asList("minecraft:skull", "Unstable Dragon Fragment", "Unstable")));
        add(new ArrayList<String>(Arrays.asList("minecraft:skull", "Superior Dragon Fragment", "Superior")));
        add(new ArrayList<String>(Arrays.asList("minecraft:nether_brick", "Livid Fragment", "Livid")));
        add(new ArrayList<String>(Arrays.asList("minecraft:red_mushroom", "Bonzo Fragment", "Bonzo")));
        add(new ArrayList<String>(Arrays.asList("minecraft:red_flower", "Scarf Fragment", "Scarf")));
    }};
    public static ArrayList<String> DungeonStartMessages = new ArrayList<String>(Arrays.asList(
            "Dungeon starts in 2 seconds.",
            "Dungeon starts in 1 seconds.",
            "Here, I found this map when I first entered the dungeon."
    ));
    public static ArrayList<String> DungeonStopMessages = new ArrayList<String>(Arrays.asList(
            "Warping...",
            "disconnected from the Dungeon and became a ghost.",
            "Sending to server"
    ));

    public static boolean isRecordItem(String type, String name) {
        for (ArrayList<String> arrayList : RecordItems) {
            if (arrayList.get(0).equals(type) && (arrayList.get(1).equals("") || arrayList.get(1).contains(name))) {
                return true;
            }
        }
        return false;
    }

    public static String getShortName(String name, String displayName) {
        for (ArrayList<String> arrayList : RecordItems) {
            if (arrayList.get(0).equals(name) && (arrayList.get(1).equals("") || arrayList.get(1).equals(displayName))) {
                return arrayList.get(2);
            }
        }
        return "Null";
    }
}
