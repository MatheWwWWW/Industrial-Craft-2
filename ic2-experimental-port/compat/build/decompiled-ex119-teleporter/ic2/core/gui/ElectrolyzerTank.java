/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.network.chat.Component
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.tuple.Pair
 */
package ic2.core.gui;

import ic2.api.recipe.IElectrolyzerRecipeManager;
import ic2.core.block.machine.container.ContainerElectrolyzer;
import ic2.core.block.machine.gui.GuiElectrolyzer;
import ic2.core.block.machine.tileentity.TileEntityElectrolyzer;
import ic2.core.fluid.Ic2FluidStack;
import ic2.core.gui.AbstractFluidSlot;
import java.util.List;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

public class ElectrolyzerTank
extends AbstractFluidSlot {
    private final TileEntityElectrolyzer electrolyzer;
    private final int id;
    private Pair<Ic2FluidStack, Direction> output;

    public ElectrolyzerTank(GuiElectrolyzer guiElectrolyzer, int n, int n2, int n3) {
        super(guiElectrolyzer, n, n2, 18, 18);
        this.electrolyzer = (TileEntityElectrolyzer)((ContainerElectrolyzer)((Object)guiElectrolyzer.getContainer())).base;
        this.id = n3;
    }

    public boolean isActive() {
        return this.output != null;
    }

    @Override
    public void tick() {
        this.output = null;
        if (!this.electrolyzer.hasRecipe()) {
            return;
        }
        IElectrolyzerRecipeManager.ElectrolyzerOutput[] electrolyzerOutputArray = this.electrolyzer.getCurrentRecipe().outputs;
        int n = electrolyzerOutputArray.length;
        if (n >= this.id) {
            return;
        }
        if ((n & 1) == 1 && this.id == 2) {
            this.output = electrolyzerOutputArray[n / 2].getFullOutput();
        }
        if (n >= 2) {
            if (this.id == 1) {
                this.output = electrolyzerOutputArray[n < 4 ? 0 : 1].getFullOutput();
            }
            if (this.id == 3) {
                this.output = electrolyzerOutputArray[n - (n < 4 ? 1 : 2)].getFullOutput();
            }
        }
        if (n >= 4) {
            if (this.id == 0) {
                this.output = electrolyzerOutputArray[0].getFullOutput();
            }
            if (this.id == 4) {
                this.output = electrolyzerOutputArray[n - 1].getFullOutput();
            }
        }
    }

    @Override
    protected List<Component> getToolTip() {
        List<Component> list = super.getToolTip();
        if (this.output != null) {
            list.add((Component)Component.m_237113_((String)("Output Tank: " + StringUtils.capitalize((String)((Direction)this.output.getRight()).m_7912_()))));
        }
        return list;
    }

    @Override
    protected Ic2FluidStack getFluidStack() {
        return this.output != null ? (Ic2FluidStack)this.output.getLeft() : null;
    }
}

