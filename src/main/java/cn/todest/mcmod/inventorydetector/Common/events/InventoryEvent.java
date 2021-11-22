package cn.todest.mcmod.inventorydetector.common.events;

import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class InventoryEvent {
    private static long openTime;

    @SubscribeEvent
    public void openInventory(GuiScreenEvent event) {
        openTime = System.currentTimeMillis();
        DetectorEvent.setCanceled(true);
        DetectorEvent.syncPrevious();
    }

    @SubscribeEvent
    public void closeInventory(RenderWorldLastEvent event) {
        if (System.currentTimeMillis() - openTime > 50) {
            DetectorEvent.setCanceled(false);
        }
    }
}
