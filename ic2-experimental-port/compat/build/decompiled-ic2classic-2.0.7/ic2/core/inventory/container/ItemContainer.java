/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.ClickType
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.inventory.container;

import ic2.core.IC2;
import ic2.core.inventory.base.IHasHeldGui;
import ic2.core.inventory.base.IPortableInventory;
import ic2.core.inventory.container.ContainerComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;

public abstract class ItemContainer<T extends IPortableInventory>
extends ContainerComponent<T> {
    int id;

    public ItemContainer(T key, Player player, int id, int windowID) {
        super(key, player, windowID);
        this.id = id;
    }

    @Override
    public Component getName() {
        return ((IPortableInventory)this.getHolder()).getInventoryStack().m_41786_();
    }

    @Override
    public int getInventorySize() {
        return ((IPortableInventory)this.getHolder()).getSlotCount();
    }

    public void m_150399_(int slotId, int dragType, ClickType clickTypeIn, Player player) {
        if (IC2.PLATFORM.isSimulating()) {
            int id;
            ItemStack stack;
            if (clickTypeIn == ClickType.THROW) {
                if (slotId == -999) {
                    if (!this.m_142621_().m_41619_()) {
                        ((IPortableInventory)this.gui).onGuiClosed(player);
                        player.m_6915_();
                    }
                } else {
                    int id2;
                    ItemStack stack2 = this.m_38853_(slotId).m_7993_();
                    if (stack2.m_41720_() instanceof IHasHeldGui && (id2 = ((IHasHeldGui)stack2.m_41720_()).getGuiId(stack2)) == this.id) {
                        ((IPortableInventory)this.gui).onGuiClosed(player);
                        player.m_6915_();
                    }
                }
            } else if (slotId == -999 && clickTypeIn == ClickType.PICKUP && (stack = this.m_142621_()).m_41720_() instanceof IHasHeldGui && (id = ((IHasHeldGui)stack.m_41720_()).getGuiId(stack)) == this.id) {
                ((IPortableInventory)this.gui).onGuiClosed(player);
                player.m_6915_();
            }
        }
        super.m_150399_(slotId, dragType, clickTypeIn, player);
    }
}

