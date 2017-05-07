package net.maunium.energeticshielding;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import net.maunium.energeticshielding.block.MauBlocks;
import net.maunium.energeticshielding.item.MauItems;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import cofh.api.modhelpers.ThermalExpansionHelper;
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
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(MauItems.ingotConductiveIron, 9), "blockConductiveIron"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MauBlocks.blockEnderDiamond),
				"III",
				"III",
				"III",
				'I', "gemEnderDiamond"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(MauItems.gemEnderDiamond, 9), "blockEnderDiamond"));
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
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(MauItems.ingotShinyElectrum, 9), "blockShinyElectrum"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MauBlocks.blockDestabilizedShinyElectrum),
				"III",
				"III",
				"III",
				'I', "ingotDestabilizedShinyElectrum"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(MauItems.ingotDestabilizedShinyElectrum, 9), "blockDestabilizedShinyElectrum"));
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
		ThermalExpansionHelper.addTransposerFill(24000, new ItemStack(MauItems.lockingWandCore, 1, 1),
				new ItemStack(MauItems.lockingWandCore, 1, 2), new FluidStack(TFFluids.fluidGlowstone, 10000), false);

		GameRegistry.addRecipe(new ItemStack(MauItems.lockingWandCore, 1, 3),
				"CEC",
				"EDE",
				"CEC",
				'C', new ItemStack(MauItems.lockingWandCore, 1, 2),
				'E', MauBlocks.blockEnderDiamond,
				'D', MauBlocks.blockDestabilizedShinyElectrum);
		ThermalExpansionHelper.addTransposerFill(20000, new ItemStack(MauItems.lockingWandCore, 1, 3),
				new ItemStack(MauItems.lockingWandCore, 1, 4), new FluidStack(TFFluids.fluidCryotheum, 5000), false);

		GameRegistry.addShapelessRecipe(new ItemStack(MauItems.lockingWand, 1, 1),
				MauItems.lockingWand, new ItemStack(MauItems.lockingWandCore, 1, 0));

		GameRegistry.addShapelessRecipe(new ItemStack(MauItems.lockingWand, 1, 2),
				MauItems.lockingWand, new ItemStack(MauItems.lockingWandCore, 1, 2));

		GameRegistry.addShapelessRecipe(new ItemStack(MauItems.lockingWand, 1, 3),
				MauItems.lockingWand, new ItemStack(MauItems.lockingWandCore, 1, 4));

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(MauItems.dustShinyElectrum), "dustPlatinum", "dustGold", "dustSilver"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(MauItems.dustShinyElectrum), "dustPlatinum", "dustElectrum", "dustElectrum"));
		GameRegistry.addSmelting(MauItems.dustShinyElectrum, new ItemStack(MauItems.ingotShinyElectrum), 2.0f);
		ThermalExpansionHelper.addTransposerFill(8000,
				new ItemStack(MauItems.ingotShinyElectrum),
				new ItemStack(MauItems.ingotDestabilizedShinyElectrum),
				new FluidStack(TFFluids.fluidRedstone, 2000), false);



		ThermalExpansionHelper.addTransposerFill(4000,
				new ItemStack(Items.stick),
				new ItemStack(MauItems.enderInfusedStick),
				new FluidStack(TFFluids.fluidEnder, 250), false);
		ThermalExpansionHelper.addTransposerFill(8000,
				new ItemStack(MauItems.lockingCircuit),
				new ItemStack(MauItems.remoteLockingCircuit),
				new FluidStack(TFFluids.fluidEnder, 500), false);
		ThermalExpansionHelper.addTransposerFill(12000,
				new ItemStack(MauItems.advancedLockingCircuit),
				new ItemStack(MauItems.advancedRemoteLockingCircuit),
				new FluidStack(TFFluids.fluidEnder, 1000), false);
		ThermalExpansionHelper.addTransposerFill(20000,
				new ItemStack(Blocks.diamond_block),
				new ItemStack(MauBlocks.blockEnderDiamond),
				new FluidStack(TFFluids.fluidEnder, 2250), false);
		ThermalExpansionHelper.addTransposerFill(2600,
				new ItemStack(Items.diamond),
				new ItemStack(MauItems.gemEnderDiamond),
				new FluidStack(TFFluids.fluidEnder, 250), false);
		// @formatter:on
	}
}
