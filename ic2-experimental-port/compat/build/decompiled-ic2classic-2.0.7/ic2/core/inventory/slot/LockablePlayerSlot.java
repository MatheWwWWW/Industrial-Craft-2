/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.Container
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.inventory.slot;

import java.util.function.BooleanSupplier;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class LockablePlayerSlot
extends Slot {
    BooleanSupplier locked;

    public LockablePlayerSlot(Container p_i1824_1_, int slot, int x, int y, BooleanSupplier locked) {
        super(p_i1824_1_, slot, x, y);
        this.locked = locked;
    }

    public boolean m_8010_(Player p_82869_1_) {
        return !this.locked.getAsBoolean() && super.m_8010_(p_82869_1_);
    }

    public boolean m_5857_(ItemStack p_75214_1_) {
        return !this.locked.getAsBoolean() && super.m_5857_(p_75214_1_);
    }
}

