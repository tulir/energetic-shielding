package net.maunium.energeticshielding.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

import net.maunium.energeticshielding.block.BlockProtected;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class BlockProtectedRenderer extends BlockRenderer implements ISimpleBlockRenderingHandler {
	public static int renderID;

	public BlockProtectedRenderer() {
		BlockProtectedRenderer.renderID = RenderingRegistry.getNextAvailableRenderId();
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
			RenderBlocks renderer) {
		return renderer.renderBlockByRenderType(((BlockProtected) block).getBlock(world, x, y, z), x, y, z);
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return BlockProtectedRenderer.renderID;
	}
}
