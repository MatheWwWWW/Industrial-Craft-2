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
import ic2.core.block.heatgenerator.container.ContainerRTHeatGenerator;
import ic2.core.block.invslot.InvSlotConsumable;
import ic2.core.block.invslot.InvSlotConsumableItemStack;
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
public class TileEntityRTHeatGenerator
extends TileEntityHeatSourceInventory
implements IHasGui {
    private boolean newActive;
    public final InvSlotConsumable fuelSlot = new InvSlotConsumableItemStack(this, "fuelSlot", 6, new ItemStack((ItemLike)Ic2Items.RTG_PELLET));
    public static final float outputMultiplier = 2.0f * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/heatgenerator/radioisotope");

    public TileEntityRTHeatGenerator(BlockPos blockPos, BlockState blockState) {
        super((BlockEntityType<? extends TileEntityHeatSourceInventory>)Ic2BlockEntities.RT_HEAT_GENERATOR, blockPos, blockState);
        this.fuelSlot.setStackSizeLimit(1);
        this.newActive = false;
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        this.newActive = this.HeatBuffer > 0;
        if (this.getActive() != this.newActive) {
            this.setActive(this.newActive);
        }
    }

    @Override
    protected int fillHeatBuffer(int n) {
        if (n >= this.getMaxHeatEmittedPerTick()) {
            return this.getMaxHeatEmittedPerTick();
        }
        return n;
    }

    @Override
    public int getMaxHeatEmittedPerTick() {
        int n = 0;
        for (int i = 0; i < this.fuelSlot.size(); ++i) {
            if (this.fuelSlot.isEmpty(i)) continue;
            ++n;
        }
        if (n == 0) {
            return 0;
        }
        return (int)(Math.pow(2.0, n - 1) * (double)outputMultiplier);
    }

    public ContainerBase<TileEntityRTHeatGenerator> createServerScreenHandler(int n, Player player) {
        return new ContainerRTHeatGenerator(n, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return new ContainerRTHeatGenerator(n, inventory, this);
    }
}

