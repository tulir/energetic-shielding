package net.maunium.energeticshielding.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import cofh.lib.util.position.BlockPosition;

public interface ILockingItem {
	public boolean protectArea(ItemStack stack, EntityPlayer player, World world, BlockPosition pos);

	public void protectBlock(World world, BlockPosition pos, int[] friends);

	public void unprotectBlock(World world, BlockPosition pos);
}
