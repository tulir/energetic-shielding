package thaumcraft.client.lib;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Timer;
import net.minecraft.util.Vec3;

import net.maunium.energeticshielding.EnergeticShielding;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.ReflectionHelper;

public class UtilsFX {
	public static final String[] colorNames = { "White", "Orange", "Magenta", "Light Blue", "Yellow", "Lime", "Pink",
			"Gray", "Light Gray", "Cyan", "Purple", "Blue", "Brown", "Green", "Red", "Black" };
	public static final String[] colorCodes = { "§f", "§6", "§d", "§9", "§e", "§a", "§d", "§8", "§7", "§b", "§5", "§9",
			"§4", "§2", "§c", "§8" };
	public static final int[] colors = { 15790320, 15435844, 12801229, 6719955, 14602026, 4312372, 14188952, 4408131,
			10526880, 2651799, 8073150, 2437522, 5320730, 3887386, 11743532, 1973019 };
	public static int[] connectedTextureRefByID = { 0, 0, 6, 6, 0, 0, 6, 6, 3, 3, 19, 15, 3, 3, 19, 15, 1, 1, 18, 18, 1,
			1, 13, 13, 2, 2, 23, 31, 2, 2, 27, 14, 0, 0, 6, 6, 0, 0, 6, 6, 3, 3, 19, 15, 3, 3, 19, 15, 1, 1, 18, 18, 1,
			1, 13, 13, 2, 2, 23, 31, 2, 2, 27, 14, 4, 4, 5, 5, 4, 4, 5, 5, 17, 17, 22, 26, 17, 17, 22, 26, 16, 16, 20,
			20, 16, 16, 28, 28, 21, 21, 46, 42, 21, 21, 43, 38, 4, 4, 5, 5, 4, 4, 5, 5, 9, 9, 30, 12, 9, 9, 30, 12, 16,
			16, 20, 20, 16, 16, 28, 28, 25, 25, 45, 37, 25, 25, 40, 32, 0, 0, 6, 6, 0, 0, 6, 6, 3, 3, 19, 15, 3, 3, 19,
			15, 1, 1, 18, 18, 1, 1, 13, 13, 2, 2, 23, 31, 2, 2, 27, 14, 0, 0, 6, 6, 0, 0, 6, 6, 3, 3, 19, 15, 3, 3, 19,
			15, 1, 1, 18, 18, 1, 1, 13, 13, 2, 2, 23, 31, 2, 2, 27, 14, 4, 4, 5, 5, 4, 4, 5, 5, 17, 17, 22, 26, 17, 17,
			22, 26, 7, 7, 24, 24, 7, 7, 10, 10, 29, 29, 44, 41, 29, 29, 39, 33, 4, 4, 5, 5, 4, 4, 5, 5, 9, 9, 30, 12, 9,
			9, 30, 12, 7, 7, 24, 24, 7, 7, 10, 10, 8, 8, 36, 35, 8, 8, 34, 11 };
	public static float[] lightBrightnessTable = null;

	public static float getBrightnessFromLight(int light) {
		if (lightBrightnessTable == null) {
			lightBrightnessTable = new float[16];
			float f = 0.0F;
			for (int i = 0; i <= 15; i++) {
				float f1 = 1.0F - i / 15.0F;
				lightBrightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f;
			}
		}
		return lightBrightnessTable[light];
	}

