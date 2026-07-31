/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Predicates
 *  com.google.common.base.Supplier
 *  com.google.common.base.Suppliers
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.level.material.Fluid
 */
package ic2.core.block.comp;

import com.google.common.base.Predicates;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import ic2.api.recipe.ILiquidAcceptManager;
import ic2.core.block.comp.TileEntityComponent;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.tileentity.Ic2TileEntity;
import ic2.core.fluid.FluidTankInfo;
import ic2.core.fluid.Ic2FluidStack;
import ic2.core.fluid.Ic2FluidTank;
import ic2.core.util.Util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

public class Fluids
extends TileEntityComponent {
    protected final List<InternalFluidTank> managedTanks = new ArrayList<InternalFluidTank>();
    protected final List<Supplier<? extends Collection<InternalFluidTank>>> unmanagedTanks = new ArrayList<Supplier<? extends Collection<InternalFluidTank>>>();

    public Fluids(Ic2TileEntity ic2TileEntity) {
        super(ic2TileEntity);
    }

    public InternalFluidTank addTankInsert(String string, int n) {
        return this.addTankInsert(string, n, (Predicate<Fluid>)Predicates.alwaysTrue());
    }

    public InternalFluidTank addTankInsert(String string, int n, Predicate<Fluid> predicate) {
        return this.addTankInsert(string, n, InvSlot.InvSide.ANY, predicate);
    }

    public InternalFluidTank addTankInsert(String string, int n, InvSlot.InvSide invSide) {
        return this.addTankInsert(string, n, invSide, (Predicate<Fluid>)Predicates.alwaysTrue());
    }

    public InternalFluidTank addTankInsert(String string, int n, InvSlot.InvSide invSide, Predicate<Fluid> predicate) {
        return this.addTank(string, n, InvSlot.Access.I, invSide, predicate);
    }

    public InternalFluidTank addTankExtract(String string, int n) {
        return this.addTankExtract(string, n, InvSlot.InvSide.ANY);
    }

    public InternalFluidTank addTankExtract(String string, int n, InvSlot.InvSide invSide) {
        return this.addTank(string, n, InvSlot.Access.O, invSide);
    }

    public InternalFluidTank addTank(String string, int n) {
        return this.addTank(string, n, InvSlot.Access.IO);
    }

    public InternalFluidTank addTank(String string, int n, InvSlot.Access access) {
        return this.addTank(string, n, access, InvSlot.InvSide.ANY);
    }

    public InternalFluidTank addTank(String string, int n, Predicate<Fluid> predicate) {
        return this.addTank(string, n, InvSlot.Access.IO, InvSlot.InvSide.ANY, predicate);
    }

    public InternalFluidTank addTank(String string, int n, InvSlot.Access access, InvSlot.InvSide invSide) {
        return this.addTank(string, n, access, invSide, (Predicate<Fluid>)Predicates.alwaysTrue());
    }

    public InternalFluidTank addTank(String string, int n, InvSlot.Access access, InvSlot.InvSide invSide, Predicate<Fluid> predicate) {
        return this.addTank(string, n, access.isInput() ? invSide.getAcceptedSides() : Collections.emptySet(), access.isOutput() ? invSide.getAcceptedSides() : Collections.emptySet(), predicate);
    }

    public InternalFluidTank addTank(String string, int n, Collection<Direction> collection, Collection<Direction> collection2, Predicate<Fluid> predicate) {
        return this.addTank(new InternalFluidTank(string, collection, collection2, predicate, n));
    }

    public InternalFluidTank addTank(InternalFluidTank internalFluidTank) {
        this.managedTanks.add(internalFluidTank);
        return internalFluidTank;
    }

    public void addUnmanagedTanks(InternalFluidTank internalFluidTank) {
        this.unmanagedTanks.add((Supplier<? extends Collection<InternalFluidTank>>)Suppliers.ofInstance(Collections.singleton(internalFluidTank)));
    }

    public void addUnmanagedTanks(Collection<InternalFluidTank> collection) {
        this.addUnmanagedTankHook((Supplier<? extends Collection<InternalFluidTank>>)Suppliers.ofInstance(collection));
    }

    public void addUnmanagedTankHook(Supplier<? extends Collection<InternalFluidTank>> supplier) {
        this.unmanagedTanks.add(supplier);
    }

    public void changeConnectivity(InternalFluidTank internalFluidTank, InvSlot.Access access, InvSlot.InvSide invSide) {
        this.changeConnectivity(internalFluidTank, access.isInput() ? invSide.getAcceptedSides() : Collections.emptySet(), access.isOutput() ? invSide.getAcceptedSides() : Collections.emptySet());
    }

    public void changeConnectivity(InternalFluidTank internalFluidTank, Collection<Direction> collection, Collection<Direction> collection2) {
        assert (this.managedTanks.contains(internalFluidTank));
        internalFluidTank.inputSides = collection;
        internalFluidTank.outputSides = collection2;
    }

    public Ic2FluidTank getFluidTank(String string) {
        for (InternalFluidTank internalFluidTank : this.getAllTanks()) {
            if (!internalFluidTank.identifier.equals(string)) continue;
            return internalFluidTank;
        }
        throw new IllegalArgumentException("Unable to find tank: " + string);
    }

    @Override
    public void readFromNbt(CompoundTag compoundTag) {
        for (InternalFluidTank internalFluidTank : this.managedTanks) {
            if (!compoundTag.m_128425_(internalFluidTank.identifier, 10)) continue;
            internalFluidTank.fromNbt(compoundTag.m_128469_(internalFluidTank.identifier));
        }
    }

    @Override
    public CompoundTag writeToNbt() {
        CompoundTag compoundTag = new CompoundTag();
        for (InternalFluidTank internalFluidTank : this.managedTanks) {
            CompoundTag compoundTag2 = new CompoundTag();
            internalFluidTank.toNbt(compoundTag2);
            compoundTag.m_128365_(internalFluidTank.identifier, (Tag)compoundTag2);
        }
        return compoundTag;
    }

    public FluidTankInfo[] getTankInfos() {
        if (this.managedTanks.isEmpty()) {
            return null;
        }
        FluidTankInfo[] fluidTankInfoArray = new FluidTankInfo[this.managedTanks.size()];
        int n = 0;
        for (InternalFluidTank internalFluidTank : this.managedTanks) {
            int n2 = 0;
            int n3 = 0;
            for (Direction direction : Util.ALL_DIRS) {
                int n4 = 1 << direction.ordinal();
                if (internalFluidTank.canDrain(direction)) {
                    n2 |= n4;
                }
                if (!internalFluidTank.canFill(direction)) continue;
                n3 |= n4;
            }
            if (!(n2 | n3)) continue;
            fluidTankInfoArray[n++] = new FluidTankInfo(n2, n3, internalFluidTank.getCapacity(), internalFluidTank.getFluidStack());
        }
        if (n == 0) {
            return null;
        }
        if (n != fluidTankInfoArray.length) {
            fluidTankInfoArray = Arrays.copyOf(fluidTankInfoArray, n);
        }
        return fluidTankInfoArray;
    }

    public Ic2FluidStack drainMb(Direction direction, int n, boolean bl) {
        if (n <= 0) {
            return Ic2FluidStack.EMPTY;
        }
        for (InternalFluidTank internalFluidTank : this.getAllTanks()) {
            Ic2FluidStack ic2FluidStack;
            if (!internalFluidTank.canDrain(direction) || (ic2FluidStack = internalFluidTank.drainMb(n, true)) == null || ic2FluidStack.isEmpty()) continue;
            ic2FluidStack.setAmountMb(n);
            n = this.drainMb(direction, ic2FluidStack, bl);
            ic2FluidStack.setAmountMb(n);
            return ic2FluidStack;
        }
        return Ic2FluidStack.EMPTY;
    }

    public int drainMb(Direction direction, Ic2FluidStack ic2FluidStack, boolean bl) {
        if (ic2FluidStack == null || ic2FluidStack.isEmpty()) {
            return 0;
        }
        int n = ic2FluidStack.getAmountMb();
        for (InternalFluidTank internalFluidTank : this.getAllTanks()) {
            int n2;
            if (!internalFluidTank.canDrain(direction) || (n2 = internalFluidTank.drainMb(ic2FluidStack, bl)) <= 0) continue;
            assert (n2 <= ic2FluidStack.getAmountMb());
            ic2FluidStack.decreaseMb(n2);
            if (!ic2FluidStack.isEmpty()) continue;
            break;
        }
        int n3 = n - ic2FluidStack.getAmountMb();
        ic2FluidStack.setAmountMb(n);
        return n3;
    }

    public int fillMb(Direction direction, Ic2FluidStack ic2FluidStack, boolean bl) {
        if (ic2FluidStack == null || ic2FluidStack.isEmpty()) {
            return 0;
        }
        int n = ic2FluidStack.getAmountMb();
        for (InternalFluidTank internalFluidTank : this.getAllTanks()) {
            int n2;
            if (!internalFluidTank.canFill(ic2FluidStack.getFluid(), direction) || (n2 = internalFluidTank.fillMb(ic2FluidStack, bl)) <= 0) continue;
            assert (n2 <= ic2FluidStack.getAmountMb());
            ic2FluidStack.decreaseMb(n2);
            if (!ic2FluidStack.isEmpty()) continue;
            break;
        }
        int n3 = n - ic2FluidStack.getAmountMb();
        ic2FluidStack.setAmountMb(n);
        return n3;
    }

    public static Predicate<Fluid> fluidPredicate(Fluid ... fluidArray) {
        Collection<Fluid> collection = fluidArray.length > 10 ? new HashSet<Fluid>(Arrays.asList(fluidArray)) : Arrays.asList(fluidArray);
        return collection::contains;
    }

    public static Predicate<Fluid> fluidPredicate(TagKey<Fluid> tagKey) {
        return fluid -> fluid.m_205067_(tagKey);
    }

    public static Predicate<Fluid> fluidPredicate(ILiquidAcceptManager iLiquidAcceptManager) {
        return iLiquidAcceptManager::acceptsFluid;
    }

    public Iterable<InternalFluidTank> getAllTanks() {
        if (this.unmanagedTanks.isEmpty()) {
            return this.managedTanks;
        }
        ArrayList<InternalFluidTank> arrayList = new ArrayList<InternalFluidTank>();
        arrayList.addAll(this.managedTanks);
        for (Supplier<? extends Collection<InternalFluidTank>> supplier : this.unmanagedTanks) {
            arrayList.addAll((Collection)supplier.get());
        }
        return arrayList;
    }

    public static class InternalFluidTank
    extends Ic2FluidTank {
        protected final String identifier;
        private final Predicate<Fluid> acceptedFluids;
        private Collection<Direction> inputSides;
        private Collection<Direction> outputSides;

        protected InternalFluidTank(String string, Collection<Direction> collection, Collection<Direction> collection2, Predicate<Fluid> predicate, int n) {
            super(n);
            this.identifier = string;
            this.acceptedFluids = predicate;
            this.inputSides = collection;
            this.outputSides = collection2;
        }

        @Override
        public boolean canFill(Fluid fluid) {
            return fluid != null && this.acceptedFluids.test(fluid);
        }

        public boolean canFill(Direction direction) {
            return this.inputSides.contains(direction);
        }

        public boolean canFill(Fluid fluid, Direction direction) {
            return this.canFill(fluid) && this.canFill(direction);
        }

        public boolean canDrain(Direction direction) {
            return this.outputSides.contains(direction);
        }
    }
}

