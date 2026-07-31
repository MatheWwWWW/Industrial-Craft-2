/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BellBlockEntity;

public class BellRenderer
implements BlockEntityRenderer<BellBlockEntity> {
    public static final Material f_112227_ = new Material(TextureAtlas.f_118259_, new ResourceLocation("entity/bell/bell_body"));
    private static final String f_173552_ = "bell_body";
    private final ModelPart f_112228_;

    public BellRenderer(BlockEntityRendererProvider.Context p_173554_) {
        ModelPart $$1 = p_173554_.m_173582_(ModelLayers.f_171269_);
        this.f_112228_ = $$1.m_171324_(f_173552_);
    }

    public static LayerDefinition m_173555_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        PartDefinition $$2 = $$1.m_171599_(f_173552_, CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-3.0f, -6.0f, -3.0f, 6.0f, 7.0f, 6.0f), PartPose.m_171419_(8.0f, 12.0f, 8.0f));
        $$2.m_171599_("bell_base", CubeListBuilder.m_171558_().m_171514_(0, 13).m_171481_(4.0f, 4.0f, 4.0f, 8.0f, 2.0f, 8.0f), PartPose.m_171419_(-8.0f, -12.0f, -8.0f));
        return LayerDefinition.m_171565_($$0, 32, 32);
    }

    @Override
    public void m_6922_(BellBlockEntity p_112233_, float p_112234_, PoseStack p_112235_, MultiBufferSource p_112236_, int p_112237_, int p_112238_) {
        float $$6 = (float)p_112233_.f_58813_ + p_112234_;
        float $$7 = 0.0f;
        float $$8 = 0.0f;
        if (p_112233_.f_58814_) {
            float $$9 = Mth.m_14031_($$6 / (float)Math.PI) / (4.0f + $$6 / 3.0f);
            if (p_112233_.f_58815_ == Direction.NORTH) {
                $$7 = -$$9;
            } else if (p_112233_.f_58815_ == Direction.SOUTH) {
                $$7 = $$9;
            } else if (p_112233_.f_58815_ == Direction.EAST) {
                $$8 = -$$9;
            } else if (p_112233_.f_58815_ == Direction.WEST) {
                $$8 = $$9;
            }
        }
        this.f_112228_.f_104203_ = $$7;
        this.f_112228_.f_104205_ = $$8;
        VertexConsumer $$10 = f_112227_.m_119194_(p_112236_, RenderType::m_110446_);
        this.f_112228_.m_104301_(p_112235_, $$10, p_112237_, p_112238_);
    }
}

