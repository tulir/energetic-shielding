package net.maunium.energeticshielding;

import net.minecraft.creativetab.CreativeTabs;

import net.maunium.energeticshielding.proxy.CommonProxy;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = EnergeticShielding.MODID, name = EnergeticShielding.MODNAME, version = EnergeticShielding.VERSION)
public class EnergeticShielding {
	public static final String MODID = "energeticshielding";
	public static final String MODNAME = "Energetic Shielding";
	public static final String VERSION = "0.1.0";
	@SidedProxy(clientSide = "net.maunium.energeticshielding.proxy.ClientProxy",
			serverSide = "net.maunium.energeticshielding.proxy.ServerProxy")
	public static CommonProxy proxy;
	public static CreativeTabs tab;

	@Instance
	public static EnergeticShielding instance = new EnergeticShielding();

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		proxy.preInit(e);
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
		proxy.init(e);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		proxy.postInit(e);
	}

	public static String texture(String item) {
		return EnergeticShielding.MODID + ":" + item;
	}
}
