/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemStack
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.World
 *  net.minecraftforge.fluids.FluidRegistry
 *  net.minecraftforge.fluids.FluidStack
 */
package ic2.core.block.machine.tileentity;

import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.MachineRecipe;
import ic2.api.recipe.MachineRecipeResult;
import ic2.api.recipe.Recipes;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.block.IInventorySlotHolder;
import ic2.core.block.invslot.InvSlotProcessableGeneric;
import ic2.core.block.machine.tileentity.TileEntityPump;
import ic2.core.block.machine.tileentity.TileEntityStandardMachine;
import ic2.core.recipe.BasicMachineRecipeManager;
import ic2.core.util.LiquidUtil;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class TileEntityCompressor
extends TileEntityStandardMachine<IRecipeInput, Collection<ItemStack>, ItemStack> {
    protected boolean usingPumpRecipe;
    protected final Set<TileEntityPump> pumps = new HashSet<TileEntityPump>(12, 0.5f);

    public TileEntityCompressor() {
        super(2, 300, 1);
        this.inputSlot = new InvSlotProcessableGeneric((IInventorySlotHolder<?>)this, "input", 1, Recipes.compressor);
    }

    public static void init() {
        Recipes.compressor = new BasicMachineRecipeManager();
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();
        this.findPumps();
    }

    @Override
    protected void onNeighborChange(Block neighbor, BlockPos neighborPos) {
        super.onNeighborChange(neighbor, neighborPos);
        this.findPumps();
    }

    protected void findPumps() {
        World world = this.func_145831_w();
        this.pumps.clear();
        for (EnumFacing side : EnumFacing.field_82609_l) {
            TileEntity te = world.func_175625_s(this.field_174879_c.func_177972_a(side));
            if (!(te instanceof TileEntityPump)) continue;
            this.pumps.add((TileEntityPump)te);
        }
    }

    @Override
    public MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> getOutput() {
        this.usingPumpRecipe = false;
        MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> output = super.getOutput();
        if (output != null) {
            return output;
        }
        if (!this.pumps.isEmpty() && this.inputSlot.isEmpty() && this.outputSlot.canAdd(new ItemStack(Items.field_151126_ay))) {
            FluidStack fluid = new FluidStack(FluidRegistry.WATER, 1000);
            for (TileEntityPump pump : this.pumps) {
                FluidStack amount = LiquidUtil.drainTile(pump, EnumFacing.UP, FluidRegistry.WATER, fluid.amount, true);
                if (amount != null) {
                    assert (amount.getFluid() == FluidRegistry.WATER);
                    fluid.amount -= amount.amount;
                }
                if (fluid.amount > 0) continue;
                this.usingPumpRecipe = true;
                output = new MachineRecipe<Object, List<ItemStack>>(null, Collections.singletonList(new ItemStack(Items.field_151126_ay))).getResult(null);
                break;
            }
        }
        return output;
    }

    @Override
    public void operateOnce(MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> output, Collection<ItemStack> processResult) {
        if (this.usingPumpRecipe) {
            FluidStack fluid = new FluidStack(FluidRegistry.WATER, 1000);
            for (TileEntityPump pump : this.pumps) {
                FluidStack amount = LiquidUtil.drainTile(pump, EnumFacing.UP, FluidRegistry.WATER, fluid.amount, false);
                if (amount != null && amount.getFluid() == FluidRegistry.WATER) {
                    fluid.amount -= amount.amount;
                }
                if (fluid.amount > 0) continue;
                break;
            }
            this.outputSlot.add(processResult);
        } else {
            super.operateOnce(output, processResult);
        }
    }

    @Override
    public String getStartSoundFile() {
        return "Machines/CompressorOp.ogg";
    }

    @Override
    public String getInterruptSoundFile() {
        return "Machines/InterruptOne.ogg";
    }

    @Override
    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.Processing, UpgradableProperty.Transformer, UpgradableProperty.EnergyStorage, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing);
    }
}

