/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;

public class BookModel
extends Model {
    private static final String f_170469_ = "left_pages";
    private static final String f_170470_ = "right_pages";
    private static final String f_170471_ = "flip_page1";
    private static final String f_170472_ = "flip_page2";
    private final ModelPart f_170473_;
    private final ModelPart f_102283_;
    private final ModelPart f_102284_;
    private final ModelPart f_102285_;
    private final ModelPart f_102286_;
    private final ModelPart f_102287_;
    private final ModelPart f_102288_;

    public BookModel(ModelPart p_170475_) {
        super(RenderType::m_110446_);
        this.f_170473_ = p_170475_;
        this.f_102283_ = p_170475_.m_171324_("left_lid");
        this.f_102284_ = p_170475_.m_171324_("right_lid");
        this.f_102285_ = p_170475_.m_171324_(f_170469_);
        this.f_102286_ = p_170475_.m_171324_(f_170470_);
        this.f_102287_ = p_170475_.m_171324_(f_170471_);
        this.f_102288_ = p_170475_.m_171324_(f_170472_);
    }

    public static LayerDefinition m_170476_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        $$1.m_171599_("left_lid", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-6.0f, -5.0f, -0.005f, 6.0f, 10.0f, 0.005f), PartPose.m_171419_(0.0f, 0.0f, -1.0f));
        $$1.m_171599_("right_lid", CubeListBuilder.m_171558_().m_171514_(16, 0).m_171481_(0.0f, -5.0f, -0.005f, 6.0f, 10.0f, 0.005f), PartPose.m_171419_(0.0f, 0.0f, 1.0f));
        $$1.m_171599_("seam", CubeListBuilder.m_171558_().m_171514_(12, 0).m_171481_(-1.0f, -5.0f, 0.0f, 2.0f, 10.0f, 0.005f), PartPose.m_171430_(0.0f, 1.5707964f, 0.0f));
        $$1.m_171599_(f_170469_, CubeListBuilder.m_171558_().m_171514_(0, 10).m_171481_(0.0f, -4.0f, -0.99f, 5.0f, 8.0f, 1.0f), PartPose.f_171404_);
        $$1.m_171599_(f_170470_, CubeListBuilder.m_171558_().m_171514_(12, 10).m_171481_(0.0f, -4.0f, -0.01f, 5.0f, 8.0f, 1.0f), PartPose.f_171404_);
        CubeListBuilder $$2 = CubeListBuilder.m_171558_().m_171514_(24, 10).m_171481_(0.0f, -4.0f, 0.0f, 5.0f, 8.0f, 0.005f);
        $$1.m_171599_(f_170471_, $$2, PartPose.f_171404_);
        $$1.m_171599_(f_170472_, $$2, PartPose.f_171404_);
        return LayerDefinition.m_171565_($$0, 64, 32);
    }

    @Override
    public void m_7695_(PoseStack p_102298_, VertexConsumer p_102299_, int p_102300_, int p_102301_, float p_102302_, float p_102303_, float p_102304_, float p_102305_) {
        this.m_102316_(p_102298_, p_102299_, p_102300_, p_102301_, p_102302_, p_102303_, p_102304_, p_102305_);
    }

    public void m_102316_(PoseStack p_102317_, VertexConsumer p_102318_, int p_102319_, int p_102320_, float p_102321_, float p_102322_, float p_102323_, float p_102324_) {
        this.f_170473_.m_104306_(p_102317_, p_102318_, p_102319_, p_102320_, p_102321_, p_102322_, p_102323_, p_102324_);
    }

    public void m_102292_(float p_102293_, float p_102294_, float p_102295_, float p_102296_) {
        float $$4 = (Mth.m_14031_(p_102293_ * 0.02f) * 0.1f + 1.25f) * p_102296_;
        this.f_102283_.f_104204_ = (float)Math.PI + $$4;
        this.f_102284_.f_104204_ = -$$4;
        this.f_102285_.f_104204_ = $$4;
        this.f_102286_.f_104204_ = -$$4;
        this.f_102287_.f_104204_ = $$4 - $$4 * 2.0f * p_102294_;
        this.f_102288_.f_104204_ = $$4 - $$4 * 2.0f * p_102295_;
        this.f_102285_.f_104200_ = Mth.m_14031_($$4);
        this.f_102286_.f_104200_ = Mth.m_14031_($$4);
        this.f_102287_.f_104200_ = Mth.m_14031_($$4);
        this.f_102288_.f_104200_ = Mth.m_14031_($$4);
    }
}

