/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machine.tileentity;

import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.api.recipe.IEmptyFluidContainerRecipeManager;
import ic2.api.recipe.MachineRecipeResult;
import ic2.api.upgrade.UpgradableProperty;
import ic2.api.util.FluidContainerOutputMode;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.block.comp.Fluids;
import ic2.core.block.invslot.InvSlotConsumableCanner;
import ic2.core.block.invslot.InvSlotConsumableLiquid;
import ic2.core.block.invslot.InvSlotProcessableCanner;
import ic2.core.block.machine.container.ContainerCanner;
import ic2.core.block.machine.tileentity.TileEntityStandardMachine;
import ic2.core.fluid.Ic2FluidStack;
import ic2.core.fluid.Ic2FluidTank;
import ic2.core.network.GrowingBuffer;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.ref.Ic2SoundEvents;
import ic2.core.util.LiquidUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityCanner
extends TileEntityStandardMachine<Object, Object, Object>
implements INetworkClientTileEntityEventListener {
    private Mode mode = Mode.BottleSolid;
    public static final int eventSetModeBase = 0;
    public static final int eventSwapTanks = 0 + Mode.values.length + 1;
    public final Ic2FluidTank inputTank;
    public final Ic2FluidTank outputTank;
    public final InvSlotConsumableCanner canInputSlot;
    protected final Fluids fluids;

    public TileEntityCanner(BlockPos blockPos, BlockState blockState) {
        super(Ic2BlockEntities.CANNER, blockPos, blockState, 4, 200, 1);
        this.inputSlot = new InvSlotProcessableCanner(this, "input", 1);
        this.canInputSlot = new InvSlotConsumableCanner(this, "canInput", 1);
        this.fluids = this.addComponent(new Fluids(this));
        this.inputTank = this.fluids.addTankInsert("inputTank", 8000);
        this.outputTank = this.fluids.addTankExtract("outputTank", 8000);
    }

    @Override
    public void m_142466_(CompoundTag compoundTag) {
        super.m_142466_(compoundTag);
        this.setMode(Mode.values[compoundTag.m_128451_("mode")]);
    }

    @Override
    public void m_183515_(CompoundTag compoundTag) {
        super.m_183515_(compoundTag);
        compoundTag.m_128405_("mode", this.mode.ordinal());
    }

    @Override
    public void operateOnce(MachineRecipeResult<Object, Object, Object> machineRecipeResult, Collection<ItemStack> collection) {
        super.operateOnce(machineRecipeResult, collection);
        if (this.mode == Mode.EmptyLiquid) {
            IEmptyFluidContainerRecipeManager.Output output = (IEmptyFluidContainerRecipeManager.Output)machineRecipeResult.getOutput();
            this.getOutputTank().fillMbUnchecked(output.fluid, false);
        } else if (this.mode == Mode.EnrichLiquid) {
            Ic2FluidStack ic2FluidStack = ((Ic2FluidStack)machineRecipeResult.getOutput()).copy();
            if (!this.canInputSlot.isEmpty()) {
                LiquidUtil.FluidOperationResult fluidOperationResult;
                do {
                    if ((fluidOperationResult = LiquidUtil.fillContainer(this.canInputSlot.get(), ic2FluidStack, FluidContainerOutputMode.EmptyFullToOutput)) == null) continue;
                    if (fluidOperationResult.extraOutput == null || this.outputSlot.canAdd(fluidOperationResult.extraOutput)) {
                        this.canInputSlot.put(fluidOperationResult.inPlaceOutput);
                        if (fluidOperationResult.extraOutput != null) {
                            this.outputSlot.add(fluidOperationResult.extraOutput);
                        }
                        ic2FluidStack.setAmountMb(ic2FluidStack.getAmountMb() - fluidOperationResult.fluidChange.getAmountMb());
                        continue;
                    }
                    fluidOperationResult = null;
                } while (fluidOperationResult != null && !ic2FluidStack.isEmpty());
            }
            this.getOutputTank().fillMbUnchecked(ic2FluidStack, false);
        }
    }

    @Override
    protected Collection<ItemStack> getOutput(Object object) {
        if (object instanceof ItemStack) {
            return Collections.singletonList((ItemStack)object);
        }
        if (object instanceof Ic2FluidStack) {
            return Collections.emptyList();
        }
        if (object instanceof IEmptyFluidContainerRecipeManager.Output) {
            return ((IEmptyFluidContainerRecipeManager.Output)object).container;
        }
        return super.getOutput(object);
    }

    @Override
    public MachineRecipeResult<Object, Object, Object> getOutput() {
        if (this.mode == Mode.EmptyLiquid || this.mode == Mode.BottleLiquid ? this.canInputSlot.isEmpty() : this.inputSlot.isEmpty()) {
            return null;
        }
        MachineRecipeResult<Object, Object, Object> machineRecipeResult = this.inputSlot.process();
        if (machineRecipeResult == null) {
            return null;
        }
        if (!this.outputSlot.canAdd(this.getOutput(machineRecipeResult.getOutput()))) {
            return null;
        }
        if (this.mode == Mode.EmptyLiquid) {
            IEmptyFluidContainerRecipeManager.Output output = (IEmptyFluidContainerRecipeManager.Output)machineRecipeResult.getOutput();
            if (this.getOutputTank().fillMbUnchecked(output.fluid, true) != output.fluid.getAmountMb()) {
                return null;
            }
        } else if (this.mode == Mode.EnrichLiquid) {
            Ic2FluidStack ic2FluidStack = ((Ic2FluidStack)machineRecipeResult.getOutput()).copy();
            if (!this.canInputSlot.isEmpty()) {
                LiquidUtil.FluidOperationResult fluidOperationResult;
                do {
                    if ((fluidOperationResult = LiquidUtil.fillContainer(this.canInputSlot.get(), ic2FluidStack, FluidContainerOutputMode.EmptyFullToOutput)) == null) continue;
                    if (fluidOperationResult.extraOutput == null || this.outputSlot.canAdd(fluidOperationResult.extraOutput)) {
                        ic2FluidStack.setAmountMb(ic2FluidStack.getAmountMb() - fluidOperationResult.fluidChange.getAmountMb());
                        continue;
                    }
                    fluidOperationResult = null;
                } while (fluidOperationResult != null && !ic2FluidStack.isEmpty());
            }
            if (this.getOutputTank().fillMbUnchecked(ic2FluidStack, true) != ic2FluidStack.getAmountMb()) {
                return null;
            }
        }
        return machineRecipeResult;
    }

    public Ic2FluidTank getInputTank() {
        return this.inputTank;
    }

    public Ic2FluidTank getOutputTank() {
        return this.outputTank;
    }

    @Override
    public List<String> getNetworkedFields() {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("canInputSlot");
        arrayList.addAll(super.getNetworkedFields());
        return arrayList;
    }

    @Override
    public SoundEvent getLoopingSoundEvent() {
        return switch (this.mode) {
            case Mode.BottleSolid, Mode.BottleLiquid -> Ic2SoundEvents.MACHINE_CANNER_OPERATE;
            case Mode.EmptyLiquid -> Ic2SoundEvents.MACHINE_CANNER_REVERSE;
            default -> null;
        };
    }

    @Override
    public SoundEvent getInterruptSoundEvent() {
        return switch (this.mode) {
            case Mode.BottleSolid, Mode.BottleLiquid, Mode.EmptyLiquid -> Ic2SoundEvents.MACHINE_INTERRUPT1;
            default -> null;
        };
    }

    @Override
    public ContainerBase<TileEntityCanner> createServerScreenHandler(int n, Player player) {
        return new ContainerCanner(n, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return new ContainerCanner(n, inventory, this);
    }

    @Override
    public void onNetworkUpdate(String string) {
        super.onNetworkUpdate(string);
        if (string.equals("mode")) {
            this.setMode(this.mode);
        }
    }

    @Override
    public void onNetworkEvent(Player player, int n) {
        if (n >= 0 && n < 0 + Mode.values.length) {
            this.setMode(Mode.values[n - 0]);
        } else if (n == eventSwapTanks) {
            this.switchTanks();
        }
    }

    public Mode getMode() {
        return this.mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
        switch (mode) {
            case BottleSolid: {
                this.canInputSlot.setOpType(InvSlotConsumableLiquid.OpType.None);
                break;
            }
            case BottleLiquid: {
                this.canInputSlot.setOpType(InvSlotConsumableLiquid.OpType.Fill);
                break;
            }
            case EmptyLiquid: {
                this.canInputSlot.setOpType(InvSlotConsumableLiquid.OpType.Drain);
                break;
            }
            case EnrichLiquid: {
                this.canInputSlot.setOpType(InvSlotConsumableLiquid.OpType.Both);
            }
        }
        if (IC2.sideProxy.isRendering()) {
            this.stopLoopingSound();
        }
    }

    private boolean switchTanks() {
        if (this.progress != 0) {
            return false;
        }
        Ic2FluidStack ic2FluidStack = this.inputTank.getFluidStack();
        Ic2FluidStack ic2FluidStack2 = this.outputTank.getFluidStack();
        this.inputTank.setFluidStack(ic2FluidStack2);
        this.outputTank.setFluidStack(ic2FluidStack);
        return true;
    }

    @Override
    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.Processing, new UpgradableProperty[]{UpgradableProperty.Transformer, UpgradableProperty.EnergyStorage, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing, UpgradableProperty.FluidConsuming, UpgradableProperty.FluidProducing});
    }

    public static enum Mode {
        BottleSolid,
        EmptyLiquid,
        BottleLiquid,
        EnrichLiquid;

        public static final Mode[] values;

        static {
            values = Mode.values();
        }
    }
}

