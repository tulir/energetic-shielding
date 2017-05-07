package net.maunium.energeticshielding.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import net.maunium.energeticshielding.EnergeticShielding;

import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.relauncher.ReflectionHelper;

public class BlockProtectedFX extends EntityFX {
	ForgeDirection side;

	public BlockProtectedFX(World world, double d, double d1, double d2, ForgeDirection side, float f, float f1,
			float f2) {
		super(world, d, d1, d2, 0.0, 0.0, 0.0);

		this.side = side;

		particleGravity = 0.0F;
		motionX = motionY = motionZ = 0.0D;
		particleMaxAge = 12 + rand.nextInt(5);
		noClip = false;
		setSize(0.01F, 0.01F);
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		noClip = true;
		particleScale = (float) (1.4D + rand.nextGaussian() * 0.30000001192092896D);
		rotation = rand.nextInt(360);
		sx = MathHelper.clamp_float(f - 0.6F + rand.nextFloat() * 0.2F, -0.4F, 0.4F);
		sy = MathHelper.clamp_float(f1 - 0.6F + rand.nextFloat() * 0.2F, -0.4F, 0.4F);
		sz = MathHelper.clamp_float(f2 - 0.6F + rand.nextFloat() * 0.2F, -0.4F, 0.4F);
		if (side.offsetX != 0) {
			sx = 0.0F;
		}
		if (side.offsetY != 0) {
			sy = 0.0F;
		}
		if (side.offsetZ != 0) {
			sz = 0.0F;
		}
	}

	int rotation = 0;
	float sx = 0.0F;
	float sy = 0.0F;
	float sz = 0.0F;

	@Override
	public void renderParticle(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5) {
		tessellator.draw();
		GL11.glPushMatrix();
		Minecraft.getMinecraft().renderEngine
				.bindTexture(new ResourceLocation(EnergeticShielding.MODID, "textures/misc/protection.png"));

		GL11.glDepthMask(false);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 1);

		GL11.glColor4f(1.0F, 1.0F, 1.0F, particleAlpha / 2.0F);

		float var13 = (float) (prevPosX + (posX - prevPosX) * f - interpPosX);
		float var14 = (float) (prevPosY + (posY - prevPosY) * f - interpPosY);
		float var15 = (float) (prevPosZ + (posZ - prevPosZ) * f - interpPosZ);

		GL11.glTranslated(var13 + sx, var14 + sy, var15 + sz);

		GL11.glRotatef(90.0F, side.offsetY, -side.offsetX, side.offsetZ);
		GL11.glRotatef(rotation, 0.0F, 0.0F, 1.0F);
		if (side.offsetZ > 0) {
			GL11.glTranslated(0.0D, 0.0D, 0.505);
			GL11.glRotatef(180.0F, 0.0F, -1.0F, 0.0F);
		} else {
			GL11.glTranslated(0.0D, 0.0D, -0.505);
		}
		float var12 = particleScale;

		float var16 = 1.0F;

		tessellator.startDrawingQuads();
		tessellator.setBrightness(240);
		tessellator.setColorRGBA_F(particleRed * var16, particleGreen * var16, particleBlue * var16,
				particleAlpha / 2.0F);

		tessellator.addVertexWithUV(-0.5D * var12, 0.5D * var12, 0.0D, 0.0D, 1.0D);
		tessellator.addVertexWithUV(0.5D * var12, 0.5D * var12, 0.0D, 1.0D, 1.0D);
		tessellator.addVertexWithUV(0.5D * var12, -0.5D * var12, 0.0D, 1.0D, 0.0D);
		tessellator.addVertexWithUV(-0.5D * var12, -0.5D * var12, 0.0D, 0.0D, 0.0D);
		tessellator.draw();

		GL11.glDisable(3042);
		GL11.glDepthMask(true);

		GL11.glPopMatrix();
		Minecraft.getMinecraft().renderEngine.bindTexture(getParticleTexture());
		tessellator.startDrawingQuads();
	}

	public ResourceLocation getParticleTexture() {
		try {
			return (ResourceLocation) ReflectionHelper.getPrivateValue(EffectRenderer.class, null,
					new String[] { "particleTextures", "b", "field_110737_b" });
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public void onUpdate() {
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		float threshold = particleMaxAge / 5.0F;
		if (particleAge <= threshold) {
			particleAlpha = particleAge / threshold;
		} else {
			particleAlpha = (particleMaxAge - particleAge) / particleMaxAge;
		}
		if (particleAge++ >= particleMaxAge) {
			setDead();
		}
		motionY -= 0.04D * particleGravity;

		posX += motionX;
		posY += motionY;
		posZ += motionZ;
	}

	public void setGravity(float value) {
		particleGravity = value;
	}
}
