package fza.base.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;
import factorization.oreprocessing.TileEntityCrystallizer;
import factorization.oreprocessing.TileEntityGrinder;
import factorization.oreprocessing.TileEntityGrinder.GrinderRecipe;
import factorization.oreprocessing.TileEntitySlagFurnace;
import factorization.oreprocessing.TileEntitySlagFurnace.SmeltingResult;
import factorization.shared.Core;
import fza.base.config.ConfigurationHandler;
import fza.base.config.SlagConfigOption;

public class RecipeGymnastics {

	public static final HashSet<String> cleans = new HashSet<String>();
	
	public static void doGymnastics() {
		
		addMetalRecipes();
		addGemRecipes();
		addDustRecipes();
		doSlagFurnaceShenanigans();
		addSmeltingAndCrystallizing();
		
	}
	
	public static void initBase() {
		
		HashSet<Integer> forbiddenIDs = new HashSet<Integer>();
		forbiddenIDs.add(Item.diamond.itemID);
		forbiddenIDs.add(Item.redstone.itemID);
		forbiddenIDs.add(Item.coal.itemID);
		forbiddenIDs.add(Item.emerald.itemID);
		
		
		Iterator<GrinderRecipe> it = TileEntityGrinder.recipes.iterator();
		
		while(it.hasNext()) {
			GrinderRecipe r = it.next();
			String outputName = OreDictionary.getOreName(OreDictionary.getOreID(r.output));
			if(forbiddenIDs.contains(r.output.itemID) || (outputName != null && (outputName.startsWith("oreNether") || (outputName.startsWith("ore") && !OreDictionaryUtil.forbiddens.contains(outputName.substring(3)))))) {
				it.remove();
			}
		}
		
		if(!OreDictionary.getOres("dustWood").isEmpty()) {
			TileEntityGrinder.addRecipe("logWood", OreDictionary.getOres("dustWood").get(0).copy(), 1F);
			if(!OreDictionary.getOres("woodLog").isEmpty()) {
				TileEntityGrinder.addRecipe("woodLog", OreDictionary.getOres("dustWood").get(0).copy(), 1F);
			}
			
		}else if(!OreDictionary.getOres("pulpWood").isEmpty()) {
			TileEntityGrinder.addRecipe("logWood", OreDictionary.getOres("pulpWood").get(0).copy(), 1F);
			if(!OreDictionary.getOres("woodLog").isEmpty()) {
				TileEntityGrinder.addRecipe("woodLog", OreDictionary.getOres("pulpWood").get(0).copy(), 1F);
			}
		}
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Item.paper, 3), new Object[]{"dustWood", "dustWood", "dustWood"}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Item.paper, 3), new Object[]{"pulpWood", "pulpWood", "pulpWood"}));
		
		for(String s : OreDictionaryUtil.forbiddens) {
			if(!OreDictionary.getOres("oreNether"+s).isEmpty()) {
				if(!s.equals("Silver")) {
					TileEntityGrinder.addRecipe("oreNether"+s, OreDictionary.getOres("dirtyGravel"+s).get(0).copy(), 4F);
				}
			}
		}
		
		if(!OreDictionary.getOres("oreNetherLapis").isEmpty()) {
			TileEntityGrinder.addRecipe("oreNetherLapis", new ItemStack(Item.dyePowder, 1, 4), 25.5F);
		}
		
		if(OreDictionary.getOres("oreDiamond").isEmpty()) {
			TileEntityGrinder.addRecipe(Block.oreDiamond, new ItemStack(Item.diamond), 2.5F);
		}else {
			TileEntityGrinder.addRecipe("oreDiamond", new ItemStack(Item.diamond), 2.5F);
		}
		if(!OreDictionary.getOres("oreNetherDiamond").isEmpty()) {
			TileEntityGrinder.addRecipe("oreNetherDiamond", new ItemStack(Item.diamond), 5F);
		}
		
		if(OreDictionary.getOres("oreEmerald").isEmpty()) {
			TileEntityGrinder.addRecipe(Block.oreEmerald, new ItemStack(Item.emerald), 2.5F);
		}else {
			TileEntityGrinder.addRecipe("oreEmerald", new ItemStack(Item.emerald), 2.5F);
		}
		if(!OreDictionary.getOres("oreNetherEmerald").isEmpty()) {
			TileEntityGrinder.addRecipe("oreNetherEmerald", new ItemStack(Item.emerald), 5F);
		}
		
		if(OreDictionary.getOres("oreCoal").isEmpty()) {
			TileEntityGrinder.addRecipe(Block.oreCoal, new ItemStack(Item.coal), 2.5F);
		}else {
			TileEntityGrinder.addRecipe("oreCoal", new ItemStack(Item.coal), 2.5F);
		}
		if(!OreDictionary.getOres("oreNetherCoal").isEmpty()) {
			TileEntityGrinder.addRecipe("oreNetherCoal", new ItemStack(Item.coal), 5F);
		}
		
		if(OreDictionary.getOres("oreRedstone").isEmpty()) {
			TileEntityGrinder.addRecipe(Block.oreRedstone, new ItemStack(Item.redstone), 9.5F);
		}else {
			TileEntityGrinder.addRecipe("oreRedstone", new ItemStack(Item.redstone), 9.5F);
		}
		if(!OreDictionary.getOres("oreNetherRedstone").isEmpty()) {
			TileEntityGrinder.addRecipe("oreNetherRedstone", new ItemStack(Item.redstone), 25.5F);
		}
		
		if(OreDictionary.getOres("cloth").isEmpty()) {
			for(int i = 0; i < 16; i++) {
				OreDictionary.registerOre("cloth", new ItemStack(Block.cloth, 1, i));
			}
		}
		TileEntityGrinder.addRecipe("cloth", new ItemStack(Item.silk), 3.5F);
		
		if(!OreDictionary.getOres("oreNetherPlatinum").isEmpty() && OreDictionary.getOres("orePlatinum").isEmpty() && !OreDictionary.getOres("ingotPlatinum").isEmpty()) {
			TileEntityGrinder.addRecipe("oreNetherPlatinum", OreDictionary.getOres("dirtyGravelPlatinum").get(0).copy(), 4F);
		}
		
		if(!OreDictionary.getOres("oreNetherSilver").isEmpty()) {
			TileEntityGrinder.addRecipe("oreNetherSilver", OreDictionary.getOres("dirtyGravelGalena").get(0).copy(), 4F);
		}
		
		if(!OreDictionary.getOres("oreGalena").isEmpty()) {
			TileEntityGrinder.addRecipe("oreGalena", OreDictionary.getOres("dirtyGravelGalena").get(0).copy(), 2F);
		}
		
	}
	
	private static void addMetalRecipes() {
		
		for(String s : OreDictionaryUtil.ingotTransforms.keySet()) {
			
			String ingotPostfix = OreDictionaryUtil.ingotTransforms.get(s);
			
			ArrayList<ItemStack> dirties = OreDictionary.getOres("dirtyGravel"+ingotPostfix);
			ItemStack dirty = dirties.get(0).copy();
			
			ArrayList<ItemStack> cleans = OreDictionary.getOres("cleanGravel"+ingotPostfix);
			ItemStack clean = cleans.get(0).copy();
			
			ArrayList<ItemStack> reduceds = OreDictionary.getOres("reduced"+ingotPostfix);
			ItemStack reduced = reduceds.get(0).copy();
			
			ArrayList<ItemStack> crystals = OreDictionary.getOres("crystalline"+ingotPostfix);
			ItemStack crystal = crystals.get(0).copy();
			
			if(!OreDictionary.getOres("ore"+s).isEmpty()) {
				TileEntityGrinder.addRecipe("ore"+s, dirty, getGravelOutput(s));
			}
			
			if(!OreDictionary.getOres("oreNether"+s).isEmpty()) {
				TileEntityGrinder.addRecipe("oreNether"+s, dirty, getGravelOutput(s)*2);
			}

		}
		
	}
	
	private static void addGemRecipes() {
		
		for(String s : OreDictionaryUtil.gemTransforms.keySet()) {
			String gemPostfix = OreDictionaryUtil.gemTransforms.get(s);
			
			ArrayList<ItemStack> gems = OreDictionary.getOres("gem"+gemPostfix);
			
			if(!gems.isEmpty() && !OreDictionary.getOres("ore"+gemPostfix).isEmpty()) {
				ItemStack gem = gems.get(0).copy();
				TileEntityGrinder.addRecipe("ore"+gemPostfix, gem, getGemOutput(gemPostfix));
				
				if(!OreDictionary.getOres("oreNether"+s).isEmpty()) {
					TileEntityGrinder.addRecipe("oreNether"+s, gem, getGemOutput(gemPostfix)*2);
				}
			}
			
		}
		
	}
	
	private static void addDustRecipes() {
		
		for(String s : OreDictionaryUtil.dustTransforms.keySet()) {
			String dustPostfix = OreDictionaryUtil.dustTransforms.get(s);
			
			ArrayList<ItemStack> dusts = OreDictionary.getOres("dust"+dustPostfix);
			
			if(!dusts.isEmpty() && !OreDictionary.getOres("ore"+dustPostfix).isEmpty()) {
				ItemStack dust = dusts.get(0).copy();
				TileEntityGrinder.addRecipe("ore"+dustPostfix, dust, getDustOutput(dustPostfix));
				
				if(!OreDictionary.getOres("oreNether"+s).isEmpty()) {
					TileEntityGrinder.addRecipe("oreNether"+s, dust, getDustOutput(dustPostfix)*2);
				}
			}
			
		}
		
	}
	
	private static float getDustOutput(String postfix) {
		float mult = 1F;
		if(ConfigurationHandler.customOreMults.containsKey(postfix)) {
			mult = ConfigurationHandler.customOreMults.get(postfix);
		}
		
		return 2F * mult;
	}
	
	private static float getGemOutput(String postfix) {
		float mult = 1F;
		if(ConfigurationHandler.customOreMults.containsKey(postfix)) {
			mult = ConfigurationHandler.customOreMults.get(postfix);
		}
		
		return 2.5F * mult;
	}
	
	private static float getGravelOutput(String postfix) {
		float mult = 1F;
		if(ConfigurationHandler.customOreMults.containsKey(postfix)) {
			mult = ConfigurationHandler.customOreMults.get(postfix);
		}
		
		return 2F * mult;
	}
	
	private static void doSlagFurnaceShenanigans() {
		
		doSlagPrep();
		HashSet<String> customParsed = new HashSet<String>();
		
		for(SlagConfigOption option : SlagParser.customSlagRecipes) {
			
			if(!OreDictionary.getOres(option.in).isEmpty() && !OreDictionary.getOres(option.mainOut).isEmpty()) {
				
				if(!OreDictionary.getOres(option.secondOut).isEmpty()) {
					addSlagRecipe(option.in, option.secondMult, option.secondOut, option.mainMult, option.mainOut);
				}else {
					addSlagRecipe(option.in, option.fallbackMult, option.mainOut, option.fallbackMult, option.mainOut);
				}
				
				customParsed.add(option.in);
				
			}
			
		}
		
		for(String s : OreDictionary.getOreNames()) {
			if(s.startsWith("cleanGravel") && !s.equals("cleanGravelSilver") && !customParsed.contains(s)) {
				
				String postfix = s.substring(11);
				
				ArrayList<ItemStack> cleans = OreDictionary.getOres(s);
				if(!cleans.isEmpty()) {
					
					ArrayList<ItemStack> ingots = OreDictionary.getOres("ingot"+postfix);
					ArrayList<ItemStack> reduceds = OreDictionary.getOres("reduced"+postfix);
					
					if(!ingots.isEmpty() && !reduceds.isEmpty()) {
						ItemStack out2 = reduceds.get(0).copy();
						ItemStack out1 = out2;
						float chance2 = .625F;
						float chance1 = .625F;
						addSlagRecipe(s, chance1, out1, chance2, out2);
					}
					
				}
			}
		}
		
	}
	
	private static void doSlagPrep() {
		
		if(!OreDictionary.getOres("oreSphalerite").isEmpty() && !OreDictionary.getOres("ingotZinc").isEmpty()) {
			for(ItemStack sphal : OreDictionary.getOres("oreSphalerite")) {
				TileEntitySlagFurnace.SlagRecipes.register(sphal.copy(), 1.2F, OreDictionary.getOres("ingotZinc").get(0).copy(), .4F, Block.stone);
			}
		}
		
		if(!OreDictionary.getOres("orePyrite").isEmpty() && !OreDictionary.getOres("ingotIron").isEmpty()) {
			for(ItemStack pyr : OreDictionary.getOres("orePyrite")) {
				TileEntitySlagFurnace.SlagRecipes.register(pyr.copy(), 1.2F, OreDictionary.getOres("ingotIron").get(0).copy(), .4F, Block.netherrack);
			}
		}
		
		for(String s : OreDictionaryUtil.ingotTransforms.keySet()) {
			ArrayList<ItemStack> ores = OreDictionary.getOres("ore"+s);
			if(!ores.isEmpty() && !ConfigurationHandler.smeltingBlackList.contains("ore"+s)) {
				ArrayList<ItemStack> ingots = OreDictionary.getOres("ingot"+OreDictionaryUtil.ingotTransforms.get(s));
				if(!ingots.isEmpty()) {
					for(ItemStack stack : ores) {
						float mult = 1F;
						if(ConfigurationHandler.customOreMults.containsKey(s)) {
							mult = ConfigurationHandler.customOreMults.get(s);
						}
						TileEntitySlagFurnace.SlagRecipes.register(stack.copy(), 1.2F*mult, ingots.get(0).copy(), .4F, Block.stone);
					}
					ArrayList<ItemStack> nethers = OreDictionary.getOres("oreNether"+s);
					if(!nethers.isEmpty()) {
						for(ItemStack stack : nethers) {
							TileEntitySlagFurnace.SlagRecipes.register(stack.copy(), 2.4F, ingots.get(0).copy(), .4F, Block.netherrack);
						}
					}
				}
			}
		}
		for(String s : OreDictionaryUtil.forbiddens) {
			ArrayList<ItemStack> ores = OreDictionary.getOres("ore"+s);
			if(!ores.isEmpty()) {
				ArrayList<ItemStack> ingots = OreDictionary.getOres("ingot"+OreDictionaryUtil.ingotTransforms.get(s));
				if(!ingots.isEmpty()) {
					ArrayList<ItemStack> nethers = OreDictionary.getOres("oreNether"+s);
					if(!nethers.isEmpty()) {
						for(ItemStack stack : nethers) {
							TileEntitySlagFurnace.SlagRecipes.register(stack.copy(), 2.4F, ingots.get(0).copy(), .4F, Block.netherrack);
						}
					}
				}
			}
		}
		
		Iterator<SmeltingResult> it = TileEntitySlagFurnace.SlagRecipes.smeltingResults.iterator();
		
		while(it.hasNext()) {
			SmeltingResult r = it.next();
			int oreID = OreDictionary.getOreID(r.input);
			if(oreID != -1 && OreDictionary.getOreName(oreID).startsWith("cleanGravel")) {
				it.remove();
			}
		}
		
		addSlagRecipe("cleanGravelGalena", 1.25F, OreDictionary.getOres("reducedSilver").get(0).copy(),  1.25F, OreDictionary.getOres("reducedLead").get(0).copy());
		
	}
	
	public static void addSmeltingAndCrystallizing() {
		
		for(String s : OreDictionary.getOreNames()) {
			
			if(s.startsWith("dirtyGravel") && !OreDictionary.getOres(s).isEmpty()) {
				String postfix = s.substring(11);
				if(!ConfigurationHandler.smeltingBlackList.contains(s)) {
					ArrayList<ItemStack> ingots = OreDictionary.getOres("ingot"+postfix);
					if(!ingots.isEmpty()) {
						ItemStack ingot = OreDictionary.getOres("ingot"+postfix).get(0).copy();
						ingot.stackSize = 1;
						ItemStack dirty = OreDictionary.getOres(s).get(0).copy();
						TileEntitySlagFurnace.SlagRecipes.register(dirty, 1.1F, ingot, .2F, new ItemStack(Block.dirt));
						FurnaceRecipes.smelting().addSmelting(dirty.itemID, dirty.getItemDamage(), ingot, 0.1F);
					}
				}
				
				ArrayList<ItemStack> cleans = OreDictionary.getOres("cleanGravel"+postfix);
				if(!cleans.isEmpty()) {
					ItemStack dirty = OreDictionary.getOres(s).get(0).copy();
					ItemStack clean = cleans.get(0).copy();
					GameRegistry.addRecipe(new ShapelessOreRecipe(clean, new Object[] { "fz.waterBucketLike", dirty }));
					clean.stackSize = 8;
					GameRegistry.addRecipe(new ShapelessOreRecipe(clean, new Object[] { "fz.waterBucketLike", dirty, dirty, dirty, dirty, dirty, dirty, dirty, dirty }));
				}
				
			}
			
			else if(s.startsWith("cleanGravel") && !OreDictionary.getOres(s).isEmpty() && !ConfigurationHandler.smeltingBlackList.contains(s)) {
				String postfix = s.substring(11);
				if(!OreDictionary.getOres("ingot"+postfix).isEmpty()) {
					ItemStack clean = OreDictionary.getOres(s).get(0).copy();
					ItemStack ingot = OreDictionary.getOres("ingot"+postfix).get(0).copy();
					FurnaceRecipes.smelting().addSmelting(clean.itemID, clean.getItemDamage(), ingot, 0.1F);
				}
			}
			
			else if(s.startsWith("reduced") && !OreDictionary.getOres(s).isEmpty()) {
				ItemStack reduced = OreDictionary.getOres(s).get(0).copy();
				String postfix = s.substring(7);
				if(!OreDictionary.getOres("crystalline"+postfix).isEmpty()) {
					ItemStack crystal = OreDictionary.getOres("crystalline"+postfix).get(0).copy();
					TileEntityCrystallizer.addRecipe(reduced, crystal, 1.2F, Core.registry.aqua_regia);
				}
				if(!ConfigurationHandler.smeltingBlackList.contains(s) && !OreDictionary.getOres("ingot"+postfix).isEmpty()) {
					ItemStack ingot = OreDictionary.getOres("ingot"+postfix).get(0).copy();
					FurnaceRecipes.smelting().addSmelting(reduced.itemID, reduced.getItemDamage(), ingot, 0.1F);
				}
			}
			
			else if(s.startsWith("crystalline") && !OreDictionary.getOres(s).isEmpty() && !ConfigurationHandler.smeltingBlackList.contains(s)) {
				String postfix = s.substring(11);
				if(!OreDictionary.getOres("ingot"+postfix).isEmpty()) {
					ItemStack crystal = OreDictionary.getOres(s).get(0).copy();
					ItemStack ingot = OreDictionary.getOres("ingot"+postfix).get(0).copy();
					FurnaceRecipes.smelting().addSmelting(crystal.itemID, crystal.getItemDamage(), ingot, 0.1F);
				}
			}
			
		}
		
	}
	
	private static void addSlagRecipe(String dictIn, float mult1, ItemStack out1, float mult2, ItemStack out2) {
		if(!OreDictionary.getOres(dictIn).isEmpty()) {
			TileEntitySlagFurnace.SlagRecipes.register(OreDictionary.getOres(dictIn).get(0).copy(), mult1, out1, mult2, out2);
		}
	}
	
	private static void addSlagRecipe(String dictIn, float mult1, String dictOut1, float mult2, String dictOut2) {
		if(!OreDictionary.getOres(dictIn).isEmpty()) {
			TileEntitySlagFurnace.SlagRecipes.register(OreDictionary.getOres(dictIn).get(0).copy(), mult1, OreDictionary.getOres(dictOut1).get(0).copy(), mult2, OreDictionary.getOres(dictOut2).get(0).copy());
		}
	}
	
}