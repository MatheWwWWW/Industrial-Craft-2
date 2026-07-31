/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machine.tileentity;

import ic2.api.network.INetworkTileEntityEventListener;
import ic2.api.recipe.MachineRecipeResult;
import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.IUpgradeItem;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.invslot.InvSlotProcessable;
import ic2.core.block.invslot.InvSlotUpgrade;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.gui.dynamic.DynamicContainer;
import ic2.core.gui.dynamic.IGuiValueProvider;
import ic2.core.network.GrowingBuffer;
import ic2.core.network.GuiSynced;
import ic2.core.sound.Sound;
import ic2.core.util.StackUtil;
import java.util.Collection;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class TileEntityStandardMachine<RI, RO, I>
extends TileEntityElectricMachine
implements IHasGui,
IGuiValueProvider,
INetworkTileEntityEventListener,
IUpgradableBlock {
    protected short progress = 0;
    public final int defaultEnergyConsume;
    public final int defaultOperationLength;
    public final int defaultTier;
    public final int defaultEnergyStorage;
    public int energyConsume;
    public int operationLength;
    public int operationsPerTick;
    @GuiSynced
    protected float guiProgress;
    public Sound sound;
    protected static final int EventStart = 0;
    protected static final int EventInterrupt = 1;
    protected static final int EventFinish = 2;
    protected static final int EventStop = 3;
    public InvSlotProcessable<RI, RO, I> inputSlot;
    public final InvSlotOutput outputSlot;
    public final InvSlotUpgrade upgradeSlot;

    public TileEntityStandardMachine(BlockEntityType<? extends TileEntityStandardMachine<RI, RO, I>> blockEntityType, BlockPos blockPos, BlockState blockState, int n, int n2, int n3) {
        this(blockEntityType, blockPos, blockState, n, n2, n3, 1);
    }

    public TileEntityStandardMachine(BlockEntityType<? extends TileEntityStandardMachine<RI, RO, I>> blockEntityType, BlockPos blockPos, BlockState blockState, int n, int n2, int n3, int n4) {
        super(blockEntityType, blockPos, blockState, n * n2, n4);
        this.defaultEnergyConsume = this.energyConsume = n;
        this.defaultOperationLength = this.operationLength = n2;
        this.defaultTier = n4;
        this.defaultEnergyStorage = n * n2;
        this.outputSlot = new InvSlotOutput(this, "output", n3);
        this.upgradeSlot = new InvSlotUpgrade(this, "upgrade", 4);
        this.comparator.setUpdate(() -> this.progress * 15 / this.operationLength);
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

    public float getProgress() {
        return this.guiProgress;
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();
        if (IC2.sideProxy.isSimulating()) {
            this.setOverclockRates();
        }
    }

    @Override
    public void m_6596_() {
        super.m_6596_();
        if (IC2.sideProxy.isSimulating()) {
            this.setOverclockRates();
        }
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        boolean bl = false;
        MachineRecipeResult<RI, RO, I> machineRecipeResult = this.getOutput();
        if (this.canOperate()) {
            if (!this.getActive()) {
                this.activate();
            }
            this.progress = (short)(this.progress + 1);
            if (this.progress >= this.operationLength) {
                this.operate(machineRecipeResult);
                bl = true;
                this.progress = 0;
                if (!this.canOperate()) {
                    this.shutdown(false);
                }
            }
        } else {
            if (this.getActive()) {
                this.shutdown(this.progress != 0);
            }
            if (machineRecipeResult == null) {
                this.progress = 0;
            }
        }
        this.guiProgress = (float)this.progress / (float)this.operationLength;
        if (bl |= this.upgradeSlot.tickNoMark()) {
            super.m_6596_();
        }
    }

    public void setOverclockRates() {
        this.upgradeSlot.onChanged();
        double d = (double)this.progress / (double)this.operationLength;
        this.operationsPerTick = this.upgradeSlot.getOperationsPerTick(this.defaultOperationLength);
        this.operationLength = this.upgradeSlot.getOperationLength(this.defaultOperationLength);
        this.energyConsume = this.upgradeSlot.getEnergyDemand(this.defaultEnergyConsume);
        int n = this.upgradeSlot.getTier(this.defaultTier);
        this.energy.setSinkTier(n);
        this.dischargeSlot.setTier(n);
        this.energy.setCapacity(this.upgradeSlot.getEnergyStorage(this.defaultEnergyStorage, this.defaultOperationLength, this.defaultEnergyConsume));
        this.progress = (short)Math.floor(d * (double)this.operationLength + 0.1);
    }

    private boolean canOperate() {
        return this.getOutput() != null && this.energy.useEnergy(this.energyConsume);
    }

    private void operate(MachineRecipeResult<RI, RO, I> machineRecipeResult) {
        for (int i = 0; i < this.operationsPerTick; ++i) {
            Collection<ItemStack> collection = this.getOutput(machineRecipeResult.getOutput());
            for (int j = 0; j < this.upgradeSlot.size(); ++j) {
                ItemStack itemStack = this.upgradeSlot.get(j);
                if (StackUtil.isEmpty(itemStack) || !(itemStack.m_41720_() instanceof IUpgradeItem)) continue;
                collection = ((IUpgradeItem)itemStack.m_41720_()).onProcessEnd(itemStack, this, collection);
            }
            this.operateOnce(machineRecipeResult, collection);
            machineRecipeResult = this.getOutput();
            if (machineRecipeResult == null) break;
        }
    }

    protected Collection<ItemStack> getOutput(RO RO) {
        return StackUtil.copy((Collection)RO);
    }

    protected void operateOnce(MachineRecipeResult<RI, RO, I> machineRecipeResult, Collection<ItemStack> collection) {
        this.inputSlot.consume(machineRecipeResult);
        this.outputSlot.add(collection);
    }

    protected MachineRecipeResult<RI, RO, I> getOutput() {
        if (this.inputSlot.isEmpty()) {
            return null;
        }
        MachineRecipeResult<RI, RO, I> machineRecipeResult = this.inputSlot.process();
        if (machineRecipeResult == null) {
            return null;
        }
        if (this.outputSlot.canAdd(this.getOutput(machineRecipeResult.getOutput()))) {
            return machineRecipeResult;
        }
        return null;
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
    public void onNetworkEvent(int n) {
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
    public double getGuiValue(String string) {
        if (string.equals("progress")) {
            return this.guiProgress;
        }
        throw new IllegalArgumentException(this.getClass().getSimpleName() + " Cannot get value for " + string);
    }
}

