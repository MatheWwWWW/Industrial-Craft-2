/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;

public class DataPackConfig {
    public static final DataPackConfig f_45842_ = new DataPackConfig((List<String>)ImmutableList.of((Object)"vanilla"), (List<String>)ImmutableList.of());
    public static final Codec<DataPackConfig> f_45843_ = RecordCodecBuilder.create(p_45854_ -> p_45854_.group((App)Codec.STRING.listOf().fieldOf("Enabled").forGetter(p_151457_ -> p_151457_.f_45844_), (App)Codec.STRING.listOf().fieldOf("Disabled").forGetter(p_151455_ -> p_151455_.f_45845_)).apply((Applicative)p_45854_, DataPackConfig::new));
    private final List<String> f_45844_;
    private final List<String> f_45845_;

    public DataPackConfig(List<String> p_45848_, List<String> p_45849_) {
        this.f_45844_ = ImmutableList.copyOf(p_45848_);
        this.f_45845_ = ImmutableList.copyOf(p_45849_);
    }

    public List<String> m_45850_() {
        return this.f_45844_;
    }

    public List<String> m_45855_() {
        return this.f_45845_;
    }
}

