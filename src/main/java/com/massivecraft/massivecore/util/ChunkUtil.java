package com.massivecraft.massivecore.util;

import com.massivecraft.massivecore.collections.MassiveSet;
import com.massivecraft.massivecore.predicate.Predicate;
import com.massivecraft.massivecore.ps.PS;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class ChunkUtil
{
	// -------------------------------------------- //
	// GET AREAS
	// -------------------------------------------- //

	public static @NotNull Set<@NotNull PS> getChunksCircle(@NotNull PS center, int radius)
	{
		// Common Startup
		final Set<PS> chunks = new MassiveSet<>();

		chunks.add(center); // The center should come first for pretty messages

		int radiusZero = radius - 1;
		double radiusSquared = radiusZero * radiusZero;

		for (int dx = -radiusZero; dx <= radiusZero; dx++)
		{
			for (int dz = -radiusZero; dz <= radiusZero; dz++)
			{
				if (dx*dx + dz*dz > radiusSquared) continue;

				int x = center.getChunkX() + dx;
				int z = center.getChunkZ() + dz;

				chunks.add(center.withChunkX(x).withChunkZ(z));
			}
		}

		return chunks;
	}

	public static @NotNull Set<@NotNull PS> getChunksSquare(@NotNull PS center, int radius)
	{
		// Common Startup
		final Set<PS> chunks = new MassiveSet<>();

		chunks.add(center); // The center should come first for pretty messages

		int radiusZero = radius - 1;

		for (int dx = -radiusZero; dx <= radiusZero; dx++)
		{
			for (int dz = -radiusZero; dz <= radiusZero; dz++)
			{
				int x = center.getChunkX() + dx;
				int z = center.getChunkZ() + dz;

				chunks.add(center.withChunkX(x).withChunkZ(z));
			}
		}

		return chunks;
	}

	public static @NotNull Set<PS> getChunkArea(PS startingPoint, Predicate<PS> matcher, int max)
	{
		Set<PS> set = new MassiveSet<>();
		set.add(startingPoint);
		floodSearch(set, matcher, max);
		return set;
	}

	@Contract("null, _, _ -> fail; !null, null, _ -> fail")
	public static void floodSearch(Set<@NotNull PS> set, Predicate<PS> matcher, int max)
	{
		// Clean
		if (set == null) throw new NullPointerException("set");
		if (matcher == null) throw new NullPointerException("color");

		// Expand
		Set<PS> expansion = new MassiveSet<>();
		for (PS chunk : set)
		{
			Set<PS> neighbours = MUtil.set(
				chunk.withChunkX(chunk.getChunkX() + 1),
				chunk.withChunkX(chunk.getChunkX() - 1),
				chunk.withChunkZ(chunk.getChunkZ() + 1),
				chunk.withChunkZ(chunk.getChunkZ() - 1)
			);

			for (PS neighbour : neighbours)
			{
				if (set.contains(neighbour)) continue;
				if ( ! matcher.apply(neighbour)) continue;

				expansion.add(neighbour);
			}
		}
		set.addAll(expansion);

		// No Expansion?
		if (expansion.isEmpty()) return;

		// Reached Max?
		if (set.size() >= max) return;

		// Recurse
		floodSearch(set, matcher, max);
	}

}
