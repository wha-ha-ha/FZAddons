package fza.base.config;

import net.minecraft.item.ItemStack;

public class SlagConfigOption {

	public String in;
	public float mainMult;
	public String mainOut;
	public float secondMult;
	public String secondOut;
	public float fallbackMult;
	
	public SlagConfigOption(String in, String mainOut, float mainMult, String secondOut, float secondMult, float fallbackMult) {
		this.in = in;
		this.mainOut = mainOut;
		this.mainMult = mainMult;
		this.secondOut = secondOut;
		this.secondMult = secondMult;
		this.fallbackMult = fallbackMult;
	}
	
}
