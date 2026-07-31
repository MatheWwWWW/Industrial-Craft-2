/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.resources.model;

import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Objects;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;

public class Material {
    private final ResourceLocation f_119187_;
    private final ResourceLocation f_119188_;
    @Nullable
    private RenderType f_119189_;

    public Material(ResourceLocation p_119191_, ResourceLocation p_119192_) {
        this.f_119187_ = p_119191_;
        this.f_119188_ = p_119192_;
    }

    public ResourceLocation m_119193_() {
        return this.f_119187_;
    }

    public ResourceLocation m_119203_() {
        return this.f_119188_;
    }

    public TextureAtlasSprite m_119204_() {
        return Minecraft.m_91087_().m_91258_(this.m_119193_()).apply(this.m_119203_());
    }

    public RenderType m_119201_(Function<ResourceLocation, RenderType> p_119202_) {
        if (this.f_119189_ == null) {
            this.f_119189_ = p_119202_.apply(this.f_119187_);
        }
        return this.f_119189_;
    }

    public VertexConsumer m_119194_(MultiBufferSource p_119195_, Function<ResourceLocation, RenderType> p_119196_) {
        return this.m_119204_().m_118381_(p_119195_.m_6299_(this.m_119201_(p_119196_)));
    }

    public VertexConsumer m_119197_(MultiBufferSource p_119198_, Function<ResourceLocation, RenderType> p_119199_, boolean p_119200_) {
        return this.m_119204_().m_118381_(ItemRenderer.m_115222_(p_119198_, this.m_119201_(p_119199_), true, p_119200_));
    }

    public boolean equals(Object p_119206_) {
        if (this == p_119206_) {
            return true;
        }
        if (p_119206_ == null || this.getClass() != p_119206_.getClass()) {
            return false;
        }
        Material $$1 = (Material)p_119206_;
        return this.f_119187_.equals($$1.f_119187_) && this.f_119188_.equals($$1.f_119188_);
    }

    public int hashCode() {
        return Objects.hash(this.f_119187_, this.f_119188_);
    }

    public String toString() {
        return "Material{atlasLocation=" + this.f_119187_ + ", texture=" + this.f_119188_ + "}";
    }
}

