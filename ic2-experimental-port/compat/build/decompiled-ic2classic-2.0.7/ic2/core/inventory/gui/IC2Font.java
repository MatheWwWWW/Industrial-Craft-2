/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.math.Matrix4f
 *  net.minecraft.client.StringSplitter
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.util.FormattedCharSequence
 */
package ic2.core.inventory.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import java.util.List;
import net.minecraft.client.StringSplitter;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.FormattedCharSequence;

public class IC2Font
extends Font {
    Font font;

    public IC2Font(Font font) {
        super(null, false);
        this.font = font;
    }

    public int m_92750_(PoseStack p_238405_1_, String p_238405_2_, float p_238405_3_, float p_238405_4_, int p_238405_5_) {
        return this.font.m_92883_(p_238405_1_, p_238405_2_, p_238405_3_, p_238405_4_, p_238405_5_);
    }

    public int m_92756_(PoseStack p_238406_1_, String p_238406_2_, float p_238406_3_, float p_238406_4_, int p_238406_5_, boolean p_238406_6_) {
        return this.font.m_92883_(p_238406_1_, p_238406_2_, p_238406_3_, p_238406_4_, p_238406_5_);
    }

    public int m_92883_(PoseStack p_238421_1_, String p_238421_2_, float p_238421_3_, float p_238421_4_, int p_238421_5_) {
        return this.font.m_92883_(p_238421_1_, p_238421_2_, p_238421_3_, p_238421_4_, p_238421_5_);
    }

    public int m_92744_(PoseStack p_238407_1_, FormattedCharSequence p_238407_2_, float p_238407_3_, float p_238407_4_, int p_238407_5_) {
        return this.font.m_92877_(p_238407_1_, p_238407_2_, p_238407_3_, p_238407_4_, p_238407_5_);
    }

    public int m_92763_(PoseStack p_243246_1_, Component p_243246_2_, float p_243246_3_, float p_243246_4_, int p_243246_5_) {
        return this.font.m_92889_(p_243246_1_, p_243246_2_, p_243246_3_, p_243246_4_, p_243246_5_);
    }

    public int m_92877_(PoseStack p_238422_1_, FormattedCharSequence p_238422_2_, float p_238422_3_, float p_238422_4_, int p_238422_5_) {
        return this.font.m_92877_(p_238422_1_, p_238422_2_, p_238422_3_, p_238422_4_, p_238422_5_);
    }

    public int m_92889_(PoseStack p_243248_1_, Component p_243248_2_, float p_243248_3_, float p_243248_4_, int p_243248_5_) {
        return this.font.m_92889_(p_243248_1_, p_243248_2_, p_243248_3_, p_243248_4_, p_243248_5_);
    }

    public String m_92801_(String p_147647_1_) {
        return this.font.m_92801_(p_147647_1_);
    }

    public int m_92811_(String p_228079_1_, float p_228079_2_, float p_228079_3_, int p_228079_4_, boolean p_228079_5_, Matrix4f p_228079_6_, MultiBufferSource p_228079_7_, boolean p_228079_8_, int p_228079_9_, int p_228079_10_) {
        return this.font.m_92811_(p_228079_1_, p_228079_2_, p_228079_3_, p_228079_4_, p_228079_5_, p_228079_6_, p_228079_7_, p_228079_8_, p_228079_9_, p_228079_10_);
    }

    public int m_92822_(String p_238411_1_, float p_238411_2_, float p_238411_3_, int p_238411_4_, boolean p_238411_5_, Matrix4f p_238411_6_, MultiBufferSource p_238411_7_, boolean p_238411_8_, int p_238411_9_, int p_238411_10_, boolean p_238411_11_) {
        return this.font.m_92822_(p_238411_1_, p_238411_2_, p_238411_3_, p_238411_4_, p_238411_5_, p_238411_6_, p_238411_7_, p_238411_8_, p_238411_9_, p_238411_10_, p_238411_11_);
    }

    public int m_92841_(Component p_243247_1_, float p_243247_2_, float p_243247_3_, int p_243247_4_, boolean p_243247_5_, Matrix4f p_243247_6_, MultiBufferSource p_243247_7_, boolean p_243247_8_, int p_243247_9_, int p_243247_10_) {
        return this.font.m_92841_(p_243247_1_, p_243247_2_, p_243247_3_, p_243247_4_, p_243247_5_, p_243247_6_, p_243247_7_, p_243247_8_, p_243247_9_, p_243247_10_);
    }

    public int m_92733_(FormattedCharSequence p_238416_1_, float p_238416_2_, float p_238416_3_, int p_238416_4_, boolean p_238416_5_, Matrix4f p_238416_6_, MultiBufferSource p_238416_7_, boolean p_238416_8_, int p_238416_9_, int p_238416_10_) {
        return this.font.m_92733_(p_238416_1_, p_238416_2_, p_238416_3_, p_238416_4_, p_238416_5_, p_238416_6_, p_238416_7_, p_238416_8_, p_238416_9_, p_238416_10_);
    }

    public int m_92895_(String p_78256_1_) {
        return this.font.m_92895_(p_78256_1_);
    }

    public int m_92852_(FormattedText p_238414_1_) {
        return this.font.m_92852_(p_238414_1_);
    }

    public int m_92724_(FormattedCharSequence p_243245_1_) {
        return this.font.m_92724_(p_243245_1_);
    }

    public String m_92837_(String p_238413_1_, int p_238413_2_, boolean p_238413_3_) {
        return this.font.m_92837_(p_238413_1_, p_238413_2_, p_238413_3_);
    }

    public String m_92834_(String p_238412_1_, int p_238412_2_) {
        return this.font.m_92834_(p_238412_1_, p_238412_2_);
    }

    public FormattedText m_92854_(FormattedText p_238417_1_, int p_238417_2_) {
        return this.font.m_92854_(p_238417_1_, p_238417_2_);
    }

    public void m_92857_(FormattedText p_238418_1_, int p_238418_2_, int p_238418_3_, int p_238418_4_, int p_238418_5_) {
        this.font.m_92857_(p_238418_1_, p_238418_2_, p_238418_3_, p_238418_4_, p_238418_5_);
    }

    public int m_92920_(String p_78267_1_, int p_78267_2_) {
        return this.font.m_92920_(p_78267_1_, p_78267_2_);
    }

    public List<FormattedCharSequence> m_92923_(FormattedText p_238425_1_, int p_238425_2_) {
        return this.font.m_92923_(p_238425_1_, p_238425_2_);
    }

    public boolean m_92718_() {
        return this.font.m_92718_();
    }

    public StringSplitter m_92865_() {
        return this.font.m_92865_();
    }
}

