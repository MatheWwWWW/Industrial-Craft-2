/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.wiring.tileentity;

import ic2.api.energy.EnergyNet;
import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.api.tile.IEnergyStorage;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.block.comp.Energy;
import ic2.core.block.comp.Redstone;
import ic2.core.block.comp.RedstoneEmitter;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotCharge;
import ic2.core.block.invslot.InvSlotDischarge;
import ic2.core.block.tileentity.Ic2TileEntityBlock;
import ic2.core.block.tileentity.TileEntityInventory;
import ic2.core.block.wiring.ContainerElectricBlock;
import ic2.core.init.Localization;
import ic2.core.init.MainConfig;
import ic2.core.network.GrowingBuffer;
import ic2.core.util.ConfigUtil;
import ic2.core.util.StackUtil;
import java.util.EnumSet;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

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

    public TileEntityElectricBlock(BlockEntityType<? extends TileEntityElectricBlock> blockEntityType, BlockPos blockPos, BlockState blockState, int n, int n2, int n3) {
        super((BlockEntityType<? extends TileEntityInventory>)blockEntityType, blockPos, blockState);
        this.output = n2;
        this.chargeSlot = new InvSlotCharge(this, n);
        this.dischargeSlot = new InvSlotDischarge(this, InvSlot.Access.IO, n, InvSlot.InvSide.BOTTOM);
        this.energy = this.addComponent(new Energy(this, n3, EnumSet.complementOf(EnumSet.of(Direction.DOWN)), EnumSet.of(Direction.DOWN), n, n, true).addManagedSlot(this.chargeSlot).addManagedSlot(this.dischargeSlot));
        this.rsEmitter = this.addComponent(new RedstoneEmitter(this));
        this.redstone = this.addComponent(new Redstone(this));
        this.comparator.setUpdate(this.energy::getComparatorValue);
    }

    @Override
    public void m_142466_(CompoundTag compoundTag) {
        super.m_142466_(compoundTag);
        this.redstoneMode = compoundTag.m_128445_("redstoneMode");
        this.energy.setDirections(EnumSet.complementOf(EnumSet.of(this.getFacing())), EnumSet.of(this.getFacing()));
    }

    @Override
    public void m_183515_(CompoundTag compoundTag) {
        super.m_183515_(compoundTag);
        compoundTag.m_128344_("redstoneMode", this.redstoneMode);
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        this.energy.setSendingEnabled(this.shouldEmitEnergy());
        this.rsEmitter.setLevel(this.shouldEmitRedstone() ? 15 : 0);
    }

    @Override
    public ContainerBase<?> createServerScreenHandler(int n, Player player) {
        return new ContainerElectricBlock(n, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return new ContainerElectricBlock(n, inventory, this);
    }

    @Override
    protected void setFacing(Level level, Direction direction) {
        super.setFacing(level, direction);
        this.energy.setDirections(EnumSet.complementOf(EnumSet.of(this.getFacing())), EnumSet.of(this.getFacing()));
    }

    protected final void superSetFacing(Level level, Direction direction) {
        super.setFacing(level, direction);
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
        boolean bl = this.redstone.hasRedstoneInput();
        if (this.redstoneMode == 5) {
            return !bl;
        }
        if (this.redstoneMode == 6) {
            return !bl || this.energy.getEnergy() > this.energy.getCapacity() - this.output * 20.0;
        }
        return true;
    }

    @Override
    public void onNetworkEvent(Player player, int n) {
        this.redstoneMode = (byte)(this.redstoneMode + 1);
        if (this.redstoneMode >= redstoneModes) {
            this.redstoneMode = 0;
        }
        IC2.sideProxy.messagePlayer(player, this.getRedstoneMode(), new Object[0]);
    }

    public String getRedstoneMode() {
        if (this.redstoneMode >= redstoneModes || this.redstoneMode < 0) {
            return "";
        }
        return Localization.translate("ic2.EUStorage.gui.mod.redstone" + this.redstoneMode);
    }

    @Override
    public void onPlaced(ItemStack itemStack, LivingEntity livingEntity, Direction direction) {
        super.onPlaced(itemStack, livingEntity, direction);
        if (!this.m_58904_().f_46443_) {
            CompoundTag compoundTag = StackUtil.getOrCreateNbtData(itemStack);
            this.energy.addEnergy(compoundTag.m_128459_("energy"));
        }
    }

    public void onUpgraded() {
        this.rerender();
    }

    @Override
    protected ItemStack adjustDrop(ItemStack itemStack, boolean bl) {
        itemStack = super.adjustDrop(itemStack, bl);
        if (bl || this.teBlock.getDefaultDrop() == Ic2TileEntityBlock.DefaultDrop.Self) {
            double d = ConfigUtil.getDouble(MainConfig.get(), "balance/energyRetainedInStorageBlockDrops");
            double d2 = this.energy.getEnergy();
            if (d > 0.0 && d2 > 0.0) {
                CompoundTag compoundTag = StackUtil.getOrCreateNbtData(itemStack);
                compoundTag.m_128347_("energy", d2 * d);
            }
        }
        return itemStack;
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
    public void setStored(int n) {
    }

    @Override
    public int addEnergy(int n) {
        this.energy.addEnergy(n);
        return n;
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
    public boolean isTeleporterCompatible(Direction direction) {
        return true;
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public void addInformation(ItemStack itemStack, List<String> list, TooltipFlag tooltipFlag) {
        super.addInformation(itemStack, list, tooltipFlag);
        list.add(String.format("%s %.0f %s %s %d %s", Localization.translate("ic2.item.tooltip.Output"), EnergyNet.instance.getPowerFromTier(this.energy.getSourceTier()), Localization.translate("ic2.generic.text.EUt"), Localization.translate("ic2.item.tooltip.Capacity"), this.getCapacity(), Localization.translate("ic2.generic.text.EU")));
        list.add(Localization.translate("ic2.item.tooltip.Store") + " " + (long)StackUtil.getOrCreateNbtData(itemStack).m_128459_("energy") + " " + Localization.translate("ic2.generic.text.EU"));
    }
}

