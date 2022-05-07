package com.github.idimabr.raphablockmoney;

import com.github.idimabr.raphablockmoney.listeners.BlockListener;
import com.github.idimabr.raphablockmoney.utils.ConfigUtil;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;

public final class RaphaBlockMoney extends JavaPlugin {

    public static ConfigUtil config;
    public static Economy economy = null;
    public static HashMap<Material, Integer> blocks = new HashMap();


    @Override
    public void onEnable() {
        // Plugin startup logic
        config = new ConfigUtil(null, "config.yml", false);
        if (!setupEconomy()) {
            Bukkit.getLogger().info("[RaphaBlockMoney] Não foi encontrado o VAULT!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        loadOres();
        Bukkit.getPluginManager().registerEvents(new BlockListener(), this);
        config.saveConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        config.reloadConfig();
    }

    public static RaphaBlockMoney getInstance(){
        return getPlugin(RaphaBlockMoney.class);
    }

    public void loadOres(){
        if(!config.getConfig().isSet("ActionBAR")){
            config.set("ActionBAR", "&a+%valor% &fadicionados");
        }
        if(!config.getConfig().isSet("World")){
            config.set("World", "mundo");
        }
        if(!config.getConfig().isSet("Sound")){
            config.set("Sound", "NOTE_PLING");
        }
        if(!config.getConfig().isSet("SoundEnabled")){
            config.set("SoundEnabled", false);
        }

        for(String key : config.getConfigurationSection("Minerios").getKeys(true)){
            if(!config.getConfigurationSection("Minerios").contains(key)){
                config.getConfigurationSection("Minerios").addDefault(key, 10);
            }
            blocks.put(Material.valueOf(key), config.getInt("Minerios." + key));
        }
        System.out.println(blocks);
    }

    private boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }
}
