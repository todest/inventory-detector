package cn.todest.mcmod.inventorydetector.common.utils;


import javafx.util.Pair;

import java.util.ArrayList;

import static java.util.Arrays.asList;

public class Utils {
    public static final ArrayList<Pair<String, String>> RecordItems = new ArrayList<Pair<String, String>>(
            asList(
                    new Pair<String, String>("diamond", ""),
                    new Pair<String, String>("iron_ingot", ""),
                    new Pair<String, String>("emerald", "")
            )
    );

    public static boolean isRecordItem(String type, String name) {
        for (Pair<String, String> pair : RecordItems) {
            if (pair.getKey().equals(type) && (pair.getValue().equals("") || pair.getValue().contains(name))) {
                return true;
            }
        }
        return false;
    }
}
