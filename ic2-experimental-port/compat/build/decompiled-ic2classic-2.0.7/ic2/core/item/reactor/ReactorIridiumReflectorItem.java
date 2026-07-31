/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.reactor;

import ic2.api.items.IRepairable;
import ic2.core.item.reactor.ReactorReflectorItem;
import net.minecraft.world.item.ItemStack;

public class ReactorIridiumReflectorItem
extends ReactorReflectorItem
implements IRepairable {
    public ReactorIridiumReflectorItem() {
        super("reflector_iridium", "reactor/reflector", "iridium_neutron", 320000, true, 53);
    }

    @Override
    public boolean repairDamage(ItemStack stack, int amount) {
        if (stack.m_41773_() > 0) {
            stack.m_41721_(Math.max(0, stack.m_41773_() - amount));
            return true;
        }
        return false;
    }
}

