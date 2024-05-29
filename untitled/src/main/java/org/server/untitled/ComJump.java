package org.server.untitled;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ComJump implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player player) {
            Block targetBlock = player.getTargetBlock(null, 100);
            if (targetBlock.getType() != Material.AIR) {
                player.teleport(targetBlock.getLocation());
            }
            return false;
        }
        return false;
    }
    }
