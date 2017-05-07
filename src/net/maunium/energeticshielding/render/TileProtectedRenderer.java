package net.maunium.energeticshielding.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import net.maunium.energeticshielding.block.BlockProtected;
import net.maunium.energeticshielding.block.MauBlocks;
import net.maunium.energeticshielding.item.ItemLockingWand;
import net.maunium.energeticshielding.tile.TileProtected;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileProtectedRenderer extends TileEntitySpecialRenderer {
	public void renderTileEntityAt(TileProtected tile, double x, double y, double z, float f) {
		EntityLivingBase viewer = Minecraft.getMinecraft().renderViewEntity;
		if (viewer instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) viewer;
			if (player.getCurrentEquippedItem() != null
					&& player.getCurrentEquippedItem().getItem() instanceof ItemLockingWand) {
				GL11.glPushMatrix();
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, 1);
				GL11.glAlphaFunc(GL11.GL_GREATER, 0.003921569F);
				GL11.glTranslatef((float) x + 0.5F, (float) y, (float) z + 0.5F);

				World world = tile.getWorldObj();
				RenderBlocks renderBlocks = new RenderBlocks();

				GL11.glDisable(GL11.GL_LIGHTING);

				Tessellator t = Tessellator.instance;

				renderBlocks.setRenderBounds(-0.001, -0.001, -0.001, 1.001, 1.001, 1.001);
				if (tile.canBeEditedBy(player)) {
					GL11.glColor4f(0, 1.0f, 0, 0.25F);
				} else {
					GL11.glColor4f(1.0f, 0, 0, 0.25F);
				}
				t.startDrawingQuads();

				t.setBrightness(200);

				field_147501_a.field_147553_e.bindTexture(TextureMap.locationBlocksTexture);
				GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_ADD);
				if (shouldSideBeRendered(world, tile.xCoord, tile.yCoord, tile.zCoord, 1)) {
					renderBlocks.renderFaceYNeg(MauBlocks.blockProtected, -0.5001, 0.0, -0.5001, getIconOnSide(world,
							tile.xCoord, tile.yCoord, tile.zCoord, 0, tile.owners, player.ticksExisted));
				}
				if (shouldSideBeRendered(world, tile.xCoord, tile.yCoord, tile.zCoord, 0)) {
					renderBlocks.renderFaceYPos(MauBlocks.blockProtected, -0.5001, 0.0, -0.5001, getIconOnSide(world,
							tile.xCoord, tile.yCoord, tile.zCoord, 1, tile.owners, player.ticksExisted));
				}
				if (shouldSideBeRendered(world, tile.xCoord, tile.yCoord, tile.zCoord, 3)) {
					renderBlocks.renderFaceZNeg(MauBlocks.blockProtected, -0.5001, 0.0, -0.5001, getIconOnSide(world,
							tile.xCoord, tile.yCoord, tile.zCoord, 2, tile.owners, player.ticksExisted));
				}
				if (shouldSideBeRendered(world, tile.xCoord, tile.yCoord, tile.zCoord, 2)) {
					renderBlocks.renderFaceZPos(MauBlocks.blockProtected, -0.5001, 0.0, -0.5001, getIconOnSide(world,
							tile.xCoord, tile.yCoord, tile.zCoord, 3, tile.owners, player.ticksExisted));
				}
				if (shouldSideBeRendered(world, tile.xCoord, tile.yCoord, tile.zCoord, 5)) {
					renderBlocks.renderFaceXNeg(MauBlocks.blockProtected, -0.5001, 0.0, -0.5001, getIconOnSide(world,
							tile.xCoord, tile.yCoord, tile.zCoord, 4, tile.owners, player.ticksExisted));
				}
				if (shouldSideBeRendered(world, tile.xCoord, tile.yCoord, tile.zCoord, 4)) {
					renderBlocks.renderFaceXPos(MauBlocks.blockProtected, -0.5001, 0.0, -0.5001, getIconOnSide(world,
							tile.xCoord, tile.yCoord, tile.zCoord, 5, tile.owners, player.ticksExisted));
				}
				t.draw();
				GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glColor3f(1.0F, 1.0F, 1.0F);
				GL11.glPopMatrix();
			}
		}
	}

	private boolean shouldSideBeRendered(World world, int x, int y, int z, int side) {
		if (world.getBlockMetadata(x, y, z) != world.getBlockMetadata(
				x - net.minecraft.util.Facing.offsetsXForSide[side],
				y - net.minecraft.util.Facing.offsetsYForSide[side],
				z - net.minecraft.util.Facing.offsetsZForSide[side])) {
			return true;
		}
		if (world.getBlock(x - net.minecraft.util.Facing.offsetsXForSide[side],
				y - net.minecraft.util.Facing.offsetsYForSide[side],
				z - net.minecraft.util.Facing.offsetsZForSide[side]) != MauBlocks.blockProtected) {
			return true;
		}
		return false;
	}

	private IIcon getIconOnSide(World world, int x, int y, int z, int side, int[] owners, int ticks) {
		return BlockProtected.overlayIcon;
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float mystery) {
		this.renderTileEntityAt((TileProtected) tile, x, y, z, mystery);
	}
}