package cn.todest.mcmod.inventorydetector.Common.event;

import net.minecraftforge.client.event.GuiContainerEvent;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class InventoryEventHandler {
    private static long time;

    @SubscribeEvent
    public static void openInventory(GuiContainerEvent event) {
        InventoryDetectEventLoader.setCanceled(true, false);
        InventoryDetectEventLoader.syncPrevious();
        time = System.currentTimeMillis();
    }

    @SubscribeEvent
    public static void closeInventory(InputUpdateEvent event) {
        if (System.currentTimeMillis() - time > 20) {
            InventoryDetectEventLoader.setCanceled(false, false);
        }
    }
}
