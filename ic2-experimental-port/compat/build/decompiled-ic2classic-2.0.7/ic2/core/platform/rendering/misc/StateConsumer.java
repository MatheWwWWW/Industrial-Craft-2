/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  it.unimi.dsi.fastutil.floats.FloatList
 */
package ic2.core.platform.rendering.misc;

import com.mojang.blaze3d.vertex.VertexConsumer;
import it.unimi.dsi.fastutil.floats.FloatList;

public class StateConsumer
implements VertexConsumer {
    FloatList values;

    public StateConsumer(FloatList values) {
        this.values = values;
    }

    public VertexConsumer m_5483_(double x, double y, double z) {
        this.values.add((float)x);
        this.values.add((float)y);
        this.values.add((float)z);
        return this;
    }

    public VertexConsumer m_6122_(int r, int g, int b, int a) {
        this.values.add((float)(r & 0xFF) / 255.0f);
        this.values.add((float)(g & 0xFF) / 255.0f);
        this.values.add((float)(b & 0xFF) / 255.0f);
        this.values.add((float)(a & 0xFF) / 255.0f);
        return this;
    }

    public VertexConsumer m_7421_(float u, float v) {
        this.values.add(u);
        this.values.add(v);
        return this;
    }

    public VertexConsumer m_7122_(int p_225585_1_, int p_225585_2_) {
        return this;
    }

    public VertexConsumer m_7120_(int p_225587_1_, int p_225587_2_) {
        return this;
    }

    public VertexConsumer m_5601_(float x, float y, float z) {
        this.values.add(x);
        this.values.add(y);
        this.values.add(z);
        return this;
    }

    public void m_5752_() {
    }

    public void m_7404_(int r, int g, int b, int a) {
    }

    public void m_141991_() {
    }
}

