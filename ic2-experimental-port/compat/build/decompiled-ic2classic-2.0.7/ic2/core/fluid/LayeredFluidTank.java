/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.IFluidTank
 *  net.minecraftforge.fluids.capability.IFluidHandler
 *  net.minecraftforge.fluids.capability.IFluidHandler$FluidAction
 */
package ic2.core.fluid;

import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.buffer.IOutputBuffer;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.collection.NBTListWrapper;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class LayeredFluidTank
implements IFluidHandler,
INetworkDataBuffer,
IFluidTank {
    List<Consumer<LayeredFluidTank>> listeners = CollectionUtils.createList();
    int capacity;
    int stored;
    Object2ObjectMap<Fluid, FluidStack> fluidMap = CollectionUtils.createLinkedMap();
    List<Fluid> fluids = CollectionUtils.createList();

    public LayeredFluidTank(int capacity) {
        this.capacity = capacity;
    }

    public void addListener(Consumer<LayeredFluidTank> listener) {
        this.listeners.add(listener);
    }

    public void notifyListeners() {
        for (Consumer<LayeredFluidTank> tanks : this.listeners) {
            tanks.accept(this);
        }
    }

    public CompoundTag write(CompoundTag nbt) {
        ListTag list = new ListTag();
        for (FluidStack fluid : this.fluidMap.values()) {
            list.add((Object)fluid.writeToNBT(new CompoundTag()));
        }
        nbt.m_128365_("fluids", (Tag)list);
        nbt.m_128405_("stored", this.stored);
        return nbt;
    }

    public void read(CompoundTag nbt) {
        this.stored = nbt.m_128451_("stored");
        for (CompoundTag data : NBTListWrapper.wrap(nbt.m_128437_("fluids", 10), CompoundTag.class)) {
            FluidStack stack = FluidStack.loadFluidStackFromNBT((CompoundTag)data);
            if (stack.isEmpty()) continue;
            this.fluids.add(stack.getFluid());
            this.fluidMap.put((Object)stack.getFluid(), (Object)stack);
        }
    }

    @Override
    public void write(IOutputBuffer buffer) {
        buffer.writeInt(this.stored);
        buffer.writeShort((short)this.fluidMap.size());
        for (FluidStack fluid : this.fluidMap.values()) {
            buffer.writeFluidStack(fluid);
        }
    }

    @Override
    public void read(IInputBuffer buffer) {
        this.stored = buffer.readInt();
        this.fluidMap.clear();
        this.fluids.clear();
        int limit = buffer.readShort();
        for (int i = 0; i < limit; ++i) {
            FluidStack fluid = buffer.readFluidStack();
            if (fluid.isEmpty()) continue;
            this.fluids.add(fluid.getFluid());
            this.fluidMap.put((Object)fluid.getFluid(), (Object)fluid);
        }
    }

    public int getTanks() {
        return Math.max(1, this.fluids.size());
    }

    public FluidStack getFluidInTank(int tank) {
        return tank >= this.fluids.size() ? FluidStack.EMPTY : (FluidStack)this.fluidMap.get((Object)this.fluids.get(tank));
    }

    public int getTankCapacity(int tank) {
        return this.capacity;
    }

    public boolean isFluidValid(int tank, FluidStack stack) {
        return true;
    }

    public int fill(FluidStack resource, IFluidHandler.FluidAction action) {
        if (resource.isEmpty() || this.capacity - this.stored <= 0) {
            return 0;
        }
        if (action.simulate()) {
            return Math.min(this.capacity - this.stored, resource.getAmount());
        }
        FluidStack stack = (FluidStack)this.fluidMap.getOrDefault((Object)resource.getFluid(), (Object)FluidStack.EMPTY);
        if (stack.isEmpty()) {
            stack = resource.copy();
            this.fluidMap.put((Object)stack.getFluid(), (Object)stack);
            this.fluids.add(stack.getFluid());
            stack.setAmount(0);
        }
        int added = Math.min(resource.getAmount(), this.capacity - this.stored);
        stack.grow(added);
        this.stored += added;
        this.notifyListeners();
        return added;
    }

    public FluidStack drain(FluidStack resource, IFluidHandler.FluidAction action) {
        if (this.stored <= 0 || resource.isEmpty()) {
            return FluidStack.EMPTY;
        }
        FluidStack found = (FluidStack)this.fluidMap.getOrDefault((Object)resource.getFluid(), (Object)FluidStack.EMPTY);
        if (found.isEmpty()) {
            return FluidStack.EMPTY;
        }
        FluidStack drained = new FluidStack(found, Math.min(found.getAmount(), resource.getAmount()));
        if (action.execute()) {
            found.shrink(drained.getAmount());
            if (found.getAmount() <= 0) {
                this.fluids.remove(resource.getFluid());
                this.fluidMap.get((Object)resource.getFluid());
            }
            this.stored -= drained.getAmount();
            this.notifyListeners();
        }
        return drained;
    }

    public FluidStack drain(int maxDrain, IFluidHandler.FluidAction action) {
        if (maxDrain <= 0 || this.stored <= 0) {
            return FluidStack.EMPTY;
        }
        FluidStack stack = (FluidStack)this.fluidMap.get((Object)this.fluids.get(0));
        FluidStack drained = new FluidStack(stack, Math.min(stack.getAmount(), maxDrain));
        if (action.execute()) {
            stack.shrink(drained.getAmount());
            if (stack.getAmount() <= 0) {
                this.fluids.remove(drained.getFluid());
                this.fluidMap.get((Object)drained.getFluid());
            }
            this.stored -= drained.getAmount();
            this.notifyListeners();
        }
        return drained;
    }

    public FluidStack getFluid() {
        return this.fluids.isEmpty() ? FluidStack.EMPTY : (FluidStack)this.fluidMap.get((Object)this.fluids.get(0));
    }

    public int getFluidAmount() {
        return this.stored;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public boolean isFluidValid(FluidStack stack) {
        return true;
    }
}

