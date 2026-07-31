/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 */
package net.minecraft.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

public class MapRenderer
implements AutoCloseable {
    private static final ResourceLocation f_93253_ = new ResourceLocation("textures/map/map_icons.png");
    static final RenderType f_93254_ = RenderType.m_110497_(f_93253_);
    private static final int f_168763_ = 128;
    private static final int f_168764_ = 128;
    final TextureManager f_93255_;
    private final Int2ObjectMap<MapInstance> f_93256_ = new Int2ObjectOpenHashMap();

    public MapRenderer(TextureManager p_93259_) {
        this.f_93255_ = p_93259_;
    }

    public void m_168765_(int p_168766_, MapItemSavedData p_168767_) {
        this.m_168778_(p_168766_, p_168767_).m_182566_();
    }

    public void m_168771_(PoseStack p_168772_, MultiBufferSource p_168773_, int p_168774_, MapItemSavedData p_168775_, boolean p_168776_, int p_168777_) {
        this.m_168778_(p_168774_, p_168775_).m_93291_(p_168772_, p_168773_, p_168776_, p_168777_);
    }

    private MapInstance m_168778_(int p_168779_, MapItemSavedData p_168780_) {
        return (MapInstance)this.f_93256_.compute(p_168779_, (p_182563_, p_182564_) -> {
            if (p_182564_ == null) {
                return new MapInstance((int)p_182563_, p_168780_);
            }
            p_182564_.m_182567_(p_168780_);
            return p_182564_;
        });
    }

    public void m_93260_() {
        for (MapInstance $$0 : this.f_93256_.values()) {
            $$0.close();
        }
        this.f_93256_.clear();
    }

    @Override
    public void close() {
        this.m_93260_();
    }

    class MapInstance
    implements AutoCloseable {
        private MapItemSavedData f_93280_;
        private final DynamicTexture f_93281_;
        private final RenderType f_93282_;
        private boolean f_182565_ = true;

        MapInstance(int p_168783_, MapItemSavedData p_168784_) {
            this.f_93280_ = p_168784_;
            this.f_93281_ = new DynamicTexture(128, 128, true);
            ResourceLocation $$2 = MapRenderer.this.f_93255_.m_118490_("map/" + p_168783_, this.f_93281_);
            this.f_93282_ = RenderType.m_110497_($$2);
        }

        void m_182567_(MapItemSavedData p_182568_) {
            boolean $$1 = this.f_93280_ != p_182568_;
            this.f_93280_ = p_182568_;
            this.f_182565_ |= $$1;
        }

        public void m_182566_() {
            this.f_182565_ = true;
        }

        private void m_93290_() {
            for (int $$0 = 0; $$0 < 128; ++$$0) {
                for (int $$1 = 0; $$1 < 128; ++$$1) {
                    int $$2 = $$1 + $$0 * 128;
                    this.f_93281_.m_117991_().m_84988_($$1, $$0, MaterialColor.m_192923_(this.f_93280_.f_77891_[$$2]));
                }
            }
            this.f_93281_.m_117985_();
        }

        void m_93291_(PoseStack p_93292_, MultiBufferSource p_93293_, boolean p_93294_, int p_93295_) {
            if (this.f_182565_) {
                this.m_93290_();
                this.f_182565_ = false;
            }
            boolean $$4 = false;
            boolean $$5 = false;
            float $$6 = 0.0f;
            Matrix4f $$7 = p_93292_.m_85850_().m_85861_();
            VertexConsumer $$8 = p_93293_.m_6299_(this.f_93282_);
            $$8.m_85982_($$7, 0.0f, 128.0f, -0.01f).m_6122_(255, 255, 255, 255).m_7421_(0.0f, 1.0f).m_85969_(p_93295_).m_5752_();
            $$8.m_85982_($$7, 128.0f, 128.0f, -0.01f).m_6122_(255, 255, 255, 255).m_7421_(1.0f, 1.0f).m_85969_(p_93295_).m_5752_();
            $$8.m_85982_($$7, 128.0f, 0.0f, -0.01f).m_6122_(255, 255, 255, 255).m_7421_(1.0f, 0.0f).m_85969_(p_93295_).m_5752_();
            $$8.m_85982_($$7, 0.0f, 0.0f, -0.01f).m_6122_(255, 255, 255, 255).m_7421_(0.0f, 0.0f).m_85969_(p_93295_).m_5752_();
            int $$9 = 0;
            for (MapDecoration $$10 : this.f_93280_.m_164811_()) {
                if (p_93294_ && !$$10.m_77809_()) continue;
                p_93292_.m_85836_();
                p_93292_.m_85837_(0.0f + (float)$$10.m_77804_() / 2.0f + 64.0f, 0.0f + (float)$$10.m_77805_() / 2.0f + 64.0f, -0.02f);
                p_93292_.m_85845_(Vector3f.f_122227_.m_122240_((float)($$10.m_77806_() * 360) / 16.0f));
                p_93292_.m_85841_(4.0f, 4.0f, 3.0f);
                p_93292_.m_85837_(-0.125, 0.125, 0.0);
                byte $$11 = $$10.m_77802_();
                float $$12 = (float)($$11 % 16 + 0) / 16.0f;
                float $$13 = (float)($$11 / 16 + 0) / 16.0f;
                float $$14 = (float)($$11 % 16 + 1) / 16.0f;
                float $$15 = (float)($$11 / 16 + 1) / 16.0f;
                Matrix4f $$16 = p_93292_.m_85850_().m_85861_();
                float $$17 = -0.001f;
                VertexConsumer $$18 = p_93293_.m_6299_(f_93254_);
                $$18.m_85982_($$16, -1.0f, 1.0f, (float)$$9 * -0.001f).m_6122_(255, 255, 255, 255).m_7421_($$12, $$13).m_85969_(p_93295_).m_5752_();
                $$18.m_85982_($$16, 1.0f, 1.0f, (float)$$9 * -0.001f).m_6122_(255, 255, 255, 255).m_7421_($$14, $$13).m_85969_(p_93295_).m_5752_();
                $$18.m_85982_($$16, 1.0f, -1.0f, (float)$$9 * -0.001f).m_6122_(255, 255, 255, 255).m_7421_($$14, $$15).m_85969_(p_93295_).m_5752_();
                $$18.m_85982_($$16, -1.0f, -1.0f, (float)$$9 * -0.001f).m_6122_(255, 255, 255, 255).m_7421_($$12, $$15).m_85969_(p_93295_).m_5752_();
                p_93292_.m_85849_();
                if ($$10.m_77810_() != null) {
                    Font $$19 = Minecraft.m_91087_().f_91062_;
                    Component $$20 = $$10.m_77810_();
                    float $$21 = $$19.m_92852_($$20);
                    float f = 25.0f / $$21;
                    Objects.requireNonNull($$19);
                    float $$22 = Mth.m_14036_(f, 0.0f, 6.0f / 9.0f);
                    p_93292_.m_85836_();
                    p_93292_.m_85837_(0.0f + (float)$$10.m_77804_() / 2.0f + 64.0f - $$21 * $$22 / 2.0f, 0.0f + (float)$$10.m_77805_() / 2.0f + 64.0f + 4.0f, -0.025f);
                    p_93292_.m_85841_($$22, $$22, 1.0f);
                    p_93292_.m_85837_(0.0, 0.0, -0.1f);
                    $$19.m_92841_($$20, 0.0f, 0.0f, -1, false, p_93292_.m_85850_().m_85861_(), p_93293_, false, Integer.MIN_VALUE, p_93295_);
                    p_93292_.m_85849_();
                }
                ++$$9;
            }
        }

        @Override
        public void close() {
            this.f_93281_.close();
        }
    }
}

