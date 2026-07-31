/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.advancements.CriteriaTriggers
 *  net.minecraft.core.BlockPos
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.chunk.LevelChunk
 */
package ic2.core.util;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;

public class WorldUtil {
    public static void findTileEntities(Level level, BlockPos blockPos, int n, ITileEntityResultHandler iTileEntityResultHandler) {
        int n2 = blockPos.m_123341_() - n;
        int n3 = blockPos.m_123342_() - n;
        int n4 = blockPos.m_123343_() - n;
        int n5 = blockPos.m_123341_() + n;
        int n6 = blockPos.m_123342_() + n;
        int n7 = blockPos.m_123343_() + n;
        int n8 = n2 >> 4;
        int n9 = n4 >> 4;
        int n10 = n5 >> 4;
        int n11 = n7 >> 4;
        for (int i = n8; i <= n10; ++i) {
            for (int j = n9; j <= n11; ++j) {
                LevelChunk levelChunk = level.m_6325_(i, j);
                for (BlockEntity blockEntity : levelChunk.m_62954_().values()) {
                    BlockPos blockPos2 = blockEntity.m_58899_();
                    if (blockPos2.m_123342_() < n3 || blockPos2.m_123342_() > n6 || blockPos2.m_123341_() < n2 || blockPos2.m_123341_() > n5 || blockPos2.m_123343_() < n4 || blockPos2.m_123343_() > n7 || !iTileEntityResultHandler.onMatch(blockEntity)) continue;
                    return;
                }
            }
        }
    }

    public static void strip(BlockState blockState, Level level, BlockPos blockPos, Player player, ItemStack itemStack, BlockState blockState2) {
        level.m_5594_(player, blockPos, SoundEvents.f_11688_, SoundSource.BLOCKS, 1.0f, 1.0f);
        if (player instanceof ServerPlayer) {
            CriteriaTriggers.f_10562_.m_220040_((ServerPlayer)player, blockPos, itemStack);
        }
        level.m_7731_(blockPos, blockState2, 11);
        itemStack.m_41622_(1, (LivingEntity)player, player2 -> player2.m_21190_(player.m_7655_()));
    }

    public static interface ITileEntityResultHandler {
        public boolean onMatch(BlockEntity var1);
    }
}

