package net.maunium.energeticshielding.tile;

import cpw.mods.fml.common.registry.GameRegistry;

public final class MauTiles {
	public static final void init() {
		GameRegistry.registerTileEntity(TileProtected.class, "tileProtected");
	}
}
