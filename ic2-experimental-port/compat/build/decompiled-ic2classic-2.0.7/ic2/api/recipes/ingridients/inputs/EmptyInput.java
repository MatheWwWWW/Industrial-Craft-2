/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.Ingredient
 */
package ic2.api.recipes.ingridients.inputs;

import com.google.gson.JsonObject;
import ic2.api.recipes.ingridients.inputs.IInput;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.List;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class EmptyInput
implements IInput {
    public static final IInput INSTANCE = new EmptyInput();

    private EmptyInput() {
    }

    @Override
    public List<ItemStack> getComponents() {
        return ObjectLists.singleton((Object)ItemStack.f_41583_);
    }

    @Override
    public Ingredient asIngredient() {
        return Ingredient.f_43901_;
    }

    @Override
    public int getInputSize() {
        return 0;
    }

    @Override
    public boolean matches(ItemStack stack) {
        return stack.m_41619_();
    }

    @Override
    public void serialize(FriendlyByteBuf buffer) {
    }

    @Override
    public JsonObject serialize() {
        return new JsonObject();
    }

    public static class InternalInput
    extends EmptyInput {
        public static final IInput INSTANCE = new InternalInput();
    }
}

