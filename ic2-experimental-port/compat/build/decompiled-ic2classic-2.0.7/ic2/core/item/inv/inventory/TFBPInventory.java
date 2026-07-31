/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.inv.inventory;

import ic2.core.inventory.base.IHasHeldGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.inv.PortableInventory;
import ic2.core.item.inv.container.TFBPContainer;
import ic2.core.item.misc.tfbp.TerraformerBlueprintItem;
import ic2.core.utils.helpers.NBTUtils;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class TFBPInventory
extends PortableInventory {
    public int radius = 0;

    public TFBPInventory(Player owner, IHasHeldGui held, ItemStack stack, Slot slot) {
        super(owner, held, stack, slot);
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new TFBPContainer(this, player, this.getID(), windowID);
    }

    @Override
    public int getSlotCount() {
        return 0;
    }

    public void onDataReceived(int key, int value) {
        if (key == 0) {
            this.radius = value;
        }
        this.markDirty();
    }

    @Override
    public void load(CompoundTag nbt) {
        this.radius = NBTUtils.getInt(nbt, "radius", this.getRadius());
    }

    @Override
    public void save(CompoundTag nbt) {
        if (this.radius != this.getRadius()) {
            NBTUtils.putInt(nbt, "radius", this.radius, 0);
        } else {
            nbt.m_128473_("radius");
        }
    }

    private int getRadius() {
        Item item = this.getInventoryStack().m_41720_();
        return item instanceof TerraformerBlueprintItem ? ((TerraformerBlueprintItem)item).getRadius(this.getInventoryStack()) : 0;
    }
}

