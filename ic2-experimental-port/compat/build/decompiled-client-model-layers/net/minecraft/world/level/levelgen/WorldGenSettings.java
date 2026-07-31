/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.Lifecycle
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  org.apache.commons.lang3.StringUtils
 */
package net.minecraft.world.level.levelgen;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.function.Function;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.WritableRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.DebugLevelSource;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import org.apache.commons.lang3.StringUtils;

public class WorldGenSettings {
    public static final Codec<WorldGenSettings> f_64600_ = RecordCodecBuilder.create(p_64626_ -> p_64626_.group((App)Codec.LONG.fieldOf("seed").stable().forGetter(WorldGenSettings::m_64619_), (App)Codec.BOOL.fieldOf("generate_features").orElse((Object)true).stable().forGetter(WorldGenSettings::m_224677_), (App)Codec.BOOL.fieldOf("bonus_chest").orElse((Object)false).stable().forGetter(WorldGenSettings::m_64660_), (App)RegistryCodecs.m_206318_(Registry.f_122820_, Lifecycle.stable(), LevelStem.f_63970_).xmap(LevelStem::m_204525_, Function.identity()).fieldOf("dimensions").forGetter(WorldGenSettings::m_204655_), (App)Codec.STRING.optionalFieldOf("legacy_custom_options").stable().forGetter(p_158959_ -> p_158959_.f_64606_)).apply((Applicative)p_64626_, p_64626_.stable(WorldGenSettings::new))).comapFlatMap(WorldGenSettings::m_64674_, Function.identity());
    private final long f_64602_;
    private final boolean f_224672_;
    private final boolean f_64604_;
    private final Registry<LevelStem> f_64605_;
    private final Optional<String> f_64606_;

    private DataResult<WorldGenSettings> m_64674_() {
        LevelStem $$0 = this.f_64605_.m_6246_(LevelStem.f_63971_);
        if ($$0 == null) {
            return DataResult.error((String)"Overworld settings missing");
        }
        if (this.m_64675_()) {
            return DataResult.success((Object)this, (Lifecycle)Lifecycle.stable());
        }
        return DataResult.success((Object)this);
    }

    private boolean m_64675_() {
        return LevelStem.m_223607_(this.f_64605_);
    }

    public WorldGenSettings(long p_204633_, boolean p_204634_, boolean p_204635_, Registry<LevelStem> p_204636_) {
        this(p_204633_, p_204634_, p_204635_, p_204636_, Optional.empty());
        LevelStem $$4 = p_204636_.m_6246_(LevelStem.f_63971_);
        if ($$4 == null) {
            throw new IllegalStateException("Overworld settings missing");
        }
    }

    private WorldGenSettings(long p_204638_, boolean p_204639_, boolean p_204640_, Registry<LevelStem> p_204641_, Optional<String> p_204642_) {
        this.f_64602_ = p_204638_;
        this.f_224672_ = p_204639_;
        this.f_64604_ = p_204640_;
        this.f_64605_ = p_204641_;
        this.f_64606_ = p_204642_;
    }

    public long m_64619_() {
        return this.f_64602_;
    }

    public boolean m_224677_() {
        return this.f_224672_;
    }

    public boolean m_64660_() {
        return this.f_64604_;
    }

    public static WorldGenSettings m_224673_(RegistryAccess p_224674_, WorldGenSettings p_224675_, ChunkGenerator p_224676_) {
        Registry<DimensionType> $$3 = p_224674_.m_175515_(Registry.f_122818_);
        Registry<LevelStem> $$4 = WorldGenSettings.m_204649_($$3, p_224675_.m_204655_(), p_224676_);
        return new WorldGenSettings(p_224675_.m_64619_(), p_224675_.m_224677_(), p_224675_.m_64660_(), $$4);
    }

    public static Registry<LevelStem> m_204649_(Registry<DimensionType> p_204650_, Registry<LevelStem> p_204651_, ChunkGenerator p_204652_) {
        LevelStem $$3 = p_204651_.m_6246_(LevelStem.f_63971_);
        Holder<DimensionType> $$4 = $$3 == null ? p_204650_.m_214121_(BuiltinDimensionTypes.f_223538_) : $$3.m_204521_();
        return WorldGenSettings.m_204645_(p_204651_, $$4, p_204652_);
    }

