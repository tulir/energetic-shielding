package net.maunium.energeticshielding.block;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import net.maunium.energeticshielding.EnergeticShielding;

import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockFluidStar extends BlockFluidClassic {
	private IIcon still, flowing;

	public BlockFluidStar(Fluid f) {
		super(f, Material.water);
	}

	@Override
	public void registerBlockIcons(IIconRegister register) {
		this.still = register.registerIcon(EnergeticShielding.texture("star_still"));
		this.flowing = register.registerIcon(EnergeticShielding.texture("star_flowing"));
	}

	@Override
	public boolean canDisplace(IBlockAccess world, int x, int y, int z) {
		return true;
	}

}
