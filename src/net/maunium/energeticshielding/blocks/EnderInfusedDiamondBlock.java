package net.maunium.energeticshielding.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import net.maunium.energeticshielding.EnergeticShielding;

public class EnderInfusedDiamondBlock extends Block {

	protected EnderInfusedDiamondBlock() {
		super(Material.rock);
		this.setBlockName("blockEnderInfusedDiamond");
		this.setBlockTextureName(EnergeticShielding.MODID + ":ender_infused_diamond_block");
		this.setCreativeTab(EnergeticShielding.tab);
		this.setHardness(5.0F);
		this.setResistance(30.0F);
		this.setHarvestLevel("pickaxe", 3);
		this.setStepSound(soundTypeStone);
	}

}
