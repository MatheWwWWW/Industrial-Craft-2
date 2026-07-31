/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  org.apache.commons.lang3.mutable.MutableBoolean
 */
package net.minecraft.world.level.levelgen.placement;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import org.apache.commons.lang3.mutable.MutableBoolean;

public record PlacedFeature(Holder<ConfiguredFeature<?, ?>> f_191775_, List<PlacementModifier> f_191776_) {
    public static final Codec<PlacedFeature> f_191772_ = RecordCodecBuilder.create(p_191788_ -> p_191788_.group((App)ConfiguredFeature.f_65374_.fieldOf("feature").forGetter(p_204928_ -> p_204928_.f_191775_), (App)PlacementModifier.f_191842_.listOf().fieldOf("placement").forGetter(p_191796_ -> p_191796_.f_191776_)).apply((Applicative)p_191788_, PlacedFeature::new));
    public static final Codec<Holder<PlacedFeature>> f_191773_ = RegistryFileCodec.m_135589_(Registry.f_194567_, f_191772_);
    public static final Codec<HolderSet<PlacedFeature>> f_191774_ = RegistryCodecs.m_206279_(Registry.f_194567_, f_191772_);
    public static final Codec<List<HolderSet<PlacedFeature>>> f_204922_ = RegistryCodecs.m_206287_(Registry.f_194567_, f_191772_, true).listOf();

    public boolean m_226357_(WorldGenLevel p_226358_, ChunkGenerator p_226359_, RandomSource p_226360_, BlockPos p_226361_) {
        return this.m_226368_(new PlacementContext(p_226358_, p_226359_, Optional.empty()), p_226360_, p_226361_);
    }

    public boolean m_226377_(WorldGenLevel p_226378_, ChunkGenerator p_226379_, RandomSource p_226380_, BlockPos p_226381_) {
        return this.m_226368_(new PlacementContext(p_226378_, p_226379_, Optional.of(this)), p_226380_, p_226381_);
    }

    private boolean m_226368_(PlacementContext p_226369_, RandomSource p_226370_, BlockPos p_226371_) {
        Stream<BlockPos> $$3 = Stream.of(p_226371_);
        for (PlacementModifier $$4 : this.f_191776_) {
            $$3 = $$3.flatMap(p_226376_ -> $$4.m_213676_(p_226369_, p_226370_, (BlockPos)p_226376_));
        }
        ConfiguredFeature<?, ?> $$5 = this.f_191775_.m_203334_();
        MutableBoolean $$6 = new MutableBoolean();
        $$3.forEach(p_226367_ -> {
            if ($$5.m_224953_(p_226369_.m_191831_(), p_226369_.m_191833_(), p_226370_, (BlockPos)p_226367_)) {
                $$6.setTrue();
            }
        });
        return $$6.isTrue();
    }

    public Stream<ConfiguredFeature<?, ?>> m_191781_() {
        return this.f_191775_.m_203334_().m_65398_();
    }

    @Override
    public String toString() {
        return "Placed " + this.f_191775_;
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{PlacedFeature.class, "feature;placement", "f_191775_", "f_191776_"}, this);
    }

    @Override
    public final boolean equals(Object p_204931_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{PlacedFeature.class, "feature;placement", "f_191775_", "f_191776_"}, this, p_204931_);
    }

    record test(int f_204933_) {
        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{test.class, "a", "f_204933_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{test.class, "a", "f_204933_"}, this);
        }

        @Override
        public final boolean equals(Object p_204938_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{test.class, "a", "f_204933_"}, this, p_204938_);
        }
    }
}

