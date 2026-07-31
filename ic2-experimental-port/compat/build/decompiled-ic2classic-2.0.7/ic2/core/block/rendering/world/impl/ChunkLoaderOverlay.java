/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.BufferBuilder
 *  com.mojang.blaze3d.vertex.DefaultVertexFormat
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  com.mojang.blaze3d.vertex.VertexFormat$Mode
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.ints.IntList
 *  it.unimi.dsi.fastutil.longs.Long2IntMap
 *  it.unimi.dsi.fastutil.longs.Long2IntMap$Entry
 *  it.unimi.dsi.fastutil.longs.Long2IntMaps
 *  it.unimi.dsi.fastutil.longs.LongArrayList
 *  it.unimi.dsi.fastutil.longs.LongList
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.MultiBufferSource$BufferSource
 *  net.minecraft.client.renderer.culling.Frustum
 *  net.minecraft.client.renderer.debug.DebugRenderer
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.ChunkPos
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.AABB
 *  net.minecraftforge.client.event.RenderLevelStageEvent
 */
package ic2.core.block.rendering.world.impl;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import ic2.core.block.rendering.world.IWorldOverlay;
import ic2.core.networking.PacketManager;
import ic2.core.networking.packets.client.ChunkRequestPacket;
import ic2.core.platform.rendering.RenderShapes;
import ic2.core.platform.rendering.RenderUtils;
import ic2.core.utils.math.ColorUtils;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.longs.Long2IntMap;
import it.unimi.dsi.fastutil.longs.Long2IntMaps;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.client.event.RenderLevelStageEvent;

public class ChunkLoaderOverlay
implements IWorldOverlay {
    public static final ChunkLoaderOverlay INSTANCE = new ChunkLoaderOverlay();
    boolean lastCheck = false;
    Long2IntMap map = Long2IntMaps.EMPTY_MAP;

    @Override
    public void cleanup() {
        this.map.clear();
        this.lastCheck = false;
    }

    @Override
    public void update(Level world, Player player) {
        Minecraft mc = Minecraft.m_91087_();
        DebugRenderer render = mc.f_91064_;
        render.m_113506_();
        if (!render.m_113506_()) {
            this.lastCheck = false;
            this.map.clear();
            return;
        }
        if (!this.lastCheck || mc.f_91073_.m_46467_() % 100L == 0L) {
            this.lastCheck = true;
            PacketManager.INSTANCE.sendToServer(new ChunkRequestPacket());
        }
    }

    @Override
    public void render(Level world, Player player, RenderLevelStageEvent event, Frustum helper) {
        if (this.map.isEmpty()) {
            return;
        }
        BlockPos playerPos = player.m_20183_();
        LongArrayList higher = new LongArrayList();
        IntArrayList number = new IntArrayList();
        RenderSystem.m_157427_(GameRenderer::m_172811_);
        RenderUtils.draw(event.getPoseStack(), (arg_0, arg_1) -> this.lambda$render$0(playerPos, helper, (LongList)higher, (IntList)number, arg_0, arg_1)).m_85914_();
        if (higher.size() > 0) {
            PoseStack stack = RenderUtils.createPlayer(event.getPoseStack());
            Font font = Minecraft.m_91087_().f_91062_;
            MultiBufferSource.BufferSource impl = Minecraft.m_91087_().m_91269_().m_110104_();
            int m = higher.size();
            for (int i = 0; i < m; ++i) {
                ChunkPos pos = new ChunkPos(higher.getLong(i));
                int y = (playerPos.m_123342_() >> 4 << 4) - 16;
                for (int j = 0; j < 4; ++j) {
                    stack.m_85836_();
                    stack.m_85837_((double)((float)pos.m_45604_() + 8.0f), (double)y + 1.0, (double)((float)pos.m_45605_() + 8.0f));
                    stack.m_85841_(-0.1f, -0.1f, -0.1f);
                    stack.m_85845_(Minecraft.m_91087_().m_91290_().m_114470_());
                    String s = Integer.toString(number.getInt(i));
                    font.m_92811_(s, 0.0f - (float)font.m_92895_(s) * 0.5f, 0.0f, ColorUtils.RED, false, stack.m_85850_().m_85861_(), (MultiBufferSource)impl, false, 0, 0xF000F0);
                    stack.m_85849_();
                    y += 16;
                }
            }
            impl.m_109911_();
            stack.m_85849_();
        }
    }

    public void setForceLoadedChunks(Long2IntMap map) {
        this.map = map;
    }

    private /* synthetic */ void lambda$render$0(BlockPos playerPos, Frustum helper, LongList higher, IntList number, BufferBuilder B, PoseStack M) {
        B.m_166779_(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.f_85815_);
        for (Long2IntMap.Entry entry : Long2IntMaps.fastIterable((Long2IntMap)this.map)) {
            int z;
            int y;
            long chunk = entry.getLongKey();
            int x = ChunkPos.m_45592_((long)chunk) * 16;
            if (!helper.m_113029_(new AABB((double)(x + 7), (double)(y = (playerPos.m_123342_() >> 4 << 4) - 16), (double)((z = ChunkPos.m_45602_((long)chunk) * 16) + 7), (double)x + 9.1, (double)(y + 48), (double)z + 9.1))) continue;
            for (int j = 0; j < 4; ++j) {
                RenderShapes.renderColorCube(new AABB((double)(x + 7), (double)y, (double)(z + 9), (double)x + 9.1, (double)y + 0.1, (double)z + 9.1), ColorUtils.BLUE, (VertexConsumer)B, M);
                RenderShapes.renderColorCube(new AABB((double)(x + 7), (double)y, (double)(z + 7), (double)x + 7.1, (double)y + 0.1, (double)z + 9.1), ColorUtils.BLUE, (VertexConsumer)B, M);
                RenderShapes.renderColorCube(new AABB((double)(x + 9), (double)y, (double)(z + 7), (double)x + 9.1, (double)y + 0.1, (double)z + 9.1), ColorUtils.BLUE, (VertexConsumer)B, M);
                RenderShapes.renderColorCube(new AABB((double)(x + 7), (double)y, (double)(z + 7), (double)x + 9.1, (double)y + 0.1, (double)z + 7.1), ColorUtils.BLUE, (VertexConsumer)B, M);
                y += 16;
            }
            if (entry.getIntValue() <= 0) continue;
            higher.add(chunk);
            number.add(entry.getIntValue());
        }
    }
}

