/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.block.BlockRenderDispatcher
 *  net.minecraft.client.renderer.entity.EntityRenderer
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  net.minecraft.client.renderer.texture.OverlayTexture
 *  net.minecraft.core.BlockPos
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.inventory.InventoryMenu
 *  net.minecraft.world.level.BlockAndTintGetter
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.client.model.data.ModelData
 */
package ic2.core.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.entity.rockets.MiningRocketEntity;
import ic2.core.platform.registries.IC2Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;

public class RocketMinerRenderer
extends EntityRenderer<MiningRocketEntity> {
    public RocketMinerRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    public ResourceLocation getTextureLocation(MiningRocketEntity entity) {
        return InventoryMenu.f_39692_;
    }

    public void render(MiningRocketEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        matrixStackIn.m_85836_();
        matrixStackIn.m_85837_(-0.5, 0.0, -0.5);
        BlockRenderDispatcher dispatcher = Minecraft.m_91087_().m_91289_();
        BlockState state = IC2Blocks.ROCKET_MINER.m_49966_();
        BlockPos pos = new BlockPos(entityIn.m_20185_(), entityIn.m_20191_().f_82292_, entityIn.m_20189_());
        dispatcher.m_110937_().tesselateBlock((BlockAndTintGetter)entityIn.f_19853_, dispatcher.m_110910_(state), state, pos, matrixStackIn, bufferIn.m_6299_(RenderType.m_110451_()), false, RandomSource.m_216327_(), state.m_60726_(entityIn.m_20183_()), OverlayTexture.f_118083_, ModelData.EMPTY, null);
        matrixStackIn.m_85849_();
    }
}

