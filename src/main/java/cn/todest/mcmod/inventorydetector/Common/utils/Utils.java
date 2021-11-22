package cn.todest.mcmod.inventorydetector.common.utils;


import java.util.ArrayList;
import java.util.Arrays;


public class Utils {
    public static final ArrayList<ArrayList<String>> RecordItems = new ArrayList<ArrayList<String>>() {{
        add(new ArrayList<String>(Arrays.asList("minecraft:diamond_horse_armor", "Diamante's Handle")));
        add(new ArrayList<String>(Arrays.asList("minecraft:ender_eye", "L.A.S.R.'s Eye")));
        add(new ArrayList<String>(Arrays.asList("minecraft:firework_charge", "Jolly Pink Rock")));
        add(new ArrayList<String>(Arrays.asList("minecraft:lead", "Bigfoot's Lasso")));
        add(new ArrayList<String>(Arrays.asList("minecraft:skull", "Holy Dragon Fragment")));
        add(new ArrayList<String>(Arrays.asList("minecraft:skull", "Young Dragon Fragment")));
        add(new ArrayList<String>(Arrays.asList("minecraft:skull", "Unstable Dragon Fragment")));
        add(new ArrayList<String>(Arrays.asList("minecraft:skull", "Superior Dragon Fragment")));
        add(new ArrayList<String>(Arrays.asList("minecraft:netherbrick", "Livid Fragment")));
        add(new ArrayList<String>(Arrays.asList("minecraft:red_mushroom", "Bonzo Fragment")));
        add(new ArrayList<String>(Arrays.asList("minecraft:red_flower", "Scarf Fragment")));
    }};

    public static boolean isRecordItem(String type, String name) {
        for (ArrayList<String> arrayList : RecordItems) {
            if (arrayList.get(0).equals(type) && (arrayList.get(1).equals("") || arrayList.get(1).contains(name))) {
                return true;
            }
        }
        return false;
    }
}
