package net.maunium.energeticshielding.proxy;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.world.World;

import net.maunium.energeticshielding.EnergeticShielding;
import net.maunium.energeticshielding.MauCrafting;
import net.maunium.energeticshielding.block.MauBlocks;
import net.maunium.energeticshielding.interop.Wrench;
import net.maunium.energeticshielding.item.MauItems;
import net.maunium.energeticshielding.tile.MauTiles;

import net.minecraftforge.common.util.ForgeDirection;

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
		MauTiles.init();
	}

	public void init(FMLInitializationEvent e) {
		MauCrafting.init();
	}

	public void postInit(FMLPostInitializationEvent e) {
		Wrench.init();
	}

	public void blockProtectedFX(World world, double x, double y, double z, ForgeDirection side, float f, float f1,
			float f2) {
	}
}
