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
 */
package ic2.core.item.upgrades.io.item;

import ic2.api.items.IUpgradeItem;
import ic2.api.recipes.ingridients.queue.IStackOutput;
import ic2.api.recipes.registries.IMachineRecipeList;
import ic2.api.tiles.IMachine;
import ic2.core.inventory.filter.SpecialFilters;
import ic2.core.inventory.filter.StackFilter;
import ic2.core.inventory.transporter.IItemTransporter;
import ic2.core.inventory.transporter.TransporterManager;
import ic2.core.item.upgrades.base.BaseDirectionalUpgrade;
import ic2.core.utils.tooltips.ToolTipHelper;
import java.util.List;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SimpleExportUpgradeItem
extends BaseDirectionalUpgrade {
    public SimpleExportUpgradeItem() {
        super("simple_export");
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
        if (!machine.isMachineWorking() && machine.getWorldObj().m_46467_() % 20L == 0L) {
            this.sendItems(stack, machine);
        }
    }

    @Override
    public void onMachineProcessed(ItemStack stack, IMachine machine) {
        this.sendItems(stack, machine);
    }

    @Override
    public void onMachineFinishedRecipePost(ItemStack stack, IMachine machine, IMachineRecipeList.RecipeEntry entry, List<IStackOutput> drops) {
        if (drops.isEmpty()) {
            return;
        }
        Direction dir = this.getFacing(stack);
        if (dir == null) {
            return;
        }
        IItemTransporter target = TransporterManager.getTransporter(machine.getConnectedInventory(dir));
        if (target == null && (target = TransporterManager.getTransporter(machine.getConnectedTube(dir))) == null) {
            return;
        }
        int count = Math.min(stack.m_41613_(), 9);
        int m = drops.size();
        for (int i = 0; i < m && count > 0; ++i) {
            IStackOutput output = drops.get(i);
            ItemStack out = output.getStack().m_41777_().m_41620_(count);
            int added = target.addItem(out, dir.m_122424_(), false);
            if (added <= 0) continue;
            count -= added;
            output.getStack().m_41774_(added);
            if (!output.getStack().m_41619_()) continue;
            drops.remove(i--);
            --m;
        }
        if (count > 0) {
            IItemTransporter source = TransporterManager.getTransporter(machine);
            if (source == null) {
                return;
            }
            this.sendItems(count, source, target, dir);
        }
    }

    protected void sendItems(ItemStack stack, IMachine machine) {
        Direction dir = this.getFacing(stack);
        if (dir == null) {
            return;
        }
        IItemTransporter target = TransporterManager.getTransporter(machine.getConnectedInventory(dir));
        if (target == null && (target = TransporterManager.getTransporter(machine.getConnectedTube(dir))) == null) {
            return;
        }
        IItemTransporter source = TransporterManager.getTransporter(machine);
        if (source == null) {
            return;
        }
        this.sendItems(Math.min(stack.m_41613_(), 9), source, target, dir);
    }

    protected void sendItems(int left, IItemTransporter source, IItemTransporter target, Direction dir) {
        int added;
        ItemStack found;
        while (left > 0 && !(found = source.removeItem(SpecialFilters.ANY_ITEM, dir, left, true)).m_41619_() && (added = target.addItem(found, dir.m_122424_(), false)) > 0) {
            source.removeItem(StackFilter.defaultCompare(found), dir, added, false);
            left -= added;
        }
    }
}

