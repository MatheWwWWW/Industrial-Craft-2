/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.DefaultVertexFormat
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  com.mojang.blaze3d.vertex.VertexFormat$Mode
 *  it.unimi.dsi.fastutil.longs.LongList
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.client.renderer.culling.Frustum
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.AABB
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.event.RenderLevelStageEvent
 */
package ic2.core.block.rendering.world.impl;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import ic2.core.block.rendering.world.IWorldOverlay;
import ic2.core.networking.PacketManager;
import ic2.core.networking.packets.server.HighlightPacket;
import ic2.core.networking.packets.server.MultiHighlightPacket;
import ic2.core.platform.rendering.RenderShapes;
import ic2.core.platform.rendering.RenderUtils;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.longs.LongList;
import java.util.List;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLevelStageEvent;

@OnlyIn(value=Dist.CLIENT)
public class BlockHighlighter
implements IWorldOverlay {
    public static final BlockHighlighter INSTANCE = new BlockHighlighter();
    List<Highlighter> highlights = CollectionUtils.createList();

    public void addServerHighlight(Player player, BlockPos pos) {
        this.addServerHighlight(player, pos, 1200);
    }

    public void addServerHighlight(Player player, BlockPos pos, int ticks) {
        this.addServerHighlight(player, pos, ticks, -2130720768);
    }

    public void addServerHighlight(Player player, BlockPos pos, int ticks, int color) {
        this.addServerHighlight(player, pos, ticks, color, false);
    }

    public void addServerHighlight(Player player, BlockPos pos, int ticks, int color, boolean doubleSided) {
        HighlightPacket packet = new HighlightPacket(pos, ticks, color, doubleSided);
        if (player == null) {
            PacketManager.INSTANCE.sendToAllPlayers(packet);
            return;
        }
        PacketManager.INSTANCE.sendToPlayer(packet, player);
    }

    public void addServerHighlight(Player player, LongList list, int ticks, int color) {
        this.addServerHighlight(player, list, ticks, color, false);
    }

    public void addServerHighlight(Player player, LongList list, int ticks, int color, boolean doubleSided) {
        MultiHighlightPacket packet = new MultiHighlightPacket(list, ticks, color, doubleSided);
        if (player == null) {
            PacketManager.INSTANCE.sendToAllPlayers(packet);
            return;
        }
        PacketManager.INSTANCE.sendToPlayer(packet, player);
    }

    public void addHighlight(BlockPos pos) {
        this.highlights.add(new Highlighter(pos, 1200, -2130720768, false, null));
    }

    public void addHighlight(BlockPos pos, int ticks) {
        this.highlights.add(new Highlighter(pos, ticks, -2130720768, false, null));
    }

    public void addHighlight(BlockPos pos, int ticks, int color) {
        this.highlights.add(new Highlighter(pos, ticks, color, false, null));
    }

    public void addHighlight(BlockPos pos, int ticks, int color, boolean doubleSided) {
        this.highlights.add(new Highlighter(pos, ticks, color, doubleSided, null));
    }

    public void addHighlight(BlockPos pos, int ticks, int color, boolean doubleSided, IHighlightController controller) {
        this.highlights.add(new Highlighter(pos, ticks, color, doubleSided, controller));
    }

    @Override
    public void cleanup() {
        this.highlights.clear();
    }

    @Override
    public void update(Level world, Player player) {
        if (this.highlights.isEmpty()) {
            return;
        }
        this.highlights.removeIf(Highlighter::update);
    }

    @Override
    public void render(Level world, Player player, RenderLevelStageEvent event, Frustum helper) {
        if (this.highlights.isEmpty()) {
            return;
        }
        RenderSystem.m_69472_();
        RenderSystem.m_69465_();
        RenderSystem.m_69478_();
        RenderSystem.m_69453_();
        RenderSystem.m_157427_(GameRenderer::m_172811_);
        RenderUtils.draw(event.getPoseStack(), (B, M) -> {
            B.m_166779_(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.f_85815_);
            for (Highlighter entry : this.highlights) {
                if (!helper.m_113029_(entry.getRenderBox())) continue;
                if (entry.isDoubleSided()) {
                    RenderShapes.renderInvertedColorCube(entry.getRenderBox(), entry.getColor(), (VertexConsumer)B, M);
                    continue;
                }
                RenderShapes.renderColorCube(entry.getRenderBox(), entry.getColor(), (VertexConsumer)B, M);
            }
        }).m_85914_();
        RenderSystem.m_69493_();
        RenderSystem.m_69482_();
    }

    public static class Highlighter {
        public int ticksLeft;
        IHighlightController controller = null;
        public BlockPos pos;
        public AABB renderBox;
        public boolean doubleSided = false;
        public int color;

        public Highlighter(BlockPos pos, int ticks, int color, boolean doubleSided, IHighlightController controller) {
            this.ticksLeft = ticks;
            this.pos = pos;
            this.color = color;
            this.doubleSided = doubleSided;
            this.controller = controller;
            this.renderBox = new AABB(pos).m_82400_(0.1);
        }

        public boolean update() {
            --this.ticksLeft;
            return this.ticksLeft <= 0 || this.controller != null && !this.controller.keepAlive(this);
        }

        public boolean isDoubleSided() {
            return this.doubleSided;
        }

        public int getColor() {
            return this.color;
        }

        public AABB getRenderBox() {
            return this.renderBox;
        }
    }

    public static interface IHighlightController {
        public boolean keepAlive(Highlighter var1);
    }
}

