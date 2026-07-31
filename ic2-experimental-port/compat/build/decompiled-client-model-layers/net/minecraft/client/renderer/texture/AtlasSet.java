/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.texture;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;

public class AtlasSet
implements AutoCloseable {
    private final Map<ResourceLocation, TextureAtlas> f_117968_;

    public AtlasSet(Collection<TextureAtlas> p_117970_) {
        this.f_117968_ = p_117970_.stream().collect(Collectors.toMap(TextureAtlas::m_118330_, Function.identity()));
    }

    public TextureAtlas m_117973_(ResourceLocation p_117974_) {
        return this.f_117968_.get(p_117974_);
    }

    public TextureAtlasSprite m_117971_(Material p_117972_) {
        return this.f_117968_.get(p_117972_.m_119193_()).m_118316_(p_117972_.m_119203_());
    }

    @Override
    public void close() {
        this.f_117968_.values().forEach(TextureAtlas::m_118329_);
        this.f_117968_.clear();
    }
}

