package cn.todest.mcmod.inventorydetector.Common.event;

import cn.todest.mcmod.inventorydetector.Common.command.*;
import cn.todest.mcmod.inventorydetector.Common.utils.Utils;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CommandEvent {
    @SubscribeEvent
    public static void onServerStaring(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSource> dispatcher = event.getDispatcher();
        dispatcher.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("reset")
                                .executes(ResetCommand.instance)
                )
        );
        dispatcher.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("reload")
                                .executes(ReloadCommand.instance)
                )
        );
        dispatcher.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("disable")
                                .executes(DisableCommand.instance)
                )
        );
        dispatcher.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("enable")
                                .executes(EnableCommand.instance)
                )
        );
        dispatcher.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("save")
                                .executes(SaveCommand.instance)
                )
        );
    }
}
