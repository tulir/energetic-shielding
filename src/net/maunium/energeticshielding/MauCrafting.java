package net.maunium.energeticshielding;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import net.maunium.energeticshielding.blocks.MauBlocks;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

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
		
		// @formatter:on

		TransposerManager.addFillRecipe(4000, new ItemStack(Items.stick), new ItemStack(MauItems.enderInfusedStick),
				new FluidStack(TFFluids.fluidEnder, 250), false);
		TransposerManager.addFillRecipe(8000, new ItemStack(MauItems.lockingCircuit),
				new ItemStack(MauItems.remoteLockingCircuit), new FluidStack(TFFluids.fluidEnder, 500), false);
		TransposerManager.addFillRecipe(12000, new ItemStack(MauItems.advancedLockingCircuit),
				new ItemStack(MauItems.advancedRemoteLockingCircuit), new FluidStack(TFFluids.fluidEnder, 1000), false);
		TransposerManager.addFillRecipe(20000, new ItemStack(Blocks.diamond_block),
				new ItemStack(MauBlocks.blockEnderDiamond), new FluidStack(TFFluids.fluidEnder, 2250), false);
	}
}
