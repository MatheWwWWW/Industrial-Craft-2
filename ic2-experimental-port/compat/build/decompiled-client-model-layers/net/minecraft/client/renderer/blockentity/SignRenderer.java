/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 */
package net.minecraft.client.renderer.blockentity;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.Material;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.phys.Vec3;

public class SignRenderer
implements BlockEntityRenderer<SignBlockEntity> {
    public static final int f_173627_ = 90;
    private static final int f_173628_ = 10;
    private static final String f_173629_ = "stick";
    private static final int f_173630_ = -988212;
    private static final int f_173631_ = Mth.m_144944_(16);
    private final Map<WoodType, SignModel> f_173632_ = (Map)WoodType.m_61843_().collect(ImmutableMap.toImmutableMap(p_173645_ -> p_173645_, p_173651_ -> new SignModel(p_173636_.m_173582_(ModelLayers.m_171291_(p_173651_)))));
    private final Font f_173633_;

    public SignRenderer(BlockEntityRendererProvider.Context p_173636_) {
        this.f_173633_ = p_173636_.m_173586_();
    }

    @Override
    public void m_6922_(SignBlockEntity p_112497_, float p_112498_, PoseStack p_112499_, MultiBufferSource p_112500_, int p_112501_, int p_112502_) {
        int $$23;
        boolean $$22;
        int $$21;
        BlockState $$6 = p_112497_.m_58900_();
        p_112499_.m_85836_();
        float $$7 = 0.6666667f;
        WoodType $$8 = SignRenderer.m_173637_($$6.m_60734_());
        SignModel $$9 = this.f_173632_.get($$8);
        if ($$6.m_60734_() instanceof StandingSignBlock) {
            p_112499_.m_85837_(0.5, 0.5, 0.5);
            float $$10 = -((float)($$6.m_61143_(StandingSignBlock.f_56987_) * 360) / 16.0f);
            p_112499_.m_85845_(Vector3f.f_122225_.m_122240_($$10));
            $$9.f_112507_.f_104207_ = true;
        } else {
            p_112499_.m_85837_(0.5, 0.5, 0.5);
            float $$11 = -$$6.m_61143_(WallSignBlock.f_58064_).m_122435_();
            p_112499_.m_85845_(Vector3f.f_122225_.m_122240_($$11));
            p_112499_.m_85837_(0.0, -0.3125, -0.4375);
            $$9.f_112507_.f_104207_ = false;
        }
        p_112499_.m_85836_();
        p_112499_.m_85841_(0.6666667f, -0.6666667f, -0.6666667f);
        Material $$12 = Sheets.m_173381_($$8);
        VertexConsumer $$13 = $$12.m_119194_(p_112500_, $$9::m_103119_);
        $$9.f_173655_.m_104301_(p_112499_, $$13, p_112501_, p_112502_);
        p_112499_.m_85849_();
        float $$14 = 0.010416667f;
        p_112499_.m_85837_(0.0, 0.3333333432674408, 0.046666666865348816);
        p_112499_.m_85841_(0.010416667f, -0.010416667f, 0.010416667f);
        int $$15 = SignRenderer.m_173639_(p_112497_);
        int $$16 = 20;
        FormattedCharSequence[] $$17 = p_112497_.m_155717_(Minecraft.m_91087_().m_167974_(), p_173653_ -> {
            List<FormattedCharSequence> $$1 = this.f_173633_.m_92923_((FormattedText)p_173653_, 90);
            return $$1.isEmpty() ? FormattedCharSequence.f_13691_ : $$1.get(0);
        });
        if (p_112497_.m_155727_()) {
            int $$18 = p_112497_.m_59753_().m_41071_();
            boolean $$19 = SignRenderer.m_173641_(p_112497_, $$18);
            int $$20 = 0xF000F0;
        } else {
            $$21 = $$15;
            $$22 = false;
            $$23 = p_112501_;
        }
        for (int $$24 = 0; $$24 < 4; ++$$24) {
            FormattedCharSequence $$25 = $$17[$$24];
            float $$26 = -this.f_173633_.m_92724_($$25) / 2;
            if ($$22) {
                this.f_173633_.m_168645_($$25, $$26, $$24 * 10 - 20, $$21, $$15, p_112499_.m_85850_().m_85861_(), p_112500_, $$23);
                continue;
            }
            this.f_173633_.m_92733_($$25, $$26, $$24 * 10 - 20, $$21, false, p_112499_.m_85850_().m_85861_(), p_112500_, false, 0, $$23);
        }
        p_112499_.m_85849_();
    }

    private static boolean m_173641_(SignBlockEntity p_173642_, int p_173643_) {
        if (p_173643_ == DyeColor.BLACK.m_41071_()) {
            return true;
        }
        Minecraft $$2 = Minecraft.m_91087_();
        LocalPlayer $$3 = $$2.f_91074_;
        if ($$3 != null && $$2.f_91066_.m_92176_().m_90612_() && $$3.m_150108_()) {
            return true;
        }
        Entity $$4 = $$2.m_91288_();
        return $$4 != null && $$4.m_20238_(Vec3.m_82512_(p_173642_.m_58899_())) < (double)f_173631_;
    }

    private static int m_173639_(SignBlockEntity p_173640_) {
        int $$1 = p_173640_.m_59753_().m_41071_();
        double $$2 = 0.4;
        int $$3 = (int)((double)NativeImage.m_85085_($$1) * 0.4);
        int $$4 = (int)((double)NativeImage.m_85103_($$1) * 0.4);
        int $$5 = (int)((double)NativeImage.m_85119_($$1) * 0.4);
        if ($$1 == DyeColor.BLACK.m_41071_() && p_173640_.m_155727_()) {
            return -988212;
        }
        return NativeImage.m_84992_(0, $$5, $$4, $$3);
    }

    public static WoodType m_173637_(Block p_173638_) {
        WoodType $$2;
        if (p_173638_ instanceof SignBlock) {
            WoodType $$1 = ((SignBlock)p_173638_).m_56297_();
        } else {
            $$2 = WoodType.f_61830_;
        }
        return $$2;
    }

    public static SignModel m_173646_(EntityModelSet p_173647_, WoodType p_173648_) {
        return new SignModel(p_173647_.m_171103_(ModelLayers.m_171291_(p_173648_)));
    }

    public static LayerDefinition m_173654_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        $$1.m_171599_("sign", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-12.0f, -14.0f, -1.0f, 24.0f, 12.0f, 2.0f), PartPose.f_171404_);
        $$1.m_171599_(f_173629_, CubeListBuilder.m_171558_().m_171514_(0, 14).m_171481_(-1.0f, -2.0f, -1.0f, 2.0f, 14.0f, 2.0f), PartPose.f_171404_);
        return LayerDefinition.m_171565_($$0, 64, 32);
    }

    public static final class SignModel
    extends Model {
        public final ModelPart f_173655_;
        public final ModelPart f_112507_;

        public SignModel(ModelPart p_173657_) {
            super(RenderType::m_110458_);
            this.f_173655_ = p_173657_;
            this.f_112507_ = p_173657_.m_171324_(SignRenderer.f_173629_);
        }

        @Override
        public void m_7695_(PoseStack p_112510_, VertexConsumer p_112511_, int p_112512_, int p_112513_, float p_112514_, float p_112515_, float p_112516_, float p_112517_) {
            this.f_173655_.m_104306_(p_112510_, p_112511_, p_112512_, p_112513_, p_112514_, p_112515_, p_112516_, p_112517_);
        }
    }
}

