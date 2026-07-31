/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen.structure.pools;

import com.google.common.collect.Lists;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.FrontAndTop;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.JigsawBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class FeaturePoolElement
extends StructurePoolElement {
    public static final Codec<FeaturePoolElement> f_210204_ = RecordCodecBuilder.create(p_210213_ -> p_210213_.group((App)PlacedFeature.f_191773_.fieldOf("feature").forGetter(p_210215_ -> p_210215_.f_210205_), FeaturePoolElement.m_210538_()).apply((Applicative)p_210213_, FeaturePoolElement::new));
    private final Holder<PlacedFeature> f_210205_;
    private final CompoundTag f_210206_;

    protected FeaturePoolElement(Holder<PlacedFeature> p_210209_, StructureTemplatePool.Projection p_210210_) {
        super(p_210210_);
        this.f_210205_ = p_210209_;
        this.f_210206_ = this.m_210239_();
    }

    private CompoundTag m_210239_() {
        CompoundTag $$0 = new CompoundTag();
        $$0.m_128359_("name", "minecraft:bottom");
        $$0.m_128359_("final_state", "minecraft:air");
        $$0.m_128359_("pool", "minecraft:empty");
        $$0.m_128359_("target", "minecraft:empty");
        $$0.m_128359_("joint", JigsawBlockEntity.JointType.ROLLABLE.m_7912_());
        return $$0;
    }

    @Override
    public Vec3i m_213577_(StructureTemplateManager p_227192_, Rotation p_227193_) {
        return Vec3i.f_123288_;
    }

    @Override
    public List<StructureTemplate.StructureBlockInfo> m_213638_(StructureTemplateManager p_227199_, BlockPos p_227200_, Rotation p_227201_, RandomSource p_227202_) {
        ArrayList $$4 = Lists.newArrayList();
        $$4.add(new StructureTemplate.StructureBlockInfo(p_227200_, (BlockState)Blocks.f_50678_.m_49966_().m_61124_(JigsawBlock.f_54222_, FrontAndTop.m_122622_(Direction.DOWN, Direction.SOUTH)), this.f_210206_));
        return $$4;
    }

    @Override
    public BoundingBox m_214015_(StructureTemplateManager p_227195_, BlockPos p_227196_, Rotation p_227197_) {
        Vec3i $$3 = this.m_213577_(p_227195_, p_227197_);
        return new BoundingBox(p_227196_.m_123341_(), p_227196_.m_123342_(), p_227196_.m_123343_(), p_227196_.m_123341_() + $$3.m_123341_(), p_227196_.m_123342_() + $$3.m_123342_(), p_227196_.m_123343_() + $$3.m_123343_());
    }

    @Override
    public boolean m_213695_(StructureTemplateManager p_227181_, WorldGenLevel p_227182_, StructureManager p_227183_, ChunkGenerator p_227184_, BlockPos p_227185_, BlockPos p_227186_, Rotation p_227187_, BoundingBox p_227188_, RandomSource p_227189_, boolean p_227190_) {
        return this.f_210205_.m_203334_().m_226357_(p_227182_, p_227184_, p_227189_, p_227185_);
    }

    @Override
    public StructurePoolElementType<?> m_207234_() {
        return StructurePoolElementType.f_210544_;
    }

    public String toString() {
        return "Feature[" + this.f_210205_ + "]";
    }
}

