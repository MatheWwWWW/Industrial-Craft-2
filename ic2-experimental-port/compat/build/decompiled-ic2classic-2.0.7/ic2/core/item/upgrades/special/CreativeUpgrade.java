/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.fluids.capability.IFluidHandler
 *  net.minecraftforge.fluids.capability.IFluidHandler$FluidAction
 */
package ic2.core.item.upgrades.special;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.items.IUpgradeItem;
import ic2.api.tiles.IMachine;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.item.upgrades.base.BaseUpgradeItem;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class CreativeUpgrade
extends BaseUpgradeItem.SimpleUpgradeItem {
    public CreativeUpgrade() {
        super("creative");
        this.functions.add(IUpgradeItem.Functions.TICK);
    }

    @Override
    public IUpgradeItem.UpgradeType getType(ItemStack stack) {
        return IUpgradeItem.UpgradeType.CUSTOM_MOD;
    }

    @Override
    public void onTick(ItemStack stack, IMachine machine) {
        IFluidHandler handler;
        IEnergySink sink;
        double needed;
        if (machine instanceof IEnergySink && (needed = (double)(sink = (IEnergySink)((Object)machine)).getRequestedEnergy()) > 1.0) {
            sink.acceptEnergy(Direction.UP, EnergyNet.INSTANCE.getPowerFromTier(sink.getSinkTier()) - 1, 0);
        }
        if (machine instanceof BaseTileEntity && (handler = (IFluidHandler)((BaseTileEntity)((Object)machine)).getInternalCapability(ForgeCapabilities.FLUID_HANDLER)) != null) {
            handler.drain(1000, IFluidHandler.FluidAction.EXECUTE);
        }
    }

    @Override
    public int getExtraProcessingSpeed(ItemStack stack, IMachine machine) {
        return Short.MAX_VALUE;
    }

    @Override
    public double getProcessingTimeMultiplier(ItemStack stack, IMachine machine) {
        return 0.0;
    }

    @Override
    public double getEnergyDemandMultiplier(ItemStack stack, IMachine machine) {
        return 0.0;
    }

    @Override
    public int getExtraTier(ItemStack stack, IMachine machine) {
        return 13;
    }
}

