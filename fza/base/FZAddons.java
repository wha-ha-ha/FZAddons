package fza.base;

import java.io.File;


import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import fza.base.config.ConfigurationHandler;
import fza.base.items.FZAItems;
import fza.base.proxy.CommonProxy;
import fza.base.proxy.PacketHandler;
import fza.base.util.ColoringUtil;
import fza.base.util.OreDictionaryUtil;
import fza.base.util.RecipeGymnastics;

@Mod(modid = "FZAddons", name = "FZAddons", version = FZAddons.VERSION, dependencies = "required-after:factorization")
@NetworkMod(serverSideRequired = true, clientSideRequired = true, channels = { "fza" }, packetHandler = PacketHandler.class)
public class FZAddons {
	
	public static final String VERSION = "0.0.1";

	@Instance("FZAddons")
	public static FZAddons instance;
	
	@SidedProxy(clientSide = "fza.base.proxy.ClientProxy", serverSide = "fza.base.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	public static String configPath;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		configPath = event.getModConfigurationDirectory().getAbsolutePath()+"/FZAddons/";
		ConfigurationHandler.init(new File(configPath+"FZAddons.cfg"));
		FZAItems.initItems();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.initRenderers();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		OreDictionaryUtil.initOreMaps();
		FZAItems.addNames();
		FZAItems.registerOreDict();
		RecipeGymnastics.initBase();
		RecipeGymnastics.doGymnastics();
	}
}
