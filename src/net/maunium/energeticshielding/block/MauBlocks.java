package net.maunium.energeticshielding.block;

import net.minecraft.block.Block;

import net.minecraftforge.oredict.OreDictionary;

import cpw.mods.fml.common.registry.GameRegistry;

public final class MauBlocks {
	public static Block blockConductiveIron, blockTranslocatable, blockEnderDiamond, blockShinyElectrum,
			blockDestabilizedShinyElectrum;

	public static final void init() {
		GameRegistry.registerBlock(blockConductiveIron = new BlockConductiveIron(), "blockConductiveIron");
		OreDictionary.registerOre("blockConductiveIron", blockConductiveIron);
		GameRegistry.registerBlock(blockTranslocatable = new BlockTranslocatable(), "blockTranslocatable");
		GameRegistry.registerBlock(blockEnderDiamond = new BlockEnderDiamond(), "blockEnderDiamond");
		OreDictionary.registerOre("blockEnderDiamond", blockEnderDiamond);
		GameRegistry.registerBlock(blockShinyElectrum = new BlockShinyElectrum(), "blockShinyElectrum");
		GameRegistry.registerBlock(blockDestabilizedShinyElectrum = new BlockDestabilizedShinyElectrum(),
				"blockDestabilizedShinyElectrum");
	}
}