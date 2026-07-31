/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machine.tileentity;

import ic2.api.recipe.IBasicMachineRecipeManager;
import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.MachineRecipe;
import ic2.api.recipe.MachineRecipeResult;
import ic2.api.recipe.RecipeOutput;
import ic2.api.recipe.Recipes;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.IC2;
import ic2.core.block.IInventorySlotHolder;
import ic2.core.block.invslot.InvSlotProcessableGeneric;
import ic2.core.block.machine.tileentity.TileEntityStandardMachine;
import ic2.core.init.MainConfig;
import ic2.core.recipe.BasicListRecipeManager;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.ref.Ic2Items;
import ic2.core.ref.Ic2SoundEvents;
import ic2.core.util.ConfigUtil;
import ic2.core.util.StackUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityRecycler
extends TileEntityStandardMachine<IRecipeInput, Collection<ItemStack>, ItemStack> {
    public TileEntityRecycler(BlockPos blockPos, BlockState blockState) {
        super(Ic2BlockEntities.RECYCLER, blockPos, blockState, 1, 45, 1);
        this.inputSlot = new InvSlotProcessableGeneric((IInventorySlotHolder<?>)this, "input", 1, (Recipes.IGetter<? extends IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ItemStack>>)((Recipes.IGetter<IMachineRecipeManager>)level -> Recipes.recycler));
    }

    public static void init() {
        Recipes.recycler = new RecyclerRecipeManager();
        Recipes.recyclerWhitelist = new BasicListRecipeManager();
        Recipes.recyclerBlacklist = new BasicListRecipeManager();
    }

    public static void initLate() {
        for (IRecipeInput iRecipeInput : ConfigUtil.asRecipeInputList(MainConfig.get(), "balance/recyclerBlacklist")) {
            Recipes.recyclerBlacklist.add(iRecipeInput);
        }
        for (IRecipeInput iRecipeInput : ConfigUtil.asRecipeInputList(MainConfig.get(), "balance/recyclerWhitelist")) {
            Recipes.recyclerWhitelist.add(iRecipeInput);
        }
    }

    public static int recycleChance() {
        return 8;
    }

    @Override
    public SoundEvent getLoopingSoundEvent() {
        return Ic2SoundEvents.MACHINE_RECYCLER_OPERATE;
    }

    @Override
    public SoundEvent getInterruptSoundEvent() {
        return Ic2SoundEvents.MACHINE_INTERRUPT1;
    }

    public static boolean getIsItemBlacklisted(ItemStack itemStack) {
        if (Recipes.recyclerWhitelist.isEmpty()) {
            return Recipes.recyclerBlacklist.contains(itemStack);
        }
        return !Recipes.recyclerWhitelist.contains(itemStack);
    }

    @Override
    public void operateOnce(MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> machineRecipeResult, Collection<ItemStack> collection) {
        this.inputSlot.consume(machineRecipeResult);
        if (IC2.random.m_188503_(TileEntityRecycler.recycleChance()) == 0) {
            this.outputSlot.add(collection);
        }
    }

    @Override
    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.Processing, UpgradableProperty.Transformer, UpgradableProperty.EnergyStorage, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing);
    }

    private static class RecyclerRecipeManager
    implements IBasicMachineRecipeManager {
        public boolean addRecipe(IRecipeInput iRecipeInput, Collection<ItemStack> collection, CompoundTag compoundTag, boolean bl) {
            return false;
        }

        public boolean addRecipe(IRecipeInput iRecipeInput, CompoundTag compoundTag, boolean bl, ItemStack ... itemStackArray) {
            return false;
        }

        @Override
        public RecipeOutput getOutputFor(ItemStack itemStack, boolean bl) {
            if (StackUtil.isEmpty(itemStack)) {
                return null;
            }
            RecipeOutput recipeOutput = new RecipeOutput(null, new ArrayList<ItemStack>(RecyclerRecipeManager.getOutput(itemStack)));
            if (bl) {
                itemStack.m_41774_(1);
            }
            return recipeOutput;
        }

        private static Collection<ItemStack> getOutput(ItemStack itemStack) {
            return TileEntityRecycler.getIsItemBlacklisted(itemStack) ? Collections.emptyList() : Collections.singletonList(new ItemStack((ItemLike)Ic2Items.SCRAP));
        }

        @Override
        public MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> apply(ItemStack itemStack, boolean bl) {
            if (StackUtil.isEmpty(itemStack)) {
                return null;
            }
            return new MachineRecipe<IRecipeInput, Collection<ItemStack>>(Recipes.inputFactory.forStack(itemStack, 1), RecyclerRecipeManager.getOutput(itemStack)).getResult(StackUtil.copyWithSize(itemStack, StackUtil.getSize(itemStack) - 1));
        }

        @Override
        public Iterable<? extends MachineRecipe<IRecipeInput, Collection<ItemStack>>> getRecipes() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isIterable() {
            return false;
        }
    }
}

