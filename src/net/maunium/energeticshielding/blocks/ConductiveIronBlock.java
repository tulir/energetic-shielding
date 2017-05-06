package net.maunium.energeticshielding.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import net.maunium.energeticshielding.EnergeticShielding;

public class ConductiveIronBlock extends Block {

	protected ConductiveIronBlock() {
		super(Material.iron);
		this.setBlockName("blockConductiveIron");

		this.setBlockTextureName(EnergeticShielding.MODID + ":conductive_iron_block");
		this.setCreativeTab(EnergeticShielding.tab);
		this.setHardness(5.0F);
		this.setResistance(30.0F);
		this.setHarvestLevel("pickaxe", 2);
		this.setStepSound(soundTypeMetal);
	}

}
