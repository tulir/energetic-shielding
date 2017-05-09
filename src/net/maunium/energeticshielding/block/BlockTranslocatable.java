package net.maunium.energeticshielding.block;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import net.maunium.energeticshielding.EnergeticShielding;
import net.maunium.energeticshielding.interop.Wrench;

import cofh.lib.util.position.BlockPosition;

public class BlockTranslocatable extends Block {
	protected BlockTranslocatable() {
		super(Material.rock);
		this
				.setBlockName("blockTranslocatable")
				.setBlockTextureName(EnergeticShielding.MODID + ":translocatable_block")
				.setCreativeTab(EnergeticShielding.tab)
				.setHardness(1.5F)
				.setResistance(30.0F)
				.setLightLevel(1.0F)
				.setStepSound(soundTypeStone)
				.setHarvestLevel("pickaxe", 2);
	}

	@Override
	public void onBlockClicked(World w, int bx, int by, int bz, EntityPlayer p) {
		if (w.isRemote) {
			super.onBlockClicked(w, bx, by, bz, p);
			return;
		}
		List<BlockPosition> freePoints = new ArrayList<BlockPosition>();
		for (int x = bx - 5; x <= bx + 5; x++) {
			for (int y = by - 5; y <= by + 5; y++) {
				for (int z = bz - 5; z <= bz + 5; z++) {
					if (w.isAirBlock(x, y, z)) {
						freePoints.add(new BlockPosition(x, y, z));
					}
				}
			}
		}
		if (freePoints.size() > 0) {
			BlockPosition pos = freePoints.get(w.rand.nextInt(freePoints.size()));
			w.setBlock(bx, by, bz, Blocks.air);
			w.setBlock(pos.x, pos.y, pos.z, this);
		} else {
			super.onBlockClicked(w, bx, by, bz, p);
		}
	}

	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer p, int side, float hitX, float hitY,
			float hitZ) {
		if (!w.isRemote && Wrench.isWrench(p.getHeldItem())) {
			this.harvestBlock(w, p, x, y, z, 0);
			w.setBlockToAir(x, y, z);
			return true;
		} else {
			return super.onBlockActivated(w, x, y, z, p, side, hitX, hitY, hitZ);
		}
	}

}
