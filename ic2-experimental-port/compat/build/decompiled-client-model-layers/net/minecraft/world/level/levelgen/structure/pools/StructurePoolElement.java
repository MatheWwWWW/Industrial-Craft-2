/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Either
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.levelgen.structure.pools;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.Vec3i;
import net.minecraft.data.worldgen.ProcessorLists;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pools.EmptyPoolElement;
import net.minecraft.world.level.levelgen.structure.pools.FeaturePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.LegacySinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.ListPoolElement;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public abstract class StructurePoolElement {
    public static final Codec<StructurePoolElement> f_210468_ = Registry.f_122892_.m_194605_().dispatch("element_type", StructurePoolElement::m_207234_, StructurePoolElementType::m_210553_);
    @Nullable
    private volatile StructureTemplatePool.Projection f_210467_;

    protected static <E extends StructurePoolElement> RecordCodecBuilder<E, StructureTemplatePool.Projection> m_210538_() {
        return StructureTemplatePool.Projection.f_210593_.fieldOf("projection").forGetter(StructurePoolElement::m_210539_);
    }

    protected StructurePoolElement(StructureTemplatePool.Projection p_210471_) {
        this.f_210467_ = p_210471_;
    }

    public abstract Vec3i m_213577_(StructureTemplateManager var1, Rotation var2);

    public abstract List<StructureTemplate.StructureBlockInfo> m_213638_(StructureTemplateManager var1, BlockPos var2, Rotation var3, RandomSource var4);

    public abstract BoundingBox m_214015_(StructureTemplateManager var1, BlockPos var2, Rotation var3);

    public abstract boolean m_213695_(StructureTemplateManager var1, WorldGenLevel var2, StructureManager var3, ChunkGenerator var4, BlockPos var5, BlockPos var6, Rotation var7, BoundingBox var8, RandomSource var9, boolean var10);

    public abstract StructurePoolElementType<?> m_207234_();

    public void m_227329_(LevelAccessor p_227330_, StructureTemplate.StructureBlockInfo p_227331_, BlockPos p_227332_, Rotation p_227333_, RandomSource p_227334_, BoundingBox p_227335_) {
    }

    public StructurePoolElement m_207247_(StructureTemplatePool.Projection p_210479_) {
        this.f_210467_ = p_210479_;
        return this;
    }

    public StructureTemplatePool.Projection m_210539_() {
        StructureTemplatePool.Projection $$0 = this.f_210467_;
        if ($$0 == null) {
            throw new IllegalStateException();
        }
        return $$0;
    }

    public int m_210540_() {
        return 1;
    }

    public static Function<StructureTemplatePool.Projection, EmptyPoolElement> m_210541_() {
        return p_210525_ -> EmptyPoolElement.f_210175_;
    }

    public static Function<StructureTemplatePool.Projection, LegacySinglePoolElement> m_210507_(String p_210508_) {
        return p_210530_ -> new LegacySinglePoolElement((Either<ResourceLocation, StructureTemplate>)Either.left((Object)new ResourceLocation(p_210508_)), ProcessorLists.f_127198_, (StructureTemplatePool.Projection)p_210530_);
    }

    public static Function<StructureTemplatePool.Projection, LegacySinglePoolElement> m_210512_(String p_210513_, Holder<StructureProcessorList> p_210514_) {
        return p_210537_ -> new LegacySinglePoolElement((Either<ResourceLocation, StructureTemplate>)Either.left((Object)new ResourceLocation(p_210513_)), p_210514_, (StructureTemplatePool.Projection)p_210537_);
    }

    public static Function<StructureTemplatePool.Projection, SinglePoolElement> m_210526_(String p_210527_) {
        return p_210511_ -> new SinglePoolElement((Either<ResourceLocation, StructureTemplate>)Either.left((Object)new ResourceLocation(p_210527_)), ProcessorLists.f_127198_, (StructureTemplatePool.Projection)p_210511_);
    }

    public static Function<StructureTemplatePool.Projection, SinglePoolElement> m_210531_(String p_210532_, Holder<StructureProcessorList> p_210533_) {
        return p_210518_ -> new SinglePoolElement((Either<ResourceLocation, StructureTemplate>)Either.left((Object)new ResourceLocation(p_210532_)), p_210533_, (StructureTemplatePool.Projection)p_210518_);
    }

    public static Function<StructureTemplatePool.Projection, FeaturePoolElement> m_210502_(Holder<PlacedFeature> p_210503_) {
        return p_210506_ -> new FeaturePoolElement(p_210503_, (StructureTemplatePool.Projection)p_210506_);
    }

    public static Function<StructureTemplatePool.Projection, ListPoolElement> m_210519_(List<Function<StructureTemplatePool.Projection, ? extends StructurePoolElement>> p_210520_) {
        return p_210523_ -> new ListPoolElement(p_210520_.stream().map(p_210482_ -> (StructurePoolElement)p_210482_.apply(p_210523_)).collect(Collectors.toList()), (StructureTemplatePool.Projection)p_210523_);
    }
}

