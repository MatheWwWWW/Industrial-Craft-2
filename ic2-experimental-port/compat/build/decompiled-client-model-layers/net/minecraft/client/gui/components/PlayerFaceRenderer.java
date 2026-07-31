/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;

public class PlayerFaceRenderer {
    public static final int f_238683_ = 8;
    public static final int f_238628_ = 8;
    public static final int f_238605_ = 8;
    public static final int f_238606_ = 8;
    public static final int f_238559_ = 40;
    public static final int f_238759_ = 8;
    public static final int f_238692_ = 8;
    public static final int f_238562_ = 8;
    public static final int f_238699_ = 64;
    public static final int f_238780_ = 64;

    public static void m_240071_(PoseStack p_240072_, int p_240073_, int p_240074_, int p_240075_) {
        PlayerFaceRenderer.m_240132_(p_240072_, p_240073_, p_240074_, p_240075_, true, false);
    }

    public static void m_240132_(PoseStack p_240133_, int p_240134_, int p_240135_, int p_240136_, boolean p_240137_, boolean p_240138_) {
        int $$6 = 8 + (p_240138_ ? 8 : 0);
        int $$7 = 8 * (p_240138_ ? -1 : 1);
        GuiComponent.m_93160_(p_240133_, p_240134_, p_240135_, p_240136_, p_240136_, 8.0f, $$6, 8, $$7, 64, 64);
        if (p_240137_) {
            PlayerFaceRenderer.m_240213_(p_240133_, p_240134_, p_240135_, p_240136_, p_240138_);
        }
    }

    private static void m_240213_(PoseStack p_240214_, int p_240215_, int p_240216_, int p_240217_, boolean p_240218_) {
        int $$5 = 8 + (p_240218_ ? 8 : 0);
        int $$6 = 8 * (p_240218_ ? -1 : 1);
        RenderSystem.m_69478_();
        GuiComponent.m_93160_(p_240214_, p_240215_, p_240216_, p_240217_, p_240217_, 40.0f, $$5, 8, $$6, 64, 64);
        RenderSystem.m_69461_();
    }
}

