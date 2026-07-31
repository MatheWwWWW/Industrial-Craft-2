/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen.structure.pools;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pools.EmptyPoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class ListPoolElement
extends StructurePoolElement {
    public static final Codec<ListPoolElement> f_210359_ = RecordCodecBuilder.create(p_210367_ -> p_210367_.group((App)StructurePoolElement.f_210468_.listOf().fieldOf("elements").forGetter(p_210369_ -> p_210369_.f_210360_), ListPoolElement.m_210538_()).apply((Applicative)p_210367_, ListPoolElement::new));
    private final List<StructurePoolElement> f_210360_;

    public ListPoolElement(List<StructurePoolElement> p_210363_, StructureTemplatePool.Projection p_210364_) {
        super(p_210364_);
        if (p_210363_.isEmpty()) {
            throw new IllegalArgumentException("Elements are empty");
        }
        this.f_210360_ = p_210363_;
        this.m_210406_(p_210364_);
    }

    @Override
    public Vec3i m_213577_(StructureTemplateManager p_227283_, Rotation p_227284_) {
        int $$2 = 0;
        int $$3 = 0;
        int $$4 = 0;
        for (StructurePoolElement $$5 : this.f_210360_) {
            Vec3i $$6 = $$5.m_213577_(p_227283_, p_227284_);
            $$2 = Math.max($$2, $$6.m_123341_());
            $$3 = Math.max($$3, $$6.m_123342_());
            $$4 = Math.max($$4, $$6.m_123343_());
        }
        return new Vec3i($$2, $$3, $$4);
    }

    @Override
    public List<StructureTemplate.StructureBlockInfo> m_213638_(StructureTemplateManager p_227290_, BlockPos p_227291_, Rotation p_227292_, RandomSource p_227293_) {
        return this.f_210360_.get(0).m_213638_(p_227290_, p_227291_, p_227292_, p_227293_);
    }

    @Override
    public BoundingBox m_214015_(StructureTemplateManager p_227286_, BlockPos p_227287_, Rotation p_227288_) {
        Stream<BoundingBox> $$3 = this.f_210360_.stream().filter(p_210371_ -> p_210371_ != EmptyPoolElement.f_210175_).map(p_227298_ -> p_227298_.m_214015_(p_227286_, p_227287_, p_227288_));
        return BoundingBox.m_162388_($$3::iterator).orElseThrow(() -> new IllegalStateException("Unable to calculate boundingbox for ListPoolElement"));
    }

    @Override
    public boolean m_213695_(StructureTemplateManager p_227272_, WorldGenLevel p_227273_, StructureManager p_227274_, ChunkGenerator p_227275_, BlockPos p_227276_, BlockPos p_227277_, Rotation p_227278_, BoundingBox p_227279_, RandomSource p_227280_, boolean p_227281_) {
        for (StructurePoolElement $$10 : this.f_210360_) {
            if ($$10.m_213695_(p_227272_, p_227273_, p_227274_, p_227275_, p_227276_, p_227277_, p_227278_, p_227279_, p_227280_, p_227281_)) continue;
            return false;
        }
        return true;
    }

    @Override
    public StructurePoolElementType<?> m_207234_() {
        return StructurePoolElementType.f_210543_;
    }

    @Override
    public StructurePoolElement m_207247_(StructureTemplatePool.Projection p_210373_) {
        super.m_207247_(p_210373_);
        this.m_210406_(p_210373_);
        return this;
    }

    public String toString() {
        return "List[" + this.f_210360_.stream().map(Object::toString).collect(Collectors.joining(", ")) + "]";
    }

    private void m_210406_(StructureTemplatePool.Projection p_210407_) {
        this.f_210360_.forEach(p_210376_ -> p_210376_.m_207247_(p_210407_));
    }
}

