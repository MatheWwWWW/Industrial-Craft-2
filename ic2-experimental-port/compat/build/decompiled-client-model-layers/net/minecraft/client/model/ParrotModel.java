/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Parrot;

public class ParrotModel
extends HierarchicalModel<Parrot> {
    private static final String f_170773_ = "feather";
    private final ModelPart f_170774_;
    private final ModelPart f_103184_;
    private final ModelPart f_103185_;
    private final ModelPart f_170775_;
    private final ModelPart f_170776_;
    private final ModelPart f_103188_;
    private final ModelPart f_103192_;
    private final ModelPart f_170777_;
    private final ModelPart f_170778_;

    public ParrotModel(ModelPart p_170780_) {
        this.f_170774_ = p_170780_;
        this.f_103184_ = p_170780_.m_171324_("body");
        this.f_103185_ = p_170780_.m_171324_("tail");
        this.f_170775_ = p_170780_.m_171324_("left_wing");
        this.f_170776_ = p_170780_.m_171324_("right_wing");
        this.f_103188_ = p_170780_.m_171324_("head");
        this.f_103192_ = this.f_103188_.m_171324_(f_170773_);
        this.f_170777_ = p_170780_.m_171324_("left_leg");
        this.f_170778_ = p_170780_.m_171324_("right_leg");
    }

    public static LayerDefinition m_170781_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        $$1.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(2, 8).m_171481_(-1.5f, 0.0f, -1.5f, 3.0f, 6.0f, 3.0f), PartPose.m_171419_(0.0f, 16.5f, -3.0f));
        $$1.m_171599_("tail", CubeListBuilder.m_171558_().m_171514_(22, 1).m_171481_(-1.5f, -1.0f, -1.0f, 3.0f, 4.0f, 1.0f), PartPose.m_171419_(0.0f, 21.07f, 1.16f));
        $$1.m_171599_("left_wing", CubeListBuilder.m_171558_().m_171514_(19, 8).m_171481_(-0.5f, 0.0f, -1.5f, 1.0f, 5.0f, 3.0f), PartPose.m_171419_(1.5f, 16.94f, -2.76f));
        $$1.m_171599_("right_wing", CubeListBuilder.m_171558_().m_171514_(19, 8).m_171481_(-0.5f, 0.0f, -1.5f, 1.0f, 5.0f, 3.0f), PartPose.m_171419_(-1.5f, 16.94f, -2.76f));
        PartDefinition $$2 = $$1.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(2, 2).m_171481_(-1.0f, -1.5f, -1.0f, 2.0f, 3.0f, 2.0f), PartPose.m_171419_(0.0f, 15.69f, -2.76f));
        $$2.m_171599_("head2", CubeListBuilder.m_171558_().m_171514_(10, 0).m_171481_(-1.0f, -0.5f, -2.0f, 2.0f, 1.0f, 4.0f), PartPose.m_171419_(0.0f, -2.0f, -1.0f));
        $$2.m_171599_("beak1", CubeListBuilder.m_171558_().m_171514_(11, 7).m_171481_(-0.5f, -1.0f, -0.5f, 1.0f, 2.0f, 1.0f), PartPose.m_171419_(0.0f, -0.5f, -1.5f));
        $$2.m_171599_("beak2", CubeListBuilder.m_171558_().m_171514_(16, 7).m_171481_(-0.5f, 0.0f, -0.5f, 1.0f, 2.0f, 1.0f), PartPose.m_171419_(0.0f, -1.75f, -2.45f));
        $$2.m_171599_(f_170773_, CubeListBuilder.m_171558_().m_171514_(2, 18).m_171481_(0.0f, -4.0f, -2.0f, 0.0f, 5.0f, 4.0f), PartPose.m_171419_(0.0f, -2.15f, 0.15f));
        CubeListBuilder $$3 = CubeListBuilder.m_171558_().m_171514_(14, 18).m_171481_(-0.5f, 0.0f, -0.5f, 1.0f, 2.0f, 1.0f);
        $$1.m_171599_("left_leg", $$3, PartPose.m_171419_(1.0f, 22.0f, -1.05f));
        $$1.m_171599_("right_leg", $$3, PartPose.m_171419_(-1.0f, 22.0f, -1.05f));
        return LayerDefinition.m_171565_($$0, 32, 32);
    }

    @Override
    public ModelPart m_142109_() {
        return this.f_170774_;
    }

    @Override
    public void m_6973_(Parrot p_103217_, float p_103218_, float p_103219_, float p_103220_, float p_103221_, float p_103222_) {
        this.m_103241_(ParrotModel.m_103209_(p_103217_), p_103217_.f_19797_, p_103218_, p_103219_, p_103220_, p_103221_, p_103222_);
    }

    @Override
    public void m_6839_(Parrot p_103212_, float p_103213_, float p_103214_, float p_103215_) {
        this.m_103239_(ParrotModel.m_103209_(p_103212_));
    }

    public void m_103223_(PoseStack p_103224_, VertexConsumer p_103225_, int p_103226_, int p_103227_, float p_103228_, float p_103229_, float p_103230_, float p_103231_, int p_103232_) {
        this.m_103239_(State.ON_SHOULDER);
        this.m_103241_(State.ON_SHOULDER, p_103232_, p_103228_, p_103229_, 0.0f, p_103230_, p_103231_);
        this.f_170774_.m_104301_(p_103224_, p_103225_, p_103226_, p_103227_);
    }

    private void m_103241_(State p_103242_, int p_103243_, float p_103244_, float p_103245_, float p_103246_, float p_103247_, float p_103248_) {
        this.f_103188_.f_104203_ = p_103248_ * ((float)Math.PI / 180);
        this.f_103188_.f_104204_ = p_103247_ * ((float)Math.PI / 180);
        this.f_103188_.f_104205_ = 0.0f;
        this.f_103188_.f_104200_ = 0.0f;
        this.f_103184_.f_104200_ = 0.0f;
        this.f_103185_.f_104200_ = 0.0f;
        this.f_170776_.f_104200_ = -1.5f;
        this.f_170775_.f_104200_ = 1.5f;
        switch (p_103242_) {
            case SITTING: {
                break;
            }
            case PARTY: {
                float $$7 = Mth.m_14089_(p_103243_);
                float $$8 = Mth.m_14031_(p_103243_);
                this.f_103188_.f_104200_ = $$7;
                this.f_103188_.f_104201_ = 15.69f + $$8;
                this.f_103188_.f_104203_ = 0.0f;
                this.f_103188_.f_104204_ = 0.0f;
                this.f_103188_.f_104205_ = Mth.m_14031_(p_103243_) * 0.4f;
                this.f_103184_.f_104200_ = $$7;
                this.f_103184_.f_104201_ = 16.5f + $$8;
                this.f_170775_.f_104205_ = -0.0873f - p_103246_;
                this.f_170775_.f_104200_ = 1.5f + $$7;
                this.f_170775_.f_104201_ = 16.94f + $$8;
                this.f_170776_.f_104205_ = 0.0873f + p_103246_;
                this.f_170776_.f_104200_ = -1.5f + $$7;
                this.f_170776_.f_104201_ = 16.94f + $$8;
                this.f_103185_.f_104200_ = $$7;
                this.f_103185_.f_104201_ = 21.07f + $$8;
                break;
            }
            case STANDING: {
                this.f_170777_.f_104203_ += Mth.m_14089_(p_103244_ * 0.6662f) * 1.4f * p_103245_;
                this.f_170778_.f_104203_ += Mth.m_14089_(p_103244_ * 0.6662f + (float)Math.PI) * 1.4f * p_103245_;
            }
            default: {
                float $$9 = p_103246_ * 0.3f;
                this.f_103188_.f_104201_ = 15.69f + $$9;
                this.f_103185_.f_104203_ = 1.015f + Mth.m_14089_(p_103244_ * 0.6662f) * 0.3f * p_103245_;
                this.f_103185_.f_104201_ = 21.07f + $$9;
                this.f_103184_.f_104201_ = 16.5f + $$9;
                this.f_170775_.f_104205_ = -0.0873f - p_103246_;
                this.f_170775_.f_104201_ = 16.94f + $$9;
                this.f_170776_.f_104205_ = 0.0873f + p_103246_;
                this.f_170776_.f_104201_ = 16.94f + $$9;
                this.f_170777_.f_104201_ = 22.0f + $$9;
                this.f_170778_.f_104201_ = 22.0f + $$9;
            }
        }
    }

    private void m_103239_(State p_103240_) {
        this.f_103192_.f_104203_ = -0.2214f;
        this.f_103184_.f_104203_ = 0.4937f;
        this.f_170775_.f_104203_ = -0.6981f;
        this.f_170775_.f_104204_ = (float)(-Math.PI);
        this.f_170776_.f_104203_ = -0.6981f;
        this.f_170776_.f_104204_ = (float)(-Math.PI);
        this.f_170777_.f_104203_ = -0.0299f;
        this.f_170778_.f_104203_ = -0.0299f;
        this.f_170777_.f_104201_ = 22.0f;
        this.f_170778_.f_104201_ = 22.0f;
        this.f_170777_.f_104205_ = 0.0f;
        this.f_170778_.f_104205_ = 0.0f;
        switch (p_103240_) {
            case FLYING: {
                this.f_170777_.f_104203_ += 0.6981317f;
                this.f_170778_.f_104203_ += 0.6981317f;
                break;
            }
            case SITTING: {
                float $$1 = 1.9f;
                this.f_103188_.f_104201_ = 17.59f;
                this.f_103185_.f_104203_ = 1.5388988f;
                this.f_103185_.f_104201_ = 22.97f;
                this.f_103184_.f_104201_ = 18.4f;
                this.f_170775_.f_104205_ = -0.0873f;
                this.f_170775_.f_104201_ = 18.84f;
                this.f_170776_.f_104205_ = 0.0873f;
                this.f_170776_.f_104201_ = 18.84f;
                this.f_170777_.f_104201_ += 1.9f;
                this.f_170778_.f_104201_ += 1.9f;
                this.f_170777_.f_104203_ += 1.5707964f;
                this.f_170778_.f_104203_ += 1.5707964f;
                break;
            }
            case PARTY: {
                this.f_170777_.f_104205_ = -0.34906584f;
                this.f_170778_.f_104205_ = 0.34906584f;
                break;
            }
        }
    }

    private static State m_103209_(Parrot p_103210_) {
        if (p_103210_.m_29439_()) {
            return State.PARTY;
        }
        if (p_103210_.m_21825_()) {
            return State.SITTING;
        }
        if (p_103210_.m_29443_()) {
            return State.FLYING;
        }
        return State.STANDING;
    }

    public static final class State
    extends Enum<State> {
        public static final /* enum */ State FLYING = new State();
        public static final /* enum */ State STANDING = new State();
        public static final /* enum */ State SITTING = new State();
        public static final /* enum */ State PARTY = new State();
        public static final /* enum */ State ON_SHOULDER = new State();
        private static final /* synthetic */ State[] $VALUES;

        public static State[] values() {
            return (State[])$VALUES.clone();
        }

        public static State valueOf(String p_103262_) {
            return Enum.valueOf(State.class, p_103262_);
        }

        private static /* synthetic */ State[] m_170783_() {
            return new State[]{FLYING, STANDING, SITTING, PARTY, ON_SHOULDER};
        }

        static {
            $VALUES = State.m_170783_();
        }
    }
}

