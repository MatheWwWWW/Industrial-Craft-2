/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.fluids.Fluid
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.FluidUtil
 *  net.minecraftforge.fluids.capability.IFluidHandlerItem
 *  net.minecraftforge.fml.common.Loader
 *  net.minecraftforge.fml.common.LoaderState
 *  net.minecraftforge.fml.common.registry.ForgeRegistries
 */
package ic2.core.recipe;

import ic2.api.recipe.IRecipeInput;
import ic2.core.IC2;
import ic2.core.recipe.RecipeInputBase;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderState;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class RecipeInputFluidContainer
extends RecipeInputBase
implements IRecipeInput {
    private final Fluid fluid;
    private final int amount;
    private static volatile FluidHandlerInfo fluidHandlerInfo = new FluidHandlerInfo(Collections.emptyList(), LoaderState.PREINITIALIZATION);

    RecipeInputFluidContainer(Fluid fluid) {
        this(fluid, 1000);
    }

    RecipeInputFluidContainer(Fluid fluid, int amount) {
        this.fluid = fluid;
        this.amount = amount;
    }

    @Override
    public boolean matches(ItemStack subject) {
        FluidStack fs = FluidUtil.getFluidContained((ItemStack)subject);
        return fs == null && this.fluid == null || fs != null && fs.getFluid() == this.fluid && fs.amount >= this.amount;
    }

    @Override
    public int getAmount() {
        return 1;
    }

    @Override
    public List<ItemStack> getInputs() {
        return RecipeInputFluidContainer.getFluidContainer(this.fluid);
    }

    public String toString() {
        return "RInputFluidContainer<" + this.amount + "x" + this.fluid.getName() + ">";
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        RecipeInputFluidContainer other = (RecipeInputFluidContainer)obj;
        if (other.fluid != this.fluid) return false;
        if (other.amount != this.amount) return false;
        return true;
    }

    public static List<ItemStack> getFluidContainer(Fluid fluid) {
        FluidHandlerInfo info = fluidHandlerInfo;
        if (info.loaderState != LoaderState.AVAILABLE && info.loaderState != Loader.instance().getLoaderState()) {
            ArrayList<ItemStack> list = new ArrayList<ItemStack>();
            for (Item item : ForgeRegistries.ITEMS) {
                ItemStack stack = new ItemStack(item);
                IFluidHandlerItem handler = FluidUtil.getFluidHandler((ItemStack)stack);
                if (handler == null) continue;
                handler.drain(Integer.MAX_VALUE, true);
                ItemStack container = handler.getContainer();
                if (container == null) {
                    IC2.platform.displayError("Null container stack!\nItem: %s\nRegistry: %s\nUnlocalised: %s\nHandler: %s (%s)", item, item.getRegistryName(), item.func_77658_a(), handler, handler.getClass());
                }
                if (FluidUtil.getFluidContained((ItemStack)container) != null) continue;
                list.add(stack);
            }
            LoaderState state = Loader.instance().hasReachedState(LoaderState.AVAILABLE) ? LoaderState.AVAILABLE : Loader.instance().getLoaderState();
            fluidHandlerInfo = info = new FluidHandlerInfo(Collections.unmodifiableList(list), state);
        }
        if (fluid == null) {
            return info.items;
        }
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        for (ItemStack stack : info.items) {
            ItemStack container;
            IFluidHandlerItem handler = FluidUtil.getFluidHandler((ItemStack)stack.func_77946_l());
            if (handler == null || handler.fill(new FluidStack(fluid, Integer.MAX_VALUE), true) <= 0 || FluidUtil.getFluidContained((ItemStack)(container = handler.getContainer())) == null) continue;
            ret.add(container);
        }
        return ret;
    }

    private static class FluidHandlerInfo {
        final List<ItemStack> items;
        final LoaderState loaderState;

        FluidHandlerInfo(List<ItemStack> items, LoaderState loaderState) {
            this.items = items;
            this.loaderState = loaderState;
        }
    }
}

