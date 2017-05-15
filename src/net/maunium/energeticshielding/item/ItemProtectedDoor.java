package net.maunium.energeticshielding.item;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import net.maunium.energeticshielding.EnergeticShielding;
import net.maunium.energeticshielding.block.MauBlocks;
import net.maunium.energeticshielding.tile.TileOwned;

public class ItemProtectedDoor extends ItemDoor {
	public ItemProtectedDoor() {
		super(Material.wood);
		this.setUnlocalizedName("protectedDoor").setCreativeTab(EnergeticShielding.tab);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
			float hitX, float hitY, float hitZ) {
		if (side != 1) {
			return false;
		} else {
			y++;
			if (player.canPlayerEdit(x, y, z, side, stack) && player.canPlayerEdit(x, y + 1, z, side, stack)) {
				if (!MauBlocks.blockProtectedDoor.canPlaceBlockAt(world, x, y, z)) {
					return false;
				} else {
					int placeSide = MathHelper.floor_double((player.rotationYaw + 180.0F) * 4.0F / 360.0F - 0.5D) & 3;
					placeProtectedDoor(world, x, y, z, placeSide);
					stack.stackSize--;

					TileEntity te = world.getTileEntity(x, y, z);
					if (te != null && te instanceof TileOwned) {
						TileOwned tile = (TileOwned) te;
						tile.addOwner(player);
						world.markBlockForUpdate(x, y, z);
					}
					return true;
				}
			} else {
				return false;
			}
		}
	}

	public static void placeProtectedDoor(World world, int x, int y, int z, int side) {
		Block block = MauBlocks.blockProtectedDoor;
		byte sideX = 0;
		byte sideZ = 0;

		if (side == 0) {
			sideZ = 1;
		}

		if (side == 1) {
			sideX = -1;
		}

		if (side == 2) {
			sideZ = -1;
		}

		if (side == 3) {
			sideX = 1;
		}

		int i1 = (world.getBlock(x - sideX, y, z - sideZ).isNormalCube() ? 1 : 0)
				+ (world.getBlock(x - sideX, y + 1, z - sideZ).isNormalCube() ? 1 : 0);
		int j1 = (world.getBlock(x + sideX, y, z + sideZ).isNormalCube() ? 1 : 0)
				+ (world.getBlock(x + sideX, y + 1, z + sideZ).isNormalCube() ? 1 : 0);
		boolean flag = world.getBlock(x - sideX, y, z - sideZ) == block
				|| world.getBlock(x - sideX, y + 1, z - sideZ) == block;
		boolean flag1 = world.getBlock(x + sideX, y, z + sideZ) == block
				|| world.getBlock(x + sideX, y + 1, z + sideZ) == block;
		boolean flag2 = false;

		if (flag && !flag1) {
			flag2 = true;
		} else if (j1 > i1) {
			flag2 = true;
		}

		world.setBlock(x, y, z, block, side, 2);
		world.setBlock(x, y + 1, z, block, 8 | (flag2 ? 1 : 0), 2);

		world.notifyBlocksOfNeighborChange(x, y, z, block);
		world.notifyBlocksOfNeighborChange(x, y + 1, z, block);
	}
}
