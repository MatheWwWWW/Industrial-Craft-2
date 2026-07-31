/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.inventory.transporter.transporters;

import ic2.core.inventory.transporter.IItemTransporter;
import ic2.core.utils.helpers.StackUtil;
import net.minecraft.world.item.ItemStack;

public abstract class BaseTransporter
implements IItemTransporter {
    protected ItemStack copyWithSize(ItemStack stack, int amount) {
        return StackUtil.copyWithSize(stack, amount);
    }
}

