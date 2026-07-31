/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ic2.core.inventory.base.IHasHeldGui
 *  ic2.core.inventory.container.IC2Container
 *  ic2.core.inventory.inv.PortableInventory
 *  ic2.core.utils.helpers.StackUtil
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 */
package trinsdar.gravisuit.items.container;

import ic2.core.inventory.base.IHasHeldGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.inv.PortableInventory;
import ic2.core.utils.helpers.StackUtil;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import trinsdar.gravisuit.items.container.ItemContainerRelocatorAdd;
import trinsdar.gravisuit.items.container.ItemContainerRelocatorDisplay;
import trinsdar.gravisuit.items.tools.ItemRelocator;

public class ItemInventoryRelocator
extends PortableInventory {
    ItemStack relocator;
    InteractionHand hand;

    public ItemInventoryRelocator(Player player, ItemRelocator inv, ItemStack relocator, InteractionHand hand) {
        super(player, (IHasHeldGui)inv, relocator, null);
        this.relocator = relocator;
        this.hand = hand;
    }

    public IC2Container createContainer(Player player, InteractionHand interactionHand, Direction direction, int i) {
        CompoundTag nbt = StackUtil.getNbtData((ItemStack)this.relocator);
        if (player.m_6047_() && nbt.m_128445_("mode") == 0) {
            return new ItemContainerRelocatorAdd(this, this.getID(), this.hand, player, i);
        }
        return new ItemContainerRelocatorDisplay(this, this.getID(), this.hand, player, i);
    }

    public boolean hasGui(Player player, InteractionHand hand, Direction side) {
        return super.hasGui(player, hand, side);
    }

    public int getSlotCount() {
        return 0;
    }
}

