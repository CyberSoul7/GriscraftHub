package me.cybersoul.hub;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class PlayerListener implements Listener {
	
	HubPlugin plugin;
	
	public PlayerListener(HubPlugin plugin) {
		this.plugin = plugin;
	}
	
	//On Join Event
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		
		Player player = e.getPlayer();
		
		//Go to Spawn
		if (plugin.getConfig().get("spawn") instanceof Location) {
			player.teleport((Location) plugin.getConfig().get("spawn"));
		}
		
		//Send them a message
		player.sendMessage(ChatColor.DARK_AQUA + "Welcome to " + ChatColor.GREEN + "Griscraft!");
		player.setGameMode(GameMode.ADVENTURE);
		
		//Remove join message
		e.setJoinMessage(null);
		
	}
	
	//On Quit Event
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		//Remove quit message
		e.setQuitMessage(null);
	}
	
	/* Double Jump */
	
	//On Toggle Flight Event
	@EventHandler
	public void onToggleFlight(PlayerToggleFlightEvent e) {
		
		Player player = e.getPlayer();
		
		//Do nothing if player is Creative
		if (player.getGameMode() == GameMode.CREATIVE) return;
		
		//Not Flying
		e.setCancelled(true);
		player.setAllowFlight(false);
		player.setFlying(false);
		
		//Change player's velocity
		player.setVelocity(player.getLocation().getDirection().multiply(1.5).setY(1));
	}
	
	//On Player Move
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		
		Player player = e.getPlayer();
		if (player.getGameMode() != GameMode.CREATIVE &&
				player.getLocation().subtract(0, 1, 0).getBlock().getType() != Material.AIR &&
				!player.isFlying()) {
			player.setAllowFlight(true);
		}
		
	}
	
	//Cancel entity damage
	@EventHandler
	public void onEntityDamage(EntityDamageEvent e) {
		
		if (e.getEntity() instanceof Player /*|| e.getEntity() instanceof Villager*/) {
			if (e.getCause() == DamageCause.SUICIDE) return;
			
			e.setCancelled(true);
		}
	}
	
	//Cancel all hunger loss
	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent e) {
		e.setFoodLevel(20);
	}
	
	@EventHandler
    public void onEntitySpawn(EntitySpawnEvent e) {
        
        if (!(e.getEntity() instanceof Villager)) return;
        
        Villager v = (Villager) e.getEntity();
        v.setSilent(true);
        v.setInvulnerable(true);
        
    }
	
	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent e) {
		
		if (!(e.getInventory().getType() == InventoryType.MERCHANT)) return;
		e.setCancelled(true);
		
	}
	
}
