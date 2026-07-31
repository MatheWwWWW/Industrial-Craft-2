/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.material.Fluid
 */
package ic2.core.block.invslot;

import ic2.core.block.IInventorySlotHolder;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumableLiquid;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.world.level.material.Fluid;

public class InvSlotConsumableLiquidByList
extends InvSlotConsumableLiquid {
    private final Set<Fluid> acceptedFluids;

    public InvSlotConsumableLiquidByList(IInventorySlotHolder<?> iInventorySlotHolder, String string, int n, Fluid ... fluidArray) {
        super(iInventorySlotHolder, string, n);
        this.acceptedFluids = new HashSet<Fluid>(Arrays.asList(fluidArray));
    }

    public InvSlotConsumableLiquidByList(IInventorySlotHolder<?> iInventorySlotHolder, String string, InvSlot.Access access, int n, InvSlot.InvSide invSide, InvSlotConsumableLiquid.OpType opType, Fluid ... fluidArray) {
        super(iInventorySlotHolder, string, access, n, invSide, opType);
        this.acceptedFluids = new HashSet<Fluid>(Arrays.asList(fluidArray));
    }

    @Override
    protected boolean acceptsLiquid(Fluid fluid) {
        return this.acceptedFluids.contains(fluid);
    }

    @Override
    protected Iterable<Fluid> getPossibleFluids() {
        return this.acceptedFluids;
    }
}

