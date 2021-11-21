package cn.todest.mcmod.inventorydetector.Common.command;

import cn.todest.mcmod.inventorydetector.Common.config.Config;
import cn.todest.mcmod.inventorydetector.Common.utils.Utils;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.TranslationTextComponent;

public class ReloadCommand implements Command<CommandSource> {
    public static ReloadCommand instance = new ReloadCommand();

    @Override
    public int run(CommandContext<CommandSource> context) {
        Config.bake();
        context.getSource().sendFeedback(new TranslationTextComponent("cmd." + Utils.MOD_ID + ".reload"), false);
        return 0;
    }
}
