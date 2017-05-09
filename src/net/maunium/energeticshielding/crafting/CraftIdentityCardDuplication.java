package net.maunium.energeticshielding.crafting;

import java.util.ArrayList;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapelessRecipes;

import net.maunium.energeticshielding.item.ItemIdentityCard;
import net.maunium.energeticshielding.item.MauItems;

public class CraftIdentityCardDuplication extends ShapelessRecipes {
	@SuppressWarnings("unchecked")
	public CraftIdentityCardDuplication() {
		super(new ItemStack(MauItems.identityCard), new ArrayList<ItemStack>());
		this.recipeItems.add(new ItemStack(MauItems.identityCard));
		this.recipeItems.add(new ItemStack(MauItems.identityCard));
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting crafting) {
		ItemStack card1 = null, card2 = null;
		for (int i = 0; i < crafting.getSizeInventory(); i++) {
			ItemStack stack = crafting.getStackInSlot(i);
			if (stack == null) {
				continue;
			} else if (stack.getItem() instanceof ItemIdentityCard) {
				if (card1 == null) {
					card1 = stack;
					continue;
				} else if (card2 == null) {
					card2 = stack;
					continue;
				}
			}
			return null;
		}
		if (card1 == null || card2 == null) {
			return null;
		}
		ItemStack newCard = card1.copy();
		newCard.stackSize = 2;
		return newCard;
	}
}
