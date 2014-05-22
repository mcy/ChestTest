package com.xorinc.chesttest;

import static org.bukkit.Material.*;
import static org.bukkit.ChatColor.*;
import static org.bukkit.enchantments.Enchantment.*;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.octagami.mayhemlib.chest.UIClickListener;
import com.octagami.mayhemlib.chest.UICloseListener;
import com.octagami.mayhemlib.chest.UIInventory;
import com.octagami.mayhemlib.chest.UIInventory.UIBuilder;
import com.octagami.mayhemlib.item.ItemBuilder;


public class Main extends JavaPlugin{

	private UIBuilder ui = 
			new UIBuilder("Welcome to the UI test!", 9)
				.with(
					new ItemBuilder(GOLD_PICKAXE)
						.named(BLUE + "DIGGING!")
						.lore(GRAY + "Spawns an god pick.")
						.lore(RED + "" + ITALIC + "Works only once!")
					.build(),
					
					new UIClickListener() {

						private ItemStack pick = 
								new ItemBuilder(DIAMOND_PICKAXE)
									.named(GOLD + "Awakened Pickaxe")
									.lore(GRAY + "Mine all the blox!")
									.enchantedWith(DIG_SPEED, 10)
									.enchantedWith(SILK_TOUCH, 2)
								.build();
						
						@Override
						public ItemStack onClick(UIInventory ui, ItemStack item, int slot) {

							Player p = ui.getPlayer();
							
							p.getWorld().dropItemNaturally(p.getLocation(), pick.clone());
							p.sendMessage(GOLD + "Enjoy your new pick!");
							
							return null;
						}
					}
				)
				.with(
					new ItemBuilder(BOOK)
						.named(BLUE + "Hello, World!")
						.lore(GRAY + "???")
					.build(),
					
					new UIClickListener() {

						@Override
						public ItemStack onClick(UIInventory ui, ItemStack item, int slot) {

							ui.getPlayer().chat("Hello, World!");
							return item;
						}
					}
				)
				.with(
					new ItemBuilder(NETHER_STAR)
						.named(RED + "FIGHT THA WITHER")
						.lore(GRAY + "Make sure you're ready!")
					.build(),
					
					8,
					
					new UIClickListener() {

						@Override
						public ItemStack onClick(UIInventory ui, ItemStack item, int slot) {

							Player p = ui.getPlayer();
							
							p.getWorld().spawn(p.getLocation().add(0, 10, 0), Wither.class);
							
							return item;
						}
					}
				)
				.closeWith(
						
					new UICloseListener() {

						@Override
						public void onClose(UIInventory ui) {

							ui.getPlayer().sendMessage(GOLD + "" + ITALIC + "Come again soon!");
						}
					}
				);
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		
		if(sender instanceof Player)
			ui.open((Player) sender);
		
		return true;
		
	}
	
}
