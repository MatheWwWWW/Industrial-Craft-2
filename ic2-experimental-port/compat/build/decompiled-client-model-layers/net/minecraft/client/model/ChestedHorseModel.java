/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.model;

import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;

public class ChestedHorseModel<T extends AbstractChestedHorse>
extends HorseModel<T> {
    private final ModelPart f_170479_;
    private final ModelPart f_170480_;

    public ChestedHorseModel(ModelPart p_170482_) {
        super(p_170482_);
        this.f_170479_ = this.f_102751_.m_171324_("left_chest");
        this.f_170480_ = this.f_102751_.m_171324_("right_chest");
    }

    public static LayerDefinition m_170483_() {
        MeshDefinition $$0 = HorseModel.m_170669_(CubeDeformation.f_171458_);
        PartDefinition $$1 = $$0.m_171576_();
        PartDefinition $$2 = $$1.m_171597_("body");
        CubeListBuilder $$3 = CubeListBuilder.m_171558_().m_171514_(26, 21).m_171481_(-4.0f, 0.0f, -2.0f, 8.0f, 8.0f, 3.0f);
        $$2.m_171599_("left_chest", $$3, PartPose.m_171423_(6.0f, -8.0f, 0.0f, 0.0f, -1.5707964f, 0.0f));
        $$2.m_171599_("right_chest", $$3, PartPose.m_171423_(-6.0f, -8.0f, 0.0f, 0.0f, 1.5707964f, 0.0f));
        PartDefinition $$4 = $$1.m_171597_("head_parts").m_171597_("head");
        CubeListBuilder $$5 = CubeListBuilder.m_171558_().m_171514_(0, 12).m_171481_(-1.0f, -7.0f, 0.0f, 2.0f, 7.0f, 1.0f);
        $$4.m_171599_("left_ear", $$5, PartPose.m_171423_(1.25f, -10.0f, 4.0f, 0.2617994f, 0.0f, 0.2617994f));
        $$4.m_171599_("right_ear", $$5, PartPose.m_171423_(-1.25f, -10.0f, 4.0f, 0.2617994f, 0.0f, -0.2617994f));
        return LayerDefinition.m_171565_($$0, 64, 64);
    }

    @Override
    public void m_6973_(T p_102366_, float p_102367_, float p_102368_, float p_102369_, float p_102370_, float p_102371_) {
        super.m_6973_(p_102366_, p_102367_, p_102368_, p_102369_, p_102370_, p_102371_);
        if (((AbstractChestedHorse)p_102366_).m_30502_()) {
            this.f_170479_.f_104207_ = true;
            this.f_170480_.f_104207_ = true;
        } else {
            this.f_170479_.f_104207_ = false;
            this.f_170480_.f_104207_ = false;
        }
    }
}

