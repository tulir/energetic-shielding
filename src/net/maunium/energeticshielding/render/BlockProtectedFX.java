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

		this.particleGravity = 0.0F;
		this.motionX = this.motionY = this.motionZ = 0.0D;
		this.particleMaxAge = 12 + this.rand.nextInt(5);
		this.noClip = false;
		this.setSize(0.01F, 0.01F);
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		this.noClip = true;
		this.particleScale = (float) (1.4D + this.rand.nextGaussian() * 0.30000001192092896D);
		this.rotation = this.rand.nextInt(360);
		this.sx = MathHelper.clamp_float(f - 0.6F + this.rand.nextFloat() * 0.2F, -0.4F, 0.4F);
		this.sy = MathHelper.clamp_float(f1 - 0.6F + this.rand.nextFloat() * 0.2F, -0.4F, 0.4F);
		this.sz = MathHelper.clamp_float(f2 - 0.6F + this.rand.nextFloat() * 0.2F, -0.4F, 0.4F);
		if (side.offsetX != 0) {
			this.sx = 0.0F;
		}
		if (side.offsetY != 0) {
			this.sy = 0.0F;
		}
		if (side.offsetZ != 0) {
			this.sz = 0.0F;
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
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, 1);

		GL11.glColor4f(1.0F, 1.0F, 1.0F, this.particleAlpha / 2.0F);

		float var13 = (float) (this.prevPosX + (this.posX - this.prevPosX) * f - interpPosX);
		float var14 = (float) (this.prevPosY + (this.posY - this.prevPosY) * f - interpPosY);
		float var15 = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * f - interpPosZ);

		GL11.glTranslated(var13 + this.sx, var14 + this.sy, var15 + this.sz);

		GL11.glRotatef(90.0F, this.side.offsetY, -this.side.offsetX, this.side.offsetZ);
		GL11.glRotatef(this.rotation, 0.0F, 0.0F, 1.0F);
		if (this.side.offsetZ > 0) {
			GL11.glTranslated(0.0D, 0.0D, 0.505);
			GL11.glRotatef(180.0F, 0.0F, -1.0F, 0.0F);
		} else {
			GL11.glTranslated(0.0D, 0.0D, -0.505);
		}
		float var12 = this.particleScale;

		float var16 = 1.0F;

		tessellator.startDrawingQuads();
		tessellator.setBrightness(240);
		tessellator.setColorRGBA_F(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16,
				this.particleAlpha / 2.0F);

		tessellator.addVertexWithUV(-0.5D * var12, 0.5D * var12, 0.0D, 0.0D, 1.0D);
		tessellator.addVertexWithUV(0.5D * var12, 0.5D * var12, 0.0D, 1.0D, 1.0D);
		tessellator.addVertexWithUV(0.5D * var12, -0.5D * var12, 0.0D, 1.0D, 0.0D);
		tessellator.addVertexWithUV(-0.5D * var12, -0.5D * var12, 0.0D, 0.0D, 0.0D);
		tessellator.draw();

		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDepthMask(true);

		GL11.glPopMatrix();
		Minecraft.getMinecraft().renderEngine.bindTexture(this.getParticleTexture());
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
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		float threshold = this.particleMaxAge / 5.0F;
		if (this.particleAge <= threshold) {
			this.particleAlpha = this.particleAge / threshold;
		} else {
			this.particleAlpha = (this.particleMaxAge - this.particleAge) / this.particleMaxAge;
		}
		if (this.particleAge++ >= this.particleMaxAge) {
			this.setDead();
		}
		this.motionY -= 0.04D * this.particleGravity;

		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;
	}

	public void setGravity(float value) {
		this.particleGravity = value;
	}
}
