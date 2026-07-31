/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.FarmBlock
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Fluids
 */
package ic2.core.block.machine.tileentity;

import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.comp.Fluids;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumable;
import ic2.core.block.invslot.InvSlotConsumableItemStack;
import ic2.core.block.invslot.InvSlotConsumableLiquid;
import ic2.core.block.invslot.InvSlotConsumableLiquidByTank;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.invslot.InvSlotUpgrade;
import ic2.core.block.machine.container.ContainerCropmatron;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.crop.TileEntityCrop;
import ic2.core.fluid.Ic2FluidTank;
import ic2.core.network.GrowingBuffer;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.ref.Ic2Fluids;
import ic2.core.ref.Ic2Items;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;

public class TileEntityCropmatron
extends TileEntityElectricMachine
implements IHasGui,
IUpgradableBlock {
    public final InvSlotUpgrade upgradeSlot;
    public int scanX = -4;
    public int scanY = -1;
    public int scanZ = -4;
    public final InvSlotConsumable fertilizerSlot;
    public final InvSlotOutput wasseroutputSlot;
    public final InvSlotOutput exOutputSlot;
    public final InvSlotConsumableLiquidByTank wasserinputSlot;
    public final InvSlotConsumableLiquidByTank exInputSlot;
    protected final Ic2FluidTank waterTank;
    protected final Ic2FluidTank exTank;
    protected final Fluids fluids = this.addComponent(new Fluids(this));

    public TileEntityCropmatron(BlockPos blockPos, BlockState blockState) {
        super(Ic2BlockEntities.CROPMATRON, blockPos, blockState, 10000, 1);
        this.waterTank = this.fluids.addTankInsert("waterTank", 2000, Fluids.fluidPredicate(new Fluid[]{net.minecraft.world.level.material.Fluids.f_76193_}));
        this.exTank = this.fluids.addTankInsert("exTank", 2000, Fluids.fluidPredicate(Ic2Fluids.WEED_EX.still));
        this.fertilizerSlot = new InvSlotConsumableItemStack(this, "fertilizer", 7, new ItemStack((ItemLike)Ic2Items.FERTILIZER));
        this.wasserinputSlot = new InvSlotConsumableLiquidByTank(this, "wasserinputSlot", InvSlot.Access.I, 1, InvSlot.InvSide.TOP, InvSlotConsumableLiquid.OpType.Drain, this.waterTank);
        this.exInputSlot = new InvSlotConsumableLiquidByTank(this, "exInputSlot", InvSlot.Access.I, 1, InvSlot.InvSide.TOP, InvSlotConsumableLiquid.OpType.Drain, this.exTank);
        this.wasseroutputSlot = new InvSlotOutput(this, "wasseroutputSlot", 1);
        this.exOutputSlot = new InvSlotOutput(this, "exOutputSlot", 1);
        this.upgradeSlot = new InvSlotUpgrade(this, "upgrade", 4);
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        this.upgradeSlot.tick();
        this.wasserinputSlot.processIntoTank(this.waterTank, this.wasseroutputSlot);
        this.exInputSlot.processIntoTank(this.exTank, this.exOutputSlot);
        this.fertilizerSlot.organize();
        if (this.f_58857_.m_46467_() % 10L == 0L && this.energy.getEnergy() >= 31.0) {
            this.scan();
        }
    }

    public void scan() {
        ++this.scanX;
        if (this.scanX > 4) {
            this.scanX = -4;
            ++this.scanZ;
            if (this.scanZ > 4) {
                this.scanZ = -4;
                ++this.scanY;
                if (this.scanY > 1) {
                    this.scanY = -1;
                }
            }
        }
        this.energy.useEnergy(1.0);
        BlockPos blockPos = this.f_58858_.m_7918_(this.scanX, this.scanY, this.scanZ);
        BlockEntity blockEntity = this.m_58904_().m_7702_(blockPos);
        if (blockEntity instanceof TileEntityCrop) {
            int n;
            TileEntityCrop tileEntityCrop = (TileEntityCrop)blockEntity;
            if (!this.fertilizerSlot.isEmpty() && this.fertilizerSlot.consume(1, true, false) != null && tileEntityCrop.applyFertilizer(false)) {
                this.energy.useEnergy(10.0);
                this.fertilizerSlot.consume(1);
            }
            if (!this.waterTank.isEmpty() && (n = tileEntityCrop.applyHydration(this.waterTank.getFluidAmount(), false)) > 0) {
                this.waterTank.drainMb(n, false);
                this.energy.useEnergy(10.0);
            }
            if (!this.exTank.isEmpty() && (n = tileEntityCrop.applyWeedEx(this.exTank.getFluidAmount(), false, false, false)) > 0) {
                this.exTank.drainMb(n, false);
                this.energy.useEnergy(10.0);
            }
        } else if (!this.waterTank.isEmpty() && this.tryHydrateFarmland(blockPos)) {
            this.energy.useEnergy(10.0);
        }
    }

    private boolean tryHydrateFarmland(BlockPos blockPos) {
        int n;
        Level level = this.m_58904_();
        BlockState blockState = level.m_8055_(blockPos);
        if (blockState.m_60734_() == Blocks.f_50093_ && (n = ((Integer)blockState.m_61143_((Property)FarmBlock.f_53243_)).intValue()) < 7) {
            int n2 = Math.min(this.waterTank.getFluidAmount(), 7 - n);
            assert (n2 > 0);
            assert (n2 <= 7);
            this.waterTank.drainMbUnchecked(n2, false);
            level.m_7731_(blockPos, (BlockState)blockState.m_61124_((Property)FarmBlock.f_53243_, (Comparable)Integer.valueOf(n + n2)), 2);
            return true;
        }
        return false;
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
    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.Transformer, UpgradableProperty.EnergyStorage, UpgradableProperty.ItemConsuming, UpgradableProperty.FluidConsuming);
    }

    public ContainerBase<TileEntityCropmatron> createServerScreenHandler(int n, Player player) {
        return new ContainerCropmatron(n, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return new ContainerCropmatron(n, inventory, this);
    }

    public Ic2FluidTank getWaterTank() {
        return this.waterTank;
    }

    public Ic2FluidTank getExTank() {
        return this.exTank;
    }
}

