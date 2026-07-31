/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 */
package net.minecraft.client.renderer.debug;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

public class StructureRenderer
implements DebugRenderer.SimpleDebugRenderer {
    private final Minecraft f_113675_;
    private final Map<DimensionType, Map<String, BoundingBox>> f_113676_ = Maps.newIdentityHashMap();
    private final Map<DimensionType, Map<String, BoundingBox>> f_113677_ = Maps.newIdentityHashMap();
    private final Map<DimensionType, Map<String, Boolean>> f_113678_ = Maps.newIdentityHashMap();
    private static final int f_173903_ = 500;

    public StructureRenderer(Minecraft p_113680_) {
        this.f_113675_ = p_113680_;
    }

    @Override
    public void m_7790_(PoseStack p_113688_, MultiBufferSource p_113689_, double p_113690_, double p_113691_, double p_113692_) {
        Camera $$5 = this.f_113675_.f_91063_.m_109153_();
        ClientLevel $$6 = this.f_113675_.f_91073_;
        DimensionType $$7 = $$6.m_6042_();
        BlockPos $$8 = new BlockPos($$5.m_90583_().f_82479_, 0.0, $$5.m_90583_().f_82481_);
        VertexConsumer $$9 = p_113689_.m_6299_(RenderType.m_110504_());
        if (this.f_113676_.containsKey($$7)) {
            for (BoundingBox boundingBox : this.f_113676_.get($$7).values()) {
                if (!$$8.m_123314_(boundingBox.m_162394_(), 500.0)) continue;
                LevelRenderer.m_109621_(p_113688_, $$9, (double)boundingBox.m_162395_() - p_113690_, (double)boundingBox.m_162396_() - p_113691_, (double)boundingBox.m_162398_() - p_113692_, (double)(boundingBox.m_162399_() + 1) - p_113690_, (double)(boundingBox.m_162400_() + 1) - p_113691_, (double)(boundingBox.m_162401_() + 1) - p_113692_, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f);
            }
        }
        if (this.f_113677_.containsKey($$7)) {
            for (Map.Entry entry : this.f_113677_.get($$7).entrySet()) {
                String $$12 = (String)entry.getKey();
                BoundingBox $$13 = (BoundingBox)entry.getValue();
                Boolean $$14 = this.f_113678_.get($$7).get($$12);
                if (!$$8.m_123314_($$13.m_162394_(), 500.0)) continue;
                if ($$14.booleanValue()) {
                    LevelRenderer.m_109621_(p_113688_, $$9, (double)$$13.m_162395_() - p_113690_, (double)$$13.m_162396_() - p_113691_, (double)$$13.m_162398_() - p_113692_, (double)($$13.m_162399_() + 1) - p_113690_, (double)($$13.m_162400_() + 1) - p_113691_, (double)($$13.m_162401_() + 1) - p_113692_, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f);
                    continue;
                }
                LevelRenderer.m_109621_(p_113688_, $$9, (double)$$13.m_162395_() - p_113690_, (double)$$13.m_162396_() - p_113691_, (double)$$13.m_162398_() - p_113692_, (double)($$13.m_162399_() + 1) - p_113690_, (double)($$13.m_162400_() + 1) - p_113691_, (double)($$13.m_162401_() + 1) - p_113692_, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f);
            }
        }
    }

    public void m_113682_(BoundingBox p_113683_, List<BoundingBox> p_113684_, List<Boolean> p_113685_, DimensionType p_113686_) {
        if (!this.f_113676_.containsKey(p_113686_)) {
            this.f_113676_.put(p_113686_, Maps.newHashMap());
        }
        if (!this.f_113677_.containsKey(p_113686_)) {
            this.f_113677_.put(p_113686_, Maps.newHashMap());
            this.f_113678_.put(p_113686_, Maps.newHashMap());
        }
        this.f_113676_.get(p_113686_).put(p_113683_.toString(), p_113683_);
        for (int $$4 = 0; $$4 < p_113684_.size(); ++$$4) {
            BoundingBox $$5 = p_113684_.get($$4);
            Boolean $$6 = p_113685_.get($$4);
            this.f_113677_.get(p_113686_).put($$5.toString(), $$5);
            this.f_113678_.get(p_113686_).put($$5.toString(), $$6);
        }
    }

    @Override
    public void m_5630_() {
        this.f_113676_.clear();
        this.f_113677_.clear();
        this.f_113678_.clear();
    }
}

