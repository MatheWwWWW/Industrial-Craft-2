/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 *  org.apache.commons.lang3.mutable.MutableInt
 */
package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.FossilFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.apache.commons.lang3.mutable.MutableInt;

public class FossilFeature
extends Feature<FossilFeatureConfiguration> {
    public FossilFeature(Codec<FossilFeatureConfiguration> p_65851_) {
        super(p_65851_);
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<FossilFeatureConfiguration> p_159789_) {
        RandomSource $$1 = p_159789_.m_225041_();
        WorldGenLevel $$2 = p_159789_.m_159774_();
        BlockPos $$3 = p_159789_.m_159777_();
        Rotation $$4 = Rotation.m_221990_($$1);
        FossilFeatureConfiguration $$5 = p_159789_.m_159778_();
        int $$6 = $$1.m_188503_($$5.f_159797_.size());
        StructureTemplateManager $$7 = $$2.m_6018_().m_7654_().m_236738_();
        StructureTemplate $$8 = $$7.m_230359_($$5.f_159797_.get($$6));
        StructureTemplate $$9 = $$7.m_230359_($$5.f_159798_.get($$6));
        ChunkPos $$10 = new ChunkPos($$3);
        BoundingBox $$11 = new BoundingBox($$10.m_45604_() - 16, $$2.m_141937_(), $$10.m_45605_() - 16, $$10.m_45608_() + 16, $$2.m_151558_(), $$10.m_45609_() + 16);
        StructurePlaceSettings $$12 = new StructurePlaceSettings().m_74379_($$4).m_74381_($$11).m_230324_($$1);
        Vec3i $$13 = $$8.m_163808_($$4);
        BlockPos $$14 = $$3.m_7918_(-$$13.m_123341_() / 2, 0, -$$13.m_123343_() / 2);
        int $$15 = $$3.m_123342_();
        for (int $$16 = 0; $$16 < $$13.m_123341_(); ++$$16) {
            for (int $$17 = 0; $$17 < $$13.m_123343_(); ++$$17) {
                $$15 = Math.min($$15, $$2.m_6924_(Heightmap.Types.OCEAN_FLOOR_WG, $$14.m_123341_() + $$16, $$14.m_123343_() + $$17));
            }
        }
        int $$18 = Math.max($$15 - 15 - $$1.m_188503_(10), $$2.m_141937_() + 10);
        BlockPos $$19 = $$8.m_74583_($$14.m_175288_($$18), Mirror.NONE, $$4);
        if (FossilFeature.m_159781_($$2, $$8.m_74633_($$12, $$19)) > $$5.f_159801_) {
            return false;
        }
        $$12.m_74394_();
        $$5.f_159799_.m_203334_().m_74425_().forEach($$12::m_74383_);
        $$8.m_230328_($$2, $$19, $$19, $$12, $$1, 4);
        $$12.m_74394_();
        $$5.f_159800_.m_203334_().m_74425_().forEach($$12::m_74383_);
        $$9.m_230328_($$2, $$19, $$19, $$12, $$1, 4);
        return true;
    }

    private static int m_159781_(WorldGenLevel p_159782_, BoundingBox p_159783_) {
        MutableInt $$2 = new MutableInt(0);
        p_159783_.m_162380_(p_204749_ -> {
            BlockState $$3 = p_159782_.m_8055_((BlockPos)p_204749_);
            if ($$3.m_60795_() || $$3.m_60713_(Blocks.f_49991_) || $$3.m_60713_(Blocks.f_49990_)) {
                $$2.add(1);
            }
        });
        return $$2.getValue();
    }
}

