package net.maunium.energeticshielding.interop;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

public final class Wrench {

	private static ArrayList<Class<?>> wrenchClasses = new ArrayList<Class<?>>();
	private static String[] wrenchNames = new String[] { "buildcraft.api.tools.IToolWrench", // Buildcraft
			"resonant.core.content.ItemScrewdriver", // Resonant
			"ic2.core.item.tool.ItemToolWrench", // IC2
			"ic2.core.item.tool.ItemToolWrenchElectric", // IC2
			"mrtjp.projectred.api.IScrewdriver", // Project Red
			"mods.railcraft.api.core.items.IToolCrowbar", // Railcraft
			"com.bluepowermod.items.ItemScrewdriver", // BluePower
			"cofh.api.item.IToolHammer", // Thermal Expansion
			"appeng.items.tools.quartz.ToolQuartzWrench", // Applied Energistics
			"crazypants.enderio.api.tool.ITool", // Ender IO
			"mekanism.api.IMekWrench", // Mekanism
	};

	public static void init() {
		for (String className : wrenchNames) {
			try {
				wrenchClasses.add(Class.forName(className));
			} catch (ClassNotFoundException e) {
			}
		}
	}

	public static boolean isWrench(ItemStack stk) {
		for (Class<?> c : wrenchClasses) {
			if (stk != null && stk.getItem() != null && (c.isInstance(stk.getItem()))) {
				return true;
			}
		}
		return false;
	}

}