	public static void renderFacingQuad(double px, double py, double pz, float angle, float scale, float alpha,
			int frames, int cframe, float partialTicks, int color) {
		if (Minecraft.getMinecraft().renderViewEntity instanceof EntityPlayer) {
			Tessellator tessellator = Tessellator.instance;
			float arX = ActiveRenderInfo.rotationX;
			float arZ = ActiveRenderInfo.rotationZ;
			float arYZ = ActiveRenderInfo.rotationYZ;
			float arXY = ActiveRenderInfo.rotationXY;
			float arXZ = ActiveRenderInfo.rotationXZ;

			EntityPlayer player = (EntityPlayer) Minecraft.getMinecraft().renderViewEntity;
			double iPX = player.prevPosX + (player.posX - player.prevPosX) * partialTicks;
			double iPY = player.prevPosY + (player.posY - player.prevPosY) * partialTicks;
			double iPZ = player.prevPosZ + (player.posZ - player.prevPosZ) * partialTicks;

			GL11.glTranslated(-iPX, -iPY, -iPZ);

			tessellator.startDrawingQuads();
			tessellator.setBrightness(220);
			tessellator.setColorRGBA_I(color, (int) (alpha * 255.0F));

			Vec3 v1 = Vec3.createVectorHelper(-arX * scale - arYZ * scale, -arXZ * scale, -arZ * scale - arXY * scale);
			Vec3 v2 = Vec3.createVectorHelper(-arX * scale + arYZ * scale, arXZ * scale, -arZ * scale + arXY * scale);
			Vec3 v3 = Vec3.createVectorHelper(arX * scale + arYZ * scale, arXZ * scale, arZ * scale + arXY * scale);
			Vec3 v4 = Vec3.createVectorHelper(arX * scale - arYZ * scale, -arXZ * scale, arZ * scale - arXY * scale);
			if (angle != 0.0F) {
				Vec3 pvec = Vec3.createVectorHelper(iPX, iPY, iPZ);
				Vec3 tvec = Vec3.createVectorHelper(px, py, pz);
				Vec3 qvec = pvec.subtract(tvec).normalize();
				QuadHelper.setAxis(qvec, angle).rotate(v1);
				QuadHelper.setAxis(qvec, angle).rotate(v2);
				QuadHelper.setAxis(qvec, angle).rotate(v3);
				QuadHelper.setAxis(qvec, angle).rotate(v4);
			}
			float f2 = cframe / frames;
			float f3 = (cframe + 1) / frames;
			float f4 = 0.0F;
			float f5 = 1.0F;
			tessellator.setNormal(0.0F, 0.0F, -1.0F);
			tessellator.addVertexWithUV(px + v1.xCoord, py + v1.yCoord, pz + v1.zCoord, f2, f5);
			tessellator.addVertexWithUV(px + v2.xCoord, py + v2.yCoord, pz + v2.zCoord, f3, f5);
			tessellator.addVertexWithUV(px + v3.xCoord, py + v3.yCoord, pz + v3.zCoord, f3, f4);
			tessellator.addVertexWithUV(px + v4.xCoord, py + v4.yCoord, pz + v4.zCoord, f2, f4);

			tessellator.draw();
		}
	}

	public static void renderFacingStrip(double px, double py, double pz, float angle, float scale, float alpha,
			int frames, int strip, int frame, float partialTicks, int color) {
		if (Minecraft.getMinecraft().renderViewEntity instanceof EntityPlayer) {
			Tessellator tessellator = Tessellator.instance;
			float arX = ActiveRenderInfo.rotationX;
			float arZ = ActiveRenderInfo.rotationZ;
			float arYZ = ActiveRenderInfo.rotationYZ;
			float arXY = ActiveRenderInfo.rotationXY;
			float arXZ = ActiveRenderInfo.rotationXZ;

			EntityPlayer player = (EntityPlayer) Minecraft.getMinecraft().renderViewEntity;
			double iPX = player.prevPosX + (player.posX - player.prevPosX) * partialTicks;
			double iPY = player.prevPosY + (player.posY - player.prevPosY) * partialTicks;
			double iPZ = player.prevPosZ + (player.posZ - player.prevPosZ) * partialTicks;

			GL11.glTranslated(-iPX, -iPY, -iPZ);

			tessellator.startDrawingQuads();
			tessellator.setBrightness(220);
			tessellator.setColorRGBA_I(color, (int) (alpha * 255.0F));

			Vec3 v1 = Vec3.createVectorHelper(-arX * scale - arYZ * scale, -arXZ * scale, -arZ * scale - arXY * scale);
			Vec3 v2 = Vec3.createVectorHelper(-arX * scale + arYZ * scale, arXZ * scale, -arZ * scale + arXY * scale);
			Vec3 v3 = Vec3.createVectorHelper(arX * scale + arYZ * scale, arXZ * scale, arZ * scale + arXY * scale);
			Vec3 v4 = Vec3.createVectorHelper(arX * scale - arYZ * scale, -arXZ * scale, arZ * scale - arXY * scale);
			if (angle != 0.0F) {
				Vec3 pvec = Vec3.createVectorHelper(iPX, iPY, iPZ);
				Vec3 tvec = Vec3.createVectorHelper(px, py, pz);
				Vec3 qvec = pvec.subtract(tvec).normalize();
				QuadHelper.setAxis(qvec, angle).rotate(v1);
				QuadHelper.setAxis(qvec, angle).rotate(v2);
				QuadHelper.setAxis(qvec, angle).rotate(v3);
				QuadHelper.setAxis(qvec, angle).rotate(v4);
			}
			float f2 = frame / frames;
			float f3 = (frame + 1) / frames;
			float f4 = strip / frames;
			float f5 = (strip + 1.0F) / frames;
			tessellator.setNormal(0.0F, 0.0F, -1.0F);
			tessellator.addVertexWithUV(px + v1.xCoord, py + v1.yCoord, pz + v1.zCoord, f3, f5);
			tessellator.addVertexWithUV(px + v2.xCoord, py + v2.yCoord, pz + v2.zCoord, f3, f4);
			tessellator.addVertexWithUV(px + v3.xCoord, py + v3.yCoord, pz + v3.zCoord, f2, f4);
			tessellator.addVertexWithUV(px + v4.xCoord, py + v4.yCoord, pz + v4.zCoord, f2, f5);

			tessellator.draw();
		}
	}

