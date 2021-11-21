package cn.todest.mcmod.inventorydetector.Common.event;

import net.minecraftforge.client.event.GuiContainerEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class InventoryEventHandler {
    @SubscribeEvent
    public static void openInventory(GuiContainerEvent event) {
//        System.out.println("-------------------------");
        InventoryDetectEventLoader.setCanceled(true);
    }

    @SubscribeEvent
    public static void closeInventory(PlayerContainerEvent.Close event) {
//        System.out.println("++++++++++++++++++++++++++");
        InventoryDetectEventLoader.setCanceled(false);
        InventoryDetectEventLoader.syncPrevious();
    }
}
