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
import ic2.core.fluid.FluidHandler;
import ic2.core.fluid.Ic2FluidStack;
import ic2.core.gui.GuiElement;
import ic2.core.gui.TankFluidSlot;
import ic2.core.init.Localization;
import ic2.core.util.Util;
import java.util.List;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.material.Fluid;

public abstract class AbstractFluidSlot
extends GuiElement<TankFluidSlot> {
    public static final int posU = 8;
    public static final int posV = 160;
    public static final int normalWidth = 18;
    public static final int normalHeight = 18;
    public static final int fluidOffsetX = 1;
    public static final int fluidOffsetY = 1;
    public static final int fluidNetWidth = 16;
    public static final int fluidNetHeight = 16;

    protected AbstractFluidSlot(Ic2Gui<?> ic2Gui, int n, int n2, int n3, int n4) {
        super(ic2Gui, n, n2, n3, n4);
    }

    @Override
    public void drawBackground(PoseStack poseStack, int n, int n2) {
        AbstractFluidSlot.bindCommonTexture();
        Ic2FluidStack ic2FluidStack = this.getFluidStack();
        this.gui.drawTexturedRect(poseStack, this.x, this.y, this.width, this.height, 8.0, 160.0);
        if (ic2FluidStack != null && !ic2FluidStack.isEmpty()) {
            int n3 = this.x + 1;
            int n4 = this.y + 1;
            int n5 = 16;
            int n6 = 16;
            Fluid fluid = ic2FluidStack.getFluid();
            TextureAtlasSprite textureAtlasSprite = fluid != null ? AbstractFluidSlot.getBlockTextureMap().m_118316_(FluidHandler.getStillSpriteId(fluid)) : null;
            int n7 = fluid != null ? FluidHandler.getColor(fluid) : -1;
            AbstractFluidSlot.bindBlockTexture();
            this.gui.drawSprite(poseStack, n3, n4, n5, n6, textureAtlasSprite, n7, 1.0, false, false);
        }
    }

    @Override
    protected List<Component> getToolTip() {
        List<Component> list = super.getToolTip();
        Ic2FluidStack ic2FluidStack = this.getFluidStack();
        if (ic2FluidStack == null || ic2FluidStack.isEmpty()) {
            list.add((Component)Component.m_237113_((String)"No Fluid"));
            list.add((Component)Component.m_237113_((String)("Amount: 0 " + Localization.translate("ic2.generic.text.mb"))));
            list.add((Component)Component.m_237113_((String)"Type: Not Available"));
        } else {
            Fluid fluid = ic2FluidStack.getFluid();
            if (fluid != null) {
                list.add((Component)Component.m_237113_((String)Util.getName(ic2FluidStack.getFluid()).toString()));
                list.add((Component)Component.m_237113_((String)("Amount: " + ic2FluidStack.getAmountMb() + " " + Localization.translate("ic2.generic.text.mb"))));
                String string = FluidHandler.isGaseous(fluid) ? "Gas" : "Liquid";
                list.add((Component)Component.m_237113_((String)("Type: " + string)));
            } else {
                list.add((Component)Component.m_237113_((String)"Invalid FluidStack instance."));
            }
        }
        return list;
    }

    protected abstract Ic2FluidStack getFluidStack();
}

