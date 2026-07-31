/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.World
 *  net.minecraft.world.chunk.Chunk
 */
package ic2.core.util;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class WorldSearchUtil {
    public static void findTileEntities(World world, BlockPos center, int range, ITileEntityResultHandler handler) {
        int minX = center.func_177958_n() - range;
        int minY = center.func_177956_o() - range;
        int minZ = center.func_177952_p() - range;
        int maxX = center.func_177958_n() + range;
        int maxY = center.func_177956_o() + range;
        int maxZ = center.func_177952_p() + range;
        int xS = minX >> 4;
        int zS = minZ >> 4;
        int xE = maxX >> 4;
        int zE = maxZ >> 4;
        for (int x = xS; x <= xE; ++x) {
            for (int z = zS; z <= zE; ++z) {
                Chunk chunk = world.func_72964_e(x, z);
                for (TileEntity te : chunk.func_177434_r().values()) {
                    BlockPos pos = te.func_174877_v();
                    if (pos.func_177956_o() < minY || pos.func_177956_o() > maxY || pos.func_177958_n() < minX || pos.func_177958_n() > maxX || pos.func_177952_p() < minZ || pos.func_177952_p() > maxZ || !handler.onMatch(te)) continue;
                    return;
                }
            }
        }
    }

    public static interface ITileEntityResultHandler {
        public boolean onMatch(TileEntity var1);
    }
}

