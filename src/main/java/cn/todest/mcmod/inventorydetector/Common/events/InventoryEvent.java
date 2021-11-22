package cn.todest.mcmod.inventorydetector.common.events;

import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class InventoryEvent {
    private static long time;

    @SubscribeEvent
    public void openInventory(GuiScreenEvent event) {
        DetectorEvent.setCanceled(true);
        DetectorEvent.syncPrevious();
        time = System.currentTimeMillis();
    }

    @SubscribeEvent
    public void closeInventory(RenderWorldLastEvent event) {
        if (System.currentTimeMillis() - time > 50) {
            DetectorEvent.setCanceled(false);
        }
    }
}
