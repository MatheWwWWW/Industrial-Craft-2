/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.nbt.TagParser
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.api.recipes.ingridients.inputs;

import com.google.gson.JsonObject;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

public interface IInput {
    default public Ingredient asIngredient() {
        List<ItemStack> stacks = this.getComponents();
        return Ingredient.m_43927_((ItemStack[])stacks.toArray(new ItemStack[stacks.size()]));
    }

    public List<ItemStack> getComponents();

    public int getInputSize();

    public boolean matches(ItemStack var1);

    default public boolean matchesAll(ItemStack ... stacks) {
        for (ItemStack stack : stacks) {
            if (this.matches(stack)) continue;
            return false;
        }
        return true;
    }

    default public boolean matchesAny(ItemStack ... stacks) {
        for (ItemStack stack : stacks) {
            if (!this.matches(stack)) continue;
            return true;
        }
        return false;
    }

    public void serialize(FriendlyByteBuf var1);

    public JsonObject serialize();

    public static JsonObject writeItemStack(ItemStack stack) {
        JsonObject obj = new JsonObject();
        obj.addProperty("item", ForgeRegistries.ITEMS.getKey((Object)stack.m_41720_()).toString());
        obj.addProperty("count", (Number)stack.m_41613_());
        if (stack.m_41782_()) {
            obj.addProperty("nbt", stack.m_41783_().toString());
        }
        return obj;
    }

    public static JsonObject writeFluidStack(FluidStack stack) {
        JsonObject obj = new JsonObject();
        obj.addProperty("fluid", ForgeRegistries.FLUIDS.getKey((Object)stack.getFluid()).toString());
        obj.addProperty("amount", (Number)stack.getAmount());
        if (stack.hasTag()) {
            obj.addProperty("nbt", stack.getTag().toString());
        }
        return obj;
    }

    public static FluidStack readFluidStack(JsonObject obj) {
        return new FluidStack((Fluid)ForgeRegistries.FLUIDS.getValue(ResourceLocation.m_135820_((String)obj.get("fluid").getAsString())), obj.get("amount").getAsInt(), obj.has("nbt") ? IInput.readNBT(obj.get("nb").getAsString()) : null);
    }

    public static CompoundTag readNBT(String s) {
        try {
            return TagParser.m_129359_((String)s);
        }
        catch (Exception e) {
            return null;
        }
    }

    public static ItemStack copyWithSize(ItemStack stack, int newSize) {
        ItemStack copy = stack.m_41777_();
        copy.m_41764_(newSize);
        return copy;
    }

    public static boolean isStackEqual(ItemStack key, ItemStack other) {
        return key.m_41656_(other) && IInput.isNBTExact(key, other);
    }

    public static boolean isNBTExact(ItemStack subject, ItemStack target) {
        boolean key = subject.m_41782_();
        boolean value = target.m_41782_();
        if (!key && !value) {
            return true;
        }
        if (!key || !value) {
            return false;
        }
        CompoundTag keyTag = subject.m_41783_();
        CompoundTag valueTag = target.m_41783_();
        if (keyTag.m_128440_() != valueTag.m_128440_()) {
            return false;
        }
        for (String entry : keyTag.m_128431_()) {
            Tag subjectNBT = keyTag.m_128423_(entry);
            Tag targetNBT = valueTag.m_128423_(entry);
            if (targetNBT != null && subjectNBT.m_7060_() == targetNBT.m_7060_() && subjectNBT.equals(targetNBT)) continue;
            return false;
        }
        return false;
    }
}

