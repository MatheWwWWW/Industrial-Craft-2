/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  com.mojang.math.Matrix4f
 *  net.minecraft.client.renderer.LevelRenderer
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.blockentity.BlockEntityRenderer
 *  net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider$Context
 *  net.minecraft.client.renderer.texture.OverlayTexture
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.level.BlockAndTintGetter
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.fluids.FluidStack
 */
package ic2.core.block.rendering.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import ic2.core.IC2;
import ic2.core.block.machines.tiles.nv.TankExpansionTileEntity;
import ic2.core.fluid.IC2Tank;
import ic2.core.platform.rendering.features.block.IBlockModel;
import ic2.core.platform.rendering.misc.IC2RenderTypes;
import ic2.core.utils.helpers.FluidHelper;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;

public class ExpansionRenderer
implements BlockEntityRenderer<TankExpansionTileEntity> {
    public ExpansionRenderer(BlockEntityRendererProvider.Context context) {
    }

    public void render(TankExpansionTileEntity tank, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        if (!IC2.CONFIG.fancyFluidExpansion.get()) {
            return;
        }
        int lightValue = LevelRenderer.m_109541_((BlockAndTintGetter)tank.m_58904_(), (BlockPos)tank.m_58899_().m_7494_());
        VertexConsumer builder = bufferIn.m_6299_(IC2RenderTypes.FLUID_RENDER);
        BlockState state = tank.m_58900_();
        this.renderCross(builder, ((IBlockModel)state.m_60734_()).getSpriteForState(state, Direction.DOWN), lightValue, matrixStackIn);
        for (int m = 0; m < 4; ++m) {
            IC2Tank subTank = tank.getTank(m);
            this.render(builder, subTank.getFluid(), subTank.getCapacity(), Direction.m_122407_((int)(m + tank.getFacing().m_122416_())), lightValue, matrixStackIn);
        }
    }

    protected void renderCross(VertexConsumer buffer, TextureAtlasSprite sprite, int light, PoseStack stack) {
        float min = 0.002f;
        float max = 0.998f;
        Matrix4f mat = stack.m_85850_().m_85861_();
        buffer.m_85982_(mat, min, min, min).m_6122_(255, 255, 255, 255).m_7421_(sprite.m_118409_(), sprite.m_118411_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
        buffer.m_85982_(mat, max, max, max).m_6122_(255, 255, 255, 255).m_7421_(sprite.m_118410_(), sprite.m_118412_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
        buffer.m_85982_(mat, min, max, min).m_6122_(255, 255, 255, 255).m_7421_(sprite.m_118410_(), sprite.m_118411_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
        buffer.m_85982_(mat, min, min, min).m_6122_(255, 255, 255, 255).m_7421_(sprite.m_118409_(), sprite.m_118411_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
        buffer.m_85982_(mat, max, min, max).m_6122_(255, 255, 255, 255).m_7421_(sprite.m_118409_(), sprite.m_118412_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
        buffer.m_85982_(mat, max, max, max).m_6122_(255, 255, 255, 255).m_7421_(sprite.m_118410_(), sprite.m_118412_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
        buffer.m_85982_(mat, min, min, min).m_6122_(255, 255, 255, 255).m_7421_(sprite.m_118409_(), sprite.m_118411_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
        buffer.m_85982_(mat, min, max, min).m_6122_(255, 255, 255, 255).m_7421_(sprite.m_118410_(), sprite.m_118411_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
        buffer.m_85982_(mat, max, max, max).m_6122_(255, 255, 255, 255).m_7421_(sprite.m_118410_(), sprite.m_118412_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
        buffer.m_85982_(mat, min, min, min).m_6122_(255, 255, 255, 255).m_7421_(sprite.m_118409_(), sprite.m_118411_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
        buffer.m_85982_(mat, max, max, max).m_6122_(255, 255, 255, 255).m_7421_(sprite.m_118410_(), sprite.m_118412_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
        buffer.m_85982_(mat, max, min, max).m_6122_(255, 255, 255, 255).m_7421_(sprite.m_118409_(), sprite.m_118412_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
        buffer.m_85982_(mat, min, min, max).m_6122_(255, 255, 255, 255).m_7421_(sprite.m_118409_(), sprite.m_118411_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
        buffer.m_85982_(mat, max, max, min).m_6122_(255, 255, 255, 255).m_7421_(sprite.m_118410_(), sprite.m_118412_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
        buffer.m_85982_(mat, max, min, min).m_6122_(255, 255, 255, 255).m_7421_(sprite.m_118410_(), sprite.m_118411_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
        buffer.m_85982_(mat, min, min, max).m_6122_(255, 255, 255, 255).m_7421_(sprite.m_118409_(), sprite.m_118411_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
        buffer.m_85982_(mat, min, max, max).m_6122_(255, 255, 255, 255).m_7421_(sprite.m_118409_(), sprite.m_118412_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
        buffer.m_85982_(mat, max, max, min).m_6122_(255, 255, 255, 255).m_7421_(sprite.m_118410_(), sprite.m_118412_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
        buffer.m_85982_(mat, min, min, max).m_6122_(255, 255, 255, 255).m_7421_(sprite.m_118409_(), sprite.m_118411_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
        buffer.m_85982_(mat, max, min, min).m_6122_(255, 255, 255, 255).m_7421_(sprite.m_118410_(), sprite.m_118411_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
        buffer.m_85982_(mat, max, max, min).m_6122_(255, 255, 255, 255).m_7421_(sprite.m_118410_(), sprite.m_118412_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
        buffer.m_85982_(mat, min, min, max).m_6122_(255, 255, 255, 255).m_7421_(sprite.m_118409_(), sprite.m_118411_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
        buffer.m_85982_(mat, max, max, min).m_6122_(255, 255, 255, 255).m_7421_(sprite.m_118410_(), sprite.m_118412_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
        buffer.m_85982_(mat, min, max, max).m_6122_(255, 255, 255, 255).m_7421_(sprite.m_118409_(), sprite.m_118412_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
    }

    protected void render(VertexConsumer buffer, FluidStack stack, int max, Direction dir, int light, PoseStack matrixStackIn) {
        if (stack.isEmpty()) {
            return;
        }
        float minY = 0.02f;
        float maxY = 0.02f + (float)stack.getAmount() / (float)max * 0.96f;
        TextureAtlasSprite sprite = FluidHelper.getTexture(stack, false);
        int color = FluidHelper.getFluidColor(stack);
        int alpha = color >> 24 & 0xFF;
        int red = color >> 16 & 0xFF;
        int green = color >> 8 & 0xFF;
        int blue = color & 0xFF;
        float minBounds = 0.001f;
        float maxBounds = 0.999f;
        Matrix4f mat = matrixStackIn.m_85850_().m_85861_();
        switch (dir) {
            case EAST: {
                buffer.m_85982_(mat, 0.5f, maxY, 0.5f).m_6122_(red, green, blue, alpha).m_7421_(sprite.m_118367_(8.0), sprite.m_118393_(8.0)).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
                buffer.m_85982_(mat, maxBounds, maxY, maxBounds).m_6122_(red, green, blue, alpha).m_7421_(sprite.m_118410_(), sprite.m_118412_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
                buffer.m_85982_(mat, maxBounds, maxY, minBounds).m_6122_(red, green, blue, alpha).m_7421_(sprite.m_118410_(), sprite.m_118411_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
                buffer.m_85982_(mat, maxBounds, minY, minBounds).m_6122_(red, green, blue, alpha).m_7421_(sprite.m_118409_(), sprite.m_118411_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
                buffer.m_85982_(mat, maxBounds, maxY, minBounds).m_6122_(red, green, blue, alpha).m_7421_(sprite.m_118410_(), sprite.m_118411_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
                buffer.m_85982_(mat, maxBounds, maxY, maxBounds).m_6122_(red, green, blue, alpha).m_7421_(sprite.m_118410_(), sprite.m_118412_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
                buffer.m_85982_(mat, maxBounds, minY, minBounds).m_6122_(red, green, blue, alpha).m_7421_(sprite.m_118409_(), sprite.m_118411_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
                buffer.m_85982_(mat, maxBounds, maxY, maxBounds).m_6122_(red, green, blue, alpha).m_7421_(sprite.m_118410_(), sprite.m_118412_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
                buffer.m_85982_(mat, maxBounds, minY, maxBounds).m_6122_(red, green, blue, alpha).m_7421_(sprite.m_118409_(), sprite.m_118412_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
                break;
            }
            case NORTH: {
                buffer.m_85982_(mat, minBounds, maxY, minBounds).m_6122_(red, green, blue, alpha).m_7421_(sprite.m_118409_(), sprite.m_118411_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
                buffer.m_85982_(mat, 0.5f, maxY, 0.5f).m_6122_(red, green, blue, alpha).m_7421_(sprite.m_118367_(8.0), sprite.m_118393_(8.0)).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
                buffer.m_85982_(mat, maxBounds, maxY, minBounds).m_6122_(red, green, blue, alpha).m_7421_(sprite.m_118410_(), sprite.m_118411_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
                buffer.m_85982_(mat, minBounds, minY, minBounds).m_6122_(red, green, blue, alpha).m_7421_(sprite.m_118409_(), sprite.m_118411_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
                buffer.m_85982_(mat, maxBounds, maxY, minBounds).m_6122_(red, green, blue, alpha).m_7421_(sprite.m_118410_(), sprite.m_118412_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
                buffer.m_85982_(mat, maxBounds, minY, minBounds).m_6122_(red, green, blue, alpha).m_7421_(sprite.m_118410_(), sprite.m_118411_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
                buffer.m_85982_(mat, minBounds, minY, minBounds).m_6122_(red, green, blue, alpha).m_7421_(sprite.m_118409_(), sprite.m_118411_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
                buffer.m_85982_(mat, minBounds, maxY, minBounds).m_6122_(red, green, blue, alpha).m_7421_(sprite.m_118409_(), sprite.m_118412_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
                buffer.m_85982_(mat, maxBounds, maxY, minBounds).m_6122_(red, green, blue, alpha).m_7421_(sprite.m_118410_(), sprite.m_118412_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
                break;
            }
            case SOUTH: {
                buffer.m_85982_(mat, 0.5f, maxY, 0.5f).m_6122_(red, green, blue, alpha).m_7421_(sprite.m_118367_(8.0), sprite.m_118393_(8.0)).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
                buffer.m_85982_(mat, minBounds, maxY, maxBounds).m_6122_(red, green, blue, alpha).m_7421_(sprite.m_118410_(), sprite.m_118412_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
                buffer.m_85982_(mat, maxBounds, maxY, maxBounds).m_6122_(red, green, blue, alpha).m_7421_(sprite.m_118410_(), sprite.m_118411_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
                buffer.m_85982_(mat, minBounds, minY, maxBounds).m_6122_(red, green, blue, alpha).m_7421_(sprite.m_118409_(), sprite.m_118411_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
                buffer.m_85982_(mat, maxBounds, minY, maxBounds).m_6122_(red, green, blue, alpha).m_7421_(sprite.m_118410_(), sprite.m_118411_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
                buffer.m_85982_(mat, maxBounds, maxY, maxBounds).m_6122_(red, green, blue, alpha).m_7421_(sprite.m_118410_(), sprite.m_118412_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
                buffer.m_85982_(mat, minBounds, minY, maxBounds).m_6122_(red, green, blue, alpha).m_7421_(sprite.m_118409_(), sprite.m_118411_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
                buffer.m_85982_(mat, maxBounds, maxY, maxBounds).m_6122_(red, green, blue, alpha).m_7421_(sprite.m_118410_(), sprite.m_118412_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
                buffer.m_85982_(mat, minBounds, maxY, maxBounds).m_6122_(red, green, blue, alpha).m_7421_(sprite.m_118409_(), sprite.m_118412_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
                break;
            }
            case WEST: {
                buffer.m_85982_(mat, minBounds, maxY, minBounds).m_6122_(red, green, blue, alpha).m_7421_(sprite.m_118409_(), sprite.m_118411_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
                buffer.m_85982_(mat, minBounds, maxY, maxBounds).m_6122_(red, green, blue, alpha).m_7421_(sprite.m_118409_(), sprite.m_118412_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
                buffer.m_85982_(mat, 0.5f, maxY, 0.5f).m_6122_(red, green, blue, alpha).m_7421_(sprite.m_118367_(8.0), sprite.m_118393_(8.0)).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
                buffer.m_85982_(mat, minBounds, minY, minBounds).m_6122_(red, green, blue, alpha).m_7421_(sprite.m_118409_(), sprite.m_118411_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
                buffer.m_85982_(mat, minBounds, maxY, maxBounds).m_6122_(red, green, blue, alpha).m_7421_(sprite.m_118410_(), sprite.m_118412_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
                buffer.m_85982_(mat, minBounds, maxY, minBounds).m_6122_(red, green, blue, alpha).m_7421_(sprite.m_118410_(), sprite.m_118411_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
                buffer.m_85982_(mat, minBounds, minY, minBounds).m_6122_(red, green, blue, alpha).m_7421_(sprite.m_118409_(), sprite.m_118411_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
                buffer.m_85982_(mat, minBounds, minY, maxBounds).m_6122_(red, green, blue, alpha).m_7421_(sprite.m_118409_(), sprite.m_118412_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
                buffer.m_85982_(mat, minBounds, maxY, maxBounds).m_6122_(red, green, blue, alpha).m_7421_(sprite.m_118410_(), sprite.m_118412_()).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
                break;
            }
        }
    }
}

