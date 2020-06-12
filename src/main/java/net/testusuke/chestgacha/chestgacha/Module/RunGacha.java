package net.testusuke.chestgacha.chestgacha.Module;

import net.testusuke.chestgacha.chestgacha.ChestGacha;
import net.testusuke.chestgacha.chestgacha.InventoryUnity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RunGacha {

    private final ChestGacha plugin;

    public RunGacha(ChestGacha plugin) {
        this.plugin = plugin;
    }

    public void startGacha(Inventory chestInv, Player p,int time,int count){
        //  Inventory
        Inventory inv = InventoryUnity.getGachaInventory();
        //  OpenINV
        p.openInventory(inv);
        //  LoadItem
        List<ItemStack> itemList = new ArrayList<ItemStack>();
        for (int i = 0; i < 24; i++){
            if(chestInv.getItem(i) == null || chestInv.getItem(i).getType() == Material.AIR){
                continue;
            }
            itemList.add(chestInv.getItem(i));
        }
        //  Run
        runGacha(inv, p, itemList,time,count);
    }


    public void runGacha(Inventory inv,Player p, List<ItemStack> itemList,int time,int count){

        int timeTicks = time * 20;
        int periodTicks = timeTicks / count;

        Random R = new Random();
        new BukkitRunnable() {
            int i = 0;

            @Override
            public void run() {

                ItemStack itemStack = itemList.get(R.nextInt(itemList.size()));
                //  getItem
                ItemStack itemStack1 = inv.getItem(4);
                ItemStack itemStack2 = inv.getItem(13);
                ItemStack itemStack3 = inv.getItem(22);
                ItemStack itemStack4 = inv.getItem(31);

                //  setItem
                inv.setItem(4, itemStack);
                inv.setItem(13, itemStack1);
                inv.setItem(22, itemStack2);
                inv.setItem(31, itemStack3);
                inv.setItem(40, itemStack4);


                Location location = p.getLocation();
                p.getWorld().playSound(location, Sound.ENTITY_SHULKER_HURT, 0.1F, 1.0F);

                if(i >= count){
                    ItemStack hitItem = inv.getItem(22); //  景品
                    ItemMeta hitMeta = hitItem.getItemMeta();
                    p.getInventory().addItem(hitItem);
                    p.getWorld().playSound(location, Sound.ENTITY_ENDERDRAGON_AMBIENT, 1.0F, 1.0F);
                    //  Status
                    plugin.PlayerStatus.remove(p);
                    plugin.PlayerStatus.put(p,false);
                    cancel();
                    /*
                    ItemMeta loreMeta = loreItem.getItemMeta();
                    String lore = loreMeta.getDisplayName().replace("&","§").replace("%item%", hitMeta.getDisplayName()).replace("%amount%", "" + hitItem.getAmount());
                    */
                    if(hitMeta.getDisplayName() == null){
                        p.sendMessage(plugin.prefix + "§6おめでとうございます！§e" + hitItem.getType().name() + "§6が" + hitItem.getAmount() + "個当たりました");
                    }else{
                        p.sendMessage(plugin.prefix + "§6おめでとうございます！§e" + hitMeta.getDisplayName() + "§6が" + hitItem.getAmount() + "個当たりました");
                    }

                    return;
                }
                i++;
            }
        }.runTaskTimer(plugin,5,periodTicks);

    }
}
