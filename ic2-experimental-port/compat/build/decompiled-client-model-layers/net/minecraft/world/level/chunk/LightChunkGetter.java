/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.chunk;

import javax.annotation.Nullable;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LightLayer;

public interface LightChunkGetter {
    @Nullable
    public BlockGetter m_6196_(int var1, int var2);

    default public void m_6506_(LightLayer p_63021_, SectionPos p_63022_) {
    }

    public BlockGetter m_7653_();
}

