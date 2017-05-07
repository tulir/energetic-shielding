package net.maunium.energeticshielding.render;

import net.maunium.energeticshielding.tile.TileProtected;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public final class MauRenderers {
	public static final void init() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileProtected.class, new TileProtectedRenderer());
		RenderingRegistry.registerBlockHandler(new BlockProtectedRenderer());
	}
}
