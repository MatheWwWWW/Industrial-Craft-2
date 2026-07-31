/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.realms;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.network.chat.Component;

public class RealmsLabel
implements Widget {
    private final Component f_120731_;
    private final int f_120732_;
    private final int f_120733_;
    private final int f_120734_;

    public RealmsLabel(Component p_120736_, int p_120737_, int p_120738_, int p_120739_) {
        this.f_120731_ = p_120736_;
        this.f_120732_ = p_120737_;
        this.f_120733_ = p_120738_;
        this.f_120734_ = p_120739_;
    }

    @Override
    public void m_6305_(PoseStack p_175036_, int p_175037_, int p_175038_, float p_175039_) {
        GuiComponent.m_93215_(p_175036_, Minecraft.m_91087_().f_91062_, this.f_120731_, this.f_120732_, this.f_120733_, this.f_120734_);
    }

    public Component m_175034_() {
        return this.f_120731_;
    }
}

