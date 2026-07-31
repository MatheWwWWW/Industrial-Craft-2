/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.network.chat.Component
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.IFluidTank
 *  net.minecraftforge.fluids.capability.IFluidHandler
 */
package ic2.core.inventory.gui.components.simple;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.gui.components.simple.TankComponent;
import ic2.core.utils.helpers.Formatters;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class MultiTankComponent<T extends IFluidHandler & IFluidTank>
extends GuiWidget {
    static final Component TANK = Component.m_237115_((String)"gui.ic2.tank.multi");
    static final Component NO_FLUID = Component.m_237115_((String)"gui.ic2.tank.no_fluid");
    T tank;
    Vec2i pos;

    public MultiTankComponent(Box2i box, T tank) {
        this(box, TankComponent.DEFAULT_TANK_POS, tank);
    }

    public MultiTankComponent(Box2i box, Vec2i pos, T tank) {
        super(box);
        this.pos = pos;
        this.tank = tank;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.DRAW_BACKGROUND);
        requests.add(GuiWidget.ActionRequest.TOOLTIP);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawBackground(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        this.gui.displayMultiTank(matrix, this.box.getX(), this.box.getY(), this.pos.getX(), this.pos.getY(), this.box.getHeight(), this.tank);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addTooltips(PoseStack matrix, int mouseX, int mouseY, Consumer<Component> tooltips) {
        if (this.box.isInBox(mouseX, mouseY)) {
            int limit = this.tank.getTanks();
            int added = 0;
            tooltips.accept(TANK);
            tooltips.accept((Component)this.translate("gui.ic2.tank.capacity", Formatters.EU_FORMAT.format(((IFluidTank)this.tank).getFluidAmount()), Formatters.EU_FORMAT.format(((IFluidTank)this.tank).getCapacity())));
            for (int i = limit - 1; i >= 0; --i) {
                FluidStack fluid = this.tank.getFluidInTank(i);
                if (fluid.isEmpty() && (i > 0 || added > 0)) continue;
                ++added;
                tooltips.accept((Component)(fluid.isEmpty() ? NO_FLUID : this.translate("gui.ic2.tank.fluid", fluid.getDisplayName(), Formatters.EU_FORMAT.format(fluid.getAmount()))));
            }
        }
    }
}

