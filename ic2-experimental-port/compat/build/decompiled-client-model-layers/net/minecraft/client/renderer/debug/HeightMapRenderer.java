/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.debug;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Vector3f;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap;

public class HeightMapRenderer
implements DebugRenderer.SimpleDebugRenderer {
    private final Minecraft f_113570_;
    private static final int f_173890_ = 2;
    private static final float f_173891_ = 0.09375f;

    public HeightMapRenderer(Minecraft p_113572_) {
        this.f_113570_ = p_113572_;
    }

    @Override
    public void m_7790_(PoseStack p_113576_, MultiBufferSource p_113577_, double p_113578_, double p_113579_, double p_113580_) {
        ClientLevel $$5 = this.f_113570_.f_91073_;
        RenderSystem.m_69461_();
        RenderSystem.m_69472_();
        RenderSystem.m_69482_();
        RenderSystem.m_157427_(GameRenderer::m_172811_);
        BlockPos $$6 = new BlockPos(p_113578_, 0.0, p_113580_);
        Tesselator $$7 = Tesselator.m_85913_();
        BufferBuilder $$8 = $$7.m_85915_();
        $$8.m_166779_(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.f_85815_);
        for (int $$9 = -2; $$9 <= 2; ++$$9) {
            for (int $$10 = -2; $$10 <= 2; ++$$10) {
                ChunkAccess $$11 = $$5.m_46865_($$6.m_7918_($$9 * 16, 0, $$10 * 16));
                for (Map.Entry<Heightmap.Types, Heightmap> $$12 : $$11.m_6890_()) {
                    Heightmap.Types $$13 = $$12.getKey();
                    ChunkPos $$14 = $$11.m_7697_();
                    Vector3f $$15 = this.m_113573_($$13);
                    for (int $$16 = 0; $$16 < 16; ++$$16) {
                        for (int $$17 = 0; $$17 < 16; ++$$17) {
                            int $$18 = SectionPos.m_175554_($$14.f_45578_, $$16);
                            int $$19 = SectionPos.m_175554_($$14.f_45579_, $$17);
                            float $$20 = (float)((double)((float)$$5.m_6924_($$13, $$18, $$19) + (float)$$13.ordinal() * 0.09375f) - p_113579_);
                            LevelRenderer.m_109556_($$8, (double)((float)$$18 + 0.25f) - p_113578_, $$20, (double)((float)$$19 + 0.25f) - p_113580_, (double)((float)$$18 + 0.75f) - p_113578_, $$20 + 0.09375f, (double)((float)$$19 + 0.75f) - p_113580_, $$15.m_122239_(), $$15.m_122260_(), $$15.m_122269_(), 1.0f);
                        }
                    }
                }
            }
        }
        $$7.m_85914_();
        RenderSystem.m_69493_();
    }

    private Vector3f m_113573_(Heightmap.Types p_113574_) {
        switch (p_113574_) {
            case WORLD_SURFACE_WG: {
                return new Vector3f(1.0f, 1.0f, 0.0f);
            }
            case OCEAN_FLOOR_WG: {
                return new Vector3f(1.0f, 0.0f, 1.0f);
            }
            case WORLD_SURFACE: {
                return new Vector3f(0.0f, 0.7f, 0.0f);
            }
            case OCEAN_FLOOR: {
                return new Vector3f(0.0f, 0.0f, 0.5f);
            }
            case MOTION_BLOCKING: {
                return new Vector3f(0.0f, 0.3f, 0.3f);
            }
            case MOTION_BLOCKING_NO_LEAVES: {
                return new Vector3f(0.0f, 0.5f, 0.5f);
            }
        }
        return new Vector3f(0.0f, 0.0f, 0.0f);
    }
}

