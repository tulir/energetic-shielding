package net.maunium.energeticshielding.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;

import net.maunium.energeticshielding.EnergeticShielding;

public class BlockConductiveIron extends Block {
	protected BlockConductiveIron() {
		super(Material.iron);
		this.setBlockName("blockConductiveIron");

		this.setBlockTextureName(EnergeticShielding.MODID + ":conductive_iron_block");
		this.setCreativeTab(EnergeticShielding.tab);
		this.setHardness(5.0F);
		this.setResistance(30.0F);
		this.setHarvestLevel("pickaxe", 2);
		this.setStepSound(soundTypeMetal);
	}

	@Override
	public boolean isBeaconBase(IBlockAccess worldObj, int x, int y, int z, int beaconX, int beaconY, int beaconZ) {
		return true;
	}
}
