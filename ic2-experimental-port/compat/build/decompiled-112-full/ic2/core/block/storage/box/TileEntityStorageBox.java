/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.SoundType
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.util.ITooltipFlag
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.util.EnumFacing
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block.storage.box;

import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlot;
import ic2.core.gui.dynamic.DynamicContainer;
import ic2.core.gui.dynamic.DynamicGui;
import ic2.core.gui.dynamic.GuiParser;
import ic2.core.util.StackUtil;
import java.util.Collections;
import java.util.List;
import net.minecraft.block.SoundType;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class TileEntityStorageBox
extends TileEntityInventory
implements IHasGui {
    protected final InvSlot inventory;

    public TileEntityStorageBox(int inventorySize) {
        this.inventory = new InvSlot(this, "inventory", InvSlot.Access.IO, inventorySize, InvSlot.InvSide.ANY);
    }

    @Override
    protected List<ItemStack> getAuxDrops(int fortune) {
        return Collections.emptyList();
    }

    @Override
    public void onPlaced(ItemStack stack, EntityLivingBase placer, EnumFacing facing) {
        super.onPlaced(stack, placer, facing);
        if (!this.func_145831_w().field_72995_K) {
            NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
            this.inventory.readFromNbt(nbt);
        }
    }

    @Override
    protected ItemStack adjustDrop(ItemStack drop, boolean wrench) {
        NBTTagCompound nbt = StackUtil.getOrCreateNbtData(drop);
        if (!this.inventory.isEmpty()) {
            this.inventory.writeToNbt(nbt);
        }
        return drop;
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void addInformation(ItemStack stack, List<String> info, ITooltipFlag advanced) {
        info.add("Stores items even when broken");
        info.add("Inventory size: " + this.inventory.size());
    }

    @Override
    protected SoundType getBlockSound(Entity entity) {
        return SoundType.field_185852_e;
    }

    public ContainerBase<? extends TileEntityStorageBox> getGuiContainer(EntityPlayer player) {
        return DynamicContainer.create(this, player, GuiParser.parse(this.teBlock));
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
        return DynamicGui.create(this, player, GuiParser.parse(this.teBlock));
    }

    @Override
    public void onGuiClosed(EntityPlayer player) {
    }
}

