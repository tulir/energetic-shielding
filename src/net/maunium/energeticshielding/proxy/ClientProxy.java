package net.maunium.energeticshielding.proxy;

import net.minecraft.world.World;

import net.maunium.energeticshielding.render.BlockProtectedFX;
import net.maunium.energeticshielding.render.MauRenderers;

import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
	@Override
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);
	}

	@Override
	public void init(FMLInitializationEvent e) {
		MauRenderers.init();
		super.init(e);
	}

	@Override
	public void postInit(FMLPostInitializationEvent e) {
		super.postInit(e);
	}

	@Override
	public void blockProtectedFX(World world, double x, double y, double z, ForgeDirection side, float f, float f1,
			float f2) {
		BlockProtectedFX fx = new BlockProtectedFX(world, x + 0.5D, y + 0.5D, z + 0.5D, side, f, f1, f2);

		FMLClientHandler.instance().getClient().effectRenderer.addEffect(fx);
	}
}
