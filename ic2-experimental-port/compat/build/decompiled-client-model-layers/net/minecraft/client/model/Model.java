/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.function.Function;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public abstract class Model {
    protected final Function<ResourceLocation, RenderType> f_103106_;

    public Model(Function<ResourceLocation, RenderType> p_103110_) {
        this.f_103106_ = p_103110_;
    }

    public final RenderType m_103119_(ResourceLocation p_103120_) {
        return this.f_103106_.apply(p_103120_);
    }

    public abstract void m_7695_(PoseStack var1, VertexConsumer var2, int var3, int var4, float var5, float var6, float var7, float var8);
}

