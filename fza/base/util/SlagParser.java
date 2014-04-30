package fza.base.util;

import java.util.LinkedList;

import net.minecraft.item.ItemStack;
import fza.base.config.SlagConfigOption;

public class SlagParser {
	
	public static LinkedList<SlagConfigOption> customSlagRecipes;
	
	public static void initCustomRecipes(String[] configStrings) {
		
		customSlagRecipes = new LinkedList<SlagConfigOption>();
		
		for(String s : configStrings) {
			try {
				
				String[] splitConfig = s.split(":");
				String in = splitConfig[0];
				String mainOut = splitConfig[1];
				float mainMult = Float.parseFloat(splitConfig[2]);
				String secondOut = splitConfig[3];
				float secondMult = Float.parseFloat(splitConfig[4]);
				float fallbackMult = Float.parseFloat(splitConfig[5]);
				
				customSlagRecipes.add(new SlagConfigOption(in, mainOut, mainMult, secondOut, secondMult, fallbackMult));
				
			}catch(Throwable t){
				System.err.println("GOT BAD SLAG FURNACE RECIPE CONFIG "+s);
			}
		}
		
	}
	
}
