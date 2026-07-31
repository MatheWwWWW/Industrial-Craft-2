/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.generator.tileentity;

import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.block.comp.Energy;
import ic2.core.block.invslot.InvSlotCharge;
import ic2.core.block.tileentity.TileEntityBase;
import ic2.core.block.tileentity.TileEntityInventory;
import ic2.core.gui.dynamic.DynamicContainer;
import ic2.core.network.GrowingBuffer;
import ic2.core.network.GuiSynced;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class TileEntityBaseGenerator
extends TileEntityBase
implements IHasGui {
    public final InvSlotCharge chargeSlot;
    protected final Energy energy;
    @GuiSynced
    public int fuel = 0;
    protected double production;
    private int ticksSinceLastActiveUpdate;
    private int activityMeter = 0;

    public TileEntityBaseGenerator(BlockEntityType<? extends TileEntityBaseGenerator> blockEntityType, BlockPos blockPos, BlockState blockState, double d, int n, int n2) {
        super((BlockEntityType<? extends TileEntityInventory>)blockEntityType, blockPos, blockState);
        this.production = d;
        this.ticksSinceLastActiveUpdate = IC2.random.m_188503_(256);
        this.chargeSlot = new InvSlotCharge(this, 1);
        this.energy = this.addComponent(Energy.asBasicSource(this, n2, n).addManagedSlot(this.chargeSlot));
    }

    @Override
    public void m_142466_(CompoundTag compoundTag) {
        super.m_142466_(compoundTag);
        this.fuel = compoundTag.m_128451_("fuel");
    }

    @Override
    public void m_183515_(CompoundTag compoundTag) {
        super.m_183515_(compoundTag);
        compoundTag.m_128405_("fuel", this.fuel);
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        boolean bl = false;
        if (this.needsFuel()) {
            bl = this.gainFuel();
        }
        boolean bl2 = this.gainEnergy();
        if (bl) {
            this.m_6596_();
        }
        if (!this.delayActiveUpdate()) {
            this.setActiveState(bl2);
        } else {
            if (this.ticksSinceLastActiveUpdate % 256 == 0) {
                this.setActiveState(this.activityMeter > 0);
                this.activityMeter = 0;
            }
            this.activityMeter = bl2 ? ++this.activityMeter : --this.activityMeter;
            ++this.ticksSinceLastActiveUpdate;
        }
    }

    public boolean gainEnergy() {
        if (this.isConverting()) {
            this.energy.addEnergy(this.production);
            --this.fuel;
            return true;
        }
        return false;
    }

    public boolean isConverting() {
        return !this.needsFuel() && this.energy.getFreeEnergy() >= this.production;
    }

    public boolean needsFuel() {
        return this.fuel <= 0 && this.energy.getFreeEnergy() >= this.production;
    }

    public abstract boolean gainFuel();

    protected boolean delayActiveUpdate() {
        return false;
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
    public void onNetworkUpdate(String string) {
        super.onNetworkUpdate(string);
    }
}

