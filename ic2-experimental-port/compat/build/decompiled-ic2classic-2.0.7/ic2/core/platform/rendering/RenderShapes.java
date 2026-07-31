/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  com.mojang.math.Matrix3f
 *  com.mojang.math.Matrix4f
 *  com.mojang.math.Vector3f
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.platform.rendering;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import ic2.api.util.DirectionList;
import ic2.core.platform.rendering.misc.UVHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(value=Dist.CLIENT)
public class RenderShapes {
    public static void drawSelectionBox(PoseStack matrixStackIn, VertexConsumer bufferIn, Entity entityIn, double xIn, double yIn, double zIn, BlockPos blockPosIn, BlockState blockStateIn) {
        RenderShapes.drawShape(matrixStackIn, bufferIn, blockStateIn.m_60651_((BlockGetter)entityIn.f_19853_, blockPosIn, CollisionContext.m_82750_((Entity)entityIn)), (double)blockPosIn.m_123341_() - xIn, (double)blockPosIn.m_123342_() - yIn, (double)blockPosIn.m_123343_() - zIn, 0.0f, 0.0f, 0.0f, 0.4f);
    }

    public static void drawShape(PoseStack matrixStackIn, VertexConsumer bufferIn, VoxelShape shapeIn, double xIn, double yIn, double zIn, float red, float green, float blue, float alpha) {
        Matrix4f matrix4f = matrixStackIn.m_85850_().m_85861_();
        Matrix3f matrix3f = matrixStackIn.m_85850_().m_85864_();
        shapeIn.m_83224_((minX, minY, minZ, maxX, maxY, maxZ) -> {
            Vector3f normal = new Vector3f((float)(maxX - minX), (float)(maxY - minY), (float)(maxZ - minZ));
            normal.m_122278_();
            bufferIn.m_85982_(matrix4f, (float)(minX + xIn), (float)(minY + yIn), (float)(minZ + zIn)).m_85950_(red, green, blue, alpha).m_85977_(matrix3f, normal.m_122239_(), normal.m_122260_(), normal.m_122269_()).m_5752_();
            bufferIn.m_85982_(matrix4f, (float)(maxX + xIn), (float)(maxY + yIn), (float)(maxZ + zIn)).m_85950_(red, green, blue, alpha).m_85977_(matrix3f, normal.m_122239_(), normal.m_122260_(), normal.m_122269_()).m_5752_();
        });
    }

    public static void renderInvertedColorCube(AABB box, int color, VertexConsumer builder, PoseStack stack) {
        RenderShapes.renderInvertedColorCube(DirectionList.ALL, box, color, builder, stack);
    }

    public static void renderInvertedColorCube(DirectionList directions, AABB box, int color, VertexConsumer builder, PoseStack stack) {
        for (Direction dir : directions) {
            RenderShapes.renderInvertedColorCube(dir, box, color, builder, stack);
        }
    }

    public static void renderInvertedColorCube(Direction dir, AABB box, int color, VertexConsumer builder, PoseStack stack) {
        RenderShapes.renderColorQuad(dir, (float)box.f_82291_, (float)box.f_82292_, (float)box.f_82293_, (float)box.f_82288_, (float)box.f_82289_, (float)box.f_82290_, color, builder, stack);
    }

    public static void renderColorCube(AABB box, int color, VertexConsumer builder, PoseStack stack) {
        RenderShapes.renderColorCube(DirectionList.ALL, box, color, builder, stack);
    }

    public static void renderColorCube(DirectionList directions, AABB box, int color, VertexConsumer builder, PoseStack stack) {
        for (Direction dir : directions) {
            RenderShapes.renderColorQuad(dir, box, color, builder, stack);
        }
    }

    public static void renderColorQuad(Direction dir, AABB box, int color, VertexConsumer builder, PoseStack stack) {
        RenderShapes.renderColorQuad(dir, (float)box.f_82288_, (float)box.f_82289_, (float)box.f_82290_, (float)box.f_82291_, (float)box.f_82292_, (float)box.f_82293_, color, builder, stack);
    }

