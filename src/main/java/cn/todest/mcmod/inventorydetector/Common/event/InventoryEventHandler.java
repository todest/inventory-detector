package cn.todest.mcmod.inventorydetector.Common.event;

import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.GuiContainerEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class InventoryEventHandler {

    @SubscribeEvent
    public static void openInventory(GuiContainerEvent event) {
        InventoryDetectEventLoader.setCanceled(true, false);
    }

    @SubscribeEvent
    public static void loginGame(ClientPlayerNetworkEvent.LoggedInEvent event) {
        InventoryDetectEventLoader.Init();
    }

    @SubscribeEvent
    public void closeInventory(PlayerContainerEvent.Close event) {
        InventoryDetectEventLoader.setCanceled(false, false);
        InventoryDetectEventLoader.syncPrevious();
    }
}
