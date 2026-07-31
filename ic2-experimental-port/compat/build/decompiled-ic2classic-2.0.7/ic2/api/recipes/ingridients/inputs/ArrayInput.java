/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.recipes.ingridients.inputs;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ic2.api.recipes.RecipeRegistry;
import ic2.api.recipes.ingridients.inputs.IInput;
import ic2.core.platform.recipes.misc.IngredientRegistry;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;

public class ArrayInput
implements IInput {
    List<IInput> inputs = new ObjectArrayList();
    boolean and;
    int stacksize;

    public ArrayInput(JsonObject obj) {
        this.stacksize = obj.get("size").getAsInt();
        this.and = obj.get("and").getAsBoolean();
        for (JsonElement element : obj.getAsJsonArray("elements")) {
            IInput input = RecipeRegistry.INGREDIENTS.readInput(element.getAsJsonObject());
            if (input == null) continue;
            this.inputs.add(input);
        }
    }

    public ArrayInput(FriendlyByteBuf buffer) {
        this.stacksize = buffer.readByte();
        this.and = buffer.readBoolean();
        int size = buffer.readByte();
        for (int i = 0; i < size; ++i) {
            IInput input = RecipeRegistry.INGREDIENTS.readInput(buffer);
            if (input == null) continue;
            this.inputs.add(input);
        }
    }

    public ArrayInput(boolean and, int size, IInput ... inputs) {
        this.inputs.addAll((Collection<IInput>)ObjectArrayList.wrap((Object[])inputs));
        this.and = and;
        this.stacksize = size;
    }

    public ArrayInput(Collection<IInput> inputs, int size, boolean and) {
        this.inputs.addAll(inputs);
        this.stacksize = size;
        this.and = and;
    }

    public ArrayInput(int size, boolean and, Object ... values) {
        this.stacksize = size;
        this.and = and;
        for (Object obj : values) {
            IInput input = RecipeRegistry.INGREDIENTS.createInputFrom(obj);
            if (input == null) {
                throw new IllegalStateException("Couldn't create Input from Object: " + obj);
            }
            this.inputs.add(input);
        }
    }

    @Override
    public List<ItemStack> getComponents() {
        ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
        for (IInput input : this.inputs) {
            stacks.addAll(input.getComponents());
        }
        return stacks;
    }

    @Override
    public int getInputSize() {
        return this.stacksize;
    }

    @Override
    public boolean matches(ItemStack stack) {
        for (IInput input : this.inputs) {
            if (input.matches(stack)) {
                if (this.and) continue;
                return true;
            }
            if (!this.and) continue;
            return false;
        }
        return this.and;
    }

    @Override
    public void serialize(FriendlyByteBuf buffer) {
        buffer.writeByte(this.stacksize);
        buffer.writeBoolean(this.and);
        buffer.writeByte(this.inputs.size());
        for (IInput input : this.inputs) {
            RecipeRegistry.INGREDIENTS.writeInput(input, buffer);
        }
    }

    @Override
    public JsonObject serialize() {
        JsonArray array = new JsonArray();
        for (IInput input : this.inputs) {
            array.add((JsonElement)IngredientRegistry.INSTANCE.serializeInput(input));
        }
        JsonObject obj = new JsonObject();
        obj.addProperty("size", (Number)this.stacksize);
        obj.addProperty("and", Boolean.valueOf(this.and));
        obj.add("elements", (JsonElement)array);
        return obj;
    }
}

