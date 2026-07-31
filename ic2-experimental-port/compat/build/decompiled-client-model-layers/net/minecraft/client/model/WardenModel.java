/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package net.minecraft.client.model;

import com.google.common.collect.ImmutableList;
import java.util.List;
import net.minecraft.client.animation.definitions.WardenAnimation;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.warden.Warden;

public class WardenModel<T extends Warden>
extends HierarchicalModel<T> {
    private static final float f_233504_ = 13.0f;
    private static final float f_233505_ = 1.0f;
    private final ModelPart f_233506_;
    protected final ModelPart f_233493_;
    protected final ModelPart f_233494_;
    protected final ModelPart f_233495_;
    protected final ModelPart f_233496_;
    protected final ModelPart f_233497_;
    protected final ModelPart f_233498_;
    protected final ModelPart f_233499_;
    protected final ModelPart f_233500_;
    protected final ModelPart f_233501_;
    protected final ModelPart f_233502_;
    protected final ModelPart f_233503_;
    private final List<ModelPart> f_233507_;
    private final List<ModelPart> f_233508_;
    private final List<ModelPart> f_233509_;
    private final List<ModelPart> f_233510_;

    public WardenModel(ModelPart p_233512_) {
        super(RenderType::m_110458_);
        this.f_233506_ = p_233512_;
        this.f_233493_ = p_233512_.m_171324_("bone");
        this.f_233494_ = this.f_233493_.m_171324_("body");
        this.f_233495_ = this.f_233494_.m_171324_("head");
        this.f_233502_ = this.f_233493_.m_171324_("right_leg");
        this.f_233498_ = this.f_233493_.m_171324_("left_leg");
        this.f_233501_ = this.f_233494_.m_171324_("right_arm");
        this.f_233499_ = this.f_233494_.m_171324_("left_arm");
        this.f_233496_ = this.f_233495_.m_171324_("right_tendril");
        this.f_233497_ = this.f_233495_.m_171324_("left_tendril");
        this.f_233503_ = this.f_233494_.m_171324_("right_ribcage");
        this.f_233500_ = this.f_233494_.m_171324_("left_ribcage");
        this.f_233507_ = ImmutableList.of((Object)this.f_233497_, (Object)this.f_233496_);
        this.f_233508_ = ImmutableList.of((Object)this.f_233494_);
        this.f_233509_ = ImmutableList.of((Object)this.f_233495_, (Object)this.f_233499_, (Object)this.f_233501_, (Object)this.f_233498_, (Object)this.f_233502_);
        this.f_233510_ = ImmutableList.of((Object)this.f_233494_, (Object)this.f_233495_, (Object)this.f_233499_, (Object)this.f_233501_, (Object)this.f_233498_, (Object)this.f_233502_);
    }

    public static LayerDefinition m_233537_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        PartDefinition $$2 = $$1.m_171599_("bone", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0f, 24.0f, 0.0f));
        PartDefinition $$3 = $$2.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-9.0f, -13.0f, -4.0f, 18.0f, 21.0f, 11.0f), PartPose.m_171419_(0.0f, -21.0f, 0.0f));
        $$3.m_171599_("right_ribcage", CubeListBuilder.m_171558_().m_171514_(90, 11).m_171481_(-2.0f, -11.0f, -0.1f, 9.0f, 21.0f, 0.0f), PartPose.m_171419_(-7.0f, -2.0f, -4.0f));
        $$3.m_171599_("left_ribcage", CubeListBuilder.m_171558_().m_171514_(90, 11).m_171480_().m_171481_(-7.0f, -11.0f, -0.1f, 9.0f, 21.0f, 0.0f).m_171555_(false), PartPose.m_171419_(7.0f, -2.0f, -4.0f));
        PartDefinition $$4 = $$3.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 32).m_171481_(-8.0f, -16.0f, -5.0f, 16.0f, 16.0f, 10.0f), PartPose.m_171419_(0.0f, -13.0f, 0.0f));
        $$4.m_171599_("right_tendril", CubeListBuilder.m_171558_().m_171514_(52, 32).m_171481_(-16.0f, -13.0f, 0.0f, 16.0f, 16.0f, 0.0f), PartPose.m_171419_(-8.0f, -12.0f, 0.0f));
        $$4.m_171599_("left_tendril", CubeListBuilder.m_171558_().m_171514_(58, 0).m_171481_(0.0f, -13.0f, 0.0f, 16.0f, 16.0f, 0.0f), PartPose.m_171419_(8.0f, -12.0f, 0.0f));
        $$3.m_171599_("right_arm", CubeListBuilder.m_171558_().m_171514_(44, 50).m_171481_(-4.0f, 0.0f, -4.0f, 8.0f, 28.0f, 8.0f), PartPose.m_171419_(-13.0f, -13.0f, 1.0f));
        $$3.m_171599_("left_arm", CubeListBuilder.m_171558_().m_171514_(0, 58).m_171481_(-4.0f, 0.0f, -4.0f, 8.0f, 28.0f, 8.0f), PartPose.m_171419_(13.0f, -13.0f, 1.0f));
        $$2.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(76, 48).m_171481_(-3.1f, 0.0f, -3.0f, 6.0f, 13.0f, 6.0f), PartPose.m_171419_(-5.9f, -13.0f, 0.0f));
        $$2.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(76, 76).m_171481_(-2.9f, 0.0f, -3.0f, 6.0f, 13.0f, 6.0f), PartPose.m_171419_(5.9f, -13.0f, 0.0f));
        return LayerDefinition.m_171565_($$0, 128, 128);
    }

    @Override
    public void m_6973_(T p_233531_, float p_233532_, float p_233533_, float p_233534_, float p_233535_, float p_233536_) {
        this.m_142109_().m_171331_().forEach(ModelPart::m_233569_);
        float $$6 = p_233534_ - (float)((Warden)p_233531_).f_19797_;
        this.m_233516_(p_233535_, p_233536_);
        this.m_233538_(p_233532_, p_233533_);
        this.m_233514_(p_233534_);
        this.m_233526_(p_233531_, p_233534_, $$6);
        this.m_233381_(((Warden)p_233531_).f_219313_, WardenAnimation.f_232347_, p_233534_);
        this.m_233381_(((Warden)p_233531_).f_219314_, WardenAnimation.f_232348_, p_233534_);
        this.m_233381_(((Warden)p_233531_).f_219347_, WardenAnimation.f_232344_, p_233534_);
        this.m_233381_(((Warden)p_233531_).f_219346_, WardenAnimation.f_232343_, p_233534_);
        this.m_233381_(((Warden)p_233531_).f_219312_, WardenAnimation.f_232345_, p_233534_);
        this.m_233381_(((Warden)p_233531_).f_219316_, WardenAnimation.f_232346_, p_233534_);
    }

    private void m_233516_(float p_233517_, float p_233518_) {
        this.f_233495_.f_104203_ = p_233518_ * ((float)Math.PI / 180);
        this.f_233495_.f_104204_ = p_233517_ * ((float)Math.PI / 180);
    }

    private void m_233514_(float p_233515_) {
        float $$1 = p_233515_ * 0.1f;
        float $$2 = Mth.m_14089_($$1);
        float $$3 = Mth.m_14031_($$1);
        this.f_233495_.f_104205_ += 0.06f * $$2;
        this.f_233495_.f_104203_ += 0.06f * $$3;
        this.f_233494_.f_104205_ += 0.025f * $$3;
        this.f_233494_.f_104203_ += 0.025f * $$2;
    }

    private void m_233538_(float p_233539_, float p_233540_) {
        float $$2 = Math.min(0.5f, 3.0f * p_233540_);
        float $$3 = p_233539_ * 0.8662f;
        float $$4 = Mth.m_14089_($$3);
        float $$5 = Mth.m_14031_($$3);
        float $$6 = Math.min(0.35f, $$2);
        this.f_233495_.f_104205_ += 0.3f * $$5 * $$2;
        this.f_233495_.f_104203_ += 1.2f * Mth.m_14089_($$3 + 1.5707964f) * $$6;
        this.f_233494_.f_104205_ = 0.1f * $$5 * $$2;
        this.f_233494_.f_104203_ = 1.0f * $$4 * $$6;
        this.f_233498_.f_104203_ = 1.0f * $$4 * $$2;
        this.f_233502_.f_104203_ = 1.0f * Mth.m_14089_($$3 + (float)Math.PI) * $$2;
        this.f_233499_.f_104203_ = -(0.8f * $$4 * $$2);
        this.f_233499_.f_104205_ = 0.0f;
        this.f_233501_.f_104203_ = -(0.8f * $$5 * $$2);
        this.f_233501_.f_104205_ = 0.0f;
        this.m_233545_();
    }

    private void m_233545_() {
        this.f_233499_.f_104204_ = 0.0f;
        this.f_233499_.f_104202_ = 1.0f;
        this.f_233499_.f_104200_ = 13.0f;
        this.f_233499_.f_104201_ = -13.0f;
        this.f_233501_.f_104204_ = 0.0f;
        this.f_233501_.f_104202_ = 1.0f;
        this.f_233501_.f_104200_ = -13.0f;
        this.f_233501_.f_104201_ = -13.0f;
    }

    private void m_233526_(T p_233527_, float p_233528_, float p_233529_) {
        float $$3;
        this.f_233497_.f_104203_ = $$3 = ((Warden)p_233527_).m_219467_(p_233529_) * (float)(Math.cos((double)p_233528_ * 2.25) * Math.PI * (double)0.1f);
        this.f_233496_.f_104203_ = -$$3;
    }

    @Override
    public ModelPart m_142109_() {
        return this.f_233506_;
    }

    public List<ModelPart> m_233541_() {
        return this.f_233507_;
    }

    public List<ModelPart> m_233542_() {
        return this.f_233508_;
    }

    public List<ModelPart> m_233543_() {
        return this.f_233509_;
    }

    public List<ModelPart> m_233544_() {
        return this.f_233510_;
    }
}

