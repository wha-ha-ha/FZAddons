package fza.base.util;

import java.util.HashMap;

public class ColoringUtil {

	static final HashMap<String, Integer> colors = new HashMap<String, Integer>();
	
	public static void init(String[] configValues) {
		for(String s : configValues) {
			try {
				String[] split = s.split(":");
				colors.put(split[0], Integer.parseInt(split[1], 16));
			}catch(Throwable t){}
		}
	}
	
	public static int getColorFromPostFix(String postfix) {
		if(postfix == null) return 0xB8B8B8;
		
		return colors.containsKey(postfix) ? colors.get(postfix) : 0xB8B8B8;
	}
	
}