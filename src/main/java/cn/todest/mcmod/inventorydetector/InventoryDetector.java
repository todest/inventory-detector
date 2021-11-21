package cn.todest.mcmod.inventorydetector;

import cn.todest.mcmod.inventorydetector.common.events.DetectorEvents;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = InventoryDetector.MODID, version = InventoryDetector.VERSION)
public class InventoryDetector {
    public static final String MODID = "inventory-detector";
    public static final String VERSION = "1.0";

    @EventHandler
    public void init(FMLInitializationEvent event) {
        // some example code
        System.out.println("DIRT BLOCK >> " + Blocks.dirt.getUnlocalizedName());
        MinecraftForge.EVENT_BUS.register(new DetectorEvents());
    }
}
