/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screens.inventory.tooltip;

import com.mojang.math.Matrix4f;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.FormattedCharSequence;

public class ClientTextTooltip
implements ClientTooltipComponent {
    private final FormattedCharSequence f_169936_;

    public ClientTextTooltip(FormattedCharSequence p_169938_) {
        this.f_169936_ = p_169938_;
    }

    @Override
    public int m_142069_(Font p_169941_) {
        return p_169941_.m_92724_(this.f_169936_);
    }

    @Override
    public int m_142103_() {
        return 10;
    }

    @Override
    public void m_142440_(Font p_169943_, int p_169944_, int p_169945_, Matrix4f p_169946_, MultiBufferSource.BufferSource p_169947_) {
        p_169943_.m_92733_(this.f_169936_, p_169944_, p_169945_, -1, true, p_169946_, p_169947_, false, 0, 0xF000F0);
    }
}

