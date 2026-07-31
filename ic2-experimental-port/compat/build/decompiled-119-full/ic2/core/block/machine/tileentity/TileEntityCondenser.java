/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machine.tileentity;

import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.comp.Fluids;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumableId;
import ic2.core.block.invslot.InvSlotConsumableLiquid;
import ic2.core.block.invslot.InvSlotConsumableLiquidByTank;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.invslot.InvSlotUpgrade;
import ic2.core.block.machine.container.ContainerCondenser;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.fluid.Ic2FluidStack;
import ic2.core.fluid.Ic2FluidTank;
import ic2.core.gui.dynamic.IGuiValueProvider;
import ic2.core.network.GrowingBuffer;
import ic2.core.profile.NotClassic;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.ref.Ic2Fluids;
import ic2.core.ref.Ic2Items;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;

@NotClassic
public class TileEntityCondenser
extends TileEntityElectricMachine
implements IHasGui,
IGuiValueProvider,
IUpgradableBlock {
    private final short passiveCooling = (short)100;
    private final short coolingPerVent = (short)100;
    public final short ventEUCost = (short)2;
    public int progress = 0;
    public final int maxProgress = 10000;
    private final Ic2FluidTank inputTank;
    private final Ic2FluidTank outputTank;
    public final InvSlotConsumableLiquidByTank waterInputSlot;
    public final InvSlotOutput waterOutputSlot;
    public final InvSlotConsumableId ventSlots;
    public final InvSlotUpgrade upgradeSlot;
    protected final Fluids fluids = this.addComponent(new Fluids(this));

    public TileEntityCondenser(BlockPos blockPos, BlockState blockState) {
        super(Ic2BlockEntities.CONDENSER, blockPos, blockState, 100000, 3);
        this.inputTank = this.fluids.addTankInsert("inputTank", 100000, Fluids.fluidPredicate(Ic2Fluids.STEAM.still, Ic2Fluids.SUPERHEATED_STEAM.still));
        this.outputTank = this.fluids.addTankExtract("outputTank", 1000);
        this.waterInputSlot = new InvSlotConsumableLiquidByTank(this, "waterInputSlot", InvSlot.Access.I, 1, InvSlot.InvSide.BOTTOM, InvSlotConsumableLiquid.OpType.Fill, this.outputTank);
        this.waterOutputSlot = new InvSlotOutput(this, "waterOutputSlot", 1);
        this.ventSlots = new InvSlotConsumableId(this, "ventSlots", 4, Ic2Items.HEAT_VENT);
        this.ventSlots.setStackSizeLimit(1);
        this.upgradeSlot = new InvSlotUpgrade(this, "upgradeSlot", 1);
    }

    @Override
    public void m_142466_(CompoundTag compoundTag) {
        super.m_142466_(compoundTag);
        this.progress = compoundTag.m_128451_("progress");
    }

    @Override
    public void m_183515_(CompoundTag compoundTag) {
        super.m_183515_(compoundTag);
        compoundTag.m_128405_("progress", this.progress);
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();
        if (!this.m_58904_().f_46443_) {
            this.updateTier();
        }
    }

    public byte getVents() {
        byte by = 0;
        for (int i = 0; i < this.ventSlots.size(); ++i) {
            if (this.ventSlots.isEmpty(i)) continue;
            by = (byte)(by + 1);
        }
        return by;
    }

    @Override
    public void m_6596_() {
        super.m_6596_();
        if (!this.m_58904_().f_46443_) {
            this.updateTier();
        }
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        this.waterInputSlot.processFromTank(this.outputTank, this.waterOutputSlot);
        this.setActive(this.inputTank.getFluidAmount() > 0);
        this.work();
        if (this.upgradeSlot.tickNoMark()) {
            super.m_6596_();
        }
    }

    private void work() {
        if (this.outputTank.getCapacity() - this.outputTank.getFluidAmount() >= 100) {
            if (this.progress >= 10000) {
                this.outputTank.fillMbUnchecked(Ic2FluidStack.create(Ic2Fluids.DISTILLED_WATER.still, 100), false);
                this.progress -= 10000;
            }
            if (this.inputTank.getFluidAmount() > 0) {
                byte by = this.getVents();
                int n = 100 + by * 100;
                if (this.energy.useEnergy(by * 2)) {
                    this.progress += this.inputTank.drainMbUnchecked(n, false).getAmountMb();
                }
            }
        }
    }

    private void updateTier() {
        this.upgradeSlot.onChanged();
        int n = this.upgradeSlot.getTier(3);
        this.energy.setSinkTier(n);
        this.dischargeSlot.setTier(n);
    }

    public ContainerBase<TileEntityCondenser> createServerScreenHandler(int n, Player player) {
        return new ContainerCondenser(n, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return new ContainerCondenser(n, inventory, this);
    }

    @Override
    public double getGuiValue(String string) {
        if ("progress".equals(string)) {
            return this.progress == 0 ? 0.0 : (double)this.progress / 10000.0;
        }
        throw new IllegalArgumentException("Invalid Gui value: " + string);
    }

    public int gaugeLiquidScaled(int n, int n2) {
        switch (n2) {
            case 0: {
                if (this.inputTank.getFluidAmount() <= 0) {
                    return 0;
                }
                return this.inputTank.getFluidAmount() * n / this.inputTank.getCapacity();
            }
            case 1: {
                if (this.outputTank.getFluidAmount() <= 0) {
                    return 0;
                }
                return this.outputTank.getFluidAmount() * n / this.outputTank.getCapacity();
            }
        }
        return 0;
    }

    public Ic2FluidTank getInputTank() {
        return this.inputTank;
    }

    public Ic2FluidTank getOutputTank() {
        return this.outputTank;
    }

    @Override
    public double getEnergy() {
        return 0.0;
    }

    @Override
    public boolean useEnergy(double d) {
        return false;
    }

    @Override
    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing, UpgradableProperty.FluidConsuming, UpgradableProperty.FluidProducing, UpgradableProperty.Transformer);
    }
}

