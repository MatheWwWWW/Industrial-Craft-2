/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screens.inventory.tooltip;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.BundleTooltip;
import net.minecraft.world.item.ItemStack;

public class ClientBundleTooltip
implements ClientTooltipComponent {
    public static final ResourceLocation f_169863_ = new ResourceLocation("textures/gui/container/bundle.png");
    private static final int f_169864_ = 4;
    private static final int f_169865_ = 1;
    private static final int f_169866_ = 128;
    private static final int f_169867_ = 18;
    private static final int f_169868_ = 20;
    private final NonNullList<ItemStack> f_169869_;
    private final int f_169870_;

    public ClientBundleTooltip(BundleTooltip p_169873_) {
        this.f_169869_ = p_169873_.m_150679_();
        this.f_169870_ = p_169873_.m_150680_();
    }

    @Override
    public int m_142103_() {
        return this.m_169911_() * 20 + 2 + 4;
    }

    @Override
    public int m_142069_(Font p_169901_) {
        return this.m_169910_() * 18 + 2;
    }

    @Override
    public void m_183452_(Font p_194042_, int p_194043_, int p_194044_, PoseStack p_194045_, ItemRenderer p_194046_, int p_194047_) {
        int $$6 = this.m_169910_();
        int $$7 = this.m_169911_();
        boolean $$8 = this.f_169870_ >= 64;
        int $$9 = 0;
        for (int $$10 = 0; $$10 < $$7; ++$$10) {
            for (int $$11 = 0; $$11 < $$6; ++$$11) {
                int $$12 = p_194043_ + $$11 * 18 + 1;
                int $$13 = p_194044_ + $$10 * 20 + 1;
                this.m_194026_($$12, $$13, $$9++, $$8, p_194042_, p_194045_, p_194046_, p_194047_);
            }
        }
        this.m_194019_(p_194043_, p_194044_, $$6, $$7, p_194045_, p_194047_);
    }

    private void m_194026_(int p_194027_, int p_194028_, int p_194029_, boolean p_194030_, Font p_194031_, PoseStack p_194032_, ItemRenderer p_194033_, int p_194034_) {
        if (p_194029_ >= this.f_169869_.size()) {
            this.m_194035_(p_194032_, p_194027_, p_194028_, p_194034_, p_194030_ ? Texture.BLOCKED_SLOT : Texture.SLOT);
            return;
        }
        ItemStack $$8 = this.f_169869_.get(p_194029_);
        this.m_194035_(p_194032_, p_194027_, p_194028_, p_194034_, Texture.SLOT);
        p_194033_.m_174253_($$8, p_194027_ + 1, p_194028_ + 1, p_194029_);
        p_194033_.m_115169_(p_194031_, $$8, p_194027_ + 1, p_194028_ + 1);
        if (p_194029_ == 0) {
            AbstractContainerScreen.m_169606_(p_194032_, p_194027_ + 1, p_194028_ + 1, p_194034_);
        }
    }

    private void m_194019_(int p_194020_, int p_194021_, int p_194022_, int p_194023_, PoseStack p_194024_, int p_194025_) {
        this.m_194035_(p_194024_, p_194020_, p_194021_, p_194025_, Texture.BORDER_CORNER_TOP);
        this.m_194035_(p_194024_, p_194020_ + p_194022_ * 18 + 1, p_194021_, p_194025_, Texture.BORDER_CORNER_TOP);
        for (int $$6 = 0; $$6 < p_194022_; ++$$6) {
            this.m_194035_(p_194024_, p_194020_ + 1 + $$6 * 18, p_194021_, p_194025_, Texture.BORDER_HORIZONTAL_TOP);
            this.m_194035_(p_194024_, p_194020_ + 1 + $$6 * 18, p_194021_ + p_194023_ * 20, p_194025_, Texture.BORDER_HORIZONTAL_BOTTOM);
        }
        for (int $$7 = 0; $$7 < p_194023_; ++$$7) {
            this.m_194035_(p_194024_, p_194020_, p_194021_ + $$7 * 20 + 1, p_194025_, Texture.BORDER_VERTICAL);
            this.m_194035_(p_194024_, p_194020_ + p_194022_ * 18 + 1, p_194021_ + $$7 * 20 + 1, p_194025_, Texture.BORDER_VERTICAL);
        }
        this.m_194035_(p_194024_, p_194020_, p_194021_ + p_194023_ * 20, p_194025_, Texture.BORDER_CORNER_BOTTOM);
        this.m_194035_(p_194024_, p_194020_ + p_194022_ * 18 + 1, p_194021_ + p_194023_ * 20, p_194025_, Texture.BORDER_CORNER_BOTTOM);
    }

    private void m_194035_(PoseStack p_194036_, int p_194037_, int p_194038_, int p_194039_, Texture p_194040_) {
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.m_157456_(0, f_169863_);
        GuiComponent.m_93143_(p_194036_, p_194037_, p_194038_, p_194039_, p_194040_.f_169919_, p_194040_.f_169920_, p_194040_.f_169921_, p_194040_.f_169922_, 128, 128);
    }

    private int m_169910_() {
        return Math.max(2, (int)Math.ceil(Math.sqrt((double)this.f_169869_.size() + 1.0)));
    }

    private int m_169911_() {
        return (int)Math.ceil(((double)this.f_169869_.size() + 1.0) / (double)this.m_169910_());
    }

    static final class Texture
    extends Enum<Texture> {
        public static final /* enum */ Texture SLOT = new Texture(0, 0, 18, 20);
        public static final /* enum */ Texture BLOCKED_SLOT = new Texture(0, 40, 18, 20);
        public static final /* enum */ Texture BORDER_VERTICAL = new Texture(0, 18, 1, 20);
        public static final /* enum */ Texture BORDER_HORIZONTAL_TOP = new Texture(0, 20, 18, 1);
        public static final /* enum */ Texture BORDER_HORIZONTAL_BOTTOM = new Texture(0, 60, 18, 1);
        public static final /* enum */ Texture BORDER_CORNER_TOP = new Texture(0, 20, 1, 1);
        public static final /* enum */ Texture BORDER_CORNER_BOTTOM = new Texture(0, 60, 1, 1);
        public final int f_169919_;
        public final int f_169920_;
        public final int f_169921_;
        public final int f_169922_;
        private static final /* synthetic */ Texture[] $VALUES;

        public static Texture[] values() {
            return (Texture[])$VALUES.clone();
        }

        public static Texture valueOf(String p_169934_) {
            return Enum.valueOf(Texture.class, p_169934_);
        }

        private Texture(int p_169928_, int p_169929_, int p_169930_, int p_169931_) {
            this.f_169919_ = p_169928_;
            this.f_169920_ = p_169929_;
            this.f_169921_ = p_169930_;
            this.f_169922_ = p_169931_;
        }

        private static /* synthetic */ Texture[] m_169932_() {
            return new Texture[]{SLOT, BLOCKED_SLOT, BORDER_VERTICAL, BORDER_HORIZONTAL_TOP, BORDER_HORIZONTAL_BOTTOM, BORDER_CORNER_TOP, BORDER_CORNER_BOTTOM};
        }

        static {
            $VALUES = Texture.m_169932_();
        }
    }
}

