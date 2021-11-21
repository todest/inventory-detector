package cn.todest.mcmod.inventorydetector;

import cn.todest.mcmod.inventorydetector.common.events.DetectorEvent;
import cn.todest.mcmod.inventorydetector.common.events.InventoryEvent;
import cn.todest.mcmod.inventorydetector.common.events.RecordGuiEvent;
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
        MinecraftForge.EVENT_BUS.register(new RecordGuiEvent());
        MinecraftForge.EVENT_BUS.register(new DetectorEvent());
        MinecraftForge.EVENT_BUS.register(new InventoryEvent());
    }
}
