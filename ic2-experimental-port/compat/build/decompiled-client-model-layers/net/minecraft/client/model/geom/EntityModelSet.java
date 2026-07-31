/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 */
package net.minecraft.client.model.geom;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;

public class EntityModelSet
implements ResourceManagerReloadListener {
    private Map<ModelLayerLocation, LayerDefinition> f_171099_ = ImmutableMap.of();

    public ModelPart m_171103_(ModelLayerLocation p_171104_) {
        LayerDefinition $$1 = this.f_171099_.get(p_171104_);
        if ($$1 == null) {
            throw new IllegalArgumentException("No model for layer " + p_171104_);
        }
        return $$1.m_171564_();
    }

    @Override
    public void m_6213_(ResourceManager p_171102_) {
        this.f_171099_ = ImmutableMap.copyOf(LayerDefinitions.m_171110_());
    }
}

