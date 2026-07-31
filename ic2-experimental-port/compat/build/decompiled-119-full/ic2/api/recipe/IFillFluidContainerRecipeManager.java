/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.recipe;

import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.MachineRecipeResult;
import ic2.api.util.FluidContainerOutputMode;
import ic2.core.fluid.Ic2FluidStack;
import java.util.Collection;
import net.minecraft.world.item.ItemStack;

public interface IFillFluidContainerRecipeManager
extends IMachineRecipeManager<Void, Collection<ItemStack>, Input> {
    public MachineRecipeResult<Void, Collection<ItemStack>, Input> apply(Input var1, FluidContainerOutputMode var2, boolean var3);

    public static class Input {
        public final ItemStack container;
        public final Ic2FluidStack fluid;

        public Input(ItemStack itemStack, Ic2FluidStack ic2FluidStack) {
            this.container = itemStack;
            this.fluid = ic2FluidStack;
        }
    }
}

