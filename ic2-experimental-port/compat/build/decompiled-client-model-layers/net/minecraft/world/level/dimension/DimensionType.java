/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.MapCodec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.dimension;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.nio.file.Path;
import java.util.Optional;
import java.util.OptionalLong;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Mth;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;

public record DimensionType(OptionalLong f_63854_, boolean f_223549_, boolean f_63856_, boolean f_63857_, boolean f_63858_, double f_63859_, boolean f_63862_, boolean f_63863_, int f_156647_, int f_156648_, int f_63865_, TagKey<Block> f_63836_, ResourceLocation f_63837_, float f_63838_, MonsterSettings f_223550_) {
    public static final int f_156649_ = BlockPos.f_121857_;
    public static final int f_156650_ = 16;
    public static final int f_156651_ = (1 << f_156649_) - 32;
    public static final int f_156652_ = (f_156651_ >> 1) - 1;
    public static final int f_156653_ = f_156652_ - f_156651_ + 1;
    public static final int f_188293_ = f_156652_ << 4;
    public static final int f_188294_ = f_156653_ << 4;
    public static final Codec<DimensionType> f_63843_ = ExtraCodecs.m_216185_(RecordCodecBuilder.create(p_223568_ -> p_223568_.group((App)ExtraCodecs.m_216166_((MapCodec<Optional<Long>>)Codec.LONG.optionalFieldOf("fixed_time")).forGetter(DimensionType::f_63854_), (App)Codec.BOOL.fieldOf("has_skylight").forGetter(DimensionType::f_223549_), (App)Codec.BOOL.fieldOf("has_ceiling").forGetter(DimensionType::f_63856_), (App)Codec.BOOL.fieldOf("ultrawarm").forGetter(DimensionType::f_63857_), (App)Codec.BOOL.fieldOf("natural").forGetter(DimensionType::f_63858_), (App)Codec.doubleRange((double)1.0E-5f, (double)3.0E7).fieldOf("coordinate_scale").forGetter(DimensionType::f_63859_), (App)Codec.BOOL.fieldOf("bed_works").forGetter(DimensionType::f_63862_), (App)Codec.BOOL.fieldOf("respawn_anchor_works").forGetter(DimensionType::f_63863_), (App)Codec.intRange((int)f_156653_, (int)f_156652_).fieldOf("min_y").forGetter(DimensionType::f_156647_), (App)Codec.intRange((int)16, (int)f_156651_).fieldOf("height").forGetter(DimensionType::f_156648_), (App)Codec.intRange((int)0, (int)f_156651_).fieldOf("logical_height").forGetter(DimensionType::f_63865_), (App)TagKey.m_203886_(Registry.f_122901_).fieldOf("infiniburn").forGetter(DimensionType::f_63836_), (App)ResourceLocation.f_135803_.fieldOf("effects").orElse((Object)BuiltinDimensionTypes.f_223542_).forGetter(DimensionType::f_63837_), (App)Codec.FLOAT.fieldOf("ambient_light").forGetter(DimensionType::f_63838_), (App)MonsterSettings.f_223578_.forGetter(DimensionType::f_223550_)).apply((Applicative)p_223568_, DimensionType::new)));
    private static final int f_156654_ = 8;
    public static final float[] f_63844_ = new float[]{1.0f, 0.75f, 0.5f, 0.25f, 0.0f, 0.25f, 0.5f, 0.75f};
    public static final Codec<Holder<DimensionType>> f_63853_ = RegistryFileCodec.m_135589_(Registry.f_122818_, f_63843_);

    public DimensionType {
        if (f_156648_ < 16) {
            throw new IllegalStateException("height has to be at least 16");
        }
        if (f_156647_ + f_156648_ > f_156652_ + 1) {
            throw new IllegalStateException("min_y + height cannot be higher than: " + (f_156652_ + 1));
        }
        if (f_63865_ > f_156648_) {
            throw new IllegalStateException("logical_height cannot be higher than height");
        }
        if (f_156648_ % 16 != 0) {
            throw new IllegalStateException("height has to be multiple of 16");
        }
        if (f_156647_ % 16 != 0) {
            throw new IllegalStateException("min_y has to be a multiple of 16");
        }
    }

    @Deprecated
    public static DataResult<ResourceKey<Level>> m_63911_(Dynamic<?> p_63912_) {
        Optional $$1 = p_63912_.asNumber().result();
        if ($$1.isPresent()) {
            int $$2 = ((Number)$$1.get()).intValue();
            if ($$2 == -1) {
                return DataResult.success(Level.f_46429_);
            }
            if ($$2 == 0) {
                return DataResult.success(Level.f_46428_);
            }
            if ($$2 == 1) {
                return DataResult.success(Level.f_46430_);
            }
        }
        return Level.f_46427_.parse(p_63912_);
    }

