package cn.todest.mcmod.inventorydetector.Common.command;

import cn.todest.mcmod.inventorydetector.Common.event.InventoryDetectEventLoader;
import cn.todest.mcmod.inventorydetector.Common.utils.Utils;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.TranslationTextComponent;

public class DisableCommand implements Command<CommandSource> {
    public static DisableCommand instance = new DisableCommand();

    @Override
    public int run(CommandContext<CommandSource> context) {
        InventoryDetectEventLoader.setCanceled(true, true);
        context.getSource().sendFeedback(new TranslationTextComponent("cmd." + Utils.MOD_ID + ".disable"), false);
        return 0;
    }
}
