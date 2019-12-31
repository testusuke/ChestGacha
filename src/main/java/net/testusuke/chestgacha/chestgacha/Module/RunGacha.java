package net.testusuke.chestgacha.chestgacha.Module;

import net.testusuke.chestgacha.chestgacha.ChestGacha;
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

    public void startGacha(Inventory chestInv, Player p,int time){

        //  Inventory
        Inventory inv = Bukkit.createInventory((InventoryHolder) null, 45, plugin.GachaGUI);

        //  ItemSet
        ItemStack glassItem = new ItemStack(Material.STAINED_GLASS_PANE,1, (byte)5);
        ItemMeta Glassmeta = glassItem.getItemMeta();
        Glassmeta.setDisplayName("");
        glassItem.setItemMeta(Glassmeta);
        int[] glass = {0,1,2,3,5,6,7,8,9,10,11,12,14,15,16,17,18,19,20,24,25,26,27,28,29,30,32,33,34,35,36,37,38,39,41,42,43,44};
        for(int i = 0; i < glass.length; i++){
            inv.setItem(glass[i], glassItem);
        }
        //  エンドクリスタル
        ItemStack end = new ItemStack(Material.END_CRYSTAL);
        inv.setItem(21, end);
        inv.setItem(23, end);

        //  バリア
        ItemStack barrie = new ItemStack(Material.BARRIER);
        inv.setItem(4,barrie);
        inv.setItem(13,barrie);
        inv.setItem(22,barrie);
        inv.setItem(31,barrie);
        inv.setItem(40,barrie);

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
        runGacha(inv, p, itemList);

        /*
        ItemStack loreItem = chestInv.getItem(25);
        ItemMeta loreMeta = loreItem.getItemMeta();
        String lore = loreMeta.getDisplayName();
        */
        /*
        if(chestInv.getItem(25) != null && chestInv.getItem(25).getType() != Material.AIR){
            //  名札か
            ItemStack hitamount = chestInv.getItem(25);
            if(hitamount.getType() != Material.NAME_TAG){
                //  確率なし
                runGacha(inv, p, itemList);
            }
            //  名前があるか
            if(hitamount.getItemMeta().getDisplayName() == null){
                //  確率なし
                runGacha(inv, p, itemList);
            }
            //  0にアイテムがあるか
            if(chestInv.getItem(0) == null && chestInv.getItem(0).getType() == Material.AIR){
                //  確率なし
                runGacha(inv, p, itemList);
            }
            //  確率が数字か
            try{
                int hitInt = Integer.parseInt(chestInv.getItem(25).getItemMeta().getDisplayName());

                ItemStack superItem = chestInv.getItem(0);
                //  Run
                //  確率あり
                runGacha(inv, p, itemList, hitInt, superItem);

            }catch (NumberFormatException e){
                //  確率なし
                runGacha(inv, p, itemList);
            }

        }else {
            //  確率なし
            runGacha(inv, p, itemList);
        }*/

    }

    //  1 3

    public void runGacha(Inventory inv,Player p, List<ItemStack> itemList){


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

                if(i >= 50){
                    ItemStack hitItem = inv.getItem(22); //  景品
                    ItemMeta hitMeta = hitItem.getItemMeta();
                    p.getInventory().addItem(hitItem);
                    p.getWorld().playSound(location, Sound.ENTITY_ENDERDRAGON_AMBIENT, 1.0F, 1.0F);

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
        }.runTaskTimer(plugin,1,2);

    }

    /*
    public void runGacha(Inventory inv,Player p, List<ItemStack> itemList, int superhit, ItemStack superHitItem){


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

                if(i >= 50){
                    ItemStack hitItem = inv.getItem(22); //  景品

                    if(hitItem.equals(superHitItem)){
                        int hit = R.nextInt(superhit) + 1;
                        //  当たった場合
                        if(hit == 1){
                            ItemMeta hitMeta = hitItem.getItemMeta();
                            p.getInventory().addItem(hitItem);
                            p.getWorld().playSound(location, Sound.ENTITY_ENDERDRAGON_AMBIENT, 1.0F, 1.0F);

                            plugin.PlayerStatus.remove(p);
                            plugin.PlayerStatus.put(p, false);

                            cancel();


                            //ItemMeta loreMeta = loreItem.getItemMeta();
                            //String lore = loreMeta.getDisplayName().replace("&","§").replace("%item%", hitMeta.getDisplayName()).replace("%amount%", "" + hitItem.getAmount());


                            if (hitMeta.getDisplayName() == null) {
                                p.sendMessage(plugin.prefix + "§6おめでとうございます！§e" + hitItem.getType().name() + "§6が" + hitItem.getAmount() + "個当たりました");
                            } else {
                                p.sendMessage(plugin.prefix + "§6おめでとうございます！§e" + hitMeta.getDisplayName() + "§6が" + hitItem.getAmount() + "個当たりました");
                            }

                            return;
                        }
                    }else {
                        ItemMeta hitMeta = hitItem.getItemMeta();
                        p.getInventory().addItem(hitItem);
                        p.getWorld().playSound(location, Sound.ENTITY_ENDERDRAGON_AMBIENT, 1.0F, 1.0F);

                        plugin.PlayerStatus.remove(p);
                        plugin.PlayerStatus.put(p, false);

                        cancel();


                        //ItemMeta loreMeta = loreItem.getItemMeta();
                        //String lore = loreMeta.getDisplayName().replace("&","§").replace("%item%", hitMeta.getDisplayName()).replace("%amount%", "" + hitItem.getAmount());

                        if (hitMeta.getDisplayName() == null) {
                            p.sendMessage(plugin.prefix + "§6おめでとうございます！§e" + hitItem.getType().name() + "§6が" + hitItem.getAmount() + "個当たりました");
                        } else {
                            p.sendMessage(plugin.prefix + "§6おめでとうございます！§e" + hitMeta.getDisplayName() + "§6が" + hitItem.getAmount() + "個当たりました");
                        }

                        return;
                    }
                }
                i++;
            }
        }.runTaskTimer(plugin, 1,2);


    }*/
}
