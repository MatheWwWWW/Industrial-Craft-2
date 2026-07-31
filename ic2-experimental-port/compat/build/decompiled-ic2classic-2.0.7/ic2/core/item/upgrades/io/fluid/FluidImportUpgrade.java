/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.fluids.capability.IFluidHandler
 */
package ic2.core.item.upgrades.io.fluid;

import ic2.api.tiles.IFluidMachine;
import ic2.api.tiles.IMachine;
import ic2.core.item.upgrades.base.BaseFluidTransportUpgrade;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class FluidImportUpgrade
extends BaseFluidTransportUpgrade {
    public FluidImportUpgrade() {
        super("fluid_import");
    }

    @Override
    public void onTick(ItemStack stack, IMachine machine) {
        if (!(machine instanceof IFluidMachine)) {
            return;
        }
        Direction dir = this.getFacing(stack);
        if (dir == null) {
            return;
        }
        IFluidHandler source = ((IFluidMachine)((Object)machine)).getConnectedTank(dir);
        if (source == null) {
            return;
        }
        IFluidHandler target = (IFluidHandler)this.getCapability(machine, dir, ForgeCapabilities.FLUID_HANDLER);
        if (target == null) {
            return;
        }
        this.transferFluid(machine, source, target, Math.min(this.transferRate(stack), machine.getAvailableEnergy() * 100), this.getFluids(stack), this.isInverted(stack));
    }
}

