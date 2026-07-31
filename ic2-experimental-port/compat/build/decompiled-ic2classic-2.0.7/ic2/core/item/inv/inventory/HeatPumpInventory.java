/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.inv.inventory;

import ic2.core.inventory.base.IHasHeldGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.inv.PortableInventory;
import ic2.core.item.inv.container.HeatPumpContainer;
import ic2.core.utils.helpers.NBTUtils;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class HeatPumpInventory
extends PortableInventory {
    public int directions;

    public HeatPumpInventory(Player owner, IHasHeldGui held, ItemStack stack, Slot slot) {
        super(owner, held, stack, slot);
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new HeatPumpContainer(this, player, windowID, windowID);
    }

    @Override
    public int getSlotCount() {
        return 0;
    }

    @Override
    public void save(CompoundTag nbt) {
        NBTUtils.putByte(nbt, "directions", this.directions, 0);
    }

    @Override
    public void load(CompoundTag nbt) {
        this.directions = nbt.m_128451_("directions");
    }

    public void onDataReceived(int key, int value) {
        if (value == 0) {
            this.directions |= 1 << key;
            this.markDirty();
            return;
        }
        this.directions &= ~(1 << key);
        this.markDirty();
    }
}

