/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.math.Vector3f
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.block.BlockRenderDispatcher
 *  net.minecraft.client.renderer.entity.EntityRenderer
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  net.minecraft.client.renderer.entity.TntMinecartRenderer
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.inventory.InventoryMenu
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import ic2.core.entity.explosion.IC2ExplosiveEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.TntMinecartRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.state.BlockState;

public class IC2ExplosiveRenderer
extends EntityRenderer<IC2ExplosiveEntity> {
    public IC2ExplosiveRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    public void render(IC2ExplosiveEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        matrixStackIn.m_85836_();
        matrixStackIn.m_85837_(0.0, 0.5, 0.0);
        if ((float)entityIn.getFuseTime() - partialTicks + 1.0f < 10.0f) {
            float f = 1.0f - ((float)entityIn.getFuseTime() - partialTicks + 1.0f) / 10.0f;
            f = Mth.m_14036_((float)f, (float)0.0f, (float)1.0f);
            f *= f;
            f *= f;
            float f1 = 1.0f + f * 0.3f;
            matrixStackIn.m_85841_(f1, f1, f1);
        }
        matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(-90.0f));
        matrixStackIn.m_85837_(-0.5, -0.5, 0.5);
        matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(90.0f));
        TntMinecartRenderer.m_234661_((BlockRenderDispatcher)Minecraft.m_91087_().m_91289_(), (BlockState)entityIn.getState(), (PoseStack)matrixStackIn, (MultiBufferSource)bufferIn, (int)packedLightIn, (entityIn.getFuseTime() / 5 % 2 == 0 ? 1 : 0) != 0);
        matrixStackIn.m_85849_();
        super.m_7392_((Entity)entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    public ResourceLocation getTextureLocation(IC2ExplosiveEntity entity) {
        return InventoryMenu.f_39692_;
    }
}

