/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Pair
 */
package net.minecraft.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Vector3f;
import java.util.List;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.BannerBlock;
import net.minecraft.world.level.block.WallBannerBlock;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.state.BlockState;

public class BannerRenderer
implements BlockEntityRenderer<BannerBlockEntity> {
    private static final int f_173515_ = 20;
    private static final int f_173516_ = 40;
    private static final int f_173517_ = 16;
    public static final String f_173514_ = "flag";
    private static final String f_173518_ = "pole";
    private static final String f_173519_ = "bar";
    private final ModelPart f_112045_;
    private final ModelPart f_112046_;
    private final ModelPart f_112047_;

    public BannerRenderer(BlockEntityRendererProvider.Context p_173521_) {
        ModelPart $$1 = p_173521_.m_173582_(ModelLayers.f_171264_);
        this.f_112045_ = $$1.m_171324_(f_173514_);
        this.f_112046_ = $$1.m_171324_(f_173518_);
        this.f_112047_ = $$1.m_171324_(f_173519_);
    }

    public static LayerDefinition m_173522_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        $$1.m_171599_(f_173514_, CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-10.0f, 0.0f, -2.0f, 20.0f, 40.0f, 1.0f), PartPose.f_171404_);
        $$1.m_171599_(f_173518_, CubeListBuilder.m_171558_().m_171514_(44, 0).m_171481_(-1.0f, -30.0f, -1.0f, 2.0f, 42.0f, 2.0f), PartPose.f_171404_);
        $$1.m_171599_(f_173519_, CubeListBuilder.m_171558_().m_171514_(0, 42).m_171481_(-10.0f, -32.0f, -1.0f, 20.0f, 2.0f, 2.0f), PartPose.f_171404_);
        return LayerDefinition.m_171565_($$0, 64, 64);
    }

    @Override
    public void m_6922_(BannerBlockEntity p_112052_, float p_112053_, PoseStack p_112054_, MultiBufferSource p_112055_, int p_112056_, int p_112057_) {
        long $$10;
        List<Pair<Holder<BannerPattern>, DyeColor>> $$6 = p_112052_.m_58508_();
        float $$7 = 0.6666667f;
        boolean $$8 = p_112052_.m_58904_() == null;
        p_112054_.m_85836_();
        if ($$8) {
            long $$9 = 0L;
            p_112054_.m_85837_(0.5, 0.5, 0.5);
            this.f_112046_.f_104207_ = true;
        } else {
            $$10 = p_112052_.m_58904_().m_46467_();
            BlockState $$11 = p_112052_.m_58900_();
            if ($$11.m_60734_() instanceof BannerBlock) {
                p_112054_.m_85837_(0.5, 0.5, 0.5);
                float $$12 = (float)(-$$11.m_61143_(BannerBlock.f_49007_).intValue() * 360) / 16.0f;
                p_112054_.m_85845_(Vector3f.f_122225_.m_122240_($$12));
                this.f_112046_.f_104207_ = true;
            } else {
                p_112054_.m_85837_(0.5, -0.1666666716337204, 0.5);
                float $$13 = -$$11.m_61143_(WallBannerBlock.f_57916_).m_122435_();
                p_112054_.m_85845_(Vector3f.f_122225_.m_122240_($$13));
                p_112054_.m_85837_(0.0, -0.3125, -0.4375);
                this.f_112046_.f_104207_ = false;
            }
        }
        p_112054_.m_85836_();
        p_112054_.m_85841_(0.6666667f, -0.6666667f, -0.6666667f);
        VertexConsumer $$14 = ModelBakery.f_119224_.m_119194_(p_112055_, RenderType::m_110446_);
        this.f_112046_.m_104301_(p_112054_, $$14, p_112056_, p_112057_);
        this.f_112047_.m_104301_(p_112054_, $$14, p_112056_, p_112057_);
        BlockPos $$15 = p_112052_.m_58899_();
        float $$16 = ((float)Math.floorMod((long)($$15.m_123341_() * 7 + $$15.m_123342_() * 9 + $$15.m_123343_() * 13) + $$10, 100L) + p_112053_) / 100.0f;
        this.f_112045_.f_104203_ = (-0.0125f + 0.01f * Mth.m_14089_((float)Math.PI * 2 * $$16)) * (float)Math.PI;
        this.f_112045_.f_104201_ = -32.0f;
        BannerRenderer.m_112065_(p_112054_, p_112055_, p_112056_, p_112057_, this.f_112045_, ModelBakery.f_119224_, true, $$6);
        p_112054_.m_85849_();
        p_112054_.m_85849_();
    }

    public static void m_112065_(PoseStack p_112066_, MultiBufferSource p_112067_, int p_112068_, int p_112069_, ModelPart p_112070_, Material p_112071_, boolean p_112072_, List<Pair<Holder<BannerPattern>, DyeColor>> p_112073_) {
        BannerRenderer.m_112074_(p_112066_, p_112067_, p_112068_, p_112069_, p_112070_, p_112071_, p_112072_, p_112073_, false);
    }

    public static void m_112074_(PoseStack p_112075_, MultiBufferSource p_112076_, int p_112077_, int p_112078_, ModelPart p_112079_, Material p_112080_, boolean p_112081_, List<Pair<Holder<BannerPattern>, DyeColor>> p_112082_, boolean p_112083_) {
        p_112079_.m_104301_(p_112075_, p_112080_.m_119197_(p_112076_, RenderType::m_110446_, p_112083_), p_112077_, p_112078_);
        for (int $$9 = 0; $$9 < 17 && $$9 < p_112082_.size(); ++$$9) {
            Pair<Holder<BannerPattern>, DyeColor> $$10 = p_112082_.get($$9);
            float[] $$11 = ((DyeColor)$$10.getSecond()).m_41068_();
            ((Holder)$$10.getFirst()).m_203543_().map(p_234428_ -> p_112081_ ? Sheets.m_234347_(p_234428_) : Sheets.m_234349_(p_234428_)).ifPresent(p_234425_ -> p_112079_.m_104306_(p_112075_, p_234425_.m_119194_(p_112076_, RenderType::m_110482_), p_112077_, p_112078_, $$11[0], $$11[1], $$11[2], 1.0f));
        }
    }
}

