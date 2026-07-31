/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.kineticgenerator.tileentity;

import ic2.api.energy.tile.IKineticSource;
import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.comp.Energy;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumableItemStack;
import ic2.core.block.invslot.InvSlotDischarge;
import ic2.core.block.kineticgenerator.container.ContainerElectricKineticGenerator;
import ic2.core.block.tileentity.TileEntityInventory;
import ic2.core.init.MainConfig;
import ic2.core.network.GrowingBuffer;
import ic2.core.profile.NotClassic;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.ref.Ic2Items;
import ic2.core.util.ConfigUtil;
import java.util.Collections;
import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

@NotClassic
public class TileEntityElectricKineticGenerator
extends TileEntityInventory
implements IKineticSource,
IHasGui {
    public InvSlotConsumableItemStack slotMotor;
    public InvSlotDischarge dischargeSlot;
    private final float kuPerEU = 4.0f * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/kineticgenerator/electric");
    public double ku = 0.0;
    public final int maxKU = 1000;
    protected final Energy energy;

    public TileEntityElectricKineticGenerator(BlockPos blockPos, BlockState blockState) {
        super((BlockEntityType<? extends TileEntityInventory>)Ic2BlockEntities.ELECTRIC_KINETIC_GENERATOR, blockPos, blockState);
        this.slotMotor = new InvSlotConsumableItemStack(this, "slotMotor", 10, new ItemStack((ItemLike)Ic2Items.ELECTRIC_MOTOR));
        this.slotMotor.setStackSizeLimit(1);
        this.dischargeSlot = new InvSlotDischarge(this, InvSlot.Access.NONE, 4);
        this.energy = this.addComponent(Energy.asBasicSink(this, 10000.0, 4).addManagedSlot(this.dischargeSlot));
    }

    @Override
    public void m_142466_(CompoundTag compoundTag) {
        super.m_142466_(compoundTag);
        this.updateDirections();
    }

    @Override
    protected void setFacing(Level level, Direction direction) {
        super.setFacing(level, direction);
        this.updateDirections();
    }

    private void updateDirections() {
        this.energy.setDirections(EnumSet.complementOf(EnumSet.of(this.getFacing())), Collections.emptySet());
    }

    @Override
    public int maxrequestkineticenergyTick(Direction direction) {
        return this.drawKineticEnergy(direction, Integer.MAX_VALUE, true);
    }

    @Override
    public int getConnectionBandwidth(Direction direction) {
        if (direction != this.getFacing()) {
            return 0;
        }
        return this.getMaxKU();
    }

    public int getMaxKU() {
        int n = 0;
        int n2 = this.getMaxKUForGUI() / 10;
        for (int i = 0; i < this.slotMotor.size(); ++i) {
            if (this.slotMotor.isEmpty(i)) continue;
            n += n2;
        }
        return n;
    }

    public int getMaxKUForGUI() {
        return 1000;
    }

    @Override
    public int requestkineticenergy(Direction direction, int n) {
        return this.drawKineticEnergy(direction, n, false);
    }

    @Override
    public int drawKineticEnergy(Direction direction, int n, boolean bl) {
        if (direction != this.getFacing()) {
            return 0;
        }
        int n2 = (int)Math.min((double)this.getMaxKU(), this.ku);
        int n3 = Math.min(n, n2);
        if (!bl) {
            this.ku -= (double)n3;
            this.m_6596_();
        }
        return n3;
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        boolean bl = false;
        if (1000.0 - this.ku > 1.0) {
            double d = Math.min(1000.0 - this.ku, this.energy.getEnergy() * (double)this.kuPerEU);
            this.energy.useEnergy(d / (double)this.kuPerEU);
            this.ku += d;
            if (d > 0.0) {
                this.m_6596_();
                bl = true;
            }
        }
        this.setActive(bl);
    }

    public ContainerBase<TileEntityElectricKineticGenerator> createServerScreenHandler(int n, Player player) {
        return new ContainerElectricKineticGenerator(n, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return new ContainerElectricKineticGenerator(n, inventory, this);
    }
}

