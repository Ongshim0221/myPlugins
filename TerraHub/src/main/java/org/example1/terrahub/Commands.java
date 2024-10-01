package org.example1.terrahub;

import net.milkbowl.vault.chat.Chat;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.example1.terrahub.TerraHub.*;

public class Commands implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender Sender, Command command, String str, String[] args) {
        if (Sender instanceof Player) {
            Player p = (Player) Sender;
            String ChunkName = p.getLocation().getChunk().getX() + "" + p.getLocation().getChunk().getZ();
            if (command.getName().equals("tr")) {
                if (CChunk.getString("Language").equals("en")) {

                    if (args[0].equals("add")) {
                        TerraHub.getEconomy().depositPlayer(p, 1000);
                    }

                    if (args[0].equals("buy")) {
                        // 특정 청크에 주인이 있는 지 확인
                        if (CChunk.get(ChunkName + ".owner") == null) {
                            // 특정 금액 이상일 때 구매 가능
                            if (TerraHub.getEconomy().has(p, Config.getInt("StartPrice"))) {
                                TerraHub.getEconomy().withdrawPlayer(p, Config.getInt("StartPrice"));

                                CChunk.set(ChunkName + ".owner", p.getUniqueId());
                                CChunk.set(ChunkName + ".cost", Config.getInt("StartPrice"));
                                TerraHub.saveChunk();

                                p.sendMessage(ChatColor.GREEN + "청크 구매가 완료되었습니다.");
                            }else {
                                p.sendMessage(ChatColor.RED + "돈이 부족합니다. (" + (Config.getInt("StartPrice") - TerraHub.getEconomy().getBalance(p)) + "원 부족)");
                            }
                        }else {
                            p.sendMessage(ChatColor.RED + "이미 주인이 있는 청크입니다. (" + p.getServer().getPlayer((UUID) CChunk.get(ChunkName + ".owner")).getName() + "님)");
                        }
                    }

                    if (args[0].equals("sell")) {
                        // 땅의 주인 일 때
                        if (CChunk.get(ChunkName + ".owner") == p.getUniqueId()) {
                            TerraHub.getEconomy().depositPlayer(p, CChunk.getInt(ChunkName + ".cost") * 0.20);
                            p.sendMessage(ChatColor.GREEN + ChunkName + "땅을 판매하셨습니다. +" + CChunk.getInt(ChunkName + ".cost") * 0.20);
                            CChunk.set(ChunkName, null);
                            saveChunk();
                        }else {
                            p.sendMessage(ChatColor.RED + "소유주가 없거나 자신의 땅이 아닙니다.");
                        }
                    }

                    if (args[0].equals("transfer")) {
                        // 땅의 주인 일 때
                        if (CChunk.get(ChunkName + ".owner") == p.getUniqueId()) {
                            TerraHub.getEconomy().depositPlayer(p, CChunk.getInt(ChunkName + ".cost") * 0.20);
                            p.sendMessage(ChatColor.GREEN + ChunkName + "땅을 판매하셨습니다. +" + CChunk.getInt(ChunkName + ".cost") * 0.20);
                            CChunk.set(ChunkName, null);
                            saveChunk();
                        }else {
                            p.sendMessage(ChatColor.RED + "소유주가 없거나 자신의 땅이 아닙니다.");
                        }
                    }

                }else if (CChunk.getString("Language").equals("kr")) {
                    if (args[0].equals("추가")) {
                        TerraHub.getEconomy().depositPlayer(p, 1000);
                    }

                    if (args[0].equals("구매")) {
                        // 특정 청크에 주인이 있는 지 확인
                        if (CChunk.get(ChunkName + ".owner") == null) {
                            // 특정 금액 이상일 때 구매 가능
                            if (TerraHub.getEconomy().has(p, Config.getInt("StartPrice"))) {
                                TerraHub.getEconomy().withdrawPlayer(p, Config.getInt("StartPrice"));

                                CChunk.set(ChunkName + ".owner", p.getUniqueId());
                                CChunk.set(ChunkName + ".cost", Config.getInt("StartPrice"));
                                TerraHub.saveChunk();

                                p.sendMessage(ChatColor.GREEN + "청크 구매가 완료되었습니다.");
                            }else {
                                p.sendMessage(ChatColor.RED + "돈이 부족합니다. (" + (Config.getInt("StartPrice") - TerraHub.getEconomy().getBalance(p)) + "원 부족)");
                            }
                        }else {
                            p.sendMessage(ChatColor.RED + "이미 주인이 있는 청크입니다. (" + p.getServer().getPlayer((UUID) CChunk.get(ChunkName + ".owner")).getName() + "님)");
                        }
                    }

                    if (args[0].equals("판매")) {
                        // 땅의 주인 일 때
                        if (CChunk.get(ChunkName + ".owner") == p.getUniqueId()) {
                            TerraHub.getEconomy().depositPlayer(p, CChunk.getInt(ChunkName + ".cost") * 0.20);
                            p.sendMessage(ChatColor.GREEN + ChunkName + "땅을 판매하셨습니다. +" + CChunk.getInt(ChunkName + ".cost") * 0.20);
                            CChunk.set(ChunkName, null);
                            saveChunk();
                        }else {
                            p.sendMessage(ChatColor.RED + "소유주가 없거나 자신의 땅이 아닙니다.");
                        }
                    }

                    if (args[0].equals("양도")) {
                        // 땅의 주인 일 때
                        if (CChunk.get(ChunkName + ".owner") == p.getUniqueId()) {
                            if (args[1] != null && p.getServer().getPlayer(args[1]) != null) {
                                CChunk.set(ChunkName + ".owner", p.getServer().getPlayer(args[1]).getUniqueId());
                                p.sendMessage(ChatColor.GREEN + p.getServer().getPlayer(args[1]).getName() + "님에게 성공적으로 " + ChunkName + "을 양도 하였습니다.");
                                saveChunk();
                            }else {
                                p.sendMessage(ChatColor.RED + "양도받을 플레이어가 존재하지 않습니다.");
                            }
                        }else {
                            p.sendMessage(ChatColor.RED + "자신의 땅이 아닙니다.");
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender Sender, Command command, String str, String[] args) {
        if (Sender instanceof Player) {
            if (command.getName().equals("tr")) {
                if (CChunk.getString("Language").equals("kr")) {
                    if (args.length == 1) {
                        return Arrays.asList("add", "buy", "sell", "transfer");
                    }
                }else if (CChunk.getString("Language").equals("en")) {
                    if (args.length == 1) {
                        return Arrays.asList("추가", "구매", "판매", "양도");
                    }
                }

            }
        }
        return List.of();
    }
}
