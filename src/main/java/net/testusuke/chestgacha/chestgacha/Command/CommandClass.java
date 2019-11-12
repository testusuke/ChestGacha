package net.testusuke.chestgacha.chestgacha.Command;

import net.testusuke.chestgacha.chestgacha.ChestGacha;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CommandClass implements CommandExecutor {

    private final ChestGacha plugin;

    public CommandClass(ChestGacha plugin) {
        this.plugin = plugin;
    }

    //////////////
    //  Command //
    //////////////
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //  CheckPlayer
        if(!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "Don't use console!");
            return false;
        }

        if(args.length == 0){
            Player p = (Player) sender;
            if(p.isOp()){
                p.sendMessage(plugin.prefix);
                p.sendMessage("§6/cgacha <- Help表示");
                p.sendMessage("§6/cgacha chest <- 設定用のチェストの取得");
                p.sendMessage("§aガチャの作成方法");
                p.sendMessage("§a1 設定用のチェストを設置します");
                p.sendMessage("§a2 棒を持ってチェストを開きます(棒でないと開けません)");
                p.sendMessage("§a3 回すためのアイテムを右下にセットします");
                p.sendMessage("§a4 流れてくるアイテムは右下の３つ以外なら設定可能です");
                p.sendMessage("§a5 右下の２つ目のスロットに名札に確率を書くと確率が設定可能です。(その確率は左上のアイテムに適応されます)");
                p.sendMessage("§a確率：仕組みはそのアイテムが出たときに設定した確率で次のアイテムを出すことが可能です");
                p.sendMessage("§a確率の設定方法は 10 などの整数を入れてください。10だと10分の1という風になります。");
                p.sendMessage("§a値は大きすぎるとバグを起こす場合があるので100以下を推奨します");
                p.sendMessage("§a※設定しなくても構いません");
            }
            return true;
        }

        if(args.length == 1){
            Player p = (Player)sender;

            //  GetChest
            if(args[0].equalsIgnoreCase("chest")){

                if(!(p.isOp())){
                    p.sendMessage(plugin.prefix  +"§cあなたには権限がありません!");
                    return false;
                }

                p.sendMessage(plugin.prefix + "§a登録用チェストを付与しました");

                ItemStack item = new ItemStack(Material.CHEST);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(plugin.ChestName);
                item.setItemMeta(meta);
                p.getInventory().addItem(item);

                return true;
            }

            return true;
        }

        return false;
    }

}
