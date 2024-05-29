package org.server.untitled;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scoreboard.*;

import java.util.List;

public class Eco implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player player) {
            if (strings.length == 0) {
                player.sendMessage("Помощь по команде:\n" +
                        "/emerald withdraw [сумма] - снять деньги с баланса\n" +
                        "/emerald deposit [сумма] - положить деньги в банк");
                return true;
            }

            if (strings[0].equalsIgnoreCase("withdraw")) {
                if (strings.length < 2) {
                    player.sendMessage("Укажите сумму для снятия!");
                    return true;
                } else if (strings.length >= 2) {
                    long amount = Long.parseLong(strings[1]);
                    long balance = Main.instance.bankMap.getOrDefault(player.getUniqueId(), 0L);
                    if (balance == 0) {
                        player.sendMessage("У вас нет денег");
                        return true;
                    }

                    long withdraw = Long.parseLong(strings[1]);
                    if (withdraw > balance) {
                        player.sendMessage("У вас нет столько денег");
                        return true;
                    }

                    ItemStack item = new ItemStack(Material.EMERALD, (int) withdraw);
                    player.getInventory().addItem(item);
                    player.sendMessage("Вы сняли со счета " + withdraw + " алмазов");
                    long oldValue = Main.instance.bankMap.getOrDefault(player.getUniqueId(), 0L);
                    Main.instance.bankMap.put(player.getUniqueId(), oldValue - withdraw);

                    Scoreboard finalBoard = player.getScoreboard();
                    Objective finalObjective = Main.instance.scoreboardMap.get(player.getUniqueId());
                    finalBoard.resetScores("§fБаланс: §a" + oldValue + "$");
                    Score score1 = finalObjective.getScore("§fБаланс: §a" + Main.instance.bankMap.getOrDefault(player.getUniqueId(), 0L) + "$");
                    score1.setScore(1);


                    return true;
                }
                }
            else if (strings[0].equalsIgnoreCase("deposit")) {
                if (strings.length < 2) {
                    player.sendMessage("Укажите сумму для депозита!");
                    return true;
                } else {
                    int amountToRemove = Integer.parseInt(strings[1]);
                    int amountRemoved = 0;
                    ItemStack[] items = player.getInventory().getContents();
                    for (int i = 0; i < items.length; i++) {
                        ItemStack item = items[i];
                        if (item != null && item.getType() == Material.EMERALD) {
                            int found = item.getAmount();
                            if (amountRemoved + found <= amountToRemove) {
                                amountRemoved += found;
                                player.getInventory().setItem(i, null);
                            } else {
                                int needToRemove = amountToRemove - amountRemoved;
                                item.setAmount(found - needToRemove);
                                amountRemoved += needToRemove;
                            }

                            if (amountRemoved >= amountToRemove) {
                                break;
                            }
                        }
                    }

                    if (amountRemoved == 0) {
                        player.sendMessage("У тебя нет алмазных блоков");
                    } else {
                        player.sendMessage("Вы положили " + amountRemoved + " денег в банк!");
                        long oldValue = Main.instance.bankMap.getOrDefault(player.getUniqueId(), 0L);
                        Main.instance.bankMap.put(player.getUniqueId(), oldValue + amountRemoved);


                        Scoreboard finalBoard = player.getScoreboard();
                        Objective finalObjective = Main.instance.scoreboardMap.get(player.getUniqueId());
                        finalBoard.resetScores("§fБаланс: §a" + oldValue + "$");
                        Score score1 = finalObjective.getScore("§fБаланс: §a" + Main.instance.bankMap.getOrDefault(player.getUniqueId(), 0L) + "$");
                        score1.setScore(1);

                        return true;
                    }
                }
            }
            }
            return false;
        }

        public void scoreboardSet(Player player) {
//            ScoreboardManager manager = Bukkit.getScoreboardManager();
//            Scoreboard board = manager.getNewScoreboard();
//            Objective objective = board.registerNewObjective("test", "dummy");
//            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
//            objective.setDisplayName("§a§lБАНК");
//            Score nickLine = objective.getScore("§fНикнейм: §a" + player.getName());
//            Score voidLine = objective.getScore("§f");
//            Main.instance.scoreboardMap.put(player.getUniqueId(), objective);
//            nickLine.setScore(2);
//            voidLine.setScore(3);
//
//            Scoreboard finalBoard = board;
//            Objective finalObjective = objective;
//            long oldValue = Main.instance.bankMap.getOrDefault(player.getUniqueId(), 0L);
//            finalBoard.resetScores("§fБаланс: §a" + oldValue + "$");
//            Score score1 = finalObjective.getScore("§fБаланс: §a" + Main.instance.bankMap.getOrDefault(player.getUniqueId(), 0L) + "$");
//            score1.setScore(1);
//
//            player.setScoreboard(board);
            player.sendMessage("Задокументирован скорборд банка");
        }
    }


