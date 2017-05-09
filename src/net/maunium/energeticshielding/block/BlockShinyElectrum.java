package net.maunium.energeticshielding.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;

import net.maunium.energeticshielding.EnergeticShielding;

public class BlockShinyElectrum extends Block {
	protected BlockShinyElectrum() {
		super(Material.iron);
		this
				.setBlockName("blockShinyElectrum")
				.setBlockTextureName(EnergeticShielding.MODID + ":shiny_electrum_block")
				.setCreativeTab(EnergeticShielding.tab)
				.setHardness(7.5F)
				.setResistance(45.0F)
				.setStepSound(soundTypeMetal)
				.setHarvestLevel("pickaxe", 3);
	}

	@Override
	public boolean isBeaconBase(IBlockAccess worldObj, int x, int y, int z, int beaconX, int beaconY, int beaconZ) {
		return true;
	}
}
