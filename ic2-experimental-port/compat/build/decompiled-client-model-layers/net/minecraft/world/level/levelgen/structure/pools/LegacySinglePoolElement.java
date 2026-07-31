/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.datafixers.util.Either
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen.structure.pools;

import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class LegacySinglePoolElement
extends SinglePoolElement {
    public static final Codec<LegacySinglePoolElement> f_210345_ = RecordCodecBuilder.create(p_210357_ -> p_210357_.group(LegacySinglePoolElement.m_210465_(), LegacySinglePoolElement.m_210462_(), LegacySinglePoolElement.m_210538_()).apply((Applicative)p_210357_, LegacySinglePoolElement::new));

    protected LegacySinglePoolElement(Either<ResourceLocation, StructureTemplate> p_210348_, Holder<StructureProcessorList> p_210349_, StructureTemplatePool.Projection p_210350_) {
        super(p_210348_, p_210349_, p_210350_);
    }

    @Override
    protected StructurePlaceSettings m_207169_(Rotation p_210353_, BoundingBox p_210354_, boolean p_210355_) {
        StructurePlaceSettings $$3 = super.m_207169_(p_210353_, p_210354_, p_210355_);
        $$3.m_74397_(BlockIgnoreProcessor.f_74046_);
        $$3.m_74383_(BlockIgnoreProcessor.f_74048_);
        return $$3;
    }

    @Override
    public StructurePoolElementType<?> m_207234_() {
        return StructurePoolElementType.f_210546_;
    }

    @Override
    public String toString() {
        return "LegacySingle[" + this.f_210411_ + "]";
    }
}

