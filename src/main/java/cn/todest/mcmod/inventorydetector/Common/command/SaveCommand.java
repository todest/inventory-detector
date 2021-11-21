package cn.todest.mcmod.inventorydetector.Common.command;

import cn.todest.mcmod.inventorydetector.Common.config.Config;
import cn.todest.mcmod.inventorydetector.Common.utils.Utils;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.TranslationTextComponent;

public class SaveCommand implements Command<CommandSource> {
    public static SaveCommand instance = new SaveCommand();

    @Override
    public int run(CommandContext<CommandSource> context) {
        Config.save();
        context.getSource().sendFeedback(new TranslationTextComponent("cmd." + Utils.MOD_ID + ".save"), false);
        return 0;
    }
}
