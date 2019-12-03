package net.testusuke.chestgacha.chestgacha;

import net.testusuke.chestgacha.chestgacha.Command.CommandClass;
import net.testusuke.chestgacha.chestgacha.Event.PlayerEventClass;
import net.testusuke.chestgacha.chestgacha.Manager.CustomConfig;
import net.testusuke.chestgacha.chestgacha.Module.PlayerInfo;
import net.testusuke.chestgacha.chestgacha.Module.RunGacha;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class ChestGacha extends JavaPlugin {

    ////////////
    //  変数  //
    ////////////

    //  Prefix
    public String prefix = "§e[ChestGacha]";

    //  ChestName
    public String ChestName = prefix + "§6ガチャ設定";

    //  GachaGUI
    public String GachaGUI = prefix;

    //  クラス変数
    public PlayerInfo playerInfo = new PlayerInfo(this);
    public RunGacha run = new RunGacha(this);

    //  PlayerStatus
    public HashMap<Player, Boolean> PlayerStatus = new HashMap<Player, Boolean>();

    //  Config
    public CustomConfig config;


    @Override
    public void onEnable() {
        // Plugin startup logic
        //  Logger
        getLogger().info("==============================");
        getLogger().info("Plugin: ChestGacha");
        getLogger().info("Ver: 1.0.0  Author: testusuke");
        getLogger().info("==============================");

        //  Command
        getCommand("chestgacha").setExecutor(new CommandClass(this));
        getCommand("cgacha").setExecutor(new CommandClass(this));

        //  Event
        getServer().getPluginManager().registerEvents(new PlayerEventClass(this), this);

        /*
        //  Vault
        new VaultManager(this);
        */

        //  Config
        config = new CustomConfig(this);

        //  SaveDefaultConfig
        config.saveDefaultConfig();

        //  PlayerStatus
        playerInfo.loadPlayer();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        //  Logger
        getLogger().info("[ChestGacha] Disable");

        //  CleanPlayer
        playerInfo.clenPlayer();
    }
}
