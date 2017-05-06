package net.maunium.energeticshielding;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import net.maunium.energeticshielding.block.MauBlocks;
import net.maunium.energeticshielding.item.MauItems;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import cofh.thermalexpansion.util.crafting.TransposerManager;
import cofh.thermalfoundation.fluid.TFFluids;
import cpw.mods.fml.common.registry.GameRegistry;

public final class MauCrafting {
	public static final void init() {
		// @formatter:off
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MauBlocks.blockConductiveIron),
				"III",
				"III",
				"III",
				'I', "ingotConductiveIron"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MauItems.ingotConductiveIron),
				" R ",
				"RIR",
				" R ",
				'R', "dustRedstone",
				'I', "ingotIron"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MauBlocks.blockShinyElectrum),
				"III",
				"III",
				"III",
				'I', "ingotShinyElectrum"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MauBlocks.blockDestabilizedShinyElectrum),
				"III",
				"III",
				"III",
				'I', "ingotDestabilizedShinyElectrum"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MauItems.lockingCircuit),
				"GGG",
				"RSR",
				"GGG",
				'G', "ingotGold",
				'R', "dustRedstone",
				'S', "ingotSignalum"));
		GameRegistry.addRecipe(new ItemStack(MauBlocks.blockTranslocatable, 2),
				" E ",
				"ESE",
				" E ",
				'E', Items.ender_pearl,
				'S', Blocks.stonebrick);
		GameRegistry.addShapelessRecipe(new ItemStack(MauItems.lockCoating, 2),
				MauBlocks.blockTranslocatable, MauItems.remoteLockingCircuit);
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MauItems.advancedLockingCircuit),
				" R ",
				"DLD",
				"GGG",
				'R', "blockRedstone",
				'D', "gemDiamond",
				'L', MauItems.lockingCircuit,
				'G', "ingotGold"));
		
		GameRegistry.addRecipe(new ItemStack(MauItems.lockingWand),
				"  L",
				" E ",
				"E  ",
				'L', MauItems.advancedRemoteLockingCircuit,
				'E', MauItems.enderInfusedStick);
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MauItems.lockingWandCore, 1, 0),
				"SES",
				"EDE",
				"SES",
				'S', "ingotSilver",
				'E', Items.ender_pearl,
				'D', Items.diamond));
		
		GameRegistry.addRecipe(new ItemStack(MauItems.lockingWandCore, 1, 1),
				"CEC",
				"EDE",
				"CEC",
				'C', new ItemStack(MauItems.lockingWandCore, 1, 0),
				'E', Items.ender_pearl,
				'D', Blocks.diamond_block);
		TransposerManager.addFillRecipe(24000, new ItemStack(MauItems.lockingWandCore, 1, 1),
				new ItemStack(MauItems.lockingWandCore, 1, 2), new FluidStack(TFFluids.fluidGlowstone, 10000), false);
		
		GameRegistry.addRecipe(new ItemStack(MauItems.lockingWandCore, 1, 3),
				"CEC",
				"EDE",
				"CEC",
				'C', new ItemStack(MauItems.lockingWandCore, 1, 2),
				'E', MauBlocks.blockEnderDiamond,
				'D', MauBlocks.blockDestabilizedShinyElectrum);
		TransposerManager.addFillRecipe(20000, new ItemStack(MauItems.lockingWandCore, 1, 3),
				new ItemStack(MauItems.lockingWandCore, 1, 4), new FluidStack(TFFluids.fluidCryotheum, 5000), false);
		
		GameRegistry.addShapelessRecipe(new ItemStack(MauItems.lockingWand, 1, 1),
				MauItems.lockingWand, new ItemStack(MauItems.lockingWandCore, 1, 0));

		GameRegistry.addShapelessRecipe(new ItemStack(MauItems.lockingWand, 1, 2),
				MauItems.lockingWand, new ItemStack(MauItems.lockingWandCore, 1, 2));

		GameRegistry.addShapelessRecipe(new ItemStack(MauItems.lockingWand, 1, 3),
				MauItems.lockingWand, new ItemStack(MauItems.lockingWandCore, 1, 4));

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(MauItems.shinyElectrumBlend), "dustPlatinum", "dustGold", "dustSilver"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(MauItems.shinyElectrumBlend), "dustPlatinum", "dustElectrum", "dustElectrum"));
		GameRegistry.addSmelting(MauItems.shinyElectrumBlend, new ItemStack(MauItems.shinyElectrumIngot), 2.0f);
		TransposerManager.addFillRecipe(8000,
				new ItemStack(MauItems.shinyElectrumIngot),
				new ItemStack(MauItems.destabilizedShinyElectrumIngot),
				new FluidStack(TFFluids.fluidRedstone, 2000), false);
		
		

		TransposerManager.addFillRecipe(4000,
				new ItemStack(Items.stick),
				new ItemStack(MauItems.enderInfusedStick),
				new FluidStack(TFFluids.fluidEnder, 250), false);
		TransposerManager.addFillRecipe(8000,
				new ItemStack(MauItems.lockingCircuit),
				new ItemStack(MauItems.remoteLockingCircuit),
				new FluidStack(TFFluids.fluidEnder, 500), false);
		TransposerManager.addFillRecipe(12000,
				new ItemStack(MauItems.advancedLockingCircuit),
				new ItemStack(MauItems.advancedRemoteLockingCircuit),
				new FluidStack(TFFluids.fluidEnder, 1000), false);
		TransposerManager.addFillRecipe(20000,
				new ItemStack(Blocks.diamond_block),
				new ItemStack(MauBlocks.blockEnderDiamond),
				new FluidStack(TFFluids.fluidEnder, 2250), false);
		// @formatter:on
	}
}
