package net.maunium.energeticshielding.tile;

import java.util.Arrays;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class TileOwned extends BaseTile {
	public int[] owners = {}, accessList = {};

	@Override
	public boolean canUpdate() {
		return false;
	}

	@Override
	public boolean shouldRefresh(Block oldBlock, Block newBlock, int oldMeta, int newMeta, World world, int x, int y,
			int z) {
		return false;
	}

	public boolean canBeEditedBy(EntityPlayer p) {
		for (int hash : this.owners) {
			if (p.getUniqueID().toString().hashCode() == hash) {
				return true;
			}
		}
		return false;
	}

	public boolean canBeAccessedBy(EntityPlayer p) {
		if (this.canBeEditedBy(p)) {
			return true;
		}
		for (int hash : this.accessList) {
			if (p.getUniqueID().toString().hashCode() == hash) {
				return true;
			}
		}
		return false;
	}

	public void addOwner(EntityPlayer p) {
		this.owners = Arrays.copyOf(this.owners, this.owners.length + 1);
		this.owners[this.owners.length - 1] = p.getUniqueID().toString().hashCode();
	}

	public void grantAccess(EntityPlayer p) {
		this.accessList = Arrays.copyOf(this.accessList, this.accessList.length + 1);
		this.accessList[this.accessList.length - 1] = p.getUniqueID().toString().hashCode();
	}

	@Override
	public void readCustomNBT(NBTTagCompound tag) {
		this.owners = tag.getIntArray("Owners");
		this.accessList = tag.getIntArray("AccessList");
	}

	@Override
	public void writeCustomNBT(NBTTagCompound tag) {
		tag.setIntArray("Owners", this.owners);
		tag.setIntArray("AccessList", this.accessList);
	}
}
