/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.model.geom.builders;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.MaterialDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;

public class LayerDefinition {
    private final MeshDefinition f_171559_;
    private final MaterialDefinition f_171560_;

    private LayerDefinition(MeshDefinition p_171562_, MaterialDefinition p_171563_) {
        this.f_171559_ = p_171562_;
        this.f_171560_ = p_171563_;
    }

    public ModelPart m_171564_() {
        return this.f_171559_.m_171576_().m_171583_(this.f_171560_.f_171569_, this.f_171560_.f_171570_);
    }

    public static LayerDefinition m_171565_(MeshDefinition p_171566_, int p_171567_, int p_171568_) {
        return new LayerDefinition(p_171566_, new MaterialDefinition(p_171567_, p_171568_));
    }
}

