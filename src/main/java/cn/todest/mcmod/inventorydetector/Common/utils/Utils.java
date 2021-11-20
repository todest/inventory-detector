package cn.todest.mcmod.inventorydetector.Common.utils;

import net.minecraft.item.Item;

import java.util.Arrays;
import java.util.List;

public class Utils {
    public static final String MOD_ID = "inventory-detector";
    public static final List<String> RecordedItems = Arrays.asList(
            "diamond", "iron_ingot", "gold_ingot", "netherite_ingot", "lapis_lazuli", "emerald"
    );

    public static Item getItemByName(String name) {
        for (int i = 0; ; i++) {
            Item item = Item.getItemById(i);
            if (item.toString().equals(name)) {
                return item;
            }
        }
    }
}
