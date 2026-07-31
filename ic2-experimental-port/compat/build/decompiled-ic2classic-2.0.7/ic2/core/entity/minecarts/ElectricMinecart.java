/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.Containers
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.vehicle.AbstractMinecart$Type
 *  net.minecraft.world.entity.vehicle.Minecart
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.common.capabilities.Capability
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.common.util.LazyOptional
 *  net.minecraftforge.items.IItemHandler
 */
package ic2.core.entity.minecarts;

import ic2.api.items.electric.ElectricItem;
import ic2.api.tiles.IEnergyStorage;
import ic2.core.IC2;
import ic2.core.entity.inv.container.EnergyMinecartContainer;
import ic2.core.inventory.base.IHasGui;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.filter.special.ElectricItemFilter;
import ic2.core.inventory.gui.ComponentContainerScreen;
import ic2.core.inventory.inv.IHasItemHandler;
import ic2.core.platform.registries.IC2Items;
import ic2.core.utils.helpers.NBTUtils;
import javax.annotation.Nullable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;

public abstract class ElectricMinecart
extends Minecart
implements IEnergyStorage,
IHasGui,
IHasInventory {
    public int storedEU;
    NonNullList<ItemStack> inventory = NonNullList.m_122780_((int)2, (Object)ItemStack.f_41583_);
    private LazyOptional<IItemHandler> itemHandler = LazyOptional.of(() -> new IHasItemHandler(this));

    public ElectricMinecart(EntityType<?> type, Level level) {
        super(type, level);
    }

    public ElectricMinecart(EntityType<?> type, Level level, double x, double y, double z) {
        super(type, level);
        this.m_6034_(x, y, z);
        this.f_19854_ = x;
        this.f_19855_ = y;
        this.f_19856_ = z;
    }

    public InteractionResult m_6096_(Player player, InteractionHand hand) {
        return IC2.PLATFORM.launchGui(player, hand, null, this) ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }

    public AbstractMinecart.Type m_6064_() {
        return AbstractMinecart.Type.CHEST;
    }

    public abstract BlockState m_6390_();

    public ItemStack m_142340_() {
        return new ItemStack((ItemLike)this.m_213728_());
    }

    protected abstract Item m_213728_();

    @Override
    public int getStoredEU() {
        return this.storedEU;
    }

    @Override
    public int addEnergy(int power) {
        int added = Math.min(this.getMaxEU() - this.storedEU, power);
        this.storedEU += added;
        return added;
    }

    @Override
    public int drawEnergy(int power) {
        int draw = Math.min(this.storedEU, power);
        this.storedEU -= draw;
        return draw;
    }

    @Override
    public int getSlotCount() {
        return 2;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return (ItemStack)this.inventory.get(slot);
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        this.inventory.set(slot, (Object)stack);
    }

    @Override
    public int getMaxStackSize(int slot) {
        return 64;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack) {
        if (slot == 0) {
            return ElectricItemFilter.CHARGE_FILTER.matches(stack);
        }
        return ElectricItemFilter.DISCHARGE_EXTERNAL_FILTER.matches(stack) || stack.m_150930_(Items.f_42451_) || stack.m_150930_(Items.f_42153_) || stack.m_150930_(IC2Items.SU_BATTERY);
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack) {
        if (slot == 0) {
            return ElectricItemFilter.NOT_CHARGE_FILTER.matches(stack);
        }
        return ElectricItemFilter.NOT_DISCHARGE_FILTER.matches(stack);
    }

    public int getGuiOffset() {
        return 0;
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new EnergyMinecartContainer(this, player, windowID);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public Screen createGui(Player player, InteractionHand hand, Direction side, IC2Container container) {
        return new ComponentContainerScreen((ContainerComponent)container);
    }

    @Override
    public boolean canInteractWith(Player player) {
        return !this.m_213877_() && player.m_6084_();
    }

    protected void m_7380_(CompoundTag nbt) {
        super.m_7380_(nbt);
        NBTUtils.saveItems(nbt, this.inventory);
        NBTUtils.putInt(nbt, "energy", this.storedEU, 0);
    }

    protected void m_7378_(CompoundTag nbt) {
        super.m_7378_(nbt);
        NBTUtils.loadItems(nbt, this.inventory);
        this.storedEU = NBTUtils.getInt(nbt, "energy", 0);
    }

    public void m_142687_(Entity.RemovalReason reason) {
        if (!this.f_19853_.f_46443_ && reason.m_146965_()) {
            Containers.m_19010_((Level)this.f_19853_, (BlockPos)this.m_20183_(), this.inventory);
        }
        super.m_142687_(reason);
    }

    public void m_8119_() {
        super.m_8119_();
        if (!((ItemStack)this.inventory.get(0)).m_41619_()) {
            this.drawEnergy(ElectricItem.MANAGER.charge((ItemStack)this.inventory.get(0), this.storedEU, this.getTier(), false, false));
        }
        if (!((ItemStack)this.inventory.get(1)).m_41619_()) {
            ItemStack fuel = (ItemStack)this.inventory.get(1);
            Item item = fuel.m_41720_();
            if (item == Items.f_42451_) {
                this.addEnergy(500);
                fuel.m_41774_(1);
            } else if (item == Items.f_42153_) {
                this.addEnergy(5000);
                fuel.m_41774_(1);
            } else if (item == IC2Items.SU_BATTERY) {
                this.addEnergy(1000);
                fuel.m_41774_(1);
            } else {
                this.addEnergy(ElectricItem.MANAGER.discharge(fuel, this.getMaxEU() - this.storedEU, this.getTier(), false, true, false));
            }
        }
    }

    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (this.m_6084_() && capability == ForgeCapabilities.ITEM_HANDLER) {
            return this.itemHandler.cast();
        }
        return super.getCapability(capability, facing);
    }

    public void invalidateCaps() {
        super.invalidateCaps();
        this.itemHandler.invalidate();
    }

    public void reviveCaps() {
        super.reviveCaps();
        this.itemHandler = LazyOptional.of(() -> new IHasItemHandler(this));
    }

    protected void m_7114_() {
        float f = 0.98f + (float)(15 - (int)((float)this.storedEU / (float)this.getMaxEU() * 15.0f)) * 0.001f;
        if (this.m_20069_()) {
            f *= 0.95f;
        }
        this.m_20256_(this.m_20184_().m_82542_((double)f, 0.0, (double)f));
    }
}

