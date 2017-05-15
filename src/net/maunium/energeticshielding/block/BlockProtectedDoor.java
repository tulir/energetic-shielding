package net.maunium.energeticshielding.block;

import java.util.Random;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.IconFlipped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.maunium.energeticshielding.EnergeticShielding;
import net.maunium.energeticshielding.item.MauItems;
import net.maunium.energeticshielding.tile.TileOwned;

public class BlockProtectedDoor extends BlockDoor implements ITileEntityProvider {
	public IIcon topDoorIcon;
	public IIcon[] flippedIcons = new IIcon[2];

	protected BlockProtectedDoor() {
		super(Material.wood);
	}

	@Override
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		if (side == 1 || side == 0) {
			return this.blockIcon;
		}
		int meta = world.getBlockMetadata(x, y, z);
		boolean flag = (meta & 4) != 0;
		int halfMeta = meta & 3;
		boolean flipped = false;

		if (flag) {
			if (halfMeta == 0 && side == 2) {
				flipped = !flipped;
			} else if (halfMeta == 1 && side == 5) {
				flipped = !flipped;
			} else if (halfMeta == 2 && side == 3) {
				flipped = !flipped;
			} else if (halfMeta == 3 && side == 4) {
				flipped = !flipped;
			}
		} else {
			if (halfMeta == 0 && side == 5) {
				flipped = !flipped;
			} else if (halfMeta == 1 && side == 3) {
				flipped = !flipped;
			} else if (halfMeta == 2 && side == 4) {
				flipped = !flipped;
			} else if (halfMeta == 3 && side == 2) {
				flipped = !flipped;
			}
			if ((meta & 16) != 0) {
				flipped = !flipped;
			}
		}

		if (flipped) {
			return this.flippedIcons[meta != 0 ? 1 : 0];
		} else {
			return meta != 0 ? this.topDoorIcon : this.blockIcon;
		}
	}

	@Override
	public IIcon getIcon(int mystery1, int mystery2) {
		return this.blockIcon;
	}

	@Override
	public void registerBlockIcons(IIconRegister reg) {
		this.blockIcon = reg.registerIcon(EnergeticShielding.texture("protected_door_lower"));
		this.topDoorIcon = reg.registerIcon(EnergeticShielding.texture("protected_door_upper"));
		this.flippedIcons[0] = new IconFlipped(this.blockIcon, true, false);
		this.flippedIcons[1] = new IconFlipped(this.topDoorIcon, true, false);
	}

	public int idPicked(World world, int x, int y, int z) {
		return Item.getIdFromItem(MauItems.protectedDoor);
	}

	public int idDropped(int mystery, Random rand, int meta) {
		return meta != 0 ? 0 : Item.getIdFromItem(MauItems.protectedDoor);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX,
			float hitY, float hitZ) {
		if (world.isRemote) {
			return false;
		}
		TileEntity te;
		if ((world.getBlockMetadata(x, y, z) & 8) != 0) {
			te = world.getTileEntity(x, y - 1, z);
		} else {
			te = world.getTileEntity(x, y, z);
		}
		if (te == null || !(te instanceof TileOwned)) {
			player.addChatMessage(new ChatComponentText("The key to that door is lost!"));
			return false;
		}

		TileOwned tile = (TileOwned) te;
		if (tile.canBeAccessedBy(player)) {
			return super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
		}
		player.addChatMessage(new ChatComponentText("The door is locked!"));
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if ((meta & 8) != 0) {
			return null;
		}
		return new TileOwned();
	}
}
