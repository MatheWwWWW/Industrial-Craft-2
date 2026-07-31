/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.material.Fluid
 */
package ic2.core.block.invslot;

import ic2.api.recipe.ILiquidAcceptManager;
import ic2.core.block.IInventorySlotHolder;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumableLiquid;
import net.minecraft.world.level.material.Fluid;

public class InvSlotConsumableLiquidByManager
extends InvSlotConsumableLiquid {
    private final ILiquidAcceptManager manager;

    public InvSlotConsumableLiquidByManager(IInventorySlotHolder<?> iInventorySlotHolder, String string, int n, ILiquidAcceptManager iLiquidAcceptManager) {
        super(iInventorySlotHolder, string, n);
        this.manager = iLiquidAcceptManager;
    }

    public InvSlotConsumableLiquidByManager(IInventorySlotHolder<?> iInventorySlotHolder, String string, InvSlot.Access access, int n, InvSlot.InvSide invSide, InvSlotConsumableLiquid.OpType opType, ILiquidAcceptManager iLiquidAcceptManager) {
        super(iInventorySlotHolder, string, access, n, invSide, opType);
        this.manager = iLiquidAcceptManager;
    }

    @Override
    protected boolean acceptsLiquid(Fluid fluid) {
        return this.manager.acceptsFluid(fluid);
    }

    @Override
    protected Iterable<Fluid> getPossibleFluids() {
        return this.manager.getAcceptedFluids();
    }
}

