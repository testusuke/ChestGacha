package net.testusuke.chestgacha.chestgacha.Module;

import net.testusuke.chestgacha.chestgacha.ChestGacha;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerInfo {
    private final ChestGacha plugin;

    public PlayerInfo(ChestGacha plugin) {
        this.plugin = plugin;
    }


    //  LoadPlayer
    public void loadPlayer() {
        for(Player player : Bukkit.getServer().getOnlinePlayers()) {
            savePlayerStatus(player);
        }
    }

    //  CleanPlayer
    public void cleanPlayer() {
        plugin.PlayerStatus.clear();
        plugin.getLogger().info("CleanPlayer");
    }

    //  SavePlayerStatus
    public void savePlayerStatus(Player p) {

        plugin.PlayerStatus.put(p,false);

        plugin.getLogger().info("SavePlayerStatus: add " + p.getName());
    }

    //  RemovePlayerStatus
    public void removePlayerStatus(Player p){

        plugin.PlayerStatus.remove(p);

        plugin.getLogger().info("RemovePlayerStatus: remove " + p.getName());
    }

}
