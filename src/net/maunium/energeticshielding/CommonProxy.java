package net.maunium.energeticshielding;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import net.maunium.energeticshielding.blocks.MauBlocks;
import net.maunium.energeticshielding.interop.Wrench;

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
