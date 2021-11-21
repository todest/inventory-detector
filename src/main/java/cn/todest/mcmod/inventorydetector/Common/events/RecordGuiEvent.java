package cn.todest.mcmod.inventorydetector.common.gui;

import cn.todest.mcmod.inventorydetector.common.events.DetectorEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Map;

public class RecorderGUI {
    private final Minecraft minecraft = Minecraft.getMinecraft();
    private final int width = minecraft.displayWidth;
    private final int height = minecraft.displayHeight;
    private FontRenderer fontRenderer = minecraft.fontRendererObj;

    @SubscribeEvent
    public void renderGUI(RenderWorldLastEvent event) {
        Map<Item, Integer> ItemCount = DetectorEvents.ItemCount;
        if (ItemCount == null) {
            return;
        }
        int typesNum = 0, maxNameLength = 0, maxCountLength = 0, count;
        String name;
        for (Item item : ItemCount.keySet()) {
            name = item.toString();
            count = ItemCount.get(item);
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
        for (Item item : ItemCount.keySet()) {
            name = item.toString();
            count = ItemCount.get(item);
            if (count == 0) {
                continue;
            }
            int x4 = width - 10; // ]
            int x2 = x4 - maxCountLength * 8; // [
            int x3 = (x2 + x4) / 2 + 1; // count
            int x1 = x2 - maxNameLength * 5; // name
            if (Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage()
                    .toString().equals("English (Australia)")) {
                x1 = x2 - maxNameLength * 3;
            }

            drawStringWithShadow(this.minecraft.fontRendererObj,
                    name + ": ", x1, height / 10 + typesNum * 10, 0xFFFFFF
            );
            drawString(this.minecraft.fontRendererObj,
                    "[", x2, height / 10 + typesNum * 10, 0xFFFFFF
            );
            drawString(this.minecraft.fontRendererObj,
                    "]", x4, height / 10 + typesNum * 10, 0xFFFFFF
            );
            drawCenteredString(this.minecraft.fontRendererObj,
                    String.valueOf(count), x3, height / 10 + typesNum * 10, 0xFFFFFF
            );
            typesNum++;
        }
    }
}
