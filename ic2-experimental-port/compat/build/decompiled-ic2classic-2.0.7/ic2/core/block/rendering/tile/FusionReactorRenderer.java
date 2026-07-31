/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  com.mojang.math.Quaternion
 *  net.minecraft.client.renderer.LevelRenderer
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.block.BlockRenderDispatcher
 *  net.minecraft.client.renderer.blockentity.BlockEntityRenderer
 *  net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider$Context
 *  net.minecraft.client.renderer.texture.OverlayTexture
 *  net.minecraft.client.resources.model.BakedModel
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Vec3i
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.level.BlockAndTintGetter
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraftforge.client.model.data.ModelData
 *  net.minecraftforge.fluids.FluidStack
 */
package ic2.core.block.rendering.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import ic2.api.util.DirectionList;
import ic2.core.block.machines.tiles.luv.FusionReactorTileEntity;
import ic2.core.block.rendering.models.RodModel;
import ic2.core.platform.rendering.RenderShapes;
import ic2.core.platform.rendering.misc.IC2RenderTypes;
import ic2.core.platform.rendering.misc.UVHelper;
import ic2.core.utils.helpers.FluidHelper;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.fluids.FluidStack;

public class FusionReactorRenderer
implements BlockEntityRenderer<FusionReactorTileEntity> {
    private static final Quaternion INVERSE = new Quaternion(180.0f, 0.0f, 0.0f, true);
    private static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/models/tile/uranium_rod.png");
    RodModel model = new RodModel();
    BlockRenderDispatcher blockRenderer;

    public FusionReactorRenderer(BlockEntityRendererProvider.Context context) {
        this.blockRenderer = context.m_173584_();
    }

    public void render(FusionReactorTileEntity tileEntityIn, float partialTicks, PoseStack matrix, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        if (!tileEntityIn.isValid || tileEntityIn.getBox() == null) {
            return;
        }
        BlockPos pos = tileEntityIn.getBox().getCenter();
        BlockState state = tileEntityIn.renderInfo.getMeltingBlock();
        BlockPos offset = pos.m_121996_((Vec3i)tileEntityIn.m_58899_());
        matrix.m_85836_();
        if (tileEntityIn.storedFluid > 0) {
            VertexConsumer builder = bufferIn.m_6299_(IC2RenderTypes.FLUID_RENDER);
            int lightValue = LevelRenderer.m_109541_((BlockAndTintGetter)tileEntityIn.m_58904_(), (BlockPos)pos);
            float filled = (float)Math.min(tileEntityIn.storedFluid, 10000) / 40000.0f;
            FluidStack stack = new FluidStack((Fluid)Fluids.f_76195_, tileEntityIn.storedFluid);
            UVHelper notFlowing = UVHelper.fromSprite(FluidHelper.getTexture(stack, false));
            RenderShapes.renderColorTextureLightCube(tileEntityIn.getBox(filled), notFlowing, FluidHelper.getFluidColor(stack), OverlayTexture.f_118083_, lightValue, builder, matrix);
        }
        matrix.m_85837_((double)offset.m_123341_(), (double)offset.m_123342_(), (double)offset.m_123343_());
        if (!state.m_60795_()) {
            long seed = state.m_60726_(pos);
            BakedModel blockModel = this.blockRenderer.m_110910_(state);
            for (RenderType renderType : blockModel.getRenderTypes(state, RandomSource.m_216335_((long)seed), ModelData.EMPTY)) {
                VertexConsumer vertexConsumer = bufferIn.m_6299_(renderType);
                this.blockRenderer.m_110937_().tesselateBlock((BlockAndTintGetter)tileEntityIn.m_58904_(), blockModel, state, pos, matrix, vertexConsumer, false, RandomSource.m_216327_(), seed, combinedOverlayIn, ModelData.EMPTY, renderType);
            }
        }
        for (Direction dir : DirectionList.ALL) {
            if (!tileEntityIn.renderInfo.isVisible(dir)) continue;
            matrix.m_85836_();
            matrix.m_85837_(0.5, 0.5, 0.5);
            Quaternion quad = dir.m_122406_();
            quad.m_80148_(INVERSE);
            matrix.m_85845_(quad);
            this.model.render(matrix, bufferIn.m_6299_(RenderType.m_110452_((ResourceLocation)TEXTURE)), 0xF000F0, 655360, tileEntityIn.renderInfo.getColor(dir), tileEntityIn.renderInfo.getCount(dir));
            matrix.m_85849_();
        }
        matrix.m_85849_();
    }
}

