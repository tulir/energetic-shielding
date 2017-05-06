package net.maunium.energeticshielding.blocks;

import net.minecraft.block.Block;

import net.minecraftforge.oredict.OreDictionary;

import cpw.mods.fml.common.registry.GameRegistry;

public final class MauBlocks {
	public static Block blockConductiveIron, blockTranslocatable, blockEnderDiamond;

	public static final void init() {
		GameRegistry.registerBlock(blockConductiveIron = new ConductiveIronBlock(), "blockConductiveIron");
		OreDictionary.registerOre("blockConductiveIron", blockConductiveIron);
		GameRegistry.registerBlock(blockTranslocatable = new TranslocatableBlock(), "blockTranslocatable");
		GameRegistry.registerBlock(blockEnderDiamond = new EnderInfusedDiamondBlock(), "blockEnderDiamond");
		OreDictionary.registerOre("blockEnderDiamond", blockEnderDiamond);

	}
}
