/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.LongOpenHashSet
 *  it.unimi.dsi.fastutil.longs.LongSet
 */
package net.minecraft.world.level;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.saveddata.SavedData;

public class ForcedChunksSavedData
extends SavedData {
    public static final String f_151479_ = "chunks";
    private static final String f_151480_ = "Forced";
    private final LongSet f_46114_;

    private ForcedChunksSavedData(LongSet p_151482_) {
        this.f_46114_ = p_151482_;
    }

    public ForcedChunksSavedData() {
        this((LongSet)new LongOpenHashSet());
    }

    public static ForcedChunksSavedData m_151483_(CompoundTag p_151484_) {
        return new ForcedChunksSavedData((LongSet)new LongOpenHashSet(p_151484_.m_128467_(f_151480_)));
    }

    @Override
    public CompoundTag m_7176_(CompoundTag p_46120_) {
        p_46120_.m_128388_(f_151480_, this.f_46114_.toLongArray());
        return p_46120_;
    }

    public LongSet m_46116_() {
        return this.f_46114_;
    }
}

