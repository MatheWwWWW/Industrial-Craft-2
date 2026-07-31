/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.levelgen.structure.pieces;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

@FunctionalInterface
public interface PieceGeneratorSupplier<C extends FeatureConfiguration> {
    public Optional<PieceGenerator<C>> m_197347_(Context<C> var1);

    public static <C extends FeatureConfiguration> PieceGeneratorSupplier<C> m_197349_(Predicate<Context<C>> p_197350_, PieceGenerator<C> p_197351_) {
        Optional $$2 = Optional.of(p_197351_);
        return p_197344_ -> p_197350_.test(p_197344_) ? $$2 : Optional.empty();
    }

    public static <C extends FeatureConfiguration> Predicate<Context<C>> m_197345_(Heightmap.Types p_197346_) {
        return p_197340_ -> p_197340_.m_197380_(p_197346_);
    }

    public record Context<C extends FeatureConfiguration>(ChunkGenerator f_197352_, BiomeSource f_197353_, RandomState f_226941_, long f_197354_, ChunkPos f_197355_, C f_197356_, LevelHeightAccessor f_197357_, Predicate<Holder<Biome>> f_197358_, StructureTemplateManager f_226942_, RegistryAccess f_197360_) {
        public boolean m_197380_(Heightmap.Types p_197381_) {
            int $$1 = this.f_197355_.m_151390_();
            int $$2 = this.f_197355_.m_151393_();
            int $$3 = this.f_197352_.m_223235_($$1, $$2, p_197381_, this.f_197357_, this.f_226941_);
            Holder<Biome> $$4 = this.f_197352_.m_62218_().m_203407_(QuartPos.m_175400_($$1), QuartPos.m_175400_($$3), QuartPos.m_175400_($$2), this.f_226941_.m_224579_());
            return this.f_197358_.test($$4);
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Context.class, "chunkGenerator;biomeSource;randomState;seed;chunkPos;config;heightAccessor;validBiome;structureTemplateManager;registryAccess", "f_197352_", "f_197353_", "f_226941_", "f_197354_", "f_197355_", "f_197356_", "f_197357_", "f_197358_", "f_226942_", "f_197360_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Context.class, "chunkGenerator;biomeSource;randomState;seed;chunkPos;config;heightAccessor;validBiome;structureTemplateManager;registryAccess", "f_197352_", "f_197353_", "f_226941_", "f_197354_", "f_197355_", "f_197356_", "f_197357_", "f_197358_", "f_226942_", "f_197360_"}, this);
        }

        @Override
        public final boolean equals(Object p_197387_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Context.class, "chunkGenerator;biomeSource;randomState;seed;chunkPos;config;heightAccessor;validBiome;structureTemplateManager;registryAccess", "f_197352_", "f_197353_", "f_226941_", "f_197354_", "f_197355_", "f_197356_", "f_197357_", "f_197358_", "f_226942_", "f_197360_"}, this, p_197387_);
        }
    }
}