    public static double m_63908_(DimensionType p_63909_, DimensionType p_63910_) {
        double $$2 = p_63909_.f_63859_();
        double $$3 = p_63910_.f_63859_();
        return $$2 / $$3;
    }

    public static Path m_196975_(ResourceKey<Level> p_196976_, Path p_196977_) {
        if (p_196976_ == Level.f_46428_) {
            return p_196977_;
        }
        if (p_196976_ == Level.f_46430_) {
            return p_196977_.resolve("DIM1");
        }
        if (p_196976_ == Level.f_46429_) {
            return p_196977_.resolve("DIM-1");
        }
        return p_196977_.resolve("dimensions").resolve(p_196976_.m_135782_().m_135827_()).resolve(p_196976_.m_135782_().m_135815_());
    }

    public boolean m_63967_() {
        return this.f_63854_.isPresent();
    }

    public float m_63904_(long p_63905_) {
        double $$1 = Mth.m_14185_((double)this.f_63854_.orElse(p_63905_) / 24000.0 - 0.25);
        double $$2 = 0.5 - Math.cos($$1 * Math.PI) / 2.0;
        return (float)($$1 * 2.0 + $$2) / 3.0f;
    }

    public int m_63936_(long p_63937_) {
        return (int)(p_63937_ / 24000L % 8L + 8L) % 8;
    }

    public boolean m_63960_() {
        return this.f_223550_.f_223579_();
    }

    public boolean m_63963_() {
        return this.f_223550_.f_223580_();
    }

    public IntProvider m_223569_() {
        return this.f_223550_.f_223581_();
    }

    public int m_223570_() {
        return this.f_223550_.f_223582_();
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{DimensionType.class, "fixedTime;hasSkyLight;hasCeiling;ultraWarm;natural;coordinateScale;bedWorks;respawnAnchorWorks;minY;height;logicalHeight;infiniburn;effectsLocation;ambientLight;monsterSettings", "f_63854_", "f_223549_", "f_63856_", "f_63857_", "f_63858_", "f_63859_", "f_63862_", "f_63863_", "f_156647_", "f_156648_", "f_63865_", "f_63836_", "f_63837_", "f_63838_", "f_223550_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{DimensionType.class, "fixedTime;hasSkyLight;hasCeiling;ultraWarm;natural;coordinateScale;bedWorks;respawnAnchorWorks;minY;height;logicalHeight;infiniburn;effectsLocation;ambientLight;monsterSettings", "f_63854_", "f_223549_", "f_63856_", "f_63857_", "f_63858_", "f_63859_", "f_63862_", "f_63863_", "f_156647_", "f_156648_", "f_63865_", "f_63836_", "f_63837_", "f_63838_", "f_223550_"}, this);
    }

    @Override
    public final boolean equals(Object p_223572_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{DimensionType.class, "fixedTime;hasSkyLight;hasCeiling;ultraWarm;natural;coordinateScale;bedWorks;respawnAnchorWorks;minY;height;logicalHeight;infiniburn;effectsLocation;ambientLight;monsterSettings", "f_63854_", "f_223549_", "f_63856_", "f_63857_", "f_63858_", "f_63859_", "f_63862_", "f_63863_", "f_156647_", "f_156648_", "f_63865_", "f_63836_", "f_63837_", "f_63838_", "f_223550_"}, this, p_223572_);
    }

    public record MonsterSettings(boolean f_223579_, boolean f_223580_, IntProvider f_223581_, int f_223582_) {
        public static final MapCodec<MonsterSettings> f_223578_ = RecordCodecBuilder.mapCodec(p_223591_ -> p_223591_.group((App)Codec.BOOL.fieldOf("piglin_safe").forGetter(MonsterSettings::f_223579_), (App)Codec.BOOL.fieldOf("has_raids").forGetter(MonsterSettings::f_223580_), (App)IntProvider.m_146545_(0, 15).fieldOf("monster_spawn_light_level").forGetter(MonsterSettings::f_223581_), (App)Codec.intRange((int)0, (int)15).fieldOf("monster_spawn_block_light_limit").forGetter(MonsterSettings::f_223582_)).apply((Applicative)p_223591_, MonsterSettings::new));

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{MonsterSettings.class, "piglinSafe;hasRaids;monsterSpawnLightTest;monsterSpawnBlockLightLimit", "f_223579_", "f_223580_", "f_223581_", "f_223582_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{MonsterSettings.class, "piglinSafe;hasRaids;monsterSpawnLightTest;monsterSpawnBlockLightLimit", "f_223579_", "f_223580_", "f_223581_", "f_223582_"}, this);
        }

        @Override
        public final boolean equals(Object p_223596_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{MonsterSettings.class, "piglinSafe;hasRaids;monsterSpawnLightTest;monsterSpawnBlockLightLimit", "f_223579_", "f_223580_", "f_223581_", "f_223582_"}, this, p_223596_);
        }
    }
}