    public static Registry<LevelStem> m_204645_(Registry<LevelStem> p_204646_, Holder<DimensionType> p_204647_, ChunkGenerator p_204648_) {
        MappedRegistry<LevelStem> $$3 = new MappedRegistry<LevelStem>(Registry.f_122820_, Lifecycle.experimental(), null);
        ((WritableRegistry)$$3).m_203505_(LevelStem.f_63971_, new LevelStem(p_204647_, p_204648_), Lifecycle.stable());
        for (Map.Entry<ResourceKey<LevelStem>, LevelStem> $$4 : p_204646_.m_6579_()) {
            ResourceKey<LevelStem> $$5 = $$4.getKey();
            if ($$5 == LevelStem.f_63971_) continue;
            ((WritableRegistry)$$3).m_203505_($$5, $$4.getValue(), p_204646_.m_6228_($$4.getValue()));
        }
        return $$3;
    }

    public Registry<LevelStem> m_204655_() {
        return this.f_64605_;
    }

    public ChunkGenerator m_64666_() {
        LevelStem $$0 = this.f_64605_.m_6246_(LevelStem.f_63971_);
        if ($$0 == null) {
            throw new IllegalStateException("Overworld settings missing");
        }
        return $$0.m_63990_();
    }

    public ImmutableSet<ResourceKey<Level>> m_64667_() {
        return (ImmutableSet)this.m_204655_().m_6579_().stream().map(Map.Entry::getKey).map(WorldGenSettings::m_190048_).collect(ImmutableSet.toImmutableSet());
    }

    public static ResourceKey<Level> m_190048_(ResourceKey<LevelStem> p_190049_) {
        return ResourceKey.m_135785_(Registry.f_122819_, p_190049_.m_135782_());
    }

    public static ResourceKey<LevelStem> m_190052_(ResourceKey<Level> p_190053_) {
        return ResourceKey.m_135785_(Registry.f_122820_, p_190053_.m_135782_());
    }

    public boolean m_64668_() {
        return this.m_64666_() instanceof DebugLevelSource;
    }

    public boolean m_64669_() {
        return this.m_64666_() instanceof FlatLevelSource;
    }

    public boolean m_64670_() {
        return this.f_64606_.isPresent();
    }

    public WorldGenSettings m_64671_() {
        return new WorldGenSettings(this.f_64602_, this.f_224672_, true, this.f_64605_, this.f_64606_);
    }

    public WorldGenSettings m_224678_() {
        return new WorldGenSettings(this.f_64602_, !this.f_224672_, this.f_64604_, this.f_64605_);
    }

    public WorldGenSettings m_64673_() {
        return new WorldGenSettings(this.f_64602_, this.f_224672_, !this.f_64604_, this.f_64605_);
    }

    public WorldGenSettings m_64654_(boolean p_64655_, OptionalLong p_64656_) {
        WorldGenSettings $$9;
        Registry<LevelStem> $$7;
        long $$2 = p_64656_.orElse(this.f_64602_);
        if (p_64656_.isPresent()) {
            MappedRegistry<LevelStem> $$3 = new MappedRegistry<LevelStem>(Registry.f_122820_, Lifecycle.experimental(), null);
            for (Map.Entry<ResourceKey<LevelStem>, LevelStem> $$4 : this.f_64605_.m_6579_()) {
                ResourceKey<LevelStem> $$5 = $$4.getKey();
                ((WritableRegistry)$$3).m_203505_($$5, new LevelStem($$4.getValue().m_204521_(), $$4.getValue().m_63990_()), this.f_64605_.m_6228_($$4.getValue()));
            }
            MappedRegistry<LevelStem> $$6 = $$3;
        } else {
            $$7 = this.f_64605_;
        }
        if (this.m_64668_()) {
            WorldGenSettings $$8 = new WorldGenSettings($$2, false, false, $$7);
        } else {
            $$9 = new WorldGenSettings($$2, this.m_224677_(), this.m_64660_() && !p_64655_, $$7);
        }
        return $$9;
    }

    public static OptionalLong m_202192_(String p_202193_) {
        if (StringUtils.isEmpty((CharSequence)(p_202193_ = p_202193_.trim()))) {
            return OptionalLong.empty();
        }
        try {
            return OptionalLong.of(Long.parseLong(p_202193_));
        }
        catch (NumberFormatException $$1) {
            return OptionalLong.of(p_202193_.hashCode());
        }
    }
}

