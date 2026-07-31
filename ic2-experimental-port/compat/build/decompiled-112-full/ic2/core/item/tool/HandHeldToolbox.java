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

import ic2.api.item.ItemWrapper;
import ic2.core.ContainerBase;
import ic2.core.item.tool.ContainerToolbox;
import ic2.core.item.tool.GuiToolbox;
import ic2.core.item.tool.HandHeldInventory;
import ic2.core.util.StackUtil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HandHeldToolbox
extends HandHeldInventory {
    public HandHeldToolbox(EntityPlayer player, ItemStack stack, int inventorySize) {
        super(player, stack, inventorySize);
    }

    public ContainerBase<HandHeldToolbox> getGuiContainer(EntityPlayer player) {
        return new ContainerToolbox(player, this);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
        return new GuiToolbox(new ContainerToolbox(player, this));
    }

    public String func_70005_c_() {
        return "toolbox";
    }

    public boolean func_145818_k_() {
        return false;
    }

    @Override
    public boolean func_94041_b(int i, ItemStack itemstack) {
        if (StackUtil.isEmpty(itemstack)) {
            return false;
        }
        return ItemWrapper.canBeStoredInToolbox(itemstack);
    }
}

