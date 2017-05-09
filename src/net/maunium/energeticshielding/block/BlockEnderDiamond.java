package net.maunium.energeticshielding.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;

import net.maunium.energeticshielding.EnergeticShielding;

public class BlockEnderDiamond extends Block {
	protected BlockEnderDiamond() {
		super(Material.rock);
		this.setBlockName("blockEnderDiamond");
		this.setBlockTextureName(EnergeticShielding.MODID + ":ender_infused_diamond_block");
		this.setCreativeTab(EnergeticShielding.tab);
		this.setHardness(5.0F);
		this.setResistance(30.0F);
		this.setHarvestLevel("pickaxe", 3);
		this.setStepSound(soundTypeStone);
	}

	@Override
	public boolean isBeaconBase(IBlockAccess worldObj, int x, int y, int z, int beaconX, int beaconY, int beaconZ) {
		return true;
	}
}
