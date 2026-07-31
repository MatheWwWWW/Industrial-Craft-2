/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Ordering
 *  com.google.common.collect.Sets
 */
package net.minecraft.client.renderer.debug;

import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;

public class NeighborsUpdateRenderer
implements DebugRenderer.SimpleDebugRenderer {
    private final Minecraft f_113592_;
    private final Map<Long, Map<BlockPos, Integer>> f_113593_ = Maps.newTreeMap((Comparator)Ordering.natural().reverse());

    NeighborsUpdateRenderer(Minecraft p_113595_) {
        this.f_113592_ = p_113595_;
    }

    public void m_113596_(long p_113597_, BlockPos p_113598_) {
        Map $$2 = this.f_113593_.computeIfAbsent(p_113597_, p_113606_ -> Maps.newHashMap());
        int $$3 = $$2.getOrDefault(p_113598_, 0);
        $$2.put(p_113598_, $$3 + 1);
    }

    @Override
    public void m_7790_(PoseStack p_113600_, MultiBufferSource p_113601_, double p_113602_, double p_113603_, double p_113604_) {
        long $$5 = this.f_113592_.f_91073_.m_46467_();
        int $$6 = 200;
        double $$7 = 0.0025;
        HashSet $$8 = Sets.newHashSet();
        HashMap $$9 = Maps.newHashMap();
        VertexConsumer $$10 = p_113601_.m_6299_(RenderType.m_110504_());
        Iterator<Map.Entry<Long, Map<BlockPos, Integer>>> $$11 = this.f_113593_.entrySet().iterator();
        while ($$11.hasNext()) {
            Map.Entry<Long, Map<BlockPos, Integer>> $$12 = $$11.next();
            Long $$13 = $$12.getKey();
            Map<BlockPos, Integer> $$14 = $$12.getValue();
            long $$15 = $$5 - $$13;
            if ($$15 > 200L) {
                $$11.remove();
                continue;
            }
            for (Map.Entry<BlockPos, Integer> $$16 : $$14.entrySet()) {
                BlockPos $$17 = $$16.getKey();
                Integer $$18 = $$16.getValue();
                if (!$$8.add($$17)) continue;
                AABB $$19 = new AABB(BlockPos.f_121853_).m_82400_(0.002).m_82406_(0.0025 * (double)$$15).m_82386_($$17.m_123341_(), $$17.m_123342_(), $$17.m_123343_()).m_82386_(-p_113602_, -p_113603_, -p_113604_);
                LevelRenderer.m_109608_(p_113600_, $$10, $$19.f_82288_, $$19.f_82289_, $$19.f_82290_, $$19.f_82291_, $$19.f_82292_, $$19.f_82293_, 1.0f, 1.0f, 1.0f, 1.0f);
                $$9.put($$17, $$18);
            }
        }
        for (Map.Entry $$20 : $$9.entrySet()) {
            BlockPos $$21 = (BlockPos)$$20.getKey();
            Integer $$22 = (Integer)$$20.getValue();
            DebugRenderer.m_113500_(String.valueOf($$22), $$21.m_123341_(), $$21.m_123342_(), $$21.m_123343_(), -1);
        }
    }
}

