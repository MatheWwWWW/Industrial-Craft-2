/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.TheEndPortalBlockEntity;

public class TheEndPortalRenderer<T extends TheEndPortalBlockEntity>
implements BlockEntityRenderer<T> {
    public static final ResourceLocation f_112626_ = new ResourceLocation("textures/environment/end_sky.png");
    public static final ResourceLocation f_112627_ = new ResourceLocation("textures/entity/end_portal.png");

    public TheEndPortalRenderer(BlockEntityRendererProvider.Context p_173689_) {
    }

    @Override
    public void m_6922_(T p_112650_, float p_112651_, PoseStack p_112652_, MultiBufferSource p_112653_, int p_112654_, int p_112655_) {
        Matrix4f $$6 = p_112652_.m_85850_().m_85861_();
        this.m_173690_(p_112650_, $$6, p_112653_.m_6299_(this.m_142330_()));
    }

    private void m_173690_(T p_173691_, Matrix4f p_173692_, VertexConsumer p_173693_) {
        float $$3 = this.m_142489_();
        float $$4 = this.m_142491_();
        this.m_173694_(p_173691_, p_173692_, p_173693_, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, Direction.SOUTH);
        this.m_173694_(p_173691_, p_173692_, p_173693_, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, Direction.NORTH);
        this.m_173694_(p_173691_, p_173692_, p_173693_, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, Direction.EAST);
        this.m_173694_(p_173691_, p_173692_, p_173693_, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, Direction.WEST);
        this.m_173694_(p_173691_, p_173692_, p_173693_, 0.0f, 1.0f, $$3, $$3, 0.0f, 0.0f, 1.0f, 1.0f, Direction.DOWN);
        this.m_173694_(p_173691_, p_173692_, p_173693_, 0.0f, 1.0f, $$4, $$4, 1.0f, 1.0f, 0.0f, 0.0f, Direction.UP);
    }

    private void m_173694_(T p_173695_, Matrix4f p_173696_, VertexConsumer p_173697_, float p_173698_, float p_173699_, float p_173700_, float p_173701_, float p_173702_, float p_173703_, float p_173704_, float p_173705_, Direction p_173706_) {
        if (((TheEndPortalBlockEntity)p_173695_).m_6665_(p_173706_)) {
            p_173697_.m_85982_(p_173696_, p_173698_, p_173700_, p_173702_).m_5752_();
            p_173697_.m_85982_(p_173696_, p_173699_, p_173700_, p_173703_).m_5752_();
            p_173697_.m_85982_(p_173696_, p_173699_, p_173701_, p_173704_).m_5752_();
            p_173697_.m_85982_(p_173696_, p_173698_, p_173701_, p_173705_).m_5752_();
        }
    }

    protected float m_142491_() {
        return 0.75f;
    }

    protected float m_142489_() {
        return 0.375f;
    }

    protected RenderType m_142330_() {
        return RenderType.m_173239_();
    }
}

