/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machine.tileentity;

import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.block.comp.Energy;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumable;
import ic2.core.block.invslot.InvSlotConsumableId;
import ic2.core.block.tileentity.TileEntityBase;
import ic2.core.block.tileentity.TileEntityInventory;
import ic2.core.block.wiring.tileentity.TileEntityElectricBlock;
import ic2.core.gui.dynamic.DynamicContainer;
import ic2.core.network.GrowingBuffer;
import ic2.core.network.GuiSynced;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.ref.Ic2Items;
import ic2.core.ref.Ic2SoundEvents;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityClassicElectrolyzer
extends TileEntityBase
implements IHasGui {
    public TileEntityElectricBlock mfe = null;
    public int ticker = IC2.random.m_188503_(16);
    public final InvSlotConsumable waterSlot = new InvSlotConsumableId(this, "water", InvSlot.Access.IO, 1, InvSlot.InvSide.TOP, Ic2Items.WATER_CELL);
    public final InvSlotConsumable hydrogenSlot = new InvSlotConsumableId(this, "hydrogen", InvSlot.Access.IO, 1, InvSlot.InvSide.BOTTOM, Ic2Items.ELECTROLYZED_WATER_CELL);
    @GuiSynced
    protected final Energy energy = this.addComponent(new Energy(this, 20000.0, Util.noFacings, Util.noFacings, 1));

    public TileEntityClassicElectrolyzer(BlockPos blockPos, BlockState blockState) {
        super((BlockEntityType<? extends TileEntityInventory>)Ic2BlockEntities.CLASSIC_ELECTROLYZER, blockPos, blockState);
        this.comparator.setUpdate(this.energy::getComparatorValue);
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        boolean bl = false;
        boolean bl2 = false;
        if (++this.ticker % 16 == 0) {
            this.mfe = this.lookForMFE();
        }
        if (this.mfe == null) {
            return;
        }
        if (this.shouldDrain() && this.canDrain()) {
            bl |= this.drain();
            bl2 = true;
        }
        if (this.shouldPower() && (this.canPower() || this.energy.getEnergy() > 0.0)) {
            bl |= this.power();
            bl2 = true;
        }
        this.setActiveState(bl2);
        if (bl) {
            this.m_6596_();
        }
    }

    @Override
    public SoundEvent getLoopingSoundEvent() {
        return Ic2SoundEvents.MACHINE_ELECTROLYZER_LOOP;
    }

    public boolean shouldDrain() {
        return this.mfe != null && this.mfe.energy.getFillRatio() >= 0.7;
    }

    public boolean shouldPower() {
        return this.mfe != null && this.mfe.energy.getFillRatio() <= 0.3;
    }

    public boolean canDrain() {
        return this.waterSlot.consume(1, true, false) != null && (this.hydrogenSlot.isEmpty() || StackUtil.getSize(this.hydrogenSlot.get()) < Math.min(this.hydrogenSlot.getStackSizeLimit(), this.hydrogenSlot.get().m_41741_()));
    }

    public boolean canPower() {
        return this.hydrogenSlot.consume(1, true, false) != null && (this.waterSlot.isEmpty() || StackUtil.getSize(this.waterSlot.get()) < Math.min(this.waterSlot.getStackSizeLimit(), this.waterSlot.get().m_41741_()));
    }

    public boolean drain() {
        double d = this.processRate();
        if (!this.mfe.energy.useEnergy(d)) {
            return false;
        }
        this.energy.addEnergy(d);
        if (this.energy.useEnergy(20000.0)) {
            this.waterSlot.consume(1);
            if (this.hydrogenSlot.isEmpty()) {
                this.hydrogenSlot.put(new ItemStack((ItemLike)Ic2Items.ELECTROLYZED_WATER_CELL));
            } else {
                this.hydrogenSlot.put(StackUtil.incSize(this.hydrogenSlot.get()));
            }
            return true;
        }
        return false;
    }

    public boolean power() {
        if (this.energy.getEnergy() > 0.0) {
            double d = Math.min(this.energy.getEnergy(), (double)this.processRate());
            this.energy.useEnergy(d);
            this.mfe.energy.addEnergy(d);
            return false;
        }
        this.energy.forceAddEnergy(12000 + 2000 * this.mfe.energy.getSinkTier());
        this.hydrogenSlot.consume(1);
        if (this.waterSlot.isEmpty()) {
            this.waterSlot.put(new ItemStack((ItemLike)Ic2Items.WATER_CELL));
        } else {
            this.waterSlot.put(StackUtil.incSize(this.waterSlot.get()));
        }
        return true;
    }

    public int processRate() {
        return switch (this.mfe.energy.getSinkTier()) {
            default -> 2;
            case 2 -> 8;
            case 3 -> 32;
            case 4 -> 128;
        };
    }

    public TileEntityElectricBlock lookForMFE() {
        Level level = this.m_58904_();
        for (Direction direction : Util.ALL_DIRS) {
            BlockEntity blockEntity = level.m_7702_(this.f_58858_.m_121945_(direction));
            if (!(blockEntity instanceof TileEntityElectricBlock)) continue;
            return (TileEntityElectricBlock)blockEntity;
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
}

