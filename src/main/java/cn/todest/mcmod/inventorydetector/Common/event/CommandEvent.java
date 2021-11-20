package cn.todest.mcmod.inventorydetector.Common.event;

import cn.todest.mcmod.inventorydetector.Common.command.ModCommand;
import cn.todest.mcmod.inventorydetector.Common.utils.Utils;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
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
        LiteralCommandNode<CommandSource> cmd = dispatcher.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("reset")
                                .requires((commandSource) -> commandSource.hasPermissionLevel(0))
                                .executes(ModCommand.instance)
                )
        );
        dispatcher.register(Commands.literal("id").redirect(cmd));
    }
}
