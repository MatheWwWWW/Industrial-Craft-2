/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package net.minecraft.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.frog.Tadpole;

public class TadpoleModel<T extends Tadpole>
extends AgeableListModel<T> {
    private final ModelPart f_233440_;
    private final ModelPart f_233441_;

    public TadpoleModel(ModelPart p_233443_) {
        super(true, 8.0f, 3.35f);
        this.f_233440_ = p_233443_;
        this.f_233441_ = p_233443_.m_171324_("tail");
    }

    public static LayerDefinition m_233460_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        float $$2 = 0.0f;
        float $$3 = 22.0f;
        float $$4 = -3.0f;
        $$1.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-1.5f, -1.0f, 0.0f, 3.0f, 2.0f, 3.0f), PartPose.m_171419_(0.0f, 22.0f, -3.0f));
        $$1.m_171599_("tail", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(0.0f, -1.0f, 0.0f, 0.0f, 2.0f, 7.0f), PartPose.m_171419_(0.0f, 22.0f, 0.0f));
        return LayerDefinition.m_171565_($$0, 16, 16);
    }

    @Override
    protected Iterable<ModelPart> m_5607_() {
        return ImmutableList.of((Object)this.f_233440_);
    }

    @Override
    protected Iterable<ModelPart> m_5608_() {
        return ImmutableList.of((Object)this.f_233441_);
    }

    @Override
    public void m_6973_(T p_233453_, float p_233454_, float p_233455_, float p_233456_, float p_233457_, float p_233458_) {
        float $$6 = ((Entity)p_233453_).m_20069_() ? 1.0f : 1.5f;
        this.f_233441_.f_104204_ = -$$6 * 0.25f * Mth.m_14031_(0.3f * p_233456_);
    }
}

