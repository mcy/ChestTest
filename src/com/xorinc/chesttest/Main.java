package com.xorinc.chesttest;

import static org.bukkit.Material.*;
import static org.bukkit.ChatColor.*;
import static org.bukkit.enchantments.Enchantment.*;

import java.util.Random;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.base.Supplier;
import com.octagami.mayhemlib.chest.UIClickListener;
import com.octagami.mayhemlib.chest.UICloseListener;
import com.octagami.mayhemlib.chest.UIInventory;
import com.octagami.mayhemlib.chest.UIInventory.UIBuilder;
import com.octagami.mayhemlib.chest.UIOpenListener;
import com.octagami.mayhemlib.item.ItemBuilder;


public class Main extends JavaPlugin{

	private UIBuilder ui = 
			new UIBuilder("Welcome to the UI test!", 9)
				.openWith(				
					new UIOpenListener() {

						@Override
						public void onOpen(UIInventory ui) {

							ui.getPlayer().sendMessage("I'm an open message!");
						}
					}
				)
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
					new ItemBuilder(REDSTONE)
						.named(DARK_GRAY + "Random UUID")
						.lore(GRAY + "Get a UUID unique to this UI instance!")
					.build(),
					
					new Supplier<UIClickListener>() {
						
						@Override
						public UIClickListener get() {
							
							return new UIClickListener() {

								String rand = UUID.randomUUID().toString().replace("-", "");
								
								@Override
								public ItemStack onClick(UIInventory ui, ItemStack item, int slot) {

									ui.getPlayer().sendMessage(GOLD + "This UUID is unique to this UI! " + rand);
									return item;
								}
							};
						}
					}
				)
				.with(
					new ItemBuilder(EMERALD)
						.named(GREEN + "Multiplying Emeralds")
						.lore(GRAY + "MONEH")
					.build(),
					
					new Supplier<UIClickListener>() {
						
						@Override
						public UIClickListener get() {
							
							return new UIClickListener() {

								int total = 1;
								
								@Override
								public ItemStack onClick(UIInventory ui, ItemStack item, int slot) {

									ui.getPlayer().getWorld().dropItemNaturally(ui.getPlayer().getLocation(), new ItemStack(EMERALD, total));
									total *= 2;
									return item;
								}
							};
						}
					}
				)				
				.with(
					new ItemBuilder(RAW_FISH, 1, (short) 2)
						.named(GOLD + "KITTEH")
						.lore(GRAY + "Get a free kitty!")
					.build(),
					
					new UIClickListener() {

						Random r = new Random();
						Ocelot.Type[] breeds = {Ocelot.Type.BLACK_CAT, Ocelot.Type.RED_CAT, Ocelot.Type.SIAMESE_CAT};
						
						@Override
						public ItemStack onClick(UIInventory ui, ItemStack item, int slot) {

							Player p = ui.getPlayer();
							
							Ocelot o = p.getWorld().spawn(p.getLocation().add(0, 10, 0), Ocelot.class);
							
							o.setTamed(true);
							o.setOwner(p);
							o.setCatType(breeds[r.nextInt(3)]);
							o.setBaby();
							o.setAgeLock(true);
							
							return item;
						}
					}
				)
				.with(
					new ItemBuilder(NETHER_STAR)
						.named(RED + "FIGHT THA WITHER")
						.lore(GRAY + "" + STRIKETHROUGH + "Make sure you're ready!")
						.lore(GRAY + "**disabled**")
					.build(),
					
					8
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
