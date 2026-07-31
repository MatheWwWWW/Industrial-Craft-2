/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.inventory.base;

import ic2.core.inventory.base.IPortableInventory;
import ic2.core.utils.helpers.StackUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IHasHeldGui {
    public static final String DEFAULT_GUI_ID = "GUI_ID";

    public IPortableInventory getInventory(Player var1, InteractionHand var2, ItemStack var3);

    default public int getGuiId(ItemStack stack) {
        return StackUtil.getNbtData(stack).m_128451_(DEFAULT_GUI_ID);
    }

    default public void setGuiID(ItemStack stack, int id) {
        if (id == -1) {
            stack.m_41749_(DEFAULT_GUI_ID);
            return;
        }
        stack.m_41784_().m_128405_(DEFAULT_GUI_ID, id);
    }
}

