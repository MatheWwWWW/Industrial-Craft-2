/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Predicate
 *  com.google.common.base.Predicates
 *  com.google.common.base.Supplier
 *  com.google.common.base.Suppliers
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.util.EnumFacing
 *  net.minecraftforge.common.capabilities.Capability
 *  net.minecraftforge.fluids.Fluid
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.FluidTank
 *  net.minecraftforge.fluids.capability.CapabilityFluidHandler
 *  net.minecraftforge.fluids.capability.IFluidHandler
 *  net.minecraftforge.fluids.capability.IFluidTankProperties
 */
package ic2.core.block.comp;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import ic2.api.recipe.ILiquidAcceptManager;
import ic2.core.block.TileEntityBlock;
import ic2.core.block.comp.TileEntityComponent;
import ic2.core.block.invslot.InvSlot;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class Fluids
extends TileEntityComponent {
    protected final List<InternalFluidTank> managedTanks = new ArrayList<InternalFluidTank>();
    protected final List<Supplier<? extends Collection<InternalFluidTank>>> unmanagedTanks = new ArrayList<Supplier<? extends Collection<InternalFluidTank>>>();

    public Fluids(TileEntityBlock parent) {
        super(parent);
    }

    public InternalFluidTank addTankInsert(String name, int capacity) {
        return this.addTankInsert(name, capacity, (Predicate<Fluid>)Predicates.alwaysTrue());
    }

    public InternalFluidTank addTankInsert(String name, int capacity, Predicate<Fluid> acceptedFluids) {
        return this.addTankInsert(name, capacity, InvSlot.InvSide.ANY, acceptedFluids);
    }

    public InternalFluidTank addTankInsert(String name, int capacity, InvSlot.InvSide side) {
        return this.addTankInsert(name, capacity, side, (Predicate<Fluid>)Predicates.alwaysTrue());
    }

    public InternalFluidTank addTankInsert(String name, int capacity, InvSlot.InvSide side, Predicate<Fluid> acceptedFluids) {
        return this.addTank(name, capacity, InvSlot.Access.I, side, acceptedFluids);
    }

    public InternalFluidTank addTankExtract(String name, int capacity) {
        return this.addTankExtract(name, capacity, InvSlot.InvSide.ANY);
    }

    public InternalFluidTank addTankExtract(String name, int capacity, InvSlot.InvSide side) {
        return this.addTank(name, capacity, InvSlot.Access.O, side);
    }

    public InternalFluidTank addTank(String name, int capacity) {
        return this.addTank(name, capacity, InvSlot.Access.IO);
    }

    public InternalFluidTank addTank(String name, int capacity, InvSlot.Access access) {
        return this.addTank(name, capacity, access, InvSlot.InvSide.ANY);
    }

    public InternalFluidTank addTank(String name, int capacity, Predicate<Fluid> acceptedFluids) {
        return this.addTank(name, capacity, InvSlot.Access.IO, InvSlot.InvSide.ANY, acceptedFluids);
    }

    public InternalFluidTank addTank(String name, int capacity, InvSlot.Access access, InvSlot.InvSide side) {
        return this.addTank(name, capacity, access, side, (Predicate<Fluid>)Predicates.alwaysTrue());
    }

    public InternalFluidTank addTank(String name, int capacity, InvSlot.Access access, InvSlot.InvSide side, Predicate<Fluid> acceptedFluids) {
        return this.addTank(name, capacity, access.isInput() ? side.getAcceptedSides() : Collections.emptySet(), access.isOutput() ? side.getAcceptedSides() : Collections.emptySet(), acceptedFluids);
    }

    public InternalFluidTank addTank(String name, int capacity, Collection<EnumFacing> inputSides, Collection<EnumFacing> outputSides, Predicate<Fluid> acceptedFluids) {
        return this.addTank(new InternalFluidTank(name, inputSides, outputSides, acceptedFluids, capacity));
    }

    public InternalFluidTank addTank(InternalFluidTank tank) {
        this.managedTanks.add(tank);
        return tank;
    }

    public void addUnmanagedTanks(InternalFluidTank tank) {
        this.unmanagedTanks.add((Supplier<? extends Collection<InternalFluidTank>>)Suppliers.ofInstance(Collections.singleton(tank)));
    }

    public void addUnmanagedTanks(Collection<InternalFluidTank> tanks) {
        this.addUnmanagedTankHook((Supplier<? extends Collection<InternalFluidTank>>)Suppliers.ofInstance(tanks));
    }

    public void addUnmanagedTankHook(Supplier<? extends Collection<InternalFluidTank>> suppl) {
        this.unmanagedTanks.add(suppl);
    }

    public void changeConnectivity(InternalFluidTank tank, InvSlot.Access access, InvSlot.InvSide side) {
        this.changeConnectivity(tank, access.isInput() ? side.getAcceptedSides() : Collections.emptySet(), access.isOutput() ? side.getAcceptedSides() : Collections.emptySet());
    }

    public void changeConnectivity(InternalFluidTank tank, Collection<EnumFacing> inputSides, Collection<EnumFacing> outputSides) {
        assert (this.managedTanks.contains((Object)tank));
        tank.inputSides = inputSides;
        tank.outputSides = outputSides;
    }

    public FluidTank getFluidTank(String name) {
        for (InternalFluidTank tank : this.getAllTanks()) {
            if (!tank.identifier.equals(name)) continue;
            return tank;
        }
        throw new IllegalArgumentException("Unable to find tank: " + name);
    }

    @Override
    public void readFromNbt(NBTTagCompound nbt) {
        for (InternalFluidTank tank : this.managedTanks) {
            if (!nbt.func_150297_b(tank.identifier, 10)) continue;
            tank.readFromNBT(nbt.func_74775_l(tank.identifier));
        }
    }

    @Override
    public NBTTagCompound writeToNbt() {
        NBTTagCompound nbt = new NBTTagCompound();
        for (InternalFluidTank tank : this.managedTanks) {
            NBTTagCompound subTag = new NBTTagCompound();
            subTag = tank.writeToNBT(subTag);
            nbt.func_74782_a(tank.identifier, (NBTBase)subTag);
        }
        return nbt;
    }

    @Override
    public Collection<? extends Capability<?>> getProvidedCapabilities(EnumFacing side) {
        return Collections.singleton(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY);
    }

    @Override
    public <T> T getCapability(Capability<T> cap, EnumFacing side) {
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return (T)new FluidHandler(side);
        }
        return super.getCapability(cap, side);
    }

    public static Predicate<Fluid> fluidPredicate(Fluid ... fluids) {
        final Collection<Fluid> acceptedFluids = fluids.length > 10 ? new HashSet<Fluid>(Arrays.asList(fluids)) : Arrays.asList(fluids);
        return new Predicate<Fluid>(){

            public boolean apply(Fluid fluid) {
                return acceptedFluids.contains(fluid);
            }
        };
    }

    public static Predicate<Fluid> fluidPredicate(final ILiquidAcceptManager manager) {
        return new Predicate<Fluid>(){

            public boolean apply(Fluid fluid) {
                return manager.acceptsFluid(fluid);
            }
        };
    }

    public Iterable<InternalFluidTank> getAllTanks() {
        if (this.unmanagedTanks.isEmpty()) {
            return this.managedTanks;
        }
        ArrayList<InternalFluidTank> tanks = new ArrayList<InternalFluidTank>();
        tanks.addAll(this.managedTanks);
        for (Supplier<? extends Collection<InternalFluidTank>> suppl : this.unmanagedTanks) {
            tanks.addAll((Collection)suppl.get());
        }
        return tanks;
    }

    private class FluidHandler
    implements IFluidHandler {
        private final EnumFacing side;

        FluidHandler(EnumFacing side) {
            this.side = side;
        }

        public IFluidTankProperties[] getTankProperties() {
            ArrayList<IFluidTankProperties> props = new ArrayList<IFluidTankProperties>(Fluids.this.managedTanks.size());
            for (InternalFluidTank tank : Fluids.this.getAllTanks()) {
                if (!tank.canFill(this.side) && !tank.canDrain(this.side)) continue;
                props.add(tank.getTankProperties(this.side));
            }
            return props.toArray(new IFluidTankProperties[0]);
        }

        public int fill(FluidStack resource, boolean doFill) {
            if (resource == null || resource.amount <= 0) {
                return 0;
            }
            int total = 0;
            FluidStack missing = resource.copy();
            for (InternalFluidTank tank : Fluids.this.getAllTanks()) {
                if (!tank.canFill(this.side)) continue;
                missing.amount = resource.amount - (total += tank.fill(missing, doFill));
                if (missing.amount > 0) continue;
                break;
            }
            return total;
        }

        public FluidStack drain(FluidStack resource, boolean doDrain) {
            if (resource == null || resource.amount <= 0) {
                return null;
            }
            FluidStack ret = new FluidStack(resource.getFluid(), 0);
            for (InternalFluidTank tank : Fluids.this.getAllTanks()) {
                FluidStack add;
                FluidStack inTank;
                if (!tank.canDrain(this.side) || (inTank = tank.getFluid()) == null || inTank.getFluid() != resource.getFluid() || (add = tank.drain(resource.amount - ret.amount, doDrain)) == null) continue;
                assert (add.getFluid() == resource.getFluid());
                ret.amount += add.amount;
                if (ret.amount < resource.amount) continue;
                break;
            }
            if (ret.amount == 0) {
                return null;
            }
            return ret;
        }

        public FluidStack drain(int maxDrain, boolean doDrain) {
            for (InternalFluidTank tank : Fluids.this.getAllTanks()) {
                FluidStack stack;
                if (!tank.canDrain(this.side) || (stack = tank.drain(maxDrain, false)) == null) continue;
                stack.amount = maxDrain;
                return this.drain(stack, doDrain);
            }
            return null;
        }
    }

    public static class InternalFluidTank
    extends FluidTank {
        protected final String identifier;
        private final Predicate<Fluid> acceptedFluids;
        private Collection<EnumFacing> inputSides;
        private Collection<EnumFacing> outputSides;

        protected InternalFluidTank(String identifier, Collection<EnumFacing> inputSides, Collection<EnumFacing> outputSides, Predicate<Fluid> acceptedFluids, int capacity) {
            super(capacity);
            this.identifier = identifier;
            this.acceptedFluids = acceptedFluids;
            this.inputSides = inputSides;
            this.outputSides = outputSides;
        }

        public boolean canFillFluidType(FluidStack fluid) {
            return fluid != null && this.acceptsFluid(fluid.getFluid());
        }

        public boolean canDrainFluidType(FluidStack fluid) {
            return fluid != null && this.acceptsFluid(fluid.getFluid());
        }

        public boolean acceptsFluid(Fluid fluid) {
            return this.acceptedFluids.apply((Object)fluid);
        }

        IFluidTankProperties getTankProperties(final EnumFacing side) {
            assert (side == null || this.inputSides.contains(side) || this.outputSides.contains(side));
            return new IFluidTankProperties(){

                public FluidStack getContents() {
                    return this.getFluid();
                }

                public int getCapacity() {
                    return capacity;
                }

                public boolean canFillFluidType(FluidStack fluidStack) {
                    if (fluidStack == null || fluidStack.amount <= 0) {
                        return false;
                    }
                    return this.acceptsFluid(fluidStack.getFluid()) && (side == null || this.canFill(side));
                }

                public boolean canFill() {
                    return this.canFill(side);
                }

                public boolean canDrainFluidType(FluidStack fluidStack) {
                    if (fluidStack == null || fluidStack.amount <= 0) {
                        return false;
                    }
                    return this.acceptsFluid(fluidStack.getFluid()) && (side == null || this.canDrain(side));
                }

                public boolean canDrain() {
                    return this.canDrain(side);
                }
            };
        }

        public boolean canFill(EnumFacing side) {
            return this.inputSides.contains(side);
        }

        public boolean canDrain(EnumFacing side) {
            return this.outputSides.contains(side);
        }
    }
}

