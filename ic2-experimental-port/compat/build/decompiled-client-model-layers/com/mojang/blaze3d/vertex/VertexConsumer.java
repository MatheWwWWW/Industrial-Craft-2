/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.system.MemoryStack
 */
package com.mojang.blaze3d.vertex;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.core.Vec3i;
import net.minecraft.util.FastColor;
import org.lwjgl.system.MemoryStack;

public interface VertexConsumer {
    public VertexConsumer m_5483_(double var1, double var3, double var5);

    public VertexConsumer m_6122_(int var1, int var2, int var3, int var4);

    public VertexConsumer m_7421_(float var1, float var2);

    public VertexConsumer m_7122_(int var1, int var2);

    public VertexConsumer m_7120_(int var1, int var2);

    public VertexConsumer m_5601_(float var1, float var2, float var3);

    public void m_5752_();

    default public void m_5954_(float p_85955_, float p_85956_, float p_85957_, float p_85958_, float p_85959_, float p_85960_, float p_85961_, float p_85962_, float p_85963_, int p_85964_, int p_85965_, float p_85966_, float p_85967_, float p_85968_) {
        this.m_5483_(p_85955_, p_85956_, p_85957_);
        this.m_85950_(p_85958_, p_85959_, p_85960_, p_85961_);
        this.m_7421_(p_85962_, p_85963_);
        this.m_86008_(p_85964_);
        this.m_85969_(p_85965_);
        this.m_5601_(p_85966_, p_85967_, p_85968_);
        this.m_5752_();
    }

    public void m_7404_(int var1, int var2, int var3, int var4);

    public void m_141991_();

    default public VertexConsumer m_85950_(float p_85951_, float p_85952_, float p_85953_, float p_85954_) {
        return this.m_6122_((int)(p_85951_ * 255.0f), (int)(p_85952_ * 255.0f), (int)(p_85953_ * 255.0f), (int)(p_85954_ * 255.0f));
    }

    default public VertexConsumer m_193479_(int p_193480_) {
        return this.m_6122_(FastColor.ARGB32.m_13665_(p_193480_), FastColor.ARGB32.m_13667_(p_193480_), FastColor.ARGB32.m_13669_(p_193480_), FastColor.ARGB32.m_13655_(p_193480_));
    }

    default public VertexConsumer m_85969_(int p_85970_) {
        return this.m_7120_(p_85970_ & 0xFFFF, p_85970_ >> 16 & 0xFFFF);
    }

    default public VertexConsumer m_86008_(int p_86009_) {
        return this.m_7122_(p_86009_ & 0xFFFF, p_86009_ >> 16 & 0xFFFF);
    }

    default public void m_85987_(PoseStack.Pose p_85988_, BakedQuad p_85989_, float p_85990_, float p_85991_, float p_85992_, int p_85993_, int p_85994_) {
        this.m_85995_(p_85988_, p_85989_, new float[]{1.0f, 1.0f, 1.0f, 1.0f}, p_85990_, p_85991_, p_85992_, new int[]{p_85993_, p_85993_, p_85993_, p_85993_}, p_85994_, false);
    }

    default public void m_85995_(PoseStack.Pose p_85996_, BakedQuad p_85997_, float[] p_85998_, float p_85999_, float p_86000_, float p_86001_, int[] p_86002_, int p_86003_, boolean p_86004_) {
        float[] $$9 = new float[]{p_85998_[0], p_85998_[1], p_85998_[2], p_85998_[3]};
        int[] $$10 = new int[]{p_86002_[0], p_86002_[1], p_86002_[2], p_86002_[3]};
        int[] $$11 = p_85997_.m_111303_();
        Vec3i $$12 = p_85997_.m_111306_().m_122436_();
        Vector3f $$13 = new Vector3f($$12.m_123341_(), $$12.m_123342_(), $$12.m_123343_());
        Matrix4f $$14 = p_85996_.m_85861_();
        $$13.m_122249_(p_85996_.m_85864_());
        int $$15 = 8;
        int $$16 = $$11.length / 8;
        try (MemoryStack $$17 = MemoryStack.stackPush();){
            ByteBuffer $$18 = $$17.malloc(DefaultVertexFormat.f_85811_.m_86020_());
            IntBuffer $$19 = $$18.asIntBuffer();
            for (int $$20 = 0; $$20 < $$16; ++$$20) {
                float $$32;
                float $$31;
                float $$30;
                $$19.clear();
                $$19.put($$11, $$20 * 8, 8);
                float $$21 = $$18.getFloat(0);
                float $$22 = $$18.getFloat(4);
                float $$23 = $$18.getFloat(8);
                if (p_86004_) {
                    float $$24 = (float)($$18.get(12) & 0xFF) / 255.0f;
                    float $$25 = (float)($$18.get(13) & 0xFF) / 255.0f;
                    float $$26 = (float)($$18.get(14) & 0xFF) / 255.0f;
                    float $$27 = $$24 * $$9[$$20] * p_85999_;
                    float $$28 = $$25 * $$9[$$20] * p_86000_;
                    float $$29 = $$26 * $$9[$$20] * p_86001_;
                } else {
                    $$30 = $$9[$$20] * p_85999_;
                    $$31 = $$9[$$20] * p_86000_;
                    $$32 = $$9[$$20] * p_86001_;
                }
                int $$33 = $$10[$$20];
                float $$34 = $$18.getFloat(16);
                float $$35 = $$18.getFloat(20);
                Vector4f $$36 = new Vector4f($$21, $$22, $$23, 1.0f);
                $$36.m_123607_($$14);
                this.m_5954_($$36.m_123601_(), $$36.m_123615_(), $$36.m_123616_(), $$30, $$31, $$32, 1.0f, $$34, $$35, p_86003_, $$33, $$13.m_122239_(), $$13.m_122260_(), $$13.m_122269_());
            }
        }
    }

    default public VertexConsumer m_85982_(Matrix4f p_85983_, float p_85984_, float p_85985_, float p_85986_) {
        Vector4f $$4 = new Vector4f(p_85984_, p_85985_, p_85986_, 1.0f);
        $$4.m_123607_(p_85983_);
        return this.m_5483_($$4.m_123601_(), $$4.m_123615_(), $$4.m_123616_());
    }

    default public VertexConsumer m_85977_(Matrix3f p_85978_, float p_85979_, float p_85980_, float p_85981_) {
        Vector3f $$4 = new Vector3f(p_85979_, p_85980_, p_85981_);
        $$4.m_122249_(p_85978_);
        return this.m_5601_($$4.m_122239_(), $$4.m_122260_(), $$4.m_122269_());
    }
}

