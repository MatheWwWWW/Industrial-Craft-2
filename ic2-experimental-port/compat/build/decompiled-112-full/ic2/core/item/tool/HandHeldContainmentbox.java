/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.item.tool;

import ic2.core.ContainerBase;
import ic2.core.item.reactor.ItemReactorMOX;
import ic2.core.item.reactor.ItemReactorUranium;
import ic2.core.item.tool.ContainerContainmentbox;
import ic2.core.item.tool.GuiContainmentbox;
import ic2.core.item.tool.HandHeldInventory;
import ic2.core.ref.ItemName;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HandHeldContainmentbox
extends HandHeldInventory {
    public HandHeldContainmentbox(EntityPlayer player, ItemStack stack1, int inventorySize) {
        super(player, stack1, inventorySize);
    }

    public ContainerBase<HandHeldContainmentbox> getGuiContainer(EntityPlayer player) {
        return new ContainerContainmentbox(player, this);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
        return new GuiContainmentbox(new ContainerContainmentbox(player, this));
    }

    public String func_70005_c_() {
        return "ic2.containment_box";
    }

    public boolean func_145818_k_() {
        return false;
    }

    @Override
    public boolean func_94041_b(int index, ItemStack stack) {
        if (stack == null) {
            return false;
        }
        return stack.func_77973_b() == ItemName.nuclear.getInstance() || stack.func_77973_b() instanceof ItemReactorMOX || stack.func_77973_b() instanceof ItemReactorUranium;
    }
}

