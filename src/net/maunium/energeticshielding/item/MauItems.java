package net.maunium.energeticshielding.item;

import net.minecraft.item.Item;

import net.maunium.energeticshielding.EnergeticShielding;

import net.minecraftforge.oredict.OreDictionary;

import cpw.mods.fml.common.registry.GameRegistry;

public final class MauItems {
	public static Item ingotConductiveIron, gemEnderDiamond, dustShinyElectrum, ingotShinyElectrum,
			ingotDestabilizedShinyElectrum;
	public static Item lockingCircuit, advancedLockingCircuit, remoteLockingCircuit, advancedRemoteLockingCircuit,
			enderInfusedStick, lockCoating, lockingWand, lockingWandCore, identityCard;

	public static final void init() {
		ingotConductiveIron = new Item()
				.setUnlocalizedName("ingotConductiveIron")
				.setTextureName(EnergeticShielding.texture("conductive_iron_ingot"))
				.setCreativeTab(EnergeticShielding.tab);
		enderInfusedStick = new Item()
				.setUnlocalizedName("enderInfusedStick")
				.setTextureName(EnergeticShielding.texture("ender_infused_stick"))
				.setCreativeTab(EnergeticShielding.tab);
		gemEnderDiamond = new Item()
				.setUnlocalizedName("gemEnderDiamond")
				.setTextureName(EnergeticShielding.texture("ender_infused_diamond"))
				.setCreativeTab(EnergeticShielding.tab);

		lockingCircuit = new Item()
				.setUnlocalizedName("lockingCircuit")
				.setTextureName(EnergeticShielding.texture("locking_circuit"))
				.setCreativeTab(EnergeticShielding.tab);
		advancedLockingCircuit = new Item()
				.setUnlocalizedName("advancedLockingCircuit")
				.setTextureName(EnergeticShielding.texture("advanced_locking_circuit"))
				.setCreativeTab(EnergeticShielding.tab);
		remoteLockingCircuit = new Item()
				.setUnlocalizedName("remoteLockingCircuit")
				.setTextureName(EnergeticShielding.texture("remote_locking_circuit"))
				.setCreativeTab(EnergeticShielding.tab);
		advancedRemoteLockingCircuit = new Item()
				.setUnlocalizedName("advancedRemoteLockingCircuit")
				.setTextureName(EnergeticShielding.texture("advanced_remote_locking_circuit"))
				.setCreativeTab(EnergeticShielding.tab);

		lockCoating = new Item()
				.setUnlocalizedName("lockCoating")
				.setTextureName(EnergeticShielding.texture("lock_coating"))
				.setCreativeTab(EnergeticShielding.tab);

		dustShinyElectrum = new Item()
				.setUnlocalizedName("dustShinyElectrum")
				.setTextureName(EnergeticShielding.texture("shiny_electrum_blend"))
				.setCreativeTab(EnergeticShielding.tab);
		ingotShinyElectrum = new Item()
				.setUnlocalizedName("ingotShinyElectrum")
				.setTextureName(EnergeticShielding.texture("shiny_electrum_ingot"))
				.setCreativeTab(EnergeticShielding.tab);
		ingotDestabilizedShinyElectrum = new Item()
				.setUnlocalizedName("ingotDestabilizedShinyElectrum")
				.setTextureName(EnergeticShielding.texture("destabilized_shiny_electrum_ingot"))
				.setCreativeTab(EnergeticShielding.tab);

		GameRegistry.registerItem(lockingWand = new ItemLockingWand(), "lockingWand");
		GameRegistry.registerItem(lockingWandCore = new ItemLockingWandCore(), "lockingWandCore");
		GameRegistry.registerItem(identityCard = new ItemIdentityCard(), "identityCard");

		GameRegistry.registerItem(ingotConductiveIron, "ingotConductiveIron");
		OreDictionary.registerOre("ingotConductiveIron", ingotConductiveIron);
		GameRegistry.registerItem(gemEnderDiamond, "gemEnderDiamond");
		OreDictionary.registerOre("gemEnderDiamond", gemEnderDiamond);
		GameRegistry.registerItem(enderInfusedStick, "enderInfusedStick");
		GameRegistry.registerItem(lockingCircuit, "lockingCircuit");
		GameRegistry.registerItem(advancedLockingCircuit, "advancedLockingCircuit");
		GameRegistry.registerItem(remoteLockingCircuit, "remoteLockingCircuit");
		GameRegistry.registerItem(advancedRemoteLockingCircuit, "advancedRemoteLockingCircuit");
		GameRegistry.registerItem(lockCoating, "lockCoating");

		GameRegistry.registerItem(dustShinyElectrum, "dustShinyElectrum");
		OreDictionary.registerOre("dustShinyElectrum", dustShinyElectrum);
		GameRegistry.registerItem(ingotShinyElectrum, "ingotShinyElectrum");
		OreDictionary.registerOre("ingotShinyElectrum", ingotShinyElectrum);
		GameRegistry.registerItem(ingotDestabilizedShinyElectrum, "ingotDestabilizedShinyElectrum");
		OreDictionary.registerOre("ingotDestabilizedShinyElectrum", ingotDestabilizedShinyElectrum);

	}
}
