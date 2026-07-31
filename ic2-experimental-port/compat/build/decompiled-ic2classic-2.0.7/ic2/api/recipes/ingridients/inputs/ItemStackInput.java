/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.common.crafting.CraftingHelper
 */
package ic2.api.recipes.ingridients.inputs;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ic2.api.recipes.ingridients.inputs.IInput;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.List;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.crafting.CraftingHelper;

public class ItemStackInput
implements IInput {
    ItemStack input;
    int size;

    public ItemStackInput(JsonObject obj) {
        this.input = CraftingHelper.getItemStack((JsonObject)obj.getAsJsonObject("item"), (boolean)true);
        this.size = obj.get("size").getAsInt();
    }

    public ItemStackInput(FriendlyByteBuf buffer) {
        this.input = buffer.m_130267_();
        this.size = buffer.readByte();
    }

    public ItemStackInput(ItemStack input, int size) {
        this.input = input.m_41777_();
        this.size = size;
    }

    public ItemStackInput(ItemStack input) {
        this(input, input.m_41613_());
    }

    @Override
    public List<ItemStack> getComponents() {
        return ObjectLists.singleton((Object)IInput.copyWithSize(this.input, this.size));
    }

    @Override
    public int getInputSize() {
        return this.size;
    }

    @Override
    public boolean matches(ItemStack stack) {
        return IInput.isStackEqual(this.input, stack);
    }

    @Override
    public void serialize(FriendlyByteBuf buffer) {
        buffer.m_130055_(this.input);
        buffer.writeByte(this.size);
    }

    @Override
    public JsonObject serialize() {
        JsonObject obj = new JsonObject();
        obj.add("item", (JsonElement)IInput.writeItemStack(this.input));
        obj.addProperty("size", (Number)this.size);
        return obj;
    }
}

