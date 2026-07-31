/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack$Pose
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  net.minecraft.client.renderer.block.model.BakedQuad
 */
package ic2.core.platform.rendering.misc;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.block.model.BakedQuad;

public class ColoringVertexBuilder
implements VertexConsumer {
    VertexConsumer builder;
    int customAlpha;

    public ColoringVertexBuilder(VertexConsumer builder, int customAlpha) {
        this.builder = builder;
        this.customAlpha = customAlpha;
    }

    public VertexConsumer m_5483_(double p_225582_1_, double p_225582_3_, double p_225582_5_) {
        this.builder.m_5483_(p_225582_1_, p_225582_3_, p_225582_5_);
        return this;
    }

    public VertexConsumer m_6122_(int p_225586_1_, int p_225586_2_, int p_225586_3_, int p_225586_4_) {
        this.builder.m_6122_(p_225586_1_, p_225586_2_, p_225586_3_, this.customAlpha);
        return this;
    }

    public VertexConsumer m_7421_(float p_225583_1_, float p_225583_2_) {
        this.builder.m_7421_(p_225583_1_, p_225583_2_);
        return this;
    }

    public VertexConsumer m_7122_(int p_225585_1_, int p_225585_2_) {
        this.builder.m_7122_(p_225585_1_, p_225585_2_);
        return this;
    }

    public VertexConsumer m_7120_(int p_225587_1_, int p_225587_2_) {
        this.builder.m_7120_(p_225587_1_, p_225587_2_);
        return this;
    }

    public VertexConsumer m_5601_(float p_225584_1_, float p_225584_2_, float p_225584_3_) {
        this.builder.m_5601_(p_225584_1_, p_225584_2_, p_225584_3_);
        return this;
    }

    public void m_5752_() {
        this.builder.m_5752_();
    }

    public void m_85995_(PoseStack.Pose p_227890_1_, BakedQuad p_227890_2_, float[] p_227890_3_, float p_227890_4_, float p_227890_5_, float p_227890_6_, int[] p_227890_7_, int p_227890_8_, boolean p_227890_9_) {
        super.m_85995_(p_227890_1_, p_227890_2_, new float[]{1.0f, 1.0f, 1.0f, 1.0f}, p_227890_4_, p_227890_5_, p_227890_6_, p_227890_7_, p_227890_8_, p_227890_9_);
    }

    public void m_7404_(int r, int g, int b, int a) {
        this.builder.m_7404_(r, g, b, a);
    }

    public void m_141991_() {
        this.builder.m_141991_();
    }
}

