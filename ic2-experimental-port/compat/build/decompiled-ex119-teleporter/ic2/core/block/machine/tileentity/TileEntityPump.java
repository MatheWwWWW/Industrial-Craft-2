/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machine.tileentity;

import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.block.comp.Fluids;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumableLiquid;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.invslot.InvSlotUpgrade;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.block.machine.tileentity.TileEntityMiner;
import ic2.core.fluid.Ic2FluidStack;
import ic2.core.fluid.Ic2FluidTank;
import ic2.core.gui.dynamic.DynamicContainer;
import ic2.core.gui.dynamic.IGuiValueProvider;
import ic2.core.network.GrowingBuffer;
import ic2.core.network.GuiSynced;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.sound.Sound;
import ic2.core.util.LiquidUtil;
import ic2.core.util.PumpUtil;
import ic2.core.util.Util;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityPump
extends TileEntityElectricMachine
implements IHasGui,
IUpgradableBlock,
IGuiValueProvider {
    public final int defaultTier;
    public int energyConsume = 1;
    public int operationsPerTick;
    public final int defaultEnergyStorage;
    public final int defaultEnergyConsume;
    public final int defaultOperationLength;
    private Sound sound;
    private TileEntityMiner miner = null;
    public boolean redstonePowered = false;
    public final InvSlotConsumableLiquid containerSlot = new InvSlotConsumableLiquid(this, "input", InvSlot.Access.I, 1, InvSlot.InvSide.TOP, InvSlotConsumableLiquid.OpType.Fill);
    public final InvSlotOutput outputSlot = new InvSlotOutput(this, "output", 1, InvSlot.InvSide.SIDE);
    public final InvSlotUpgrade upgradeSlot = new InvSlotUpgrade(this, "upgrade", 4);
    @GuiSynced
    protected final Ic2FluidTank fluidTank;
    public short progress = 0;
    public int operationLength = 20;
    @GuiSynced
    public float guiProgress;
    protected final Fluids fluids;

    public TileEntityPump(BlockPos blockPos, BlockState blockState) {
        super(Ic2BlockEntities.PUMP, blockPos, blockState, 20, 1);
        this.defaultEnergyConsume = 1;
        this.defaultOperationLength = 20;
        this.defaultTier = 1;
        this.defaultEnergyStorage = 1 * this.operationLength;
        this.fluids = this.addComponent(new Fluids(this));
        this.fluidTank = this.fluids.addTankExtract("fluid", 8000);
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();
        if (!Objects.requireNonNull(this.m_58904_()).f_46443_) {
            this.setUpgradestat();
        }
    }

    @Override
    protected void onUnloaded() {
        if (IC2.sideProxy.isRendering() && this.sound != null) {
            IC2.soundManager.removeSound(this, this.sound);
            this.sound = null;
        }
        this.miner = null;
        super.onUnloaded();
    }

    @Override
    public void m_142466_(CompoundTag compoundTag) {
        super.m_142466_(compoundTag);
        this.progress = compoundTag.m_128448_("progress");
    }

    @Override
    public void m_183515_(CompoundTag compoundTag) {
        super.m_183515_(compoundTag);
        compoundTag.m_128376_("progress", this.progress);
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        boolean bl = false;
        if (this.canOperate() && this.energy.getEnergy() >= (double)(this.energyConsume * this.operationLength)) {
            if (this.progress < this.operationLength) {
                this.progress = (short)(this.progress + 1);
                this.energy.useEnergy(this.energyConsume);
            } else {
                this.progress = 0;
                this.operate(false);
            }
            this.activate();
        } else {
            this.shutdown(false);
        }
        bl |= this.containerSlot.processFromTank(this.fluidTank, this.outputSlot);
        this.guiProgress = (float)this.progress / (float)this.operationLength;
        if (bl |= this.upgradeSlot.tickNoMark()) {
            super.m_6596_();
        }
    }

    public boolean canOperate() {
        return this.operate(true);
    }

    public boolean operate(boolean bl) {
        Object object;
        if (this.miner == null || this.miner.m_58901_()) {
            this.miner = null;
            object = this.m_58904_();
            if (object == null) {
                return false;
            }
            for (Direction direction : Util.downSideFacings) {
                BlockEntity blockEntity = object.m_7702_(this.f_58858_.m_121945_(direction));
                if (!(blockEntity instanceof TileEntityMiner)) continue;
                this.miner = (TileEntityMiner)blockEntity;
                break;
            }
        }
        object = null;
        if (this.miner != null) {
            if (this.miner.canProvideLiquid) {
                object = this.pump(this.miner.liquidPos, bl, this.miner);
            }
        } else {
            Direction direction = this.getFacing();
            object = this.pump(this.f_58858_.m_121945_(direction), bl, this.miner);
        }
        if (object != null && this.fluidTank.fillMbUnchecked((Ic2FluidStack)object, true) > 0) {
            if (!bl) {
                this.fluidTank.fillMbUnchecked((Ic2FluidStack)object, false);
            }
            return true;
        }
        return false;
    }

    public Ic2FluidStack pump(BlockPos blockPos, boolean bl, TileEntityMiner tileEntityMiner) {
        BlockEntity blockEntity;
        Level level = this.m_58904_();
        if (level == null) {
            return null;
        }
        int n = this.fluidTank.getCapacity() - this.fluidTank.getFluidAmount();
        if (tileEntityMiner == null && n > 0) {
            Direction direction;
            blockEntity = level.m_7702_(blockPos);
            BlockState blockState = level.m_8055_(blockPos);
            if (LiquidUtil.isFluidTile(blockState, blockEntity, direction = this.getFacing().m_122424_())) {
                if (n > 1000) {
                    n = 1000;
                }
                return LiquidUtil.drainTile(blockState, level, blockPos, direction, n, bl);
            }
        }
        if (n >= 1000) {
            if (tileEntityMiner != null && tileEntityMiner.canProvideLiquid) {
                assert (tileEntityMiner.liquidPos != null);
                blockEntity = tileEntityMiner.liquidPos;
            } else {
                blockEntity = PumpUtil.searchFluidSource(level, blockPos);
            }
            if (blockEntity != null) {
                return LiquidUtil.drainWorldFluidBlock(level, (BlockPos)blockEntity, bl);
            }
        }
        return null;
    }

    @Override
    public void m_6596_() {
        super.m_6596_();
        if (IC2.sideProxy.isSimulating()) {
            this.setUpgradestat();
        }
    }

    public void setUpgradestat() {
        double d = (double)this.progress / (double)this.operationLength;
        this.operationsPerTick = this.upgradeSlot.getOperationsPerTick(this.defaultOperationLength);
        this.operationLength = this.upgradeSlot.getOperationLength(this.defaultOperationLength);
        this.energyConsume = this.upgradeSlot.getEnergyDemand(this.defaultEnergyConsume);
        this.energy.setSinkTier(this.upgradeSlot.getTier(this.defaultTier));
        this.dischargeSlot.setTier(this.energy.getSinkTier());
        this.energy.setCapacity(this.upgradeSlot.getEnergyStorage(this.defaultEnergyStorage, this.defaultOperationLength, this.defaultEnergyConsume));
        this.progress = (short)Math.floor(d * (double)this.operationLength + 0.1);
    }

    @Override
    public double getGuiValue(String string) {
        if (string.equals("progress")) {
            return this.guiProgress;
        }
        throw new IllegalArgumentException(this.getClass().getSimpleName() + " Cannot get value for " + string);
    }

    @Override
    public double getEnergy() {
        return this.energy.getEnergy();
    }

    @Override
    public boolean useEnergy(double d) {
        return this.energy.useEnergy(d);
    }

    @Override
    public ContainerBase<?> createServerScreenHandler(int n, Player player) {
        return DynamicContainer.create(n, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return DynamicContainer.create(n, inventory, this);
    }

    @Override
    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.Processing, new UpgradableProperty[]{UpgradableProperty.Transformer, UpgradableProperty.EnergyStorage, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing, UpgradableProperty.FluidProducing});
    }
}

