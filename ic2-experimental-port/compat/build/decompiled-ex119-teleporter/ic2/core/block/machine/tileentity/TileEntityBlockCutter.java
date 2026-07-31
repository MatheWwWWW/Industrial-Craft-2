/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machine.tileentity;

import ic2.api.item.IBlockCuttingBlade;
import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.MachineRecipeResult;
import ic2.api.recipe.Recipes;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.ContainerBase;
import ic2.core.block.IInventorySlotHolder;
import ic2.core.block.invslot.InvSlotConsumableClass;
import ic2.core.block.invslot.InvSlotProcessableGeneric;
import ic2.core.block.machine.tileentity.TileEntityStandardMachine;
import ic2.core.gui.dynamic.DynamicContainer;
import ic2.core.network.GrowingBuffer;
import ic2.core.network.GuiSynced;
import ic2.core.profile.NotClassic;
import ic2.core.ref.Ic2BlockEntities;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

@NotClassic
public class TileEntityBlockCutter
extends TileEntityStandardMachine<IRecipeInput, Collection<ItemStack>, ItemStack> {
    @GuiSynced
    private boolean bladeTooWeak = false;
    public final InvSlotConsumableClass cutterSlot;

    public TileEntityBlockCutter(BlockPos blockPos, BlockState blockState) {
        super(Ic2BlockEntities.BLOCK_CUTTER, blockPos, blockState, 4, 450, 1);
        this.inputSlot = new InvSlotProcessableGeneric((IInventorySlotHolder<?>)this, "input", 1, (Recipes.IGetter<? extends IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ItemStack>>)Recipes.blockcutter);
        this.cutterSlot = new InvSlotConsumableClass(this, "cutterInputSlot", 1, IBlockCuttingBlade.class);
    }

    public static void init() {
    }

    @Override
    public MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> getOutput() {
        MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> machineRecipeResult;
        if (this.cutterSlot.isEmpty()) {
            if (!this.bladeTooWeak) {
                this.bladeTooWeak = true;
            }
            return null;
        }
        if (this.bladeTooWeak) {
            this.bladeTooWeak = false;
        }
        if ((machineRecipeResult = super.getOutput()) == null || machineRecipeResult.getRecipe().getMetaData() == null) {
            return null;
        }
        ItemStack itemStack = this.cutterSlot.get();
        IBlockCuttingBlade iBlockCuttingBlade = (IBlockCuttingBlade)itemStack.m_41720_();
        if (machineRecipeResult.getRecipe().getMetaData().m_128451_("hardness") > iBlockCuttingBlade.getHardness(itemStack)) {
            if (!this.bladeTooWeak) {
                this.bladeTooWeak = true;
            }
            return null;
        }
        if (this.bladeTooWeak) {
            this.bladeTooWeak = false;
        }
        return machineRecipeResult;
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
    public boolean getGuiState(String string) {
        if ("isBladeTooWeak".equals(string)) {
            return this.bladeTooWeak;
        }
        return super.getGuiState(string);
    }

    @Override
    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.Processing, UpgradableProperty.Transformer, UpgradableProperty.EnergyStorage, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing);
    }
}

