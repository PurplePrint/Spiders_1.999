package tcb.spiderstpo.common.entity.movement;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public interface IAdvancedPathFindingEntity {
	/**
	 * Called when the mob tries to move along the path but is obstructed
	 */
	public void onPathingObstructed(EnumFacing facing);

	/**
	 * Returns how many ticks the mob can be stuck before the path is considered to be obstructed
	 * @return
	 */
	public default int getMaxStuckCheckTicks() {
		return 40;
	}

	/**
	 * Returns the pathing malus for building a bridge
	 * @param entity
	 * @param pos
	 * @param fallPathPoint
	 * @return
	 */
	public default float getBridgePathingMalus(EntityLiving entity, BlockPos pos, @Nullable PathPoint fallPathPoint) {
		return -1.0f;
	}

	/**
	 * Returns the pathing malus for the given {@link PathNodeType} and block position.
	 * Nodes with negative values are avoided at all cost. Nodes with value 0.0 have the highest priority, i.e.
	 * are preferred over all other nodes. Nodes with a positive value incur an additional travel cost of the same magnitude
	 * and the higher their value the less they are preferred. Note that the additional travel cost increases the path's "length" (i.e. cost)
	 * and thus decreases the actual maximum path length in blocks.
	 * @param type
	 * @param pos
	 * @return
	 */
	public default float getPathingMalus(EntityLiving entity, PathNodeType nodeType, BlockPos pos) {
		return entity.getPathPriority(nodeType);
	}
}
