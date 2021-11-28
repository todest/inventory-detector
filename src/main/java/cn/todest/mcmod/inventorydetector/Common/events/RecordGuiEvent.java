package cn.todest.mcmod.inventorydetector.common.events;

import cn.todest.mcmod.inventorydetector.common.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Map;

public class RecordGuiEvent {
    private final Minecraft minecraft = Minecraft.getMinecraft();
    private final FontRenderer fontRenderer = minecraft.fontRendererObj;

    @SubscribeEvent
    public void renderGUI(RenderGameOverlayEvent event) {
        Map<ItemStack, Integer> ItemCount = DetectorEvent.ItemCount;
        if (ItemCount == null) {
            return;
        }
        int height = minecraft.displayHeight / 4;
        int width = minecraft.displayWidth / 4;
        int typesNum = 0, maxNameLength = 0, maxCountLength = 0, count;
        String name;
        for (ItemStack itemStack : ItemCount.keySet()) {
            name = Utils.getShortName(itemStack.getItem().delegate.name(), itemStack.getDisplayName());
            count = ItemCount.get(itemStack);
            if (count == 0) {
                continue;
            }
            if (maxNameLength < name.length() * 2) {
                maxNameLength = name.length() * 2;
            }
            if (maxCountLength < count) {
                maxCountLength = count;
            }
        }
        maxCountLength = String.valueOf(maxCountLength).length();
        for (ItemStack itemStack : ItemCount.keySet()) {
            name = Utils.getShortName(itemStack.getItem().delegate.name(), itemStack.getDisplayName());
            count = ItemCount.get(itemStack);
            if (count == 0) {
                continue;
            }
            int x4 = width - 10; // ]
            int x2 = x4 - maxCountLength * 8; // [
            int x3 = x2 + 3; // count
//            int x1 = x2 - maxNameLength * 6; // name
//            if (Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage()
//                    .toString().equals("English (Australia)")) {
            int x1 = x2 - maxNameLength * 3;
//            }
            fontRenderer.drawString(
                    name + ": ", x1, height / 10 + typesNum * 10, 0xFFFFFF
            );
            fontRenderer.drawString(
                    "[", x2, height / 10 + typesNum * 10, 0xFFFFFF
            );
            fontRenderer.drawString(
                    "]", x4, height / 10 + typesNum * 10, 0xFFFFFF
            );
            fontRenderer.drawString(
                    String.valueOf(count), x3, height / 10 + typesNum * 10, 0xFFFFFF
            );
            typesNum++;
        }
        minecraft.renderEngine.bindTexture(Gui.icons);
    }
}
