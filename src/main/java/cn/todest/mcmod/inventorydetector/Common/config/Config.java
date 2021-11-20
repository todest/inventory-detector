package cn.todest.mcmod.inventorydetector.Common.config;

import cn.todest.mcmod.inventorydetector.Common.event.InventoryDetectEventLoader;
import cn.todest.mcmod.inventorydetector.Common.utils.Utils;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Utils.MOD_ID)
public class Config {
    public static ConfigValue<Integer> NameColor;
    public static ConfigValue<Integer> CountColor;
    public static ConfigValue<Integer> BracketsColor;
    public static ConfigValue<List<? extends String>> RecordedItems;
    public static List<ConfigValue<Integer>> Count;
    public static ForgeConfigSpec CLIENT_CONFIG;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.comment(" General Settings");
        builder.push("General");
        NameColor = builder.comment("Color of Name")
                .define("NameColor", 0xFFFFFF);
        CountColor = builder.comment("Color of Count")
                .define("CountColor", 0xFFFFFF);
        BracketsColor = builder.comment("Color of Brackets")
                .define("BracketsColor", 0xFFFFFF);
        builder.pop();

        builder.comment(" This category will configure which items you want to record picked up count.");
        builder.push("RecordedItems");
        RecordedItems = builder.comment(" This config entry will hold a list of strings.")
                .defineList("Items", Utils.RecordedItems, entry -> true);
        builder.comment(" There is the count of the recorded items");
        builder.push("Count");
        Count = new ArrayList<>();
        for (int i = 0; i < Utils.RecordedItems.size(); i++) {
            Count.add(builder.define(Utils.RecordedItems.get(i), 0));
        }
        builder.pop();
        builder.pop();
        CLIENT_CONFIG = builder.build();
    }

    public static void save() {
        for (Item item : InventoryDetectEventLoader.ItemCount.keySet()) {
            for (ConfigValue<Integer> configValue : Count) {
                if (item.toString().equals(configValue.getPath().get(2))) {
                    configValue.set(InventoryDetectEventLoader.ItemCount.get(item));
                }
            }
        }
        CLIENT_CONFIG.save();
    }

    public static void bake() {
        for (ConfigValue<Integer> configValue : Count) {
            InventoryDetectEventLoader.ItemCount.put(Utils.getItemByName(configValue.getPath().get(2)), configValue.get());
        }
    }

    @SubscribeEvent
    public static void onLoad(final ModConfig.ModConfigEvent configEvent) {
        bake();
    }
}
