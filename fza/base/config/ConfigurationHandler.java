package fza.base.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import net.minecraftforge.common.Configuration;
import fza.base.items.FZAItemInfo;
import fza.base.util.ColoringUtil;
import fza.base.util.SlagParser;

public class ConfigurationHandler {
	
	private static File cfg;
	public static final HashSet<String> smeltingBlackList = new HashSet<String>();
	public static final HashMap<String, Float> customOreMults = new HashMap<String, Float>();
	public static String[] customIngotMappings;
	public static boolean wrathIgniterRevert;
	
	public static void init(File file) {
		
		cfg = file;
		
		Configuration config = new Configuration(file);
		config.load();

		FZAItemInfo.DIRTY_ID = config.getItem(FZAItemInfo.DIRTY_KEY, FZAItemInfo.DIRTY_DEFAULT).getInt() - 256;
		FZAItemInfo.CLEAN_ID = config.getItem(FZAItemInfo.CLEAN_KEY, FZAItemInfo.CLEAN_DEFAULT).getInt() - 256;
		FZAItemInfo.REDUCED_ID = config.getItem(FZAItemInfo.REDUCED_KEY, FZAItemInfo.REDUCED_DEFAULT).getInt() - 256;
		FZAItemInfo.CRYSTAL_ID = config.getItem(FZAItemInfo.CRYSTAL_KEY, FZAItemInfo.CRYSTAL_DEFAULT).getInt() - 256;
		
		wrathIgniterRevert = config.get("OPTIONS", "wrathIgniterRevert", true, "Overrides the Wrath Igniter to make it spawn Wrath Fire and burn Iron Blocks to Dark Iron again!").getBoolean(true);
		
		for(String s : config.get("SMELTING BLACKLIST", "smeltingBlacklist",
				new String[]{
				
				"dirtyGravelTungsten",
				"cleanGravelTungsten",
				"oreTungsten",
				"oreTungstate",
				"reducedTungsten",
				"dirtyGravelSilver",
				"cleanGravelSilver"
				
				}, 
				"Ore dict names to keep from smelting into ingots.").getStringList()) {
			
			smeltingBlackList.add(s);
			
		}
		
		for(String s : config.get("CUSTOM ORE OUTPUT MULTIPLIERS", "customOreMults",
				new String[]{
				
				"Bauxite:20",
				"Sodalite:6",
				"Saltpeter:4.125",
				"Sulfur:4.125",
				"Apatite:4.8",
				"Cassiterite:2"
				
				}, 
				"Specify ore types whose direct outputs (putting the ore directly in a machine) should be multiplied by a certain amount.\nDusts and gravels default out to 2x from the lacerator,\nGems default out to 2.5x from the lacerator.\nIf you wanted, say 12 gems out of an ore, you'd want to divide that number by the default (12/2.5) and put that result here.").getStringList()) {
			
			try {
				String[] split = s.split(":");
				float mult = Float.parseFloat(split[1]);
				customOreMults.put(split[0], mult);
			}catch(Throwable t) {
				System.err.println("GOT BAD CUSTOM ORE OUTPUT CONFIG "+s);
			}
			
		}
		
		String[] rawColorStringArray = config.get("ORE COLORS", "colors",
				new String[]{
				
				"Osmium:699BD1",
				"Nickel:DED897",
				"Platinum:0AE7FF",
				"Cooperite:0AE7FF",
				"Yellorium:C8DB1A",
				"Yellorite:C8DB1A",
				"Iridium:FFFFFF",
				"Tungsten:20132B",
				"Tungstate:20132B",
				"Pyrite:DB613B",
				"Zinc:FFDEDE",
				"Uranium:59944E",
				"Aluminum:B4E7ED",
				"Aluminium:B4E7ED",
				"Sphalerite:B8963B"
				
				}, 
				"Hex color values for this mod's meta items.\nFormat: DictPostfix:HexValue - e.g. Osmium:699BD1").getStringList();
		
		customIngotMappings = config.get("CUSTOM ORE MAPPINGS", "customOreMappings",
				new String[]{
				
				"Tungstate:Tungsten",
				"Sphalerite:Sphalerite",
				"Zinc:Zinc",
				"Tetrahedrite:Copper",
				"Galena:Lead",
				"Cassiterite:Tin",
				"Pyrite:Pyrite",
				"Cooperite:Platinum",
				"Yellorite:Yellorium"
				
				}, 
				"Map ore dict postfixes for ore types to metal types coming from them that are different from their ore types OR that don't have an ingot.").getStringList();
		
		ColoringUtil.init(rawColorStringArray);
		
		String[] slagConfigs = config.get("CUSTOM SLAG FURNACE RECIPES", "customSlag",
				new String[]{
				
				"cleanGravelSphalerite:reducedZinc:.5:dustSulfur:1:.25",
				"cleanGravelPyrite:reducedIron:.5:dustSulfur:1:.25",
				"cleanGravelGalena:reducedSilver:1.25:reducedLead:1.25:.625",
				"cleanGravelCopper:reducedCopper:1.25:reducedGold:.065:.625",
				"cleanGravelLead:reducedLead:1.25:reducedSilver:.065:.625",
				"cleanGravelGold:reducedGold:1.25:reducedSilver:.065:.625",
				"cleanGravelIron:reducedIron:1.25:reducedNickel:.065:.625",
				"cleanGravelTin:reducedTin:1.25:reducedIron:.065:.625",
				"cleanGravelNickel:reducedNickel:1.25:reducedPlatinum:.065:.625",
				"cleanGravelPlatinum:reducedPlatinum:1.25:reducedIridium:.065:.625",
				"cleanGravelTungsten:reducedTungsten:1.25:dustManganese:.065:.625",
				"cleanGravelUranium:reducedUranium:1.25:dustPlutonium:.065:.625",
				"cleanGravelTin:reducedTin:1.25:reducedIron:.065:.625"
				
				},
				"Specify custom Slag Furnace recipes! Format:\ninputOreDictName:mainOutputOreDictName:mainOutputAmount:secondaryOutputOreDictName:secondaryOutputAmount:fallBackAmount\n\"Fallback\" amount specifies a multiplier to use when putting the main output into both output slots,\nin the event that the secondary output doesn't exist in the ore dictionary.\n\nTHIS IS JUST FOR ADDING FANCY RECIPES FOR THINGS.\n If an ore dicted ore has a corresponding ingot and isn't specified here, the mod will automatically generate slag recipes for it, so don't worry!").getStringList();
		
		SlagParser.initCustomRecipes(slagConfigs);
		
		config.save();
	}

	/**
	 * Sets a config value manually by editing the text file
	 * @param prefix - The prefix of the config option (anythin before the '=')
	 * @param from - The setting to change it from 
	 * @param to - The setting to change it to
	 */
	public static void manuallyChangeConfigValue(String prefix, String from, String to)
	{
		try
		{
			FileReader fr1 = new FileReader(cfg);
			BufferedReader read = new BufferedReader(fr1);
			
			ArrayList<String> strings = new ArrayList<String>();
			
			while (read.ready())
			{
				strings.add(read.readLine());
			}
			
			fr1.close();
			read.close();
			
			FileWriter fw = new FileWriter(cfg);
			BufferedWriter bw = new BufferedWriter(fw);
			
			for (String s : strings)
			{
				if (s.equals("    " + prefix + "=" + from))
					s = "    " + prefix + "=" + to;
				
				fw.write(s + "\n");
			}	
			
			bw.flush();
			bw.close();
		}
		catch (Throwable t)
		{
			t.printStackTrace();
		}
	}
}
