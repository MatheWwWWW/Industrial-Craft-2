/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Fluids
 */
package ic2.core.block.machine.tileentity;

import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.MachineRecipe;
import ic2.api.recipe.MachineRecipeResult;
import ic2.api.recipe.Recipes;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.block.IInventorySlotHolder;
import ic2.core.block.invslot.InvSlotProcessableGeneric;
import ic2.core.block.machine.tileentity.TileEntityPump;
import ic2.core.block.machine.tileentity.TileEntityStandardMachine;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.ref.Ic2SoundEvents;
import ic2.core.util.LiquidUtil;
import ic2.core.util.Util;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class TileEntityCompressor
extends TileEntityStandardMachine<IRecipeInput, Collection<ItemStack>, ItemStack> {
    protected boolean usingPumpRecipe;
    protected final Set<TileEntityPump> pumps = new HashSet<TileEntityPump>(12, 0.5f);

    public TileEntityCompressor(BlockPos blockPos, BlockState blockState) {
        super(Ic2BlockEntities.COMPRESSOR, blockPos, blockState, 2, 300, 1);
        this.inputSlot = new InvSlotProcessableGeneric((IInventorySlotHolder<?>)this, "input", 1, (Recipes.IGetter<? extends IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ItemStack>>)Recipes.compressor);
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();
        this.findPumps();
    }

    @Override
    protected void onNeighborChange(Block block, BlockPos blockPos) {
        super.onNeighborChange(block, blockPos);
        this.findPumps();
    }

    protected void findPumps() {
        Level level = this.m_58904_();
        this.pumps.clear();
        for (Direction direction : Util.ALL_DIRS) {
            BlockEntity blockEntity = level.m_7702_(this.f_58858_.m_121945_(direction));
            if (!(blockEntity instanceof TileEntityPump)) continue;
            this.pumps.add((TileEntityPump)blockEntity);
        }
    }

    @Override
    public MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> getOutput() {
        this.usingPumpRecipe = false;
        MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> machineRecipeResult = super.getOutput();
        if (machineRecipeResult != null) {
            return machineRecipeResult;
        }
        if (!this.pumps.isEmpty() && this.inputSlot.isEmpty() && this.outputSlot.canAdd(new ItemStack((ItemLike)Items.f_42452_))) {
            int n = 1000;
            for (TileEntityPump tileEntityPump : this.pumps) {
                int n2 = LiquidUtil.drainTile(tileEntityPump, Direction.UP, (Fluid)Fluids.f_76193_, n, true);
                if (n2 <= 0 || (n -= n2) > 0) continue;
                this.usingPumpRecipe = true;
                machineRecipeResult = new MachineRecipe<Object, List<ItemStack>>(null, Collections.singletonList(new ItemStack((ItemLike)Items.f_42452_))).getResult(null);
                break;
            }
        }
        return machineRecipeResult;
    }

    @Override
    public void operateOnce(MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> machineRecipeResult, Collection<ItemStack> collection) {
        if (this.usingPumpRecipe) {
            TileEntityPump tileEntityPump;
            int n;
            int n2 = 1000;
            Iterator<TileEntityPump> iterator = this.pumps.iterator();
            while (iterator.hasNext() && ((n = LiquidUtil.drainTile(tileEntityPump = iterator.next(), Direction.UP, (Fluid)Fluids.f_76193_, n2, false)) <= 0 || (n2 -= n) > 0)) {
            }
            assert (n2 == 0);
            this.outputSlot.add(collection);
        } else {
            super.operateOnce(machineRecipeResult, collection);
        }
    }

    @Override
    public SoundEvent getLoopingSoundEvent() {
        return Ic2SoundEvents.MACHINE_COMPRESSOR_OPERATE;
    }

    @Override
    public SoundEvent getInterruptSoundEvent() {
        return Ic2SoundEvents.MACHINE_INTERRUPT1;
    }

    @Override
    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.Processing, UpgradableProperty.Transformer, UpgradableProperty.EnergyStorage, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing);
    }
}

