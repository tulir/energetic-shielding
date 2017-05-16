package net.maunium.energeticshielding.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import net.maunium.energeticshielding.block.MauBlocks;
import net.maunium.energeticshielding.tile.TileProtected;

import net.minecraftforge.common.util.ForgeDirection;

import cofh.lib.util.position.BlockPosition;

public abstract class AbstractLockingItem extends Item implements ILockingItem {
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer p, World w, int x, int y, int z, int side, float hitX,
			float hitY, float hitZ) {
		if (w.isRemote) {
			return false;
		}
		return this.protectArea(stack, p, w, new BlockPosition(x, y, z, ForgeDirection.getOrientation(side)));
	}

	@Override
	public void protectBlock(World world, BlockPosition pos, int[] friends) {
		Block block = pos.getBlock(world);
		int meta = world.getBlockMetadata(pos.x, pos.y, pos.z);
		int light = block.getLightValue(world, pos.x, pos.y, pos.z);
		world.setBlock(pos.x, pos.y, pos.z, MauBlocks.blockProtected, meta, 3);
		TileProtected tile = pos.getTileEntity(world, TileProtected.class);
		if (tile != null) {
			tile.block = block;
			tile.blockMeta = (byte) meta;
			tile.light = (byte) light;
			tile.owners = friends;
			world.markBlockForUpdate(pos.x, pos.y, pos.z);
		}
	}

	@Override
	public void unprotectBlock(World world, BlockPosition pos) {
		TileProtected tile = pos.getTileEntity(world, TileProtected.class);
		world.setBlock(pos.x, pos.y, pos.z, tile.block, tile.blockMeta, 3);
		world.markBlockForUpdate(pos.x, pos.y, pos.z);
	}

	public List<BlockPosition> getBlocksInRadius(int radius, EntityPlayer p, World w, BlockPosition center,
			boolean protect) {
		List<BlockPosition> blocks = new ArrayList<>();
		if (center.orientation == ForgeDirection.DOWN || center.orientation == ForgeDirection.UP) {
			for (int x = center.x - radius; x <= center.x + radius; x++) {
				for (int z = center.z - radius; z <= center.z + radius; z++) {
					BlockPosition pos = new BlockPosition(x, center.y, z);
					if (this.canProtectOrUnprotect(p, w, pos, protect)) {
						blocks.add(pos);
					}
				}
			}
		} else if (center.orientation == ForgeDirection.NORTH || center.orientation == ForgeDirection.SOUTH) {
			for (int x = center.x - radius; x <= center.x + radius; x++) {
				for (int y = center.y - radius; y <= center.y + radius; y++) {
					BlockPosition pos = new BlockPosition(x, y, center.z);
					if (this.canProtectOrUnprotect(p, w, pos, protect)) {
						blocks.add(pos);
					}
				}
			}
		} else if (center.orientation == ForgeDirection.EAST || center.orientation == ForgeDirection.WEST) {
			for (int z = center.z - radius; z <= center.z + radius; z++) {
				for (int y = center.y - radius; y <= center.y + radius; y++) {
					BlockPosition pos = new BlockPosition(center.x, y, z);
					if (this.canProtectOrUnprotect(p, w, pos, protect)) {
						blocks.add(pos);
					}
				}
			}
		}
		return blocks;
	}

	public boolean canProtect(EntityPlayer p, World w, BlockPosition pos) {
		return pos.getTileEntity(w) == null && w.isBlockNormalCubeDefault(pos.x, pos.y, pos.z, true);
	}

	public boolean canUnprotect(EntityPlayer p, World w, BlockPosition pos) {
		TileProtected tile = pos.getTileEntity(w, TileProtected.class);
		return tile != null && tile.canBeEditedBy(p);
	}

	public boolean canProtectOrUnprotect(EntityPlayer p, World w, BlockPosition pos, boolean protect) {
		if (protect) {
			return this.canProtect(p, w, pos);
		} else {
			return this.canUnprotect(p, w, pos);
		}
	}
}
