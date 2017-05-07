package net.maunium.energeticshielding.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import net.maunium.energeticshielding.EnergeticShielding;

import cofh.api.energy.IEnergyContainerItem;

public class ItemLockingWand extends Item implements IEnergyContainerItem {
	public IIcon[] icons = new IIcon[4];

	public ItemLockingWand() {
		super();
		this.setHasSubtypes(true).setUnlocalizedName("lockingWand").setCreativeTab(EnergeticShielding.tab).setFull3D();
	}

	@Override
	public void registerIcons(IIconRegister reg) {
		String textureName = EnergeticShielding.texture("locking_wand");
		this.icons[0] = reg.registerIcon(textureName + "_coreless");
		this.icons[1] = reg.registerIcon(textureName + "_tier1");
		this.icons[2] = reg.registerIcon(textureName + "_tier2");
		this.icons[3] = reg.registerIcon(textureName + "_tier3");
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return this.getUnlocalizedName() + "_" + stack.getItemDamage();
	}

	@Override
	public IIcon getIconFromDamage(int meta) {
		return this.icons[meta % 4];
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List l) {
		for (int i = 0; i < this.icons.length; i++) {
			l.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public int extractEnergy(ItemStack container, int max, boolean simulate) {
		return 0;
	}

	public boolean useEnergy(ItemStack container, int amount) {
		if (container.getTagCompound() == null || !container.getTagCompound().hasKey("Energy")) {
			return false;
		}
		int energy = container.getTagCompound().getInteger("Energy");
		if (energy >= amount) {
			energy -= amount;
			container.getTagCompound().setInteger("Energy", energy);
			return true;
		}
		return false;
	}

	@Override
	public int getEnergyStored(ItemStack container) {
		if (container.getTagCompound() == null || !container.getTagCompound().hasKey("Energy")) {
			return 0;
		}
		return container.getTagCompound().getInteger("Energy");
	}

	public int getMaxReceive(ItemStack container) {
		switch (container.getItemDamage() % 4) {
		case 1:
			return 500;
		case 2:
			return 1000;
		case 3:
			return 5000;
		default:
			return 0;
		}
	}

	@Override
	public int getMaxEnergyStored(ItemStack container) {
		switch (container.getItemDamage() % 4) {
		case 1:
			return 10000;
		case 2:
			return 50000;
		case 3:
			return 200000;
		default:
			return 0;
		}
	}

	public int getDisplayDamage(ItemStack stack) {
		if (stack.getItemDamage() == 0) {
			return 0;
		}
		return this.getMaxEnergyStored(stack) - this.getEnergyStored(stack);
	}

	public int getMaxDamage(ItemStack stack) {
		if (stack.getItemDamage() == 0) {
			return 0;
		}
		return this.getMaxEnergyStored(stack);
	}

	@Override
	public boolean isDamaged(ItemStack stack) {
		return stack.getItemDamage() != 0;
	}

	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {
		if (!container.hasTagCompound()) {
			container.setTagCompound(new NBTTagCompound());
		}
		int energy = container.getTagCompound().getInteger("Energy");
		int energyReceived = Math.min(getMaxEnergyStored(container) - energy,
				Math.min(getMaxReceive(container), maxReceive));

		if (!simulate) {
			energy += energyReceived;
			container.getTagCompound().setInteger("Energy", energy);
		}
		return energyReceived;
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer p, World w, int x, int y, int z, int side, float hitX,
			float hitY, float hitZ) {
		this.useEnergy(stack, 5000);
		return true;
	}

	@Override
	public boolean isItemTool(ItemStack stack) {
		return true;
	}
}
