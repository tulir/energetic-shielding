package net.maunium.energeticshielding.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import net.maunium.energeticshielding.EnergeticShielding;

public class ItemLockingWandCore extends Item {
	public IIcon[] icons = new IIcon[5];

	public ItemLockingWandCore() {
		super();
		this.setHasSubtypes(true).setUnlocalizedName("lockingWandCore").setCreativeTab(EnergeticShielding.tab);
	}

	@Override
	public void registerIcons(IIconRegister reg) {
		String textureName = EnergeticShielding.texture("locking_wand_core");
		this.icons[0] = reg.registerIcon(textureName + "_tier1");
		this.icons[1] = reg.registerIcon(textureName + "_tier2_empty");
		this.icons[2] = reg.registerIcon(textureName + "_tier2");
		this.icons[3] = reg.registerIcon(textureName + "_tier3_unstable");
		this.icons[4] = reg.registerIcon(textureName + "_tier3");
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return this.getUnlocalizedName() + "_" + stack.getItemDamage();
	}

	@Override
	public IIcon getIconFromDamage(int meta) {
		return this.icons[meta % this.icons.length];
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List l) {
		for (int i = 0; i < this.icons.length; i++) {
			l.add(new ItemStack(item, 1, i));
		}
	}
}
