package cn.todest.mcmod.inventorydetector.common.command;

import cn.todest.mcmod.inventorydetector.common.events.DetectorEvent;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;

public class ModCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "inventory-detector";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "inventory-detector <enable|disable|reset>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("enable")) {
                DetectorEvent.setCanceled(false, true);
                notifyOperators(sender, this, "Enabled Success!");
            } else if (args[0].equalsIgnoreCase("disable")) {
                DetectorEvent.setCanceled(true, true);
                notifyOperators(sender, this, "Disabled Success!");
            } else if (args[0].equalsIgnoreCase("reset")) {
                DetectorEvent.ItemCount.clear();
                notifyOperators(sender, this, "Reset Success!");
            }
        } else {
            throw new WrongUsageException(getCommandUsage(sender));
        }
    }
}
