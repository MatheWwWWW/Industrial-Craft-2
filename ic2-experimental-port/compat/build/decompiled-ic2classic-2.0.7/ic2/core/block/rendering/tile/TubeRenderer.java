/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.math.Quaternion
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.block.model.ItemTransforms$TransformType
 *  net.minecraft.client.renderer.blockentity.BlockEntityRenderer
 *  net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider$Context
 *  net.minecraft.client.renderer.entity.ItemRenderer
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$AxisDirection
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 */
package ic2.core.block.rendering.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import ic2.api.tiles.tubes.TransportedItem;
import ic2.core.block.transport.item.TubeTileEntity;
import ic2.core.block.transport.item.logic.TransportList;
import ic2.core.block.transport.item.tubes.ColorTubeTileEntity;
import ic2.core.platform.rendering.RenderShapes;
import ic2.core.platform.rendering.misc.IC2RenderTypes;
import ic2.core.utils.math.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class TubeRenderer
implements BlockEntityRenderer<TubeTileEntity> {
    static final Vec3[] OFFSETS = new Vec3[]{new Vec3(0.0, -0.5, 0.0), new Vec3(0.0, 0.5, 0.0), new Vec3(0.0, 0.0, -0.5), new Vec3(0.0, 0.0, 0.5), new Vec3(-0.5, 0.0, 0.0), new Vec3(0.5, 0.0, 0.0)};
    static final AABB CUBE_BOX = new AABB(-0.5, -0.5, -0.5, 0.5, 0.5, 0.5);
    final int[] dyeColors = new int[16];

    public TubeRenderer(BlockEntityRendererProvider.Context context) {
        for (int i = 0; i < 16; ++i) {
            float[] color = DyeColor.m_41053_((int)i).m_41068_();
            this.dyeColors[i] = ColorUtils.rgb(color[0], color[1], color[2], 0.5f);
        }
    }

    public void render(TubeTileEntity tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        TransportList items = tileEntityIn.items;
        if (items.isEmpty() || !tileEntityIn.synchronize) {
            return;
        }
        matrixStackIn.m_85837_(0.5, 0.5, 0.5);
        LocalPlayer player = Minecraft.m_91087_().f_91074_;
        ClientLevel world = Minecraft.m_91087_().f_91073_;
        ItemRenderer render = Minecraft.m_91087_().m_91291_();
        int m = Math.min(items.size(), 200);
        for (int i = 0; i < m; ++i) {
            TransportedItem item = (TransportedItem)items.get(i);
            Direction dir = item.getTransferDirection();
            Vec3 vec = OFFSETS[dir.m_122411_()].m_82490_((double)Mth.m_14036_((float)(item.isCentering() ? 1.0f - ((float)item.getProgress() + 100.0f) / 100.0f : (float)item.getProgress() / 100.0f), (float)0.0f, (float)1.0f));
            matrixStackIn.m_85837_(vec.f_82479_, vec.f_82480_, vec.f_82481_);
            matrixStackIn.m_85841_(0.25f, 0.25f, 0.25f);
            dir = item.isCentering() ? dir.m_122424_() : dir;
            matrixStackIn.m_85845_(this.getRotation(dir, 1.0f));
            render.m_174242_((LivingEntity)player, item.getStack(), ItemTransforms.TransformType.FIXED, false, matrixStackIn, bufferIn, (Level)world, combinedLightIn, combinedOverlayIn, 0);
            DyeColor color = item.getColor();
            if (color != null) {
                RenderShapes.renderColorCube(CUBE_BOX, this.dyeColors[color.m_41060_()], bufferIn.m_6299_(IC2RenderTypes.TUBE_ITEM_HIGHLIGHT), matrixStackIn);
            }
            matrixStackIn.m_85845_(this.getRotation(dir, -1.0f));
            matrixStackIn.m_85841_(4.0f, 4.0f, 4.0f);
            matrixStackIn.m_85837_(-vec.f_82479_, -vec.f_82480_, -vec.f_82481_);
        }
        matrixStackIn.m_85837_(-0.5, -0.5, -0.5);
    }

    public Quaternion getRotation(Direction dir, float value) {
        return dir.m_122434_().m_122479_() ? new Quaternion(0.0f, dir.m_122435_() * value, 0.0f, true) : new Quaternion((dir.m_122421_() == Direction.AxisDirection.NEGATIVE ? 90.0f : 270.0f) * value, 0.0f, 0.0f, true);
    }

    public static class ColorTubeRenderer
    extends TubeRenderer {
        public ColorTubeRenderer(BlockEntityRendererProvider.Context context) {
            super(context);
        }

        @Override
        public void render(TubeTileEntity tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
            if (tileEntityIn instanceof ColorTubeTileEntity) {
                ColorTubeTileEntity tile = (ColorTubeTileEntity)tileEntityIn;
                if (tile.color != 0) {
                    matrixStackIn.m_85837_(0.5, 0.5, 0.5);
                    matrixStackIn.m_85841_(0.4f, 0.4f, 0.4f);
                    RenderShapes.renderColorCube(CUBE_BOX, this.dyeColors[tile.color - 1], bufferIn.m_6299_(IC2RenderTypes.TUBE_ITEM_HIGHLIGHT), matrixStackIn);
                    matrixStackIn.m_85841_(2.5f, 2.5f, 2.5f);
                    matrixStackIn.m_85837_(-0.5, -0.5, -0.5);
                }
            }
            super.render(tileEntityIn, partialTicks, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        }
    }
}

