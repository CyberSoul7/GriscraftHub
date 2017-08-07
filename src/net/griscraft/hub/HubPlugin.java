package net.griscraft.hub;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class HubPlugin extends JavaPlugin {
	
	public void onEnable() {
		
		CustomEntities.registerEntities();
		
		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
	public void onDisable() {
		CustomEntities.unregisterEntities();
	}
	
	//Send Message
	public void sendMessage(MessageType type, String message) {
		
		String messageType = "";
		ChatColor color = ChatColor.RESET;
		
		switch(type) {
		case ANNOUNCEMENT:
			messageType = "ANNOUNCEMENT";
			color = ChatColor.GOLD;
			break;
		case IMPORTANT:
			messageType = "IMPORTANT";
			color = ChatColor.YELLOW;
			break;
		case WARNING:
			messageType = "WARNING";
			color = ChatColor.RED;
			break;
		case INFO:
			messageType = "INFO";
			color = ChatColor.GREEN;
			break;
		}
		
		for (Player players : Bukkit.getOnlinePlayers()) {
			players.sendMessage(color + "[" + messageType + "] " + ChatColor.DARK_AQUA + message);
		}
	}
	
	//Send Message to one player
	public void sendMessage(MessageType type, String message, Player player) {
		
		String messageType = "";
		ChatColor color = ChatColor.RESET;
		
		switch(type) {
		case ANNOUNCEMENT:
			messageType = "ANNOUNCEMENT";
			color = ChatColor.GOLD;
			break;
		case IMPORTANT:
			messageType = "IMPORTANT";
			color = ChatColor.YELLOW;
			break;
		case WARNING:
			messageType = "WARNING";
			color = ChatColor.RED;
			break;
		case INFO:
			messageType = "INFO";
			color = ChatColor.GREEN;
			break;
		}
		
		player.sendMessage(color + "[" + messageType + "] " + ChatColor.DARK_AQUA + message);
	}
	
	//Commands
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		//setspawn command; sets the world spawnpoint
		if (cmd.getName().equalsIgnoreCase("setspawn")) {
			
			if (!(sender instanceof Player)) {
				sender.sendMessage("You must be a player to run this command!");
				return true;
			}
			
			Player player = (Player) sender;
			if (!player.isOp()) {
				sendMessage(MessageType.WARNING, "You do not have permission to run this command!", player);
				return true;
			}
			
			Location spawn = player.getLocation();
			
			spawn.setX(Math.floor(spawn.getX()) + 0.5);
			spawn.setY(Math.floor(spawn.getY()));
			spawn.setZ(Math.floor(spawn.getZ()) + 0.5);
			
			spawn.setPitch(0);
			spawn.setYaw(Math.round(spawn.getYaw() / 45) * 45);
			
			getConfig().set("spawn", spawn);
			saveConfig();
			sendMessage(MessageType.INFO, "You have succesfully set the world spawn!", player);
			
			return true;
			
		}
		
		//spawn command; teleports the player to spawn
		if (cmd.getName().equalsIgnoreCase("spawn")) {
			
			if (!(sender instanceof Player)) {
				sender.sendMessage("You must be a player to run this command!");
				return true;
			}
			
			Player player = (Player) sender;
			
			if (!(getConfig().get("spawn") instanceof Location)) {
				sendMessage(MessageType.WARNING, "There is no spawn location set!", player);
				sendMessage(MessageType.WARNING, "If you believe this is incorrect, please contact a staff member!", player);
				return true;
			}
			
			player.teleport((Location) getConfig().get("spawn"));
			player.sendMessage(ChatColor.DARK_AQUA + "*Poof*");
			
			return true;
			
		}
		
		if (cmd.getName().equalsIgnoreCase("announce")) {
			
			if (args.length < 1) {
				if (sender instanceof Player) {
					sendMessage(MessageType.WARNING, "You must give a message!", (Player) sender);
					return true;
				}
				sender.sendMessage(ChatColor.RED + "You must give a message!");
				return true;
			}
			
			String msg = String.join(" ", args);
			sendMessage(MessageType.ANNOUNCEMENT, msg);
			
			return true;
			
		}
		
		if (cmd.getName().equalsIgnoreCase("warn")) {
			
			if (args.length < 1) {
				if (sender instanceof Player) {
					sendMessage(MessageType.WARNING, "You must give a message!", (Player) sender);
					return true;
				}
				sender.sendMessage(ChatColor.RED + "You must give a message!");
				return true;
			}
			
			String msg = String.join(" ", args);
			sendMessage(MessageType.WARNING, msg);
			
			return true;
			
		}
		
		if (cmd.getName().equalsIgnoreCase("info")) {
			
			if (args.length < 1) {
				if (sender instanceof Player) {
					sendMessage(MessageType.WARNING, "You must give a message!", (Player) sender);
					return true;
				}
				sender.sendMessage(ChatColor.RED + "You must give a message!");
				return true;
			}
			
			String msg = String.join(" ", args);
			sendMessage(MessageType.INFO, msg);
			
			return true;
			
		}
		
		if (cmd.getName().equalsIgnoreCase("important")) {
			
			if (args.length < 1) {
				if (sender instanceof Player) {
					sendMessage(MessageType.WARNING, "You must give a message!", (Player) sender);
					return true;
				}
				sender.sendMessage(ChatColor.RED + "You must give a message!");
				return true;
			}
			
			String msg = String.join(" ", args);
			sendMessage(MessageType.IMPORTANT, msg);
			
			return true;
			
		}
		
		return false;
		
	}
	
}