	public static void renderAnimatedQuad(float scale, float alpha, int frames, int cframe, float partialTicks,
			int color) {
		if (Minecraft.getMinecraft().renderViewEntity instanceof EntityPlayer) {
			Tessellator tessellator = Tessellator.instance;

			tessellator.startDrawingQuads();
			tessellator.setBrightness(220);
			tessellator.setColorRGBA_I(color, (int) (alpha * 255.0F));

			float f2 = cframe / frames;
			float f3 = (cframe + 1) / frames;
			float f4 = 0.0F;
			float f5 = 1.0F;
			tessellator.setNormal(0.0F, 0.0F, -1.0F);

			tessellator.addVertexWithUV(-0.5D * scale, 0.5D * scale, 0.0D, f2, f5);
			tessellator.addVertexWithUV(0.5D * scale, 0.5D * scale, 0.0D, f3, f5);
			tessellator.addVertexWithUV(0.5D * scale, -0.5D * scale, 0.0D, f3, f4);
			tessellator.addVertexWithUV(-0.5D * scale, -0.5D * scale, 0.0D, f2, f4);
			tessellator.draw();
		}
	}

	public static void renderAnimatedQuadStrip(float scale, float alpha, int frames, int strip, int cframe,
			float partialTicks, int color) {
		if (Minecraft.getMinecraft().renderViewEntity instanceof EntityPlayer) {
			Tessellator tessellator = Tessellator.instance;

			tessellator.startDrawingQuads();
			tessellator.setBrightness(220);
			tessellator.setColorRGBA_I(color, (int) (alpha * 255.0F));

			float f2 = cframe / frames;
			float f3 = (cframe + 1) / frames;
			float f4 = strip / frames;
			float f5 = (strip + 1) / frames;
			tessellator.setNormal(0.0F, 0.0F, -1.0F);

			tessellator.addVertexWithUV(-0.5D * scale, 0.5D * scale, 0.0D, f2, f5);
			tessellator.addVertexWithUV(0.5D * scale, 0.5D * scale, 0.0D, f3, f5);
			tessellator.addVertexWithUV(0.5D * scale, -0.5D * scale, 0.0D, f3, f4);
			tessellator.addVertexWithUV(-0.5D * scale, -0.5D * scale, 0.0D, f2, f4);
			tessellator.draw();
		}
	}

	public static Vec3 perpendicular(Vec3 v) {
		if (v.zCoord == 0.0D) {
			return zCrossProduct(v);
		}
		return xCrossProduct(v);
	}

	public static Vec3 xCrossProduct(Vec3 v) {
		double d = v.zCoord;
		double d1 = -v.yCoord;
		v.xCoord = 0.0D;
		v.yCoord = d;
		v.zCoord = d1;
		return v;
	}

	public static Vec3 zCrossProduct(Vec3 v) {
		double d = v.yCoord;
		double d1 = -v.xCoord;
		v.xCoord = d;
		v.yCoord = d1;
		v.zCoord = 0.0D;
		return v;
	}

