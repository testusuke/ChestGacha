package net.testusuke.chestgacha.chestgacha.Event;

import net.testusuke.chestgacha.chestgacha.ChestGacha;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PlayerEventClass implements Listener {
    private final ChestGacha plugin;

    public PlayerEventClass(ChestGacha plugin) {
        this.plugin = plugin;
    }

    //////////////
    //  Event   //
    //////////////

    //  PlayerJoin
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent ev){
        Player p = ev.getPlayer();

        plugin.playerInfo.savePlayerStatus(p);
    }

    //  PlayerQuit
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent ev){
        Player p = ev.getPlayer();
        plugin.playerInfo.removePlayerStatus(p);
    }

    //  PlayerInteractEvent
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent ev){

        if(ev.getAction() == Action.RIGHT_CLICK_BLOCK){
            //  BlockCheck
            if(ev.getClickedBlock().getState() instanceof Chest){
                //  GetChest
                Block block = ev.getClickedBlock();
                Chest chest = (Chest) block.getState();
                Inventory chestinv = chest.getInventory();

                //  Player
                Player p = ev.getPlayer();

                //  ChestName
                if(!(chestinv.getName().equalsIgnoreCase(plugin.ChestName))){
                    return;
                }
                //  ２度回してないか
                if(plugin.PlayerStatus.get(p)){
                    p.sendMessage(plugin.prefix + "§c１度に２回まわせません!");
                    ev.setCancelled(true);
                    return;
                }
                //  OP
                if(p.getInventory().getItemInMainHand().getType() == Material.STICK && p.isOp()){
                    p.sendMessage(plugin.prefix + "§ガチャ設定用チェストを開きます");
                    return;
                }
                //  hasItem
                if(!(p.getInventory().getItemInMainHand().isSimilar(chestinv.getItem(26)))){
                    p.sendMessage(plugin.prefix + "§c必要なアイテムを持っていません！");
                    ev.setCancelled(true);
                    return;
                }
                //  hasItemInt
                if(!(p.getInventory().getItemInMainHand().getAmount() >= chestinv.getItem(26).getAmount())){
                    p.sendMessage(plugin.prefix + "§c個数が足りません!");
                    ev.setCancelled(true);
                    return;
                }

                //  LoadItem
                List<ItemStack> itemList = new ArrayList<ItemStack>();
                for (int i = 0; i < 24; i++){
                    if(chestinv.getItem(i) == null || chestinv.getItem(i).getType() == Material.AIR){
                        continue;
                    }
                    itemList.add(chestinv.getItem(i));
                }
                if(itemList.size() <= 0){
                    p.sendMessage(plugin.prefix + "§cこのガチャは現在使用できません");
                    ev.setCancelled(true);
                    return;
                }

                ev.setCancelled(true);

                int amount = p.getInventory().getItemInMainHand().getAmount();
                p.getInventory().getItemInMainHand().setAmount(amount - chestinv.getItem(26).getAmount());
                plugin.run.startGacha(chestinv,p,4,5*4);
                plugin.PlayerStatus.put(p,true);
            }
        }


    }


    //  ClickInventory
    @EventHandler
    public void onClick(InventoryClickEvent ev){
        Player p = (Player) ev.getWhoClicked();
        Inventory inv = ev.getInventory();

        //  Name
        if(!(inv.getName().equals(plugin.GachaGUI))){
            return;
        }

        ev.setCancelled(true);
    }

    //  BlockBreak
    @EventHandler
    public void onBlockBreak(BlockBreakEvent ev) {
        Player p = ev.getPlayer();

        if (!(ev.getBlock().getState() instanceof Chest)) {
            return;
        }

        Block block = ev.getBlock();
        Chest chest = (Chest) block.getState();
        Inventory inv = chest.getInventory();

        if (!(inv.getName().equals(plugin.ChestName))) {
            return;
        }

        if (!(p.isOp())) {
            p.sendMessage(plugin.prefix + "§cあなたには壊せません");
            ev.setCancelled(true);
            return;
        }

        p.sendMessage(plugin.prefix + "§6ガチャを壊しました");

    }


}
