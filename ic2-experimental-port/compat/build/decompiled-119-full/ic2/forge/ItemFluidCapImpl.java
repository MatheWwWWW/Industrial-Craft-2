/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.common.capabilities.Capability
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.common.capabilities.ICapabilityProvider
 *  net.minecraftforge.common.util.LazyOptional
 *  net.minecraftforge.common.util.NonNullSupplier
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.capability.IFluidHandler$FluidAction
 *  net.minecraftforge.fluids.capability.IFluidHandlerItem
 *  org.apache.commons.lang3.mutable.Mutable
 */
package ic2.forge;

import ic2.core.fluid.Ic2FluidItem;
import ic2.core.fluid.Ic2FluidStack;
import ic2.forge.EnvFluidHandlerForge;
import ic2.forge.Ic2FluidStackImpl;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import org.apache.commons.lang3.mutable.Mutable;

final class ItemFluidCapImpl
implements ICapabilityProvider,
IFluidHandlerItem,
NonNullSupplier<IFluidHandlerItem>,
Mutable<ItemStack> {
    private ItemStack stack;

    public ItemFluidCapImpl(ItemStack itemStack) {
        this.stack = itemStack;
    }

    public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction direction) {
        return capability == ForgeCapabilities.FLUID_HANDLER_ITEM ? LazyOptional.of((NonNullSupplier)this) : LazyOptional.empty();
    }

    public int getTanks() {
        return 1;
    }

    public int getTankCapacity(int n) {
        if (n != 0) {
            return 0;
        }
        Ic2FluidItem ic2FluidItem = (Ic2FluidItem)this.stack.m_41720_();
        return ic2FluidItem.getCapacityMb(this.stack);
    }

    public FluidStack getFluidInTank(int n) {
        if (n != 0) {
            return FluidStack.EMPTY;
        }
        Ic2FluidItem ic2FluidItem = (Ic2FluidItem)this.stack.m_41720_();
        Ic2FluidStack ic2FluidStack = ic2FluidItem.drainMb(this.stack, Integer.MAX_VALUE, true, null);
        return EnvFluidHandlerForge.getForgeFs(ic2FluidStack);
    }

    public boolean isFluidValid(int n, FluidStack fluidStack) {
        return n == 0;
    }

    public FluidStack drain(int n, IFluidHandler.FluidAction fluidAction) {
        if (n <= 0 || this.stack.m_41613_() != 1) {
            return FluidStack.EMPTY;
        }
        Ic2FluidItem ic2FluidItem = (Ic2FluidItem)this.stack.m_41720_();
        return EnvFluidHandlerForge.getForgeFs(ic2FluidItem.drainMb(this.stack, n, fluidAction.simulate(), (Mutable<ItemStack>)this));
    }

    public FluidStack drain(FluidStack fluidStack, IFluidHandler.FluidAction fluidAction) {
        if (fluidStack == null || fluidStack.isEmpty() || this.stack.m_41613_() != 1) {
            return FluidStack.EMPTY;
        }
        Ic2FluidItem ic2FluidItem = (Ic2FluidItem)this.stack.m_41720_();
        int n = ic2FluidItem.drainMb(this.stack, new Ic2FluidStackImpl(fluidStack), fluidAction.simulate(), (Mutable<ItemStack>)this);
        if (n <= 0) {
            return FluidStack.EMPTY;
        }
        fluidStack = fluidStack.copy();
        fluidStack.setAmount(n);
        return fluidStack;
    }

    public int fill(FluidStack fluidStack, IFluidHandler.FluidAction fluidAction) {
        if (fluidStack == null || fluidStack.isEmpty() || this.stack.m_41613_() != 1) {
            return 0;
        }
        Ic2FluidItem ic2FluidItem = (Ic2FluidItem)this.stack.m_41720_();
        return ic2FluidItem.fillMb(this.stack, new Ic2FluidStackImpl(fluidStack), fluidAction.simulate(), this);
    }

    public ItemStack getContainer() {
        return this.stack;
    }

    public IFluidHandlerItem get() {
        return this;
    }

    public ItemStack getValue() {
        return this.stack;
    }

    public void setValue(ItemStack itemStack) {
        this.stack = itemStack;
    }
}

