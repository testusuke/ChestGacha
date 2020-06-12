package net.testusuke.chestgacha.chestgacha;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryUnity {

    public static Inventory getGachaInventory(){
        Inventory inv = Bukkit.createInventory((InventoryHolder)null,54,ChestGacha.getPlugin().GachaGUI);
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
        return  inv;
    }
}
