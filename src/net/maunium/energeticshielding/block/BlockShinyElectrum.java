package net.maunium.energeticshielding.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import net.maunium.energeticshielding.EnergeticShielding;

public class BlockShinyElectrum extends Block {

	protected BlockShinyElectrum() {
		super(Material.iron);
		this.setBlockName("blockShinyElectrum");

		this.setBlockTextureName(EnergeticShielding.MODID + ":shiny_electrum_block");
		this.setCreativeTab(EnergeticShielding.tab);
		this.setHardness(7.5F);
		this.setResistance(45.0F);
		this.setHarvestLevel("pickaxe", 3);
		this.setStepSound(soundTypeMetal);
	}
}
