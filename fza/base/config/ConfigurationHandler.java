package fza.base.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import net.minecraftforge.common.Configuration;
import fza.base.items.FZAItemInfo;
import fza.base.util.ColoringUtil;

public class ConfigurationHandler {
	
	private static File cfg;
	
	
	public static void init(File file) {
		
		cfg = file;
		
		Configuration config = new Configuration(file);
		config.load();

		FZAItemInfo.DIRTY_ID = config.getItem(FZAItemInfo.DIRTY_KEY, FZAItemInfo.DIRTY_DEFAULT).getInt();
		FZAItemInfo.CLEAN_ID = config.getItem(FZAItemInfo.CLEAN_KEY, FZAItemInfo.CLEAN_DEFAULT).getInt();
		FZAItemInfo.REDUCED_ID = config.getItem(FZAItemInfo.REDUCED_KEY, FZAItemInfo.REDUCED_DEFAULT).getInt();
		FZAItemInfo.CRYSTAL_ID = config.getItem(FZAItemInfo.CRYSTAL_KEY, FZAItemInfo.CRYSTAL_DEFAULT).getInt();
		
		String[] rawColorStringArray = config.get("ORE COLORS", "colors", new String[]{"Osmium:699BD1", "Nickel:C4C795", "Platinum:40D6FF"}, 
				"Hex color values for this mod's meta items.\nFormat: DictPostfix:HexValue - e.g. Osmium:699BD1").getStringList();
		ColoringUtil.init(rawColorStringArray);
		
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
