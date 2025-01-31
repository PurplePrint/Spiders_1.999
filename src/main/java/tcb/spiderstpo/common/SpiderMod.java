package tcb.spiderstpo.common;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import tcb.spiderstpo.common.entity.mob.BetterCaveSpiderEntity;
import tcb.spiderstpo.common.entity.mob.BetterSpiderEntity;
import tcb.spiderstpo.compat.mobends.MoBendsCompat;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.entity.EntitySpawnPlacementRegistry.ENTITY_PLACEMENTS;

@Mod(modid = "spiderstpo", name = "Spiders 2.0", acceptedMinecraftVersions = "[1.12.2]", useMetadata = true)
public class SpiderMod {
	@Instance("spiderstpo")
	public static SpiderMod instance;

	@SidedProxy(modId = "spiderstpo", clientSide = "tcb.spiderstpo.client.ClientProxy", serverSide = "tcb.spiderstpo.common.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public static void preInit(FMLPreInitializationEvent event) {
		ConfigManager.sync("spiderstpo", net.minecraftforge.common.config.Config.Type.INSTANCE);

		MinecraftForge.EVENT_BUS.register(SpiderMod.class);
		MinecraftForge.EVENT_BUS.register(Config.class);

		Entities.register();

		ENTITY_PLACEMENTS.put(BetterSpiderEntity.class, EntityLiving.SpawnPlacementType.ON_GROUND);
		ENTITY_PLACEMENTS.put(BetterCaveSpiderEntity.class, EntityLiving.SpawnPlacementType.ON_GROUND);

		proxy.preInit();
	}

	@EventHandler
	public static void init(FMLInitializationEvent event) {
		MoBendsCompat.init();
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onSpawnEntity(final LivingSpawnEvent.CheckSpawn event) {
		if(Config.replaceAnySpawns || Config.replaceNaturalSpawns) {
			Entity entity = event.getEntity();

			if(!entity.getEntityWorld().isRemote) {
				Entity replacement = replaceSpawn(entity, true);
				if(replacement != null) {
					event.setResult(Result.DENY);

					MobSpawnerBaseLogic spawner = event.getSpawner();
					if(spawner != null) {
						spawner.setEntityId(EntityList.getKey(replacement.getClass()));
					}
				}
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onSpawnEntity(final LivingSpawnEvent.SpecialSpawn event) {
		if(Config.replaceAnySpawns || Config.replaceNaturalSpawns) {
			Entity entity = event.getEntity();

			if(!entity.getEntityWorld().isRemote) {
				Entity replacement = replaceSpawn(entity, true);
				if(replacement != null) {
					event.setCanceled(true);

					MobSpawnerBaseLogic spawner = event.getSpawner();
					if(spawner != null) {
						spawner.setEntityId(EntityList.getKey(replacement.getClass()));
					}
				}
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onAddEntity(final EntityJoinWorldEvent event) {
		if(Config.replaceAnySpawns) {
			Entity entity = event.getEntity();

			if(!entity.getEntityWorld().isRemote && replaceSpawn(entity, false) != null) {
				event.setCanceled(true);
			}
		}
	}

	private static Entity replaceSpawn(Entity entity, boolean newSpawn) {
		World world = entity.getEntityWorld();
		BlockPos pos = new BlockPos(entity.posX, entity.posY, entity.posZ);

		if (Config.brightSpawningChance < 100) {
			boolean banBright = false;
			int chance = Config.brightSpawningChance;
			if (chance < 0)
				chance = 0;

			if (chance == 0) {
				banBright = true;
			} else {
				if (world.rand.nextInt(100) + 1 > chance) {
					banBright = true;
				}
			}

			if (banBright) {
				if (world.getLightFromNeighbors(pos) > 7) {
					return null;
				}
			}
		}

		Entity replacement = null;

		if(entity.getClass().equals(EntitySpider.class)) {
			replacement = new BetterSpiderEntity(world);
		} else if(entity.getClass().equals(EntityCaveSpider.class)) {
			replacement = new BetterCaveSpiderEntity(world);
		}

		if(replacement != null) {
//			replacement.readFromNBT(entity.writeToNBT(new NBTTagCompound())); // THE MAIN ISSUE
			replacement.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);

			if(newSpawn && replacement instanceof EntityLiving) {
				((EntityLiving) replacement).onInitialSpawn(world.getDifficultyForLocation(entity.getPosition()), null);
			}

			replacement.forceSpawn = entity.forceSpawn;

			world.spawnEntity(replacement);
			return replacement;
		}

		return null;
	}
}
