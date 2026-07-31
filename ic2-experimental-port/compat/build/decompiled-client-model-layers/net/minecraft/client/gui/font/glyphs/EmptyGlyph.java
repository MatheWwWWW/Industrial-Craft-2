/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.font.glyphs;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class EmptyGlyph
extends BakedGlyph {
    public static final EmptyGlyph f_232594_ = new EmptyGlyph();

    public EmptyGlyph() {
        super(RenderType.m_110497_(new ResourceLocation("")), RenderType.m_110500_(new ResourceLocation("")), RenderType.m_181444_(new ResourceLocation("")), 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
    }

    @Override
    public void m_5626_(boolean p_95278_, float p_95279_, float p_95280_, Matrix4f p_95281_, VertexConsumer p_95282_, float p_95283_, float p_95284_, float p_95285_, float p_95286_, int p_95287_) {
    }
}

