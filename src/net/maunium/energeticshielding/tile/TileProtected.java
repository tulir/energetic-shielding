package net.maunium.energeticshielding.tile;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import net.minecraftforge.common.util.Constants;

public class TileProtected extends BaseTile {
	public int[] owners = {};
	public Block block = Blocks.air;
	public byte blockMeta = 0;
	public boolean safeToRemove = false;
	public byte light;

	@Override
	public boolean canUpdate() {
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

	@Override
	public void readCustomNBT(NBTTagCompound tag) {
		this.block = (Block) Block.blockRegistry.getObject(tag.getString("BlockName"));
		this.blockMeta = tag.getByte("BlockMeta");
		this.light = tag.getByte("BlockLight");
		this.owners = tag.getIntArray("OwnerHashes");
		if (this.owners.length == 0) {
			NBTTagList tags = tag.getTagList("Owners", Constants.NBT.TAG_STRING);
			this.owners = new int[tags.tagCount()];
			for (int i = 0; i < tags.tagCount(); i++) {
				String uuid = tags.getStringTagAt(i);
				if (uuid != null) {
					this.owners[i] = uuid.hashCode();
				}
			}
		}
		if (this.block == null) {
			this.block = Blocks.stone;
		}
	}

	@Override
	public void writeCustomNBT(NBTTagCompound tag) {
		tag.setString("BlockName", Block.blockRegistry.getNameForObject(this.block));
		tag.setByte("BlockMeta", this.blockMeta);
		tag.setByte("BlockLight", this.light);
		tag.setIntArray("OwnerHashes", this.owners);
	}
}
