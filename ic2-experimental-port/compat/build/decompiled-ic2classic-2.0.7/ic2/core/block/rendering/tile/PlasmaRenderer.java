/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.blockentity.BlockEntityRenderer
 *  net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider$Context
 *  net.minecraft.client.renderer.texture.OverlayTexture
 *  net.minecraft.world.phys.AABB
 */
package ic2.core.block.rendering.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.block.machines.tiles.ev.PlasmafierTileEntity;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.RenderShapes;
import ic2.core.platform.rendering.misc.IC2RenderTypes;
import ic2.core.platform.rendering.misc.UVHelper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.phys.AABB;

public class PlasmaRenderer
implements BlockEntityRenderer<PlasmafierTileEntity> {
    public PlasmaRenderer(BlockEntityRendererProvider.Context context) {
    }

    public void render(PlasmafierTileEntity te, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        if (te.plasma <= 0) {
            return;
        }
        RenderShapes.renderColorTextureLightCube(new AABB(0.02, 0.02, 0.02, 0.98, 0.02 + (double)((float)te.plasma / 10000.0f * 0.96f), 0.98), UVHelper.fromSprite(IC2Textures.getMappedEntriesBlockIC2("misc").get("plasma")), -1, OverlayTexture.f_118083_, combinedLightIn, bufferIn.m_6299_(IC2RenderTypes.FLUID_RENDER), matrixStackIn);
    }
}

