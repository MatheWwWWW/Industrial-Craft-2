/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen.flat;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;

public class FlatLayerInfo {
    public static final Codec<FlatLayerInfo> f_70329_ = RecordCodecBuilder.create(p_70341_ -> p_70341_.group((App)Codec.intRange((int)0, (int)DimensionType.f_156651_).fieldOf("height").forGetter(FlatLayerInfo::m_70337_), (App)Registry.f_122824_.m_194605_().fieldOf("block").orElse((Object)Blocks.f_50016_).forGetter(p_161902_ -> p_161902_.m_70344_().m_60734_())).apply((Applicative)p_70341_, FlatLayerInfo::new));
    private final Block f_161900_;
    private final int f_70331_;

    public FlatLayerInfo(int p_70335_, Block p_70336_) {
        this.f_70331_ = p_70335_;
        this.f_161900_ = p_70336_;
    }

    public int m_70337_() {
        return this.f_70331_;
    }

    public BlockState m_70344_() {
        return this.f_161900_.m_49966_();
    }

    public String toString() {
        return (String)(this.f_70331_ != 1 ? this.f_70331_ + "*" : "") + Registry.f_122824_.m_7981_(this.f_161900_);
    }
}