    public static void renderColorQuad(Direction dir, float minX, float minY, float minZ, float maxX, float maxY, float maxZ, int color, VertexConsumer vertexbuffer, PoseStack stack) {
        int r = color >> 16 & 0xFF;
        int g = color >> 8 & 0xFF;
        int b = color & 0xFF;
        int a = color >> 24 & 0xFF;
        Matrix4f mat = stack.m_85850_().m_85861_();
        switch (dir) {
            case WEST: {
                vertexbuffer.m_85982_(mat, minX, maxY, maxZ).m_6122_(r, g, b, a).m_5752_();
                vertexbuffer.m_85982_(mat, minX, minY, minZ).m_6122_(r, g, b, a).m_5752_();
                vertexbuffer.m_85982_(mat, minX, minY, maxZ).m_6122_(r, g, b, a).m_5752_();
                vertexbuffer.m_85982_(mat, minX, maxY, maxZ).m_6122_(r, g, b, a).m_5752_();
                vertexbuffer.m_85982_(mat, minX, maxY, minZ).m_6122_(r, g, b, a).m_5752_();
                vertexbuffer.m_85982_(mat, minX, minY, minZ).m_6122_(r, g, b, a).m_5752_();
                break;
            }
            case EAST: {
                vertexbuffer.m_85982_(mat, maxX, maxY, minZ).m_6122_(r, g, b, a).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, minY, maxZ).m_6122_(r, g, b, a).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, minY, minZ).m_6122_(r, g, b, a).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, maxY, minZ).m_6122_(r, g, b, a).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, maxY, maxZ).m_6122_(r, g, b, a).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, minY, maxZ).m_6122_(r, g, b, a).m_5752_();
                break;
            }
            case DOWN: {
                vertexbuffer.m_85982_(mat, maxX, minY, maxZ).m_6122_(r, g, b, a).m_5752_();
                vertexbuffer.m_85982_(mat, minX, minY, minZ).m_6122_(r, g, b, a).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, minY, minZ).m_6122_(r, g, b, a).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, minY, maxZ).m_6122_(r, g, b, a).m_5752_();
                vertexbuffer.m_85982_(mat, minX, minY, maxZ).m_6122_(r, g, b, a).m_5752_();
                vertexbuffer.m_85982_(mat, minX, minY, minZ).m_6122_(r, g, b, a).m_5752_();
                break;
            }
            case UP: {
                vertexbuffer.m_85982_(mat, minX, maxY, minZ).m_6122_(r, g, b, a).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, maxY, maxZ).m_6122_(r, g, b, a).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, maxY, minZ).m_6122_(r, g, b, a).m_5752_();
                vertexbuffer.m_85982_(mat, minX, maxY, minZ).m_6122_(r, g, b, a).m_5752_();
                vertexbuffer.m_85982_(mat, minX, maxY, maxZ).m_6122_(r, g, b, a).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, maxY, maxZ).m_6122_(r, g, b, a).m_5752_();
                break;
            }
            case NORTH: {
                vertexbuffer.m_85982_(mat, maxX, minY, minZ).m_6122_(r, g, b, a).m_5752_();
                vertexbuffer.m_85982_(mat, minX, maxY, minZ).m_6122_(r, g, b, a).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, maxY, minZ).m_6122_(r, g, b, a).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, minY, minZ).m_6122_(r, g, b, a).m_5752_();
                vertexbuffer.m_85982_(mat, minX, minY, minZ).m_6122_(r, g, b, a).m_5752_();
                vertexbuffer.m_85982_(mat, minX, maxY, minZ).m_6122_(r, g, b, a).m_5752_();
                break;
            }
            case SOUTH: {
                vertexbuffer.m_85982_(mat, maxX, maxY, maxZ).m_6122_(r, g, b, a).m_5752_();
                vertexbuffer.m_85982_(mat, minX, minY, maxZ).m_6122_(r, g, b, a).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, minY, maxZ).m_6122_(r, g, b, a).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, maxY, maxZ).m_6122_(r, g, b, a).m_5752_();
                vertexbuffer.m_85982_(mat, minX, maxY, maxZ).m_6122_(r, g, b, a).m_5752_();
                vertexbuffer.m_85982_(mat, minX, minY, maxZ).m_6122_(r, g, b, a).m_5752_();
            }
        }
    }

    public static void renderTextureCube(AABB box, UVHelper sprite, VertexConsumer builder, PoseStack stack) {
        RenderShapes.renderTextureCube(DirectionList.ALL, box, sprite, builder, stack);
    }

    public static void renderTextureCube(DirectionList directions, AABB box, UVHelper sprite, VertexConsumer builder, PoseStack stack) {
        for (Direction dir : directions) {
            RenderShapes.renderTextureQuad(dir, box, sprite, builder, stack);
        }
    }

    public static void renderTextureQuad(Direction dir, AABB box, UVHelper sprite, VertexConsumer builder, PoseStack stack) {
        RenderShapes.renderTextureQuad(dir, (float)box.f_82288_, (float)box.f_82289_, (float)box.f_82290_, (float)box.f_82291_, (float)box.f_82292_, (float)box.f_82293_, sprite, builder, stack);
    }

    public static void renderTextureQuad(Direction dir, float minX, float minY, float minZ, float maxX, float maxY, float maxZ, UVHelper sprite, VertexConsumer vertexbuffer, PoseStack stack) {
        Matrix4f mat = stack.m_85850_().m_85861_();
        if (dir == Direction.NORTH) {
            sprite.rotate(180);
        }
        switch (dir) {
            case WEST: {
                vertexbuffer.m_85982_(mat, minX, maxY, maxZ).m_7421_(sprite.getVertexU(2), sprite.getVertexV(2)).m_5752_();
                vertexbuffer.m_85982_(mat, minX, minY, minZ).m_7421_(sprite.getVertexU(0), sprite.getVertexV(0)).m_5752_();
                vertexbuffer.m_85982_(mat, minX, minY, maxZ).m_7421_(sprite.getVertexU(1), sprite.getVertexV(1)).m_5752_();
                vertexbuffer.m_85982_(mat, minX, maxY, maxZ).m_7421_(sprite.getVertexU(2), sprite.getVertexV(2)).m_5752_();
                vertexbuffer.m_85982_(mat, minX, maxY, minZ).m_7421_(sprite.getVertexU(3), sprite.getVertexV(3)).m_5752_();
                vertexbuffer.m_85982_(mat, minX, minY, minZ).m_7421_(sprite.getVertexU(0), sprite.getVertexV(0)).m_5752_();
                break;
            }
            case EAST: {
                vertexbuffer.m_85982_(mat, maxX, maxY, minZ).m_7421_(sprite.getVertexU(2), sprite.getVertexV(2)).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, minY, maxZ).m_7421_(sprite.getVertexU(0), sprite.getVertexV(0)).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, minY, minZ).m_7421_(sprite.getVertexU(1), sprite.getVertexV(1)).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, maxY, minZ).m_7421_(sprite.getVertexU(2), sprite.getVertexV(2)).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, maxY, maxZ).m_7421_(sprite.getVertexU(3), sprite.getVertexV(3)).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, minY, maxZ).m_7421_(sprite.getVertexU(0), sprite.getVertexV(0)).m_5752_();
                break;
            }
            case DOWN: {
                vertexbuffer.m_85982_(mat, maxX, minY, maxZ).m_7421_(sprite.getVertexU(2), sprite.getVertexV(2)).m_5752_();
                vertexbuffer.m_85982_(mat, minX, minY, minZ).m_7421_(sprite.getVertexU(0), sprite.getVertexV(0)).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, minY, minZ).m_7421_(sprite.getVertexU(1), sprite.getVertexV(1)).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, minY, maxZ).m_7421_(sprite.getVertexU(2), sprite.getVertexV(2)).m_5752_();
                vertexbuffer.m_85982_(mat, minX, minY, maxZ).m_7421_(sprite.getVertexU(3), sprite.getVertexV(3)).m_5752_();
                vertexbuffer.m_85982_(mat, minX, minY, minZ).m_7421_(sprite.getVertexU(0), sprite.getVertexV(0)).m_5752_();
                break;
            }
            case UP: {
                vertexbuffer.m_85982_(mat, minX, maxY, minZ).m_7421_(sprite.getVertexU(2), sprite.getVertexV(2)).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, maxY, maxZ).m_7421_(sprite.getVertexU(0), sprite.getVertexV(0)).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, maxY, minZ).m_7421_(sprite.getVertexU(1), sprite.getVertexV(1)).m_5752_();
                vertexbuffer.m_85982_(mat, minX, maxY, minZ).m_7421_(sprite.getVertexU(2), sprite.getVertexV(2)).m_5752_();
                vertexbuffer.m_85982_(mat, minX, maxY, maxZ).m_7421_(sprite.getVertexU(3), sprite.getVertexV(3)).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, maxY, maxZ).m_7421_(sprite.getVertexU(0), sprite.getVertexV(0)).m_5752_();
                break;
            }
            case NORTH: {
                vertexbuffer.m_85982_(mat, maxX, minY, minZ).m_7421_(sprite.getVertexU(2), sprite.getVertexV(2)).m_5752_();
                vertexbuffer.m_85982_(mat, minX, maxY, minZ).m_7421_(sprite.getVertexU(0), sprite.getVertexV(0)).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, maxY, minZ).m_7421_(sprite.getVertexU(1), sprite.getVertexV(1)).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, minY, minZ).m_7421_(sprite.getVertexU(2), sprite.getVertexV(2)).m_5752_();
                vertexbuffer.m_85982_(mat, minX, minY, minZ).m_7421_(sprite.getVertexU(3), sprite.getVertexV(3)).m_5752_();
                vertexbuffer.m_85982_(mat, minX, maxY, minZ).m_7421_(sprite.getVertexU(0), sprite.getVertexV(0)).m_5752_();
                break;
            }
            case SOUTH: {
                vertexbuffer.m_85982_(mat, maxX, maxY, maxZ).m_7421_(sprite.getVertexU(2), sprite.getVertexV(2)).m_5752_();
                vertexbuffer.m_85982_(mat, minX, minY, maxZ).m_7421_(sprite.getVertexU(0), sprite.getVertexV(0)).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, minY, maxZ).m_7421_(sprite.getVertexU(1), sprite.getVertexV(1)).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, maxY, maxZ).m_7421_(sprite.getVertexU(2), sprite.getVertexV(2)).m_5752_();
                vertexbuffer.m_85982_(mat, minX, maxY, maxZ).m_7421_(sprite.getVertexU(3), sprite.getVertexV(3)).m_5752_();
                vertexbuffer.m_85982_(mat, minX, minY, maxZ).m_7421_(sprite.getVertexU(0), sprite.getVertexV(0)).m_5752_();
            }
        }
        if (dir == Direction.NORTH) {
            sprite.rotate(180);
        }
    }

    public static void renderColorTextureCube(AABB box, UVHelper sprite, int color, VertexConsumer builder, PoseStack stack) {
        RenderShapes.renderColorTextureCube(DirectionList.ALL, box, sprite, color, builder, stack);
    }

    public static void renderColorTextureCube(DirectionList directions, AABB box, UVHelper sprite, int color, VertexConsumer builder, PoseStack stack) {
        for (Direction dir : directions) {
            RenderShapes.renderColorTextureQuad(dir, box, sprite, color, builder, stack);
        }
    }

    public static void renderColorTextureQuad(Direction dir, AABB box, UVHelper sprite, int color, VertexConsumer builder, PoseStack stack) {
        RenderShapes.renderColorTextureQuad(dir, (float)box.f_82288_, (float)box.f_82289_, (float)box.f_82290_, (float)box.f_82291_, (float)box.f_82292_, (float)box.f_82293_, sprite, color, builder, stack);
    }

    public static void renderColorTextureQuad(Direction dir, float minX, float minY, float minZ, float maxX, float maxY, float maxZ, UVHelper sprite, int color, VertexConsumer vertexbuffer, PoseStack stack) {
        int r = color >> 16 & 0xFF;
        int g = color >> 8 & 0xFF;
        int b = color & 0xFF;
        int a = color >> 24 & 0xFF;
        Matrix4f mat = stack.m_85850_().m_85861_();
        if (dir == Direction.NORTH) {
            sprite.rotate(180);
        }
        switch (dir) {
            case WEST: {
                vertexbuffer.m_85982_(mat, minX, maxY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(2), sprite.getVertexV(2)).m_5752_();
                vertexbuffer.m_85982_(mat, minX, minY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(0), sprite.getVertexV(0)).m_5752_();
                vertexbuffer.m_85982_(mat, minX, minY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(1), sprite.getVertexV(1)).m_5752_();
                vertexbuffer.m_85982_(mat, minX, maxY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(2), sprite.getVertexV(2)).m_5752_();
                vertexbuffer.m_85982_(mat, minX, maxY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(3), sprite.getVertexV(3)).m_5752_();
                vertexbuffer.m_85982_(mat, minX, minY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(0), sprite.getVertexV(0)).m_5752_();
                break;
            }
            case EAST: {
                vertexbuffer.m_85982_(mat, maxX, maxY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(2), sprite.getVertexV(2)).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, minY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(0), sprite.getVertexV(0)).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, minY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(1), sprite.getVertexV(1)).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, maxY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(2), sprite.getVertexV(2)).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, maxY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(3), sprite.getVertexV(3)).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, minY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(0), sprite.getVertexV(0)).m_5752_();
                break;
            }
            case DOWN: {
                vertexbuffer.m_85982_(mat, maxX, minY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(2), sprite.getVertexV(2)).m_5752_();
                vertexbuffer.m_85982_(mat, minX, minY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(0), sprite.getVertexV(0)).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, minY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(1), sprite.getVertexV(1)).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, minY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(2), sprite.getVertexV(2)).m_5752_();
                vertexbuffer.m_85982_(mat, minX, minY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(3), sprite.getVertexV(3)).m_5752_();
                vertexbuffer.m_85982_(mat, minX, minY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(0), sprite.getVertexV(0)).m_5752_();
                break;
            }
            case UP: {
                vertexbuffer.m_85982_(mat, minX, maxY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(2), sprite.getVertexV(2)).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, maxY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(0), sprite.getVertexV(0)).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, maxY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(1), sprite.getVertexV(1)).m_5752_();
                vertexbuffer.m_85982_(mat, minX, maxY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(2), sprite.getVertexV(2)).m_5752_();
                vertexbuffer.m_85982_(mat, minX, maxY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(3), sprite.getVertexV(3)).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, maxY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(0), sprite.getVertexV(0)).m_5752_();
                break;
            }
            case NORTH: {
                vertexbuffer.m_85982_(mat, maxX, minY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(2), sprite.getVertexV(2)).m_5752_();
                vertexbuffer.m_85982_(mat, minX, maxY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(0), sprite.getVertexV(0)).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, maxY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(1), sprite.getVertexV(1)).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, minY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(2), sprite.getVertexV(2)).m_5752_();
                vertexbuffer.m_85982_(mat, minX, minY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(3), sprite.getVertexV(3)).m_5752_();
                vertexbuffer.m_85982_(mat, minX, maxY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(0), sprite.getVertexV(0)).m_5752_();
                break;
            }
            case SOUTH: {
                vertexbuffer.m_85982_(mat, maxX, maxY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(2), sprite.getVertexV(2)).m_5752_();
                vertexbuffer.m_85982_(mat, minX, minY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(0), sprite.getVertexV(0)).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, minY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(1), sprite.getVertexV(1)).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, maxY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(2), sprite.getVertexV(2)).m_5752_();
                vertexbuffer.m_85982_(mat, minX, maxY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(3), sprite.getVertexV(3)).m_5752_();
                vertexbuffer.m_85982_(mat, minX, minY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(0), sprite.getVertexV(0)).m_5752_();
            }
        }
        if (dir == Direction.NORTH) {
            sprite.rotate(180);
        }
    }

    public static void renderColorTextureLightCube(AABB box, UVHelper sprite, int color, int overlay, int light, VertexConsumer builder, PoseStack stack) {
        RenderShapes.renderColorTextureLightCube(DirectionList.ALL, box, sprite, color, overlay, light, builder, stack);
    }

    public static void renderColorTextureLightCube(DirectionList directions, AABB box, UVHelper sprite, int color, int overlay, int light, VertexConsumer builder, PoseStack stack) {
        for (Direction dir : directions) {
            RenderShapes.renderColorTextureLightQuad(dir, box, sprite, color, overlay, light, builder, stack);
        }
    }

    public static void renderColorTextureLightQuad(Direction dir, AABB box, UVHelper sprite, int color, int overlay, int light, VertexConsumer builder, PoseStack stack) {
        RenderShapes.renderColorTextureLightQuad(dir, (float)box.f_82288_, (float)box.f_82289_, (float)box.f_82290_, (float)box.f_82291_, (float)box.f_82292_, (float)box.f_82293_, sprite, color, overlay, light, builder, stack);
    }

    public static void renderColorTextureLightQuad(Direction dir, float minX, float minY, float minZ, float maxX, float maxY, float maxZ, UVHelper sprite, int color, int overlay, int light, VertexConsumer vertexbuffer, PoseStack stack) {
        int r = color >> 16 & 0xFF;
        int g = color >> 8 & 0xFF;
        int b = color & 0xFF;
        int a = color >> 24 & 0xFF;
        Matrix4f mat = stack.m_85850_().m_85861_();
        if (dir == Direction.NORTH) {
            sprite.rotate(180);
        }
        switch (dir) {
            case WEST: {
                vertexbuffer.m_85982_(mat, minX, maxY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(2), sprite.getVertexV(2)).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, minX, minY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(0), sprite.getVertexV(0)).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, minX, minY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(1), sprite.getVertexV(1)).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, minX, maxY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(2), sprite.getVertexV(2)).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, minX, maxY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(3), sprite.getVertexV(3)).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, minX, minY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(0), sprite.getVertexV(0)).m_86008_(overlay).m_85969_(light).m_5752_();
                break;
            }
            case EAST: {
                vertexbuffer.m_85982_(mat, maxX, maxY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(2), sprite.getVertexV(2)).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, minY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(0), sprite.getVertexV(0)).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, minY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(1), sprite.getVertexV(1)).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, maxY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(2), sprite.getVertexV(2)).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, maxY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(3), sprite.getVertexV(3)).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, minY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(0), sprite.getVertexV(0)).m_86008_(overlay).m_85969_(light).m_5752_();
                break;
            }
            case DOWN: {
                vertexbuffer.m_85982_(mat, maxX, minY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(2), sprite.getVertexV(2)).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, minX, minY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(0), sprite.getVertexV(0)).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, minY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(1), sprite.getVertexV(1)).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, minY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(2), sprite.getVertexV(2)).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, minX, minY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(3), sprite.getVertexV(3)).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, minX, minY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(0), sprite.getVertexV(0)).m_86008_(overlay).m_85969_(light).m_5752_();
                break;
            }
            case UP: {
                vertexbuffer.m_85982_(mat, minX, maxY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(2), sprite.getVertexV(2)).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, maxY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(0), sprite.getVertexV(0)).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, maxY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(1), sprite.getVertexV(1)).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, minX, maxY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(2), sprite.getVertexV(2)).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, minX, maxY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(3), sprite.getVertexV(3)).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, maxY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(0), sprite.getVertexV(0)).m_86008_(overlay).m_85969_(light).m_5752_();
                break;
            }
            case NORTH: {
                vertexbuffer.m_85982_(mat, maxX, minY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(2), sprite.getVertexV(2)).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, minX, maxY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(0), sprite.getVertexV(0)).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, maxY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(1), sprite.getVertexV(1)).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, minY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(2), sprite.getVertexV(2)).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, minX, minY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(3), sprite.getVertexV(3)).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, minX, maxY, minZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(0), sprite.getVertexV(0)).m_86008_(overlay).m_85969_(light).m_5752_();
                break;
            }
            case SOUTH: {
                vertexbuffer.m_85982_(mat, maxX, maxY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(2), sprite.getVertexV(2)).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, minX, minY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(0), sprite.getVertexV(0)).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, minY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(1), sprite.getVertexV(1)).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, maxX, maxY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(2), sprite.getVertexV(2)).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, minX, maxY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(3), sprite.getVertexV(3)).m_86008_(overlay).m_85969_(light).m_5752_();
                vertexbuffer.m_85982_(mat, minX, minY, maxZ).m_6122_(r, g, b, a).m_7421_(sprite.getVertexU(0), sprite.getVertexV(0)).m_86008_(overlay).m_85969_(light).m_5752_();
            }
        }
        if (dir == Direction.NORTH) {
            sprite.rotate(180);
        }
    }
}

