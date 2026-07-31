/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableMap$Builder
 *  javax.annotation.Nullable
 */
package net.minecraft.client.renderer.debug;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientChunkCache;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.core.SectionPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;

public class ChunkDebugRenderer
implements DebugRenderer.SimpleDebugRenderer {
    final Minecraft f_113363_;
    private double f_113364_ = Double.MIN_VALUE;
    private final int f_113365_ = 12;
    @Nullable
    private ChunkData f_113366_;

    public ChunkDebugRenderer(Minecraft p_113368_) {
        this.f_113363_ = p_113368_;
    }

    @Override
    public void m_7790_(PoseStack p_113370_, MultiBufferSource p_113371_, double p_113372_, double p_113373_, double p_113374_) {
        double $$5 = Util.m_137569_();
        if ($$5 - this.f_113364_ > 3.0E9) {
            this.f_113364_ = $$5;
            IntegratedServer $$6 = this.f_113363_.m_91092_();
            this.f_113366_ = $$6 != null ? new ChunkData($$6, p_113372_, p_113374_) : null;
        }
        if (this.f_113366_ != null) {
            RenderSystem.m_69478_();
            RenderSystem.m_69453_();
            RenderSystem.m_69832_(2.0f);
            RenderSystem.m_69472_();
            RenderSystem.m_69458_(false);
            Map $$7 = this.f_113366_.f_113379_.getNow(null);
            double $$8 = this.f_113363_.f_91063_.m_109153_().m_90583_().f_82480_ * 0.85;
            for (Map.Entry<ChunkPos, String> $$9 : this.f_113366_.f_113378_.entrySet()) {
                ChunkPos $$10 = $$9.getKey();
                Object $$11 = $$9.getValue();
                if ($$7 != null) {
                    $$11 = (String)$$11 + (String)$$7.get($$10);
                }
                String[] $$12 = ((String)$$11).split("\n");
                int $$13 = 0;
                for (String $$14 : $$12) {
                    DebugRenderer.m_113483_($$14, SectionPos.m_175554_($$10.f_45578_, 8), $$8 + (double)$$13, SectionPos.m_175554_($$10.f_45579_, 8), -1, 0.15f);
                    $$13 -= 2;
                }
            }
            RenderSystem.m_69458_(true);
            RenderSystem.m_69493_();
            RenderSystem.m_69461_();
        }
    }

    final class ChunkData {
        final Map<ChunkPos, String> f_113378_;
        final CompletableFuture<Map<ChunkPos, String>> f_113379_;

        ChunkData(IntegratedServer p_113382_, double p_113383_, double p_113384_) {
            ClientLevel $$3 = ChunkDebugRenderer.this.f_113363_.f_91073_;
            ResourceKey<Level> $$4 = $$3.m_46472_();
            int $$5 = SectionPos.m_175552_(p_113383_);
            int $$6 = SectionPos.m_175552_(p_113384_);
            ImmutableMap.Builder $$7 = ImmutableMap.builder();
            ClientChunkCache $$8 = $$3.m_7726_();
            for (int $$9 = $$5 - 12; $$9 <= $$5 + 12; ++$$9) {
                for (int $$10 = $$6 - 12; $$10 <= $$6 + 12; ++$$10) {
                    ChunkPos $$11 = new ChunkPos($$9, $$10);
                    Object $$12 = "";
                    LevelChunk $$13 = $$8.m_62227_($$9, $$10, false);
                    $$12 = (String)$$12 + "Client: ";
                    if ($$13 == null) {
                        $$12 = (String)$$12 + "0n/a\n";
                    } else {
                        $$12 = (String)$$12 + ($$13.m_6430_() ? " E" : "");
                        $$12 = (String)$$12 + "\n";
                    }
                    $$7.put((Object)$$11, $$12);
                }
            }
            this.f_113378_ = $$7.build();
            this.f_113379_ = p_113382_.m_18691_(() -> {
                ServerLevel $$4 = p_113382_.m_129880_($$4);
                if ($$4 == null) {
                    return ImmutableMap.of();
                }
                ImmutableMap.Builder $$5 = ImmutableMap.builder();
                ServerChunkCache $$6 = $$4.m_7726_();
                for (int $$7 = $$5 - 12; $$7 <= $$5 + 12; ++$$7) {
                    for (int $$8 = $$6 - 12; $$8 <= $$6 + 12; ++$$8) {
                        ChunkPos $$9 = new ChunkPos($$7, $$8);
                        $$5.put((Object)$$9, (Object)("Server: " + $$6.m_8448_($$9)));
                    }
                }
                return $$5.build();
            });
        }
    }
}

