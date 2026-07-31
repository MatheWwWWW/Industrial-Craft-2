/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.fluids.FluidStack
 */
package ic2.api.recipe;

import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.MachineRecipeResult;
import ic2.api.util.FluidContainerOutputMode;
import java.util.Collection;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface IFillFluidContainerRecipeManager
extends IMachineRecipeManager<Void, Collection<ItemStack>, Input> {
    public MachineRecipeResult<Void, Collection<ItemStack>, Input> apply(Input var1, FluidContainerOutputMode var2, boolean var3);

    public static class Input {
        public final ItemStack container;
        public final FluidStack fluid;

        public Input(ItemStack container, FluidStack fluid) {
            this.container = container;
            this.fluid = fluid;
        }
    }
}

