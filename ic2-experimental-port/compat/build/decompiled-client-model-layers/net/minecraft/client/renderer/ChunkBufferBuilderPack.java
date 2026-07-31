/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer;

import com.mojang.blaze3d.vertex.BufferBuilder;
import java.util.Map;
import java.util.stream.Collectors;
import net.minecraft.client.renderer.RenderType;

public class ChunkBufferBuilderPack {
    private final Map<RenderType, BufferBuilder> f_108836_ = RenderType.m_110506_().stream().collect(Collectors.toMap(p_108845_ -> p_108845_, p_108843_ -> new BufferBuilder(p_108843_.m_110507_())));

    public BufferBuilder m_108839_(RenderType p_108840_) {
        return this.f_108836_.get(p_108840_);
    }

    public void m_108838_() {
        this.f_108836_.values().forEach(BufferBuilder::m_85729_);
    }

    public void m_108841_() {
        this.f_108836_.values().forEach(BufferBuilder::m_85730_);
    }
}

