package cn.todest.mcmod.inventorydetector.common.utils;


import java.util.ArrayList;
import java.util.Arrays;


public class Utils {
    public static final ArrayList<ArrayList<String>> RecordItems = new ArrayList<ArrayList<String>>() {{
        add(new ArrayList<String>(Arrays.asList("minecraft:diamond", "")));
        add(new ArrayList<String>(Arrays.asList("minecraft:iron_ingot", "")));
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
