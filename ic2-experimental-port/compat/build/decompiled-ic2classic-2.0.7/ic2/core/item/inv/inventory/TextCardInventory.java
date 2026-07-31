/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.inv.inventory;

import ic2.core.inventory.base.IHasHeldGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.inv.PortableInventory;
import ic2.core.item.inv.container.TextCardContainer;
import ic2.core.networking.buffers.data.StringDataBuffer;
import ic2.core.utils.helpers.NBTUtils;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

public class TextCardInventory
extends PortableInventory {
    public DyeColor color = DyeColor.WHITE;
    public String text = "";

    public TextCardInventory(Player owner, IHasHeldGui held, ItemStack stack, Slot slot) {
        super(owner, held, stack, slot);
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new TextCardContainer(this, player, this.getID(), windowID);
    }

    @Override
    public int getSlotCount() {
        return 0;
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.text = nbt.m_128461_("text");
        this.color = NBTUtils.getEnum(nbt, "color", DyeColor.class, DyeColor.WHITE);
    }

    @Override
    public void save(CompoundTag nbt) {
        super.save(nbt);
        NBTUtils.putString(nbt, "text", this.text, "");
        NBTUtils.putEnum(nbt, "color", this.color, DyeColor.WHITE);
    }

    public void onDataReceived(int key, int value) {
        if (key == 0) {
            this.color = DyeColor.m_41053_((int)value);
        }
        this.markDirty();
    }

    public void onDataReceived(StringDataBuffer buffer) {
        if (buffer.getId() == 0) {
            this.text = this.text + buffer.getValue();
        } else if (buffer.getId() == 1) {
            this.text = this.text.substring(0, this.text.length() - buffer.getValue().length());
        } else if (buffer.getId() == 2) {
            this.text = buffer.getValue();
        }
        if (this.text == null) {
            this.text = "";
        }
        this.markDirty();
    }
}

