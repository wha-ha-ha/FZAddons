package fza.base.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.OreDictionary;
import factorization.oreprocessing.TileEntityGrinder;
import factorization.oreprocessing.TileEntityGrinder.GrinderRecipe;

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
		
		if(!OreDictionary.getOres("oreTungstate").isEmpty() && !OreDictionary.getOres("ingotTungsten").isEmpty()) {
			ingotTransforms.put("Tungstate", "Tungsten");
		}
		
		if(!OreDictionary.getOres("oreCooperite").isEmpty() && !OreDictionary.getOres("ingotPlatinum").isEmpty()) {
			ingotTransforms.put("Cooperite", "Platinum");
		}
		
		if(!OreDictionary.getOres("oreTetrahedrite").isEmpty() && !OreDictionary.getOres("ingotCopper").isEmpty()) {
			ingotTransforms.put("Tetrahedrite", "Copper");
		}
		
		if(!OreDictionary.getOres("oreGalena").isEmpty() && !OreDictionary.getOres("ingotSilver").isEmpty() && !OreDictionary.getOres("ingotLead").isEmpty()) {
			ingotTransforms.put("Galena", "Lead");
		}
		
		if(!OreDictionary.getOres("oreCassiterite").isEmpty() && !OreDictionary.getOres("ingotTin").isEmpty()) {
			ingotTransforms.put("Cassiterite", "Tin");
		}
		
		if(!OreDictionary.getOres("orePyrite").isEmpty() && !OreDictionary.getOres("ingotIron").isEmpty()) {
			ingotTransforms.put("Pyrite", "Pyrite");
		}
		
		if(!OreDictionary.getOres("oreSphalerite").isEmpty() && !OreDictionary.getOres("ingotZinc").isEmpty()) {
			int index = ("Zinc".hashCode() % ORENAME_HASHSPACE/2) + ORENAME_HASHSPACE/2;
			postfixes[index] = "Zinc";
			ingotTransforms.put("Sphalerite", "Sphalerite");
			ingotTransforms.put("Zinc", "Zinc");
		}
		
		if(!OreDictionary.getOres("oreYellorite").isEmpty()) {
			ItemStack smelt = FurnaceRecipes.smelting().getSmeltingResult(OreDictionary.getOres("oreYellorite").get(0));
			if(smelt != null) {
				int oreid = OreDictionary.getOreID(smelt);
				if(oreid != -1) {
					String name = OreDictionary.getOreName(oreid);
					if(name.startsWith("ingot")) {
						ingotTransforms.put("Yellorite", name.substring(5));
					}
				}
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
