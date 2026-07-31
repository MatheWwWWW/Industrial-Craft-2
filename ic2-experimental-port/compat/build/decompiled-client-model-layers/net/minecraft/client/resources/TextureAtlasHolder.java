/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.resources;

import java.util.stream.Stream;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

public abstract class TextureAtlasHolder
extends SimplePreparableReloadListener<TextureAtlas.Preparations>
implements AutoCloseable {
    private final TextureAtlas f_118884_;
    private final String f_118885_;

    public TextureAtlasHolder(TextureManager p_118887_, ResourceLocation p_118888_, String p_118889_) {
        this.f_118885_ = p_118889_;
        this.f_118884_ = new TextureAtlas(p_118888_);
        p_118887_.m_118495_(this.f_118884_.m_118330_(), this.f_118884_);
    }

    protected abstract Stream<ResourceLocation> m_7535_();

    protected TextureAtlasSprite m_118901_(ResourceLocation p_118902_) {
        return this.f_118884_.m_118316_(this.m_118906_(p_118902_));
    }

    private ResourceLocation m_118906_(ResourceLocation p_118907_) {
        return new ResourceLocation(p_118907_.m_135827_(), this.f_118885_ + "/" + p_118907_.m_135815_());
    }

    @Override
    protected TextureAtlas.Preparations m_5944_(ResourceManager p_118891_, ProfilerFiller p_118892_) {
        p_118892_.m_7242_();
        p_118892_.m_6180_("stitching");
        TextureAtlas.Preparations $$2 = this.f_118884_.m_118307_(p_118891_, this.m_7535_().map(this::m_118906_), p_118892_, 0);
        p_118892_.m_7238_();
        p_118892_.m_7241_();
        return $$2;
    }

    @Override
    protected void m_5787_(TextureAtlas.Preparations p_118894_, ResourceManager p_118895_, ProfilerFiller p_118896_) {
        p_118896_.m_7242_();
        p_118896_.m_6180_("upload");
        this.f_118884_.m_118312_(p_118894_);
        p_118896_.m_7238_();
        p_118896_.m_7241_();
    }

    @Override
    public void close() {
        this.f_118884_.m_118329_();
    }

    @Override
    protected /* synthetic */ Object m_5944_(ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        return this.m_5944_(resourceManager, profilerFiller);
    }
}

