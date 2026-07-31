/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.EntityPlayerMP
 *  net.minecraft.inventory.ClickType
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.inventory.Slot
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.server.SPacketHeldItemChange
 */
package ic2.core.item;

import ic2.core.ContainerBase;
import ic2.core.item.tool.HandHeldInventory;
import ic2.core.util.StackUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketHeldItemChange;

public class ContainerHandHeldInventory<T extends HandHeldInventory>
extends ContainerBase<T> {
    public ContainerHandHeldInventory(T inventory) {
        super(inventory);
    }

    @Override
    public ItemStack func_184996_a(int slot, int button, ClickType type, EntityPlayer player) {
        ItemStack held;
        boolean closeGUI = false;
        block0 : switch (type) {
            case CLONE: {
                break;
            }
            case PICKUP: {
                if (slot < 0 || slot >= this.field_75151_b.size()) break;
                closeGUI = ((HandHeldInventory)this.base).isThisContainer(((Slot)this.field_75151_b.get(slot)).func_75211_c());
                break;
            }
            case PICKUP_ALL: {
                break;
            }
            case QUICK_CRAFT: {
                break;
            }
            case QUICK_MOVE: {
                if (slot < 0 || slot >= this.field_75151_b.size() || !((HandHeldInventory)this.base).isThisContainer(((Slot)this.field_75151_b.get(slot)).func_75211_c())) break;
                return StackUtil.emptyStack;
            }
            case SWAP: {
                assert (slot >= 0 && slot < this.field_75151_b.size());
                assert (this.func_75147_a((IInventory)player.field_71071_by, button) != null);
                boolean swapOut = ((HandHeldInventory)this.base).isThisContainer(this.func_75147_a((IInventory)player.field_71071_by, button).func_75211_c());
                boolean swapTo = ((HandHeldInventory)this.base).isThisContainer(((Slot)this.field_75151_b.get(slot)).func_75211_c());
                if (!swapOut && !swapTo) break;
                for (int i = 0; i < 9; ++i) {
                    if ((!swapOut || slot != this.func_75147_a((IInventory)player.field_71071_by, (int)i).field_75222_d) && (!swapTo || button != i)) continue;
                    if (!(player instanceof EntityPlayerMP)) break block0;
                    ((EntityPlayerMP)player).field_71135_a.func_147359_a((Packet)new SPacketHeldItemChange(i));
                    break block0;
                }
                break;
            }
            case THROW: {
                if (slot < 0 || slot >= this.field_75151_b.size()) break;
                closeGUI = ((HandHeldInventory)this.base).isThisContainer(((Slot)this.field_75151_b.get(slot)).func_75211_c());
                break;
            }
            default: {
                throw new RuntimeException("Unexpected ClickType: " + type);
            }
        }
        ItemStack stack = super.func_184996_a(slot, button, type, player);
        if (closeGUI && !player.func_130014_f_().field_72995_K) {
            ((HandHeldInventory)this.base).saveAsThrown(stack);
            player.func_71053_j();
        } else if (type == ClickType.CLONE && ((HandHeldInventory)this.base).isThisContainer(held = player.field_71071_by.func_70445_o())) {
            held.func_77978_p().func_82580_o("uid");
        }
        return stack;
    }

    public void func_75134_a(EntityPlayer player) {
        ((HandHeldInventory)this.base).onGuiClosed(player);
        super.func_75134_a(player);
    }
}

