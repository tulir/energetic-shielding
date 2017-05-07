package net.maunium.energeticshielding.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import net.maunium.energeticshielding.EnergeticShielding;
import net.maunium.energeticshielding.block.MauBlocks;
import net.maunium.energeticshielding.tile.TileProtected;

import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;

import cofh.api.energy.IEnergyContainerItem;
import cofh.lib.util.position.BlockPosition;

public class ItemLockingWand extends Item implements IEnergyContainerItem {
	public IIcon[] icons = new IIcon[4];

	public ItemLockingWand() {
		super();
		setHasSubtypes(true)
				.setUnlocalizedName("lockingWand")
				.setCreativeTab(EnergeticShielding.tab)
				.setMaxStackSize(1)
				.setFull3D();
	}

	@Override
	public void registerIcons(IIconRegister reg) {
		String textureName = EnergeticShielding.texture("locking_wand");
		icons[0] = reg.registerIcon(textureName + "_coreless");
		icons[1] = reg.registerIcon(textureName + "_tier1");
		icons[2] = reg.registerIcon(textureName + "_tier2");
		icons[3] = reg.registerIcon(textureName + "_tier3");
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return this.getUnlocalizedName() + "_" + stack.getItemDamage();
	}

	@Override
	public IIcon getIconFromDamage(int meta) {
		return icons[meta % 4];
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List l) {
		for (int i = 0; i < icons.length; i++) {
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

	@Override
	public int getDisplayDamage(ItemStack stack) {
		if (stack.getItemDamage() == 0) {
			return 0;
		}
		return getMaxEnergyStored(stack) - getEnergyStored(stack);
	}

	@Override
	public int getMaxDamage(ItemStack stack) {
		if (stack.getItemDamage() == 0) {
			return 0;
		}
		return getMaxEnergyStored(stack);
	}

	@Override
	public boolean isDamaged(ItemStack stack) {
		return stack.getItemDamage() % 4 != 0;
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

	public int getMaxRadius(ItemStack stack) {
		// Coreless -> No effect
		// Tier 1 -> 1x1 (radius 1)
		// Tier 2 -> 3x3 (radius 2)
		// Tier 3 -> 5x5 (radius 3)
		return stack.getItemDamage() % 4;
	}

	public int getRadius(ItemStack stack) {
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		int radius = stack.getTagCompound().getInteger("Radius");
		return radius > 0 ? radius : 1;
	}

	public int setRadius(ItemStack stack, int radius) {
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		int maxRadius = getMaxRadius(stack);
		if (radius > maxRadius) {
			radius = maxRadius;
		}
		stack.getTagCompound().setInteger("Radius", radius);
		return radius;
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer p, World w, int x, int y, int z, int side, float hitX,
			float hitY, float hitZ) {
		if (w.isRemote || p.isSneaking()) {
			return false;
		}
		int radius = getRadius(stack);
		if (useEnergy(stack, radius * 5000 - stack.getItemDamage() % 4 * 1000)) {
			protect(stack, p, w, x, y, z, side);
			return true;
		}
		return false;
	}

	public void protect(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side) {
		if (world.isAirBlock(x, y, z)) {
			return;
		}
		player.swingItem();

		TileEntity currentTile = world.getTileEntity(x, y, z);
		boolean solid = world.isBlockNormalCubeDefault(x, y, z, true);
		if (currentTile == null && solid) {
			List<BlockPosition> blocks = getBlocksInRadius(stack, x, y, z, ForgeDirection.getOrientation(side));
			blocks.add(new BlockPosition(x, y, z));
			for (BlockPosition pos : blocks) {
				if (pos.getTileEntity(world) == null && world.isBlockNormalCubeDefault(pos.x, pos.y, pos.z, true)) {
					Block block = pos.getBlock(world);
					int meta = world.getBlockMetadata(pos.x, pos.y, pos.z);
					int light = block.getLightValue(world, pos.x, pos.y, pos.z);
					world.setBlock(pos.x, pos.y, pos.z, MauBlocks.blockProtected, meta, 3);
					TileProtected tile = pos.getTileEntity(world, TileProtected.class);
					if (tile != null) {
						tile.block = block;
						tile.blockMeta = (byte) meta;
						tile.light = (byte) light;

						if (!stack.hasTagCompound()) {
							tile.owners = new int[] { player.getUniqueID().toString().hashCode() };
						} else {
							NBTTagCompound tag = stack.getTagCompound();
							NBTTagList list = tag.getTagList("Friends", Constants.NBT.TAG_STRING);
							if (list.tagCount() == 0) {
								tile.owners = new int[] { player.getUniqueID().toString().hashCode() };
							} else {
								int selfHash = player.getUniqueID().toString().hashCode();
								boolean selfIsOwner = false;
								tile.owners = new int[list.tagCount()];
								for (int i = 0; i < list.tagCount(); i++) {
									tile.owners[i] = list.getStringTagAt(i).hashCode();
									if (!selfIsOwner && tile.owners[i] == selfHash) {
										selfIsOwner = true;
									}
								}
								if (!selfIsOwner) {
									tile.owners = Arrays.copyOf(tile.owners, tile.owners.length + 1);
									tile.owners[tile.owners.length - 1] = selfHash;
								}
							}
						}
						world.markBlockForUpdate(pos.x, pos.y, pos.z);
					}
				}
			}
			// Sound effect can be played here
		} else if (currentTile != null && currentTile instanceof TileProtected) {
			TileProtected currentTileProtected = (TileProtected) currentTile;
			if (currentTileProtected.canBeEditedBy(player)) {
				List<BlockPosition> blocks = getBlocksInRadius(stack, x, y, z, ForgeDirection.getOrientation(side));
				for (BlockPosition pos : blocks) {
					TileProtected tile = pos.getTileEntity(world, TileProtected.class);
					if (tile != null && tile.canBeEditedBy(player)) {
						world.setBlock(pos.x, pos.y, pos.z, tile.block, tile.blockMeta, 3);
						world.markBlockForUpdate(pos.x, pos.y, pos.z);
					}
				}
				// Sound effect can be played here
			}
		}
	}

	public List<BlockPosition> getBlocksInRadius(ItemStack stack, int centerX, int centerY, int centerZ,
			ForgeDirection side) {
		int radius = getRadius(stack) - 1;
		List<BlockPosition> blocks = new ArrayList<>();
		if (side == ForgeDirection.DOWN || side == ForgeDirection.UP) {
			for (int x = centerX - radius; x <= centerX + radius; x++) {
				for (int z = centerZ - radius; z <= centerZ + radius; z++) {
					blocks.add(new BlockPosition(x, centerY, z));
				}
			}
		} else if (side == ForgeDirection.NORTH || side == ForgeDirection.SOUTH) {
			for (int x = centerX - radius; x <= centerX + radius; x++) {
				for (int y = centerY - radius; y <= centerY + radius; y++) {
					blocks.add(new BlockPosition(x, y, centerZ));
				}
			}
		} else if (side == ForgeDirection.EAST || side == ForgeDirection.WEST) {
			for (int z = centerZ - radius; z <= centerZ + radius; z++) {
				for (int y = centerY - radius; y <= centerY + radius; y++) {
					blocks.add(new BlockPosition(centerX, y, z));
				}
			}
		}
		return blocks;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World w, EntityPlayer p) {
		if (!w.isRemote && p.isSneaking()) {
			int radius = getRadius(stack);
			int maxRadius = getMaxRadius(stack);
			int newRadius;
			if (radius < maxRadius) {
				newRadius = setRadius(stack, radius + 1);
			} else {
				newRadius = setRadius(stack, 1);
			}
			if (newRadius != radius) {
				p.addChatMessage(new ChatComponentText("Radius: " + newRadius + " (max: " + maxRadius + ")"));
			}
			return stack;
		}
		return super.onItemRightClick(stack, w, p);
	}

	@Override
	public boolean isItemTool(ItemStack stack) {
		return stack.getItemDamage() % 4 != 0;
	}
}
