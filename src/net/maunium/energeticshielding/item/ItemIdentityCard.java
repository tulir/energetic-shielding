package net.maunium.energeticshielding.item;

import java.util.Arrays;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import net.maunium.energeticshielding.EnergeticShielding;

import net.minecraftforge.common.util.Constants;

public class ItemIdentityCard extends Item {
	public ItemIdentityCard() {
		super();
		this
				.setHasSubtypes(true)
				.setUnlocalizedName("identityCard")
				.setCreativeTab(EnergeticShielding.tab)
				.setTextureName(EnergeticShielding.texture("identity_card"));
	}

	public void click(ItemStack stack, EntityPlayer player, EntityPlayer target) {
		target.swingItem();
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		NBTTagCompound tag = stack.getTagCompound();
		NBTTagList list = tag.getTagList("Friends", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound oldFriend = list.getCompoundTagAt(i);
			if (oldFriend.getString("UUID").equals(target.getUniqueID().toString())) {
				if (!oldFriend.getString("Name").equals(target.getDisplayName())) {
					oldFriend.setString("Name", target.getDisplayName());
				}
				return;
			}
		}
		NBTTagCompound friend = new NBTTagCompound();
		friend.setString("Name", target.getDisplayName());
		friend.setString("UUID", target.getUniqueID().toString());
		list.appendTag(friend);
		player.addChatComponentMessage(new ChatComponentText("Added " + target.getDisplayName() + " to friend list!"));
	}

	public static int[] getFriendHashes(ItemStack stack, EntityPlayer player) {
		if (!stack.hasTagCompound()) {
			return new int[] { player.getUniqueID().toString().hashCode() };
		} else {
			NBTTagCompound tag = stack.getTagCompound();
			NBTTagList list = tag.getTagList("Friends", Constants.NBT.TAG_COMPOUND);
			if (list.tagCount() == 0) {
				return new int[] { player.getUniqueID().toString().hashCode() };
			} else {
				int selfHash = player.getUniqueID().toString().hashCode();
				boolean selfIsOwner = false;
				int[] friends = new int[list.tagCount()];
				for (int i = 0; i < list.tagCount(); i++) {
					String uuid = list.getCompoundTagAt(i).getString("UUID");
					if (uuid == null || uuid.length() == 0) {
						continue;
					}
					friends[i] = uuid.hashCode();
					if (!selfIsOwner && friends[i] == selfHash) {
						selfIsOwner = true;
					}
				}
				if (!selfIsOwner) {
					friends = Arrays.copyOf(friends, friends.length + 1);
					friends[friends.length - 1] = selfHash;
				}
				return friends;
			}
		}
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		if (!stack.hasTagCompound()) {
			return;
		}
		NBTTagCompound tag = stack.getTagCompound();
		NBTTagList tagList = tag.getTagList("Friends", Constants.NBT.TAG_COMPOUND);
		if (tagList.tagCount() > 0) {
			list.add("Friends:");
		} else {
			list.add("No friends :(");
		}
		for (int i = 0; i < tagList.tagCount(); i++) {
			list.add("- " + tagList.getCompoundTagAt(i).getString("Name"));
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (world.isRemote) {
			return super.onItemRightClick(stack, world, player);
		}
		if (player.isSneaking()) {
			this.click(stack, player, player);
			return stack;
		}
		return super.onItemRightClick(stack, world, player);
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target) {
		if (player.worldObj.isRemote) {
			return super.itemInteractionForEntity(stack, player, target);
		}
		if (target instanceof EntityPlayer) {
			this.click(stack, player, (EntityPlayer) target);
			return true;
		}
		return super.itemInteractionForEntity(stack, player, target);
	}

	public static final IRecipe cardDuplication = new ShapelessRecipes(new ItemStack(MauItems.lockingWand), Arrays
			.asList(new ItemStack[] { new ItemStack(MauItems.lockingWand), new ItemStack(MauItems.identityCard) })) {
		@Override
		public ItemStack getCraftingResult(InventoryCrafting crafting) {
			ItemStack card1 = null, card2 = null;
			for (int i = 0; i < crafting.getSizeInventory(); i++) {
				ItemStack stack = crafting.getStackInSlot(i);
				if (stack != null && stack.getItem() instanceof ItemIdentityCard) {
					if (card1 == null) {
						card1 = stack;
					} else if (card2 == null) {
						card2 = stack;
					} else {
						return null;
					}
				} else {
					continue;
				}
			}
			ItemStack newCard = card1.copy();
			newCard.stackSize = 2;
			return newCard;
		}
	};

	public static final IRecipe cardToWand = new ShapelessRecipes(new ItemStack(MauItems.identityCard), Arrays
			.asList(new ItemStack[] { new ItemStack(MauItems.identityCard), new ItemStack(MauItems.identityCard) })) {
		@Override
		public ItemStack getCraftingResult(InventoryCrafting crafting) {
			ItemStack wand = null, card = null;
			for (int i = 0; i < crafting.getSizeInventory(); i++) {
				ItemStack stack = crafting.getStackInSlot(i);
				if (stack != null && stack.getItem() instanceof ItemLockingWand) {
					if (wand != null) {
						return null;
					}
					wand = stack.copy();
				} else if (stack != null && stack.getItem() instanceof ItemIdentityCard) {
					if (card != null) {
						return null;
					}
					card = stack;
				} else {
					return null;
				}
			}
			if (wand == null || card == null) {
				return null;
			}
			if (card.hasTagCompound()) {
				if (!wand.hasTagCompound()) {
					wand.setTagCompound(new NBTTagCompound());
				}
				NBTTagCompound tag = wand.getTagCompound();
				tag.setTag("Friends", card.getTagCompound().getTagList("Friends", Constants.NBT.TAG_COMPOUND));
			}

			return wand;
		}
	};
}
