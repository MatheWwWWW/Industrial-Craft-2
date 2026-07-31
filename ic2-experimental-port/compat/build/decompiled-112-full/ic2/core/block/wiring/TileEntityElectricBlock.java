/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.util.ITooltipFlag
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.util.EnumFacing
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block.wiring;

import ic2.api.energy.EnergyNet;
import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.api.tile.IEnergyStorage;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.comp.Energy;
import ic2.core.block.comp.Redstone;
import ic2.core.block.comp.RedstoneEmitter;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotCharge;
import ic2.core.block.invslot.InvSlotDischarge;
import ic2.core.block.wiring.ContainerElectricBlock;
import ic2.core.block.wiring.GuiElectricBlock;
import ic2.core.init.Localization;
import ic2.core.init.MainConfig;
import ic2.core.ref.TeBlock;
import ic2.core.util.ConfigUtil;
import ic2.core.util.StackUtil;
import java.util.EnumSet;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class TileEntityElectricBlock
extends TileEntityInventory
implements IHasGui,
INetworkClientTileEntityEventListener,
IEnergyStorage {
    protected double output;
    public byte redstoneMode = 0;
    public static byte redstoneModes = (byte)7;
    public final InvSlotCharge chargeSlot;
    public final InvSlotDischarge dischargeSlot;
    public final Energy energy;
    public final Redstone redstone;
    public final RedstoneEmitter rsEmitter;

    public TileEntityElectricBlock(int tier, int output, int maxStorage) {
        this.output = output;
        this.chargeSlot = new InvSlotCharge(this, tier);
        this.dischargeSlot = new InvSlotDischarge(this, InvSlot.Access.IO, tier, InvSlot.InvSide.BOTTOM);
        this.energy = this.addComponent(new Energy(this, maxStorage, EnumSet.complementOf(EnumSet.of(EnumFacing.DOWN)), EnumSet.of(EnumFacing.DOWN), tier, tier, true).addManagedSlot(this.chargeSlot).addManagedSlot(this.dischargeSlot));
        this.rsEmitter = this.addComponent(new RedstoneEmitter(this));
        this.redstone = this.addComponent(new Redstone(this));
        this.comparator.setUpdate(this.energy::getComparatorValue);
    }

    @Override
    public void func_145839_a(NBTTagCompound nbt) {
        this.superReadFromNBT(nbt);
        this.energy.setDirections(EnumSet.complementOf(EnumSet.of(this.getFacing())), EnumSet.of(this.getFacing()));
    }

    protected final void superReadFromNBT(NBTTagCompound nbt) {
        super.func_145839_a(nbt);
        this.redstoneMode = nbt.func_74771_c("redstoneMode");
    }

    @Override
    public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
        super.func_189515_b(nbt);
        nbt.func_74774_a("redstoneMode", this.redstoneMode);
        return nbt;
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        this.energy.setSendingEnabled(this.shouldEmitEnergy());
        this.rsEmitter.setLevel(this.shouldEmitRedstone() ? 15 : 0);
    }

    public ContainerBase<? extends TileEntityElectricBlock> getGuiContainer(EntityPlayer player) {
        return new ContainerElectricBlock(player, this);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
        return new GuiElectricBlock(new ContainerElectricBlock(player, this));
    }

    @Override
    public void onGuiClosed(EntityPlayer player) {
    }

    @Override
    public void setFacing(EnumFacing facing) {
        super.setFacing(facing);
        this.energy.setDirections(EnumSet.complementOf(EnumSet.of(this.getFacing())), EnumSet.of(this.getFacing()));
    }

    protected final void superSetFacing(EnumFacing facing) {
        super.setFacing(facing);
    }

    protected boolean shouldEmitRedstone() {
        switch (this.redstoneMode) {
            case 1: {
                return this.energy.getEnergy() >= this.energy.getCapacity() - this.output * 20.0;
            }
            case 2: {
                return this.energy.getEnergy() > this.output && this.energy.getEnergy() < this.energy.getCapacity() - this.output;
            }
            case 3: {
                return this.energy.getEnergy() < this.energy.getCapacity() - this.output;
            }
            case 4: {
                return this.energy.getEnergy() < this.output;
            }
        }
        return false;
    }

    protected boolean shouldEmitEnergy() {
        boolean redstone = this.redstone.hasRedstoneInput();
        if (this.redstoneMode == 5) {
            return !redstone;
        }
        if (this.redstoneMode == 6) {
            return !redstone || this.energy.getEnergy() > this.energy.getCapacity() - this.output * 20.0;
        }
        return true;
    }

    @Override
    public void onNetworkEvent(EntityPlayer player, int event) {
        this.redstoneMode = (byte)(this.redstoneMode + 1);
        if (this.redstoneMode >= redstoneModes) {
            this.redstoneMode = 0;
        }
        IC2.platform.messagePlayer(player, this.getRedstoneMode(), new Object[0]);
    }

    public String getRedstoneMode() {
        if (this.redstoneMode >= redstoneModes || this.redstoneMode < 0) {
            return "";
        }
        return Localization.translate("ic2.EUStorage.gui.mod.redstone" + this.redstoneMode);
    }

    @Override
    public void onPlaced(ItemStack stack, EntityLivingBase placer, EnumFacing facing) {
        super.onPlaced(stack, placer, facing);
        if (!this.func_145831_w().field_72995_K) {
            NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
            this.energy.addEnergy(nbt.func_74769_h("energy"));
        }
    }

    public void onUpgraded() {
        this.rerender();
    }

    @Override
    protected ItemStack adjustDrop(ItemStack drop, boolean wrench) {
        drop = super.adjustDrop(drop, wrench);
        if (wrench || this.teBlock.getDefaultDrop() == TeBlock.DefaultDrop.Self) {
            double retainedRatio = ConfigUtil.getDouble(MainConfig.get(), "balance/energyRetainedInStorageBlockDrops");
            double totalEnergy = this.energy.getEnergy();
            if (retainedRatio > 0.0 && totalEnergy > 0.0) {
                NBTTagCompound nbt = StackUtil.getOrCreateNbtData(drop);
                nbt.func_74780_a("energy", totalEnergy * retainedRatio);
            }
        }
        return drop;
    }

    @Override
    public int getOutput() {
        return (int)this.output;
    }

    @Override
    public double getOutputEnergyUnitsPerTick() {
        return this.output;
    }

    @Override
    public void setStored(int energy) {
    }

    @Override
    public int addEnergy(int amount) {
        this.energy.addEnergy(amount);
        return amount;
    }

    @Override
    public int getStored() {
        return (int)this.energy.getEnergy();
    }

    @Override
    public int getCapacity() {
        return (int)this.energy.getCapacity();
    }

    @Override
    public boolean isTeleporterCompatible(EnumFacing side) {
        return true;
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void addInformation(ItemStack stack, List<String> tooltip, ITooltipFlag advanced) {
        super.addInformation(stack, tooltip, advanced);
        tooltip.add(String.format("%s %.0f %s %s %d %s", Localization.translate("ic2.item.tooltip.Output"), EnergyNet.instance.getPowerFromTier(this.energy.getSourceTier()), Localization.translate("ic2.generic.text.EUt"), Localization.translate("ic2.item.tooltip.Capacity"), this.getCapacity(), Localization.translate("ic2.generic.text.EU")));
        tooltip.add(Localization.translate("ic2.item.tooltip.Store") + " " + (long)StackUtil.getOrCreateNbtData(stack).func_74769_h("energy") + " " + Localization.translate("ic2.generic.text.EU"));
    }
}

