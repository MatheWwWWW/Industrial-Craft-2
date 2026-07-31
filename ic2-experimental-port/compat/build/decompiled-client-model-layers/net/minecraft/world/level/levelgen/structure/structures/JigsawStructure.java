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
package net.minecraft.world.level.levelgen.structure.structures;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

public final class JigsawStructure
extends Structure {
    public static final int f_227603_ = 128;
    public static final Codec<JigsawStructure> f_227604_ = RecordCodecBuilder.mapCodec(p_227640_ -> p_227640_.group(JigsawStructure.m_226567_(p_227640_), (App)StructureTemplatePool.f_210555_.fieldOf("start_pool").forGetter(p_227656_ -> p_227656_.f_227605_), (App)ResourceLocation.f_135803_.optionalFieldOf("start_jigsaw_name").forGetter(p_227654_ -> p_227654_.f_227606_), (App)Codec.intRange((int)0, (int)7).fieldOf("size").forGetter(p_227652_ -> p_227652_.f_227607_), (App)HeightProvider.f_161970_.fieldOf("start_height").forGetter(p_227649_ -> p_227649_.f_227608_), (App)Codec.BOOL.fieldOf("use_expansion_hack").forGetter(p_227646_ -> p_227646_.f_227609_), (App)Heightmap.Types.f_64274_.optionalFieldOf("project_start_to_heightmap").forGetter(p_227644_ -> p_227644_.f_227610_), (App)Codec.intRange((int)1, (int)128).fieldOf("max_distance_from_center").forGetter(p_227642_ -> p_227642_.f_227611_)).apply((Applicative)p_227640_, JigsawStructure::new)).flatXmap(JigsawStructure.m_227650_(), JigsawStructure.m_227650_()).codec();
    private final Holder<StructureTemplatePool> f_227605_;
    private final Optional<ResourceLocation> f_227606_;
    private final int f_227607_;
    private final HeightProvider f_227608_;
    private final boolean f_227609_;
    private final Optional<Heightmap.Types> f_227610_;
    private final int f_227611_;

    private static Function<JigsawStructure, DataResult<JigsawStructure>> m_227650_() {
        return p_227638_ -> {
            int $$1;
            switch (p_227638_.m_226620_()) {
                default: {
                    throw new IncompatibleClassChangeError();
                }
                case NONE: {
                    int n = 0;
                    break;
                }
                case BURY: 
                case BEARD_THIN: 
                case BEARD_BOX: {
                    int n = $$1 = 12;
                }
            }
            if (p_227638_.f_227611_ + $$1 > 128) {
                return DataResult.error((String)"Structure size including terrain adaptation must not exceed 128");
            }
            return DataResult.success((Object)p_227638_);
        };
    }

    public JigsawStructure(Structure.StructureSettings p_227627_, Holder<StructureTemplatePool> p_227628_, Optional<ResourceLocation> p_227629_, int p_227630_, HeightProvider p_227631_, boolean p_227632_, Optional<Heightmap.Types> p_227633_, int p_227634_) {
        super(p_227627_);
        this.f_227605_ = p_227628_;
        this.f_227606_ = p_227629_;
        this.f_227607_ = p_227630_;
        this.f_227608_ = p_227631_;
        this.f_227609_ = p_227632_;
        this.f_227610_ = p_227633_;
        this.f_227611_ = p_227634_;
    }

    public JigsawStructure(Structure.StructureSettings p_227620_, Holder<StructureTemplatePool> p_227621_, int p_227622_, HeightProvider p_227623_, boolean p_227624_, Heightmap.Types p_227625_) {
        this(p_227620_, p_227621_, Optional.empty(), p_227622_, p_227623_, p_227624_, Optional.of(p_227625_), 80);
    }

    public JigsawStructure(Structure.StructureSettings p_227614_, Holder<StructureTemplatePool> p_227615_, int p_227616_, HeightProvider p_227617_, boolean p_227618_) {
        this(p_227614_, p_227615_, Optional.empty(), p_227616_, p_227617_, p_227618_, Optional.empty(), 80);
    }

    @Override
    public Optional<Structure.GenerationStub> m_214086_(Structure.GenerationContext p_227636_) {
        ChunkPos $$1 = p_227636_.f_226628_();
        int $$2 = this.f_227608_.m_213859_(p_227636_.f_226626_(), new WorldGenerationContext(p_227636_.f_226622_(), p_227636_.f_226629_()));
        BlockPos $$3 = new BlockPos($$1.m_45604_(), $$2, $$1.m_45605_());
        Pools.m_236490_();
        return JigsawPlacement.m_227238_(p_227636_, this.f_227605_, this.f_227606_, this.f_227607_, $$3, this.f_227609_, this.f_227610_, this.f_227611_);
    }

    @Override
    public StructureType<?> m_213658_() {
        return StructureType.f_226867_;
    }
}

