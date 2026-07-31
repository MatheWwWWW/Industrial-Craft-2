/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.inventory.CraftingContainer
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.inventory.inv;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;

public class IC2CraftingInventory
extends CraftingContainer {
    public IC2CraftingInventory(int width, int height) {
        super(new AbstractContainerMenu(null, height){

            public boolean m_6875_(Player playerIn) {
                return false;
            }

            public ItemStack m_7648_(Player p_38941_, int p_38942_) {
                return ItemStack.f_41583_;
            }
        }, width, height);
    }
}

