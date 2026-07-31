/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.BufferBuilder
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  com.mojang.math.Matrix3f
 *  com.mojang.math.Matrix4f
 *  com.mojang.math.Vector3f
 *  it.unimi.dsi.fastutil.floats.FloatArrayList
 *  it.unimi.dsi.fastutil.floats.FloatList
 *  it.unimi.dsi.fastutil.longs.LongArrayList
 *  it.unimi.dsi.fastutil.longs.LongList
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.MultiBufferSource$BufferSource
 *  net.minecraft.client.renderer.culling.Frustum
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.Vec3i
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.event.RenderLevelStageEvent
 */
package ic2.core.block.rendering.world.impl;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import ic2.core.block.base.misc.ITubeBlock;
import ic2.core.block.rendering.world.IWorldOverlay;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.rendering.RenderUtils;
import ic2.core.platform.rendering.misc.GLUtils;
import ic2.core.platform.rendering.misc.IC2RenderTypes;
import ic2.core.platform.rendering.misc.StateConsumer;
import ic2.core.utils.math.ColorUtils;
import it.unimi.dsi.fastutil.floats.FloatArrayList;
import it.unimi.dsi.fastutil.floats.FloatList;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLevelStageEvent;

public class PipeOverlay
implements IWorldOverlay {
    static final int MINING_PIPE = ColorUtils.GREEN;
    long lastRefresh = 0L;
    FloatList cache = new FloatArrayList();
    LongList posCache = new LongArrayList();

    @Override
    public void cleanup() {
        this.lastRefresh = 0L;
    }

    @Override
    public void update(Level world, Player player) {
        if (player.m_21205_().m_41720_() != IC2Blocks.MINING_PIPE_SHAFT.m_5456_() && player.m_21206_().m_41720_() != IC2Blocks.MINING_PIPE_SHAFT.m_5456_()) {
            this.cache.clear();
            this.posCache.clear();
            this.lastRefresh = 0L;
            return;
        }
        if (this.lastRefresh <= world.m_46467_()) {
            this.lastRefresh = world.m_46467_() + 20L;
            BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
            StateConsumer builder = new StateConsumer(this.cache);
            this.cache.clear();
            this.posCache.clear();
            for (int x = -20; x <= 20; ++x) {
                for (int y = -20; y <= 20; ++y) {
                    for (int z = -20; z <= 20; ++z) {
                        BlockState state = world.m_8055_((BlockPos)mutable.m_122154_((Vec3i)player.m_20183_(), x, y, z));
                        if (!(state.m_60734_() instanceof ITubeBlock)) continue;
                        ITubeBlock block = (ITubeBlock)state.m_60734_();
                        int color = block.getHighlightColor(state);
                        PipeOverlay.drawShape(builder, this.posCache, block.getRealShapes(state, (BlockGetter)world, (BlockPos)mutable), block.getBlockColor(state), mutable.m_123341_(), mutable.m_123342_(), mutable.m_123343_(), ColorUtils.getFloatR(color), ColorUtils.getFloatG(color), ColorUtils.getFloatB(color), ColorUtils.getFloatA(color));
                    }
                }
            }
        }
    }

    @Override
    public void render(Level world, Player player, RenderLevelStageEvent event, Frustum helper) {
        if (this.cache.isEmpty()) {
            return;
        }
        GLUtils.enableHighlight(true);
        RenderSystem.m_157427_(GameRenderer::m_172757_);
        RenderUtils.draw(event.getPoseStack(), (B, M) -> {
            Matrix4f entry = M.m_85850_().m_85861_();
            Matrix3f normal = M.m_85850_().m_85864_();
            BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
            MultiBufferSource.BufferSource buffer = MultiBufferSource.m_109898_((BufferBuilder)B);
            VertexConsumer V = buffer.m_6299_(IC2RenderTypes.LINES);
            int i = 0;
            int m = this.cache.size();
            while (i + 19 < m) {
                if (helper.m_113029_(new AABB((BlockPos)mutable.m_122188_(this.posCache.getLong(i / 20))))) {
                    this.vertex(V, entry, normal, i);
                    this.vertex(V, entry, normal, i + 10);
                }
                i += 20;
            }
            buffer.m_109911_();
        });
        GLUtils.disableHighlight(true);
    }

    private void vertex(VertexConsumer consumer, Matrix4f pose, Matrix3f normal, int index) {
        consumer.m_85982_(pose, this.cache.getFloat(index), this.cache.getFloat(index + 1), this.cache.getFloat(index + 2)).m_85950_(this.cache.getFloat(index + 3), this.cache.getFloat(index + 4), this.cache.getFloat(index + 5), this.cache.getFloat(index + 6)).m_85977_(normal, this.cache.getFloat(index + 7), this.cache.getFloat(index + 8), this.cache.getFloat(index + 9)).m_5752_();
    }

    @OnlyIn(value=Dist.CLIENT)
    public static void drawShape(VertexConsumer bufferIn, LongList posCache, VoxelShape[] shapeIn, DyeColor color, int xIn, int yIn, int zIn, float red, float green, float blue, float alpha) {
        shapeIn[0].m_83224_((minX, minY, minZ, maxX, maxY, maxZ) -> {
            Vector3f normal = new Vector3f((float)(maxX - minX), (float)(maxY - minY), (float)(maxZ - minZ));
            normal.m_122278_();
            bufferIn.m_5483_((double)((float)(minX + (double)xIn)), (double)((float)(minY + (double)yIn)), (double)((float)(minZ + (double)zIn))).m_85950_(red, green, blue, alpha).m_5601_(normal.m_122239_(), normal.m_122260_(), normal.m_122269_()).m_5752_();
            bufferIn.m_5483_((double)((float)(maxX + (double)xIn)), (double)((float)(maxY + (double)yIn)), (double)((float)(maxZ + (double)zIn))).m_85950_(red, green, blue, alpha).m_5601_(normal.m_122239_(), normal.m_122260_(), normal.m_122269_()).m_5752_();
            posCache.add(BlockPos.m_121882_((int)xIn, (int)yIn, (int)zIn));
        });
        shapeIn[1].m_83224_((minX, minY, minZ, maxX, maxY, maxZ) -> {
            Vector3f normal = new Vector3f((float)(maxX - minX), (float)(maxY - minY), (float)(maxZ - minZ));
            normal.m_122278_();
            bufferIn.m_5483_((double)((float)(minX + (double)xIn)), (double)((float)(minY + (double)yIn)), (double)((float)(minZ + (double)zIn))).m_85950_(ColorUtils.getFloatR(MINING_PIPE), ColorUtils.getFloatG(MINING_PIPE), ColorUtils.getFloatB(MINING_PIPE), ColorUtils.getFloatA(MINING_PIPE)).m_5601_(normal.m_122239_(), normal.m_122260_(), normal.m_122269_()).m_5752_();
            bufferIn.m_5483_((double)((float)(maxX + (double)xIn)), (double)((float)(maxY + (double)yIn)), (double)((float)(maxZ + (double)zIn))).m_85950_(ColorUtils.getFloatR(MINING_PIPE), ColorUtils.getFloatG(MINING_PIPE), ColorUtils.getFloatB(MINING_PIPE), ColorUtils.getFloatA(MINING_PIPE)).m_5601_(normal.m_122239_(), normal.m_122260_(), normal.m_122269_()).m_5752_();
            posCache.add(BlockPos.m_121882_((int)xIn, (int)yIn, (int)zIn));
        });
        if (color != null) {
            int text = color.m_41071_() | 0xFF000000;
            Block.m_49796_((double)7.0, (double)7.0, (double)7.0, (double)9.0, (double)9.0, (double)9.0).m_83224_((minX, minY, minZ, maxX, maxY, maxZ) -> {
                Vector3f normal = new Vector3f((float)(maxX - minX), (float)(maxY - minY), (float)(maxZ - minZ));
                normal.m_122278_();
                bufferIn.m_5483_((double)((float)(minX + (double)xIn)), (double)((float)(minY + (double)yIn)), (double)((float)(minZ + (double)zIn))).m_85950_(ColorUtils.getFloatR(text), ColorUtils.getFloatG(text), ColorUtils.getFloatB(text), ColorUtils.getFloatA(text)).m_5601_(normal.m_122239_(), normal.m_122260_(), normal.m_122269_()).m_5752_();
                bufferIn.m_5483_((double)((float)(maxX + (double)xIn)), (double)((float)(maxY + (double)yIn)), (double)((float)(maxZ + (double)zIn))).m_85950_(ColorUtils.getFloatR(text), ColorUtils.getFloatG(text), ColorUtils.getFloatB(text), ColorUtils.getFloatA(text)).m_5601_(normal.m_122239_(), normal.m_122260_(), normal.m_122269_()).m_5752_();
                posCache.add(BlockPos.m_121882_((int)xIn, (int)yIn, (int)zIn));
            });
        }
    }
}

