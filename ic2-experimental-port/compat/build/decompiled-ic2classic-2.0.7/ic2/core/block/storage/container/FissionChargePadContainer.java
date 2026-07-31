/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.Slot
 */
package ic2.core.block.storage.container;

import ic2.api.reactor.IUsableUranium;
import ic2.core.block.base.tiles.impls.BaseChargePadTileEntity;
import ic2.core.block.storage.container.ChargePadContainer;
import ic2.core.inventory.filter.ArrayOrFilter;
import ic2.core.inventory.filter.ClassFilter;
import ic2.core.inventory.filter.special.ElectricItemFilter;
import ic2.core.inventory.slot.FilterSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;

public class FissionChargePadContainer
extends ChargePadContainer {
    public FissionChargePadContainer(BaseChargePadTileEntity key, Player player, int id) {
        super(key, player, id);
    }

    @Override
    protected Slot createDischargeSlot(BaseChargePadTileEntity key) {
        return new FilterSlot(key, 0, 8, 56, new ArrayOrFilter(new ElectricItemFilter(false, true, key.tier), new ClassFilter(IUsableUranium.class)));
    }
}

