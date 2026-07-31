/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.Keyable
 *  com.mojang.serialization.MapCodec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.biome;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Keyable;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.random.Weight;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import org.slf4j.Logger;

public class MobSpawnSettings {
    private static final Logger f_48325_ = LogUtils.getLogger();
    private static final float f_151797_ = 0.1f;
    public static final WeightedRandomList<SpawnerData> f_151796_ = WeightedRandomList.m_146332_();
    public static final MobSpawnSettings f_48326_ = new Builder().m_48381_();
    public static final MapCodec<MobSpawnSettings> f_48327_ = RecordCodecBuilder.mapCodec(p_187051_ -> p_187051_.group((App)Codec.floatRange((float)0.0f, (float)0.9999999f).optionalFieldOf("creature_spawn_probability", (Object)Float.valueOf(0.1f)).forGetter(p_187055_ -> Float.valueOf(p_187055_.f_48328_)), (App)Codec.simpleMap(MobCategory.f_21584_, (Codec)WeightedRandomList.m_146333_(SpawnerData.f_48403_).promotePartial(Util.m_137489_("Spawn data: ", arg_0 -> ((Logger)f_48325_).error(arg_0))), (Keyable)StringRepresentable.m_14357_(MobCategory.values())).fieldOf("spawners").forGetter(p_187053_ -> p_187053_.f_48329_), (App)Codec.simpleMap(Registry.f_122826_.m_194605_(), MobSpawnCost.f_48384_, Registry.f_122826_).fieldOf("spawn_costs").forGetter(p_187049_ -> p_187049_.f_48330_)).apply((Applicative)p_187051_, MobSpawnSettings::new));
    private final float f_48328_;
    private final Map<MobCategory, WeightedRandomList<SpawnerData>> f_48329_;
    private final Map<EntityType<?>, MobSpawnCost> f_48330_;

    MobSpawnSettings(float p_196689_, Map<MobCategory, WeightedRandomList<SpawnerData>> p_196690_, Map<EntityType<?>, MobSpawnCost> p_196691_) {
        this.f_48328_ = p_196689_;
        this.f_48329_ = ImmutableMap.copyOf(p_196690_);
        this.f_48330_ = ImmutableMap.copyOf(p_196691_);
    }

    public WeightedRandomList<SpawnerData> m_151798_(MobCategory p_151799_) {
        return this.f_48329_.getOrDefault(p_151799_, f_151796_);
    }

    @Nullable
    public MobSpawnCost m_48345_(EntityType<?> p_48346_) {
        return this.f_48330_.get(p_48346_);
    }

    public float m_48344_() {
        return this.f_48328_;
    }

    public static class MobSpawnCost {
        public static final Codec<MobSpawnCost> f_48384_ = RecordCodecBuilder.create(p_48399_ -> p_48399_.group((App)Codec.DOUBLE.fieldOf("energy_budget").forGetter(p_151813_ -> p_151813_.f_48385_), (App)Codec.DOUBLE.fieldOf("charge").forGetter(p_151811_ -> p_151811_.f_48386_)).apply((Applicative)p_48399_, MobSpawnCost::new));
        private final double f_48385_;
        private final double f_48386_;

        MobSpawnCost(double p_48389_, double p_48390_) {
            this.f_48385_ = p_48389_;
            this.f_48386_ = p_48390_;
        }

        public double m_48395_() {
            return this.f_48385_;
        }

        public double m_48400_() {
            return this.f_48386_;
        }
    }

    public static class SpawnerData
    extends WeightedEntry.IntrusiveBase {
        public static final Codec<SpawnerData> f_48403_ = RecordCodecBuilder.create(p_151822_ -> p_151822_.group((App)Registry.f_122826_.m_194605_().fieldOf("type").forGetter(p_151826_ -> p_151826_.f_48404_), (App)Weight.f_146274_.fieldOf("weight").forGetter(WeightedEntry.IntrusiveBase::m_142631_), (App)Codec.INT.fieldOf("minCount").forGetter(p_151824_ -> p_151824_.f_48405_), (App)Codec.INT.fieldOf("maxCount").forGetter(p_151820_ -> p_151820_.f_48406_)).apply((Applicative)p_151822_, SpawnerData::new));
        public final EntityType<?> f_48404_;
        public final int f_48405_;
        public final int f_48406_;

        public SpawnerData(EntityType<?> p_48409_, int p_48410_, int p_48411_, int p_48412_) {
            this(p_48409_, Weight.m_146282_(p_48410_), p_48411_, p_48412_);
        }

        public SpawnerData(EntityType<?> p_151815_, Weight p_151816_, int p_151817_, int p_151818_) {
            super(p_151816_);
            this.f_48404_ = p_151815_.m_20674_() == MobCategory.MISC ? EntityType.f_20510_ : p_151815_;
            this.f_48405_ = p_151817_;
            this.f_48406_ = p_151818_;
        }

        public String toString() {
            return EntityType.m_20613_(this.f_48404_) + "*(" + this.f_48405_ + "-" + this.f_48406_ + "):" + this.m_142631_();
        }
    }

    public static class Builder {
        private final Map<MobCategory, List<SpawnerData>> f_48362_ = (Map)Stream.of(MobCategory.values()).collect(ImmutableMap.toImmutableMap(p_48383_ -> p_48383_, p_48375_ -> Lists.newArrayList()));
        private final Map<EntityType<?>, MobSpawnCost> f_48363_ = Maps.newLinkedHashMap();
        private float f_48364_ = 0.1f;

        public Builder m_48376_(MobCategory p_48377_, SpawnerData p_48378_) {
            this.f_48362_.get(p_48377_).add(p_48378_);
            return this;
        }

        public Builder m_48370_(EntityType<?> p_48371_, double p_48372_, double p_48373_) {
            this.f_48363_.put(p_48371_, new MobSpawnCost(p_48373_, p_48372_));
            return this;
        }

        public Builder m_48368_(float p_48369_) {
            this.f_48364_ = p_48369_;
            return this;
        }

        public MobSpawnSettings m_48381_() {
            return new MobSpawnSettings(this.f_48364_, (Map)this.f_48362_.entrySet().stream().collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, p_151809_ -> WeightedRandomList.m_146328_((List)p_151809_.getValue()))), (Map<EntityType<?>, MobSpawnCost>)ImmutableMap.copyOf(this.f_48363_));
        }
    }
}