	public static void drawTexturedQuad(int par1, int par2, int par3, int par4, int par5, int par6, double zLevel) {
		float var7 = 0.00390625F;
		float var8 = 0.00390625F;
		Tessellator var9 = Tessellator.instance;
		var9.startDrawingQuads();
		var9.addVertexWithUV(par1 + 0, par2 + par6, zLevel, (par3 + 0) * var7, (par4 + par6) * var8);
		var9.addVertexWithUV(par1 + par5, par2 + par6, zLevel, (par3 + par5) * var7, (par4 + par6) * var8);
		var9.addVertexWithUV(par1 + par5, par2 + 0, zLevel, (par3 + par5) * var7, (par4 + 0) * var8);
		var9.addVertexWithUV(par1 + 0, par2 + 0, zLevel, (par3 + 0) * var7, (par4 + 0) * var8);
		var9.draw();
	}

	public static void drawTexturedQuadFull(int par1, int par2, double zLevel) {
		Tessellator var9 = Tessellator.instance;
		var9.startDrawingQuads();
		var9.addVertexWithUV(par1 + 0, par2 + 16, zLevel, 0.0D, 1.0D);
		var9.addVertexWithUV(par1 + 16, par2 + 16, zLevel, 1.0D, 1.0D);
		var9.addVertexWithUV(par1 + 16, par2 + 0, zLevel, 1.0D, 0.0D);
		var9.addVertexWithUV(par1 + 0, par2 + 0, zLevel, 0.0D, 0.0D);
		var9.draw();
	}

	public static void renderQuad(String texture) {
		renderQuad(texture, 1, 0.66F);
	}

	public static void renderQuad(String texture, int blend, float trans) {
		renderQuad(texture, blend, trans, 1.0F, 1.0F, 1.0F);
	}

	public static void renderQuad(String texture, int blend, float trans, float r, float g, float b) {
		bindTexture(texture);
		Tessellator tessellator = Tessellator.instance;

		GL11.glEnable(32826);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, blend);
		GL11.glColor4f(r, g, b, trans);

