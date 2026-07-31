/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectOpenHashSet
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.fluids.FluidActionResult
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.FluidUtil
 *  net.minecraftforge.fluids.capability.IFluidHandler
 *  net.minecraftforge.fluids.capability.templates.FluidTank
 */
package ic2.core.inventory.filter;

import ic2.core.fluid.WrapperFluidHandler;
import ic2.core.inventory.filter.IFilter;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import java.util.Set;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class FluidFilter
implements IFilter {
    public static final IFilter EMPTY_CONTAINER = T -> {
        if (T.m_41720_() == Items.f_42455_) {
            return false;
        }
        return T.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent() && !FluidUtil.getFluidContained((ItemStack)T).isPresent();
    };
    public static final IFilter ANY_FLUID = T -> FluidUtil.getFluidContained((ItemStack)T).isPresent();
    Set<Fluid> fluids;

    public static IFilter createFillFilter(Fluid fluid) {
        return new FluidFillFilter(fluid);
    }

    public FluidFilter(Fluid ... fluids) {
        this.fluids = new ObjectOpenHashSet((Object[])fluids);
    }

    @Override
    public boolean matches(ItemStack input) {
        return this.fluids.contains(FluidUtil.getFluidContained((ItemStack)input).orElse(FluidStack.EMPTY).getFluid());
    }

    public static class FluidFillFilter
    implements IFilter {
        FluidTank tank = new FluidTank(1000);
        Fluid fluid;

        public FluidFillFilter(Fluid fluid) {
            this.fluid = fluid;
            this.tank.setFluid(new FluidStack(fluid, 1000));
        }

        @Override
        public boolean matches(ItemStack input) {
            FluidActionResult result = FluidUtil.tryFillContainer((ItemStack)input, (IFluidHandler)new WrapperFluidHandler((IFluidHandler)this.tank), (int)1000, null, (boolean)true);
            return result.isSuccess() && !result.getResult().m_41619_();
        }
    }

    public static class FluidTankFillFilter
    implements IFilter {
        IFluidHandler handler;

        public FluidTankFillFilter(IFluidHandler handler) {
            this.handler = handler;
        }

        @Override
        public boolean matches(ItemStack input) {
            FluidActionResult result = FluidUtil.tryFillContainer((ItemStack)input, (IFluidHandler)new WrapperFluidHandler(this.handler), (int)1000, null, (boolean)true);
            return result.isSuccess() && !result.getResult().m_41619_();
        }
    }

    public static class FluidTankFilter
    implements IFilter {
        IFluidHandler handler;

        public FluidTankFilter(IFluidHandler handler) {
            this.handler = new WrapperFluidHandler.FillHandlerWrapper(handler);
        }

        @Override
        public boolean matches(ItemStack input) {
            FluidActionResult result = FluidUtil.tryEmptyContainer((ItemStack)input, (IFluidHandler)this.handler, (int)1000, null, (boolean)true);
            return result.isSuccess() && !result.getResult().m_41619_();
        }
    }
}

