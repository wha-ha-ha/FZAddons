package fza.base.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.LanguageRegistry;
import fza.base.items.concretes.ItemCleanGravel;
import fza.base.items.concretes.ItemCrystallizedChunks;
import fza.base.items.concretes.ItemDirtyGravel;
import fza.base.items.concretes.ItemReducedChunks;
import fza.base.util.OreDictionaryUtil;


public class FZAItems {
	
	public static Item dirty;
	public static Item clean;
	public static Item reduced;
	public static Item crystal;
	
	public static void initItems() {
		dirty = new ItemDirtyGravel(FZAItemInfo.DIRTY_ID);
		clean = new ItemCleanGravel(FZAItemInfo.CLEAN_ID);
		reduced = new ItemReducedChunks(FZAItemInfo.REDUCED_ID);
		crystal = new ItemCrystallizedChunks(FZAItemInfo.CRYSTAL_ID);
	}
	
	public static void addNames() {
		for(int i = 0; i < OreDictionaryUtil.postfixes.length; i++) {
			if(OreDictionaryUtil.postfixes[i] != null) {
				if(OreDictionaryUtil.ingotTransforms.containsKey(OreDictionaryUtil.postfixes[i])) {
					String ingot = OreDictionaryUtil.ingotTransforms.get(OreDictionaryUtil.postfixes[i]);
					LanguageRegistry.addName(new ItemStack(dirty, 1, i), "Dirty "+ingot+" Gravel");
					LanguageRegistry.addName(new ItemStack(clean, 1, i), "Clean "+ingot+" Gravel");
					LanguageRegistry.addName(new ItemStack(reduced, 1, i), "Reduced "+ingot+" Chunks");
					LanguageRegistry.addName(new ItemStack(crystal, 1, i), "Crystalline "+ingot);
				}
			}
		}
	}
	
	public static void registerOreDict() {
		for(int i = 0; i < OreDictionaryUtil.postfixes.length; i++) {
			if(OreDictionaryUtil.postfixes[i] != null) {
				if(OreDictionaryUtil.ingotTransforms.containsKey(OreDictionaryUtil.postfixes[i])) {
					String ingot = OreDictionaryUtil.ingotTransforms.get(OreDictionaryUtil.postfixes[i]);
					OreDictionary.registerOre("dirtyGravel"+ingot, new ItemStack(dirty, 1, i));
					OreDictionary.registerOre("cleanGravel"+ingot, new ItemStack(clean, 1, i));
					OreDictionary.registerOre("reduced"+ingot, new ItemStack(reduced, 1, i));
					OreDictionary.registerOre("crystalline"+ingot, new ItemStack(crystal, 1, i));
				}
			}
		}
	}
	
}
