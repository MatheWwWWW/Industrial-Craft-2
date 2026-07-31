/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.inventory.filter.special;

import ic2.api.tiles.IInputMachine;
import ic2.core.inventory.filter.IFilter;
import net.minecraft.world.item.ItemStack;

public class MachineFilter
implements IFilter {
    IInputMachine machine;

    public MachineFilter(IInputMachine machine) {
        this.machine = machine;
    }

    @Override
    public boolean matches(ItemStack input) {
        return this.machine.getValidRoom(input) > 0;
    }
}

