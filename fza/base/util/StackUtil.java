package fza.base.util;

import net.minecraft.item.ItemStack;

public class StackUtil {

	public static ItemStack getDuplicateStackOfSize(int size, ItemStack sourceStack) {
		ItemStack out = sourceStack.copy();
		out.stackSize = size;
		return out;
	}
	
}
