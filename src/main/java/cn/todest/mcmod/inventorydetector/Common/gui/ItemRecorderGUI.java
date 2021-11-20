package cn.todest.mcmod.inventorydetector.Common.gui;

import cn.todest.mcmod.inventorydetector.Common.config.Config;
import cn.todest.mcmod.inventorydetector.Common.event.InventoryDetectEventLoader;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.item.Item;

import java.util.Map;

public class ItemRecorderGUI extends AbstractGui {
    private final int width;
    private final int height;
    private final Minecraft minecraft;
    //    private final ResourceLocation HUD = new ResourceLocation("bagpack-detector", "textures/gui/hud.png");
    private final MatrixStack matrixStack;

    public ItemRecorderGUI(MatrixStack matrixStack) {
        this.width = Minecraft.getInstance().getMainWindow().getScaledWidth();
        this.height = Minecraft.getInstance().getMainWindow().getScaledHeight();
        this.minecraft = Minecraft.getInstance();
        this.matrixStack = matrixStack;
    }

    public void render() {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
//        this.minecraft.getTextureManager().bindTexture(HUD);
//        blit(matrixStack, width / 2 - 16, height / 2 - 64, 0, 0, 32, 32, 32, 32);
        Map<Item, Integer> ItemCount = InventoryDetectEventLoader.ItemCount;
        if (ItemCount == null) {
            return;
        }
        int typesNum = 0, maxNameLength = 0, maxCountLength = 0, count;
        String name;
        for (Item item : ItemCount.keySet()) {
            name = item.getName().getString();
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
            name = item.getName().getString();
            count = ItemCount.get(item);
            if (count == 0) {
                continue;
            }
            int x4 = width - 10; // ]
            int x2 = x4 - maxCountLength * 8; // [
            int x3 = (x2 + x4) / 2 + 1; // count
            int x1 = x2 - maxNameLength * 5; // name
            drawString(matrixStack, this.minecraft.fontRenderer,
                    name + ": ", x1, height / 10 + typesNum * 10, Config.NameColor.get()
            );
            drawString(matrixStack, this.minecraft.fontRenderer,
                    "[", x2, height / 10 + typesNum * 10, Config.BracketsColor.get()
            );
            drawString(matrixStack, this.minecraft.fontRenderer,
                    "]", x4, height / 10 + typesNum * 10, Config.BracketsColor.get()
            );
            drawCenteredString(matrixStack, this.minecraft.fontRenderer,
                    String.valueOf(count), x3, height / 10 + typesNum * 10, Config.CountColor.get()
            );
            typesNum++;
        }
    }
}
