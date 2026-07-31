/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.heatgenerator.tileentity;

import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.comp.Energy;
import ic2.core.block.heatgenerator.container.ContainerElectricHeatGenerator;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumable;
import ic2.core.block.invslot.InvSlotConsumableItemStack;
import ic2.core.block.invslot.InvSlotDischarge;
import ic2.core.block.tileentity.TileEntityHeatSourceInventory;
import ic2.core.init.MainConfig;
import ic2.core.network.GrowingBuffer;
import ic2.core.profile.NotClassic;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.ref.Ic2Items;
import ic2.core.util.ConfigUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

@NotClassic
public class TileEntityElectricHeatGenerator
extends TileEntityHeatSourceInventory
implements IHasGui {
    private boolean newActive;
    public final InvSlotDischarge dischargeSlot;
    public final InvSlotConsumable coilSlot = new InvSlotConsumableItemStack(this, "CoilSlot", 10, new ItemStack((ItemLike)Ic2Items.COIL));
    protected final Energy energy;
    public static final double outputMultiplier = ConfigUtil.getFloat(MainConfig.get(), "balance/energy/heatgenerator/electric");

    public TileEntityElectricHeatGenerator(BlockPos blockPos, BlockState blockState) {
        super((BlockEntityType<? extends TileEntityHeatSourceInventory>)Ic2BlockEntities.ELECTRIC_HEAT_GENERATOR, blockPos, blockState);
        this.coilSlot.setStackSizeLimit(1);
        this.dischargeSlot = new InvSlotDischarge(this, InvSlot.Access.NONE, 4);
        this.energy = this.addComponent(Energy.asBasicSink(this, 10000.0, 4).addManagedSlot(this.dischargeSlot));
        this.newActive = false;
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        if (this.getActive() != this.newActive) {
            this.setActive(this.newActive);
        }
    }

    public ContainerBase<TileEntityElectricHeatGenerator> createServerScreenHandler(int n, Player player) {
        return new ContainerElectricHeatGenerator(n, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return new ContainerElectricHeatGenerator(n, inventory, this);
    }

    @Override
    protected int fillHeatBuffer(int n) {
        int n2 = Math.min(n, (int)(this.energy.getEnergy() / outputMultiplier));
        if (n2 > 0) {
            this.energy.useEnergy((double)n2 / outputMultiplier);
            this.newActive = true;
        } else {
            this.newActive = false;
        }
        return n2;
    }

    @Override
    public int getMaxHeatEmittedPerTick() {
        int n = 0;
        for (int i = 0; i < this.coilSlot.size(); ++i) {
            if (this.coilSlot.isEmpty(i)) continue;
            ++n;
        }
        return n * 10;
    }

    public final float getChargeLevel() {
        return (float)Math.min(1.0, this.energy.getFillRatio());
    }
}

