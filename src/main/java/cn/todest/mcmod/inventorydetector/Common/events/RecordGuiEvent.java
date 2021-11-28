package cn.todest.mcmod.inventorydetector.common.events;

import cn.todest.mcmod.inventorydetector.common.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
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
        if (ItemCount == null || DetectorEvent.getCanceled()) {
            minecraft.renderEngine.bindTexture(Gui.icons);
            return;
        }
        ScaledResolution scaledresolution = new ScaledResolution(minecraft);
        int height = scaledresolution.getScaledHeight();
        int width = scaledresolution.getScaledWidth();
        int typesNum = 0, maxNameLength = 0, maxCountLength = 0, count;
        int bracketLength = fontRenderer.getStringWidth("[");
        String name;
        for (ItemStack itemStack : ItemCount.keySet()) {
            name = Utils.getShortName(itemStack.getItem().delegate.name(), itemStack.getDisplayName());
            count = ItemCount.get(itemStack);
            if (count == 0) {
                continue;
            }
            if (maxNameLength < fontRenderer.getStringWidth(name)) {
                maxNameLength = fontRenderer.getStringWidth(name);
            }
            if (maxCountLength < fontRenderer.getStringWidth(String.valueOf(count))) {
                maxCountLength = fontRenderer.getStringWidth(String.valueOf(count));
            }
        }
        for (ItemStack itemStack : ItemCount.keySet()) {
            name = Utils.getShortName(itemStack.getItem().delegate.name(), itemStack.getDisplayName());
            count = ItemCount.get(itemStack);
            if (count == 0) {
                continue;
            }
            float x4 = width - 10; // ]
            float x2 = x4 - maxCountLength - 6; // [
            float x3 = x2 + 2 + bracketLength; // count
            float x1 = x2 - maxNameLength - 6;
            fontRenderer.drawStringWithShadow(
                    name + ": ", x1, (float) (height / 10.0 + typesNum * 10), 0xFF0000
            );
            fontRenderer.drawStringWithShadow(
                    "[", x2, (float) (height / 10.0 + typesNum * 10), 0x808080
            );
            fontRenderer.drawStringWithShadow(
                    "]", x4, (float) (height / 10.0 + typesNum * 10), 0x808080
            );
            fontRenderer.drawStringWithShadow(
                    String.valueOf(count), x3, (float) (height / 10.0 + typesNum * 10), 0x00FF00
            );
            typesNum++;
        }
        fontRenderer.drawString("", 0, 0, 0xFFFFFF);
        minecraft.renderEngine.bindTexture(Gui.icons);
    }
}
