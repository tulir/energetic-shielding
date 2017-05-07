package net.maunium.energeticshielding.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public abstract class BaseTile extends TileEntity {
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		this.readCustomNBT(tag);
	}

	public abstract void readCustomNBT(NBTTagCompound tag);

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		this.writeCustomNBT(tag);
	}

	public abstract void writeCustomNBT(NBTTagCompound tag);

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound tag = new NBTTagCompound();
		this.writeCustomNBT(tag);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 64537, tag);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity updatePacket) {
		super.onDataPacket(net, updatePacket);
		this.readCustomNBT(updatePacket.func_148857_g());
	}
}
