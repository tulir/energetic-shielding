package net.maunium.energeticshielding.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import net.maunium.energeticshielding.EnergeticShielding;
import net.maunium.energeticshielding.tile.TileProtected;

import cofh.lib.util.position.BlockPosition;

public class ItemLockCoating extends AbstractLockingItem {
	public ItemLockCoating() {
		super();
		this
				.setUnlocalizedName("lockCoating")
				.setTextureName(EnergeticShielding.texture("lock_coating"))
				.setCreativeTab(EnergeticShielding.tab);
	}

	@Override
	public boolean protectArea(ItemStack stack, EntityPlayer player, World world, BlockPosition pos) {
		if (!pos.blockExists(world) || world.isAirBlock(pos.x, pos.y, pos.z)) {
			return false;
		}

		TileEntity te = pos.getTileEntity(world);
		boolean solid = world.isBlockNormalCubeDefault(pos.x, pos.y, pos.z, true);
		if (te == null && (player.isSneaking() || solid)) {
			// int[] wandFriends = ItemIdentityCard.getFriendHashes(stack, player);
			int[] wandFriends = new int[] { player.getUniqueID().toString().hashCode() };

			this.protectBlock(world, pos, wandFriends);
			player.swingItem();
			stack.stackSize--;
			// Sound effect can be played here
			return true;
		} else if (te != null && te instanceof TileProtected) {
			TileProtected tile = (TileProtected) te;
			if (tile.canBeEditedBy(player)) {
				this.unprotectBlock(world, pos);
				player.swingItem();
				// Sound effect can be played here
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
