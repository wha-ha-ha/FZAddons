package fza.base.util;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import fza.base.config.ConfigurationHandler;

public class WrathRecipeEventHandler {
	
	private boolean initialized = false;
	
	@ForgeSubscribe
	public void onPlayerJoin(EntityJoinWorldEvent event) {
		if(!initialized && ConfigurationHandler.wrathIgniterRevert) {
			RecipeGymnastics.revertDarkIronRecipes();
		}
		initialized = true;
	}
	
}