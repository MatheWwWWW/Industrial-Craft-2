/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.LongOpenHashSet
 *  it.unimi.dsi.fastutil.longs.LongSet
 */
package net.minecraft.world.level.levelgen.structure;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.saveddata.SavedData;

public class StructureFeatureIndexSavedData
extends SavedData {
    private static final String f_163528_ = "Remaining";
    private static final String f_163529_ = "All";
    private final LongSet f_73360_;
    private final LongSet f_73361_;

    private StructureFeatureIndexSavedData(LongSet p_163532_, LongSet p_163533_) {
        this.f_73360_ = p_163532_;
        this.f_73361_ = p_163533_;
    }

    public StructureFeatureIndexSavedData() {
        this((LongSet)new LongOpenHashSet(), (LongSet)new LongOpenHashSet());
    }

    public static StructureFeatureIndexSavedData m_163534_(CompoundTag p_163535_) {
        return new StructureFeatureIndexSavedData((LongSet)new LongOpenHashSet(p_163535_.m_128467_(f_163529_)), (LongSet)new LongOpenHashSet(p_163535_.m_128467_(f_163528_)));
    }

    @Override
    public CompoundTag m_7176_(CompoundTag p_73372_) {
        p_73372_.m_128388_(f_163529_, this.f_73360_.toLongArray());
        p_73372_.m_128388_(f_163528_, this.f_73361_.toLongArray());
        return p_73372_;
    }

    public void m_73365_(long p_73366_) {
        this.f_73360_.add(p_73366_);
        this.f_73361_.add(p_73366_);
    }

    public boolean m_73369_(long p_73370_) {
        return this.f_73360_.contains(p_73370_);
    }

    public boolean m_73373_(long p_73374_) {
        return this.f_73361_.contains(p_73374_);
    }

    public void m_73375_(long p_73376_) {
        this.f_73361_.remove(p_73376_);
    }

    public LongSet m_73364_() {
        return this.f_73360_;
    }
}

