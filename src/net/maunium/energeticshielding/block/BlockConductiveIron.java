package net.maunium.energeticshielding.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;

import net.maunium.energeticshielding.EnergeticShielding;

public class BlockConductiveIron extends Block {
	protected BlockConductiveIron() {
		super(Material.iron);
		this
				.setBlockName("blockConductiveIron")
				.setBlockTextureName(EnergeticShielding.MODID + ":conductive_iron_block")
				.setCreativeTab(EnergeticShielding.tab)
				.setHardness(5.0F)
				.setResistance(30.0F)
				.setStepSound(soundTypeMetal)
				.setHarvestLevel("pickaxe", 2);
	}

	@Override
	public boolean isBeaconBase(IBlockAccess worldObj, int x, int y, int z, int beaconX, int beaconY, int beaconZ) {
		return true;
	}
}
