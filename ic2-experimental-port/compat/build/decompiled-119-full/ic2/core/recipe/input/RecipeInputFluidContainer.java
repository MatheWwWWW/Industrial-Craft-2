/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  net.minecraft.core.Registry
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.material.Fluid
 *  org.apache.commons.lang3.mutable.Mutable
 *  org.apache.commons.lang3.mutable.MutableObject
 */
package ic2.core.recipe.input;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ic2.core.fluid.FluidHandler;
import ic2.core.fluid.Ic2FluidStack;
import ic2.core.recipe.input.RecipeInputBase;
import ic2.core.util.StackUtil;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableObject;

public class RecipeInputFluidContainer
extends RecipeInputBase {
    public final Fluid fluid;
    public final int amount;
    private static volatile List<ItemStack> containerItems = null;

    RecipeInputFluidContainer(Fluid fluid) {
        this(fluid, 1000);
    }

    public RecipeInputFluidContainer(Fluid fluid, int n) {
        this.fluid = fluid;
        this.amount = n;
    }

    @Override
    public boolean matches(ItemStack itemStack) {
        Ic2FluidStack ic2FluidStack = Ic2FluidStack.get(itemStack);
        if (ic2FluidStack == null || ic2FluidStack.isEmpty()) {
            return this.fluid == null;
        }
        return ic2FluidStack.getFluid() == this.fluid && ic2FluidStack.getAmountMb() >= this.amount;
    }

    @Override
    public int getAmount() {
        return 1;
    }

    @Override
    protected List<ItemStack> listStacks() {
        return RecipeInputFluidContainer.getFluidContainer(this.fluid);
    }

    public String toString() {
        return "RInputFluidContainer<" + this.amount + "x" + this.fluid + ">";
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean equals(Object object) {
        if (object == null) return false;
        if (this.getClass() != object.getClass()) return false;
        RecipeInputFluidContainer recipeInputFluidContainer = (RecipeInputFluidContainer)object;
        if (recipeInputFluidContainer.fluid != this.fluid) return false;
        if (recipeInputFluidContainer.amount != this.amount) return false;
        return true;
    }

    @Override
    public JsonElement toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("fluid", Registry.f_122822_.m_7981_((Object)this.fluid).toString());
        jsonObject.addProperty("amount", (Number)this.amount);
        return jsonObject;
    }

    public static List<ItemStack> getFluidContainer(Fluid fluid) {
        List<ItemStack> list = RecipeInputFluidContainer.getContainerItems();
        if (fluid == null) {
            return list;
        }
        ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>();
        Ic2FluidStack ic2FluidStack = Ic2FluidStack.create(fluid, Integer.MAX_VALUE);
        MutableObject mutableObject = new MutableObject();
        for (ItemStack itemStack : list) {
            Ic2FluidStack ic2FluidStack2;
            int n = FluidHandler.fillMb(itemStack.m_41777_(), ic2FluidStack, false, (Mutable<ItemStack>)mutableObject);
            if (n <= 0 || mutableObject.getValue() == null || (ic2FluidStack2 = FluidHandler.drainMb((ItemStack)mutableObject.getValue(), Integer.MAX_VALUE, true, null)) == null || ic2FluidStack2.getAmountMb() <= 0) continue;
            arrayList.add((ItemStack)mutableObject.getValue());
        }
        return arrayList;
    }

    private static List<ItemStack> getContainerItems() {
        List<ItemStack> list = containerItems;
        if (list != null) {
            return list;
        }
        list = new ArrayList<ItemStack>();
        MutableObject mutableObject = new MutableObject();
        for (Item item : Registry.f_122827_) {
            if (item == Items.f_41852_) continue;
            ItemStack itemStack = new ItemStack((ItemLike)item);
            assert (!itemStack.m_41619_());
            Ic2FluidStack ic2FluidStack = FluidHandler.drainMb(itemStack, Integer.MAX_VALUE, false, (Mutable<ItemStack>)mutableObject);
            if (ic2FluidStack == null || (itemStack = (ItemStack)mutableObject.getValue()).m_41619_() || (ic2FluidStack = FluidHandler.drainMb(itemStack, Integer.MAX_VALUE, true, null)) == null || !ic2FluidStack.isEmpty()) continue;
            boolean bl = false;
            for (ItemStack itemStack2 : list) {
                if (!StackUtil.checkItemEqualityStrict(itemStack2, itemStack)) continue;
                bl = true;
                break;
            }
            if (bl) continue;
            list.add(itemStack);
        }
        containerItems = list;
        return list;
    }
}

