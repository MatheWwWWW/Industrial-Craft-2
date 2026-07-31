/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientboundSetCarriedItemPacket
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.Container
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.ClickType
 *  net.minecraft.world.inventory.MenuType
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item;

import ic2.core.ContainerBase;
import ic2.core.item.tool.HandHeldInventory;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundSetCarriedItemPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ContainerHandHeldInventory<T extends HandHeldInventory>
extends ContainerBase<T> {
    public ContainerHandHeldInventory(MenuType<?> menuType, int n, T t) {
        super(menuType, n, ((HandHeldInventory)t).player.m_150109_(), t);
    }

    @Override
    public void m_150399_(int n, int n2, ClickType clickType, Player player) {
        ItemStack itemStack;
        ItemStack itemStack2 = null;
        boolean bl = false;
        switch (clickType) {
            case CLONE: {
                break;
            }
            case PICKUP: 
            case THROW: {
                if (n < 0 || n >= this.f_38839_.size()) break;
                itemStack2 = ((Slot)this.f_38839_.get(n)).m_7993_();
                bl = ((HandHeldInventory)this.base).isThisContainer(itemStack2);
                break;
            }
            case PICKUP_ALL: {
                break;
            }
            case QUICK_CRAFT: {
                break;
            }
            case QUICK_MOVE: {
                if (n < 0 || n >= this.f_38839_.size() || !((HandHeldInventory)this.base).isThisContainer(((Slot)this.f_38839_.get(n)).m_7993_())) break;
                return;
            }
            case SWAP: {
                assert (n >= 0 && n < this.f_38839_.size());
                int n3 = this.m_182417_((Container)player.m_150109_(), n2).orElse(-1);
                assert (n3 >= 0);
                int n4 = -1;
                if (((HandHeldInventory)this.base).isThisContainer(player.m_150109_().m_8020_(n2))) {
                    Slot slot = (Slot)this.f_38839_.get(n);
                    int n5 = slot.m_150661_();
                    if (slot.f_40218_ == player.m_150109_() && n5 >= 0 && n5 < 9) {
                        n4 = n5;
                    }
                } else if (((HandHeldInventory)this.base).isThisContainer(((Slot)this.f_38839_.get(n)).m_7993_())) {
                    n4 = n2;
                }
                if (n4 < 0 || !(player instanceof ServerPlayer)) break;
                ((ServerPlayer)player).f_8906_.m_9829_((Packet)new ClientboundSetCarriedItemPacket(n4));
                break;
            }
            default: {
                throw new RuntimeException("Unexpected ClickType: " + clickType);
            }
        }
        super.m_150399_(n, n2, clickType, player);
        if (bl && !player.m_20193_().f_46443_) {
            assert (itemStack2 != null);
            ((HandHeldInventory)this.base).saveAsThrown(itemStack2);
            ((ServerPlayer)player).m_6915_();
        } else if (clickType == ClickType.CLONE && ((HandHeldInventory)this.base).isThisContainer(itemStack = this.m_142621_())) {
            itemStack.m_41783_().m_128473_("uid");
        }
    }

    public void m_6877_(Player player) {
        ((HandHeldInventory)this.base).onScreenClosed(player);
        super.m_6877_(player);
    }
}

