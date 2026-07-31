/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.fluids.capability.IFluidHandler
 *  net.minecraftforge.fluids.capability.IFluidHandler$FluidAction
 */
package ic2.core.item.upgrades.io.fluid;

import ic2.api.items.IUpgradeItem;
import ic2.api.tiles.IFluidMachine;
import ic2.api.tiles.IMachine;
import ic2.core.item.upgrades.base.BaseDirectionalUpgrade;
import ic2.core.utils.tooltips.ToolTipHelper;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class SimpleFluidImportUpgrade
extends BaseDirectionalUpgrade {
    public SimpleFluidImportUpgrade() {
        super("basic_fluid_import");
        this.functions.remove((Object)IUpgradeItem.Functions.RECIPE);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        super.addToolTip(stack, player, type, helper);
        helper.addSimpleToolTip("tooltip.item.ic2.upgrade.stacking", new Object[0]);
    }

    @Override
    public IUpgradeItem.UpgradeType getType(ItemStack stack) {
        return IUpgradeItem.UpgradeType.TRANSPORT_MOD;
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
        source.drain(target.fill(source.drain(Math.min(90, stack.m_41613_() * 10), IFluidHandler.FluidAction.SIMULATE), IFluidHandler.FluidAction.EXECUTE), IFluidHandler.FluidAction.EXECUTE);
    }
}

