package fza.base.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.OreDictionary;
import factorization.oreprocessing.TileEntityGrinder;
import factorization.oreprocessing.TileEntityGrinder.GrinderRecipe;
import fza.base.config.ConfigurationHandler;

public class OreDictionaryUtil {

	private static final int ORENAME_HASHSPACE = 8192;
	public static final HashSet<String> forbiddens = new HashSet<String>();
	public static final String[] postfixes = new String[ORENAME_HASHSPACE];
	public static final HashMap<String, String> ingotTransforms = new HashMap<String, String>();
	public static final HashMap<String, String> gemTransforms = new HashMap<String, String>();
	public static final HashMap<String, String> dustTransforms = new HashMap<String, String>();
	
	public static void initOreMaps() {
		
		forbiddens.add("FzDarkIron");
		forbiddens.add("DarkIron");
		forbiddens.add("Lead");
		forbiddens.add("Tin");
		forbiddens.add("Iron");
		forbiddens.add("Copper");
		forbiddens.add("Gold");
		forbiddens.add("Silver");
		forbiddens.add("Cobalt");
		forbiddens.add("Ardite");
		
		String[] bizarres = {"Tungstate", "Cooperite", "Tetrahedrite", "Cassiterite", 
				"Pyrite", "Sphalerite", "Yellorite"};
		
		for(String s : OreDictionary.getOreNames()) {
			if(s.startsWith("ore")) {
				String postfix = s.substring(3);
				if(!OreDictionary.getOres(s).isEmpty() && !forbiddens.contains(postfix)) {
					int index = (postfix.hashCode() % ORENAME_HASHSPACE/2) + ORENAME_HASHSPACE/2;
					postfixes[index] = postfix;
				}
			}
		}
		
		for(String s : ConfigurationHandler.customIngotMappings) {
			try {
				String[] ingotSplit = s.split(":");
				if(!OreDictionary.getOres("ore"+ingotSplit[0]).isEmpty() || !OreDictionary.getOres("ingot"+ingotSplit[1]).isEmpty()) {
					int index = (ingotSplit[0].hashCode() % ORENAME_HASHSPACE/2) + ORENAME_HASHSPACE/2;
					postfixes[index] = ingotSplit[0];
					ingotTransforms.put(ingotSplit[0], ingotSplit[1]);
				}
			}catch(Throwable t) {
				System.err.println("GOT BAD CUSTOM ORE TRANSFORM CONFIG "+s);
			}
		}
		
		HashSet<String> blackList = new HashSet<String>();
		blackList.add("Diamond");
		blackList.add("Redstone");
		blackList.add("Coal");
		blackList.add("NetherQuartz");
		blackList.add("Galena");
		
		for(String s : postfixes) {
			if(!OreDictionary.getOres("ingot"+s).isEmpty() && !blackList.contains(s)) {
				ingotTransforms.put(s, s);
			}
			if(!OreDictionary.getOres("gem"+s).isEmpty() && !ingotTransforms.containsKey(s) && !blackList.contains(s)) {
				gemTransforms.put(s, s);	
			}
			if(!OreDictionary.getOres("dust"+s).isEmpty() && !ingotTransforms.containsKey(s) && !gemTransforms.containsKey(s) && !blackList.contains(s)) {
				dustTransforms.put(s, s);
			}
		}
		
		printSets();
		
	}
	
	private static void printSets() {
		
		System.out.println("INGOTS {");
		for(String s : ingotTransforms.keySet()) {
			System.out.println(s+", "+ingotTransforms.get(s));
		}
		System.out.println("}");
		
		System.out.println("GEMS {");
		for(String s : gemTransforms.keySet()) {
			System.out.println(s+", "+gemTransforms.get(s));
		}
		System.out.println("}");
		
		System.out.println("DUSTS {");
		for(String s : dustTransforms.keySet()) {
			System.out.println(s+", "+dustTransforms.get(s));
		}
		System.out.println("}");
		
	}
	
}
