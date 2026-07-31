/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 */
package ic2.core.item.upgrades.special;

import ic2.api.items.IUpgradeItem;
import ic2.api.tiles.IMachine;
import ic2.core.block.base.features.IInventoryMachine;
import ic2.core.inventory.transporter.IItemTransporter;
import ic2.core.inventory.transporter.TransporterManager;
import ic2.core.item.upgrades.base.BaseUpgradeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

public class CobblegenUpgradeItem
extends BaseUpgradeItem.SimpleUpgradeItem {
    public CobblegenUpgradeItem() {
        super("cobble_generator");
        this.functions.add(IUpgradeItem.Functions.TICK);
    }

    @Override
    public IUpgradeItem.UpgradeType getType(ItemStack stack) {
        return IUpgradeItem.UpgradeType.CUSTOM_MOD;
    }

    @Override
    public void onTick(ItemStack stack, IMachine machine) {
        IItemTransporter trans;
        if (machine.getWorldObj().m_46467_() % 20L == 0L && (trans = TransporterManager.getTransporter(machine instanceof IInventoryMachine ? ((IInventoryMachine)machine).getInputInventory() : machine)) != null) {
            trans.addItem(new ItemStack((ItemLike)Items.f_42594_, stack.m_41613_()), null, false);
        }
    }
}

