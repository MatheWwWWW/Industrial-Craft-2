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
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.common.crafting.CraftingHelper
 */
package ic2.api.recipes.ingridients.inputs;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ic2.api.recipes.ingridients.inputs.IInput;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.CraftingHelper;

public class ItemListInput
implements IInput {
    List<ItemStack> list = new ObjectArrayList();
    int size;
    boolean or;

    private ItemListInput() {
    }

    public ItemListInput(JsonObject obj) {
        this.size = obj.get("size").getAsInt();
        this.or = obj.get("or").getAsBoolean();
        for (JsonElement el : obj.getAsJsonArray("items")) {
            ItemStack stack = CraftingHelper.getItemStack((JsonObject)el.getAsJsonObject(), (boolean)true);
            if (stack.m_41619_()) continue;
            this.list.add(stack);
        }
    }

    public ItemListInput(FriendlyByteBuf buffer) {
        int size = buffer.m_130242_();
        for (int i = 0; i < size; ++i) {
            ItemStack stack = buffer.m_130267_();
            if (stack.m_41619_()) continue;
            this.list.add(stack);
        }
        this.size = buffer.m_130242_();
        this.or = buffer.readBoolean();
    }

    public ItemListInput(List<ItemStack> list, int size, boolean or) {
        this.list.addAll(list);
        this.size = size;
        this.or = or;
    }

    public static IInput createItemList(ItemLike ... prov) {
        return ItemListInput.createItemList(1, true, prov);
    }

    public static IInput createItemList(int size, ItemLike ... prov) {
        return ItemListInput.createItemList(size, true, prov);
    }

    public static IInput createItemList(int size, boolean or, ItemLike ... prov) {
        ItemListInput inv = new ItemListInput();
        inv.size = size;
        inv.or = or;
        for (int i = 0; i < prov.length; ++i) {
            ItemStack stack = new ItemStack(prov[i]);
            if (stack.m_41619_()) continue;
            inv.list.add(stack);
        }
        return inv;
    }

    public static IInput createItemList(ItemStack ... prov) {
        return ItemListInput.createItemList(1, true, prov);
    }

    public static IInput createItemList(int size, ItemStack ... prov) {
        return ItemListInput.createItemList(size, true, prov);
    }

    public static IInput createItemList(int size, boolean or, ItemStack ... stacks) {
        ItemListInput inv = new ItemListInput();
        inv.size = size;
        inv.or = or;
        for (int i = 0; i < stacks.length; ++i) {
            ItemStack stack = stacks[i];
            if (stack.m_41619_()) continue;
            inv.list.add(stack.m_41777_());
        }
        return inv;
    }

    @Override
    public List<ItemStack> getComponents() {
        ObjectArrayList result = new ObjectArrayList();
        int m = this.list.size();
        for (int i = 0; i < m; ++i) {
            result.add(IInput.copyWithSize(this.list.get(i), this.size));
        }
        return result;
    }

    @Override
    public int getInputSize() {
        return this.size;
    }

    @Override
    public boolean matches(ItemStack stack) {
        if (this.or) {
            int m = this.list.size();
            for (int i = 0; i < m; ++i) {
                if (!IInput.isStackEqual(this.list.get(i), stack)) continue;
                return true;
            }
            return false;
        }
        int m = this.list.size();
        for (int i = 0; i < m; ++i) {
            if (IInput.isStackEqual(this.list.get(i), stack)) continue;
            return false;
        }
        return true;
    }

    @Override
    public void serialize(FriendlyByteBuf buffer) {
        buffer.m_130130_(this.list.size());
        int m = this.list.size();
        for (int i = 0; i < m; ++i) {
            buffer.m_130055_(this.list.get(i));
        }
        buffer.m_130130_(this.size);
        buffer.writeBoolean(this.or);
    }

    @Override
    public JsonObject serialize() {
        JsonArray array = new JsonArray();
        for (ItemStack stack : this.list) {
            array.add((JsonElement)IInput.writeItemStack(stack));
        }
        JsonObject obj = new JsonObject();
        obj.add("items", (JsonElement)array);
        obj.addProperty("or", Boolean.valueOf(this.or));
        obj.addProperty("size", (Number)this.size);
        return obj;
    }
}

