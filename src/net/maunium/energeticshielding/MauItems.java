package net.maunium.energeticshielding;

import net.minecraft.item.Item;

import net.minecraftforge.oredict.OreDictionary;

import cpw.mods.fml.common.registry.GameRegistry;

public final class MauItems {
	public static Item ingotConductiveIron, enderInfusedStick;
	public static Item lockingCircuit, advancedLockingCircuit, remoteLockingCircuit, advancedRemoteLockingCircuit;
	public static Item lockCoating;

	public static final void init() {
		ingotConductiveIron = new Item()
				.setUnlocalizedName("ingotConductiveIron")
				.setTextureName(EnergeticShielding.texture("conductive_iron_ingot"))
				.setCreativeTab(EnergeticShielding.tab);
		enderInfusedStick = new Item()
				.setUnlocalizedName("enderInfusedStick")
				.setTextureName(EnergeticShielding.texture("ender_infused_stick"))
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

		GameRegistry.registerItem(ingotConductiveIron, "ingotConductiveIron");
		OreDictionary.registerOre("ingotConductiveIron", ingotConductiveIron);
		GameRegistry.registerItem(enderInfusedStick, "enderInfusedStick");
		GameRegistry.registerItem(lockingCircuit, "lockingCircuit");
		GameRegistry.registerItem(advancedLockingCircuit, "advancedLockingCircuit");
		GameRegistry.registerItem(remoteLockingCircuit, "remoteLockingCircuit");
		GameRegistry.registerItem(advancedRemoteLockingCircuit, "advancedRemoteLockingCircuit");
		GameRegistry.registerItem(lockCoating, "lockCoating");

	}
}
