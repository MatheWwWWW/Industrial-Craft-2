/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface BonemealableBlock {
    public boolean m_7370_(BlockGetter var1, BlockPos var2, BlockState var3, boolean var4);

    public boolean m_214167_(Level var1, RandomSource var2, BlockPos var3, BlockState var4);

    public void m_214148_(ServerLevel var1, RandomSource var2, BlockPos var3, BlockState var4);
}

