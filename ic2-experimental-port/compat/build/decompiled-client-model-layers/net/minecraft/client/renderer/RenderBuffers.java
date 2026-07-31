/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap
 */
package net.minecraft.client.renderer;

import com.mojang.blaze3d.vertex.BufferBuilder;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import java.util.SortedMap;
import net.minecraft.Util;
import net.minecraft.client.renderer.ChunkBufferBuilderPack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.OutlineBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.ModelBakery;

public class RenderBuffers {
    private final ChunkBufferBuilderPack f_110092_ = new ChunkBufferBuilderPack();
    private final SortedMap<RenderType, BufferBuilder> f_110093_ = (SortedMap)Util.m_137469_(new Object2ObjectLinkedOpenHashMap(), p_110100_ -> {
        p_110100_.put((Object)Sheets.m_110789_(), (Object)this.f_110092_.m_108839_(RenderType.m_110451_()));
        p_110100_.put((Object)Sheets.m_110790_(), (Object)this.f_110092_.m_108839_(RenderType.m_110463_()));
        p_110100_.put((Object)Sheets.m_110762_(), (Object)this.f_110092_.m_108839_(RenderType.m_110457_()));
        p_110100_.put((Object)Sheets.m_110792_(), (Object)this.f_110092_.m_108839_(RenderType.m_110466_()));
        RenderBuffers.m_110101_((Object2ObjectLinkedOpenHashMap<RenderType, BufferBuilder>)p_110100_, Sheets.m_110782_());
        RenderBuffers.m_110101_((Object2ObjectLinkedOpenHashMap<RenderType, BufferBuilder>)p_110100_, Sheets.m_110785_());
        RenderBuffers.m_110101_((Object2ObjectLinkedOpenHashMap<RenderType, BufferBuilder>)p_110100_, Sheets.m_110786_());
        RenderBuffers.m_110101_((Object2ObjectLinkedOpenHashMap<RenderType, BufferBuilder>)p_110100_, Sheets.m_110787_());
        RenderBuffers.m_110101_((Object2ObjectLinkedOpenHashMap<RenderType, BufferBuilder>)p_110100_, Sheets.m_110788_());
        RenderBuffers.m_110101_((Object2ObjectLinkedOpenHashMap<RenderType, BufferBuilder>)p_110100_, RenderType.m_110472_());
        RenderBuffers.m_110101_((Object2ObjectLinkedOpenHashMap<RenderType, BufferBuilder>)p_110100_, RenderType.m_110481_());
        RenderBuffers.m_110101_((Object2ObjectLinkedOpenHashMap<RenderType, BufferBuilder>)p_110100_, RenderType.m_110484_());
        RenderBuffers.m_110101_((Object2ObjectLinkedOpenHashMap<RenderType, BufferBuilder>)p_110100_, RenderType.m_110490_());
        RenderBuffers.m_110101_((Object2ObjectLinkedOpenHashMap<RenderType, BufferBuilder>)p_110100_, RenderType.m_110493_());
        RenderBuffers.m_110101_((Object2ObjectLinkedOpenHashMap<RenderType, BufferBuilder>)p_110100_, RenderType.m_110487_());
        RenderBuffers.m_110101_((Object2ObjectLinkedOpenHashMap<RenderType, BufferBuilder>)p_110100_, RenderType.m_110496_());
        RenderBuffers.m_110101_((Object2ObjectLinkedOpenHashMap<RenderType, BufferBuilder>)p_110100_, RenderType.m_110499_());
        RenderBuffers.m_110101_((Object2ObjectLinkedOpenHashMap<RenderType, BufferBuilder>)p_110100_, RenderType.m_110478_());
        ModelBakery.f_119229_.forEach(p_173062_ -> RenderBuffers.m_110101_((Object2ObjectLinkedOpenHashMap<RenderType, BufferBuilder>)p_110100_, p_173062_));
    });
    private final MultiBufferSource.BufferSource f_110094_ = MultiBufferSource.m_109900_(this.f_110093_, new BufferBuilder(256));
    private final MultiBufferSource.BufferSource f_110095_ = MultiBufferSource.m_109898_(new BufferBuilder(256));
    private final OutlineBufferSource f_110096_ = new OutlineBufferSource(this.f_110094_);

    private static void m_110101_(Object2ObjectLinkedOpenHashMap<RenderType, BufferBuilder> p_110102_, RenderType p_110103_) {
        p_110102_.put((Object)p_110103_, (Object)new BufferBuilder(p_110103_.m_110507_()));
    }

    public ChunkBufferBuilderPack m_110098_() {
        return this.f_110092_;
    }

    public MultiBufferSource.BufferSource m_110104_() {
        return this.f_110094_;
    }

    public MultiBufferSource.BufferSource m_110108_() {
        return this.f_110095_;
    }

    public OutlineBufferSource m_110109_() {
        return this.f_110096_;
    }
}

