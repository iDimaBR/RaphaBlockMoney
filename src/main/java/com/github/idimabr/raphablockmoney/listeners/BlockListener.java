package com.github.idimabr.raphablockmoney.listeners;

import com.github.idimabr.raphablockmoney.RaphaBlockMoney;
import com.github.idimabr.raphablockmoney.utils.ActionBar;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockListener implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onBlock(BlockBreakEvent e){
        Player player = e.getPlayer();
        Block block = e.getBlock();
        if(!player.getWorld().getName().equalsIgnoreCase(RaphaBlockMoney.config.getString("World"))) return;
        if(RaphaBlockMoney.blocks.containsKey(e.getBlock().getType())){
            int value = RaphaBlockMoney.blocks.get(e.getBlock().getType());
            e.setCancelled(true);
            e.getBlock().setType(Material.AIR);
            RaphaBlockMoney.economy.depositPlayer(player, value);
            new ActionBar(RaphaBlockMoney.config.getString("ActionBAR").replace("&","§").replace("%valor%", value+"").replace("%player%", player.getName()), player);
            if(RaphaBlockMoney.config.getBoolean("SoundEnabled"))
                block.getWorld().playSound(block.getLocation(), Sound.valueOf(RaphaBlockMoney.config.getString("Sound")), 0.2f, 0.2f);
        }
    }
}
