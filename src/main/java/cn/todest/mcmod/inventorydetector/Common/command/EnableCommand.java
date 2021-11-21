package cn.todest.mcmod.inventorydetector.Common.command;

import cn.todest.mcmod.inventorydetector.Common.event.InventoryDetectEventLoader;
import cn.todest.mcmod.inventorydetector.Common.utils.Utils;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.TranslationTextComponent;

public class EnableCommand implements Command<CommandSource> {
    public static EnableCommand instance = new EnableCommand();

    @Override
    public int run(CommandContext<CommandSource> context) {
        InventoryDetectEventLoader.setCanceled(false, true);
        InventoryDetectEventLoader.syncPrevious();
        context.getSource().sendFeedback(new TranslationTextComponent("cmd." + Utils.MOD_ID + ".enable"), false);
        return 0;
    }
}
