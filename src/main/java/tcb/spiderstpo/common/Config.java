package tcb.spiderstpo.common;

import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RequiresWorldRestart;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@net.minecraftforge.common.config.Config(modid = "spiderstpo", name = "spiders-2.0")
public class Config {
	@Name("replace_natural_spawns")
	@Comment("Whether natural spider spawns should be replaced. This includes e.g. mob spawners and spawn eggs.")
	public static boolean replaceNaturalSpawns = true;

	@Name("bright_spawning_chance")
	@Comment("Chance at which spider spawning in a light area (>7) will be allowed. '0' completely disables spider spawning in the light. In the original mod, a huge number of spiders spawned during the daytime all over the world, but this is not normal for a hostile mob.")
	public static int brightSpawningChance = 0;

	@Name("replace_any_spawns")
	@Comment("Whether any spider spawns should be replaced. This also applies retroactively to already existing spiders.")
	public static boolean replaceAnySpawns = true;

	@Name("path_finder_debug_preview")
	@Comment("Whether the path finder debug preview should be enabled.")
	@RequiresWorldRestart
	public static boolean pathFinderDebugPreview = false;

	@SubscribeEvent
	public static void onConfigChanged(OnConfigChangedEvent event) {
		if("spiderstpo".equals(event.getModID())) {
			ConfigManager.sync("spiderstpo", net.minecraftforge.common.config.Config.Type.INSTANCE);
		}
	}
}
