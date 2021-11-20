package cn.todest.mcmod.inventorydetector.Common.event;

import cn.todest.mcmod.inventorydetector.Common.gui.ItemRecorderGUI;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class RecorderHudClientEvent {

    @SubscribeEvent
    public static void onOverlayRender(RenderGameOverlayEvent event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) {
            return;
        }
        if (Minecraft.getInstance().player == null) {
            return;
        }
        ItemRecorderGUI obsidianGUI = new ItemRecorderGUI(event.getMatrixStack());
        obsidianGUI.render();
    }
}
