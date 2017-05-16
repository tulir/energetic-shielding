package net.maunium.energeticshielding.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;

import net.maunium.energeticshielding.EnergeticShielding;

public class BlockTranslocatableItem extends ItemBlockWithMetadata {
	public BlockTranslocatableItem(Block b) {
		super(b, b);
		this.setHasSubtypes(true).setCreativeTab(EnergeticShielding.tab);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		list.add(new ItemStack(item, 1, 0));
		list.add(new ItemStack(item, 1, 1));
		list.add(new ItemStack(item, 1, 2));
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		String name = super.getUnlocalizedName();
		switch (stack.getItemDamage() % 3) {
		case 1:
			return name + "_unstable";
		case 2:
			return name + "_stable";
		default:
			return name + "_wild";
		}
	}
}
