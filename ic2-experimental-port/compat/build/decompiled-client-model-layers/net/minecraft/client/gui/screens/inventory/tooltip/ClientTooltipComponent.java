/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screens.inventory.tooltip;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientBundleTooltip;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTextTooltip;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.inventory.tooltip.BundleTooltip;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

public interface ClientTooltipComponent {
    public static ClientTooltipComponent m_169948_(FormattedCharSequence p_169949_) {
        return new ClientTextTooltip(p_169949_);
    }

    public static ClientTooltipComponent m_169950_(TooltipComponent p_169951_) {
        if (p_169951_ instanceof BundleTooltip) {
            return new ClientBundleTooltip((BundleTooltip)p_169951_);
        }
        throw new IllegalArgumentException("Unknown TooltipComponent");
    }

    public int m_142103_();

    public int m_142069_(Font var1);

    default public void m_142440_(Font p_169953_, int p_169954_, int p_169955_, Matrix4f p_169956_, MultiBufferSource.BufferSource p_169957_) {
    }

    default public void m_183452_(Font p_194048_, int p_194049_, int p_194050_, PoseStack p_194051_, ItemRenderer p_194052_, int p_194053_) {
    }
}

