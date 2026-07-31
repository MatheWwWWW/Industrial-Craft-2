/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Maps
 *  it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap
 */
package net.minecraft.client.model.geom.builders;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDefinition;
import net.minecraft.client.model.geom.builders.CubeListBuilder;

public class PartDefinition {
    private final List<CubeDefinition> f_171577_;
    private final PartPose f_171578_;
    private final Map<String, PartDefinition> f_171579_ = Maps.newHashMap();

    PartDefinition(List<CubeDefinition> p_171581_, PartPose p_171582_) {
        this.f_171577_ = p_171581_;
        this.f_171578_ = p_171582_;
    }

    public PartDefinition m_171599_(String p_171600_, CubeListBuilder p_171601_, PartPose p_171602_) {
        PartDefinition $$3 = new PartDefinition(p_171601_.m_171557_(), p_171602_);
        PartDefinition $$4 = this.f_171579_.put(p_171600_, $$3);
        if ($$4 != null) {
            $$3.f_171579_.putAll($$4.f_171579_);
        }
        return $$3;
    }

    public ModelPart m_171583_(int p_171584_, int p_171585_) {
        Object2ObjectArrayMap $$2 = this.f_171579_.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, p_171593_ -> ((PartDefinition)p_171593_.getValue()).m_171583_(p_171584_, p_171585_), (p_171595_, p_171596_) -> p_171595_, Object2ObjectArrayMap::new));
        List $$3 = (List)this.f_171577_.stream().map(p_171589_ -> p_171589_.m_171455_(p_171584_, p_171585_)).collect(ImmutableList.toImmutableList());
        ModelPart $$4 = new ModelPart($$3, (Map<String, ModelPart>)$$2);
        $$4.m_233560_(this.f_171578_);
        $$4.m_171322_(this.f_171578_);
        return $$4;
    }

    public PartDefinition m_171597_(String p_171598_) {
        return this.f_171579_.get(p_171598_);
    }
}

