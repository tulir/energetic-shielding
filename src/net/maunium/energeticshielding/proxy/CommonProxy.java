package net.maunium.energeticshielding.proxy;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import net.maunium.energeticshielding.EnergeticShielding;
import net.maunium.energeticshielding.MauCrafting;
import net.maunium.energeticshielding.block.MauBlocks;
import net.maunium.energeticshielding.interop.Wrench;
import net.maunium.energeticshielding.item.MauItems;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
	public void preInit(FMLPreInitializationEvent e) {
		EnergeticShielding.tab = new CreativeTabs("energeticShielding") {
			@Override
			public Item getTabIconItem() {
				return MauItems.ingotConductiveIron;
			}
		};
		MauItems.init();
		MauBlocks.init();
	}

	public void init(FMLInitializationEvent e) {
		MauCrafting.init();
	}

	public void postInit(FMLPostInitializationEvent e) {
		Wrench.init();
	}
}
