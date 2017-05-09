package net.maunium.energeticshielding.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.maunium.energeticshielding.EnergeticShielding;

public class BlockDestabilizedShinyElectrum extends Block {
	protected BlockDestabilizedShinyElectrum() {
		super(Material.iron);
		this
				.setBlockName("blockDestabilizedShinyElectrum")
				.setBlockTextureName(EnergeticShielding.MODID + ":destabilized_shiny_electrum_block")
				.setCreativeTab(EnergeticShielding.tab)
				.setHardness(7.5F)
				.setResistance(45.0F)
				.setStepSound(soundTypeMetal)
				.setHarvestLevel("pickaxe", 3);
	}

	@Override
	public void harvestBlock(World w, EntityPlayer p, int x, int y, int z, int meta) {
		if (w.rand.nextInt(5) != 0) {
			super.harvestBlock(w, p, x, y, z, meta);
		}
	}

	@Override
	public float getExplosionResistance(Entity e, World world, int x, int y, int z, double expX, double expY,
			double expZ) {
		return world.rand.nextFloat() * 30 + 30;
	}

	@Override
	public boolean isBeaconBase(IBlockAccess world, int x, int y, int z, int beaconX, int beaconY, int beaconZ) {
		return ((World) world).rand.nextInt(25) != 0;
	}
}
