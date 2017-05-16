package net.maunium.energeticshielding.block;

import net.minecraft.block.Block;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;

import cpw.mods.fml.common.registry.GameRegistry;

public final class MauBlocks {
	public static Block blockConductiveIron, blockTranslocatable, blockEnderDiamond, blockShinyElectrum,
			blockDestabilizedShinyElectrum, blockProtected, blockFluidStar;
	public static BlockProtectedDoor blockProtectedDoor;
	public static Fluid fluidStar;

	public static final void init() {
		GameRegistry.registerBlock(blockConductiveIron = new BlockConductiveIron(), "blockConductiveIron");
		OreDictionary.registerOre("blockConductiveIron", blockConductiveIron);
		GameRegistry.registerBlock(blockTranslocatable = new BlockTranslocatable(), BlockTranslocatableItem.class,
				"blockTranslocatable");
		GameRegistry.registerBlock(blockEnderDiamond = new BlockEnderDiamond(), "blockEnderDiamond");
		OreDictionary.registerOre("blockEnderDiamond", blockEnderDiamond);
		GameRegistry.registerBlock(blockShinyElectrum = new BlockShinyElectrum(), "blockShinyElectrum");
		OreDictionary.registerOre("blockShinyElectrum", blockShinyElectrum);
		GameRegistry.registerBlock(blockDestabilizedShinyElectrum = new BlockDestabilizedShinyElectrum(),
				"blockDestabilizedShinyElectrum");
		OreDictionary.registerOre("blockDestabilizedShinyElectrum", blockDestabilizedShinyElectrum);
		GameRegistry.registerBlock(blockProtected = new BlockProtected(), "blockProtected");
		FluidRegistry.registerFluid(fluidStar = new Fluid("fluidStar"));
		GameRegistry.registerBlock(blockFluidStar = new BlockFluidStar(fluidStar), "blockFluidStar");
		GameRegistry.registerBlock(blockProtectedDoor = new BlockProtectedDoor(), "blockProtectedDoor");
	}
}
