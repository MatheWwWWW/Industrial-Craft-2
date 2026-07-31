/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.math.Vector3f
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.block.model.ItemTransforms$TransformType
 *  net.minecraft.client.renderer.entity.EntityRenderer
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  net.minecraft.client.renderer.texture.OverlayTexture
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.inventory.InventoryMenu
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import ic2.core.entity.renderer.IRenderableItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;

public class ThrowItemRenderer
extends EntityRenderer<Entity> {
    public ThrowItemRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    public ResourceLocation m_5478_(Entity entity) {
        return InventoryMenu.f_39692_;
    }

    public void m_7392_(Entity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        if (entityIn instanceof IRenderableItem) {
            ItemStack stack = ((IRenderableItem)entityIn).getStack();
            matrixStackIn.m_85836_();
            matrixStackIn.m_85841_(0.5f, 0.5f, 0.5f);
            matrixStackIn.m_85845_(this.f_114476_.m_114470_());
            matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(180.0f));
            Minecraft.m_91087_().m_91291_().m_174269_(stack, ItemTransforms.TransformType.FIXED, packedLightIn, OverlayTexture.f_118083_, matrixStackIn, bufferIn, entityIn.m_19879_());
            matrixStackIn.m_85849_();
        }
        super.m_7392_(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }
}

