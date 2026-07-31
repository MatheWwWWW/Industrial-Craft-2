/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.function.Function;
import net.minecraft.core.QuartPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.dimension.DimensionType;

public record NoiseSettings(int f_158688_, int f_64508_, int f_64512_, int f_64513_) {
    public static final Codec<NoiseSettings> f_64507_ = RecordCodecBuilder.create(p_64536_ -> p_64536_.group((App)Codec.intRange((int)DimensionType.f_156653_, (int)DimensionType.f_156652_).fieldOf("min_y").forGetter(NoiseSettings::f_158688_), (App)Codec.intRange((int)0, (int)DimensionType.f_156651_).fieldOf("height").forGetter(NoiseSettings::f_64508_), (App)Codec.intRange((int)1, (int)4).fieldOf("size_horizontal").forGetter(NoiseSettings::f_64512_), (App)Codec.intRange((int)1, (int)4).fieldOf("size_vertical").forGetter(NoiseSettings::f_64513_)).apply((Applicative)p_64536_, NoiseSettings::new)).comapFlatMap(NoiseSettings::m_158720_, Function.identity());
    protected static final NoiseSettings f_224519_ = NoiseSettings.m_224525_(-64, 384, 1, 2);
    protected static final NoiseSettings f_209630_ = NoiseSettings.m_224525_(0, 128, 1, 2);
    protected static final NoiseSettings f_209631_ = NoiseSettings.m_224525_(0, 128, 2, 1);
    protected static final NoiseSettings f_209632_ = NoiseSettings.m_224525_(-64, 192, 1, 2);
    protected static final NoiseSettings f_209633_ = NoiseSettings.m_224525_(0, 256, 2, 1);

    private static DataResult<NoiseSettings> m_158720_(NoiseSettings p_158721_) {
        if (p_158721_.f_158688_() + p_158721_.f_64508_() > DimensionType.f_156652_ + 1) {
            return DataResult.error((String)("min_y + height cannot be higher than: " + (DimensionType.f_156652_ + 1)));
        }
        if (p_158721_.f_64508_() % 16 != 0) {
            return DataResult.error((String)"height has to be a multiple of 16");
        }
        if (p_158721_.f_158688_() % 16 != 0) {
            return DataResult.error((String)"min_y has to be a multiple of 16");
        }
        return DataResult.success((Object)p_158721_);
    }

    public static NoiseSettings m_224525_(int p_224526_, int p_224527_, int p_224528_, int p_224529_) {
        NoiseSettings $$4 = new NoiseSettings(p_224526_, p_224527_, p_224528_, p_224529_);
        NoiseSettings.m_158720_($$4).error().ifPresent(p_158719_ -> {
            throw new IllegalStateException(p_158719_.message());
        });
        return $$4;
    }

    public int m_189212_() {
        return QuartPos.m_175402_(this.f_64513_());
    }

    public int m_189213_() {
        return QuartPos.m_175402_(this.f_64512_());
    }

    public NoiseSettings m_224530_(LevelHeightAccessor p_224531_) {
        int $$1 = Math.max(this.f_158688_, p_224531_.m_141937_());
        int $$2 = Math.min(this.f_158688_ + this.f_64508_, p_224531_.m_151558_()) - $$1;
        return new NoiseSettings($$1, $$2, this.f_64512_, this.f_64513_);
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{NoiseSettings.class, "minY;height;noiseSizeHorizontal;noiseSizeVertical", "f_158688_", "f_64508_", "f_64512_", "f_64513_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{NoiseSettings.class, "minY;height;noiseSizeHorizontal;noiseSizeVertical", "f_158688_", "f_64508_", "f_64512_", "f_64513_"}, this);
    }

    @Override
    public final boolean equals(Object p_189215_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{NoiseSettings.class, "minY;height;noiseSizeHorizontal;noiseSizeVertical", "f_158688_", "f_64508_", "f_64512_", "f_64513_"}, this, p_189215_);
    }
}

