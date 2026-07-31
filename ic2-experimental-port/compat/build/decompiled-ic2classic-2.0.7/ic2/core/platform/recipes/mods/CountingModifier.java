/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.platform.recipes.mods;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ic2.api.recipes.ingridients.inputs.IInput;
import ic2.api.recipes.ingridients.inputs.ItemInput;
import ic2.core.platform.recipes.misc.IngredientRegistry;
import ic2.core.platform.recipes.mods.IRecipeModifier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class CountingModifier
implements IRecipeModifier {
    IInput desiredItem;
    int amountWanted;
    int found;

    public CountingModifier(Item input, int amountWanted) {
        this(new ItemInput(input), amountWanted);
    }

    public CountingModifier(IInput desiredItem, int amountWanted) {
        this.desiredItem = desiredItem;
        this.amountWanted = amountWanted;
    }

    public CountingModifier(FriendlyByteBuf buffer) {
        this.desiredItem = IngredientRegistry.INSTANCE.readInput(buffer);
        this.amountWanted = buffer.m_130242_();
    }

    public CountingModifier(JsonObject obj) {
        this.desiredItem = IngredientRegistry.INSTANCE.readInput(obj.getAsJsonObject("input"));
        this.amountWanted = obj.get("amount").getAsInt();
    }

    @Override
    public void reset() {
        this.found = 0;
    }

    @Override
    public boolean isSlotValid(ItemStack input) {
        if (this.desiredItem.matches(input)) {
            this.found += input.m_41613_();
        }
        return true;
    }

    @Override
    public boolean isOutputItem(ItemStack input) {
        return false;
    }

    @Override
    public ItemStack applyChanges(ItemStack input, boolean forDisplay) {
        if (!forDisplay) {
            int amount = this.found / this.amountWanted;
            if (amount <= 0) {
                return ItemStack.f_41583_;
            }
            input.m_41721_(input.m_41776_() - amount);
        }
        return input;
    }

    @Override
    public void serialize(FriendlyByteBuf buffer) {
        IngredientRegistry.INSTANCE.writeInput(this.desiredItem, buffer);
        buffer.m_130130_(this.amountWanted);
    }

    @Override
    public JsonObject serialize() {
        JsonObject obj = new JsonObject();
        obj.add("input", (JsonElement)IngredientRegistry.INSTANCE.serializeInput(this.desiredItem));
        obj.addProperty("amount", (Number)this.amountWanted);
        return obj;
    }
}

