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
 */
package ic2.core.inventory.gui.components.simple;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.inventory.gui.components.GuiWidget;
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

public class TankComponent
extends GuiWidget {
    public static final Vec2i DEFAULT_TANK_POS = new Vec2i(176, 0);
    static final Component NO_FLUID = Component.m_237115_((String)"gui.ic2.tank.no_fluid");
    IFluidTank tank;
    Vec2i pos;
    Box2i info;
    Component tankName = this.translate("gui.ic2.tank.simple");

    public TankComponent(Box2i box, IFluidTank tank) {
        this(box, DEFAULT_TANK_POS, tank);
    }

    public TankComponent(Box2i box, Vec2i pos, IFluidTank tank) {
        super(box);
        this.pos = pos;
        this.tank = tank;
    }

    public TankComponent setTankName(String name) {
        return this.setTankName((Component)this.translate(name));
    }

    public TankComponent setTankName(Component comp) {
        this.tankName = comp;
        return this;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.DRAW_BACKGROUND);
        requests.add(GuiWidget.ActionRequest.TOOLTIP);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawBackground(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        float max = (float)this.tank.getFluidAmount() / (float)this.tank.getCapacity() * (float)this.box.getHeight();
        this.gui.displayTank(matrix, this.box.getX(), this.box.getY(), this.pos.getX(), this.pos.getY(), this.box.getWidth(), max, this.box.getHeight(), this.tank.getFluid());
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addTooltips(PoseStack matrix, int mouseX, int mouseY, Consumer<Component> tooltips) {
        if (this.box.isInBox(mouseX, mouseY)) {
            tooltips.accept(this.tankName);
            tooltips.accept((Component)this.translate("gui.ic2.tank.capacity", Formatters.EU_FORMAT.format(this.tank.getFluidAmount()), Formatters.EU_FORMAT.format(this.tank.getCapacity())));
            FluidStack fluid = this.tank.getFluid();
            tooltips.accept((Component)(this.tank.getFluidAmount() <= 0 ? NO_FLUID : this.translate("gui.ic2.tank.fluid", fluid.getDisplayName(), Formatters.EU_FORMAT.format(fluid.getAmount()))));
        }
    }
}

