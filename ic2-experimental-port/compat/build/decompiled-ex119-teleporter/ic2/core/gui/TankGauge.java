/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.level.material.Fluid
 */
package ic2.core.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.Ic2Gui;
import ic2.core.fluid.Ic2FluidStack;
import ic2.core.fluid.Ic2FluidTank;
import ic2.core.gui.GuiElement;
import ic2.core.init.Localization;
import ic2.core.proxy.SideProxyClient;
import ic2.core.util.Util;
import java.util.List;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.material.Fluid;

public class TankGauge
extends GuiElement<TankGauge> {
    public static final int filledBackgroundU = 6;
    public static final int filledScaleU = 38;
    public static final int emptyU = 70;
    public static final int v = 100;
    public static final int normalWidth = 20;
    public static final int normalHeight = 55;
    public static final int fluidOffsetX = 4;
    public static final int fluidOffsetY = 4;
    public static final int fluidNetWidth = 12;
    public static final int fluidNetHeight = 47;
    private final Ic2FluidTank tank;
    private final TankGuiStyle style;

    public static TankGauge createNormal(Ic2Gui<?> ic2Gui, int n, int n2, Ic2FluidTank ic2FluidTank) {
        return new TankGauge(ic2Gui, n, n2, 20, 55, ic2FluidTank, TankGuiStyle.Normal);
    }

    public static TankGauge createPlain(Ic2Gui<?> ic2Gui, int n, int n2, int n3, int n4, Ic2FluidTank ic2FluidTank) {
        return new TankGauge(ic2Gui, n, n2, n3, n4, ic2FluidTank, TankGuiStyle.Plain);
    }

    public static TankGauge createBorderless(Ic2Gui<?> ic2Gui, int n, int n2, Ic2FluidTank ic2FluidTank, boolean bl) {
        return new TankGauge(ic2Gui, n, n2, 12, 47, ic2FluidTank, bl ? TankGuiStyle.BorderlessMirrored : TankGuiStyle.Borderless);
    }

    private TankGauge(Ic2Gui<?> ic2Gui, int n, int n2, int n3, int n4, Ic2FluidTank ic2FluidTank, TankGuiStyle tankGuiStyle) {
        super(ic2Gui, n, n2, n3, n4);
        if (ic2FluidTank == null) {
            throw new NullPointerException("null tank");
        }
        this.tank = ic2FluidTank;
        this.style = tankGuiStyle;
    }

    @Override
    public void drawBackground(PoseStack poseStack, int n, int n2) {
        TankGauge.bindCommonTexture();
        Ic2FluidStack ic2FluidStack = this.tank.getFluidStack();
        if (ic2FluidStack == null || ic2FluidStack.isEmpty()) {
            if (this.style.withBorder) {
                this.gui.drawTexturedRect(poseStack, this.x, this.y, this.width, this.height, 70.0, 100.0, this.style.mirrorGauge);
            } else if (this.style.withGauge) {
                this.gui.drawTexturedRect(poseStack, this.x, this.y, this.width, this.height, 74.0, 104.0, this.style.mirrorGauge);
            }
        } else {
            if (this.style.withBorder) {
                this.gui.drawTexturedRect(poseStack, this.x, this.y, this.width, this.height, 6.0, 100.0);
            }
            int n3 = this.x;
            int n4 = this.y;
            int n5 = this.width;
            int n6 = this.height;
            if (this.style.withBorder) {
                n3 += 4;
                n4 += 4;
                n5 = 12;
                n6 = 47;
            }
            TextureAtlasSprite textureAtlasSprite = SideProxyClient.envProxy.getFluidStillSprite(ic2FluidStack);
            int n7 = SideProxyClient.envProxy.getFluidColor(ic2FluidStack);
            double d = (double)n6 * Util.limit((double)ic2FluidStack.getAmountMb() / (double)this.tank.getCapacity(), 0.0, 1.0);
            TankGauge.bindBlockTexture();
            this.gui.drawSprite(poseStack, n3, (double)(n4 + n6) - d, n5, d, textureAtlasSprite, n7, 1.0, false, true);
            if (this.style.withGauge) {
                TankGauge.bindCommonTexture();
                int n8 = this.x;
                int n9 = this.y;
                if (!this.style.withBorder) {
                    n8 -= 4;
                    n9 -= 4;
                }
                this.gui.drawTexturedRect(poseStack, n8, n9, 20.0, 55.0, 38.0, 100.0, this.style.mirrorGauge);
            }
        }
    }

    @Override
    protected List<Component> getToolTip() {
        List<Component> list = super.getToolTip();
        Ic2FluidStack ic2FluidStack = this.tank.getFluidStack();
        if (ic2FluidStack == null || ic2FluidStack.isEmpty()) {
            list.add((Component)Component.m_237115_((String)"ic2.generic.text.empty"));
        } else {
            Fluid fluid = ic2FluidStack.getFluid();
            if (fluid != null) {
                list.add((Component)Component.m_237113_((String)(SideProxyClient.envProxy.getFluidName(ic2FluidStack) + ": " + ic2FluidStack.getAmountMb() + " " + Localization.translate("ic2.generic.text.mb"))));
            } else {
                list.add((Component)Component.m_237113_((String)"invalid fluid stack"));
            }
        }
        return list;
    }

    private static enum TankGuiStyle {
        Normal(true, true, false),
        Borderless(false, true, false),
        BorderlessMirrored(false, true, true),
        Plain(false, false, false);

        public final boolean withBorder;
        public final boolean withGauge;
        public final boolean mirrorGauge;

        private TankGuiStyle(boolean bl, boolean bl2, boolean bl3) {
            this.withBorder = bl;
            this.withGauge = bl2;
            this.mirrorGauge = bl3;
        }
    }
}

