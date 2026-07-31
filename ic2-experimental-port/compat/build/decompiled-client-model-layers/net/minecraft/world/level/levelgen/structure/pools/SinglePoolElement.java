/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.datafixers.util.Either
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.Decoder
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 */
package net.minecraft.world.level.levelgen.structure.pools;

import com.google.common.collect.Lists;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.data.worldgen.ProcessorLists;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.properties.StructureMode;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.JigsawReplacementProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class SinglePoolElement
extends StructurePoolElement {
    private static final Codec<Either<ResourceLocation, StructureTemplate>> f_210409_ = Codec.of(SinglePoolElement::m_210424_, (Decoder)ResourceLocation.f_135803_.map(Either::left));
    public static final Codec<SinglePoolElement> f_210410_ = RecordCodecBuilder.create(p_210429_ -> p_210429_.group(SinglePoolElement.m_210465_(), SinglePoolElement.m_210462_(), SinglePoolElement.m_210538_()).apply((Applicative)p_210429_, SinglePoolElement::new));
    protected final Either<ResourceLocation, StructureTemplate> f_210411_;
    protected final Holder<StructureProcessorList> f_210412_;

    private static <T> DataResult<T> m_210424_(Either<ResourceLocation, StructureTemplate> p_210425_, DynamicOps<T> p_210426_, T p_210427_) {
        Optional $$3 = p_210425_.left();
        if (!$$3.isPresent()) {
            return DataResult.error((String)"Can not serialize a runtime pool element");
        }
        return ResourceLocation.f_135803_.encode((Object)((ResourceLocation)$$3.get()), p_210426_, p_210427_);
    }

    protected static <E extends SinglePoolElement> RecordCodecBuilder<E, Holder<StructureProcessorList>> m_210462_() {
        return StructureProcessorType.f_74468_.fieldOf("processors").forGetter(p_210464_ -> p_210464_.f_210412_);
    }

    protected static <E extends SinglePoolElement> RecordCodecBuilder<E, Either<ResourceLocation, StructureTemplate>> m_210465_() {
        return f_210409_.fieldOf("location").forGetter(p_210431_ -> p_210431_.f_210411_);
    }

    protected SinglePoolElement(Either<ResourceLocation, StructureTemplate> p_210415_, Holder<StructureProcessorList> p_210416_, StructureTemplatePool.Projection p_210417_) {
        super(p_210417_);
        this.f_210411_ = p_210415_;
        this.f_210412_ = p_210416_;
    }

    public SinglePoolElement(StructureTemplate p_210419_) {
        this((Either<ResourceLocation, StructureTemplate>)Either.right((Object)p_210419_), ProcessorLists.f_127198_, StructureTemplatePool.Projection.RIGID);
    }

    @Override
    public Vec3i m_213577_(StructureTemplateManager p_227313_, Rotation p_227314_) {
        StructureTemplate $$2 = this.m_227299_(p_227313_);
        return $$2.m_163808_(p_227314_);
    }

    private StructureTemplate m_227299_(StructureTemplateManager p_227300_) {
        return (StructureTemplate)this.f_210411_.map(p_227300_::m_230359_, Function.identity());
    }

    public List<StructureTemplate.StructureBlockInfo> m_227324_(StructureTemplateManager p_227325_, BlockPos p_227326_, Rotation p_227327_, boolean p_227328_) {
        StructureTemplate $$4 = this.m_227299_(p_227325_);
        ObjectArrayList<StructureTemplate.StructureBlockInfo> $$5 = $$4.m_230335_(p_227326_, new StructurePlaceSettings().m_74379_(p_227327_), Blocks.f_50677_, p_227328_);
        ArrayList $$6 = Lists.newArrayList();
        for (StructureTemplate.StructureBlockInfo $$7 : $$5) {
            StructureMode $$8;
            if ($$7.f_74677_ == null || ($$8 = StructureMode.valueOf($$7.f_74677_.m_128461_("mode"))) != StructureMode.DATA) continue;
            $$6.add($$7);
        }
        return $$6;
    }

    @Override
    public List<StructureTemplate.StructureBlockInfo> m_213638_(StructureTemplateManager p_227320_, BlockPos p_227321_, Rotation p_227322_, RandomSource p_227323_) {
        StructureTemplate $$4 = this.m_227299_(p_227320_);
        ObjectArrayList<StructureTemplate.StructureBlockInfo> $$5 = $$4.m_230335_(p_227321_, new StructurePlaceSettings().m_74379_(p_227322_), Blocks.f_50678_, true);
        Util.m_214673_($$5, p_227323_);
        return $$5;
    }

    @Override
    public BoundingBox m_214015_(StructureTemplateManager p_227316_, BlockPos p_227317_, Rotation p_227318_) {
        StructureTemplate $$3 = this.m_227299_(p_227316_);
        return $$3.m_74633_(new StructurePlaceSettings().m_74379_(p_227318_), p_227317_);
    }

    @Override
    public boolean m_213695_(StructureTemplateManager p_227302_, WorldGenLevel p_227303_, StructureManager p_227304_, ChunkGenerator p_227305_, BlockPos p_227306_, BlockPos p_227307_, Rotation p_227308_, BoundingBox p_227309_, RandomSource p_227310_, boolean p_227311_) {
        StructurePlaceSettings $$11;
        StructureTemplate $$10 = this.m_227299_(p_227302_);
        if ($$10.m_230328_(p_227303_, p_227306_, p_227307_, $$11 = this.m_207169_(p_227308_, p_227309_, p_227311_), p_227310_, 18)) {
            List<StructureTemplate.StructureBlockInfo> $$12 = StructureTemplate.m_74517_(p_227303_, p_227306_, p_227307_, $$11, this.m_227324_(p_227302_, p_227306_, p_227308_, false));
            for (StructureTemplate.StructureBlockInfo $$13 : $$12) {
                this.m_227329_(p_227303_, $$13, p_227306_, p_227308_, p_227310_, p_227309_);
            }
            return true;
        }
        return false;
    }

    protected StructurePlaceSettings m_207169_(Rotation p_210421_, BoundingBox p_210422_, boolean p_210423_) {
        StructurePlaceSettings $$3 = new StructurePlaceSettings();
        $$3.m_74381_(p_210422_);
        $$3.m_74379_(p_210421_);
        $$3.m_74402_(true);
        $$3.m_74392_(false);
        $$3.m_74383_(BlockIgnoreProcessor.f_74046_);
        $$3.m_74405_(true);
        if (!p_210423_) {
            $$3.m_74383_(JigsawReplacementProcessor.f_74122_);
        }
        this.f_210412_.m_203334_().m_74425_().forEach($$3::m_74383_);
        this.m_210539_().m_210609_().forEach($$3::m_74383_);
        return $$3;
    }

    @Override
    public StructurePoolElementType<?> m_207234_() {
        return StructurePoolElementType.f_210542_;
    }

    public String toString() {
        return "Single[" + this.f_210411_ + "]";
    }
}

