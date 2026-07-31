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
import ic2.core.item.tool.ContainerMeter;
import ic2.core.item.tool.GuiToolMeter;
import ic2.core.item.tool.HandHeldInventory;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HandHeldMeter
extends HandHeldInventory {
    public HandHeldMeter(EntityPlayer player, ItemStack stack) {
        super(player, stack, 0);
    }

    @Override
    public ContainerBase<?> getGuiContainer(EntityPlayer player) {
        return new ContainerMeter(player, this);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
        return new GuiToolMeter(new ContainerMeter(player, this));
    }

    public String func_70005_c_() {
        return "ic2.meter";
    }

    public boolean func_145818_k_() {
        return false;
    }

    void closeGUI() {
        this.player.func_71053_j();
    }
}

