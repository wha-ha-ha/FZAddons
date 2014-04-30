package fza.base.items.concretes;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import factorization.shared.Core;
import fza.base.items.FZAItemInfo;
import fza.base.util.ColoringUtil;
import fza.base.util.OreDictionaryUtil;

public class ItemDirtyGravel extends Item {
	
	public ItemDirtyGravel(int par1) {
		super(par1);
		setHasSubtypes(true);
		setUnlocalizedName(FZAItemInfo.DIRTY_UNLOCALIZED_NAME);
		setCreativeTab(Core.tabFactorization);
		setNoRepair();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int par2) {
		return ColoringUtil.getColorFromPostFix(OreDictionaryUtil.postfixes[stack.getItemDamage()]);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register) {
		itemIcon = register.registerIcon(FZAItemInfo.TEXTURE_LOCATION + ":" + FZAItemInfo.DIRTY_ICON);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack s) {
		return super.getUnlocalizedName(s)+s.getItemDamage();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs tabs, List list) {
		for(int i = 0; i < OreDictionaryUtil.postfixes.length; i++) {
			if(OreDictionaryUtil.postfixes[i] != null) {
				String postfix = OreDictionaryUtil.postfixes[i];
				if(OreDictionaryUtil.ingotTransforms.containsKey(postfix) && !OreDictionaryUtil.forbiddens.contains(OreDictionaryUtil.ingotTransforms.get(postfix))) {
					list.add(new ItemStack(this, 1, i));
				}
			}
		}
	}
	
}