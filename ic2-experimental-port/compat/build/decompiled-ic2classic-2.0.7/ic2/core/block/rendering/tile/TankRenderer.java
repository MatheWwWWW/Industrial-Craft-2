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
 *  net.minecraft.core.Vec3i
 *  net.minecraft.util.Mth
 *  net.minecraft.world.level.BlockAndTintGetter
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.common.Tags$Fluids
 *  net.minecraftforge.fluids.FluidStack
 */
package ic2.core.block.rendering.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import ic2.api.util.DirectionList;
import ic2.core.block.base.tiles.BaseLinkingTileEntity;
import ic2.core.block.storage.tiles.tank.TankTileEntity;
import ic2.core.block.storage.tiles.tank.ValveTileEntity;
import ic2.core.fluid.IC2Tank;
import ic2.core.platform.rendering.RenderShapes;
import ic2.core.platform.rendering.misc.IC2RenderTypes;
import ic2.core.platform.rendering.misc.UVHelper;
import ic2.core.utils.helpers.FluidHelper;
import ic2.core.utils.math.geometry.Box;
import java.util.function.BiConsumer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fluids.FluidStack;

public class TankRenderer
implements BlockEntityRenderer<TankTileEntity> {
    public TankRenderer(BlockEntityRendererProvider.Context context) {
    }

    public boolean shouldRenderOffScreen(TankTileEntity p_188185_1_) {
        return true;
    }

    public void render(TankTileEntity tile, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        if (!tile.isValid) {
            return;
        }
        IC2Tank tank = tile.tank;
        if (tank.getCapacity() <= 0 || tank.getFluidAmount() <= 0) {
            return;
        }
        FluidStack stack = tank.getFluid();
        if (stack.isEmpty()) {
            return;
        }
        VertexConsumer builder = bufferIn.m_6299_(IC2RenderTypes.FLUID_RENDER);
        int lightValue = LevelRenderer.m_109541_((BlockAndTintGetter)tile.m_58904_(), (BlockPos)tile.getCenter());
        float filled = (float)tank.getFluidAmount() / (float)tank.getCapacity();
        UVHelper notFlowing = UVHelper.fromSprite(FluidHelper.getTexture(stack, false));
        if (stack.getFluid().m_205067_(Tags.Fluids.GASEOUS)) {
            int color = FluidHelper.getFluidColor(stack);
            int alpha = color >> 24 & 0xFF;
            int sub = (int)((float)alpha * 0.5f);
            color = color & 0xFFFFFF | sub + (int)((float)(alpha - sub) * filled) << 24;
            RenderShapes.renderColorTextureLightCube(tile.getBox(1.0f), notFlowing, color, OverlayTexture.f_118083_, lightValue, builder, matrixStackIn);
        } else {
            int color = FluidHelper.getFluidColor(stack);
            AABB renderBounds = tile.getBox(filled);
            Box box = tile.getBox();
            float minimum = (float)renderBounds.f_82292_;
            RenderShapes.renderColorTextureLightCube(renderBounds, notFlowing, color, OverlayTexture.f_118083_, lightValue, builder, matrixStackIn);
            UVHelper flowing = UVHelper.fromSpriteScaled(FluidHelper.getTexture(stack, true), 3.0f, 0.0f, 90);
            for (BaseLinkingTileEntity subTile : tile.children.getClientTiles()) {
                Direction dir;
                if (!(subTile instanceof ValveTileEntity) || minimum >= (float)subTile.m_58899_().m_123342_() + 0.5f) continue;
                ValveTileEntity valve = (ValveTileEntity)subTile;
                if (!valve.render.shouldRender() || (dir = this.getDir(valve.m_58899_(), box)) == null) continue;
                BlockPos valvePos = valve.m_58899_().m_121945_(dir);
                float minX = (float)(valvePos.m_123341_() - tile.m_58899_().m_123341_()) + 0.25f;
                float minY = (float)(valvePos.m_123342_() - tile.m_58899_().m_123342_()) + 0.25f;
                float minZ = (float)(valvePos.m_123343_() - tile.m_58899_().m_123343_()) + 0.25f;
                AABB subBox = new AABB((double)minX, renderBounds.f_82292_, (double)minZ, (double)(minX + 0.5f), (double)(minY + 0.5f), (double)(minZ + 0.5f)).m_82383_(Vec3.m_82528_((Vec3i)dir.m_122424_().m_122436_()).m_82542_(0.25, 0.25, 0.25));
                this.render(subBox, (T, D) -> {
                    if (D.contains(Direction.UP)) {
                        D = D.remove(Direction.UP);
                        RenderShapes.renderColorTextureLightQuad(Direction.UP, T, notFlowing, color, OverlayTexture.f_118083_, lightValue, builder, matrixStackIn);
                    }
                    if (D.contains(Direction.DOWN)) {
                        D = D.remove(Direction.DOWN);
                        RenderShapes.renderColorTextureLightQuad(Direction.DOWN, T, notFlowing, color, OverlayTexture.f_118083_, lightValue, builder, matrixStackIn);
                    }
                    RenderShapes.renderColorTextureLightCube(D, T, flowing, color, OverlayTexture.f_118083_, lightValue, builder, matrixStackIn);
                });
            }
        }
    }

    public void render(AABB box, BiConsumer<AABB, DirectionList> listener) {
        float min = (float)box.f_82289_;
        float max = (float)box.f_82292_;
        int start = Mth.m_14107_((double)box.f_82289_);
        int end = (int)box.f_82292_;
        int m = Mth.m_14167_((float)max);
        for (int i = Mth.m_14107_((double)box.f_82289_); i < m; ++i) {
            float maxY;
            float minY = Mth.m_14036_((float)i, (float)min, (float)max);
            if (Float.compare(minY, maxY = Mth.m_14036_((float)(i + 1), (float)min, (float)max)) == 0) {
                return;
            }
            listener.accept(new AABB(box.f_82288_, (double)minY, box.f_82290_, box.f_82291_, (double)maxY, box.f_82293_), i == start ? DirectionList.UP.invert() : (i == end ? DirectionList.DOWN.invert() : DirectionList.HORIZONTAL));
        }
    }

    public Direction getDir(BlockPos pos, Box box) {
        for (Direction dir : DirectionList.DOWN.invert()) {
            if (box.intersectsWith(pos.m_121945_(dir))) continue;
            return dir.m_122424_();
        }
        return null;
    }

    public static void renderColorTextureLightCube(DirectionList directions, AABB box, TextureAtlasSprite sprite, int color, int overlay, int light, VertexConsumer builder, PoseStack stack) {
        for (Direction dir : directions) {
            TankRenderer.renderColorTextureLightQuad(dir, (float)box.f_82288_, (float)box.f_82289_, (float)box.f_82290_, (float)box.f_82291_, (float)box.f_82292_, (float)box.f_82293_, sprite, color, overlay, light, builder, stack);
        }
    }

    public static void renderColorTextureLightQuad(Direction dir, AABB box, TextureAtlasSprite sprite, int color, int overlay, int light, VertexConsumer builder, PoseStack stack) {
        TankRenderer.renderColorTextureLightQuad(dir, (float)box.f_82288_, (float)box.f_82289_, (float)box.f_82290_, (float)box.f_82291_, (float)box.f_82292_, (float)box.f_82293_, sprite, color, overlay, light, builder, stack);
    }

    public static void renderColorTextureLightQuad(Direction dir, float minX, float minY, float minZ, float maxX, float maxY, float maxZ, TextureAtlasSprite sprite, int color, int overlay, int light, VertexConsumer vertexbuffer, PoseStack stack) {
        int r = color >> 16 & 0xFF;
        int g = color >> 8 & 0xFF;
        int b = color & 0xFF;
        int a = color >> 24 & 0xFF;
        Matrix4f mat = stack.m_85850_().m_85861_();
        switch (dir) {
            case WEST: {
                vertexbuffer.m_85982_(mat, minX, maxY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.m_118410_(), sprite.m_118411_()).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, minX, minY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.m_118409_(), sprite.m_118412_()).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, minX, minY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.m_118410_(), sprite.m_118412_()).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, minX, maxY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.m_118410_(), sprite.m_118411_()).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, minX, maxY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.m_118409_(), sprite.m_118411_()).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, minX, minY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.m_118409_(), sprite.m_118412_()).m_86008_(overlay).m_85969_(light).m_5752_();
                break;
            }
            case EAST: {
                vertexbuffer.m_85982_(mat, maxX, maxY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.m_118410_(), sprite.m_118411_()).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, minY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.m_118409_(), sprite.m_118412_()).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, minY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.m_118410_(), sprite.m_118412_()).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, maxY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.m_118410_(), sprite.m_118411_()).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, maxY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.m_118409_(), sprite.m_118411_()).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, minY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.m_118409_(), sprite.m_118412_()).m_86008_(overlay).m_85969_(light).m_5752_();
                break;
            }
            case DOWN: {
                vertexbuffer.m_85982_(mat, maxX, minY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.m_118410_(), sprite.m_118411_()).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, minX, minY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.m_118409_(), sprite.m_118412_()).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, minY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.m_118410_(), sprite.m_118412_()).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, minY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.m_118410_(), sprite.m_118411_()).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, minX, minY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.m_118409_(), sprite.m_118411_()).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, minX, minY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.m_118409_(), sprite.m_118412_()).m_86008_(overlay).m_85969_(light).m_5752_();
                break;
            }
            case UP: {
                vertexbuffer.m_85982_(mat, minX, maxY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.m_118410_(), sprite.m_118411_()).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, maxY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.m_118409_(), sprite.m_118412_()).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, maxY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.m_118410_(), sprite.m_118412_()).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, minX, maxY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.m_118410_(), sprite.m_118411_()).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, minX, maxY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.m_118409_(), sprite.m_118411_()).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, maxY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.m_118409_(), sprite.m_118412_()).m_86008_(overlay).m_85969_(light).m_5752_();
                break;
            }
            case NORTH: {
                vertexbuffer.m_85982_(mat, maxX, minY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.m_118409_(), sprite.m_118412_()).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, minX, maxY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.m_118410_(), sprite.m_118411_()).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, maxY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.m_118409_(), sprite.m_118411_()).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, minY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.m_118409_(), sprite.m_118412_()).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, minX, minY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.m_118410_(), sprite.m_118412_()).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, minX, maxY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.m_118410_(), sprite.m_118411_()).m_86008_(overlay).m_85969_(light).m_5752_();
                break;
            }
            case SOUTH: {
                vertexbuffer.m_85982_(mat, maxX, maxY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.m_118410_(), sprite.m_118411_()).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, minX, minY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.m_118409_(), sprite.m_118412_()).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, minY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.m_118410_(), sprite.m_118412_()).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, maxY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.m_118410_(), sprite.m_118411_()).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, minX, maxY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.m_118409_(), sprite.m_118411_()).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, minX, minY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.m_118409_(), sprite.m_118412_()).m_86008_(overlay).m_85969_(light).m_5752_();
            }
        }
    }
}

