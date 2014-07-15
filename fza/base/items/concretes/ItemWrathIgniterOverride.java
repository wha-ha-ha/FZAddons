package fza.base.items.concretes;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import factorization.api.Coord;
import factorization.wrath.ItemWrathIgniter;
import factorization.wrath.TileEntityWrathFire;
import fza.base.items.FZAItemInfo;

public class ItemWrathIgniterOverride extends ItemWrathIgniter {

	public ItemWrathIgniterOverride(int par1) {
		super(par1);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register) {
		itemIcon = register.registerIcon(FZAItemInfo.TEXTURE_LOCATION + ":" + FZAItemInfo.IGNITER_ICON);
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