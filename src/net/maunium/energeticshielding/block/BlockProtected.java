package net.maunium.energeticshielding.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.maunium.energeticshielding.EnergeticShielding;
import net.maunium.energeticshielding.render.BlockProtectedRenderer;
import net.maunium.energeticshielding.tile.TileProtected;

import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockProtected extends BlockContainer {
	public static IIcon overlayIcon;
	public IIcon icon;

	public BlockProtected() {
		super(Material.rock);
		this.setStepSound(soundTypeStone);
		this.disableStats();
		this.setResistance(999.0F);
		this.setBlockUnbreakable();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir) {
		this.icon = ir.registerIcon(EnergeticShielding.texture("protected_blank"));
		BlockProtected.overlayIcon = ir.registerIcon(EnergeticShielding.texture("protected_overlay"));
	}

	@Override
	public IIcon getIcon(int i, int m) {
		return this.icon;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer) {
		float f = (float) target.hitVec.xCoord - target.blockX;
		float f1 = (float) target.hitVec.yCoord - target.blockY;
		float f2 = (float) target.hitVec.zCoord - target.blockZ;
		EnergeticShielding.proxy.blockProtectedFX(worldObj, target.blockX, target.blockY, target.blockZ,
				ForgeDirection.getOrientation(target.sideHit), f, f1, f2);
		return true;
	}

	@Override
	public int getRenderType() {
		return BlockProtectedRenderer.renderID;
	}

	int sc = 0;

	public Block getBlock(World world, int x, int y, int z) {
		if (this.sc > 5) {
			this.sc = 0;
			return Blocks.stone;
		}
		this.sc += 1;
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null && tile instanceof TileProtected) {
			this.sc = 0;
			return ((TileProtected) tile).block;
		}
		return Blocks.stone;
	}

	public Block getBlock(IBlockAccess world, int x, int y, int z) {
		if (this.sc > 5) {
			this.sc = 0;
			return Blocks.stone;
		}
		this.sc += 1;
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null && tile instanceof TileProtected) {
			this.sc = 0;
			return ((TileProtected) tile).block;
		}
		return Blocks.stone;
	}

	@Override
	public Item getItemDropped(int a, Random rand, int b) {
		return Item.getItemById(0);
	}

	@Override
	public int damageDropped(int a) {
		return a;
	}

	@Override
	public int getMobilityFlag() {
		return 2;
	}

	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileProtected();
	}

	@Override
	public boolean canBeReplacedByLeaves(IBlockAccess world, int x, int y, int z) {
		return false;
	}

	@Override
	public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity) {
		return false;
	}

	@Override
	public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z) {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess ba, int x, int y, int z, int side) {
		return this.getBlock(ba, x, y, z).getIcon(ba, x, y, z, side);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess ba, int x, int y, int z) {
		return this.getBlock(ba, x, y, z).colorMultiplier(ba, x, y, z);
	}

	@Override
	public int getDamageValue(World world, int x, int y, int z) {
		return this.getBlock(world, x, y, z).getDamageValue(world, x, y, z);
	}

	@Override
	public int getMixedBrightnessForBlock(IBlockAccess w, int x, int y, int z) {
		return this.getBlock(w, x, y, z).getMixedBrightnessForBlock(w, x, y, z);
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess w, int x, int y, int z, int side) {
		return this.getBlock(w, x, y, z).shouldSideBeRendered(w, x, y, z, side);
	}

	@Override
	public boolean isBlockSolid(IBlockAccess w, int x, int y, int z, int par5) {
		return this.getBlock(w, x, y, z).isBlockSolid(w, x, y, z, par5);
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World w, int x, int y, int z) {
		return this.getBlock(w, x, y, z).getSelectedBoundingBoxFromPool(w, x, y, z);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World w, int x, int y, int z) {
		return this.getBlock(w, x, y, z).getCollisionBoundingBoxFromPool(w, x, y, z);
	}

	@Override
	public void randomDisplayTick(World w, int x, int y, int z, Random rand) {
		this.getBlock(w, x, y, z).randomDisplayTick(w, x, y, z, rand);
	}

	@Override
	public boolean canPlaceBlockOnSide(World w, int x, int y, int z, int side) {
		return this.getBlock(w, x, y, z).canPlaceBlockOnSide(w, x, y, z, side);
	}

	@Override
	public void onEntityWalking(World w, int x, int y, int z, Entity e) {
		this.getBlock(w, x, y, z).onEntityWalking(w, x, y, z, e);
	}

	@Override
	public void onBlockClicked(World w, int x, int y, int z, EntityPlayer player) {
		this.getBlock(w, x, y, z).onBlockClicked(w, x, y, z, player);
	}

	@Override
	public void velocityToAddToEntity(World ba, int x, int y, int z, Entity par5Entity, Vec3 par6Vec3) {
		this.getBlock(ba, x, y, z).velocityToAddToEntity(ba, x, y, z, par5Entity, par6Vec3);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess ba, int x, int y, int z) {
		this.getBlock(ba, x, y, z).setBlockBoundsBasedOnState(ba, x, y, z);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public void addCollisionBoxesToList(World ba, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity) {
		this.getBlock(ba, x, y, z).addCollisionBoxesToList(ba, x, y, z, aabb, list, entity);
	}

	@Override
	public void onEntityCollidedWithBlock(World ba, int x, int y, int z, Entity par5Entity) {
		this.getBlock(ba, x, y, z).onEntityCollidedWithBlock(ba, x, y, z, par5Entity);
	}

	@Override
	public void onFallenUpon(World ba, int x, int y, int z, Entity par5Entity, float par6) {
		this.getBlock(ba, x, y, z).onFallenUpon(ba, x, y, z, par5Entity, par6);
	}

	@Override
	public Item getItem(World ba, int x, int y, int z) {
		return this.getBlock(ba, x, y, z).getItem(ba, x, y, z);
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null && tile instanceof TileProtected) {
			return ((TileProtected) tile).light;
		}
		return 0;
	}

	@Override
	public boolean isLadder(IBlockAccess world, int x, int y, int z, EntityLivingBase entity) {
		return this.getBlock(world, x, y, z).isLadder(world, x, y, z, entity);
	}

	@Override
	public boolean isNormalCube(IBlockAccess world, int x, int y, int z) {
		return this.getBlock(world, x, y, z).isNormalCube(world, x, y, z);
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
		return this.getBlock(world, x, y, z).isSideSolid(world, x, y, z, side);
	}

	@Override
	public boolean canSustainLeaves(IBlockAccess world, int x, int y, int z) {
		return this.getBlock(world, x, y, z).canSustainLeaves(world, x, y, z);
	}

	@Override
	public boolean canPlaceTorchOnTop(World world, int x, int y, int z) {
		return this.getBlock(world, x, y, z).canPlaceTorchOnTop(world, x, y, z);
	}

	@Override
	@Deprecated
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		return this.getBlock(world, x, y, z).getPickBlock(target, world, x, y, z);
	}

	@Override
	public boolean isFoliage(IBlockAccess world, int x, int y, int z) {
		return this.getBlock(world, x, y, z).isFoliage(world, x, y, z);
	}

	@Override
	public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction,
			IPlantable plant) {
		return this.getBlock(world, x, y, z).canSustainPlant(world, x, y, z, direction, plant);
	}

	@Override
	public boolean isFertile(World world, int x, int y, int z) {
		return this.getBlock(world, x, y, z).isFertile(world, x, y, z);
	}

	@Override
	public int getLightOpacity(IBlockAccess world, int x, int y, int z) {
		return this.getBlock(world, x, y, z).getLightOpacity(world, x, y, z);
	}

	@Override
	public boolean isBeaconBase(IBlockAccess world, int x, int y, int z, int beaconX, int beaconY, int beaconZ) {
		return this.getBlock(world, x, y, z).isBeaconBase(world, x, y, z, beaconX, beaconY, beaconZ);
	}

	@Override
	public float getEnchantPowerBonus(World world, int x, int y, int z) {
		return this.getBlock(world, x, y, z).getEnchantPowerBonus(world, x, y, z);
	}

	@Override
	public boolean canHarvestBlock(EntityPlayer player, int meta) {
		return true;
	}

	@Override
	public void onBlockExploded(World world, int x, int y, int z, Explosion explosion) {
	}
}
