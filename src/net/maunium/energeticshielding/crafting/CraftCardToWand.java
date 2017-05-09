package net.maunium.energeticshielding.crafting;

import java.util.ArrayList;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTTagCompound;

import net.maunium.energeticshielding.item.ItemIdentityCard;
import net.maunium.energeticshielding.item.ItemLockingWand;
import net.maunium.energeticshielding.item.MauItems;

public class CraftCardToWand extends ShapelessRecipes {
	@SuppressWarnings("unchecked")
	public CraftCardToWand() {
		super(new ItemStack(MauItems.lockingWand), new ArrayList<ItemStack>());
		this.recipeItems.add(new ItemStack(MauItems.lockingWand, 1, 32767));
		this.recipeItems.add(new ItemStack(MauItems.identityCard));
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting crafting) {
		ItemStack wand = null, card = null;
		for (int i = 0; i < crafting.getSizeInventory(); i++) {
			ItemStack stack = crafting.getStackInSlot(i);
			if (stack == null) {
				continue;
			} else if (stack.getItem() instanceof ItemLockingWand) {
				if (wand != null) {
					return null;
				}
				wand = stack.copy();
				continue;
			} else if (stack.getItem() instanceof ItemIdentityCard) {
				if (card != null) {
					return null;
				}
				card = stack;
				continue;
			}
			return null;
		}
		if (wand == null || card == null) {
			return null;
		}
		if (card.hasTagCompound()) {
			if (!wand.hasTagCompound()) {
				wand.setTagCompound(new NBTTagCompound());
			}
			NBTTagCompound tag = wand.getTagCompound();
			tag.setTag("Friends", card.getTagCompound().getTag("Friends"));
		}

		return wand;
	}
}
