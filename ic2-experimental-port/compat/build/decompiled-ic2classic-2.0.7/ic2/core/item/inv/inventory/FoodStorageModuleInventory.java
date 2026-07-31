/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.inv.inventory;

import ic2.core.inventory.base.IHasHeldGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.inv.PortableInventory;
import ic2.core.item.inv.container.FoodStorageModuleContainer;
import ic2.core.utils.helpers.NBTUtils;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class FoodStorageModuleInventory
extends PortableInventory {
    public FoodStorageModuleInventory(Player owner, IHasHeldGui held, ItemStack stack, Slot slot) {
        super(owner, held, stack, slot);
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new FoodStorageModuleContainer(this, player, this.getID(), windowID);
    }

    @Override
    public void load(CompoundTag nbt) {
        NBTUtils.loadItems(nbt.m_128469_("food_storage").m_128469_("inventory"), (NonNullList<ItemStack>)this.inventory);
    }

    @Override
    public void save(CompoundTag nbt) {
        CompoundTag data = new CompoundTag();
        NBTUtils.saveItems(data, (NonNullList<ItemStack>)this.inventory);
        nbt.m_128469_("food_storage").m_128365_("inventory", (Tag)data);
    }

    @Override
    public int getSlotCount() {
        return 4;
    }
}

