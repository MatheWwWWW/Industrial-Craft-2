/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.logic;

import ic2.core.inventory.base.INBTSavable;
import ic2.core.utils.helpers.StackUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public abstract class TickableItemLogic
implements INBTSavable {
    Player player;

    public TickableItemLogic(ItemStack stack, Player player) {
        this.player = player;
    }

    public abstract void onTick(ItemStack var1);

    public final TickableItemLogic load(ItemStack stack) {
        this.load(this.getNBTData(stack, false));
        return this;
    }

    public final void save(ItemStack stack) {
        this.save(this.getNBTData(stack, true));
    }

    public Player getPlayer() {
        return this.player;
    }

    public CompoundTag getNBTData(ItemStack stack, boolean create) {
        return create ? stack.m_41784_() : StackUtil.getNbtData(stack);
    }
}

