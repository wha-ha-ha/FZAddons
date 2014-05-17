package fza.base.items.concretes;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import factorization.api.Coord;
import factorization.shared.Core;
import factorization.wrath.ItemWrathIgniter;
import factorization.wrath.TileEntityWrathFire;

public class ItemWrathIgniterOverride extends ItemWrathIgniter {

	public ItemWrathIgniterOverride(int par1) {
		super(par1);
		setUnlocalizedName("FZA:WrathIgniter");
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public String getIconString() {
		return "factorization:tool/wrath_igniter";
	}
	
	@Override
	public boolean tryPlaceIntoWorld(ItemStack is, EntityPlayer player, World world, int x, int y, int z, int side, float vecx, float vecy, float vecz) {
		Coord baseBlock = new Coord(world, x, y, z);
		Coord fireBlock = baseBlock.copy().towardSide(side);
		if((fireBlock.getId() != 0) && (!fireBlock.isAir())) {
			return true;
		}

		is.damageItem(2, player);
		TileEntityWrathFire.ignite(baseBlock, fireBlock, player);
		return true;
	}
	
	@Override
	public String getUnlocalizedName() {
		return "FZA:WrathIgniter";
	}

}