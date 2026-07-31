/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.Registry
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.chunk.LevelChunk
 *  net.minecraft.world.level.chunk.LevelChunkSection
 *  net.minecraft.world.level.chunk.PalettedContainer
 */
package ic2.core.block;

import ic2.core.block.ChunkLoadAwareBlock;
import java.util.IdentityHashMap;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.PalettedContainer;

public final class ChunkLoadAwareBlockHandler {
    private static final int CS = 16;
    private static final int CSM = 15;
    private static final Map<BlockState, ChunkLoadAwareBlock> stateMap = new IdentityHashMap<BlockState, ChunkLoadAwareBlock>();

    public static void init() {
        for (Block block : Registry.f_122824_) {
            if (!(block instanceof ChunkLoadAwareBlock)) continue;
            ChunkLoadAwareBlock chunkLoadAwareBlock = (ChunkLoadAwareBlock)block;
            for (BlockState blockState : chunkLoadAwareBlock.getLoadAwareState(block)) {
                stateMap.put(blockState, chunkLoadAwareBlock);
            }
        }
    }

    public static void onChunkLoad(LevelChunk levelChunk) {
        ChunkLoadAwareBlockHandler.processChunk(levelChunk, true);
    }

    public static void onChunkUnload(LevelChunk levelChunk) {
        ChunkLoadAwareBlockHandler.processChunk(levelChunk, false);
    }

    private static void processChunk(LevelChunk levelChunk, boolean bl) {
        for (LevelChunkSection levelChunkSection : levelChunk.m_7103_()) {
            if (levelChunkSection.m_188008_()) continue;
            PalettedContainer palettedContainer = levelChunkSection.m_63019_();
            if (!palettedContainer.m_63109_(stateMap::containsKey)) continue;
            Level level = levelChunk.m_62953_();
            BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos(levelChunk.m_7697_().m_45604_(), levelChunkSection.m_63017_(), levelChunk.m_7697_().m_45605_());
            BlockState blockState = null;
            ChunkLoadAwareBlock chunkLoadAwareBlock = null;
            for (int i = 0; i < 16; ++i) {
                for (int j = 0; j < 16; ++j) {
                    for (int k = 0; k < 16; ++k) {
                        BlockState blockState2 = (BlockState)palettedContainer.m_63087_(k, i, j);
                        if (blockState2 != blockState) {
                            blockState = blockState2;
                            chunkLoadAwareBlock = stateMap.get(blockState2);
                        }
                        if (chunkLoadAwareBlock == null) continue;
                        mutableBlockPos.m_122178_(mutableBlockPos.m_123341_() & 0xFFFFFFF0 | k, mutableBlockPos.m_123342_() & 0xFFFFFFF0 | i, mutableBlockPos.m_123343_() & 0xFFFFFFF0 | j);
                        if (bl) {
                            chunkLoadAwareBlock.onLoad(blockState2, level, (BlockPos)mutableBlockPos);
                            continue;
                        }
                        chunkLoadAwareBlock.onUnload(blockState2, level, (BlockPos)mutableBlockPos);
                    }
                }
            }
        }
    }
}

