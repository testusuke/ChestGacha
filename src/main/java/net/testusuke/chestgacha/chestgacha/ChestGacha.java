package net.testusuke.chestgacha.chestgacha;

import net.testusuke.chestgacha.chestgacha.Command.CommandClass;
import net.testusuke.chestgacha.chestgacha.Event.PlayerEventClass;
import net.testusuke.chestgacha.chestgacha.Module.PlayerInfo;
import net.testusuke.chestgacha.chestgacha.Module.RunGacha;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class ChestGacha extends JavaPlugin {

    ////////////
    //  変数  //
    ////////////

    //  Prefix
    public String prefix = "§e[ChestGacha]";
    public String version = "1.0.0";

    public static ChestGacha plugin = null ;
    //  ChestName
    public String ChestName = prefix + "§6ガチャ設定";

    //  GachaGUI
    public String GachaGUI = prefix;

    //  クラス変数
    public PlayerInfo playerInfo = new PlayerInfo(this);
    public RunGacha run = new RunGacha(this);

    //  PlayerStatus
    public HashMap<Player, Boolean> PlayerStatus = new HashMap<>();


    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        //  Logger
        getLogger().info("==============================");
        getLogger().info("Plugin: ChestGacha");
        getLogger().info("Ver: " + version + "  Author: testusuke");
        getLogger().info("==============================");
        //  Command
        getCommand("chestgacha").setExecutor(new CommandClass(this));
        getCommand("cgacha").setExecutor(new CommandClass(this));
        //  Event
        getServer().getPluginManager().registerEvents(new PlayerEventClass(this), this);
        //  Config
        this.saveDefaultConfig();
        //  PlayerStatus
        playerInfo.loadPlayer();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        //  Logger
        getLogger().info("[ChestGacha] Disable");

        //  CleanPlayer
        playerInfo.cleanPlayer();
    }

    public static ChestGacha getPlugin(){
        return plugin;
    }
}
