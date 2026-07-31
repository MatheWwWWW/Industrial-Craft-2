/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.upgrades.io.item;

import ic2.api.recipes.ingridients.queue.IStackOutput;
import ic2.api.recipes.registries.IMachineRecipeList;
import ic2.api.tiles.IMachine;
import ic2.core.inventory.base.IHasHeldSlotInventory;
import ic2.core.inventory.base.IPortableInventory;
import ic2.core.inventory.filter.ArrayAndFilter;
import ic2.core.inventory.filter.StackFilter;
import ic2.core.inventory.filter.special.MachineFilter;
import ic2.core.inventory.transporter.IItemTransporter;
import ic2.core.inventory.transporter.TransporterManager;
import ic2.core.item.inv.inventory.ImportExportInventory;
import ic2.core.item.upgrades.base.BaseTransportUpgrade;
import java.util.List;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ImportUpgradeItem
extends BaseTransportUpgrade
implements IHasHeldSlotInventory {
    public ImportUpgradeItem() {
        super("import");
    }

    @Override
    public void onTick(ItemStack stack, IMachine machine) {
        if (machine.getWorldObj().m_46467_() % 20L == 0L) {
            this.importItems(stack, machine);
        }
    }

    @Override
    public void onMachineFinishedRecipePost(ItemStack stack, IMachine machine, IMachineRecipeList.RecipeEntry entry, List<IStackOutput> drops) {
        if (drops.isEmpty() || entry != null && entry.getInputs().length == 1 && entry.getInputs()[0].getInputSize() < 4) {
            return;
        }
        this.importItems(stack, machine);
    }

    @Override
    public void onMachineProcessed(ItemStack stack, IMachine machine) {
        this.importItems(stack, machine);
    }

    public void importItems(ItemStack stack, IMachine machine) {
        ItemStack found;
        int totalAdded;
        int added;
        Direction dir = this.getFacing(stack);
        if (dir == null) {
            return;
        }
        IItemTransporter source = TransporterManager.getTransporter(machine.getConnectedInventory(dir));
        if (source == null) {
            return;
        }
        IItemTransporter target = TransporterManager.getTransporter(machine);
        if (target == null) {
            return;
        }
        int size = Math.min(machine.getAvailableEnergy() * 3, this.getTransferSize(stack));
        if (size <= 0) {
            return;
        }
        ArrayAndFilter filter = new ArrayAndFilter(new MachineFilter(machine), this.getFilter(stack));
        for (totalAdded = 0; size > totalAdded && !(found = source.removeItem(filter, dir.m_122424_(), size, true)).m_41619_() && (added = target.addItem(found, dir, false)) > 0; totalAdded += added) {
            source.removeItem(StackFilter.defaultCompare(found), dir.m_122424_(), added, false);
        }
        if (totalAdded > 0) {
            machine.useEnergy(Math.max(1, totalAdded / 3), true);
        }
    }

    @Override
    public IPortableInventory getInventory(Player player, InteractionHand hand, ItemStack stack) {
        return new ImportExportInventory(player, this, stack, null).load(stack);
    }

    @Override
    public IPortableInventory getInventory(Player player, ItemStack stack, Slot slot) {
        return new ImportExportInventory(player, this, stack, slot).load(stack);
    }
}

