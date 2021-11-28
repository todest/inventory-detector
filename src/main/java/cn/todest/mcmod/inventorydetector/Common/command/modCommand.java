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
        return "inventory-detector <enable|disable|reset|auto>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("enable")) {
                DetectorEvent.isCanceledPermanent = false;
                notifyOperators(sender, this, "Enabled Success!");
            } else if (args[0].equalsIgnoreCase("disable")) {
                DetectorEvent.isCanceledPermanent = true;
                notifyOperators(sender, this, "Disabled Success!");
            } else if (args[0].equalsIgnoreCase("reset")) {
                DetectorEvent.ItemCount.clear();
                notifyOperators(sender, this, "Reset Success!");
            } else if (args[0].equalsIgnoreCase("auto")) {
                if (args.length > 1) {
                    if (args[1].equalsIgnoreCase("true")) {
                        if (!DetectorEvent.isAutomatic) {
                            DetectorEvent.isAutomatic = true;
                            DetectorEvent.isCanceledPermanent = true;
                        }
                        notifyOperators(sender, this, "Automatic is Enabled!");
                    } else if (args[1].equalsIgnoreCase("false")) {
                        if (DetectorEvent.isAutomatic) {
                            DetectorEvent.isAutomatic = false;
                            DetectorEvent.isCanceledPermanent = false;
                        }
                        notifyOperators(sender, this, "Automatic is Disabled!");
                    }
                } else {
                    throw new WrongUsageException("inventory-detector auto <true|false>");
                }
            }
        } else {
            throw new WrongUsageException(getCommandUsage(sender));
        }
    }
}