		tessellator.startDrawingQuads();
		tessellator.setColorRGBA_F(r, g, b, trans);
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		tessellator.addVertexWithUV(0.0D, 1.0D, 0.0D, 0.0D, 1.0D);
		tessellator.addVertexWithUV(1.0D, 1.0D, 0.0D, 1.0D, 1.0D);
		tessellator.addVertexWithUV(1.0D, 0.0D, 0.0D, 1.0D, 0.0D);
		tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
		tessellator.draw();
		GL11.glDisable(3042);
		GL11.glDisable(32826);
	}

	public static void renderQuadCenteredFromTexture(String texture, float scale, float red, float green, float blue,
			int brightness, int blend, float opacity) {
		bindTexture(texture);
		renderQuadCenteredFromTexture(scale, red, green, blue, brightness, blend, opacity);
	}

	public static void renderQuadCenteredFromTexture(ResourceLocation texture, float scale, float red, float green,
			float blue, int brightness, int blend, float opacity) {
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		renderQuadCenteredFromTexture(scale, red, green, blue, brightness, blend, opacity);
	}

	public static void renderQuadCenteredFromTexture(float scale, float red, float green, float blue, int brightness,
			int blend, float opacity) {
		Tessellator tessellator = Tessellator.instance;

		GL11.glScalef(scale, scale, scale);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, blend);

		GL11.glColor4f(1.0F, 1.0F, 1.0F, opacity);

		tessellator.startDrawingQuads();
		if (brightness > 0) {
			tessellator.setBrightness(brightness);
		}
		tessellator.setColorRGBA_F(red, green, blue, opacity);

		tessellator.addVertexWithUV(-0.5D, 0.5D, 0.0D, 0.0D, 1.0D);
		tessellator.addVertexWithUV(0.5D, 0.5D, 0.0D, 1.0D, 1.0D);
		tessellator.addVertexWithUV(0.5D, -0.5D, 0.0D, 1.0D, 0.0D);
		tessellator.addVertexWithUV(-0.5D, -0.5D, 0.0D, 0.0D, 0.0D);
		tessellator.draw();

		GL11.glDisable(3042);
	}

	public static void renderQuadFromTexture(String texture, int tileSize, int icon, float scale, float red,
			float green, float blue, int brightness, int blend, float opacity) {
		bindTexture(texture);
		int size = getTextureSize(texture, tileSize);
		float size16 = size * tileSize;
		float float_sizeMinus0_01 = size - 0.01F;
		float float_texNudge = 1.0F / (size * size * 2.0F);
		float float_reciprocal = 1.0F / size;

		Tessellator tessellator = Tessellator.instance;

		int i = icon;
		float f = (i % tileSize * size + 0.0F) / size16;
		float f1 = (i % tileSize * size + float_sizeMinus0_01) / size16;
		float f2 = (i / tileSize * size + 0.0F) / size16;
		float f3 = (i / tileSize * size + float_sizeMinus0_01) / size16;
		float f5 = 0.0F;
		float f6 = 0.3F;
		GL11.glEnable(32826);
		GL11.glScalef(scale, scale, scale);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, blend);

		GL11.glColor4f(1.0F, 1.0F, 1.0F, opacity);

		tessellator.startDrawingQuads();
		tessellator.setBrightness(brightness);
		tessellator.setColorRGBA_F(red, green, blue, opacity);
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		tessellator.addVertexWithUV(0.0D, 1.0D, 0.0D, f1, f2);
		tessellator.addVertexWithUV(1.0D, 1.0D, 0.0D, f, f2);
		tessellator.addVertexWithUV(1.0D, 0.0D, 0.0D, f, f3);
		tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, f1, f3);
		tessellator.draw();

		GL11.glDisable(3042);
		GL11.glDisable(32826);
	}

	public static void renderQuadFromIcon(boolean isBlock, IIcon icon, float scale, float red, float green, float blue,
			int brightness, int blend, float opacity) {
		if (isBlock) {
			Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
		} else {
			Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationItemsTexture);
		}
		Tessellator tessellator = Tessellator.instance;

		float f1 = icon.getMaxU();
		float f2 = icon.getMinV();
		float f3 = icon.getMinU();
		float f4 = icon.getMaxV();

		GL11.glScalef(scale, scale, scale);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, blend);

		GL11.glColor4f(red, green, blue, opacity);

		tessellator.startDrawingQuads();
		if (brightness > -1) {
			tessellator.setBrightness(brightness);
		}
		tessellator.setColorRGBA_F(red, green, blue, opacity);
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, f1, f4);
		tessellator.addVertexWithUV(1.0D, 0.0D, 0.0D, f3, f4);
		tessellator.addVertexWithUV(1.0D, 1.0D, 0.0D, f3, f2);
		tessellator.addVertexWithUV(0.0D, 1.0D, 0.0D, f1, f2);
		tessellator.draw();

		GL11.glDisable(3042);
	}

	public static void renderQuadCenteredFromIcon(boolean isBlock, IIcon icon, float scale, float red, float green,
			float blue, int brightness, int blend, float opacity) {
		if (isBlock) {
			Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
		} else {
			Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationItemsTexture);
		}
		Tessellator tessellator = Tessellator.instance;

		float f1 = icon.getMaxU();
		float f2 = icon.getMinV();
		float f3 = icon.getMinU();
		float f4 = icon.getMaxV();

		GL11.glEnable(32826);
		GL11.glScalef(scale, scale, scale);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, blend);

		GL11.glColor4f(red, green, blue, opacity);

		tessellator.startDrawingQuads();
		tessellator.setBrightness(brightness);
		tessellator.setColorRGBA_F(red, green, blue, opacity);
		tessellator.setNormal(0.0F, 0.0F, 1.0F);

		tessellator.addVertexWithUV(-0.5D, 0.5D, 0.0D, f1, f4);
		tessellator.addVertexWithUV(0.5D, 0.5D, 0.0D, f3, f4);
		tessellator.addVertexWithUV(0.5D, -0.5D, 0.0D, f3, f2);
		tessellator.addVertexWithUV(-0.5D, -0.5D, 0.0D, f1, f2);
		tessellator.draw();

		GL11.glDisable(3042);
		GL11.glDisable(32826);
	}

	public static int getTextureAnimationSize(String s) {
		if (textureSizeCache.get(s) != null) {
			return ((Integer) textureSizeCache.get(s)).intValue();
		}
		try {
			InputStream inputstream = Minecraft
					.getMinecraft()
					.getResourceManager()
					.getResource(new ResourceLocation(EnergeticShielding.MODID, s))
					.getInputStream();
			if (inputstream == null) {
				throw new Exception("Image not found: " + s);
			}
			BufferedImage bi = ImageIO.read(inputstream);

			int size = bi.getWidth() / bi.getHeight();
			textureSizeCache.put(s, Integer.valueOf(size));
			return size;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 16;
	}

	public static int getTextureSize(String s, int dv) {
		if (textureSizeCache.get(Arrays.asList(new Serializable[] { s, Integer.valueOf(dv) })) != null) {
			return ((Integer) textureSizeCache.get(Arrays.asList(new Serializable[] { s, Integer.valueOf(dv) })))
					.intValue();
		}
		try {
			InputStream inputstream = Minecraft
					.getMinecraft()
					.getResourceManager()
					.getResource(new ResourceLocation(EnergeticShielding.MODID, s))
					.getInputStream();
			if (inputstream == null) {
				throw new Exception("Image not found: " + s);
			}
			BufferedImage bi = ImageIO.read(inputstream);

			int size = bi.getWidth() / dv;
			textureSizeCache.put(Arrays.asList(new Serializable[] { s, Integer.valueOf(dv) }), Integer.valueOf(size));
			return size;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 16;
	}

	private static Map textureSizeCache = new HashMap();

	public static int getBrightnessForRender(Entity entity, double x, double z) {
		int var2 = MathHelper.floor_double(x);
		int var3 = MathHelper.floor_double(z);
		if (entity.worldObj.blockExists(var2, 0, var3)) {
			double var4 = (entity.boundingBox.maxY - entity.boundingBox.minY) * 0.66D;
			int var6 = MathHelper.floor_double(entity.posY - entity.yOffset + var4);
			return entity.worldObj.getLightBrightnessForSkyBlocks(var2, var6, var3, 2);
		}
		return 0;
	}

	static Map<String, ResourceLocation> boundTextures = new HashMap();

	public static void bindTexture(String texture) {
		ResourceLocation rl = null;
		if (boundTextures.containsKey(texture)) {
			rl = boundTextures.get(texture);
		} else {
			rl = new ResourceLocation(EnergeticShielding.MODID, texture);
		}
		Minecraft.getMinecraft().renderEngine.bindTexture(rl);
	}

	public static void bindTexture(String mod, String texture) {
		ResourceLocation rl = null;
		if (boundTextures.containsKey(mod + ":" + texture)) {
			rl = boundTextures.get(mod + ":" + texture);
		} else {
			rl = new ResourceLocation(mod, texture);
		}
		Minecraft.getMinecraft().renderEngine.bindTexture(rl);
	}

	public static void bindTexture(ResourceLocation resource) {
		Minecraft.getMinecraft().renderEngine.bindTexture(resource);
	}

	static DecimalFormat myFormatter = new DecimalFormat("#######.##");

	public static boolean isVisibleTo(float fov, Entity ent, double x, double y, double z) {
		double dist = ent.getDistance(x, y, z);
		if (dist < 2.0D) {
			return true;
		}
		Minecraft mc = FMLClientHandler.instance().getClient();
		double vT = fov + mc.gameSettings.fovSetting / 2.0F;
		int j = 512;
		if (j > 400) {
			j = 400;
		}
		double rD = j;

		float f1 = MathHelper.cos(-ent.rotationYaw * 0.01745329F - 3.141593F);
		float f3 = MathHelper.sin(-ent.rotationYaw * 0.01745329F - 3.141593F);
		float f5 = -MathHelper.cos(-ent.rotationPitch * 0.01745329F);
		float f7 = MathHelper.sin(-ent.rotationPitch * 0.01745329F);
		double lx = f3 * f5;
		double ly = f7;
		double lz = f1 * f5;
		double dx = x + 0.5D - ent.posX;
		double dy = y + 0.5D - ent.posY - ent.getEyeHeight();
		double dz = z + 0.5D - ent.posZ;
		double len = Math.sqrt(dx * dx + dy * dy + dz * dz);
		double dot = dx / len * lx + dy / len * ly + dz / len * lz;
		double angle = Math.acos(dot);

		return angle < vT && mc.gameSettings.thirdPersonView == 0 && dist < rD
				|| mc.gameSettings.thirdPersonView > 0 && dist < rD;
	}

	public static void drawCustomTooltip(GuiScreen gui, RenderItem itemRenderer, FontRenderer fr, List var4, int par2,
			int par3, int subTipColor) {
		GL11.glDisable(32826);

		GL11.glDisable(2929);
		if (!var4.isEmpty()) {
			int var5 = 0;
			Iterator var6 = var4.iterator();
			while (var6.hasNext()) {
				String var7 = (String) var6.next();
				int var8 = fr.getStringWidth(var7);
				if (var8 > var5) {
					var5 = var8;
				}
			}
			int var15 = par2 + 12;
			int var16 = par3 - 12;
			int var9 = 8;
			if (var4.size() > 1) {
				var9 += 2 + (var4.size() - 1) * 10;
			}
			itemRenderer.zLevel = 300.0F;
			int var10 = -267386864;
			drawGradientRect(var15 - 3, var16 - 4, var15 + var5 + 3, var16 - 3, var10, var10);
			drawGradientRect(var15 - 3, var16 + var9 + 3, var15 + var5 + 3, var16 + var9 + 4, var10, var10);
			drawGradientRect(var15 - 3, var16 - 3, var15 + var5 + 3, var16 + var9 + 3, var10, var10);
			drawGradientRect(var15 - 4, var16 - 3, var15 - 3, var16 + var9 + 3, var10, var10);
			drawGradientRect(var15 + var5 + 3, var16 - 3, var15 + var5 + 4, var16 + var9 + 3, var10, var10);
			int var11 = 1347420415;
			int var12 = (var11 & 0xFEFEFE) >> 1 | var11 & 0xFF000000;
			drawGradientRect(var15 - 3, var16 - 3 + 1, var15 - 3 + 1, var16 + var9 + 3 - 1, var11, var12);
			drawGradientRect(var15 + var5 + 2, var16 - 3 + 1, var15 + var5 + 3, var16 + var9 + 3 - 1, var11, var12);
			drawGradientRect(var15 - 3, var16 - 3, var15 + var5 + 3, var16 - 3 + 1, var11, var11);
			drawGradientRect(var15 - 3, var16 + var9 + 2, var15 + var5 + 3, var16 + var9 + 3, var12, var12);
			for (int var13 = 0; var13 < var4.size(); var13++) {
				String var14 = (String) var4.get(var13);
				if (var13 == 0) {
					var14 = "§" + Integer.toHexString(subTipColor) + var14;
				} else {
					var14 = "§7" + var14;
				}
				fr.drawStringWithShadow(var14, var15, var16, -1);
				if (var13 == 0) {
					var16 += 2;
				}
				var16 += 10;
			}
		}
		itemRenderer.zLevel = 0.0F;
		GL11.glEnable(2929);
	}

	public static void drawGradientRect(int par1, int par2, int par3, int par4, int par5, int par6) {
		float var7 = (par5 >> 24 & 0xFF) / 255.0F;
		float var8 = (par5 >> 16 & 0xFF) / 255.0F;
		float var9 = (par5 >> 8 & 0xFF) / 255.0F;
		float var10 = (par5 & 0xFF) / 255.0F;
		float var11 = (par6 >> 24 & 0xFF) / 255.0F;
		float var12 = (par6 >> 16 & 0xFF) / 255.0F;
		float var13 = (par6 >> 8 & 0xFF) / 255.0F;
		float var14 = (par6 & 0xFF) / 255.0F;
		GL11.glDisable(3553);
		GL11.glEnable(3042);
		GL11.glDisable(3008);
		GL11.glBlendFunc(770, 771);
		GL11.glShadeModel(7425);
		Tessellator var15 = Tessellator.instance;
		var15.startDrawingQuads();
		var15.setColorRGBA_F(var8, var9, var10, var7);
		var15.addVertex(par3, par2, 300.0D);
		var15.addVertex(par1, par2, 300.0D);
		var15.setColorRGBA_F(var12, var13, var14, var11);
		var15.addVertex(par1, par4, 300.0D);
		var15.addVertex(par3, par4, 300.0D);
		var15.draw();
		GL11.glShadeModel(7424);
		GL11.glDisable(3042);
		GL11.glEnable(3008);
		GL11.glEnable(3553);
	}

	public static void drawFloatyGUILine(double x, double y, double x2, double y2, float partialTicks, int color,
			String texture, float speed, float distance) {
		GL11.glPushMatrix();
		GL11.glTranslated(x2, y2, 0.0D);

		float time = System.nanoTime() / 30000000L;

		Color co = new Color(color);
		float r = co.getRed() / 255.0F;
		float g = co.getGreen() / 255.0F;
		float b = co.getBlue() / 255.0F;

		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);

		Tessellator tessellator = Tessellator.instance;

		double dc1x = (float) (x - x2);
		double dc1y = (float) (y - y2);

		bindTexture(texture);

		tessellator.startDrawing(5);

		double d3 = x - x2;
		double d4 = y - y2;

		float dist = MathHelper.sqrt_double(d3 * d3 + d4 * d4);
		double dx = d3 / dist;
		double dy = d4 / dist;

		GL11.glRotated((float) -(Math.atan2(d3, d4) * 180.0D / 3.141592653589793D) + 90.0F, 0.0D, 0.0D, 1.0D);

		float blocks = Math.round(dist);
		float length = blocks * distance;
		float f9 = 0.0F;
		float f10 = 1.0F;
		float sec = 1.0F / length;
		for (int i = 0; i <= length; i++) {
			float f2 = i / length;

			tessellator.setColorRGBA_F(r, g, b, 1.0F);

			float f13 = (1.0F - f2) * length;
			float f14 = (1.0F - f2) * length + sec;
			float width = 1.0F;
			tessellator.addVertexWithUV(dx * i, 0.0F - width, 0.0D, f13 / width, f10);
			tessellator.addVertexWithUV(dx * i, 0.0F + width, 0.0D, f14 / width, f9);
		}
		tessellator.draw();

		GL11.glDisable(3042);

		GL11.glPopMatrix();
	}

	public static float getEquippedProgress(ItemRenderer ir) {
		try {
			return ((Float) ReflectionHelper.getPrivateValue(ItemRenderer.class, ir,
					new String[] { "equippedProgress", "f", "field_78454_c" })).floatValue();
		} catch (Exception e) {
		}
		return 0.0F;
	}

	public static float getPrevEquippedProgress(ItemRenderer ir) {
		try {
			return ((Float) ReflectionHelper.getPrivateValue(ItemRenderer.class, ir,
					new String[] { "prevEquippedProgress", "g", "field_78451_d" })).floatValue();
		} catch (Exception e) {
		}
		return 0.0F;
	}

	public static Timer getTimer(Minecraft mc) {
		try {
			return (Timer) ReflectionHelper.getPrivateValue(Minecraft.class, mc,
					new String[] { "timer", "Q", "field_71428_T" });
		} catch (Exception e) {
		}
		return new Timer(20.0F);
	}

	public static int getGuiXSize(GuiContainer gui) {
		try {
			return ((Integer) ReflectionHelper.getPrivateValue(GuiContainer.class, gui,
					new String[] { "xSize", "f", "field_146999_f" })).intValue();
		} catch (Exception e) {
		}
		return 0;
	}

	public static int getGuiYSize(GuiContainer gui) {
		try {
			return ((Integer) ReflectionHelper.getPrivateValue(GuiContainer.class, gui,
					new String[] { "ySize", "g", "field_147000_g" })).intValue();
		} catch (Exception e) {
		}
		return 0;
	}

	public static float getGuiZLevel(Gui gui) {
		try {
			return ((Float) ReflectionHelper.getPrivateValue(Gui.class, gui,
					new String[] { "zLevel", "e", "field_73735_i" })).floatValue();
		} catch (Exception e) {
		}
		return 0.0F;
	}

	public static ResourceLocation getParticleTexture() {
		try {
			return (ResourceLocation) ReflectionHelper.getPrivateValue(EffectRenderer.class, null,
					new String[] { "particleTextures", "b", "field_110737_b" });
		} catch (Exception e) {
		}
		return null;
	}
}
