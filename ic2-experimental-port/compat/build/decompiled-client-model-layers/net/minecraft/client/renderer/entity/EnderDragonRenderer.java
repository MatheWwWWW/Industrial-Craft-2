/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import javax.annotation.Nullable;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EndCrystalRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;

public class EnderDragonRenderer
extends EntityRenderer<EnderDragon> {
    public static final ResourceLocation f_114174_ = new ResourceLocation("textures/entity/end_crystal/end_crystal_beam.png");
    private static final ResourceLocation f_114175_ = new ResourceLocation("textures/entity/enderdragon/dragon_exploding.png");
    private static final ResourceLocation f_114176_ = new ResourceLocation("textures/entity/enderdragon/dragon.png");
    private static final ResourceLocation f_114177_ = new ResourceLocation("textures/entity/enderdragon/dragon_eyes.png");
    private static final RenderType f_114178_ = RenderType.m_110458_(f_114176_);
    private static final RenderType f_114179_ = RenderType.m_110479_(f_114176_);
    private static final RenderType f_114180_ = RenderType.m_110488_(f_114177_);
    private static final RenderType f_114181_ = RenderType.m_110476_(f_114174_);
    private static final float f_114182_ = (float)(Math.sqrt(3.0) / 2.0);
    private final DragonModel f_114183_;

    public EnderDragonRenderer(EntityRendererProvider.Context p_173973_) {
        super(p_173973_);
        this.f_114477_ = 0.5f;
        this.f_114183_ = new DragonModel(p_173973_.m_174023_(ModelLayers.f_171144_));
    }

    @Override
    public void m_7392_(EnderDragon p_114208_, float p_114209_, float p_114210_, PoseStack p_114211_, MultiBufferSource p_114212_, int p_114213_) {
        p_114211_.m_85836_();
        float $$6 = (float)p_114208_.m_31101_(7, p_114210_)[0];
        float $$7 = (float)(p_114208_.m_31101_(5, p_114210_)[1] - p_114208_.m_31101_(10, p_114210_)[1]);
        p_114211_.m_85845_(Vector3f.f_122225_.m_122240_(-$$6));
        p_114211_.m_85845_(Vector3f.f_122223_.m_122240_($$7 * 10.0f));
        p_114211_.m_85837_(0.0, 0.0, 1.0);
        p_114211_.m_85841_(-1.0f, -1.0f, 1.0f);
        p_114211_.m_85837_(0.0, -1.501f, 0.0);
        boolean $$8 = p_114208_.f_20916_ > 0;
        this.f_114183_.m_6839_(p_114208_, 0.0f, 0.0f, p_114210_);
        if (p_114208_.f_31084_ > 0) {
            float $$9 = (float)p_114208_.f_31084_ / 200.0f;
            VertexConsumer $$10 = p_114212_.m_6299_(RenderType.m_173235_(f_114175_));
            this.f_114183_.m_7695_(p_114211_, $$10, p_114213_, OverlayTexture.f_118083_, 1.0f, 1.0f, 1.0f, $$9);
            VertexConsumer $$11 = p_114212_.m_6299_(f_114179_);
            this.f_114183_.m_7695_(p_114211_, $$11, p_114213_, OverlayTexture.m_118090_(0.0f, $$8), 1.0f, 1.0f, 1.0f, 1.0f);
        } else {
            VertexConsumer $$12 = p_114212_.m_6299_(f_114178_);
            this.f_114183_.m_7695_(p_114211_, $$12, p_114213_, OverlayTexture.m_118090_(0.0f, $$8), 1.0f, 1.0f, 1.0f, 1.0f);
        }
        VertexConsumer $$13 = p_114212_.m_6299_(f_114180_);
        this.f_114183_.m_7695_(p_114211_, $$13, p_114213_, OverlayTexture.f_118083_, 1.0f, 1.0f, 1.0f, 1.0f);
        if (p_114208_.f_31084_ > 0) {
            float $$14 = ((float)p_114208_.f_31084_ + p_114210_) / 200.0f;
            float $$15 = Math.min($$14 > 0.8f ? ($$14 - 0.8f) / 0.2f : 0.0f, 1.0f);
            RandomSource $$16 = RandomSource.m_216335_(432L);
            VertexConsumer $$17 = p_114212_.m_6299_(RenderType.m_110502_());
            p_114211_.m_85836_();
            p_114211_.m_85837_(0.0, -1.0, -2.0);
            int $$18 = 0;
            while ((float)$$18 < ($$14 + $$14 * $$14) / 2.0f * 60.0f) {
                p_114211_.m_85845_(Vector3f.f_122223_.m_122240_($$16.m_188501_() * 360.0f));
                p_114211_.m_85845_(Vector3f.f_122225_.m_122240_($$16.m_188501_() * 360.0f));
                p_114211_.m_85845_(Vector3f.f_122227_.m_122240_($$16.m_188501_() * 360.0f));
                p_114211_.m_85845_(Vector3f.f_122223_.m_122240_($$16.m_188501_() * 360.0f));
                p_114211_.m_85845_(Vector3f.f_122225_.m_122240_($$16.m_188501_() * 360.0f));
                p_114211_.m_85845_(Vector3f.f_122227_.m_122240_($$16.m_188501_() * 360.0f + $$14 * 90.0f));
                float $$19 = $$16.m_188501_() * 20.0f + 5.0f + $$15 * 10.0f;
                float $$20 = $$16.m_188501_() * 2.0f + 1.0f + $$15 * 2.0f;
                Matrix4f $$21 = p_114211_.m_85850_().m_85861_();
                int $$22 = (int)(255.0f * (1.0f - $$15));
                EnderDragonRenderer.m_114219_($$17, $$21, $$22);
                EnderDragonRenderer.m_114214_($$17, $$21, $$19, $$20);
                EnderDragonRenderer.m_114223_($$17, $$21, $$19, $$20);
                EnderDragonRenderer.m_114219_($$17, $$21, $$22);
                EnderDragonRenderer.m_114223_($$17, $$21, $$19, $$20);
                EnderDragonRenderer.m_114228_($$17, $$21, $$19, $$20);
                EnderDragonRenderer.m_114219_($$17, $$21, $$22);
                EnderDragonRenderer.m_114228_($$17, $$21, $$19, $$20);
                EnderDragonRenderer.m_114214_($$17, $$21, $$19, $$20);
                ++$$18;
            }
            p_114211_.m_85849_();
        }
        p_114211_.m_85849_();
        if (p_114208_.f_31086_ != null) {
            p_114211_.m_85836_();
            float $$23 = (float)(p_114208_.f_31086_.m_20185_() - Mth.m_14139_(p_114210_, p_114208_.f_19854_, p_114208_.m_20185_()));
            float $$24 = (float)(p_114208_.f_31086_.m_20186_() - Mth.m_14139_(p_114210_, p_114208_.f_19855_, p_114208_.m_20186_()));
            float $$25 = (float)(p_114208_.f_31086_.m_20189_() - Mth.m_14139_(p_114210_, p_114208_.f_19856_, p_114208_.m_20189_()));
            EnderDragonRenderer.m_114187_($$23, $$24 + EndCrystalRenderer.m_114158_(p_114208_.f_31086_, p_114210_), $$25, p_114210_, p_114208_.f_19797_, p_114211_, p_114212_, p_114213_);
            p_114211_.m_85849_();
        }
        super.m_7392_(p_114208_, p_114209_, p_114210_, p_114211_, p_114212_, p_114213_);
    }

    private static void m_114219_(VertexConsumer p_114220_, Matrix4f p_114221_, int p_114222_) {
        p_114220_.m_85982_(p_114221_, 0.0f, 0.0f, 0.0f).m_6122_(255, 255, 255, p_114222_).m_5752_();
    }

    private static void m_114214_(VertexConsumer p_114215_, Matrix4f p_114216_, float p_114217_, float p_114218_) {
        p_114215_.m_85982_(p_114216_, -f_114182_ * p_114218_, p_114217_, -0.5f * p_114218_).m_6122_(255, 0, 255, 0).m_5752_();
    }

    private static void m_114223_(VertexConsumer p_114224_, Matrix4f p_114225_, float p_114226_, float p_114227_) {
        p_114224_.m_85982_(p_114225_, f_114182_ * p_114227_, p_114226_, -0.5f * p_114227_).m_6122_(255, 0, 255, 0).m_5752_();
    }

    private static void m_114228_(VertexConsumer p_114229_, Matrix4f p_114230_, float p_114231_, float p_114232_) {
        p_114229_.m_85982_(p_114230_, 0.0f, p_114231_, 1.0f * p_114232_).m_6122_(255, 0, 255, 0).m_5752_();
    }

    public static void m_114187_(float p_114188_, float p_114189_, float p_114190_, float p_114191_, int p_114192_, PoseStack p_114193_, MultiBufferSource p_114194_, int p_114195_) {
        float $$8 = Mth.m_14116_(p_114188_ * p_114188_ + p_114190_ * p_114190_);
        float $$9 = Mth.m_14116_(p_114188_ * p_114188_ + p_114189_ * p_114189_ + p_114190_ * p_114190_);
        p_114193_.m_85836_();
        p_114193_.m_85837_(0.0, 2.0, 0.0);
        p_114193_.m_85845_(Vector3f.f_122225_.m_122270_((float)(-Math.atan2(p_114190_, p_114188_)) - 1.5707964f));
        p_114193_.m_85845_(Vector3f.f_122223_.m_122270_((float)(-Math.atan2($$8, p_114189_)) - 1.5707964f));
        VertexConsumer $$10 = p_114194_.m_6299_(f_114181_);
        float $$11 = 0.0f - ((float)p_114192_ + p_114191_) * 0.01f;
        float $$12 = Mth.m_14116_(p_114188_ * p_114188_ + p_114189_ * p_114189_ + p_114190_ * p_114190_) / 32.0f - ((float)p_114192_ + p_114191_) * 0.01f;
        int $$13 = 8;
        float $$14 = 0.0f;
        float $$15 = 0.75f;
        float $$16 = 0.0f;
        PoseStack.Pose $$17 = p_114193_.m_85850_();
        Matrix4f $$18 = $$17.m_85861_();
        Matrix3f $$19 = $$17.m_85864_();
        for (int $$20 = 1; $$20 <= 8; ++$$20) {
            float $$21 = Mth.m_14031_((float)$$20 * ((float)Math.PI * 2) / 8.0f) * 0.75f;
            float $$22 = Mth.m_14089_((float)$$20 * ((float)Math.PI * 2) / 8.0f) * 0.75f;
            float $$23 = (float)$$20 / 8.0f;
            $$10.m_85982_($$18, $$14 * 0.2f, $$15 * 0.2f, 0.0f).m_6122_(0, 0, 0, 255).m_7421_($$16, $$11).m_86008_(OverlayTexture.f_118083_).m_85969_(p_114195_).m_85977_($$19, 0.0f, -1.0f, 0.0f).m_5752_();
            $$10.m_85982_($$18, $$14, $$15, $$9).m_6122_(255, 255, 255, 255).m_7421_($$16, $$12).m_86008_(OverlayTexture.f_118083_).m_85969_(p_114195_).m_85977_($$19, 0.0f, -1.0f, 0.0f).m_5752_();
            $$10.m_85982_($$18, $$21, $$22, $$9).m_6122_(255, 255, 255, 255).m_7421_($$23, $$12).m_86008_(OverlayTexture.f_118083_).m_85969_(p_114195_).m_85977_($$19, 0.0f, -1.0f, 0.0f).m_5752_();
            $$10.m_85982_($$18, $$21 * 0.2f, $$22 * 0.2f, 0.0f).m_6122_(0, 0, 0, 255).m_7421_($$23, $$11).m_86008_(OverlayTexture.f_118083_).m_85969_(p_114195_).m_85977_($$19, 0.0f, -1.0f, 0.0f).m_5752_();
            $$14 = $$21;
            $$15 = $$22;
            $$16 = $$23;
        }
        p_114193_.m_85849_();
    }

    @Override
    public ResourceLocation m_5478_(EnderDragon p_114206_) {
        return f_114176_;
    }

    public static LayerDefinition m_173974_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        float $$2 = -16.0f;
        PartDefinition $$3 = $$1.m_171599_("head", CubeListBuilder.m_171558_().m_171534_("upperlip", -6.0f, -1.0f, -24.0f, 12, 5, 16, 176, 44).m_171534_("upperhead", -8.0f, -8.0f, -10.0f, 16, 16, 16, 112, 30).m_171480_().m_171534_("scale", -5.0f, -12.0f, -4.0f, 2, 4, 6, 0, 0).m_171534_("nostril", -5.0f, -3.0f, -22.0f, 2, 2, 4, 112, 0).m_171480_().m_171534_("scale", 3.0f, -12.0f, -4.0f, 2, 4, 6, 0, 0).m_171534_("nostril", 3.0f, -3.0f, -22.0f, 2, 2, 4, 112, 0), PartPose.f_171404_);
        $$3.m_171599_("jaw", CubeListBuilder.m_171558_().m_171534_("jaw", -6.0f, 0.0f, -16.0f, 12, 4, 16, 176, 65), PartPose.m_171419_(0.0f, 4.0f, -8.0f));
        $$1.m_171599_("neck", CubeListBuilder.m_171558_().m_171534_("box", -5.0f, -5.0f, -5.0f, 10, 10, 10, 192, 104).m_171534_("scale", -1.0f, -9.0f, -3.0f, 2, 4, 6, 48, 0), PartPose.f_171404_);
        $$1.m_171599_("body", CubeListBuilder.m_171558_().m_171534_("body", -12.0f, 0.0f, -16.0f, 24, 24, 64, 0, 0).m_171534_("scale", -1.0f, -6.0f, -10.0f, 2, 6, 12, 220, 53).m_171534_("scale", -1.0f, -6.0f, 10.0f, 2, 6, 12, 220, 53).m_171534_("scale", -1.0f, -6.0f, 30.0f, 2, 6, 12, 220, 53), PartPose.m_171419_(0.0f, 4.0f, 8.0f));
        PartDefinition $$4 = $$1.m_171599_("left_wing", CubeListBuilder.m_171558_().m_171480_().m_171534_("bone", 0.0f, -4.0f, -4.0f, 56, 8, 8, 112, 88).m_171534_("skin", 0.0f, 0.0f, 2.0f, 56, 0, 56, -56, 88), PartPose.m_171419_(12.0f, 5.0f, 2.0f));
        $$4.m_171599_("left_wing_tip", CubeListBuilder.m_171558_().m_171480_().m_171534_("bone", 0.0f, -2.0f, -2.0f, 56, 4, 4, 112, 136).m_171534_("skin", 0.0f, 0.0f, 2.0f, 56, 0, 56, -56, 144), PartPose.m_171419_(56.0f, 0.0f, 0.0f));
        PartDefinition $$5 = $$1.m_171599_("left_front_leg", CubeListBuilder.m_171558_().m_171534_("main", -4.0f, -4.0f, -4.0f, 8, 24, 8, 112, 104), PartPose.m_171419_(12.0f, 20.0f, 2.0f));
        PartDefinition $$6 = $$5.m_171599_("left_front_leg_tip", CubeListBuilder.m_171558_().m_171534_("main", -3.0f, -1.0f, -3.0f, 6, 24, 6, 226, 138), PartPose.m_171419_(0.0f, 20.0f, -1.0f));
        $$6.m_171599_("left_front_foot", CubeListBuilder.m_171558_().m_171534_("main", -4.0f, 0.0f, -12.0f, 8, 4, 16, 144, 104), PartPose.m_171419_(0.0f, 23.0f, 0.0f));
        PartDefinition $$7 = $$1.m_171599_("left_hind_leg", CubeListBuilder.m_171558_().m_171534_("main", -8.0f, -4.0f, -8.0f, 16, 32, 16, 0, 0), PartPose.m_171419_(16.0f, 16.0f, 42.0f));
        PartDefinition $$8 = $$7.m_171599_("left_hind_leg_tip", CubeListBuilder.m_171558_().m_171534_("main", -6.0f, -2.0f, 0.0f, 12, 32, 12, 196, 0), PartPose.m_171419_(0.0f, 32.0f, -4.0f));
        $$8.m_171599_("left_hind_foot", CubeListBuilder.m_171558_().m_171534_("main", -9.0f, 0.0f, -20.0f, 18, 6, 24, 112, 0), PartPose.m_171419_(0.0f, 31.0f, 4.0f));
        PartDefinition $$9 = $$1.m_171599_("right_wing", CubeListBuilder.m_171558_().m_171534_("bone", -56.0f, -4.0f, -4.0f, 56, 8, 8, 112, 88).m_171534_("skin", -56.0f, 0.0f, 2.0f, 56, 0, 56, -56, 88), PartPose.m_171419_(-12.0f, 5.0f, 2.0f));
        $$9.m_171599_("right_wing_tip", CubeListBuilder.m_171558_().m_171534_("bone", -56.0f, -2.0f, -2.0f, 56, 4, 4, 112, 136).m_171534_("skin", -56.0f, 0.0f, 2.0f, 56, 0, 56, -56, 144), PartPose.m_171419_(-56.0f, 0.0f, 0.0f));
        PartDefinition $$10 = $$1.m_171599_("right_front_leg", CubeListBuilder.m_171558_().m_171534_("main", -4.0f, -4.0f, -4.0f, 8, 24, 8, 112, 104), PartPose.m_171419_(-12.0f, 20.0f, 2.0f));
        PartDefinition $$11 = $$10.m_171599_("right_front_leg_tip", CubeListBuilder.m_171558_().m_171534_("main", -3.0f, -1.0f, -3.0f, 6, 24, 6, 226, 138), PartPose.m_171419_(0.0f, 20.0f, -1.0f));
        $$11.m_171599_("right_front_foot", CubeListBuilder.m_171558_().m_171534_("main", -4.0f, 0.0f, -12.0f, 8, 4, 16, 144, 104), PartPose.m_171419_(0.0f, 23.0f, 0.0f));
        PartDefinition $$12 = $$1.m_171599_("right_hind_leg", CubeListBuilder.m_171558_().m_171534_("main", -8.0f, -4.0f, -8.0f, 16, 32, 16, 0, 0), PartPose.m_171419_(-16.0f, 16.0f, 42.0f));
        PartDefinition $$13 = $$12.m_171599_("right_hind_leg_tip", CubeListBuilder.m_171558_().m_171534_("main", -6.0f, -2.0f, 0.0f, 12, 32, 12, 196, 0), PartPose.m_171419_(0.0f, 32.0f, -4.0f));
        $$13.m_171599_("right_hind_foot", CubeListBuilder.m_171558_().m_171534_("main", -9.0f, 0.0f, -20.0f, 18, 6, 24, 112, 0), PartPose.m_171419_(0.0f, 31.0f, 4.0f));
        return LayerDefinition.m_171565_($$0, 256, 256);
    }

    public static class DragonModel
    extends EntityModel<EnderDragon> {
        private final ModelPart f_114235_;
        private final ModelPart f_114236_;
        private final ModelPart f_114237_;
        private final ModelPart f_114238_;
        private final ModelPart f_114239_;
        private final ModelPart f_114240_;
        private final ModelPart f_114241_;
        private final ModelPart f_114242_;
        private final ModelPart f_114243_;
        private final ModelPart f_114244_;
        private final ModelPart f_114245_;
        private final ModelPart f_114246_;
        private final ModelPart f_114247_;
        private final ModelPart f_114248_;
        private final ModelPart f_114249_;
        private final ModelPart f_114250_;
        private final ModelPart f_114251_;
        private final ModelPart f_114252_;
        private final ModelPart f_114253_;
        private final ModelPart f_114254_;
        @Nullable
        private EnderDragon f_114233_;
        private float f_114234_;

        public DragonModel(ModelPart p_173976_) {
            this.f_114235_ = p_173976_.m_171324_("head");
            this.f_114237_ = this.f_114235_.m_171324_("jaw");
            this.f_114236_ = p_173976_.m_171324_("neck");
            this.f_114238_ = p_173976_.m_171324_("body");
            this.f_114239_ = p_173976_.m_171324_("left_wing");
            this.f_114240_ = this.f_114239_.m_171324_("left_wing_tip");
            this.f_114241_ = p_173976_.m_171324_("left_front_leg");
            this.f_114242_ = this.f_114241_.m_171324_("left_front_leg_tip");
            this.f_114243_ = this.f_114242_.m_171324_("left_front_foot");
            this.f_114244_ = p_173976_.m_171324_("left_hind_leg");
            this.f_114245_ = this.f_114244_.m_171324_("left_hind_leg_tip");
            this.f_114246_ = this.f_114245_.m_171324_("left_hind_foot");
            this.f_114247_ = p_173976_.m_171324_("right_wing");
            this.f_114248_ = this.f_114247_.m_171324_("right_wing_tip");
            this.f_114249_ = p_173976_.m_171324_("right_front_leg");
            this.f_114250_ = this.f_114249_.m_171324_("right_front_leg_tip");
            this.f_114251_ = this.f_114250_.m_171324_("right_front_foot");
            this.f_114252_ = p_173976_.m_171324_("right_hind_leg");
            this.f_114253_ = this.f_114252_.m_171324_("right_hind_leg_tip");
            this.f_114254_ = this.f_114253_.m_171324_("right_hind_foot");
        }

        @Override
        public void m_6839_(EnderDragon p_114269_, float p_114270_, float p_114271_, float p_114272_) {
            this.f_114233_ = p_114269_;
            this.f_114234_ = p_114272_;
        }

        @Override
        public void m_6973_(EnderDragon p_114274_, float p_114275_, float p_114276_, float p_114277_, float p_114278_, float p_114279_) {
        }

        @Override
        public void m_7695_(PoseStack p_114281_, VertexConsumer p_114282_, int p_114283_, int p_114284_, float p_114285_, float p_114286_, float p_114287_, float p_114288_) {
            p_114281_.m_85836_();
            float $$8 = Mth.m_14179_(this.f_114234_, this.f_114233_.f_31081_, this.f_114233_.f_31082_);
            this.f_114237_.f_104203_ = (float)(Math.sin($$8 * ((float)Math.PI * 2)) + 1.0) * 0.2f;
            float $$9 = (float)(Math.sin($$8 * ((float)Math.PI * 2) - 1.0f) + 1.0);
            $$9 = ($$9 * $$9 + $$9 * 2.0f) * 0.05f;
            p_114281_.m_85837_(0.0, $$9 - 2.0f, -3.0);
            p_114281_.m_85845_(Vector3f.f_122223_.m_122240_($$9 * 2.0f));
            float $$10 = 0.0f;
            float $$11 = 20.0f;
            float $$12 = -12.0f;
            float $$13 = 1.5f;
            double[] $$14 = this.f_114233_.m_31101_(6, this.f_114234_);
            float $$15 = Mth.m_14209_(this.f_114233_.m_31101_(5, this.f_114234_)[0] - this.f_114233_.m_31101_(10, this.f_114234_)[0]);
            float $$16 = Mth.m_14209_(this.f_114233_.m_31101_(5, this.f_114234_)[0] + (double)($$15 / 2.0f));
            float $$17 = $$8 * ((float)Math.PI * 2);
            for (int $$18 = 0; $$18 < 5; ++$$18) {
                double[] $$19 = this.f_114233_.m_31101_(5 - $$18, this.f_114234_);
                float $$20 = (float)Math.cos((float)$$18 * 0.45f + $$17) * 0.15f;
                this.f_114236_.f_104204_ = Mth.m_14209_($$19[0] - $$14[0]) * ((float)Math.PI / 180) * 1.5f;
                this.f_114236_.f_104203_ = $$20 + this.f_114233_.m_31108_($$18, $$14, $$19) * ((float)Math.PI / 180) * 1.5f * 5.0f;
                this.f_114236_.f_104205_ = -Mth.m_14209_($$19[0] - (double)$$16) * ((float)Math.PI / 180) * 1.5f;
                this.f_114236_.f_104201_ = $$11;
                this.f_114236_.f_104202_ = $$12;
                this.f_114236_.f_104200_ = $$10;
                $$11 += Mth.m_14031_(this.f_114236_.f_104203_) * 10.0f;
                $$12 -= Mth.m_14089_(this.f_114236_.f_104204_) * Mth.m_14089_(this.f_114236_.f_104203_) * 10.0f;
                $$10 -= Mth.m_14031_(this.f_114236_.f_104204_) * Mth.m_14089_(this.f_114236_.f_104203_) * 10.0f;
                this.f_114236_.m_104306_(p_114281_, p_114282_, p_114283_, p_114284_, 1.0f, 1.0f, 1.0f, p_114288_);
            }
            this.f_114235_.f_104201_ = $$11;
            this.f_114235_.f_104202_ = $$12;
            this.f_114235_.f_104200_ = $$10;
            double[] $$21 = this.f_114233_.m_31101_(0, this.f_114234_);
            this.f_114235_.f_104204_ = Mth.m_14209_($$21[0] - $$14[0]) * ((float)Math.PI / 180);
            this.f_114235_.f_104203_ = Mth.m_14209_(this.f_114233_.m_31108_(6, $$14, $$21)) * ((float)Math.PI / 180) * 1.5f * 5.0f;
            this.f_114235_.f_104205_ = -Mth.m_14209_($$21[0] - (double)$$16) * ((float)Math.PI / 180);
            this.f_114235_.m_104306_(p_114281_, p_114282_, p_114283_, p_114284_, 1.0f, 1.0f, 1.0f, p_114288_);
            p_114281_.m_85836_();
            p_114281_.m_85837_(0.0, 1.0, 0.0);
            p_114281_.m_85845_(Vector3f.f_122227_.m_122240_(-$$15 * 1.5f));
            p_114281_.m_85837_(0.0, -1.0, 0.0);
            this.f_114238_.f_104205_ = 0.0f;
            this.f_114238_.m_104306_(p_114281_, p_114282_, p_114283_, p_114284_, 1.0f, 1.0f, 1.0f, p_114288_);
            float $$22 = $$8 * ((float)Math.PI * 2);
            this.f_114239_.f_104203_ = 0.125f - (float)Math.cos($$22) * 0.2f;
            this.f_114239_.f_104204_ = -0.25f;
            this.f_114239_.f_104205_ = -((float)(Math.sin($$22) + 0.125)) * 0.8f;
            this.f_114240_.f_104205_ = (float)(Math.sin($$22 + 2.0f) + 0.5) * 0.75f;
            this.f_114247_.f_104203_ = this.f_114239_.f_104203_;
            this.f_114247_.f_104204_ = -this.f_114239_.f_104204_;
            this.f_114247_.f_104205_ = -this.f_114239_.f_104205_;
            this.f_114248_.f_104205_ = -this.f_114240_.f_104205_;
            this.m_173977_(p_114281_, p_114282_, p_114283_, p_114284_, $$9, this.f_114239_, this.f_114241_, this.f_114242_, this.f_114243_, this.f_114244_, this.f_114245_, this.f_114246_, p_114288_);
            this.m_173977_(p_114281_, p_114282_, p_114283_, p_114284_, $$9, this.f_114247_, this.f_114249_, this.f_114250_, this.f_114251_, this.f_114252_, this.f_114253_, this.f_114254_, p_114288_);
            p_114281_.m_85849_();
            float $$23 = -Mth.m_14031_($$8 * ((float)Math.PI * 2)) * 0.0f;
            $$17 = $$8 * ((float)Math.PI * 2);
            $$11 = 10.0f;
            $$12 = 60.0f;
            $$10 = 0.0f;
            $$14 = this.f_114233_.m_31101_(11, this.f_114234_);
            for (int $$24 = 0; $$24 < 12; ++$$24) {
                $$21 = this.f_114233_.m_31101_(12 + $$24, this.f_114234_);
                this.f_114236_.f_104204_ = (Mth.m_14209_($$21[0] - $$14[0]) * 1.5f + 180.0f) * ((float)Math.PI / 180);
                this.f_114236_.f_104203_ = ($$23 += Mth.m_14031_((float)$$24 * 0.45f + $$17) * 0.05f) + (float)($$21[1] - $$14[1]) * ((float)Math.PI / 180) * 1.5f * 5.0f;
                this.f_114236_.f_104205_ = Mth.m_14209_($$21[0] - (double)$$16) * ((float)Math.PI / 180) * 1.5f;
                this.f_114236_.f_104201_ = $$11;
                this.f_114236_.f_104202_ = $$12;
                this.f_114236_.f_104200_ = $$10;
                $$11 += Mth.m_14031_(this.f_114236_.f_104203_) * 10.0f;
                $$12 -= Mth.m_14089_(this.f_114236_.f_104204_) * Mth.m_14089_(this.f_114236_.f_104203_) * 10.0f;
                $$10 -= Mth.m_14031_(this.f_114236_.f_104204_) * Mth.m_14089_(this.f_114236_.f_104203_) * 10.0f;
                this.f_114236_.m_104306_(p_114281_, p_114282_, p_114283_, p_114284_, 1.0f, 1.0f, 1.0f, p_114288_);
            }
            p_114281_.m_85849_();
        }

        private void m_173977_(PoseStack p_173978_, VertexConsumer p_173979_, int p_173980_, int p_173981_, float p_173982_, ModelPart p_173983_, ModelPart p_173984_, ModelPart p_173985_, ModelPart p_173986_, ModelPart p_173987_, ModelPart p_173988_, ModelPart p_173989_, float p_173990_) {
            p_173987_.f_104203_ = 1.0f + p_173982_ * 0.1f;
            p_173988_.f_104203_ = 0.5f + p_173982_ * 0.1f;
            p_173989_.f_104203_ = 0.75f + p_173982_ * 0.1f;
            p_173984_.f_104203_ = 1.3f + p_173982_ * 0.1f;
            p_173985_.f_104203_ = -0.5f - p_173982_ * 0.1f;
            p_173986_.f_104203_ = 0.75f + p_173982_ * 0.1f;
            p_173983_.m_104306_(p_173978_, p_173979_, p_173980_, p_173981_, 1.0f, 1.0f, 1.0f, p_173990_);
            p_173984_.m_104306_(p_173978_, p_173979_, p_173980_, p_173981_, 1.0f, 1.0f, 1.0f, p_173990_);
            p_173987_.m_104306_(p_173978_, p_173979_, p_173980_, p_173981_, 1.0f, 1.0f, 1.0f, p_173990_);
        }
    }
}

