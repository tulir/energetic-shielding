package net.maunium.energeticshielding.block;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import net.maunium.energeticshielding.EnergeticShielding;
import net.maunium.energeticshielding.interop.Wrench;

import cofh.lib.util.position.BlockPosition;

public class BlockTranslocatable extends Block {
	public IIcon[] icons = new IIcon[3];

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
	public void registerBlockIcons(IIconRegister reg) {
		String textureName = EnergeticShielding.texture("translocatable_block");
		this.icons[0] = reg.registerIcon(textureName + "_wild");
		this.icons[1] = reg.registerIcon(textureName + "_unstable");
		this.icons[2] = reg.registerIcon(textureName + "_stable");
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return this.icons[meta % 3];
	}

	public BlockPosition teleportBlock(World world, BlockPosition pos) {
		List<BlockPosition> freePoints = new ArrayList<BlockPosition>();
		for (int x = pos.x - 5; x <= pos.x + 5; x++) {
			for (int y = pos.y - 5; y <= pos.y + 5; y++) {
				for (int z = pos.z - 5; z <= pos.z + 5; z++) {
					if (world.isAirBlock(x, y, z)) {
						freePoints.add(new BlockPosition(x, y, z));
					}
				}
			}
		}
		if (freePoints.size() > 0) {
			BlockPosition free = freePoints.get(world.rand.nextInt(freePoints.size()));
			int meta = world.getBlockMetadata(pos.x, pos.y, pos.z);
			world.setBlock(pos.x, pos.y, pos.z, Blocks.air);
			world.setBlock(free.x, free.y, free.z, this, meta, 3);
			return free;
		}
		return null;
	}

	@Override
	public void onBlockClicked(World world, int bx, int by, int bz, EntityPlayer p) {
		if (world.isRemote) {
			super.onBlockClicked(world, bx, by, bz, p);
			return;
		}
		BlockPosition pos = new BlockPosition(bx, by, bz);
		int meta = world.getBlockMetadata(bx, by, bz) % 3;
		if (meta == 0) {
			pos = this.teleportBlock(world, pos);
			if (pos == null) {
				return;
			}
			int pxFloor = (int) Math.floor(p.posX);
			int pyFloor = (int) Math.floor(p.posY);
			int pzFloor = (int) Math.floor(p.posZ);
			if (pxFloor == bx && pyFloor == by + 1 && pzFloor == bz) {
				p.setPositionAndUpdate(pos.x + p.posX - pxFloor, pos.y + 1 + p.posY - pyFloor,
						pos.z + p.posZ - pzFloor);
			}
		} else if (meta == 1) {
			for (int i = 0; i < world.rand.nextInt(10); i++) {
				pos = this.teleportBlock(world, pos);
				if (pos == null) {
					return;
				}
			}
		} else if (meta == 2) {
			// TODO teleport to player facing by a few blocks
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
