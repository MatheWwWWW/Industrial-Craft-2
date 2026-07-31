/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.levelgen.structure.pieces;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

@FunctionalInterface
public interface PieceGenerator<C extends FeatureConfiguration> {
    public void m_197325_(StructurePiecesBuilder var1, Context<C> var2);

    public record Context<C extends FeatureConfiguration>(C f_197328_, ChunkGenerator f_192703_, StructureTemplateManager f_226931_, ChunkPos f_192705_, LevelHeightAccessor f_192707_, WorldgenRandom f_192708_, long f_192709_) {
        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Context.class, "config;chunkGenerator;structureTemplateManager;chunkPos;heightAccessor;random;seed", "f_197328_", "f_192703_", "f_226931_", "f_192705_", "f_192707_", "f_192708_", "f_192709_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Context.class, "config;chunkGenerator;structureTemplateManager;chunkPos;heightAccessor;random;seed", "f_197328_", "f_192703_", "f_226931_", "f_192705_", "f_192707_", "f_192708_", "f_192709_"}, this);
        }

        @Override
        public final boolean equals(Object p_192735_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Context.class, "config;chunkGenerator;structureTemplateManager;chunkPos;heightAccessor;random;seed", "f_197328_", "f_192703_", "f_226931_", "f_192705_", "f_192707_", "f_192708_", "f_192709_"}, this, p_192735_);
        }
    }
}

