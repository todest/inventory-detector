package cn.todest.mcmod.inventorydetector.Common.command;

import cn.todest.mcmod.inventorydetector.Common.event.InventoryDetectEventLoader;
import cn.todest.mcmod.inventorydetector.Common.utils.Utils;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.TranslationTextComponent;

public class ModCommand implements Command<CommandSource> {
    public static ModCommand instance = new ModCommand();

    @Override
    public int run(CommandContext<CommandSource> context) {
        InventoryDetectEventLoader.ItemCount = null;
        context.getSource().sendFeedback(new TranslationTextComponent("cmd." + Utils.MOD_ID + ".reset"), false);
        return 0;
    }
}
