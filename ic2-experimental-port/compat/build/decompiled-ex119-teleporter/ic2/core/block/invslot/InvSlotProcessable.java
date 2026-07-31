/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.block.invslot;

import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.MachineRecipeResult;
import ic2.api.recipe.Recipes;
import ic2.core.block.IInventorySlotHolder;
import ic2.core.block.invslot.InvSlotConsumable;
import ic2.core.util.StackUtil;
import net.minecraft.world.item.ItemStack;

public abstract class InvSlotProcessable<RI, RO, I>
extends InvSlotConsumable {
    protected Recipes.IGetter<? extends IMachineRecipeManager<RI, RO, I>> recipeManager;

    public InvSlotProcessable(IInventorySlotHolder<?> iInventorySlotHolder, String string, int n, Recipes.IGetter<? extends IMachineRecipeManager<RI, RO, I>> iGetter) {
        super(iInventorySlotHolder, string, n);
        this.recipeManager = iGetter;
    }

    @Override
    public boolean accepts(ItemStack itemStack) {
        ItemStack itemStack2 = StackUtil.copyWithSize(itemStack, Integer.MAX_VALUE);
        return this.getOutputFor(this.getInput(itemStack2), true) != null;
    }

    public MachineRecipeResult<RI, RO, I> process() {
        ItemStack itemStack = this.get();
        if (StackUtil.isEmpty(itemStack) && !this.allowEmptyInput()) {
            return null;
        }
        return this.getOutputFor(this.getInput(itemStack), false);
    }

    public void consume(MachineRecipeResult<RI, RO, I> machineRecipeResult) {
        if (machineRecipeResult == null) {
            throw new NullPointerException("null result");
        }
        ItemStack itemStack = this.get();
        if (StackUtil.isEmpty(itemStack) && !this.allowEmptyInput()) {
            throw new IllegalStateException("consume from empty slot");
        }
        this.setInput(machineRecipeResult.getAdjustedInput());
    }

    public void setRecipeManager(Recipes.IGetter<? extends IMachineRecipeManager<RI, RO, I>> iGetter) {
        this.recipeManager = iGetter;
    }

    protected boolean allowEmptyInput() {
        return false;
    }

    protected MachineRecipeResult<RI, RO, I> getOutputFor(I i, boolean bl) {
        return this.recipeManager.get(this.base.getParent().m_58904_()).apply(i, bl);
    }

    protected abstract I getInput(ItemStack var1);

    protected abstract void setInput(I var1);
}

